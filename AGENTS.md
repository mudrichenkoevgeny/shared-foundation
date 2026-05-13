# shared-foundation — Project Standards (Index)

This document is the entry point for architectural and coding standards. These rules ensure consistency across the Kotlin Multiplatform ecosystem for all contributors and AI agents.

## Core Principles

- **Precedence:** Local project standards override any global IDE, linter, or assistant rules.
- **Library Nature:** This is a **KMP library** defining **shared wire types and constants** (JSON DTOs, route paths, permissions, error codes) for **backend and client**.
- **Strict Boundary:** No UI, no Ktor `Application`/`HttpClient` engine wiring. Consumers handle implementation.
- **FQN Forbidden:** Do not write fully qualified names (FQN) in expressions, types, or generics. Use imports/typealiases. Minimal FQN only if technically unavoidable (explain why in the commit).
- **No Comments:** Do not write comments in the code. Logic must be self-documenting through naming and structure.
- **Manual Test Execution:** Test runs must be triggered explicitly. Automated execution on file change is prohibited.

## Module Map

### Core Modules (`core/*`)
*Internal logic, domain models, and transport payloads.*

- **`core/common`:** `FoundationJson` (serialization), `ApiErrorResponse`, base `PermissionCode`, `WebSocketContract` (frames/envelopes), `ClientInfo`, `PagedResult`.
- **`core/audit`:** `AuditEvent`, `AuditActorType`, `AuditValueSensitivity` (redaction), `AuditEventMapper`, listing filters/sort keys.
- **`core/security`:** MFA/TOTP (`TotpSetup`, `VerifyTotp`), `PasswordPolicy` (rules + `PasswordPolicyValidator`), `EncryptedString`, `SecurityErrorCodes`.
- **`core/settings`:** `GlobalSettings` domain and payloads, `SettingsWebSocketEventTypes`.

### Feature API Modules (`feature/*-api`)
*HTTP route definitions, permissions, and audit taxonomy.*

- **`feature/auditapi`:** `ManagementAuditRoutes`, `AuditPermissionCode` (masked/unmasked access), `CommonAuditResourceType`.
- **`feature/securityapi`:** `OpenSecuritySettingsRoutes`, `ManagementSecuritySettingsRoutes`, `SecurityPermissionCode`.
- **`feature/settingsapi`:** `OpenGlobalSettingsRoutes`, `ManagementGlobalSettingsRoutes`, `SettingsPermissionCode`.
- **`feature/user`:** Comprehensive auth/user/session/identifier contracts. Aggregates `core/security` for TOTP flows and `core/audit` for user-specific logging (`UserAuditActionType`).

## Detailed Standards (`.agent/`)

- **`project-overview.md`** — Identity, module boundaries, and consumer SDK relations.
- **`architecture-patterns.md`** — Serialization, WebSocket contracts, and API error DTO structures.
- **`api-documentation-standard.md`** — Mandatory KDoc fields (Auth, Audit, Method) for API route constants.
- **`docs-kdoc-basics.md`** — Language standards, link resolution rules, and KDoc Definition of Done.
- **`docs-kdoc-type-requirements.md`** — Specific KDoc patterns for DTOs, Error Codes, Sealed types, and Routes.
- **`kotlin-coding-style.md`** — Naming, `when` subject rules, brace requirements, and FQN/Comments ban.
- **`testing-conventions.md`** — Unit and integration test standards, mocking policy, and manual execution rule.
- **`ai-collaboration-workflow.md`** — AI constraints, dependency management (Version Catalog), and module responsibility mapping.