# Development and Publishing

## Dependency Analysis

The project uses the [`com.autonomousapps.dependency-analysis`](https://github.com/autonomousapps/dependency-analysis-gradle-plugin) plugin to maintain a clean classpath and keep published Maven metadata accurate (`api` vs `implementation`, unused deps, transitive deps that should be direct).

### 1. Project-wide health check

To audit unused, transitive, and misconfigured dependencies across the whole build, run:

```bash
./gradlew buildHealth
```

To analyze a single module, use `projectHealth`:

```bash
./gradlew :<gradle-path>:projectHealth
```

Example for `shared/foundation/core/common` (published artifact id `shared-foundation-core-common`):

```bash
./gradlew :shared:foundation:core:common:projectHealth
```

### 2. Dependency insight

If the report says a dependency **should be declared directly**, inspect how it reaches the module:

```bash
./gradlew :<gradle-path>:dependencyInsight --dependency <coordinates> --configuration <configuration-name>
```

This repository is **Kotlin Multiplatform** with a JVM target. For the JVM compile classpath of `commonMain` dependencies, use `jvmCompileClasspath` (not plain `compileClasspath`).

Example for `core:common` and `org.jetbrains.kotlinx:kotlinx-serialization-json`:

```bash
./gradlew :shared:foundation:core:common:dependencyInsight --dependency org.jetbrains.kotlinx:kotlinx-serialization-json --configuration jvmCompileClasspath
```

Other targets (Android, iOS, Wasm) expose different configurations; list them with `./gradlew :<gradle-path>:dependencies` if you need a non-JVM variant.

To dump a full, searchable dependency tree for a module (JVM compile classpath):

```bash
./gradlew :<gradle-path>:dependencies --configuration jvmCompileClasspath > build/reports/<report-name>.txt
```

Example:

```bash
./gradlew :shared:foundation:core:common:dependencies --configuration jvmCompileClasspath > build/reports/core-common-jvm-dependencies.txt
```

On Windows PowerShell you can use the same redirection; ensure `build/reports` exists or choose another path.

## Artifact Deployment

The project utilizes the `com.vanniktech.maven.publish` plugin for artifact management.

### 1. Local Maven Deployment (`mavenLocal`)

1. **Publish artifacts to local Maven repository:**
   Uploads all KMP library artifacts to your machine's local Maven cache (`~/.m2/repository/`):
   ```bash
   ./gradlew publishToMavenLocal
   ```

2. **Connect in your consumer project:**
   Add `mavenLocal()` to your consumer project's `settings.gradle.kts` (or root `build.gradle.kts`):
   ```kotlin
   dependencyResolutionManagement {
       repositories {
           mavenLocal() // Must be at the top to take priority over remote repositories
           mavenCentral()
           google()
       }
   }
   ```

### 2. Remote Staging

Uploads all publications to the remote staging repository (Maven Central) without performing a final release. Use this for manual verification in the repository manager:

```bash
./gradlew publishAllPublicationsToMavenCentralRepository
```

### 3. Full Release Cycle

Performs a complete deployment workflow, including uploading, closing the staging repository, and releasing artifacts to Maven Central:

```bash
./gradlew publishAndReleaseToMavenCentral
```

## NPM Package Generation & Publishing

The project exports TypeScript declarations, Zod schemas, and route constants to npm as [`@mudrichenkoevgeny/shared-foundation`](https://www.npmjs.com/package/@mudrichenkoevgeny/shared-foundation).

### 1. Local Package Generation

To compile and generate the NPM package artifact (CJS, ESM, and TypeScript typings):

```bash
./gradlew generateNpmPackage
```

The output package will be generated at `build/npm-package/`.

### 2. Local Testing in Consumer Web Projects (`npm pack`)

To test the generated package locally in any web project or library (pnpm workspace) without publishing to a remote registry:

1. **Generate the `.tgz` tarball archive:**
   ```bash
   cd build/npm-package
   npm pack
   ```
   The archive `mudrichenkoevgeny-shared-foundation-0.0.57.tgz` will be created inside `build/npm-package/`.

2. **Connect to your consumer pnpm workspace:**
   In your consumer project, update the catalog in `pnpm-workspace.yaml`:
   ```yaml
   catalog:
     '@mudrichenkoevgeny/shared-foundation': 'file:/path/to/shared-foundation/build/npm-package/mudrichenkoevgeny-shared-foundation-0.0.57.tgz'
   ```
   And run from the root directory:
   ```bash
   pnpm install
   ```

### 3. Publishing to npmjs.com

1. Authenticate with your npm account (if not logged in):
   ```bash
   npm login
   ```
2. Publish the generated package directly from the project root:
   ```bash
   npm publish ./build/npm-package --access public
   ```
