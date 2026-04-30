# core/security

**Security-related shared contracts:** password policy **domain** and **payloads**, **MFA (TOTP)** setup and verification, **cryptographic value objects**, **mappers**, **error code/args** strings, and **security settings**. Depends on **core/common** and **core/audit** for shared listing and audit alignment.

**Open and management HTTP paths** for security operations are in **feature/security/api** ([README](../../feature/security/api/README.md)). This module keeps policy, validation, and payload types only.

## What it provides

- **Multi-Factor Authentication (MFA/TOTP):**
    - **Setup:** [TotpSetup] and [TotpSetupPayload] — provides `secret_key` (Base32) and `otpauth_url` for QR-code provisioning.
    - **Verification:** [VerifyTotp] and [VerifyTotpPayload] — unified DTO for activating TOTP, login verification, and step-up authentication using a `mfa_token`.
    - **Recovery:** [TotpRecoveryCodes] and [TotpRecoveryCodesPayload] — management of one-time backup codes.
- **Security Settings & Policies:**
    - [SecuritySettings] & [SecuritySettingsPayload]: Unified configuration for session validity (standard and management), MFA token TTL, and nested policies.
    - [PasswordPolicy] & [PasswordPolicyPayload]: Rules for strength validation (length, character sets, common passwords).
    - [OtpConfirmation] & [OtpConfirmationPayload]: Configuration for OTP generation (TTL, symbols, retry cooling-off).
- **Cryptographic Value Objects:**
    - [DecryptedString] & [EncryptedString]: `inline value` classes for type-safe handling of raw and encrypted data.
    - [PasswordHash]: Dedicated type for hashed password storage.
- **Error Naming & Audit:**
    - [SecurityErrorCodes] & [SecurityErrorArgs]: Specialized codes for MFA failures, password policy violations, and rate limits.
    - [SecurityAuditMetadataDeniedReasonValues]: Standardized string constants for [CommonAuditMetadataKey.DENIED_REASON] in audit logs.
- **Validation & Mapping:**
    - [PasswordPolicyValidator]: Interface and default implementation for enforcing password rules.
    - **Mappers:** Extensive set of mappers for Domain ↔ Payload conversion (e.g., `toVerifyTotpPayload`, `toSecuritySettings`).

## Usage

- **Dependency:** Add `shared-foundation-core-security` via BOM.
- **MFA Flow:**
  1. Obtain `TotpSetupPayload` to show a QR code.
  2. Submit the code via `VerifyTotpPayload` along with the provided `mfa_token`.
- **Validation:** Use `PasswordPolicyValidatorImpl` to check user passwords against the current `PasswordPolicy`.
- **Serialization:** All payloads use `snake_case` naming convention via [SecurityApiFields] and are compatible with [FoundationJson].

[SecurityErrorCodes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/error/naming/SecurityErrorCodes.kt
[SecurityErrorArgs]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/error/naming/SecurityErrorArgs.kt
[SecurityApiFields]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/network/contract/SecurityApiFields.kt
[PasswordPolicy]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/domain/model/passwordpolicy/PasswordPolicy.kt
[PasswordPolicyPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/network/model/passwordpolicy/PasswordPolicyPayload.kt
[PasswordPolicyValidator]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/passwordpolicy/validator/PasswordPolicyValidator.kt
[SecuritySettings]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/domain/model/securitysettings/SecuritySettings.kt
[SecuritySettingsPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/network/model/securitysettings/SecuritySettingsPayload.kt
[TotpSetup]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/domain/model/totpsetup/TotpSetup.kt
[TotpSetupPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/network/model/totpsetup/TotpSetupPayload.kt
[VerifyTotp]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/domain/model/verifytotp/VerifyTotp.kt
[VerifyTotpPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/network/model/verifytotp/VerifyTotpPayload.kt
[TotpRecoveryCodes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/domain/model/totprecoverycodes/TotpRecoveryCodes.kt
[TotpRecoveryCodesPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/network/model/totprecoverycodes/TotpRecoveryCodesPayload.kt
[OtpConfirmation]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/domain/model/otpconfirmation/OtpConfirmation.kt
[OtpConfirmationPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/network/model/otpconfirmation/OtpConfirmationPayload.kt
[DecryptedString]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/domain/model/crypt/DecryptedString.kt
[EncryptedString]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/domain/model/crypt/EncryptedString.kt
[PasswordHash]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/domain/model/passwordhash/PasswordHash.kt
[SecurityAuditMetadataDeniedReasonValues]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/domain/model/audit/metadata/SecurityAuditMetadataDeniedReasonValues.kt
[FoundationJson]: ../common/src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/serialization/FoundationJson.kt