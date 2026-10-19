# shared-foundation-testing

Comprehensive testing infrastructure, mock factories, test fixtures, deterministic constants, screen parameter builders, and in-memory fakes for `shared-foundation`.

`shared-foundation-testing` allows consumer applications and SDKs to write clean unit tests, integration tests, and UI preview harnesses using standardized test fixtures instead of writing custom mocks.

---

## Capabilities & Key Features

1. **1-File-Per-Class Mirrored Fixtures:**
   Every domain model, network payload, network request, navigation parameter, and value class has a dedicated `<OriginalClassName>Fixture.kt` file in the exact mirrored package under `io.github.mudrichenkoevgeny.shared.foundation.testing.*`.
2. **Deterministic Builders (`createTest*`):**
   Factory functions provide safe default values for all model properties while allowing parameter overrides in tests.
3. **Value Class Generators & Constants:**
   Domain value classes expose both top-level string constants (`TEST_*`) and constructor factories (`createTest*()`).
4. **Navigation Parameter Fixtures:**
   All screen route parameter classes have corresponding test builders (`createTest*Params()`) for testing router logic, deep link parsing, and screen state.
5. **In-Memory Fakes:**
   - **`FakeLocalizedStringResolver`** — Predictable localized string resolution.
   - **`FakeLocalizationBundleProvider`** — In-memory bundle provider for testing localization fallbacks.
   - **`FakePasswordPolicyValidator`** — Configurable password policy validator.

---

## Installation & Consumption

### Gradle (Kotlin Multiplatform / JVM / Android)

Add `:shared:foundation:testing` as a test dependency:

```kotlin
// build.gradle.kts
kotlin {
    sourceSets {
        commonTest.dependencies {
            implementation(project(":shared:foundation:testing"))
            // or via Published Artifact:
            // implementation("io.github.mudrichenkoevgeny:shared-foundation-testing")
        }
    }
}
```

### TypeScript / Web (npm)

The npm package `@mudrichenkoevgeny/shared-foundation` exports equivalent JavaScript/TypeScript testing constants and payload factory functions:

```typescript
import {
  TEST_USER_ID,
  TEST_EMAIL,
  createTestUserPrivatePayload,
  createTestAuditEventPrivatePayload
} from '@mudrichenkoevgeny/shared-foundation';

const userPayload = createTestUserPrivatePayload({
  role: 'admin'
});
```

---

## Usage Examples

### 1. Creating Domain Models & Payloads

```kotlin
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.user.createTestUserPrivate
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.model.user.createTestUserPrivatePayload

// Creates a valid UserPrivate domain model with default test data
val user = createTestUserPrivate(
    authorityLevel = 100
)

// Creates a valid UserPrivatePayload DTO with wire keys
val payload = createTestUserPrivatePayload(
    role = "admin"
)
```

### 2. Using Navigation Parameter Fixtures

```kotlin
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.navigation.screen.base.auth.login.email.createTestLoginByEmailParams

val params = createTestLoginByEmailParams(
    email = "john.doe@example.com"
)
```

### 3. Using Fakes

```kotlin
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.security.passwordpolicy.validator.FakePasswordPolicyValidator

// Pass a fake validator that succeeds by default
val validator = FakePasswordPolicyValidator()

// Or configure it to return specific failure reasons
val failingValidator = FakePasswordPolicyValidator(
    failReasons = listOf(PasswordPolicyFailReason.TOO_SHORT)
)
```

---

## Architecture & Package Structure

`shared-foundation-testing` strictly mirrors the package layout of production modules under the `io.github.mudrichenkoevgeny.shared.foundation.testing` prefix:

```
io.github.mudrichenkoevgeny.shared.foundation.testing
├── core
│   ├── audit
│   │   ├── domain.model.event (AuditEventPrivateFixture, AuditEventIdFixture, ...)
│   │   └── navigation.screen... (ManagementAuditEventListParamsFixture, ...)
│   ├── common
│   │   ├── domain.model.client (ClientInfoFixture, ClientDeviceIdFixture, ...)
│   │   ├── error.model (ApiErrorResponseFixture)
│   │   └── localization (FakeLocalizedStringResolver, FakeLocalizationBundleProvider)
│   └── security
│       ├── domain.model.passwordpolicy (ManagementPasswordPolicyFixture, ...)
│       └── passwordpolicy.validator (FakePasswordPolicyValidator)
└── feature
    └── user
        ├── domain.model.user (UserPrivateFixture, UserIdFixture, ...)
        ├── domain.model.session (UserSessionPrivateFixture, UserSessionIdFixture, ...)
        ├── network.model.user (UserPrivatePayloadFixture, ...)
        └── navigation.screen... (LoginByEmailParamsFixture, ManagementUserListParamsFixture, ...)
```
