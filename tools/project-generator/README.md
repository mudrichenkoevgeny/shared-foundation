# tools/project-generator

Internal Kotlin JVM module and CLI engine for scaffolding clean, minimal, production-ready downstream Kotlin Multiplatform (KMP) contract repositories (`<name>-shared`) on disk in a single command.

Triggered via the root Gradle tasks: **`./gradlew initProject`** or **`./gradlew scaffoldProject`**.

---

## Generator Architecture & Core Engine

The project skeleton generator is implemented as an internal (non-published) JVM module under `tools/project-generator/`:

- **[ProjectGenerator]**: Main generator engine. Copies binary infrastructure files (`gradlew`, `gradlew.bat`, `gradle/wrapper/*`, `gradle-daemon-jvm.properties`, `.editorconfig`, `.agent/global/`), processes physical resource templates from `src/main/resources/templates/`, and initializes a Git repository.
- **[ProjectGeneratorConfig]**: Parameter resolution data class. Reads system properties passed by Gradle, validates required inputs, and computes `groupId`, `packageName`, `packageDirPath`, `npmScope`, `npmPackageName`, and `targetDir` with safe defaults.
- **[TemplateReplacer]**: String substitution engine. Replaces generic placeholder tokens (`{{PROJECT_NAME}}`, `{{GROUP_ID}}`, `{{PACKAGE_NAME}}`, `{{SF_VERSION}}`, `{{KOTLIN_VERSION}}`, `{{TARGET_JVM}}`, `{{TOOLCHAIN_JVM}}`, `{{DETEKT_VERSION}}`, `{{KTLINT_VERSION}}`, `{{SERIALIZATION_VERSION}}`, `{{MAVEN_LOCAL_REPOS}}`) with dynamic values injected directly from `shared-foundation`'s catalog (`libs.versions.toml`).
- **[ProjectGeneratorVerifier]**: In-process E2E verification engine. Scaffolds a test project in `build/tmp/scaffold-test/scaffold-test-shared` and executes `./gradlew check publishToMavenLocal generateNpmPackage` inside it to ensure zero template rot.

---

## CLI Task Usage & Parameters

Scaffold a new downstream contracts repository:

```bash
./gradlew initProject -Pname=sample -Pgroup=com.sample -PnpmScope=@sample -PlinkLocal=true
```

### Command Parameter Matrix

| Parameter | Default Value | Description |
| :--- | :--- | :--- |
| **`-Pname`** | *(Required)* | Short name of the project (e.g. `sample`, `payment`). Hyphens are sanitized for Kotlin package paths. |
| **`-PgroupId` / `-Pgroup`** | `com.<name>` | Maven group ID (e.g. `com.sample`). If it does not end with `.shared`, `.shared` is automatically appended to the base package name (`com.sample.shared`). |
| **`-PnpmScope`** | `@<name>` | NPM scope name (e.g. `@sample`). |
| **`-PtargetDir`** | `../<name>-shared` | Absolute or relative target path for the scaffolded repository. |
| **`-PlinkLocal`** | `false` | When `true`, configures `mavenLocal()` first in the generated `settings.gradle.kts` for local development. |
| **`-PskipGit`** | `false` | When `true`, skips running `git init` in the created directory. |

---

## Resource Templates (`src/main/resources/templates/`)

All text-based files for scaffolded repositories are stored as static `.template` resource files:

- **Root Configurations:** `build.gradle.kts.template`, `settings.gradle.kts.template`, `gradle.properties.template`, `libs.versions.toml.template`, `.gitignore.template`, `AGENTS.md.template`, `ARCHITECTURE.md.template`, `README.md.template`, `.agent/local/project-overview.md.template`, `.github/workflows/build.yml.template`.
- **`core/common`:** `core/common/build.gradle.kts.template`, starter localization dictionaries (`en/common.json.template`, `ru/common.json.template`).
- **`testing`:** `testing/build.gradle.kts.template`.
- **`tools/npm-generator-runner`:** `tools/npm-generator-runner/build.gradle.kts.template`, `NpmClassCollectorRunner.kt.template`, `NpmConfigProviderRunner.kt.template`, `NpmGeneratorRunner.kt.template`.
- **`bom`:** `bom/build.gradle.kts.template`.

---

## E2E Automated Verification

The generator is continuously verified against template rot and version drift:

```bash
./gradlew verifyProjectGenerator
```

This task compiles `shared-foundation`, publishes artifacts to `mavenLocal()`, scaffolds a test project in `build/tmp/scaffold-test/scaffold-test-shared`, and verifies that `./gradlew check publishToMavenLocal generateNpmPackage` passes cleanly out of the box inside the scaffolded project.

[ProjectGenerator]: src/main/kotlin/io/github/mudrichenkoevgeny/shared/foundation/tools/projectgenerator/ProjectGenerator.kt
[ProjectGeneratorConfig]: src/main/kotlin/io/github/mudrichenkoevgeny/shared/foundation/tools/projectgenerator/ProjectGeneratorConfig.kt
[TemplateReplacer]: src/main/kotlin/io/github/mudrichenkoevgeny/shared/foundation/tools/projectgenerator/TemplateReplacer.kt
[ProjectGeneratorVerifier]: src/main/kotlin/io/github/mudrichenkoevgeny/shared/foundation/tools/projectgenerator/ProjectGeneratorVerifier.kt
