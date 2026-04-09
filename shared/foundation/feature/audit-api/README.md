# feature/audit-api

**Management audit HTTP contract** and **permission codes** for the platform. Builds on [core/audit](../../core/audit/README.md) and [core/common](../../core/common/README.md): path `const val`s, KDoc for list/detail semantics, and typed [PermissionCode] values that govern masked vs unmasked payloads.

Publishable artifact: **`shared-foundation-feature-audit-api`** (version aligned via [shared-foundation-bom](../../bom)).

## What it provides

- **Base paths:** [BaseAuditRoutes] — shared segments under the management API (no authorization KDoc here; see management object).
- **Management routes:** [ManagementAuditRoutes] — `GET` list + `GET` by id; pagination, sort, and filters reference types from core/audit.
- **Permissions:** [AuditPermissionCode] — fine-grained audit read grants (actor scope × masked/unmasked); servers map these to field visibility on [AuditEventPayload].
- **Wire taxonomy:** [CommonAuditResourceType] — resource enum segments for audit-list/detail events in this feature; feature-specific `AuditActionType` enums live in their owning modules.

## Usage

- Add the dependency next to `shared-foundation-core-audit` when the app implements or calls the management audit HTTP API.
- Enforce [AuditPermissionCode] in your authorization layer; keep JSON keys aligned with [AuditEventFields] from core/audit.

[PermissionCode]: ../../core/common/src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/domain/model/permission/PermissionCode.kt
[BaseAuditRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/audit/network/route/base/BaseAuditRoutes.kt
[ManagementAuditRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/audit/network/route/management/ManagementAuditRoutes.kt
[AuditPermissionCode]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/audit/domain/permissions/AuditPermissionCode.kt
[CommonAuditResourceType]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/audit/domain/audit/resource/CommonAuditResourceType.kt
[AuditEventPayload]: ../../core/audit/src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/network/model/event/AuditEventPayload.kt
[AuditEventFields]: ../../core/audit/src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/contract/AuditEventFields.kt
