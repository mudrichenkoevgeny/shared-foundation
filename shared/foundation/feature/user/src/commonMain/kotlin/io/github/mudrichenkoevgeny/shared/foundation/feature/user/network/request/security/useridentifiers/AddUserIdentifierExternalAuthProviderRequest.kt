package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.security.useridentifiers

import io.github.mudrichenkoevgeny.shared.foundation.core.common.validation.NotBlankStringField
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.constants.UserApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddUserIdentifierExternalAuthProviderRequest(
    @NotBlankStringField
    @SerialName(UserApiFields.AUTH_PROVIDER)
    val authProvider: String,

    @NotBlankStringField
    @SerialName(UserApiFields.TOKEN)
    val token: String
)