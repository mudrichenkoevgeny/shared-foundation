# feature/auditapi

**Management audit HTTP contract** and **permission codes** for the platform. Builds on [core/audit](../../core/audit/README.md) and [core/common](../../core/common/README.md): path `const val`s, KDoc for list/detail semantics, and typed [PermissionCode] values that govern masked vs unmasked payloads.

Publishable artifact: **`shared-foundation-feature-auditapi`** (version aligned via [shared-foundation-bom](../../bom)).

## What it provides

- **Base paths:** [BaseAuditRoutes] — shared path segments under the management API prefix (e.g., `/management/audit/events`).
- **Management routes:** [ManagementAuditRoutes] — `GET` list and `GET` by ID; defines detailed KDoc for pagination, sorting, and filtering using types from `core/audit`.
- **Permissions:** [AuditPermissionCode] — fine-grained read grants based on actor scope (`user`, `staff`, `admin`, `system`, `service`) and visibility (`masked` vs `unmasked`).
- **Wire taxonomy:** [CommonAuditResourceType] — resource enum segments specifically for the audit-management endpoints (`audit_event`, `audit_events`).

## Usage

- Add the dependency next to `shared-foundation-core-audit` when the app implements or calls the management audit HTTP API.
- Enforce [AuditPermissionCode] in your authorization layer to control field visibility on [AuditEventPayload].
- Keep JSON keys aligned with `AuditEventFields` from `core/audit`.

[PermissionCode]: ../../core/common/src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/domain/model/permission/PermissionCode.kt
[BaseAuditRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/auditapi/api/network/route/base/BaseAuditRoutes.kt
[ManagementAuditRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/auditapi/api/network/route/management/ManagementAuditRoutes.kt
[AuditPermissionCode]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/auditapi/api/domain/permissions/AuditPermissionCode.kt
[CommonAuditResourceType]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/auditapi/api/domain/audit/resource/CommonAuditResourceType.kt
[AuditEventPayload]: ../../core/audit/src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/network/model/event/AuditEventPayload.kt