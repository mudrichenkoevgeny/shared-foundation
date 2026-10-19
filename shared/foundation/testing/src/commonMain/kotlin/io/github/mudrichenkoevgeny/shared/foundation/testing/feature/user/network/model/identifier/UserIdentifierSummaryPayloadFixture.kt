package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.model.identifier

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.identifier.UserIdentifierSummaryPayload
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_EMAIL
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.identifier.TEST_IDENTIFIER_ID
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.user.TEST_USER_ID

fun createTestUserIdentifierSummaryPayload(
    id: String = TEST_IDENTIFIER_ID,
    userId: String = TEST_USER_ID,
    userAuthProvider: String = UserAuthProvider.EMAIL.value,
    identifier: String = TEST_EMAIL,
    displayName: String = "Test User"
) = UserIdentifierSummaryPayload(
    id = id,
    userId = userId,
    userAuthProvider = userAuthProvider,
    identifier = identifier,
    displayName = displayName
)
