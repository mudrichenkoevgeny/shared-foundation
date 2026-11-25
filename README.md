# shared-foundation

A shared contracts library providing REST/WS API contracts, JSON DTOs, Zod schemas, WebSocket envelopes, error codes, permissions, and domain models for Backend (JVM), Web (TypeScript/NPM), Mobile, and Desktop applications.

[![Maven Central](https://img.shields.io/maven-central/v/io.github.mudrichenkoevgeny/shared-foundation-bom)](https://central.sonatype.com/artifact/io.github.mudrichenkoevgeny/shared-foundation-bom)
[![npm](https://img.shields.io/npm/v/@mudrichenkoevgeny/shared-foundation)](https://www.npmjs.com/package/@mudrichenkoevgeny/shared-foundation)

## Supported Targets

- **JVM** (Server / Desktop)
- **Android**
- **iOS** (`iosArm64`, `iosSimulatorArm64`)
- **WasmJS / Node.js** (Browser & Web via npm)

## Installation

### Kotlin (JVM / Android / iOS / Wasm)

Use the BOM and add only the modules you need:

```kotlin
// build.gradle.kts
dependencies {
    implementation(platform("io.github.mudrichenkoevgeny:shared-foundation-bom:<version>"))
    implementation("io.github.mudrichenkoevgeny:shared-foundation-core")
    implementation("io.github.mudrichenkoevgeny:shared-foundation-feature-user")

    // For unit, integration, and UI preview tests:
    testImplementation("io.github.mudrichenkoevgeny:shared-foundation-testing")

    // For enforcing shared architectural Detekt lint rules (NoFullyQualifiedName, NoInlineComment, PreferValueOverName, ProhibitNonSafeAssertion, ProhibitPrintln):
    detektPlugins("io.github.mudrichenkoevgeny:shared-foundation-detekt-rules")
}
```

With a version catalog: declare BOM and module aliases in `gradle/libs.versions.toml`, then use `implementation(platform(libs.shared.foundation.bom))` and `testImplementation(libs.shared.foundation.fixtures)`.

### Gradle Convention Plugins

Consumer projects (`backend-platform-sdk`, `kmp-platform-sdk`) can also apply shared convention plugins to automatically configure Ktlint, Detekt with custom rules, compiler options, publishing, and agent rules synchronization:

```kotlin
// build.gradle.kts (Consumer Projects)
plugins {
    id("io.github.mudrichenkoevgeny.shared-foundation-agent-sync") version "<version>"
    id("io.github.mudrichenkoevgeny.shared-foundation-quality") version "<version>"
    id("io.github.mudrichenkoevgeny.shared-foundation-kotlin") version "<version>"
    id("io.github.mudrichenkoevgeny.shared-foundation-publish") version "<version>"
    id("io.github.mudrichenkoevgeny.shared-foundation-localization") version "<version>"
}
```

### Web / TypeScript (NPM)

For TypeScript and React applications, install the generated Zod schemas, routes, branded types, and test fixtures from npm:

```bash
pnpm add @mudrichenkoevgeny/shared-foundation
```

## Project Skeleton Generator

Scaffold a new, production-ready downstream Kotlin Multiplatform contracts repository on disk:

```bash
./gradlew initProject -Pname=sample -Pgroup=com.sample -PnpmScope=@sample
```

This creates `sample-shared` configured with `shared-foundation` Gradle convention plugins, version catalog, BOM, `core`, `testing`, `tools/npm-generator-runner`, and agent rules.

## Modules

Published artifacts (versions aligned via the BOM):

- **core** — consolidated core module including common shared errors, network contracts (`CommonNetworkRoutes`, `BaseSettingsNetworkRoutes`), WebSocket envelopes, common DTOs, navigation contracts, root navigation, parameter parsers, core localization infrastructure, security policies, MFA, global settings, S3 presigned storage contracts, entity projections, and audit events ([module README](core/README.md)).
- **feature-user** — comprehensive HTTP contracts for authentication, MFA, session management, user identifiers, open/management user configurations, self-service account unlocking, navigation screen routes, screen parameters, parameter parsers, and user localization (`UserModuleStrings`) ([module README](feature/user/README.md)).
- **testing** — standardized test data builders (`createTest*`), screen parameter fixtures, deterministic constants, value class factories, and in-memory mocks for unit, integration, and UI preview tests ([module README](testing/README.md)).
- **tools-design-system** — pure Kotlin design token parser, icon extractors, and code generators (`DesignTokensParser`, `ComposeTokensGenerator`, `WebTokensGenerator`, `PenpotTokensGenerator`, `DesignTokensFontExtractor`, `DesignSystemIconExtractor`) supporting multi-target generation for Jetpack Compose, Web (CSS/TS), and Penpot ([module README](tools/design-system/README.md)).
- **tools-detekt-rules** — custom Detekt static analysis rules (`NoFullyQualifiedName`, `NoInlineComment`, `PreferValueOverName`, `ProhibitNonSafeAssertion`, `ProhibitPrintln`) for enforcing shared library architectural standards across consumer applications ([module README](tools/detekt-rules/README.md)).
- **tools-gradle-plugin** — Gradle Convention Plugins (`shared-foundation-agent-sync`, `shared-foundation-quality`, `shared-foundation-kotlin`, `shared-foundation-publish`, `shared-foundation-localization`, `shared-foundation-design-system`) for auto-configuring quality checks, compiler options, publishing, localization generation, design token generation, and agent rule synchronization across consumer applications ([module README](tools/gradle-plugin/README.md)).
- **tools-npm-generator** — executable JVM generator generating TypeScript declarations, Zod schemas, routes, domain mappers, testing constants/fixtures, and localization classes into `@mudrichenkoevgeny/shared-foundation` ([module README](tools/npm-generator/README.md)).
- **tools-project-generator** — internal JVM generator triggered via `./gradlew initProject` for scaffolding clean downstream contract repositories (`<name>-shared`) on disk ([module README](tools/project-generator/README.md)).
- **bom** — dependency constraints for the modules above.

## Testing Fixtures, Mocks & Architecture Assertions

The library provides ready-to-use test data builders (`createTest*`), deterministic constants, value class factories, screen parameter fixtures, and in-memory mocks.

You can include `:testing` in your `testImplementation` / `commonTest.dependencies` (or import from `@mudrichenkoevgeny/shared-foundation` in npm) to use these pre-built fixtures in unit and integration tests instead of writing custom mocks.

For JVM test suites (`jvmTest`), the module also provides **`FoundationArchitectureAssertions`** — a reusable Konsist-based validation object that downstream repositories can call to enforce architectural rules (e.g. strict DTO boundaries, API route KDoc structures, and `snake_case` JSON localization keys).

> **Note on Gradle Version Catalog (`libs.versions.toml`)**: When defining an alias for the testing module, it is recommended to use the suffix `-fixtures` (e.g. `shared-foundation-fixtures = { ... name = "shared-foundation-testing" }`) instead of `-testing` to prevent naming conflicts with the built-in Gradle `Project.testing` extension in JVM modules. See the [testing README](testing/README.md) for details.

## Documentation & Architecture

- **[ARCHITECTURE.md](docs/architecture/ARCHITECTURE.md)** — Architectural diagrams in Mermaid covering module dependencies, surface separation (`Open` vs `Management`), WebSocket wire contract, error handling taxonomy, navigation, and localization architecture.
- **[AGENTS.md](AGENTS.md)** — Project coding standards, architectural boundaries, KDoc requirements, and operational rules for contributors and agents.
- **[CONTRIBUTING.md](CONTRIBUTING.md)** — Build instructions, dependency health checks (`./gradlew projectHealth`), and Maven Central publishing guidelines.
- **[core README](core/README.md)** — Consolidated core contracts, localization infrastructure (`LocalizedStringResolver`, `LocalizationFormatter`), and platform integration examples.
- **[feature/user README](feature/user/README.md)** — IAM subsystem documentation, network contracts, routing hierarchy, and component index.
- **[testing README](testing/README.md)** — Testing module documentation, mock factories, screen parameter fixtures, value class factories, and architecture assertions (`FoundationArchitectureAssertions`).
- **[tools/code-review README](tools/code-review/README.md)** — Universal cross-platform CLI tool (`@mudrichenkoevgeny/code-review`) for code review packing and prompt management.
- **[tools/design-system README](tools/design-system/README.md)** — Pure Kotlin design token parser, icon extractors, and code generators for Compose Multiplatform, Web (CSS/TS), and Penpot.
- **[tools/detekt-rules README](tools/detekt-rules/README.md)** — Custom Detekt static analysis rules for enforcing shared library architectural standards.
- **[tools/gradle-plugin README](tools/gradle-plugin/README.md)** — Convention plugins (`agent-sync`, `quality`, `publish`, `localization`, `design-system`) and Detekt quality checks.
- **[tools/npm-generator README](tools/npm-generator/README.md)** — TypeScript/Zod contract generator engine, declaration verification, and npm package build pipeline.
- **[tools/npm-generator-runner README](tools/npm-generator-runner/README.md)** — Executable runner for NPM package generation and export auditing.
- **[tools/project-generator README](tools/project-generator/README.md)** — Internal generator for scaffolding downstream contract repositories.

## Samples & Usage

Check out these projects to see the library in action:

* **[Backend Platform SDK](https://github.com/mudrichenkoevgeny/backend-platform-sdk)** — A server-side library that uses this library for server-side development.
* **[KMP Platform SDK](https://github.com/mudrichenkoevgeny/kmp-platform-sdk)** — A Kotlin Multiplatform library that uses this library for client-side development.
* **[Web Platform SDK](https://github.com/mudrichenkoevgeny/web-platform-sdk)** — A TypeScript / React client SDK that uses `@mudrichenkoevgeny/shared-foundation` for Zod schema validation, API routes, and security policies.

## License

This project is licensed under the Apache License 2.0 — see the [LICENSE](LICENSE) file for details.
