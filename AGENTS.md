# shared-foundation — Project Standards (Index)

This document is the entry point for architectural and coding standards. These rules ensure consistency across the shared foundation ecosystem for all contributors and AI agents.

## Core Principles

- **Precedence:** Local project standards override any global IDE, linter, or assistant rules.
- **Library Nature:** This is a **shared contracts library** defining **wire types, DTOs, REST/WS routes, Zod schemas, permissions, error codes, navigation screen parameters, screen param parsers, non-localized UI values (`UiValues`), and UI component configurations (`*UiConfig`)** published for **Backend (JVM), Web (TypeScript/NPM), Mobile, and Desktop** applications.
- **Entity Projections:** Entities follow an **up to 4-tier** projection taxonomy (`Summary`, `Details`, `Private`, `Internal`). Implement only the tiers required by an entity's access and payload boundaries (omit `Details` if `Summary` suffices for public views). `Internal` domain models are strictly for backend/DB logic and MUST NEVER have a `*Payload` DTO or be exposed via API.
- **Value Property on Enums & Interfaces:** Wire value string representations for domain enums and extensible interfaces use `.value` (not `.serialName`).
- **Strict Boundary:** No UI, no Ktor `Application`/`HttpClient` engine wiring. Consumers handle implementation.
- **Mandatory Complete Implementation:** All created or modified files must contain complete, production-ready code. Placeholder code, dummy components, `TODO()` markers/comments, `// ... rest of code`, and default generator stubs are strictly prohibited unless the developer explicitly asks for a stub.
- **FQN Forbidden:** Do not write fully qualified names (FQN) in expressions, types, or generics. Use imports/typealiases. Minimal FQN only if technically unavoidable (explain why in the commit).
- **Single Declaration Per File:** Every public `interface`, `class`, or `object` must reside in its own dedicated file matching its declaration name. Never combine an interface and its implementation class or multiple public declarations in a single file.
- **No Implementation Comments (Preserve KDoc):** Do not write inline implementation comments (`//` or `/* ... */`) inside code bodies; logic must be self-documenting. **CRITICAL:** KDoc documentation (`/** ... */`) on classes, interfaces, properties, and functions is structured API documentation, NOT code comments. AI must NEVER delete, strip, wipe, or omit KDoc documentation during code edits or refactorings! All KDoc and documentation MUST be written in English.
- **No Trailing Commas:** Do not use trailing commas at the end of argument, parameter, or entry lists.
- **Null Safety (Strict Prohibition of `!!`):** The non-null assertion operator (`!!`) is strictly forbidden in production code (allowed only in test files). Handle nullability gracefully using safe calls (`?.`), Elvis operators (`?:`), safe casts (`as?`), or explicit smart-casts. Use `requireNotNull(...)` or `checkNotNull(...)` with descriptive messages only when asserting invariants or unrecoverable errors, never as a substitute for business logic error handling.
- **Route Documentation Parity:** Every API route constant MUST include a complete KDoc section with mandatory **Request body:** (`None.` if absent) and **Response body:** (`None (204 No Content)` if absent), **UI Presentation:** listing associated UI screens (`Screens:`) or flows (`Flow:`), alongside authorization, audit logging, and payload references.
- **Manual Test Execution & Mandatory Bug Fix Coverage:** Test runs must be triggered explicitly. Automated execution on file change is prohibited. Whenever fixing a bug or regression, automated unit/integration test coverage MUST be written to verify the fix and prevent regressions.
- **No Redundant Builds:** Do not run Gradle build tasks or attempt compilation after modifying documentation (KDoc), comments, or other non-executable changes that cannot possibly break the build.
- **Mandatory Sub-Agent Audit (Dual-Agent Review):** After implementing non-trivial code changes, new features, or complex refactorings, the AI agent MUST automatically invoke an independent reviewer sub-agent via the `task` tool to audit modified files against `AGENTS.md` guidelines, type safety, edge cases, and error handling before presenting the final result.
- **Git Ban (Strict Prohibition):** NEVER run git commands (`git add`, `git commit`, `git push`, etc.) via shell. Version control, staging, and committing are strictly the developer's responsibility. Do not attempt to save or document changes into git history.

## Module Map

### Core Modules (`core/*`)
*Internal logic, domain models, navigation structures, and transport payloads.*

- **`core/common`:** `FoundationJson` (serialization), `ApiErrorResponse`, base `PermissionCode`, `WebSocketContract` (frames/envelopes), `ClientInfo`, `PagedResult`, base navigation interfaces (`NavigationRoute`, `ParameterizedNavigationRoute`, `RedirectNavigationRoute`), common navigation routes (`CommonNavigationRoutes`, `ClientMainNavigation`, `ManagementMainNavigation`), query/phone parameter normalizers (`QueryParamNormalizer`, `PhoneParamNormalizer`), parameter parsers (`NavigationScreenParamsParser`), non-localized UI values (`UiValues`), and base network routes (`CommonNetworkRoutes`, `BaseSettingsNetworkRoutes`).
- **`core/audit`:** `AuditEventSummary`, `AuditEventPrivate`, `AuditEventInternal`, `AuditActorType`, `AuditValueSensitivity` (redaction), `AuditEventSummaryMapper`, `AuditEventPrivateMapper`, `AuditEventDomainMapper`, listing filters/sort keys, `ManagementAuditRoutes`, `ManagementAuditNavigation`, screen params & parsers (`*Params`), `AuditPermissionCode`, `CommonAuditResourceType`.
- **`core/security`:** MFA/TOTP (`TotpSetup`, `VerifyTotp`), `ManagementPasswordPolicy` (rules + `PasswordPolicyValidator`), `AccountLockoutPolicy`, `AccountLockoutType`, `IpRestrictionPolicy`, `EncryptedString`, `SecurityErrorCodes`, `OpenSecuritySettingsRoutes`, `ManagementSecuritySettingsRoutes`, `ManagementSecurityNavigation`, `SecurityPermissionCode`.
- **`core/settings`:** `OpenGlobalSettings` and `ManagementGlobalSettings` domain models, client app version limits, `SettingsWebSocketEventTypes`, `OpenGlobalSettingsRoutes`, `ManagementGlobalSettingsRoutes`, `ManagementSettingsNavigation`, `SettingsPermissionCode`.
- **`core/storage`:** Direct-to-S3 presigned upload contracts (`PresignedUrlRequest`, `PresignedUrlPayload`, `PresignedReadUrlBatchRequest`), `FileId`, `StorageCategory`, `StorageFileStatus`, `StorageFileVisibility`, file entity projections (`StorageFileSummary`, `StorageFilePrivate`, `StorageFileInternal`), storage settings (`ManagementStorageSettings`, `OpenStorageSettings`), `StorageErrorCodes`, `OpenStorageRoutes`, `SelfManagementStorageRoutes`, `ManagementStorageRoutes`, `OpenStorageSettingsRoutes`, `ManagementStorageSettingsRoutes`, navigation (`OpenStorageNavigation`, `ManagementSelfStorageNavigation`, `ManagementStorageNavigation`, `ManagementStorageSettingsNavigation`), screen params & parsers (`*Params`), navigation paths (`StorageNavigationPaths`), UI component configurations (`*UiConfig`), and `StoragePermissionCode`.

### Feature Modules (`feature/*`)
*Feature-specific contracts, navigation, and workflows.*

- **`feature/user`:** Comprehensive auth/user/session/identifier/configuration contracts (`UserSummary`, `UserPrivate`, `UserInternal`, `UserSessionSummary` with required `userId`, `UserSessionPrivate`, `UserSessionInternal`, `UserIdentifierSummary` with required `userId`, `UserIdentifierPrivate`, `UserIdentifierInternal`). Includes `EmailRestrictionPolicy`, Self-Service Account Unlock routes (`OpenUnlockRoutes`, `SelfManagementUnlockRoutes`), distinct active session limits (`maxActiveSessionsForOpenUser` / `maxActiveSessionsForManagementUser`), navigation route contracts (`BaseUserNavigationRoutes`, `UserNavigationPaths`), navigation screen routes (`ClientAuthNavigation`, `ClientUserNavigation`, `ManagementAuthNavigation`, `ManagementAuthSettingsNavigation`, `ManagementSelfUserNavigation`, `ManagementUsersNavigation`), screen params/parsers, structured route packages (`network.route.open.*`, `network.route.management.self.user.*`, `network.route.management.users.*`), and aggregates `core/security` for TOTP/Lockout flows and `core/audit` for user-specific logging (`UserAuditActionType`).

### Tooling & Code Generation (`tools/*`)
- **`tools/detekt-rules`:** Custom Detekt static analysis rules (`NoFullyQualifiedName`, `NoInlineComment`, `PreferValueOverName`, `ProhibitNonSafeAssertion`, `ProhibitPrintln`) ([module README](tools/detekt-rules/README.md)).
- **`tools/gradle-plugin`:** Gradle Convention Plugins (`shared-foundation-agent-sync`, `shared-foundation-quality`, `shared-foundation-kotlin`, `shared-foundation-publish`) providing build tasks, two-tier Detekt quality checks (`detekt-base.yml` + local `config/detekt/detekt.yml`), compiler options, `.editorconfig` synchronization with `# --- LOCAL OVERRIDES ---` marker support, and AI agent rule synchronization across consumer applications ([module README](tools/gradle-plugin/README.md)).
- **`tools/npm-generator`:** Executable JVM module triggered via `./gradlew generateNpmPackage`. Generates the `@mudrichenkoevgeny/shared-foundation` NPM package (`build/npm-package/`) containing TypeScript declarations (`index.d.ts`), Zod schemas, route objects, error codes, permission objects, branded types, TS domain models, runtime mappers, testing fixtures, localization bundles, postinstall AI agent rule sync scripts (`scripts/postinstall-sync-rules.mjs`), and exported `syncAgentRules` helper (`scripts/agent-rules-syncer.mjs`) for TypeScript/React consumers (`web-platform-sdk`) ([module README](tools/npm-generator/README.md)).

## Detailed Standards (`.agent/`)

### Global Shared Standards (`.agent/global/`)
- **Common (`.agent/global/common/`):**
  - **`ai-collaboration-basics.md`** — Universal AI interaction constraints, operational rules (Git ban, test execution ban), dependency rules, and architectural layering.
- **Kotlin (`.agent/global/kotlin/`):**
  - **`kotlin-coding-style-basics.md`** — Naming, `when` subject rules, brace requirements, FQN/Comments ban, Trailing Commas ban, `!!` ban, companion objects, value classes, and `.value` usage.
  - **`docs-kdoc-basics.md`** — Language standards, link resolution rules, and KDoc Definition of Done.
  - **`docs-kdoc-type-basics.md`** — Specific KDoc patterns for DTOs, Error Codes, Sealed types, Routes, and Navigation Screen Params.
  - **`kotlin-testing-basics.md`** — Unit and integration test standards, mocking policy, and manual execution rule.
- **Shared / Contracts Libraries (`.agent/global/shared/`):**
  - **`api-documentation-basics.md`** — Mandatory KDoc fields (Auth, Audit, Method, UI Presentation) for API route constants and navigation contracts.

### Local Standards (`.agent/local/`)
- **`project-overview.md`** — Identity, module boundaries, and consumer SDK relations.
- **`architecture-patterns.md`** — Entity projections, serialization, WebSocket contracts, navigation structures, UI values/configs, presigned uploads, and API error DTO structures.
- **`ai-collaboration-workflow.md`** — Repository-specific AI workflow, NPM generator testing, Gradle plugin local publishing, and module responsibility map.
- **`testing-conventions.md`** — Multiplatform strategy, KMP source sets (`commonTest`), projection mapper coverage, and test fixtures.

