# core/settings

**Global (system-wide) settings** contracts for shared-foundation: HTTP routes for public and admin APIs, **request/response DTOs**, JSON field name constants, and **WebSocket event type** strings. Intended for use by backend and client SDKs together with [core/common](../common/README.md) (`FoundationJson`, shared header/field conventions where applicable). This module does **not** implement persistence, Ktor routes, or authorization — only **types and path constants**.

## What it provides

- **Field names:** [GlobalSettingsApiFields] — snake_case JSON keys for privacy policy URL, terms of service URL, and support email.
- **Responses:** [GlobalSettingsResponse] — nullable URL and email fields for public reads.
- **Requests:** [UpdateGlobalSettingsRequest] — same shape for admin updates (all properties nullable).
- **HTTP routes — public API:** [GlobalSettingsRoutes] — `GET` for current global settings ([GlobalSettingsResponse]).
- **HTTP routes — admin API:** [AdminGlobalSettingsRoutes] — `PUT` with [UpdateGlobalSettingsRequest].
- **WebSocket:** [SettingsWebSocketEventTypes] — e.g. `GLOBAL_SETTINGS_UPDATED` with payload described as [GlobalSettingsResponse] in KDoc.

## Usage

- Depend on the published artifact `shared-foundation-core-settings` (or use [shared-foundation-bom](../../bom) / version catalog — see repository [README.md](../../../../README.md)).
- Serialize with the same `Json` settings as the rest of the stack (typically [FoundationJson] from `core/common`).
- Wire server and client HTTP paths using the `const val` routes; keep JSON keys aligned with [GlobalSettingsApiFields].

[GlobalSettingsApiFields]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/network/contract/GlobalSettingsApiFields.kt
[GlobalSettingsResponse]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/network/response/GlobalSettingsResponse.kt
[UpdateGlobalSettingsRequest]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/network/request/UpdateGlobalSettingsRequest.kt
[GlobalSettingsRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/network/route/GlobalSettingsRoutes.kt
[AdminGlobalSettingsRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/network/route/AdminGlobalSettingsRoutes.kt
[SettingsWebSocketEventTypes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/network/contract/SettingsWebSocketEventTypes.kt
[FoundationJson]: ../common/src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/serialization/FoundationJson.kt
