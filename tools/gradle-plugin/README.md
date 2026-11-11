# tools/gradle-plugin

**Gradle Convention Plugins for shared-foundation** providing build tasks, quality checks, compiler conventions, publishing setup, multi-layer `.editorconfig` synchronization, and AI agent rules synchronization across Kotlin/JVM/KMP consumer applications (such as `backend-platform-sdk` and `kmp-platform-sdk`).

Published Maven artifact: **`shared-foundation-gradle-plugin`**.

## Included Convention Plugins

1. **`io.github.mudrichenkoevgeny.shared-foundation-agent-sync`** (`AgentSyncPlugin`):
   - **Task:** `syncAgentRules`.
   - **Description:** Extracts shared global AI agent rules (`common/`, `kotlin/`, and optionally `shared/`) directly from plugin resources into `.agent/global/`.
   - **Multi-Layer Agent Rules Registration:** Supports registering additional AI agent rule layers from downstream SDK convention plugins via `AgentSyncUtils.registerAgentRulesLayer(...)` into dedicated target subdirectories under `.agent/global/`.
   - **IDE Integration:** Automatically hooks into IDE import (`prepareKotlinBuildScriptModel` and `prepareKotlinIdeaImport`).

2. **`io.github.mudrichenkoevgeny.shared-foundation-quality`** (`QualityPlugin`):
   - **Description:** Applies and configures Ktlint, Detekt, and `.editorconfig` synchronization across project modules.
   - **Multi-Layer `.editorconfig` Synchronization (`syncEditorConfig`):**
     - Automatically registers or configures the single root project task `syncEditorConfig` of type `EditorConfigSyncTask`.
     - Supports multi-layer plugin inheritance (`QualityUtils.registerEditorConfigLayer(...)`) across convention plugins (e.g. `shared-foundation` -> `backend-platform-sdk` -> consumer projects).
     - **`.editorconfig` Local Overrides Marker:** Uses the `# --- LOCAL OVERRIDES ---` marker in `.editorconfig`:
       - **Layered Base Standards (above marker):** Centralized formatting rules from registered resource layers (e.g. `config/editorconfig/editorconfig-base.txt`) are merged cleanly with double line breaks.
       - **Local Overrides (below marker):** Repository-specific rules (e.g. `.sql`, `.env`, `.properties`, Dockerfiles, or module exclusions) are strictly preserved and never overwritten.
       - **Timestamp Optimization:** Normalizes LF line endings and only writes to `.editorconfig` on disk if content actually changed, avoiding unnecessary IDE file reload triggers.
     - **IDE & Lint Integration:** Automatically wired to `prepareKotlinBuildScriptModel`, `prepareKotlinIdeaImport`, and all Ktlint tasks.
   - **Multi-Tier Detekt Configuration:**
     - **Tier 1 (Base Rules):** Extracts clean, project-agnostic `detekt-base.yml` directly from plugin resources (`build/generated/detekt/detekt-base.yml`).
     - **Tier 2 (Local Overrides):** Merges optional `$rootDir/config/detekt/detekt.yml` on top of the base configuration via `detekt.config.setFrom(localConfigFile, extractedFileProvider, existingConfigs)`, guaranteeing that local project-level overrides take highest precedence in Detekt's `CompositeConfig`.
     - **Tool Exclusions:** Disables Detekt tasks completely (`detektTask.enabled = !isIgnoredTool`) for executable code generator modules like `:tools:npm-generator`.

3. **`io.github.mudrichenkoevgeny.shared-foundation-kotlin`** (`KotlinConventionPlugin`):
   - **Description:** Configures Kotlin JVM compiler options, `explicitApi()`, `jvmToolchain`, `jvmTarget`, and `allWarningsAsErrors`.
   - **Tests:** Adds `-XX:+EnableDynamicAgentLoading` to `Test` tasks.

4. **`io.github.mudrichenkoevgeny.shared-foundation-publish`** (`PublishConventionPlugin`):
   - **Description:** Configures Vanniktech Maven Publishing and Dokka with standardized POM metadata, license info, developer records, and artifactId derivation.

## Quality & Synchronization Utilities

- **`AgentSyncUtils`**:
  - `registerAgentRulesLayer(target, classLoader, resourcePath, targetSubdir)` — ensures `AgentSyncPlugin` is applied to root project and registers extra AI agent rule resource layers targeting subdirectories under `.agent/global/`.
- **`QualityUtils`**:
  - `registerEditorConfigLayer(target, classLoader, resourcePath)` — registers or appends resource layers to the root `syncEditorConfig` task.
  - `registerDetektConfigExtraction(target, taskName, classLoader, resourcePath, outputFileName)` — extracts YAML configs and prepends local `$rootDir/config/detekt/detekt.yml` to `detekt.config`.
  - `applyDetektRulesDependency(...)` — wires `:tools:detekt-rules` subproject or published Maven rule coordinates to `detektPlugins`.
  - `resolvePluginVersion(...)` — resolves plugin version from resource file or package metadata.
- **`ResourceUtils`**:
  - `copyResourceFolder(classLoader, resourcePath, targetFolder, logger)` — copies resource folders from JARs or file system safely after verifying resource existence and checking `canonicalFile` boundaries.

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
