package com.example.valetkey.storage;

import com.example.valetkey.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Same contract as {@link com.example.valetkey.service.AWSS3Service}, pointed at
 * IBM Cloud Object Storage instead of AWS S3. Reuses the "ibmS3Client" bean
 * from IBMConfig for plain object operations, and builds its own S3Presigner
 * (pointed at the IBM endpoint) for presigned URLs, since S3Client itself
 * cannot presign.
 */
@Service
public class IbmCosStorageProvider implements StorageProvider {

    private static final Logger log = LoggerFactory.getLogger(IbmCosStorageProvider.class);

    @Autowired
    @Qualifier("ibmS3Client")
    private S3Client ibmS3Client;

    @Value("${ibm.cos.bucket-name:valet-backup}")
    private String bucketName;

    @Value("${ibm.cos.endpoint:https://s3.us-south.cloud-object-storage.appdomain.cloud}")
    private String endpoint;

    @Value("${ibm.cos.region:us-south}")
    private String region;

    @Value("${ibm.cos.access-key:}")
    private String accessKey;

    @Value("${ibm.cos.secret-key:}")
    private String secretKey;

    @Override
    public String getProviderName() {
        return "IBM_COS";
    }

    @Override
    public String generatePresignedUploadUrl(String objectKey, int expiryMinutes, User user) {
        if (!user.isCreate() && !user.isWrite()) {
            throw new RuntimeException("User does not have permission to upload files");
        }
        try (S3Presigner presigner = createPresigner()) {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .build();

            PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(expiryMinutes))
                    .putObjectRequest(putObjectRequest)
                    .build();

            PresignedPutObjectRequest presignedRequest = presigner.presignPutObject(presignRequest);
            log.info("Generated IBM COS presigned upload URL for key: {}", objectKey);
            return presignedRequest.url().toString();
        } catch (Exception e) {
            log.error("Error generating presigned upload URL (IBM COS) for key: {}", objectKey, e);
            throw new RuntimeException("Failed to generate upload URL: " + e.getMessage(), e);
        }
    }

    @Override
    public String generatePresignedDownloadUrl(String objectKey, int expiryMinutes, User user) {
        if (!user.isRead()) {
            throw new RuntimeException("User does not have permission to download files");
        }
        if (!objectExists(objectKey)) {
            throw new RuntimeException("File not found: " + objectKey);
        }
        try (S3Presigner presigner = createPresigner()) {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .build();

            GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(expiryMinutes))
                    .getObjectRequest(getObjectRequest)
                    .build();

            return presigner.presignGetObject(presignRequest).url().toString();
        } catch (Exception e) {
            log.error("Error generating presigned download URL (IBM COS) for key: {}", objectKey, e);
            throw new RuntimeException("Failed to generate download URL: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteObject(String objectKey) {
        try {
            ibmS3Client.deleteObject(DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .build());
            log.debug("Deleted object from IBM COS: {}", objectKey);
        } catch (Exception e) {
            log.error("Error deleting object from IBM COS: {}", objectKey, e);
            throw new RuntimeException("Failed to delete file: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean objectExists(String objectKey) {
        try {
            ibmS3Client.headObject(HeadObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .build());
            return true;
        } catch (NoSuchKeyException e) {
            return false;
        } catch (Exception e) {
            log.error("Error checking object existence (IBM COS): {}", objectKey, e);
            return false;
        }
    }

    @Override
    public List<String> listObjects(String prefix) {
        List<String> result = new ArrayList<>();
        try {
            ListObjectsV2Response listResponse = ibmS3Client.listObjectsV2(ListObjectsV2Request.builder()
                    .bucket(bucketName)
                    .prefix(prefix)
                    .build());
            for (S3Object s3Object : listResponse.contents()) {
                result.add(s3Object.key());
            }
        } catch (Exception e) {
            log.error("Error listing objects with prefix (IBM COS): {}", prefix, e);
        }
        return result;
    }

    @Override
    public InputStream getObjectInputStream(String objectKey) {
        try {
            return ibmS3Client.getObject(GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .build());
        } catch (Exception e) {
            log.error("Error getting object input stream (IBM COS): {}", objectKey, e);
            throw new RuntimeException("Failed to read file: " + e.getMessage(), e);
        }
    }

    @Override
    public long getObjectContentLength(String objectKey) {
        try {
            HeadObjectResponse head = ibmS3Client.headObject(HeadObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .build());
            return head.contentLength();
        } catch (Exception e) {
            log.error("Error getting object content length (IBM COS): {}", objectKey, e);
            throw new RuntimeException("Failed to get content length: " + e.getMessage(), e);
        }
    }

    @Override
    public void uploadObject(String objectKey, byte[] data, String contentType) {
        try {
            PutObjectRequest putRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .contentType(contentType)
                    .build();
            ibmS3Client.putObject(putRequest, RequestBody.fromInputStream(
                    new ByteArrayInputStream(data), data.length));
            log.debug("Uploaded object to IBM COS: {}", objectKey);
        } catch (Exception e) {
            log.error("Error uploading object to IBM COS: {}", objectKey, e);
            throw new RuntimeException("Failed to upload object: " + e.getMessage(), e);
        }
    }

    @Override
    public void uploadObject(String objectKey, Path sourcePath, String contentType) {
        try {
            PutObjectRequest putRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .contentType(contentType)
                    .build();
            ibmS3Client.putObject(putRequest, sourcePath);
            log.debug("Uploaded object from path to IBM COS: {}", objectKey);
        } catch (Exception e) {
            log.error("Error uploading object from path to IBM COS: {}", objectKey, e);
            throw new RuntimeException("Failed to upload object: " + e.getMessage(), e);
        }
    }

    private S3Presigner createPresigner() {
        AwsBasicCredentials creds = AwsBasicCredentials.create(accessKey, secretKey);
        return S3Presigner.builder()
                .region(Region.of(region))
                .endpointOverride(URI.create(endpoint))
                .credentialsProvider(StaticCredentialsProvider.create(creds))
                .serviceConfiguration(S3Configuration.builder()
                        .pathStyleAccessEnabled(true)
                        .build())
                .build();
    }
}
