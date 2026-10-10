package io.github.mudrichenkoevgeny.shared.foundation.feature.user.mapper.auth.data

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.auth.data.AuthData
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.mapper.token.toSessionToken
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.mapper.token.toSessionTokenPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.mapper.user.toUserPrivate
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.mapper.user.toUserPrivatePayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.data.AuthDataPayload

/**
 * Maps between [AuthDataPayload] and domain [AuthData].
 */

/**
 * Builds domain [AuthData] from [AuthDataPayload].
 */
fun AuthDataPayload.toAuthData(): AuthData =
    AuthData(
        userPrivate = userPrivatePayload.toUserPrivate(),
        sessionToken = sessionTokenPayload.toSessionToken()
    )

/**
 * Builds network [AuthDataPayload] from domain [AuthData].
 */
fun AuthData.toAuthDataPayload(): AuthDataPayload =
    AuthDataPayload(
        userPrivatePayload = userPrivate.toUserPrivatePayload(),
        sessionTokenPayload = sessionToken.toSessionTokenPayload()
    )
