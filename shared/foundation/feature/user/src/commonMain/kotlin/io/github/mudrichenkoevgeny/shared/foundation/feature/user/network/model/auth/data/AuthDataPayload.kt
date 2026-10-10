package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.data

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.token.SessionTokenPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.user.UserPrivatePayload
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO containing comprehensive authentication data, including user private profile and session tokens.
 *
 * @property userPrivatePayload The [UserPrivatePayload] profile information of the authenticated user.
 * @property sessionTokenPayload The [SessionTokenPayload] set of tokens issued for the current session.
 */
@Serializable
data class AuthDataPayload(
    @SerialName(UserApiFields.USER)
    val userPrivatePayload: UserPrivatePayload,

    @SerialName(UserApiFields.SESSION_TOKEN)
    val sessionTokenPayload: SessionTokenPayload
)
