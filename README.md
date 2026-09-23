# shared-foundation

A Kotlin Multiplatform (KMP) library providing shared business logic, API contracts, WebSocket envelopes, error codes, and permission models between Backend and Client applications (Mobile, Web, Desktop).

[![Maven Central](https://img.shields.io/maven-central/v/io.github.mudrichenkoevgeny/shared-foundation-bom)](https://central.sonatype.com/artifact/io.github.mudrichenkoevgeny/shared-foundation-bom)

## Supported Targets

- **JVM** (Server / Desktop)
- **Android** (API 24+)
- **iOS** (`iosArm64`, `iosSimulatorArm64`)
- **WasmJS** (Browser)

## Installation

Use the BOM and add only the modules you need:

```kotlin
// build.gradle.kts
dependencies {
    implementation(platform("io.github.mudrichenkoevgeny:shared-foundation-bom:0.0.46"))
    implementation("io.github.mudrichenkoevgeny:shared-foundation-core-common")
    implementation("io.github.mudrichenkoevgeny:shared-foundation-core-security")
    implementation("io.github.mudrichenkoevgeny:shared-foundation-feature-user")
    // ... add other shared-foundation modules as required
}
```

With a version catalog: declare BOM and module aliases in `gradle/libs.versions.toml`, then use `implementation(platform(libs.shared.foundation.bom))` and `implementation(libs.shared.foundation.core.common)` (and other aliases as needed).

## Modules

Published artifacts (versions aligned via the BOM):

- **core-common** — shared errors, network contracts, WebSocket envelopes, common DTOs ([module README](shared/foundation/core/common/README.md)).
- **core-security** — Open/Management password policies, MFA logic, security settings, account lockout policies, validation, HTTP routes, permission codes, and mappers ([module README](shared/foundation/core/security/README.md)).
- **core-settings** — Open/Management global settings, client app version requirements, HTTP routes, permission codes, and mappers ([module README](shared/foundation/core/settings/README.md)).
- **core-audit** — audit events, filters, sort keys, payloads, management HTTP routes, permission codes, mappers ([module README](shared/foundation/core/audit/README.md)).
- **feature-user** — comprehensive HTTP contracts for authentication, MFA, session management, user identifiers (self-service & management), open/management user configurations, and self-service account unlocking ([module README](shared/foundation/feature/user/README.md)).
- **bom** — dependency constraints for the modules above.

## Documentation & Architecture

- **[ARCHITECTURE.md](ARCHITECTURE.md)** — Architectural diagrams in Mermaid covering module dependencies, surface separation (`Open` vs `Management`), WebSocket wire contract, and error handling taxonomy.
- **[CONTRIBUTING.md](CONTRIBUTING.md)** — Build instructions, dependency health checks (`./gradlew projectHealth`), and Maven Central publishing guidelines.
- **[AGENTS.md](AGENTS.md)** — Project coding standards, architectural boundaries, KDoc requirements, and constraints for contributors and AI coding assistants.

## Samples & Usage

Check out these projects to see the library in action:

* **[Backend Platform SDK](https://github.com/mudrichenkoevgeny/backend-platform-sdk)** — A server-side library that uses this library for server-side development.
* **[KMP Platform SDK](https://github.com/mudrichenkoevgeny/kmp-platform-sdk)** — A Kotlin Multiplatform library that uses this library for client-side development.

## License

This project is licensed under the Apache License 2.0 — see the [LICENSE](LICENSE) file for details.
