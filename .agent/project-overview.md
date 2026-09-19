---
description: Project identity, module boundaries, and consumer SDK relations
alwaysApply: true
---

# shared-foundation — Overview

## Project identity
- **Type:** Kotlin Multiplatform **library** of **shared API contracts** (same serialized JSON shapes, path strings, permissions, and machine-readable error **code** strings).
- **Publishing:** Maven Central via `com.vanniktech.maven.publish`; version in root `build.gradle.kts`.
- **Coordinates:** group `io.github.mudrichenkoevgeny`; artifact ids follow `shared-foundation-*`.
- **Consumers:** Not in this repo. Consumed by **[Backend Platform SDK](https://github.com/mudrichenkoevgeny/backend-platform-sdk)** (server) and **[KMP Platform SDK](https://github.com/mudrichenkoevgeny/kmp-platform-sdk)** (client).

## Tech stack
- **Kotlin** 2.x, **kotlinx.serialization** (`kotlinx-serialization-json`).
- **JSON configuration:** `FoundationJson` in `core/common` (standardized `snake_case`, `ignoreUnknownKeys`, `encodeDefaults`).
- **Targets:** JVM, Android Library, iOS, Wasm JS.
- **Strictly No UI:** This repository contains only logic, types, and constants.

## Modules Structure (`shared/foundation/`)

### Core Modules (`core/`)
Focus on domain logic, data models, and transport payloads.

- **`core/common`:** The foundation. Contains `ApiErrorResponse`, `FoundationJson`, `SocketFrame` (WebSocket envelopes), `ClientInfo`, and `PagedResult`. Defines base `PermissionCode`.
- **`core/audit`:** Audit domain logic and HTTP routes. `AuditEvent`, `AuditActorType`, `AuditValueSensitivity` (PII redaction policies), mappers, `ManagementAuditRoutes`, `AuditPermissionCode`, and `CommonAuditResourceType`.
- **`core/security`:** Security primitives and HTTP routes. `OpenSecuritySettingsRoutes`, `ManagementSecuritySettingsRoutes`, `SecurityPermissionCode`, MFA/TOTP lifecycle (`TotpSetup`, `VerifyTotp`), `ManagementPasswordPolicy` with `PasswordPolicyValidator`, `AccountLockoutPolicy`, `AccountLockoutType`, `IpRestrictionPolicy`, and cryptographic value objects (`EncryptedString`, `PasswordHash`).
- **`core/settings`:** Global system settings shapes and HTTP routes. `OpenGlobalSettingsRoutes`, `ManagementGlobalSettingsRoutes`, `SettingsPermissionCode`, `OpenGlobalSettings` and `ManagementGlobalSettings` domain models, client app version constraints, and `SettingsWebSocketEventTypes`.

### Feature Modules (`feature/`)
Focus on feature-specific contracts and workflows.

- **`feature/user`:** Comprehensive feature module for Auth, Sessions, Identifiers, and Configuration. Includes `EmailRestrictionPolicy`, Self-Service Account Unlock routes (`OpenUnlockRoutes`, `SelfManagementUnlockRoutes`), separate session limits (`maxActiveSessionsForOpenUser` / `maxActiveSessionsForManagementUser`), and aggregates `core/security` for MFA/Lockout flows and `core/audit` for user-specific audit taxonomy (`UserAuditActionType`).

### Utility
- **`bom`:** Bill of Materials to align versions across all `shared-foundation-*` artifacts.

## Boundaries & Dependencies
- **`core/common`** is the leaf: It must not depend on any other internal modules.
- **Core-to-Core:** `core/audit` and `core/security` depend on `core/common`; `core/security` and `core/settings` depend on `core/audit`.
- **`feature/user`** is a high-level aggregator: It depends on `core/common`, `core/audit`, `core/security`, and `core/settings`.
- **No Frameworks:** No Ktor Server/Client engines or database drivers. Only "pure" Kotlin logic and serialization.

## Guidelines for AI
- All rules and standards are now located in the `.agent/` directory.
- Refer to `AGENTS.md` for the full index of available standards.
- Always check the specific module's `README.md` for the latest component links and usage examples.

---
*Refer to `AGENTS.md` for the full list of project standards.*
