package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.model.identifier

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.identifier.UserIdentifierPrivatePayload
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_TIME_MILLIS
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_EMAIL
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.identifier.TEST_IDENTIFIER_ID
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.user.TEST_USER_ID

fun createTestUserIdentifierPrivatePayload(
    id: String = TEST_IDENTIFIER_ID,
    userId: String = TEST_USER_ID,
    userAuthProvider: String = UserAuthProvider.EMAIL.value,
    identifier: String = TEST_EMAIL,
    displayName: String = "Test User",
    externalProviderEmail: String? = null,
    isSensitiveValuesMasked: Boolean = false,
    createdAt: Long = TEST_TIME_MILLIS,
    updatedAt: Long? = TEST_TIME_MILLIS
) = UserIdentifierPrivatePayload(
    id = id,
    userId = userId,
    userAuthProvider = userAuthProvider,
    identifier = identifier,
    displayName = displayName,
    externalProviderEmail = externalProviderEmail,
    isSensitiveValuesMasked = isSensitiveValuesMasked,
    createdAt = createdAt,
    updatedAt = updatedAt
)
