# Development and Publishing

## Artifact Deployment

The project utilizes the `com.vanniktech.maven.publish` plugin for artifact management.

### 1. Remote Staging
Uploads all publications to the remote staging repository (Maven Central) without performing a final release. Use this for manual verification in the repository manager:

./gradlew publishAllPublicationsToMavenCentralRepository

### 2. Full Release Cycle
Performs a complete deployment workflow, including uploading, closing the staging repository, and releasing artifacts to Maven Central:

./gradlew publishAndReleaseToMavenCentral