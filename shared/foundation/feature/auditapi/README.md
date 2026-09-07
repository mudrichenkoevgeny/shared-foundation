# feature/auditapi

**Management audit HTTP contract** and **permission codes** for the platform. Builds on [core/audit](../../core/audit/README.md) and [core/common](../../core/common/README.md): path constants, list/detail semantics, and typed [PermissionCode] values governing masked vs unmasked payloads.

Publishable artifact: **`shared-foundation-feature-auditapi`** (version aligned via [shared-foundation-bom](../../bom)).

## Core Capabilities

- **Management Audit Routes:** `GET` list and `GET` by ID routes ([ManagementAuditRoutes]) returning [AuditEventPayload].
- **Fine-Grained Permissions:** Scope-based read grants ([AuditPermissionCode]) for masked vs unmasked fields across `user`, `staff`, `admin`, `system`, and `service` actors.
- **Wire Taxonomy:** Resource enum segments ([CommonAuditResourceType]) for audit-management endpoints.

## Component Index

### HTTP Routes

| Domain | Open Routes | Management Routes |
| :--- | :--- | :--- |
| **Audit Events** | — | [ManagementAuditRoutes] |

### Permissions & Audit Taxonomy

| Category | Classes |
| :--- | :--- |
| **Permissions** | [AuditPermissionCode] |
| **Audit Taxonomy** | [CommonAuditResourceType] |

---

[ManagementAuditRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/auditapi/network/route/management/ManagementAuditRoutes.kt

[AuditPermissionCode]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/auditapi/domain/permissions/AuditPermissionCode.kt
[CommonAuditResourceType]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/auditapi/domain/audit/resource/CommonAuditResourceType.kt

[AuditEventPayload]: ../../core/audit/src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/network/model/event/AuditEventPayload.kt
[PermissionCode]: ../../core/common/src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/domain/model/permission/PermissionCode.kt
