package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.data

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.token.SessionTokenPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.user.UserDetailsPayload
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO containing comprehensive authentication data, including user profile and session tokens.
 *
 * @property userDetailsPayload The [UserDetailsPayload] profile information of the authenticated user.
 * @property sessionTokenPayload The [SessionTokenPayload] set of tokens issued for the current session.
 */
@Serializable
data class AuthDataPayload(
    @SerialName(UserApiFields.USER)
    val userDetailsPayload: UserDetailsPayload,

    @SerialName(UserApiFields.SESSION_TOKEN)
    val sessionTokenPayload: SessionTokenPayload
)