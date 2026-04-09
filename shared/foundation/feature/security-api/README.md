# feature/security-api

**Security settings HTTP routes** (public read + management update) and **permission** code for changing security settings. Uses types from [core/security](../../core/security/README.md) and [core/common](../../core/common/README.md).

Publishable artifact: **`shared-foundation-feature-security-api`** (version aligned via [shared-foundation-bom](../../bom)).

## What it provides

- **Base path segments:** [BaseSecurityRoutes], [BaseSecuritySettingsRoutes].
- **Open API:** [OpenSecuritySettingsRoutes] — `GET` [SecuritySettingsPayload].
- **Management API:** [ManagementSecuritySettingsRoutes] — `PUT` with [SecuritySettingsPayload]; requires [SecurityPermissionCode] `SECURITY_SETTINGS_UPDATE` (see KDoc).
- **Permission:** [SecurityPermissionCode] — management security-settings update grant.
- **Audit taxonomy:** [SecurityAuditActionType], [SecurityAuditResourceType] — action/resource enum segments for security-related audit events.

## Usage

- Depend on this module for public/management URL constants and authorization codes alongside `shared-foundation-core-security` for validation and payloads.

[BaseSecurityRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/security/network/route/base/security/BaseSecurityRoutes.kt
[BaseSecuritySettingsRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/security/network/route/base/security/settings/BaseSecuritySettingsRoutes.kt
[OpenSecuritySettingsRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/security/network/route/open/security/settings/OpenSecuritySettingsRoutes.kt
[ManagementSecuritySettingsRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/security/network/route/management/security/settings/ManagementSecuritySettingsRoutes.kt
[SecuritySettingsPayload]: ../../core/security/src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/network/model/securitysettings/SecuritySettingsPayload.kt
[SecurityPermissionCode]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/security/domain/permission/SecurityPermissionCode.kt
[SecurityAuditActionType]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/security/domain/audit/action/SecurityAuditActionType.kt
[SecurityAuditResourceType]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/security/domain/audit/resource/SecurityAuditResourceType.kt
