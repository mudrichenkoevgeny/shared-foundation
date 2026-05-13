# feature/securityapi

**Security settings HTTP routes** (public read + management update) and **permission** code for changing security settings. Uses types from [core/security](../../core/security/README.md) and [core/common](../../core/common/README.md).

Publishable artifact: **`shared-foundation-feature-securityapi`** (version aligned via [shared-foundation-bom](../../bom)).

## What it provides

- **Base path segments:** [BaseSecurityRoutes] (`/security`) and [BaseSecuritySettingsRoutes] (`/security/settings`).
- **Open API:** [OpenSecuritySettingsRoutes] — public `GET` route to retrieve [SecuritySettingsPayload].
- **Management API:** [ManagementSecuritySettingsRoutes] — `PUT` route for global security policies; defines strict authorization (Staff/Admin roles) and audit requirements.
- **Permission:** [SecurityPermissionCode] — contains `SECURITY_SETTINGS_UPDATE` (`security.settings.update`) grant.
- **Audit taxonomy:** [SecurityAuditActionType], [SecurityAuditResourceType] — enums for security-related audit events.

## Usage

- Depend on this module for public/management URL constants and authorization codes alongside `shared-foundation-core-security` for validation and payloads.

[BaseSecurityRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/securityapi/api/network/route/base/security/BaseSecurityRoutes.kt
[BaseSecuritySettingsRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/securityapi/api/network/route/base/security/settings/BaseSecuritySettingsRoutes.kt
[OpenSecuritySettingsRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/securityapi/api/network/route/open/security/settings/OpenSecuritySettingsRoutes.kt
[ManagementSecuritySettingsRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/securityapi/api/network/route/management/security/settings/ManagementSecuritySettingsRoutes.kt
[SecuritySettingsPayload]: ../../core/security/src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/network/model/securitysettings/SecuritySettingsPayload.kt
[SecurityPermissionCode]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/securityapi/api/domain/permission/SecurityPermissionCode.kt
[SecurityAuditActionType]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/securityapi/api/domain/audit/action/SecurityAuditActionType.kt
[SecurityAuditResourceType]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/securityapi/api/domain/audit/resource/SecurityAuditResourceType.kt