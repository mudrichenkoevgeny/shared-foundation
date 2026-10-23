---
description: Project identity, module boundaries, and consumer SDK relations
alwaysApply: true
---

# shared-foundation — Overview

## Project identity
- **Type:** Shared contracts **library** of **wire payloads, REST/WS API routes, Zod schemas, permissions, and error codes** (same serialized JSON shapes, path strings, permissions, and machine-readable error **code** strings).
- **Publishing:** Maven Central via `com.vanniktech.maven.publish` for Kotlin/JVM/Android/iOS/Wasm; npmjs.com (`@mudrichenkoevgeny/shared-foundation`) via `./gradlew generateNpmPackage` for Web/TypeScript. The NPM package exports constants, route maps, Zod schemas, fully-typed domain models and runtime mappers.
- **Coordinates:** group `io.github.mudrichenkoevgeny`; artifact ids follow `shared-foundation-*`; npm package `@mudrichenkoevgeny/shared-foundation`.
- **Consumers:** Not in this repo. Consumed by **[Backend Platform SDK](https://github.com/mudrichenkoevgeny/backend-platform-sdk)** (server), **[KMP Platform SDK](https://github.com/mudrichenkoevgeny/kmp-platform-sdk)** (client), and **[Web Platform SDK](https://github.com/mudrichenkoevgeny/web-platform-sdk)** (web).

## Tech stack
- **Kotlin** 2.x, **kotlinx.serialization** (`kotlinx-serialization-json`).
- **JSON configuration:** `FoundationJson` in `core/common` (standardized `snake_case`, `ignoreUnknownKeys`, `encodeDefaults`).
- **Targets:** JVM, Android Library, iOS, Wasm JS.
- **Strictly No UI:** This repository contains only logic, types, and constants.

## Modules Structure (`shared/foundation/`)

### Core Modules (`core/`)
Focus on domain logic, data models, navigation structures, and transport payloads.

- **`core/common`:** The foundation. Contains `ApiErrorResponse`, `FoundationJson`, `SocketFrame` (WebSocket envelopes), `ClientInfo`, and `PagedResult`. Defines base `PermissionCode`, base navigation interfaces (`NavigationRoute`, `ParameterizedNavigationRoute`, `RedirectNavigationRoute`), common navigation routes (`CommonNavigationRoutes`), parameter normalizers (`QueryParamNormalizer`, `PhoneParamNormalizer`), parameter parsers (`NavigationScreenParamsParser`), base network routes (`CommonNetworkRoutes`, `BaseSettingsNetworkRoutes`), and root client/management navigation (`ClientMainNavigation`, `ManagementMainNavigation`).
- **`core/audit`:** Audit domain logic, HTTP routes, and navigation. `AuditEvent`, `AuditActorType`, `AuditValueSensitivity` (PII redaction policies), mappers, `ManagementAuditRoutes`, `ManagementAuditNavigation`, screen params (`ManagementAuditEventListParams`, `ManagementAuditEventDetailParams`), `AuditPermissionCode`, and `CommonAuditResourceType`.
- **`core/security`:** Security primitives, HTTP routes, and navigation. `OpenSecuritySettingsRoutes`, `ManagementSecuritySettingsRoutes`, `ManagementSecurityNavigation`, `SecurityPermissionCode`, MFA/TOTP lifecycle (`TotpSetup`, `VerifyTotp`), `ManagementPasswordPolicy` with `PasswordPolicyValidator`, `AccountLockoutPolicy`, `AccountLockoutType`, `IpRestrictionPolicy`, and cryptographic value objects (`EncryptedString`, `PasswordHash`).
- **`core/settings`:** Global system settings shapes, HTTP routes, and navigation. `OpenGlobalSettingsRoutes`, `ManagementGlobalSettingsRoutes`, `ManagementSettingsNavigation`, `SettingsPermissionCode`, `OpenGlobalSettings` and `ManagementGlobalSettings` domain models, client app version constraints, and `SettingsWebSocketEventTypes`.
- **`core/storage`:** Direct-to-S3 presigned upload contracts, HTTP routes, and navigation. `OpenStorageRoutes`, `ManagementStorageRoutes`, `OpenStorageSettingsRoutes`, `ManagementStorageSettingsRoutes`, `OpenStorageNavigation`, `ManagementStorageNavigation`, `ManagementStorageSettingsNavigation`, `StoragePermissionCode`, `FileId`, `StorageCategory`, `StorageFileStatus`, `StorageFileVisibility`, projections (`StorageFileSummary`, `StorageFilePrivate`, `StorageFileInternal`), storage settings (`ManagementStorageSettings`, `OpenStorageSettings`), `StorageErrorCodes`, and `StorageErrorArgs`.

### Feature Modules (`feature/`)
Focus on feature-specific contracts, navigation, and workflows.

- **`feature/user`:** Comprehensive feature module for Auth, Sessions, Identifiers, and Configuration. Includes `EmailRestrictionPolicy`, Self-Service Account Unlock routes (`OpenUnlockRoutes`, `SelfManagementUnlockRoutes`), separate session limits (`maxActiveSessionsForOpenUser` / `maxActiveSessionsForManagementUser`), user navigation contracts (`BaseUserNavigationRoutes`, `UserNavigationPaths`), navigation screen routes (`ClientAuthNavigation`, `ClientUserNavigation`, `ManagementAuthNavigation`, `ManagementAuthSettingsNavigation`, `ManagementSelfUserNavigation`, `ManagementUsersNavigation`), screen params/parsers, structured route packages (`network.route.open.*`, `network.route.management.self.user.*`, `network.route.management.users.*`), and aggregates `core/security` for MFA/Lockout flows and `core/audit` for user-specific audit taxonomy (`UserAuditActionType`).

### Utility
- **`bom`:** Bill of Materials to align versions across all `shared-foundation-*` artifacts.

## Boundaries & Dependencies
- **`core/common`** is the leaf: It must not depend on any other internal modules.
- **Core-to-Core:** `core/audit` and `core/security` depend on `core/common`; `core/security` and `core/settings` depend on `core/audit`.
- **`feature/user`** is a high-level aggregator: It depends on `core/common`, `core/audit`, `core/security`, `core/settings`, and `core/storage`.
- **No Frameworks:** No Ktor Server/Client engines or database drivers. Only "pure" Kotlin logic and serialization.

## Guidelines for AI
- All rules and standards are now located in the `.agent/` directory.
- Refer to `AGENTS.md` for the full index of available standards.
- Always check the specific module's `README.md` for the latest component links and usage examples.

---
*Refer to `AGENTS.md` for the full list of project standards.*
