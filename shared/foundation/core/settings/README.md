# core/settings

**Global (system-wide) settings** contracts: **domain model**, **HTTP/WebSocket payloads**, JSON **field name** constants, **mappers**, and **WebSocket event type** strings. Depends on **core/common** and **core/audit** (Gradle) so generated artifacts stay aligned with the rest of the monorepo; source in this module focuses on settings shapes only.

**Open and management HTTP paths** are defined in **feature/settingsApi** ([README](../../feature/settingsApi/README.md)). This module does **not** ship `const val` full URLs for those APIs.

This module does **not** implement persistence, Ktor plugins, or authorization.

## What it provides

- **Field names:** [GlobalSettingsApiFields] — snake_case JSON keys (URLs, support email, etc.).
- **Domain:** [GlobalSettings] — non-wire model paired with the mapper.
- **Wire payload:** [GlobalSettingsPayload] — `@Serializable` body for reads and writes.
- **Mapper:** [GlobalSettingsMapper] — domain ↔ payload.
- **WebSocket:** [SettingsWebSocketEventTypes] — e.g. broadcast when global settings change (payload described in KDoc).

## Usage

- Depend on `shared-foundation-core-settings` (or [shared-foundation-bom](../../bom) — see repository [README.md](../../../../README.md)).
- Serialize with the same `Json` as the rest of the stack ([FoundationJson] from `core/common`).
- To compile against **route strings** and **management permission** constants, add `shared-foundation-feature-settingsApi`.

[GlobalSettingsApiFields]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/network/contract/GlobalSettingsApiFields.kt
[GlobalSettings]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/domain/model/globalsettings/GlobalSettings.kt
[GlobalSettingsPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/network/model/globalsettings/GlobalSettingsPayload.kt
[GlobalSettingsMapper]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/mapper/globalsettings/GlobalSettingsMapper.kt
[SettingsWebSocketEventTypes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/network/contract/SettingsWebSocketEventTypes.kt
[FoundationJson]: ../common/src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/serialization/FoundationJson.kt
