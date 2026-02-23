# shared-foundation
A Kotlin Multiplatform (KMP) library providing shared business logic and API contracts between Backend and Client applications (Mobile, Web, Desktop).

[![Maven Central](https://img.shields.io/maven-central/v/io.github.mudrichenkoevgeny/shared-foundation-bom)](https://central.sonatype.com/artifact/io.github.mudrichenkoevgeny/shared-foundation-bom)
## Installation
Add the library to your projects using one of the following methods:
### Option 1: Version Catalog
_1. In your gradle/libs.versions.toml file:_
```
[versions]
shared-foundation = "0.0.16"

[libraries]
shared-foundation-bom = { group = "io.github.mudrichenkoevgeny", name = "shared-foundation-bom", version.ref = "shared-foundation" }
shared-foundation-core-common = { group = "io.github.mudrichenkoevgeny", name = "shared-foundation-core-common" }
shared-foundation-core-security = { group = "io.github.mudrichenkoevgeny", name = "shared-foundation-core-security" }
shared-foundation-core-settings = { group = "io.github.mudrichenkoevgeny", name = "shared-foundation-core-settings" }
shared-foundation-feature-user = { group = "io.github.mudrichenkoevgeny", name = "shared-foundation-feature-user" }

# Or if you don`t wan`t to use bom:
shared-foundation-core-common = { group = "io.github.mudrichenkoevgeny", name = "shared-foundation-core-common", version.ref = "shared-foundation" }
shared-foundation-core-security = { group = "io.github.mudrichenkoevgeny", name = "shared-foundation-core-security", version.ref = "shared-foundation" }
shared-foundation-core-settings = { group = "io.github.mudrichenkoevgeny", name = "shared-foundation-core-settings", version.ref = "shared-foundation" }
shared-foundation-feature-user = { group = "io.github.mudrichenkoevgeny", name = "shared-foundation-feature-user", version.ref = "shared-foundation" }
```
In your build.gradle.kts:
```
dependencies {
    implementation(platform(libs.shared.foundation.bom))
    implementation(libs.shared.foundation.core.common)
    implementation(libs.shared.foundation.core.security)
    implementation(libs.shared.foundation.core.settings)
    implementation(libs.shared.foundation.feature.user)
    
    // Or if you don`t wan`t to use bom:
    implementation(libs.shared.foundation.core.common)
    implementation(libs.shared.foundation.core.security)
    implementation(libs.shared.foundation.core.settings)
    implementation(libs.shared.foundation.feature.user)
}
```
### Option 2: Direct Dependency
```
implementation(platform(io.github.mudrichenkoevgeny:shared-foundation-bom:0.0.16))
implementation("io.github.mudrichenkoevgeny:shared-foundation-core-common")
implementation("io.github.mudrichenkoevgeny:shared-foundation-core-security")
implementation("io.github.mudrichenkoevgeny:shared-foundation-core-settings")
implementation("io.github.mudrichenkoevgeny:shared-foundation-feature-user")

// Or if you don`t wan`t to use bom:
implementation("io.github.mudrichenkoevgeny:shared-foundation-core-common:0.0.16")
implementation("io.github.mudrichenkoevgeny:shared-foundation-core-security:0.0.16")
implementation("io.github.mudrichenkoevgeny:shared-foundation-core-settings:0.0.16")
implementation("io.github.mudrichenkoevgeny:shared-foundation-feature-user:0.0.16")
```

## Samples & Usage

Check out these projects to see the library in action:

* **[Backend Platform SDK](https://github.com/mudrichenkoevgeny/backend-platform-sdk)** — A server-side library that uses this library for server-side development.
* **[KMP Platform SDK](https://github.com/mudrichenkoevgeny/kmp-platform-sdk)** — A Kotlin Multiplatform library that uses this library for client-side development.
