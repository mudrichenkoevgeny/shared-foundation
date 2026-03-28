# core/audit

**Audit API contracts** shared by backend and client: event **DTOs**, JSON **field and query parameter names**, and **management route path** constants. The module depends on **core/common** ([CommonApiFields], [PagedResponse], pagination/sort query names) so audit payloads stay aligned with the rest of the platform.

This module does **not** define Ktor routes, repositories, or persistence — only types and `const val` paths for the wire contract.

## What it provides

- **Domain:** [AuditStatus] — serializable outcome enum (`SUCCESS`, `FAILED`, `DENIED`) for event payloads.
- **JSON field names:** [AuditApiFields] — audit-specific snake_case keys; shared keys (e.g. `id`, `created_at`, `metadata`, `message`) use [CommonApiFields] in [AuditEventResponse].
- **List filters:** [AuditApiQueryParams.AuditEventQueryParams] — query parameter names for listing audit events (`actor_id`, `action`, `resource`, `resource_id`, `status`, `message`, …).
- **Responses:** [AuditEventResponse] — one persisted audit event on the wire.
- **Routes:** [BaseAuditRoutes] — path segments under the management API; [ManagementAuditRoutes] documents the callable contract (e.g. paginated `GET` for events as [PagedResponse] of [AuditEventResponse], with sort/filter semantics).

## Usage

- Add a dependency on the published artifact `shared-foundation-core-audit` (or align versions via [shared-foundation-bom](../../bom) / your version catalog — see repository [README.md](../../../../README.md)).
- Serialize and deserialize with the same `Json` settings as the rest of the stack (typically [FoundationJson] from core/common) so enums, maps, and defaults match other modules.

[FoundationJson]: ../common/src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/serialization/FoundationJson.kt
[CommonApiFields]: ../common/src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/network/contract/CommonApiFields.kt
[PagedResponse]: ../common/src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/network/model/listing/PagedResponse.kt
[AuditStatus]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/domain/model/AuditStatus.kt
[AuditApiFields]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/network/contract/AuditApiFields.kt
[AuditApiQueryParams.AuditEventQueryParams]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/network/contract/AuditApiQueryParams.kt
[AuditEventResponse]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/network/response/AuditEventResponse.kt
[BaseAuditRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/network/route/base/BaseAuditRoutes.kt
[ManagementAuditRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/network/route/management/ManagementAuditRoutes.kt
