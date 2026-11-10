# tools/gradle-plugin

**Gradle Convention Plugins for shared-foundation** providing build tasks, quality checks, compiler conventions, publishing setup, and AI agent rules synchronization across Kotlin/JVM/KMP consumer applications (such as `backend-platform-sdk` and `kmp-platform-sdk`).

Published Maven artifact: **`shared-foundation-gradle-plugin`**.

## Included Convention Plugins

1. **`io.github.mudrichenkoevgeny.shared-foundation-agent-sync`** (`AgentSyncPlugin`):
   - **Task:** `syncAgentRules`.
   - **Description:** Extracts shared global AI agent rules (`common/`, `kotlin/`, and optionally `shared/`) directly from plugin resources into `.agent/global/`, and synchronizes the global `.editorconfig` file in the consumer project's root directory.
   - **IDE Integration:** Automatically hooks into IDE import (`prepareKotlinBuildScriptModel` and `prepareKotlinIdeaImport`).
   - **`.editorconfig` Local Overrides Marker:** Uses the `# --- LOCAL OVERRIDES ---` marker in `.editorconfig`:
     - **Global Standards (above marker):** Centralized formatting rules (4 spaces indent for Kotlin/Java, LF line endings, 120 max line length, imports layout) are automatically updated on sync.
     - **Local Overrides (below marker):** Repository-specific rules (e.g. `.sql`, `.env`, `.properties`, Dockerfiles, or module exclusions) are strictly preserved and never overwritten.
     - **Timestamp Optimization:** Only updates `.editorconfig` on disk if normalized LF content actually changed, avoiding unnecessary IDE file reload triggers.

2. **`io.github.mudrichenkoevgeny.shared-foundation-quality`** (`QualityPlugin`):
   - **Description:** Applies and configures Ktlint and Detekt across project modules.
   - **Two-Tier Detekt Configuration:**
     - **Tier 1 (Base Rules):** Extracts clean, project-agnostic `detekt-base.yml` directly from plugin resources (`build/generated/detekt/detekt-base.yml`).
     - **Tier 2 (Local Overrides):** Merges optional `$rootDir/config/detekt/detekt.yml` on top of the base configuration, allowing consumer projects to declare repository-specific rule overrides or exclusions (e.g. excluding code generator modules) without polluting global rules.

3. **`io.github.mudrichenkoevgeny.shared-foundation-kotlin`** (`KotlinConventionPlugin`):
   - **Description:** Configures Kotlin JVM compiler options, `explicitApi()`, `jvmToolchain`, `jvmTarget`, and `allWarningsAsErrors`.
   - **Tests:** Adds `-XX:+EnableDynamicAgentLoading` to `Test` tasks.

4. **`io.github.mudrichenkoevgeny.shared-foundation-publish`** (`PublishConventionPlugin`):
   - **Description:** Configures Vanniktech Maven Publishing and Dokka with standardized POM metadata, license info, developer records, and artifactId derivation.

## Usage in Consumer Projects

Apply any of the convention plugins in your consumer project's `build.gradle.kts`:

```kotlin
// build.gradle.kts
plugins {
    id("io.github.mudrichenkoevgeny.shared-foundation-agent-sync") version "<version>"
    id("io.github.mudrichenkoevgeny.shared-foundation-quality") version "<version>"
    id("io.github.mudrichenkoevgeny.shared-foundation-kotlin") version "<version>"
    id("io.github.mudrichenkoevgeny.shared-foundation-publish") version "<version>"
}
```

Or configure via Version Catalog (`gradle/libs.versions.toml`).
