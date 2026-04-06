# core/audit

**Audit log wire and domain contracts** shared by backend and client: serialized **event models**, JSON **field names**, list **filters/sort** keys, path **parameter** names, and **mappers** between domain and API payloads. Depends on **core/common** ([CommonApiFields], [PagedResult], [ListingParamNames]) so listings stay consistent across the platform.

HTTP **URL constants** and **feature permission codes** live in **feature/auditApi** ([README](../../feature/auditApi/README.md)); this module stays free of those so server and client can depend only on payloads and parsing logic if needed.

This module does **not** define Ktor routes, repositories, or persistence — only types, parsers, and `const val` names for the wire contract.

## What it provides

- **Domain:** [AuditStatus]; [AuditEvent] with [AuditEventId]; actor, action, and resource types; [AuditEventMetadata] and [AuditMetadataKey] helpers; [AuditValueSensitivity] for metadata classification.
- **Wire payloads:** [AuditEventPayload], [AuditEventMetadataPayload].
- **Field names:** [AuditEventFields]; shared keys on events use [CommonApiFields] where applicable.
- **Path naming:** [AuditApiPaths] — e.g. the event id path segment key for detail URLs.
- **List contract:** [AuditFilterValues.AuditEventFilterValues], [AuditSortValues] — query parameter names for paginated audit listings.
- **Mappers:** [AuditEventMapper], [AuditEventMetadataMapper] — domain ↔ payload.
- **Composite parsers:** [CompositeAuditActionTypeParser], [CompositeAuditResourceTypeParser], [CompositeAuditMetadataKeyParser] — resolve feature-supplied enum segments from wire strings.

## Usage

- Add `shared-foundation-core-audit` (versions via [shared-foundation-bom](../../bom) — see repository [README.md](../../../../README.md)).
- Use the same `Json` as the rest of the stack ([FoundationJson] from core/common).
- For management **routes** and **permissions**, also depend on `shared-foundation-feature-auditApi`.

[FoundationJson]: ../common/src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/serialization/FoundationJson.kt
[CommonApiFields]: ../common/src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/network/contract/CommonApiFields.kt
[PagedResult]: ../common/src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/domain/model/listing/PagedResult.kt
[ListingParamNames]: ../common/src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/domain/model/listing/ListingParamNames.kt
[AuditStatus]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/domain/model/status/AuditStatus.kt
[AuditEvent]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/domain/model/event/AuditEvent.kt
[AuditEventId]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/domain/model/event/AuditEventId.kt
[AuditEventMetadata]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/domain/model/metadata/AuditEventMetadata.kt
[AuditMetadataKey]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/domain/model/metadata/AuditMetadataKey.kt
[AuditValueSensitivity]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/domain/model/event/AuditValueSensitivity.kt
[AuditEventPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/network/model/event/AuditEventPayload.kt
[AuditEventMetadataPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/network/model/event/AuditEventMetadataPayload.kt
[AuditEventFields]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/contract/AuditEventFields.kt
[AuditApiPaths]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/network/contract/AuditApiPaths.kt
[AuditFilterValues.AuditEventFilterValues]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/domain/model/listing/AuditFilterValues.kt
[AuditSortValues]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/domain/model/listing/AuditSortValues.kt
[AuditEventMapper]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/mapper/audit/AuditEventMapper.kt
[AuditEventMetadataMapper]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/mapper/audit/AuditEventMetadataMapper.kt
[CompositeAuditActionTypeParser]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/domain/model/action/CompositeAuditActionTypeParser.kt
[CompositeAuditResourceTypeParser]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/domain/model/resource/CompositeAuditResourceTypeParser.kt
[CompositeAuditMetadataKeyParser]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/domain/model/metadata/CompositeAuditMetadataKeyParser.kt
