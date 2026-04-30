# feature/settings-api

**Global settings HTTP routes** (public read + management update) and **permission** code for updating global settings. Uses payloads and contracts from [core/settings](../../core/settings/README.md) and [core/common](../../core/common/README.md).

Publishable artifact: **`shared-foundation-feature-settings-api`** (version aligned via [shared-foundation-bom](../../bom)).

## What it provides

- **Base path segments:** [BaseGlobalSettingsRoutes] — shared `/global-settings` prefix.
- **Open API:** [OpenGlobalSettingsRoutes] — public `GET` route to retrieve [GlobalSettingsPayload].
- **Management API:** [ManagementGlobalSettingsRoutes] — `PUT` route for platform-wide updates; defines strict authorization (Staff/Admin roles) and audit requirements.
- **Permission:** [SettingsPermissionCode] — contains `GLOBAL_SETTINGS_UPDATE` (`global.settings.update`) grant.
- **Audit taxonomy:** [SettingsAuditActionType], [SettingsAuditResourceType] — enums for settings-related audit events (actions and resource kinds).

## Usage

- Depend on this module when wiring HTTP clients or servers for global settings URLs and authorization checks.
- Pair with `shared-foundation-core-settings` for mappers and WebSocket event strings.

[BaseGlobalSettingsRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/settings/api/network/route/base/globalsettings/BaseGlobalSettingsRoutes.kt
[OpenGlobalSettingsRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/settings/api/network/route/open/globalsettings/OpenGlobalSettingsRoutes.kt
[ManagementGlobalSettingsRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/settings/api/network/route/management/globalsettings/ManagementGlobalSettingsRoutes.kt
[GlobalSettingsPayload]: ../../core/settings/src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/network/model/globalsettings/GlobalSettingsPayload.kt
[SettingsPermissionCode]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/settings/api/domain/permission/SettingsPermissionCode.kt
[SettingsAuditActionType]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/settings/api/domain/audit/action/SettingsAuditActionType.kt
[SettingsAuditResourceType]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/settings/api/domain/audit/resource/SettingsAuditResourceType.kt