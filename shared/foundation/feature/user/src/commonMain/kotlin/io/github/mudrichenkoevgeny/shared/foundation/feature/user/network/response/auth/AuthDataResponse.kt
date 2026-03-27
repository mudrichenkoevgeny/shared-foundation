package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.response.auth

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.response.token.SessionTokenResponse
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.response.user.UserDetailsResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO containing comprehensive authentication data, including user profile and session tokens.
 *
 * @property userDetailsResponse The [UserDetailsResponse] profile information of the authenticated user.
 * @property sessionTokenResponse The [SessionTokenResponse] set of tokens issued for the current session.
 */
@Serializable
data class AuthDataResponse(
    @SerialName(UserApiFields.USER)
    val userDetailsResponse: UserDetailsResponse,

    @SerialName(UserApiFields.SESSION_TOKEN)
    val sessionTokenResponse: SessionTokenResponse
)