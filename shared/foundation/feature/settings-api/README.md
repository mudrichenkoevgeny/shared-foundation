# feature/settings-api

**Global settings HTTP routes** (public read + management update) and **permission** code for updating global settings. Uses payloads and contracts from [core/settings](../../core/settings/README.md) and [core/common](../../core/common/README.md).

Publishable artifact: **`shared-foundation-feature-settings-api`** (version aligned via [shared-foundation-bom](../../bom)).

## What it provides

- **Base path segments:** [BaseGlobalSettingsRoutes].
- **Open API:** [OpenGlobalSettingsRoutes] — `GET` [GlobalSettingsPayload].
- **Management API:** [ManagementGlobalSettingsRoutes] — `PUT` with [GlobalSettingsPayload]; requires [SettingsSecurityPermissionCode] `GLOBAL_SETTINGS_UPDATE` (see KDoc).
- **Permission:** [SettingsSecurityPermissionCode] — management global-settings update grant.
- **Audit taxonomy:** [SettingsAuditActionType], [SettingsAuditResourceType] — action/resource enum segments for settings-related audit events.

## Usage

- Depend on this module when wiring HTTP clients or servers for global settings URLs and authorization checks.
- Pair with `shared-foundation-core-settings` for mappers and WebSocket event strings.

[BaseGlobalSettingsRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/settings/network/route/base/globalsettings/BaseGlobalSettingsRoutes.kt
[OpenGlobalSettingsRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/settings/network/route/open/globalsettings/OpenGlobalSettingsRoutes.kt
[ManagementGlobalSettingsRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/settings/network/route/management/globalsettings/ManagementGlobalSettingsRoutes.kt
[GlobalSettingsPayload]: ../../core/settings/src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/network/model/globalsettings/GlobalSettingsPayload.kt
[SettingsSecurityPermissionCode]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/settings/domain/permission/SettingsSecurityPermissionCode.kt
[SettingsAuditActionType]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/settings/domain/model/audit/action/SettingsAuditActionType.kt
[SettingsAuditResourceType]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/settings/domain/model/audit/resource/SettingsAuditResourceType.kt
