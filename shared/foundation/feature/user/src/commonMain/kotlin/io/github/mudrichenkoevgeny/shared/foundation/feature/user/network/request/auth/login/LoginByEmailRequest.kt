package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.login

import io.github.mudrichenkoevgeny.shared.foundation.core.common.validation.NotBlankStringField
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.constants.UserApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginByEmailRequest(
    @NotBlankStringField
    @SerialName(UserApiFields.EMAIL)
    val email: String,

    @NotBlankStringField
    @SerialName(UserApiFields.PASSWORD)
    val password: String
)