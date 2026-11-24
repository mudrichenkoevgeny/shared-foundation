# Contributing to shared-foundation

## Branching & Pull Requests

- **`main`**: Stable production branch.
- **`develop`**: Active development branch.
- All pull requests target `main` or `develop` and must pass CI (`ci.yml`): `./gradlew check`, `./gradlew apiCheck`, `./gradlew generateNpmPackage`, and Gitleaks secret scanning.
- All public API declarations require complete English KDoc. Inline implementation comments (`//`, `/* ... */`) and fully qualified names in executable code are prohibited.

---

## Verification & Quality Checks

Run relevant checks before committing:

| Task | Command |
|---|---|
| Format check | `./gradlew ktlintCheck` |
| Format auto-fix | `./gradlew ktlintFormat` |
| Static analysis & custom Detekt rules | `./gradlew detekt` |
| Binary compatibility check | `./gradlew apiCheck` |
| Binary compatibility dump update | `./gradlew apiDump` |
| Full verification suite | `./gradlew check` |
| NPM package generation & parity audit | `./gradlew generateNpmPackage` |
| Dependency health audit (project-wide) | `./gradlew buildHealth` |
| Dependency health audit (single module) | `./gradlew :<module>:projectHealth` |
| Project scaffolding E2E check | `./gradlew verifyProjectGenerator` |

---

## Local Testing in Consumer Projects

### 1. Local Maven (`mavenLocal`)

Because `tools/gradle-plugin` is an included build, publish both library artifacts and convention plugins to `~/.m2/repository/`:

```bash
./gradlew publishToMavenLocal :gradle-plugin:publishToMavenLocal
```

Add `mavenLocal()` to `pluginManagement` and `dependencyResolutionManagement` in the consumer project's `settings.gradle.kts`.

### 2. Local NPM Package (`npm pack`)

1. Build the package into `build/npm-package/`:
   ```bash
   ./gradlew generateNpmPackage
   ```
2. Pack a `.tgz` archive inside `build/npm-package/`:
   ```bash
   cd build/npm-package && npm pack
   ```
   *(Or from repository root using `--pack-destination`):*
   ```bash
   npm pack ./build/npm-package --pack-destination ./build/npm-package
   ```
3. Point the consumer `pnpm-workspace.yaml` catalog to the generated tarball in `build/npm-package/` and run `pnpm install`:
   ```yaml
   catalog:
     '@mudrichenkoevgeny/shared-foundation': 'file:/path/to/shared-foundation/build/npm-package/mudrichenkoevgeny-shared-foundation-<version>.tgz'
   ```

---

## Standalone Tooling (`tools/code-review`)

`@mudrichenkoevgeny/code-review` is a zero-dependency Node.js CLI located in `tools/code-review/`.

- **Run unit tests:**
  ```bash
  node --test tools/code-review/test/code-review.test.mjs
  ```
- **Publish manually to npmjs.com** (from `tools/code-review/`):
  ```bash
  npm publish --access public
  ```

---

## Releases

Releases are automated via `.github/workflows/release.yml` on pushing a `v*` tag. The workflow publishes:

- **Maven Central (GPG-signed):** `shared-foundation-gradle-plugin`, `shared-foundation-bom`, `shared-foundation-core-*`, `shared-foundation-feature-user`, `shared-foundation-testing`, `shared-foundation-design-tokens`, `shared-foundation-npm-generator`.
- **npmjs.com:** `@mudrichenkoevgeny/shared-foundation` (with provenance).
- **GitHub Pages:** Dokka HTML API documentation (`gh-pages`).
- **GitHub Release:** Release entry with generated release notes.
