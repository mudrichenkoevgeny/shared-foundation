package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.token

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserAuthSpec
import kotlin.time.Instant

/**
 * Pair of access/refresh tokens and session metadata representing an authenticated session on the client.
 *
 * @property accessToken Short-lived token used to authorize API calls.
 * @property refreshToken Long-lived token used to refresh [accessToken].
 * @property expiresAt Access token expiration instant.
 * @property tokenType Token type for the `Authorization` header (defaults to bearer).
 * @property sessionId Unique identifier of the authenticated session.
 * @property identifierId Unique identifier of the credential record used for login.
 */
data class SessionToken(
    val accessToken: AccessToken,
    val refreshToken: RefreshToken,
    val expiresAt: Instant,
    val tokenType: String = UserAuthSpec.TOKEN_TYPE_BEARER,
    val sessionId: UserSessionId,
    val identifierId: UserIdentifierId
)