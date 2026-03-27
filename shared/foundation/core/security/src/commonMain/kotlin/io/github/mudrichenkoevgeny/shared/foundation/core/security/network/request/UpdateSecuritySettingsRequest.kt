package io.github.mudrichenkoevgeny.shared.foundation.core.security.network.request

import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.contract.SecurityApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.response.settings.PasswordPolicyResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO representing the request to update security-specific configuration.
 *
 * @property passwordPolicy The new set of rules and requirements for password strength and validation to be applied.
 */
@Serializable
data class UpdateSecuritySettingsRequest(
    @SerialName(SecurityApiFields.PASSWORD_POLICY)
    val passwordPolicy: PasswordPolicyResponse
)