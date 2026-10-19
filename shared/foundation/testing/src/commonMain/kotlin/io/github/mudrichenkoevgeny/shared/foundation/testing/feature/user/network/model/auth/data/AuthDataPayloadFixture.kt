package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.model.auth.data

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.data.AuthDataPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.token.SessionTokenPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.user.UserPrivatePayload
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.model.token.createTestSessionTokenPayload
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.model.user.createTestUserPrivatePayload

fun createTestAuthDataPayload(
    userPrivatePayload: UserPrivatePayload = createTestUserPrivatePayload(),
    sessionTokenPayload: SessionTokenPayload = createTestSessionTokenPayload()
) = AuthDataPayload(
    userPrivatePayload = userPrivatePayload,
    sessionTokenPayload = sessionTokenPayload
)
