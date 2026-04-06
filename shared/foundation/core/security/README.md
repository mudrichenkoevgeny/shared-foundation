# core/security

**Security-related shared contracts:** password policy **domain** and **payloads**, **validator** (interface + default implementation), **mappers**, **error code/args** strings, **security settings** wire types, optional **audit metadata** value constants, and **WebSocket event** type for security settings updates. Depends on **core/common** and **core/audit** for shared listing/audit alignment.

**Open and management HTTP paths** for reading/updating security settings are in **feature/securityApi** ([README](../../feature/securityApi/README.md)). This module keeps policy, validation, and payload types only.

Does **not** implement authentication, Ktor wiring, or storage.

## What it provides

- **Error naming:** [SecurityErrorCodes], [SecurityErrorArgs].
- **API fields:** [SecurityApiFields] — snake_case JSON keys for password policy and nested settings.
- **Password policy:** [PasswordPolicy], [PasswordPolicyFailReason], [PasswordPolicyValidatorResult]; wire type [PasswordPolicyPayload].
- **Validator:** [PasswordPolicyValidator], [PasswordPolicyValidatorImpl].
- **Mappers:** [PasswordPolicyMapper], [SecuritySettingsMapper].
- **Security settings:** domain [SecuritySettings]; wire [SecuritySettingsPayload].
- **WebSocket:** [SecurityWebSocketEventTypes] — e.g. security settings updated (payload described in KDoc).
- **Audit metadata helpers:** [SecurityAuditMetadataDeniedReasonValues] — shared string values for denied-reason keys where applicable.

## Usage

- Depend on `shared-foundation-core-security` (or [shared-foundation-bom](../../bom) — see repository [README.md](../../../../README.md)).
- Serialize with [FoundationJson] from `core/common`.
- For **HTTP route constants** and **management permission** codes, add `shared-foundation-feature-securityApi`.

[SecurityErrorCodes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/error/naming/SecurityErrorCodes.kt
[SecurityErrorArgs]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/error/naming/SecurityErrorArgs.kt
[SecurityApiFields]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/network/contract/SecurityApiFields.kt
[PasswordPolicy]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/domain/model/passwordpolicy/PasswordPolicy.kt
[PasswordPolicyFailReason]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/domain/model/passwordpolicy/PasswordPolicyFailReason.kt
[PasswordPolicyValidatorResult]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/domain/model/passwordpolicy/PasswordPolicyValidatorResult.kt
[PasswordPolicyPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/network/model/passwordpolicy/PasswordPolicyPayload.kt
[PasswordPolicyValidator]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/passwordpolicy/validator/PasswordPolicyValidator.kt
[PasswordPolicyValidatorImpl]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/passwordpolicy/validator/PasswordPolicyValidatorImpl.kt
[PasswordPolicyMapper]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/mapper/passwordpolicy/PasswordPolicyMapper.kt
[SecuritySettingsMapper]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/mapper/securitysettings/SecuritySettingsMapper.kt
[SecuritySettings]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/domain/model/securitysettings/SecuritySettings.kt
[SecuritySettingsPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/network/model/securitysettings/SecuritySettingsPayload.kt
[SecurityWebSocketEventTypes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/network/contract/SecurityWebSocketEventTypes.kt
[SecurityAuditMetadataDeniedReasonValues]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/domain/model/audit/metadata/SecurityAuditMetadataDeniedReasonValues.kt
[FoundationJson]: ../common/src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/serialization/FoundationJson.kt
