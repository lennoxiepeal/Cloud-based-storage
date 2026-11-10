const path = require('path');
const S3rver = require('../.tools/local-services/node_modules/s3rver');
new S3rver({
  address: '0.0.0.0', port: 4569, silent: true,
  directory: path.resolve(__dirname, '../.local-data/objects'),
  configureBuckets: [{ name: 'cloud-storage-demo', configs: [
    '<CORSConfiguration><CORSRule><AllowedOrigin>http://localhost:8080</AllowedOrigin><AllowedOrigin>http://127.0.0.1:8080</AllowedOrigin><AllowedMethod>GET</AllowedMethod><AllowedMethod>PUT</AllowedMethod><AllowedMethod>HEAD</AllowedMethod><AllowedHeader>*</AllowedHeader><ExposeHeader>ETag</ExposeHeader></CORSRule></CORSConfiguration>'
  ] }]
}).run().then(() => console.log('Persistent S3 demo service on port 4569'));
