package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.auth.data

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.token.SessionToken
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserPrivate
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.data.AuthDataPayload

/**
 * Authenticated sign-in result: private profile plus issued session tokens.
 *
 * Aligns with [AuthDataPayload] on the wire.
 */
data class AuthData(
    val userPrivate: UserPrivate,
    val sessionToken: SessionToken
)
