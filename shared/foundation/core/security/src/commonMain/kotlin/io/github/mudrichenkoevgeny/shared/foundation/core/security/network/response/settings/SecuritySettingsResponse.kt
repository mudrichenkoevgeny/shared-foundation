package io.github.mudrichenkoevgeny.shared.foundation.core.security.network.response.settings

import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.contract.SecurityApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO representing the security-specific configuration of the user module.
 *
 * @property passwordPolicy The set of rules and requirements for password strength and validation.
 */
@Serializable
data class SecuritySettingsResponse(
    @SerialName(SecurityApiFields.PASSWORD_POLICY)
    val passwordPolicy: PasswordPolicyResponse
)