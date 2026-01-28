package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.refreshtoken

import io.github.mudrichenkoevgeny.shared.foundation.core.common.validation.NotBlankStringField
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.constants.UserApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenRequest(
    @NotBlankStringField
    @SerialName(UserApiFields.REFRESH_TOKEN)
    val refreshToken: String
)