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
data class LoginByEmailRequest(
    @NotBlankStringField
    @SerialName(UserApiFields.EMAIL)
    val email: String,

    @NotBlankStringField
    @SerialName(UserApiFields.PASSWORD)
    val password: String
)