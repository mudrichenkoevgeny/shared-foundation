# core/common

Cross-cutting **shared contracts** for backend and client: **kotlinx.serialization** defaults, **HTTP/WebSocket JSON field names and headers**, **API error wire shape** (`code` / `args` string constants), **WebSocket frame and handshake payloads**, shared **domain models** (client info, permissions, listing), and **JVM retention annotations** for request validation hints. This module does **not** pull in other in-repo `feature/*` modules. It does **not** include Ktor client/server wiring, UI, or localized error text — consumers (e.g. [KMP Platform SDK](https://github.com/mudrichenkoevgeny/kmp-platform-sdk), backend SDK) integrate runtime behavior on top of these types.

## What it provides

- **Serialization:** [FoundationJson] — shared `Json` configuration (`ignoreUnknownKeys`, `encodeDefaults`, etc.) for encoding/decoding DTOs in this library and dependent modules.
- **API errors (wire):** [ApiErrorResponse] with snake_case JSON keys via [CommonApiFields]; machine-readable **codes** and **args** keys in [CommonErrorCodes] and [CommonErrorArgs].
- **HTTP naming & routes:** [CommonApiFields] (JSON field names), [CommonHttpHeaders] (e.g. trace, client type, device, API version headers), and [ManagementRoutes] for platform-wide paths.
- **WebSocket — paths and envelopes:** [WebSocketContract] (realtime path string); [SocketFrame] (id, type, optional `JsonElement` payload, metadata, timestamp); [WebSocketInitializePayload] for client metadata on connect.
- **WebSocket — event and close strings:** [CommonWebSocketEventTypes] (e.g. PING, INITIALIZE), [CommonWebSocketCloseReasons].
- **Domain — Client:** [ClientType] serializable enum, [ClientInfo], and [ClientDeviceInfo] to describe connecting applications.
- **Domain — Permissions:** [PermissionCode] base type for shared permission constants.
- **Domain — Listing:** [PagedResult], [ListingParamNames], [SortOrder], and [CommonSortValues] for standardized pagination and sorting contracts.
- **Validation hints:** [RequiredField], [NotBlankStringField], [NotEmptyCollectionField] in [NetworkFieldAnnotations] (runtime interpretation is outside this module unless you add validators).

## Usage

- Add a dependency on the published artifact `shared-foundation-core-common` (or align versions via [shared-foundation-bom](../../bom) / your version catalog — see repository [README.md](../../../../README.md)).
- Use [FoundationJson] (or the same settings) when serializing types from this module so wire format stays consistent.
- Compare API error `code` values to [CommonErrorCodes] (and feature-specific `*ErrorCodes` in other modules); populate `args` using keys from [CommonErrorArgs] where applicable.
- Use [CommonApiFields] / [CommonHttpHeaders] in client and server code so JSON keys and header names stay aligned.
- For WebSocket framing, send/receive [SocketFrame] and use shared event type strings from [CommonWebSocketEventTypes] (feature modules may define additional types).

[FoundationJson]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/serialization/FoundationJson.kt
[ApiErrorResponse]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/error/model/ApiErrorResponse.kt
[CommonErrorCodes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/error/naming/CommonErrorCodes.kt
[CommonErrorArgs]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/error/naming/CommonErrorArgs.kt
[CommonApiFields]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/network/contract/CommonApiFields.kt
[CommonHttpHeaders]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/network/contract/CommonHttpHeaders.kt
[ManagementRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/network/route/management/ManagementRoutes.kt
[WebSocketContract]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/network/contract/WebSocketContract.kt
[SocketFrame]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/network/model/websocket/SocketFrame.kt
[WebSocketInitializePayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/network/model/websocket/WebSocketInitializePayload.kt
[CommonWebSocketEventTypes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/network/contract/CommonWebSocketEventTypes.kt
[CommonWebSocketCloseReasons]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/network/contract/CommonWebSocketCloseReasons.kt
[ClientType]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/domain/model/client/ClientType.kt
[ClientInfo]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/domain/model/client/ClientInfo.kt
[ClientDeviceInfo]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/domain/model/client/ClientDeviceInfo.kt
[PermissionCode]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/domain/model/permission/PermissionCode.kt
[PagedResult]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/domain/model/listing/PagedResult.kt
[ListingParamNames]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/domain/model/listing/ListingParamNames.kt
[SortOrder]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/domain/model/listing/SortOrder.kt
[CommonSortValues]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/domain/model/listing/CommonSortValues.kt
[RequiredField]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/validation/NetworkFieldAnnotations.kt
[NotBlankStringField]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/validation/NetworkFieldAnnotations.kt
[NotEmptyCollectionField]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/validation/NetworkFieldAnnotations.kt
[NetworkFieldAnnotations]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/validation/NetworkFieldAnnotations.kt
