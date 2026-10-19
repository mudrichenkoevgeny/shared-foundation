package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.auth.data

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.auth.data.AuthData
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.token.SessionToken
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserPrivate
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.token.createTestSessionToken
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.user.createTestUserPrivate

fun createTestAuthData(
    userPrivate: UserPrivate = createTestUserPrivate(),
    sessionToken: SessionToken = createTestSessionToken()
) = AuthData(
    userPrivate = userPrivate,
    sessionToken = sessionToken
)
