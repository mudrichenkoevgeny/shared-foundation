# core/audit

**Audit log wire and domain contracts** shared by backend and client: serialized **event models**, JSON **field names**, list **filters/sort** keys, path **parameter** names, and **mappers** between domain and API payloads. Depends on **core/common** ([CommonApiFields], [PagedResult], [ListingParamNames]) so listings stay consistent across the platform.

HTTP **URL constants** and **feature permission codes** live in **feature/audit/api** ([README](../../feature/audit/api/README.md)); this module stays free of those so server and client can depend only on payloads and parsing logic if needed.

## What it provides

- **Domain Models:**
    - [AuditEvent]: Central record capturing *who* ([AuditActorType]), *did what* ([AuditActionType]), *to what* ([AuditResourceType]), and the *outcome* ([AuditStatus]).
    - [AuditEventMetadata]: Key-value pairs for rich diagnostics (e.g., HTTP headers, trace IDs).
    - [AuditValueSensitivity]: Defines redaction policies (Full mask, Partial mask, IP-address) for sensitive data in resource IDs or metadata.
- **Common Metadata:**
    - [CommonAuditMetadataKey]: Standardized keys for client diagnostics (Device Info, User-Agent, IP, Trace ID) and error tracking (`error_id`, `denied_reason`).
- **Wire Payloads:** [AuditEventPayload], [AuditEventMetadataPayload] for consistent JSON serialization.
- **Mappers:**
    - [AuditEventMapper], [AuditEventMetadataMapper]: Domain ↔ Payload conversion.
    - [ClientInfo.toAuditMetadata()][ClientInfoAuditMapper]: Extension to automatically extract audit metadata from standard `ClientInfo`.
- **List & Search Contract:**
    - [AuditFilterValues.AuditEventFilterValues]: Constants for query parameters (actor_id, action, status, etc.).
    - [AuditSortValues]: Supported sorting keys (primarily `created_at`).
- **Extensibility:**
    - [AuditActionType] & [AuditResourceType]: Interface-based contracts allowing feature-modules to define their own domain-specific actions and resources.
    - **Composite Parsers:** [CompositeAuditActionTypeParser], [CompositeAuditResourceTypeParser], [CompositeAuditMetadataKeyParser] — tools to resolve these extensible types from wire strings.

## Usage

- **Dependency:** Add `shared-foundation-core-audit` via BOM.
- **Enrichment:** Use `ClientInfo.toAuditMetadata()` to automatically populate technical context (IP, Device, OS) from the request context.
- **Naming:** Use `AuditApiPaths.EVENT_ID` for consistent path parameter naming in REST controllers.
- **Redaction:** Use `CommonAuditMetadataKey` or custom keys with appropriate `AuditValueSensitivity` to ensure PII data is handled correctly by the UI or exporters.

[FoundationJson]: ../common/src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/serialization/FoundationJson.kt
[CommonApiFields]: ../common/src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/network/contract/CommonApiFields.kt
[PagedResult]: ../common/src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/domain/model/listing/PagedResult.kt
[ListingParamNames]: ../common/src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/domain/model/listing/ListingParamNames.kt
[AuditStatus]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/domain/model/status/AuditStatus.kt
[AuditEvent]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/domain/model/event/AuditEvent.kt
[AuditActorType]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/domain/model/actor/AuditActorType.kt
[AuditActionType]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/domain/model/action/AuditActionType.kt
[AuditResourceType]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/domain/model/resource/AuditResourceType.kt
[AuditValueSensitivity]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/domain/model/event/AuditValueSensitivity.kt
[AuditEventMetadata]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/domain/model/metadata/AuditEventMetadata.kt
[CommonAuditMetadataKey]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/domain/model/metadata/CommonAuditMetadataKey.kt
[AuditEventPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/network/model/event/AuditEventPayload.kt
[AuditEventMetadataPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/network/model/event/AuditEventMetadataPayload.kt
[AuditApiPaths]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/network/contract/AuditApiPaths.kt
[AuditFilterValues.AuditEventFilterValues]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/domain/model/listing/AuditFilterValues.kt
[AuditSortValues]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/domain/model/listing/AuditSortValues.kt
[AuditEventMapper]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/mapper/audit/AuditEventMapper.kt
[ClientInfoAuditMapper]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/mapper/audit/ClientInfoAuditMapper.kt
[CompositeAuditActionTypeParser]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/domain/model/action/CompositeAuditActionTypeParser.kt
[CompositeAuditResourceTypeParser]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/domain/model/resource/CompositeAuditResourceTypeParser.kt
[CompositeAuditMetadataKeyParser]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/domain/model/metadata/CompositeAuditMetadataKeyParser.kt