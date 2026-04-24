package io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.verifytotp

import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.contract.SecurityApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.security.error.naming.SecurityErrorCodes
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO for Multi-Factor Authentication (MFA) verification.
 *
 * Used for:
 * 1. Activating TOTP after setup.
 * 2. Second-factor verification during login.
 * 3. Step-up authentication for sensitive operations.
 *
 * @property mfaToken The temporary challenge token identifying the current MFA session.
 * Usually provided by the backend in a previous response or error context.
 * @property code The verification secret provided by the user. Supports any format
 * (numeric TOTP codes of any length, alphanumeric recovery codes, etc.).
 */
@Serializable
data class VerifyTotpPayload(
    @SerialName(SecurityApiFields.MFA_TOKEN)
    val mfaToken: String,

    @SerialName(SecurityApiFields.TOTP_CODE)
    val code: String
)