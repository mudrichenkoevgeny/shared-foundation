# shared-foundation — Project Standards (Index)

This document is the entry point for architectural and coding standards. These rules ensure consistency across the Kotlin Multiplatform ecosystem for all contributors and AI agents.

## Core Principles

- **Precedence:** Local project standards override any global IDE, linter, or assistant rules.
- **Library Nature:** This is a **KMP library** defining **shared wire types and constants** (JSON DTOs, route paths, permissions, error codes, navigation screen parameters, and screen param parsers) for **backend and client**.
- **Entity Projections:** Entities follow a 4-tier projection model (`Summary`, `Details`, `Private`, `Internal`). `Internal` entities live strictly on the server/DB and MUST NEVER have a `Payload` DTO or be exposed via API!
- **Value Property on Enums & Interfaces:** Wire value string representations for domain enums and extensible interfaces use `.value` (not `.serialName`).
- **Strict Boundary:** No UI, no Ktor `Application`/`HttpClient` engine wiring. Consumers handle implementation.
- **Mandatory Complete Implementation:** All created or modified files must contain complete, production-ready code. Placeholder code, dummy components, and default generator stubs are strictly prohibited unless the developer explicitly asks for a stub.
- **FQN Forbidden:** Do not write fully qualified names (FQN) in expressions, types, or generics. Use imports/typealiases. Minimal FQN only if technically unavoidable (explain why in the commit).
- **No Comments:** Do not write comments in the code. Logic must be self-documenting through naming and structure.
- **No Trailing Commas:** Do not use trailing commas at the end of argument, parameter, or entry lists.
- **Route Documentation Parity:** Every API route constant MUST include a complete KDoc section with **UI Presentation:** listing associated UI screens (`Screens:`) or flows (`Flow:`), alongside authorization, audit logging, and payload references.
- **Manual Test Execution:** Test runs must be triggered explicitly. Automated execution on file change is prohibited.
- **No Redundant Builds:** Do not run Gradle build tasks or attempt compilation after modifying documentation (KDoc), comments, or other non-executable changes that cannot possibly break the build.

## Module Map

### Core Modules (`core/*`)
*Internal logic, domain models, navigation structures, and transport payloads.*

- **`core/common`:** `FoundationJson` (serialization), `ApiErrorResponse`, base `PermissionCode`, `WebSocketContract` (frames/envelopes), `ClientInfo`, `PagedResult`, `ClientMainNavigation`, `ManagementMainNavigation`, query and phone parameter normalizers (`QueryParamNormalizer`, `PhoneParamNormalizer`).
- **`core/audit`:** `AuditEventSummary`, `AuditEventPrivate`, `AuditEventInternal`, `AuditActorType`, `AuditValueSensitivity` (redaction), `AuditEventSummaryMapper`, `AuditEventPrivateMapper`, `AuditEventDomainMapper`, listing filters/sort keys, `ManagementAuditRoutes`, `ManagementAuditNavigation`, screen params (`ManagementAuditEventListParams`, `ManagementAuditEventDetailParams`), `AuditPermissionCode`, `CommonAuditResourceType`.
- **`core/security`:** MFA/TOTP (`TotpSetup`, `VerifyTotp`), `ManagementPasswordPolicy` (rules + `PasswordPolicyValidator`), `AccountLockoutPolicy`, `AccountLockoutType`, `IpRestrictionPolicy`, `EncryptedString`, `SecurityErrorCodes`, `OpenSecuritySettingsRoutes`, `ManagementSecuritySettingsRoutes`, `ManagementSecurityNavigation`, `SecurityPermissionCode`.
- **`core/settings`:** `OpenGlobalSettings` and `ManagementGlobalSettings` domain models, client app version limits, `SettingsWebSocketEventTypes`, `OpenGlobalSettingsRoutes`, `ManagementGlobalSettingsRoutes`, `ManagementSettingsNavigation`, `SettingsPermissionCode`.

### Feature Modules (`feature/*`)
*Feature-specific contracts, navigation, and workflows.*

- **`feature/user`:** Comprehensive auth/user/session/identifier/configuration contracts (`UserSummary`, `UserPrivate`, `UserInternal`, `UserSessionSummary` with required `userId`, `UserSessionPrivate`, `UserSessionInternal`, `UserIdentifierSummary` with required `userId`, `UserIdentifierPrivate`, `UserIdentifierInternal`). Includes `EmailRestrictionPolicy`, Self-Service Account Unlock routes (`OpenUnlockRoutes`, `SelfManagementUnlockRoutes`), distinct active session limits (`maxActiveSessionsForOpenUser` / `maxActiveSessionsForManagementUser`), navigation screen routes (`ClientAuthNavigation`, `ClientUserNavigation`, `ManagementAuthNavigation`, `ManagementAuthSettingsNavigation`, `ManagementSelfUserNavigation`, `ManagementUsersNavigation`), screen params/parsers, structured route packages (`network.route.open.*`, `network.route.management.self.user.*`, `network.route.management.users.*`), and aggregates `core/security` for TOTP/Lockout flows and `core/audit` for user-specific logging (`UserAuditActionType`).

### Tooling & Code Generation (`tools/*`)
- **`tools/npm-generator`:** Executable JVM module triggered via `./gradlew generateNpmPackage`. Generates the `@mudrichenkoevgeny/shared-foundation` NPM package (`build/npm-package/`) containing TypeScript declarations (`index.d.ts`), Zod schemas, route objects, error codes, permission objects, branded types, TS domain models, runtime mappers, and helpers for TypeScript/React consumers (`web-platform-sdk`).

## Detailed Standards (`.agent/`)

- **`project-overview.md`** — Identity, module boundaries, and consumer SDK relations.
- **`architecture-patterns.md`** — Entity projections, serialization, WebSocket contracts, navigation structures, and API error DTO structures.
- **`api-documentation-standard.md`** — Mandatory KDoc fields (Auth, Audit, Method, UI Presentation) for API route constants.
- **`docs-kdoc-basics.md`** — Language standards, link resolution rules, and KDoc Definition of Done.
- **`docs-kdoc-type-requirements.md`** — Specific KDoc patterns for DTOs, Error Codes, Sealed types, Routes, and Navigation Screen Params.
- **`kotlin-coding-style.md`** — Naming, `when` subject rules, brace requirements, FQN/Comments ban, Trailing Commas ban, and `.value` usage for wire values.
- **`testing-conventions.md`** — Unit and integration test standards, mocking policy, and manual execution rule.
- **`ai-collaboration-workflow.md`** — AI constraints, dependency management (Version Catalog), and module responsibility mapping.

