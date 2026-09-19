# core/settings

**Global (system-wide) settings** contracts: **domain models**, **HTTP/WebSocket payloads**, JSON **field name** constants, **mappers**, and **WebSocket event type** strings. Depends on **core/common** and **core/audit** (Gradle) so generated artifacts stay aligned with the rest of the monorepo; source in this module focuses on settings shapes only.

**Open and management HTTP paths** ([OpenGlobalSettingsRoutes], [ManagementGlobalSettingsRoutes]) and **permission codes** ([SettingsPermissionCode]) are defined in this module.

This module does **not** implement persistence, Ktor plugins, or authorization.

## What it provides

- **Field names:** [GlobalSettingsApiFields] — snake_case JSON keys used in `SerialName` (privacy policy, TOS, support email, service flags).
- **Domain:** [OpenGlobalSettings] and [ManagementGlobalSettings] — clean data classes for business logic and UI.
- **Wire payloads:** [OpenGlobalSettingsPayload] and [ManagementGlobalSettingsPayload] — `@Serializable` models for network transport, using constants for field naming.
- **Mappers:** Extension functions for mapping between payloads and domain models.
- **WebSocket:** [SettingsWebSocketEventTypes] — event strings `OPEN_GLOBAL_SETTINGS_UPDATED` (carries [OpenGlobalSettingsPayload]) and `MANAGEMENT_GLOBAL_SETTINGS_UPDATED` (carries [ManagementGlobalSettingsPayload]).

## Usage

- Depend on `shared-foundation-core-settings` (or [shared-foundation-bom](../../bom) — see repository [README.md](../../../../README.md)).
- Serialize with the same `Json` as the rest of the stack ([FoundationJson] from `core/common`).
- Use [SettingsPermissionCode] and route constants ([OpenGlobalSettingsRoutes], [ManagementGlobalSettingsRoutes]) for HTTP endpoints and authorization.

[GlobalSettingsApiFields]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/network/contract/GlobalSettingsApiFields.kt
[OpenGlobalSettings]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/domain/model/globalsettings/OpenGlobalSettings.kt
[ManagementGlobalSettings]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/domain/model/globalsettings/ManagementGlobalSettings.kt
[OpenGlobalSettingsPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/network/model/globalsettings/OpenGlobalSettingsPayload.kt
[ManagementGlobalSettingsPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/network/model/globalsettings/ManagementGlobalSettingsPayload.kt
[SettingsWebSocketEventTypes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/network/contract/SettingsWebSocketEventTypes.kt
[SettingsPermissionCode]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/domain/permission/SettingsPermissionCode.kt
[OpenGlobalSettingsRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/network/route/open/globalsettings/OpenGlobalSettingsRoutes.kt
[ManagementGlobalSettingsRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/network/route/management/globalsettings/ManagementGlobalSettingsRoutes.kt
[FoundationJson]: ../common/src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/serialization/FoundationJson.kt
