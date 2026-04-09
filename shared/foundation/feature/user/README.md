# feature/user

**User, auth, session, and identifier HTTP contracts** (open/self-service and management), **permission codes**, **error code strings**, **WebSocket event names**, DTOs, domain models, and mappers shared by backend and clients. Depends on [core/common](../../core/common/README.md), [core/audit](../../core/audit/README.md), [core/security](../../core/security/README.md), [core/settings](../../core/settings/README.md), and the feature API modules [audit-api](../audit/api/README.md), [security-api](../security/api/README.md), and [settings-api](../settings-api/README.md).

Publishable artifact: **`shared-foundation-feature-user`** (version aligned via [shared-foundation-bom](../../bom)).

## What it provides

- **Base route segments:** [BaseAuthRoutes], [BaseUserRoutes], [BaseSessionRoutes], [BaseIdentifiersRoutes], [BaseUserConfigurationRoutes], [BaseManagementUserRoutes], [BaseManagementSessionRoutes], [BaseManagementIdentifierRoutes], [BaseAuthSettingsRoutes] — path templates reused by open and management endpoints.
- **Open API:** registration, login, refresh token, reset password ([OpenRegisterRoutes], [OpenLoginRoutes], [OpenRefreshTokenRoutes], [OpenResetPasswordRoutes]); self-service user and session flows ([OpenUserRoutes], [OpenSessionRoutes], [OpenIdentifierRoutes]); public configuration and auth settings ([OpenUserConfigurationRoutes], [OpenAuthSettingsRoutes]). Request/response shapes and KDoc live next to each route object.
- **Management API:** user CRUD and profile updates ([ManagementUserRoutes], [BaseManagementUserRoutes]); sessions and identifiers ([ManagementSessionRoutes], [ManagementIdentifierRoutes]); full auth settings read/update ([ManagementAuthSettingsRoutes]). Each endpoint’s KDoc states HTTP method, query/body contracts where applicable, and required [PermissionCode] grants (see types under `domain/permission/`).
- **Permissions:** [UserPermissionCode], [SessionPermissionCode], [IdentifierPermissionCode]; role defaults in [UserRoleDefaultPermissionCode]. Servers enforce these alongside optional grants from security/settings/audit API modules.
- **Errors:** [UserErrorCodes], [UserErrorArgs] — machine-readable strings aligned with [ApiErrorResponse] `code` / `args` from core/common.
- **Audit taxonomy:** [UserAuditActionType], [UserAuditResourceType] — action/resource enum segments for user-feature audit events.
- **Real-time:** [UserWebSocketEventTypes] and related payloads where the user feature publishes WebSocket notifications.
- **Mappers:** payload ↔ domain mapping under `mapper/` (e.g. user, session, identifier, auth settings).

## Usage

- Depend on this module when implementing or calling user/auth/session HTTP APIs and shared user configuration; pair with the `core/*` artifacts for listing primitives, audit/security/settings payloads, and shared HTTP headers.
- Keep JSON field names aligned with `network/contract` objects such as [UserApiFields] and feature WebSocket contracts; use route KDoc as the authoritative per-endpoint contract (methods, bodies, authorization).

[ApiErrorResponse]: ../../core/common/src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/error/model/ApiErrorResponse.kt
[BaseAuthRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/base/auth/BaseAuthRoutes.kt
[BaseUserRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/base/user/BaseUserRoutes.kt
[BaseSessionRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/base/session/BaseSessionRoutes.kt
[BaseIdentifiersRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/base/identifier/BaseIdentifiersRoutes.kt
[BaseUserConfigurationRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/base/configuration/BaseUserConfigurationRoutes.kt
[BaseManagementUserRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/base/user/BaseManagementUserRoutes.kt
[BaseManagementSessionRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/base/session/BaseManagementSessionRoutes.kt
[BaseManagementIdentifierRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/base/identifier/BaseManagementIdentifierRoutes.kt
[BaseAuthSettingsRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/base/auth/settings/BaseAuthSettingsRoutes.kt
[OpenRegisterRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/open/auth/register/OpenRegisterRoutes.kt
[OpenLoginRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/open/auth/login/OpenLoginRoutes.kt
[OpenRefreshTokenRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/open/auth/refreshtoken/OpenRefreshTokenRoutes.kt
[OpenResetPasswordRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/open/auth/resetpassword/OpenResetPasswordRoutes.kt
[OpenUserRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/open/user/OpenUserRoutes.kt
[OpenSessionRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/open/session/OpenSessionRoutes.kt
[OpenIdentifierRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/open/identifier/OpenIdentifierRoutes.kt
[OpenUserConfigurationRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/open/configuration/OpenUserConfigurationRoutes.kt
[OpenAuthSettingsRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/open/auth/settings/OpenAuthSettingsRoutes.kt
[ManagementUserRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/management/user/ManagementUserRoutes.kt
[ManagementSessionRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/management/session/ManagementSessionRoutes.kt
[ManagementIdentifierRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/management/identifier/ManagementIdentifierRoutes.kt
[ManagementAuthSettingsRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/management/auth/settings/ManagementAuthSettingsRoutes.kt
[PermissionCode]: ../../core/common/src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/domain/model/permission/PermissionCode.kt
[UserPermissionCode]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/permission/UserPermissionCode.kt
[SessionPermissionCode]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/permission/SessionPermissionCode.kt
[IdentifierPermissionCode]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/permission/IdentifierPermissionCode.kt
[UserRoleDefaultPermissionCode]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/permission/UserRoleDefaultPermissionCode.kt
[UserErrorCodes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/error/naming/UserErrorCodes.kt
[UserErrorArgs]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/error/naming/UserErrorArgs.kt
[UserAuditActionType]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/audit/action/UserAuditActionType.kt
[UserAuditResourceType]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/audit/resource/UserAuditResourceType.kt
[UserWebSocketEventTypes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/contract/UserWebSocketEventTypes.kt
[UserApiFields]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/contract/UserApiFields.kt
