package com.example.valetkey.storage;

import com.example.valetkey.model.User;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

/**
 * Abstraction for any backend that can store and serve files
 * (AWS S3, IBM COS, MinIO, Azure Blob, ...).
 *
 * Implementations are plain Spring beans; {@link StorageProviderRegistry}
 * discovers all of them automatically and resolves by {@link #getProviderName()}.
 */
public interface StorageProvider {

    /**
     * Stable identifier for this provider, stored on {@code Resource.storageProvider}.
     * Must match a key used in configuration (e.g. "S3", "IBM_COS").
     */
    String getProviderName();

    String generatePresignedUploadUrl(String objectKey, int expiryMinutes, User user);

    String generatePresignedDownloadUrl(String objectKey, int expiryMinutes, User user);

    void deleteObject(String objectKey);

    boolean objectExists(String objectKey);

    InputStream getObjectInputStream(String objectKey);

    long getObjectContentLength(String objectKey);

    void uploadObject(String objectKey, byte[] data, String contentType);

    void uploadObject(String objectKey, Path sourcePath, String contentType);

    List<String> listObjects(String prefix);
}
