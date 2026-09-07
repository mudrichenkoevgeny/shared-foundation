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
- **`core/audit`:** Audit domain logic. `AuditEvent`, `AuditActorType`, `AuditValueSensitivity` (PII redaction policies), and mappers for converting domain events to wire payloads.
- **`core/security`:** Security primitives. MFA/TOTP lifecycle (`TotpSetup`, `VerifyTotp`), `PasswordPolicy` with `PasswordPolicyValidator`, and cryptographic value objects (`EncryptedString`, `PasswordHash`).
- **`core/settings`:** Global system settings shapes. `GlobalSettings` domain models and `SettingsWebSocketEventTypes`.

### Feature API Modules (`feature/`)
Focus on HTTP contracts, specific route constants, and access control.

- **`feature/auditapi`:** Management endpoints for audit logs. Contains `ManagementAuditRoutes` and `AuditPermissionCode` (controlling masked vs unmasked data visibility).
- **`feature/securityapi`:** Security configuration routes. `OpenSecuritySettingsRoutes` (open) and `ManagementSecuritySettingsRoutes` (restricted).
- **`feature/settingsapi`:** System-wide settings routes. `OpenGlobalSettingsRoutes` and `ManagementGlobalSettingsRoutes`.
- **`feature/user`:** Comprehensive feature module for Auth, Sessions, and Identifiers. Aggregates `core/security` for MFA flows and `core/audit` for user-specific audit taxonomy (`UserAuditActionType`).

### Utility
- **`bom`:** Bill of Materials to align versions across all `shared-foundation-*` artifacts.

## Boundaries & Dependencies
- **`core/common`** is the leaf: It must not depend on any other internal modules.
- **Core-to-Core:** `core/audit` and `core/security` depend on `core/common`.
- **Feature-to-Core:** Feature-API modules depend on their respective `core` modules (e.g., `feature/securityapi` -> `core/security`).
- **`feature/user`** is a high-level aggregator: It depends on `core/common`, `core/audit`, `core/security`, and `core/settings`.
- **No Frameworks:** No Ktor Server/Client engines or database drivers. Only "pure" Kotlin logic and serialization.

## Guidelines for AI
- All rules and standards are now located in the `.agent/` directory.
- Refer to `AGENTS.md` for the full index of available standards.
- Always check the specific module's `README.md` for the latest component links and usage examples.

---
*Refer to `AGENTS.md` for the full list of project standards.*