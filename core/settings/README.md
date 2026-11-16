# core/settings

**Global (system-wide) settings contracts:** domain models, HTTP/WebSocket request/response payloads, field name constants, mappers, permission codes, audit taxonomy, and management navigation screen routes. Depends on **core/common** and **core/audit** so generated artifacts stay aligned across the platform.

This module defines pure KMP contracts and route specifications for reading and updating platform-wide settings (support email, terms of service, privacy policy, client version constraints, maintenance flags) without handling server storage or Ktor engine wiring.

## What it provides

- **Domain Models & Entities:**
  - [OpenGlobalSettings]: Public configuration available to client applications (privacy policy URL, terms of service URL, support email, client version limits, service maintenance flags).
  - [ManagementGlobalSettings]: Full administrative configuration including internal administrative parameters.
- **Wire Payloads & Serialization:**
  - [OpenGlobalSettingsPayload] & [ManagementGlobalSettingsPayload]: `@Serializable` network transport models using snake_case field naming from [GlobalSettingsApiFields].
- **Mappers & Domain Converters:**
  - [OpenGlobalSettingsMapper] & [ManagementGlobalSettingsMapper]: Conversions between domain models and DTO payloads.
- **HTTP Routes & Permissions:**
  - [OpenGlobalSettingsRoutes]: Public route (`GET_SETTINGS = "/settings/global"`) for fetching open system configuration.
  - [ManagementGlobalSettingsRoutes]: Management routes (`GET_SETTINGS = "/management/settings/global"`, `PUT_SETTINGS = "/management/settings/global"`) for inspecting and updating global settings.
  - [BaseSettingsNetworkRoutes] & [BaseSettingsNavigationRoutes]: Base route contracts (`/settings/global`).
  - [SettingsPermissionCode]: Permission codes (`SETTINGS_GLOBAL_VIEW`, `SETTINGS_GLOBAL_UPDATE`) controlling access to settings endpoints.
- **WebSocket Real-Time Contracts:**
  - [SettingsWebSocketEventTypes]: Realtime event strings `OPEN_GLOBAL_SETTINGS_UPDATED` (carries [OpenGlobalSettingsPayload]) and `MANAGEMENT_GLOBAL_SETTINGS_UPDATED` (carries [ManagementGlobalSettingsPayload]).
- **Navigation & Screen Routes:**
  - [ManagementSettingsNavigation]: Screen navigation routes:
    - `GlobalSettings` (`/settings/global`) — Params: None.
- **Audit Taxonomy:**
  - [SettingsAuditActionType] & [SettingsAuditResourceType]: Audit action and resource types for tracking administrative changes to global settings in audit logs.

## Usage

- **Dependency:** Add `shared-foundation-core-settings` via BOM (see repository [README.md](../../README.md)).
- **Serialization:** All payloads use `snake_case` naming via [GlobalSettingsApiFields] and are compatible with [FoundationJson].
- **Realtime Sync:** Connect to WebSocket and handle [SettingsWebSocketEventTypes.OPEN_GLOBAL_SETTINGS_UPDATED] to dynamically update client settings without requiring app restarts.

[GlobalSettingsApiFields]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/network/contract/GlobalSettingsApiFields.kt
[OpenGlobalSettings]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/domain/model/globalsettings/OpenGlobalSettings.kt
[ManagementGlobalSettings]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/domain/model/globalsettings/ManagementGlobalSettings.kt
[OpenGlobalSettingsPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/network/model/globalsettings/OpenGlobalSettingsPayload.kt
[ManagementGlobalSettingsPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/network/model/globalsettings/ManagementGlobalSettingsPayload.kt
[OpenGlobalSettingsMapper]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/mapper/globalsettings/OpenGlobalSettingsMapper.kt
[ManagementGlobalSettingsMapper]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/mapper/globalsettings/ManagementGlobalSettingsMapper.kt
[SettingsWebSocketEventTypes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/network/contract/SettingsWebSocketEventTypes.kt
[SettingsPermissionCode]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/domain/permission/SettingsPermissionCode.kt
[OpenGlobalSettingsRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/network/route/open/globalsettings/OpenGlobalSettingsRoutes.kt
[ManagementGlobalSettingsRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/network/route/management/globalsettings/ManagementGlobalSettingsRoutes.kt
[BaseSettingsNetworkRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/network/route/BaseSettingsNetworkRoutes.kt
[BaseSettingsNavigationRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/navigation/route/BaseSettingsNavigationRoutes.kt
[ManagementSettingsNavigation]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/navigation/route/management/ManagementSettingsNavigation.kt
[SettingsAuditActionType]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/domain/audit/action/SettingsAuditActionType.kt
[SettingsAuditResourceType]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/domain/audit/resource/SettingsAuditResourceType.kt
[FoundationJson]: ../common/src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/serialization/FoundationJson.kt
