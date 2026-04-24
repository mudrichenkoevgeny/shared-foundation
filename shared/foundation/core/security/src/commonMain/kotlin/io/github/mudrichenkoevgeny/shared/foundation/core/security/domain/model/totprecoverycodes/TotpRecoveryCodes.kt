package io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.totprecoverycodes

/**
 * Domain model representing a collection of backup recovery codes.
 *
 * Used to manage secondary access credentials that bypass standard TOTP
 * verification in case of device loss.
 *
 * @property codes A list of available backup codes.
 */
data class TotpRecoveryCodes(
    val codes: List<String>
)