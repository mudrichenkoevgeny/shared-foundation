# core/security

**Security-related shared contracts**: password policy **DTOs** and **domain** model, **validator** implementation, **mappers** between API and domain, **error code/args** strings, HTTP routes for security settings (public + admin), JSON field names, and **WebSocket event** type for security settings updates. On `commonMain` this module depends only on **kotlinx.serialization**; use [FoundationJson] from `core/common` in tests and in apps for consistent JSON. Does **not** implement authentication, Ktor plugins, or storage.

## What it provides

- **Error naming:** [SecurityErrorCodes], [SecurityErrorArgs] — machine-readable keys aligned with `ApiErrorResponse` usage in consumers.
- **API fields:** [SecurityApiFields] — snake_case JSON keys for password policy and nested settings.
- **Password policy wire types:** [PasswordPolicyResponse] — serializable rules (`minLength`, boolean flags, `commonPasswords` set).
- **Security settings:** [SecuritySettingsResponse], [UpdateSecuritySettingsRequest] — wrap `passwordPolicy`.
- **Domain + validation:** [PasswordPolicy], [PasswordPolicyFailReason], [PasswordPolicyValidatorResult].
- **Validator:** [PasswordPolicyValidator] (interface), [PasswordPolicyValidatorImpl] — validates a password against a [PasswordPolicy].
- **Mappers:** [PasswordPolicyMapper] — `PasswordPolicyResponse.toPasswordPolicy()`, `PasswordPolicy.toPasswordPolicyResponse()`.
- **HTTP routes:** [SecurityRoutes], [AdminSecurityRoutes] (base paths); [SecuritySettingsRoutes] (`GET` [SecuritySettingsResponse]); [AdminSecuritySettingsRoutes] (`PUT` [UpdateSecuritySettingsRequest]).
- **WebSocket:** [SecurityWebSocketEventTypes] — e.g. `SECURITY_SETTINGS_UPDATED` with payload described as [SecuritySettingsResponse] in KDoc.

## Usage

- Depend on `shared-foundation-core-security` (or the [shared-foundation-bom](../../bom) — see repository [README.md](../../../../README.md)).
- Serialize DTOs with the same `Json` as the rest of the stack ([FoundationJson]).
- On the server/client, use routes and field constants so paths and JSON keys stay aligned with this module.

[SecurityErrorCodes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/error/naming/SecurityErrorCodes.kt
[SecurityErrorArgs]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/error/naming/SecurityErrorArgs.kt
[SecurityApiFields]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/network/contract/SecurityApiFields.kt
[PasswordPolicyResponse]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/network/response/settings/PasswordPolicyResponse.kt
[SecuritySettingsResponse]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/network/response/settings/SecuritySettingsResponse.kt
[UpdateSecuritySettingsRequest]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/network/request/UpdateSecuritySettingsRequest.kt
[PasswordPolicy]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/passwordpolicy/model/PasswordPolicy.kt
[PasswordPolicyFailReason]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/passwordpolicy/model/PasswordPolicyFailReason.kt
[PasswordPolicyValidatorResult]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/passwordpolicy/model/PasswordPolicyValidatorResult.kt
[PasswordPolicyValidator]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/passwordpolicy/validator/PasswordPolicyValidator.kt
[PasswordPolicyValidatorImpl]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/passwordpolicy/validator/PasswordPolicyValidatorImpl.kt
[PasswordPolicyMapper]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/passwordpolicy/mapper/PasswordPolicyMapper.kt
[SecurityRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/network/route/SecurityRoutes.kt
[AdminSecurityRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/network/route/AdminSecurityRoutes.kt
[SecuritySettingsRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/network/route/settings/SecuritySettingsRoutes.kt
[AdminSecuritySettingsRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/network/route/settings/AdminSecuritySettingsRoutes.kt
[SecurityWebSocketEventTypes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/network/contract/SecurityWebSocketEventTypes.kt
[FoundationJson]: ../common/src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/serialization/FoundationJson.kt
