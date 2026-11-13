package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.login

import io.github.mudrichenkoevgeny.shared.foundation.core.common.validation.NotBlankStringField
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO for user authentication using email and password.
 *
 * @property email The user's registered email address.
 * @property password The user's account password.
 */
@Serializable
public data class LoginByEmailRequest(
    @NotBlankStringField
    @SerialName(UserApiFields.EMAIL)
    public val email: String,
    @NotBlankStringField
    @SerialName(UserApiFields.PASSWORD)
    public val password: String
)
