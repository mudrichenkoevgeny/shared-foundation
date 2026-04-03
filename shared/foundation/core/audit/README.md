# core/audit

**Audit log contracts** shared by backend and client: event **domain model** ([AuditEvent]), JSON **field names** ([AuditEventFields]), list **filter keys** ([AuditFilterValues]), and **management route path** constants. The module depends on **core/common** ([CommonApiFields], [PagedResult], [ListingParamNames], [ListingSortValues]) so payloads stay aligned with the rest of the platform.

This module does **not** define Ktor routes, repositories, or persistence — only types and `const val` paths for the wire contract.

## What it provides

- **Domain:** [AuditStatus] — serializable outcome enum (`SUCCESS`, `FAILED`, `DENIED`) for events; [AuditEvent] — one persisted audit event (`@Serializable`, shared for APIs, services, and clients).
- **Field names:** [AuditEventFields] — audit-specific snake_case keys; shared keys (e.g. `id`, `created_at`, `metadata`, `message`) use [CommonApiFields] on [AuditEvent].
- **List filters:** [AuditFilterValues.AuditEventFilterValues] — query parameter names for listing audit events (`actor_id`, `actor_type`, `actor_user_role`, `action`, `resource`, `resource_id`, `status`, `message`, …).
- **Routes:** [BaseAuditRoutes] — path segments under the management API; [ManagementAuditRoutes] documents the callable contract (e.g. paginated `GET` for events as [PagedResult] of [AuditEvent], with sort/filter semantics).

## Usage

- Add a dependency on the published artifact `shared-foundation-core-audit` (or align versions via [shared-foundation-bom](../../bom) / your version catalog — see repository [README.md](../../../../README.md)).
- Serialize and deserialize with the same `Json` settings as the rest of the stack (typically [FoundationJson] from core/common) so enums, maps, and defaults match other modules.

[FoundationJson]: ../common/src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/serialization/FoundationJson.kt
[CommonApiFields]: ../common/src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/network/contract/CommonApiFields.kt
[PagedResult]: ../common/src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/domain/model/listing/PagedResult.kt
[ListingParamNames]: ../common/src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/domain/model/listing/ListingParamNames.kt
[ListingSortValues]: ../common/src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/domain/model/listing/ListingSortValues.kt
[AuditStatus]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/domain/model/AuditStatus.kt
[AuditEvent]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/domain/model/AuditEvent.kt
[AuditEventFields]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/contract/AuditEventFields.kt
[AuditFilterValues.AuditEventFilterValues]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/domain/model/listing/AuditFilterValues.kt
[BaseAuditRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/network/route/base/BaseAuditRoutes.kt
[ManagementAuditRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/network/route/management/ManagementAuditRoutes.kt
