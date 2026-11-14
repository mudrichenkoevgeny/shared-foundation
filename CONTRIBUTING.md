# Development and Publishing

## Code Quality, Formatting & Pre-Commit Verification

Before submitting changes or creating a commit, run the pre-commit verification suite to ensure code style, static analysis, and compilation rules pass cleanly.

### 1. Code Style & Formatting (Ktlint)

Check for code style and formatting violations:
```bash
./gradlew ktlintCheck
```
If formatting errors occur (e.g. trailing whitespace, indentation, or imports), automatically fix them with:
```bash
./gradlew ktlintFormat
```

### 2. Static Analysis & Architectural Rules (Detekt)

Run Detekt static analysis, complexity checks, and custom library rules (`NoFullyQualifiedName`, `NoInlineComment`, `PreferValueOverName`, `ProhibitNonSafeAssertion`, `ProhibitPrintln`):
```bash
./gradlew detekt
```

### 3. Compilation & Full Verification

Verify Kotlin compilation across all target platforms:
```bash
./gradlew compileKotlin
```

Run the complete project verification suite (Ktlint, Detekt, Binary Compatibility API checks, multiplatform unit tests, and NPM package generation):
```bash
./gradlew check
```

---

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

Example for `core/common` (published artifact id `shared-foundation-core-common`):

```bash
./gradlew :core:common:projectHealth
```

### 2. Dependency insight

If the report says a dependency **should be declared directly**, inspect how it reaches the module:

```bash
./gradlew :<gradle-path>:dependencyInsight --dependency <coordinates> --configuration <configuration-name>
```

This repository is a **Kotlin Multiplatform (KMP)** library (targeting JVM, Android, iOS, and WasmJS). For inspecting the JVM compile classpath of `commonMain` dependencies, use `jvmCompileClasspath` (not plain `compileClasspath`).

Example for `core:common` and `org.jetbrains.kotlinx:kotlinx-serialization-json`:

```bash
./gradlew :core:common:dependencyInsight --dependency org.jetbrains.kotlinx:kotlinx-serialization-json --configuration jvmCompileClasspath
```

Other targets (Android, iOS, Wasm) expose different configurations; list them with `./gradlew :<gradle-path>:dependencies` if you need a non-JVM variant.

To dump a full, searchable dependency tree for a module (JVM compile classpath):

```bash
./gradlew :<gradle-path>:dependencies --configuration jvmCompileClasspath > build/reports/<report-name>.txt
```

Example:

```bash
./gradlew :core:common:dependencies --configuration jvmCompileClasspath > build/reports/core-common-jvm-dependencies.txt
```

On Windows PowerShell you can use the same redirection; ensure `build/reports` exists or choose another path.

## Artifact Deployment

The project utilizes the `com.vanniktech.maven.publish` plugin for artifact management.

### 1. Local Maven Deployment (`mavenLocal`)

Because `tools/gradle-plugin` is configured as an included build (`includeBuild`), running a plain `./gradlew publishToMavenLocal` only publishes library artifacts (`shared-foundation-bom`, `core-*`, `feature-*`, `testing`, `detekt-rules`). To publish both libraries and Gradle convention plugins to your machine's local Maven cache (`~/.m2/repository/`), specify both tasks:

1. **Publish all libraries and Gradle convention plugins:**
   ```bash
   ./gradlew publishToMavenLocal :gradle-plugin:publishToMavenLocal
   ```

   *To publish only the Gradle convention plugins and custom Detekt rules, run:*
   ```bash
   ./gradlew :gradle-plugin:publishToMavenLocal :tools:detekt-rules:publishToMavenLocal
   ```

2. **Connect in your consumer project:**
   Add `mavenLocal()` to both `pluginManagement` (for convention plugins) and `dependencyResolutionManagement` (for library artifacts) in your consumer project's `settings.gradle.kts`:
   ```kotlin
   pluginManagement {
       repositories {
           mavenLocal() // Required for shared-foundation Gradle convention plugins
           gradlePluginPortal()
           google()
           mavenCentral()
       }
   }

   dependencyResolutionManagement {
       repositories {
           mavenLocal() // Required for shared-foundation library artifacts (BOM, core-*, feature-*)
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
   - **From the repository root (`shared-foundation/`):**
     ```bash
     npm pack ./build/npm-package
     ```
   - **Or if your terminal is inside `build/npm-package/`:**
     ```bash
     npm pack
     ```

   The archive file (e.g. `mudrichenkoevgeny-shared-foundation-0.1.16.tgz`) will be created.

2. **Connect to your consumer pnpm workspace:**
   In your consumer project, update the catalog in `pnpm-workspace.yaml`:
   ```yaml
   catalog:
     '@mudrichenkoevgeny/shared-foundation': 'file:/path/to/shared-foundation/mudrichenkoevgeny-shared-foundation-0.1.16.tgz'
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
2. Publish the generated package:
   - **From the repository root:**
     ```bash
     npm publish ./build/npm-package --access public
     ```
   - **Or if your terminal is inside `build/npm-package/`:**
     ```bash
     npm publish --access public
     ```
