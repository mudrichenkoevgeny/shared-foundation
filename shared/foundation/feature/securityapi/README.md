# feature/securityapi

**Security settings HTTP routes** (open read + management update) and **permission** code for changing security settings. Uses types from [core/security](../../core/security/README.md) and [core/common](../../core/common/README.md).

Publishable artifact: **`shared-foundation-feature-securityapi`** (version aligned via [shared-foundation-bom](../../bom)).

## Core Capabilities

- **Open Security Settings:** Open read endpoint to retrieve [OpenSecuritySettingsPayload].
- **Management Security Settings:** Administrative update endpoint for security policies ([ManagementSecuritySettingsPayload]).
- **Permission Controls:** Granular update grant via [SecurityPermissionCode].
- **Audit Logging:** Audit taxonomy mapped via [SecurityAuditActionType] and [SecurityAuditResourceType].

## Component Index

### HTTP Routes

| Domain | Open Routes | Management Routes |
| :--- | :--- | :--- |
| **Security Settings** | [OpenSecuritySettingsRoutes] | [ManagementSecuritySettingsRoutes] |

### Permissions & Audit Taxonomy

| Category | Classes |
| :--- | :--- |
| **Permissions** | [SecurityPermissionCode] |
| **Audit Taxonomy** | [SecurityAuditActionType]<br>[SecurityAuditResourceType] |

---

[OpenSecuritySettingsRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/securityapi/network/route/open/security/settings/OpenSecuritySettingsRoutes.kt
[ManagementSecuritySettingsRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/securityapi/network/route/management/security/settings/ManagementSecuritySettingsRoutes.kt

[OpenSecuritySettingsPayload]: ../../core/security/src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/network/model/securitysettings/OpenSecuritySettingsPayload.kt
[ManagementSecuritySettingsPayload]: ../../core/security/src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/network/model/securitysettings/ManagementSecuritySettingsPayload.kt

[SecurityPermissionCode]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/securityapi/domain/permission/SecurityPermissionCode.kt
[SecurityAuditActionType]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/securityapi/domain/audit/action/SecurityAuditActionType.kt
[SecurityAuditResourceType]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/securityapi/domain/audit/resource/SecurityAuditResourceType.kt
