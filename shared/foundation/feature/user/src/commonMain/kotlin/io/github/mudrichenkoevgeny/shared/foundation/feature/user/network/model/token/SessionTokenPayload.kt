package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.token

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO containing authentication tokens and session metadata.
 *
 * @property accessToken The token used to authorize requests to protected resources.
 * @property refreshToken The token used to obtain a new access token when the current one expires.
 * @property expiresAt The timestamp when the access token expires, in milliseconds.
 * @property tokenType The type of the issued token (e.g. "Bearer").
 * @property sessionId The [UserSessionId] on the wire as a hex-dash string.
 * @property identifierId The [UserIdentifierId] on the wire as a hex-dash string.
 */
@Serializable
data class SessionTokenPayload(
    @SerialName(UserApiFields.ACCESS_TOKEN)
    val accessToken: String,

    @SerialName(UserApiFields.REFRESH_TOKEN)
    val refreshToken: String,

    @SerialName(UserApiFields.EXPIRES_AT)
    val expiresAt: Long,

    @SerialName(UserApiFields.TOKEN_TYPE)
    val tokenType: String,

    @SerialName(UserApiFields.SESSION_ID)
    val sessionId: String,

    @SerialName(UserApiFields.IDENTIFIER_ID)
    val identifierId: String
)