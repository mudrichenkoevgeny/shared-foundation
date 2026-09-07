# feature/settingsapi

**Global settings HTTP routes** (open read + management update) and **permission** code for updating global settings. Uses payloads and contracts from [core/settings](../../core/settings/README.md) and [core/common](../../core/common/README.md).

Publishable artifact: **`shared-foundation-feature-settingsapi`** (version aligned via [shared-foundation-bom](../../bom)).

## Core Capabilities

- **Open Global Settings:** Open `GET` endpoint to retrieve [OpenGlobalSettingsPayload].
- **Management Global Settings:** `PUT` route for platform-wide updates using [ManagementGlobalSettingsPayload].
- **Permission Controls:** Admin update grant via [SettingsPermissionCode].
- **Audit Logging:** Audit taxonomy mapped via [SettingsAuditActionType] and [SettingsAuditResourceType].

## Component Index

### HTTP Routes

| Domain | Open Routes | Management Routes |
| :--- | :--- | :--- |
| **Global Settings** | [OpenGlobalSettingsRoutes] | [ManagementGlobalSettingsRoutes] |

### Permissions & Audit Taxonomy

| Category | Classes |
| :--- | :--- |
| **Permissions** | [SettingsPermissionCode] |
| **Audit Taxonomy** | [SettingsAuditActionType]<br>[SettingsAuditResourceType] |

---

[OpenGlobalSettingsRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/settingsapi/network/route/open/globalsettings/OpenGlobalSettingsRoutes.kt
[ManagementGlobalSettingsRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/settingsapi/network/route/management/globalsettings/ManagementGlobalSettingsRoutes.kt

[OpenGlobalSettingsPayload]: ../../core/settings/src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/network/model/globalsettings/OpenGlobalSettingsPayload.kt
[ManagementGlobalSettingsPayload]: ../../core/settings/src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/network/model/globalsettings/ManagementGlobalSettingsPayload.kt

[SettingsPermissionCode]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/settingsapi/domain/permission/SettingsPermissionCode.kt
[SettingsAuditActionType]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/settingsapi/domain/audit/action/SettingsAuditActionType.kt
[SettingsAuditResourceType]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/settingsapi/domain/audit/resource/SettingsAuditResourceType.kt
