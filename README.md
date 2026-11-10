# Cloud Based Storage

Cloud Based Storage is a cloud-based file storage application built with Spring Boot and React. It supports file uploads to Amazon S3, folder organization, file sharing, storage quotas, and administrative controls.

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![React](https://img.shields.io/badge/React-18.2.0-blue.svg)](https://react.dev/)
[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![MySQL](https://img.shields.io/badge/MySQL-8-blue.svg)](https://www.mysql.com/)
[![AWS S3](https://img.shields.io/badge/AWS-S3-orange.svg)](https://aws.amazon.com/s3/)

## Run locally with one command

On **Linux x86_64 or Ubuntu/WSL2**, clone and start:

```bash
git clone https://github.com/lennoxiepeal/Cloud-based-storage.git
cd Cloud-based-storage
./scripts/start-local.sh
```

First start downloads Java 17, Node.js, and Maven into `.tools/`, installs locked dependencies, and builds the application. Allow several minutes and an internet connection. Later starts reuse the installation; changed application sources trigger a rebuild. Base tools required: Bash, curl, tar, xz, Python 3, and util-linux (standard Ubuntu packages). No AWS account, Docker, MySQL, or preinstalled Java/Node/Maven is needed.

Open **http://localhost:8080**. Sign in as `demo` / `demo123` or `admin` / `admin123`.

```bash
./scripts/stop-local.sh     # Stop both services; preserve your data
./scripts/status-local.sh  # Check service status
```

The local profile stores metadata in persistent H2 and file bytes in persistent S3-compatible storage under `.local-data/`. Local data, downloaded runtimes, logs, and build artifacts are excluded from Git. A new clone starts with fresh demo/admin accounts and sample files in `demo-files/`; uploaded files are not copied between machines. Real AWS/IBM backup and CloudWatch integrations are disabled locally.

See [DEMO_GUIDE.md](./DEMO_GUIDE.md) for setup, [DEMO_SCRIPT_VI.md](./DEMO_SCRIPT_VI.md) for the 4:45 Vietnamese presentation script, and [VERIFICATION.md](./VERIFICATION.md) for test coverage. The cloud configuration instructions below describe a separate deployment option.

## Architecture

<img width="1476" height="1506" alt="Cloud Based Storage architecture diagram" src="https://github.com/user-attachments/assets/a8a9889a-6310-4657-8549-2b8c1409f7a7" />

## Features

### File and folder management

- **Folder organization:** Create nested folders and navigate between them using breadcrumbs.
- **Direct S3 uploads:** Generate presigned URLs so clients can upload files directly to Amazon S3.
- **File downloads:** Generate time-limited URLs for downloading stored files.
- **File search:** Search by file name, type, and size, with support for filters.
- **Public sharing:** Create shareable links for individual files.
- **Storage quotas:** Set per-user storage limits and track usage.
- **Access control:** Assign user and administrator roles.
- **Authentication:** Authenticate users through Spring Security with session-based authentication.

### Administration

The administration panel provides tools for managing users and monitoring storage usage:

- View system-wide user and storage statistics.
- View user accounts and update their roles or permissions.
- Set storage quotas for individual users or across the system.
- Identify accounts with the highest storage usage.
- Configure create, read, and write permissions.

### Performance

- Generate upload URLs asynchronously.
- Use HikariCP for database connection pooling.
- Track per-user storage usage and quotas.
- Support bulk file operations, including delete, move, and download.
- Configure the application for deployment behind a load balancer.

### Backup and monitoring

- Process backup tasks using AWS SQS and Lambda.
- Collect application metrics through AWS CloudWatch.
- Record application errors for troubleshooting.

## Technology Stack

### Backend

| Technology | Purpose |
|---|---|
| Spring Boot 3.2.0 | Application framework |
| Spring Security | Authentication and authorization |
| Spring Data JPA | Database access and persistence |
| HikariCP | Database connection pooling |
| MySQL 8 | Default database; PostgreSQL and H2 drivers are also included |
| AWS SDK | Integration with S3, SQS, and CloudWatch |
| AWS SDK S3 client | S3-compatible integration with IBM Cloud Object Storage |

### Frontend

| Technology | Purpose |
|---|---|
| React 18.2.0 | User interface |
| React Router | Client-side routing |
| Axios | HTTP requests |
| React Icons | Interface icons |

### Infrastructure

| Service | Purpose |
|---|---|
| AWS S3 | Primary object storage |
| AWS SQS | Message queuing |
| AWS Lambda | Serverless backup processing |
| AWS CloudWatch | Monitoring and metrics |
| IBM Cloud Object Storage | Secondary backup storage |

## Prerequisites

Make sure the following tools and accounts are available before setting up the project:

- Java 17 or later
- Node.js 16 or later and npm
- MySQL 8 (used by the example configuration)
- Maven 3.6 or later
- An AWS account with access to S3, SQS, Lambda, and CloudWatch
- An IBM Cloud account (optional, for backup storage)

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/lennoxiepeal/Cloud-based-storage.git
cd Cloud-based-storage
```

### 2. Configure the backend

#### Database

Create `application.properties` from the provided example file:

```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

Create the MySQL database `cloud_based_storage`, then update `application.properties` (the example file uses the legacy database name):

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/cloud_based_storage?useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.username=your_username
spring.datasource.password=your_password
```

#### AWS credentials and services

Set these environment variables and replace the literal `fake` credentials in `application.properties` with `aws.s3.access-key=${AWS_ACCESS_KEY_ID}` and `aws.s3.secret-key=${AWS_SECRET_ACCESS_KEY}`. Configure S3 CORS for the frontend origin:

```bash
export AWS_ACCESS_KEY_ID=your_access_key
export AWS_SECRET_ACCESS_KEY=your_secret_key
export AWS_REGION=ap-southeast-1
export AWS_S3_BUCKET_NAME=your-bucket-name
export AWS_SQS_QUEUE_URL=https://sqs.region.amazonaws.com/account/queue-name
```

#### Start the backend

Build and run the Spring Boot application:

```bash
mvn clean install
mvn spring-boot:run
```

The backend runs at `http://localhost:8080` by default.

### 3. Configure and start the frontend

From the project root, move to the frontend directory and install its dependencies:

```bash
cd frontend
npm install
```

For the separate frontend development server, set `REACT_APP_API_URL=http://localhost:8080` in `frontend/.env`. The client defaults to the same origin, as used by the packaged local deployment in [DEMO_GUIDE.md](./DEMO_GUIDE.md).

Start the development server:

```bash
npm start
```

The frontend runs at `http://localhost:3000` by default.

### 4. Set up an administrator account

After the application has started and the database has been initialized, an existing user can be assigned the administrator role:

```sql
UPDATE `user`
SET role = 'ROLE_ADMIN'
WHERE username = 'admin';
```

## Configuration

### Environment variables

| Variable | Description | Requirement |
|---|---|---|
| `AWS_ACCESS_KEY_ID` | AWS access key ID | Required |
| `AWS_SECRET_ACCESS_KEY` | AWS secret access key | Required |
| `AWS_REGION` | AWS region | `ap-southeast-1` by default |
| `AWS_S3_BUCKET_NAME` | Name of the S3 bucket | Required |
| `AWS_SQS_QUEUE_URL` | URL of the SQS queue | Required |
| `STORAGE_PRIMARY_PROVIDER` | Provider for new uploads: `S3` or `IBM_COS` | `S3` by default |
| `INTERNAL_API_KEY` | Shared key for internal backup callbacks | Required when configuring backup callbacks |
| `IBM_COS_BUCKET_NAME` | Name of the IBM COS bucket | Optional |

For IBM COS, configure `ibm.cos.access-key`, `ibm.cos.secret-key`, `ibm.cos.endpoint`, and `ibm.cos.region` in `application.properties`. Backup is disabled in the example (`backup.enabled=false`); configure SQS, Lambda, and internal callbacks before enabling it.

### Database and connection pool

The following properties configure the database connection and HikariCP connection pool:

```properties
spring.datasource.url=jdbc:postgresql://host:port/database
spring.datasource.username=username
spring.datasource.password=password

# Connection pool
spring.datasource.hikari.maximum-pool-size=50
spring.datasource.hikari.minimum-idle=10
spring.datasource.hikari.connection-timeout=30000
```

### Server settings

The following example configures the embedded Tomcat thread pool and HTTP session timeout:

```properties
# Tomcat thread pool
server.tomcat.threads.max=300
server.tomcat.threads.min-spare=30
server.tomcat.max-connections=10000

# Session
server.servlet.session.timeout=30m
```

## API Reference

The examples below illustrate the main API endpoints. See the [backend controllers](./src/main/java/com/example/valetkey/controller) and [frontend API client](./frontend/src/services/api.js) for details.

### Authentication

```http
POST /api/login
Content-Type: application/json

{
  "username": "user",
  "password": "password"
}
```

### File operations

```http
# Generate an upload URL
POST /api/files/upload-url
{
  "fileName": "example.pdf",
  "fileSize": 1024000,
  "folderId": 1
}

# List files in a folder
GET /api/files/list?folderId=1&page=0&size=20

# Download a file
GET /api/files/{fileId}/download

# Delete a file
DELETE /api/files/{fileId}

# Search files
GET /api/files/search?query=example&page=0&size=20
```

### Administrative operations

```http
# Retrieve the user list
GET /admin/user-list

# Update a user's storage quota
PUT /admin/quota/{userId}
{
  "storageQuotaGb": 2.0
}

# Update a user's permissions
POST /admin/permission/{userId}
{
  "create": true,
  "read": true,
  "write": true
}

# Retrieve system statistics
GET /admin/stats?top=5
```

## Usage Examples

### Upload a file

The upload process uses a presigned URL. The client first requests an upload URL from the backend, uploads the file directly to S3, and then confirms the upload.

```javascript
import { fileAPI } from './services/api';

// 1. Request a presigned upload URL
const response = await fileAPI.generateUploadUrl('document.pdf', 1024000, folderId);
const { uploadUrl, fileId } = response.data;

// 2. Upload the file directly to S3
await fileAPI.uploadToS3(file, uploadUrl);

// 3. Confirm the upload with the backend
await fileAPI.confirmUpload(fileId, file.type);
```

### Bulk operations

The client can also perform operations on multiple files:

```javascript
// Delete multiple files
await fileAPI.bulkDelete([fileId1, fileId2, fileId3]);

// Move multiple files to another folder
await fileAPI.bulkMove([fileId1, fileId2], targetFolderId);

// Download multiple files as a ZIP archive
const response = await fileAPI.bulkDownload([fileId1, fileId2]);
window.open(response.data.downloadUrl);
```

## Testing

The repository includes a [JMeter load test plan](./jmeter/valetkey-load-test.jmx). Open it in Apache JMeter and configure the host, port, credentials, and test parameters for your environment. Measure performance against your deployment.

## Project Structure

```text
Cloud-based-storage/
|-- src/main/java/com/example/valetkey/
|   |-- config/
|   |-- controller/
|   |-- lambda/
|   |-- model/
|   |-- repository/
|   |-- service/
|   `-- storage/
|-- src/main/resources/application.properties.example
|-- frontend/
|-- grafana/
|-- jmeter/
`-- pom.xml
```

The Java package remains `com.example.valetkey`, and Maven currently produces `demo-0.0.1-SNAPSHOT.jar`. These existing code identifiers are retained; the project is named **Cloud Based Storage**.

## Security

The project includes the following security measures:

- Session-based user authentication.
- Role-based access control (RBAC).
- Password hashing with BCrypt.
- CORS configuration.
- JPA-based database access to help prevent SQL injection.
- XSS protection.
- File upload validation.

## Deployment

### Build for production

Build the backend JAR:

```bash
mvn clean package
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

Build the frontend:

```bash
cd frontend
npm run build
```

The generated frontend build can be served using Nginx or another static file server.

### Docker

The following Dockerfile provides a basic image for running the backend JAR:

```dockerfile
FROM openjdk:17-jdk-slim
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

### Deployment checklist

1. Set the required environment variables.
2. Configure the database connection.
3. Create and configure the AWS S3 bucket and SQS queue.
4. Configure CORS for the frontend domain.
5. Set up a reverse proxy, such as Nginx, if required.

## Monitoring

### CloudWatch metrics

The application tracks the following metrics through CloudWatch:

- Upload success and failure rates.
- Backup processing latency.
- Storage usage trends.
- API response times.

### Spring Boot Actuator

The following Actuator endpoints are available for health checks and metrics:

```http
GET /actuator/health
GET /actuator/metrics
GET /actuator/prometheus
```

## Contributing

Contributions are welcome at [lennoxiepeal/Cloud-based-storage](https://github.com/lennoxiepeal/Cloud-based-storage). To propose a change:

1. Fork the repository.
2. Create a feature branch: `git checkout -b feature/your-feature`.
3. Commit your changes: `git commit -m "Describe your changes"`.
4. Push the branch: `git push origin feature/your-feature`.
5. Open a pull request.
