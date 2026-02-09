package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.refreshtoken

import io.github.mudrichenkoevgeny.shared.foundation.core.common.validation.NotBlankStringField
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO for requesting a new access token using a valid refresh token.
 *
 * @property refreshToken The token used to obtain a new session without re-authenticating.
 */
@Serializable
data class RefreshTokenRequest(
    @NotBlankStringField
    @SerialName(UserApiFields.REFRESH_TOKEN)
    val refreshToken: String
)