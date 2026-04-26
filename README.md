# shared-foundation
A Kotlin Multiplatform (KMP) library providing shared business logic and API contracts between Backend and Client applications (Mobile, Web, Desktop).

[![Maven Central](https://img.shields.io/maven-central/v/io.github.mudrichenkoevgeny/shared-foundation-bom)](https://central.sonatype.com/artifact/io.github.mudrichenkoevgeny/shared-foundation-bom)
## Installation
Use the BOM and add only the modules you need:

```kotlin
// build.gradle.kts
dependencies {
    implementation(platform("io.github.mudrichenkoevgeny:shared-foundation-bom:0.0.35"))
    implementation("io.github.mudrichenkoevgeny:shared-foundation-core-common")
    implementation("io.github.mudrichenkoevgeny:shared-foundation-core-security")
    implementation("io.github.mudrichenkoevgeny:shared-foundation-feature-user")
    // ... add other shared-foundation modules as required
}
```

With a version catalog: declare BOM and module aliases in `gradle/libs.versions.toml`, then use `implementation(platform(libs.shared.foundation.bom))` and `implementation(libs.shared.foundation.core.common)` (and other aliases as needed).

## Modules

Published artifacts (versions aligned via the BOM):

- **shared-foundation-core-common** — shared errors, network contracts, WebSocket envelopes, common DTOs.
- **shared-foundation-core-security** — password policy, security settings payloads/domain, validation, mappers, WebSocket type strings ([module README](shared/foundation/core/security/README.md)).
- **shared-foundation-core-settings** — global settings payloads/domain, field names, WebSocket type strings ([module README](shared/foundation/core/settings/README.md)).
- **shared-foundation-core-audit** — audit events, filters, sort keys, payloads, mappers ([module README](shared/foundation/core/audit/README.md)).
- **shared-foundation-feature-security-api** — security settings HTTP routes + permission code ([module README](shared/foundation/feature/security/api/README.md)).
- **shared-foundation-feature-settings-api** — global settings HTTP routes + permission code ([module README](shared/foundation/feature/settings/api/README.md)).
- **shared-foundation-feature-audit-api** — management audit HTTP routes + permission codes ([module README](shared/foundation/feature/audit/api/README.md)).
- **shared-foundation-feature-user** — user, auth, and session API contracts.
- **shared-foundation-bom** — dependency constraints for the modules above.

## Samples & Usage

Check out these projects to see the library in action:

* **[Backend Platform SDK](https://github.com/mudrichenkoevgeny/backend-platform-sdk)** — A server-side library that uses this library for server-side development.
* **[KMP Platform SDK](https://github.com/mudrichenkoevgeny/kmp-platform-sdk)** — A Kotlin Multiplatform library that uses this library for client-side development.
