---
description: Local AI workflow, NPM generator testing, Gradle plugin local publishing, and module responsibility map for shared-foundation
alwaysApply: true
---

# Shared Foundation — Local AI Collaboration & Workflow

This document defines repository-specific workflow requirements for AI-assisted development within `shared-foundation`.

## 1. Single Source of Truth (SSOT) Contract Principles

- **Contract Library Role:** `shared-foundation` defines the wire types, DTOs, REST/WS routes, Zod schemas, permissions, error codes, navigation screen parameters, screen param parsers, non-localized UI values (`UiValues`), and UI component configurations (`*UiConfig`) published for Backend (JVM), Web (TypeScript/NPM), Mobile (KMP), and Desktop applications.
- **Strict Boundary:** No UI rendering, no Ktor `Application`/`HttpClient` engine wiring. Framework-specific implementation belongs in consumer SDKs (`backend-platform-sdk`, `kmp-platform-sdk`, `web-platform-sdk`).
- **Backward Compatibility:** All DTOs, route constants, and Zod schemas are public APIs for external consumers. Avoid breaking changes without semver coordination.

## 2. Verification Workflows

- **NPM Package Verification:** When creating or modifying DTOs, routes, domain models, permissions, screen parameters, or testing fixtures, verify NPM generator exports by running:
  `./gradlew generateNpmPackage`
  This task automatically builds the NPM package, runs `auditNpmExports` parity checks, and executes `verifyConsumerNpmPackage` (Node.js syntax, declaration integrity, and `npm pack` tarball verification).
- **Gradle Convention Plugins Verification:** When modifying `tools/gradle-plugin` or Detekt base configurations, publish plugins to local Maven for verification:
  `./gradlew publishToMavenLocal`

## 3. Module Responsibility Mapping

Place code in the correct module based on its functional role:

- **`core/common`:** `FoundationJson` (serialization), `ApiErrorResponse`, base `PermissionCode`, `WebSocketContract` (frames/envelopes), `ClientInfo`, `PagedResult`, base navigation interfaces (`NavigationRoute`, `ParameterizedNavigationRoute`, `RedirectNavigationRoute`), common navigation routes (`CommonNavigationRoutes`), parameter normalizers (`QueryParamNormalizer`, `PhoneParamNormalizer`), parameter parsers (`NavigationScreenParamsParser`), non-localized UI values (`UiValues`), and base network routes (`CommonNetworkRoutes`, `BaseSettingsNetworkRoutes`).
- **`core/audit`:** `AuditEventSummary`, `AuditEventPrivate`, `AuditEventInternal`, `AuditActorType`, `AuditValueSensitivity` (redaction), `AuditEventSummaryMapper`, `AuditEventPrivateMapper`, `AuditEventDomainMapper`, listing filters/sort keys, `ManagementAuditRoutes`, `ManagementAuditNavigation`, screen params & parsers (`*Params`), `AuditPermissionCode`, `CommonAuditResourceType`.
- **`core/security`:** MFA/TOTP (`TotpSetup`, `VerifyTotp`), `ManagementPasswordPolicy` (rules + `PasswordPolicyValidator`), `AccountLockoutPolicy`, `AccountLockoutType`, `IpRestrictionPolicy`, `EncryptedString`, `SecurityErrorCodes`, `OpenSecuritySettingsRoutes`, `ManagementSecuritySettingsRoutes`, `ManagementSecurityNavigation`, `SecurityPermissionCode`.
- **`core/settings`:** `OpenGlobalSettings` and `ManagementGlobalSettings` domain models, client app version limits, `SettingsWebSocketEventTypes`, `OpenGlobalSettingsRoutes`, `ManagementGlobalSettingsRoutes`, `ManagementSettingsNavigation`, `SettingsPermissionCode`.
- **`core/storage`:** Direct-to-S3 presigned upload contracts (`PresignedUrlRequest`, `PresignedUrlPayload`, `PresignedReadUrlBatchRequest`), `FileId`, `StorageCategory`, `StorageFileStatus`, `StorageFileVisibility`, file entity projections (`StorageFileSummary`, `StorageFilePrivate`, `StorageFileInternal`), storage settings (`ManagementStorageSettings`, `OpenStorageSettings`), `StorageErrorCodes`, `OpenStorageRoutes`, `SelfManagementStorageRoutes`, `ManagementStorageRoutes`, `OpenStorageSettingsRoutes`, `ManagementStorageSettingsRoutes`, navigation (`OpenStorageNavigation`, `ManagementSelfStorageNavigation`, `ManagementStorageNavigation`, `ManagementStorageSettingsNavigation`), screen params & parsers (`*Params`), navigation paths (`StorageNavigationPaths`), UI component configurations (`*UiConfig`), and `StoragePermissionCode`.
- **`feature/user`:** Comprehensive auth/user/session/identifier/configuration contracts (`UserSummary`, `UserPrivate`, `UserInternal`, `UserSessionSummary` with required `userId`, `UserSessionPrivate`, `UserSessionInternal`, `UserIdentifierSummary` with required `userId`, `UserIdentifierPrivate`, `UserIdentifierInternal`). Includes `EmailRestrictionPolicy`, Self-Service Account Unlock routes (`OpenUnlockRoutes`, `SelfManagementUnlockRoutes`), distinct active session limits (`maxActiveSessionsForOpenUser` / `maxActiveSessionsForManagementUser`), navigation route contracts (`BaseUserNavigationRoutes`, `UserNavigationPaths`), navigation screen routes (`ClientAuthNavigation`, `ClientUserNavigation`, `ManagementAuthNavigation`, `ManagementAuthSettingsNavigation`, `ManagementSelfUserNavigation`, `ManagementUsersNavigation`), screen params/parsers, structured route packages (`network.route.open.*`, `network.route.management.self.user.*`, `network.route.management.users.*`), and aggregates `core/security` for TOTP/Lockout flows and `core/audit` for user-specific logging (`UserAuditActionType`).
- **`testing`:** Published testing infrastructure (`shared-foundation-testing`) providing 1-file-per-class mirrored fixtures (`<OriginalClassName>Fixture.kt`), deterministic builders (`createTest*`), value class constants/generators (`TEST_*`), navigation parameter builders (`createTest*Params`), collection/paged generators (`createTest*List`, `createTestPagedResult`), and in-memory mocks (`LocalizedStringResolverMock`, `LocalizationBundleProviderMock`, `PasswordPolicyValidatorMock`). When creating or updating domain models, DTOs, value classes, or screen params, create or update their corresponding fixtures in `testing` ([module README](testing/README.md)).
- **`tools/detekt-rules`:** Custom Detekt static analysis rules (`NoFullyQualifiedName`, `NoInlineComment`, `PreferValueOverName`, `ProhibitNonSafeAssertion`, `ProhibitPrintln`).
- **`tools/gradle-plugin`:** Gradle Convention Plugins (`shared-foundation-agent-sync`, `shared-foundation-quality`, `shared-foundation-kotlin`, `shared-foundation-publish`, `shared-foundation-localization`).
- **`tools/npm-generator`:** Executable JVM module triggered via `./gradlew generateNpmPackage`. Generates the `@mudrichenkoevgeny/shared-foundation` NPM package (`build/npm-package/`).

---
*Refer to `AGENTS.md` for the full list of project standards.*
