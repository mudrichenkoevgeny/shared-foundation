package io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.totprecoverycodes

import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.contract.SecurityApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO containing a set of backup recovery codes for Multi-Factor Authentication.
 *
 * These codes allow account access if the primary TOTP device is lost.
 * Each code is typically intended for one-time use.
 *
 * @property codes A list of alphanumeric backup recovery codes.
 */
@Serializable
data class TotpRecoveryCodesPayload(
    @SerialName(SecurityApiFields.TOTP_RECOVERY_CODES)
    val codes: List<String>
)