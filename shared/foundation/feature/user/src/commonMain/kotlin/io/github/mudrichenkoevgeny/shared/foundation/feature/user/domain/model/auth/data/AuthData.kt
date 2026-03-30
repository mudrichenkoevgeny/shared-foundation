package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.auth.data

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.token.SessionToken
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserDetails
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.data.AuthDataPayload

/**
 * Authenticated sign-in result: profile plus issued session tokens.
 *
 * Aligns with [AuthDataPayload] on the wire.
 */
data class AuthData(
    val userDetails: UserDetails,
    val sessionToken: SessionToken
)
