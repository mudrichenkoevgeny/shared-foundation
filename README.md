# shared-foundation

A shared contracts library providing REST/WS API contracts, JSON DTOs, Zod schemas, WebSocket envelopes, error codes, permissions, and domain models for Backend (JVM), Web (TypeScript/NPM), Mobile, and Desktop applications.

[![Maven Central](https://img.shields.io/maven-central/v/io.github.mudrichenkoevgeny/shared-foundation-bom)](https://central.sonatype.com/artifact/io.github.mudrichenkoevgeny/shared-foundation-bom)
[![npm](https://img.shields.io/npm/v/@mudrichenkoevgeny/shared-foundation)](https://www.npmjs.com/package/@mudrichenkoevgeny/shared-foundation)

## Supported Targets

- **JVM** (Server / Desktop)
- **Android** (API 24+)
- **iOS** (`iosArm64`, `iosSimulatorArm64`)
- **WasmJS / Node.js** (Browser & Web via npm)

## Installation

### Kotlin (JVM / Android / iOS / Wasm)

Use the BOM and add only the modules you need:

```kotlin
// build.gradle.kts
dependencies {
    implementation(platform("io.github.mudrichenkoevgeny:shared-foundation-bom:0.0.79"))
    implementation("io.github.mudrichenkoevgeny:shared-foundation-core-common")
    implementation("io.github.mudrichenkoevgeny:shared-foundation-core-security")
    implementation("io.github.mudrichenkoevgeny:shared-foundation-feature-user")
    
    // For unit, integration, and UI preview tests:
    testImplementation("io.github.mudrichenkoevgeny:shared-foundation-testing")
}
```

With a version catalog: declare BOM and module aliases in `gradle/libs.versions.toml`, then use `implementation(platform(libs.shared.foundation.bom))` and `testImplementation(libs.shared.foundation.fixtures)`.

### Web / TypeScript (NPM)

For TypeScript and React applications, install the generated Zod schemas, routes, branded types, and test fixtures from npm:

```bash
pnpm add @mudrichenkoevgeny/shared-foundation
```

## Modules

Published artifacts (versions aligned via the BOM):

- **core-common** — shared errors, network contracts (`BaseManagementRoutes`, `BaseSettingsRoutes`), WebSocket envelopes, common DTOs, navigation contracts, root navigation (`ClientMainNavigation`, `ManagementMainNavigation`), parameter parsers, and core localization infrastructure (`LocalizedStringResolver`, `LocalizationFormatter`, `CommonModuleStrings`) ([module README](shared/foundation/core/common/README.md)).
- **core-security** — Open/Management password policies, MFA logic, security settings, account lockout policies, HTTP routes, permission codes, mappers, navigation (`ManagementSecurityNavigation`), and security localization (`SecurityModuleStrings`) ([module README](shared/foundation/core/security/README.md)).
- **core-settings** — Open/Management global settings, client app version requirements, HTTP routes, permission codes, mappers, and settings navigation (`ManagementSettingsNavigation`) ([module README](shared/foundation/core/settings/README.md)).
- **core-audit** — audit events, filters, sort keys, payloads, management HTTP routes, permission codes, mappers, and audit navigation (`ManagementAuditNavigation`) ([module README](shared/foundation/core/audit/README.md)).
- **feature-user** — comprehensive HTTP contracts for authentication, MFA, session management, user identifiers, open/management user configurations, self-service account unlocking, navigation screen routes, screen parameters, parameter parsers, and user localization (`UserModuleStrings`) ([module README](shared/foundation/feature/user/README.md)).
- **testing** — standardized test data builders (`createTest*`), screen parameter fixtures, deterministic constants, value class factories, and in-memory fakes for unit, integration, and UI preview tests ([module README](shared/foundation/testing/README.md)).
- **tools-npm-generator** — executable JVM generator generating TypeScript declarations, Zod schemas, routes, domain mappers, testing constants/fixtures, and localization classes into `@mudrichenkoevgeny/shared-foundation` ([module README](tools/npm-generator/README.md)).
- **bom** — dependency constraints for the modules above.

## Testing Fixtures & Fakes

The library provides ready-to-use test data builders (`createTest*`), deterministic constants, value class factories, screen parameter fixtures, and in-memory fakes.

You can include `:shared:foundation:testing` in your `testImplementation` / `commonTest.dependencies` (or import from `@mudrichenkoevgeny/shared-foundation` in npm) to use these pre-built fixtures in unit and integration tests instead of writing custom mocks.

> **Note on Gradle Version Catalog (`libs.versions.toml`)**: When defining an alias for the testing module, it is recommended to use the suffix `-fixtures` (e.g. `shared-foundation-fixtures = { ... name = "shared-foundation-testing" }`) instead of `-testing` to prevent naming conflicts with the built-in Gradle `Project.testing` extension in JVM modules. See the [testing README](shared/foundation/testing/README.md) for details.

## Documentation & Architecture

- **[ARCHITECTURE.md](ARCHITECTURE.md)** — Architectural diagrams in Mermaid covering module dependencies, surface separation (`Open` vs `Management`), WebSocket wire contract, error handling taxonomy, navigation, and localization architecture.
- **[core/common README](shared/foundation/core/common/README.md)** — Core localization infrastructure (`LocalizedStringResolver`, `LocalizationFormatter`) and platform integration examples (Ktor/JVM, Android, iOS, Web).
- **[testing README](shared/foundation/testing/README.md)** — Testing module documentation, mock factories, screen parameter fixtures, value class factories, and fakes.
- **[CONTRIBUTING.md](CONTRIBUTING.md)** — Build instructions, dependency health checks (`./gradlew projectHealth`), and Maven Central publishing guidelines.
- **[AGENTS.md](AGENTS.md)** — Project coding standards, architectural boundaries, KDoc requirements, and constraints for contributors and AI coding assistants.

## Samples & Usage

Check out these projects to see the library in action:

* **[Backend Platform SDK](https://github.com/mudrichenkoevgeny/backend-platform-sdk)** — A server-side library that uses this library for server-side development.
* **[KMP Platform SDK](https://github.com/mudrichenkoevgeny/kmp-platform-sdk)** — A Kotlin Multiplatform library that uses this library for client-side development.
* **[Web Platform SDK](https://github.com/mudrichenkoevgeny/web-platform-sdk)** — A TypeScript / React client SDK that uses `@mudrichenkoevgeny/shared-foundation` for Zod schema validation, API routes, and security policies.

## License

This project is licensed under the Apache License 2.0 — see the [LICENSE](LICENSE) file for details.
