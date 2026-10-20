package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.model.identifier

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.identifier.UserIdentifierPrivatePayload
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_EMAIL
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_TIME_MILLIS
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.createTestList
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.identifier.TEST_IDENTIFIER_ID
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.user.TEST_USER_ID

/**
 * Creates a test [UserIdentifierPrivatePayload] wire object for unit testing mapper logic.
 *
 * @param id Raw identifier ID string.
 * @param userId Raw user ID string.
 * @param userAuthProvider Wire string representation of [UserAuthProvider].
 * @param identifier Raw credential string.
 * @param displayName Non-null display name string.
 * @param externalProviderEmail External email if available.
 * @param isSensitiveValuesMasked Whether sensitive values are masked.
 * @param createdAt Creation epoch-millisecond timestamp.
 * @param updatedAt Modification epoch-millisecond timestamp.
 */
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

/**
 * Creates a list of test [UserIdentifierPrivatePayload] wire objects with deterministic raw IDs (`ident_1`, ...),
 * alternating providers, and time shifts.
 *
 * @param count Number of elements to generate (defaults to 2).
 * @param generator Custom item builder receiving 0-based element index.
 */
fun createTestUserIdentifierPrivatePayloadList(
    count: Int = 2,
    generator: (index: Int) -> UserIdentifierPrivatePayload = { index ->
        val createdAtMs = TEST_TIME_MILLIS + (index * 60_000L)
        val isEmail = index % 2 == 0
        createTestUserIdentifierPrivatePayload(
            id = "ident_${index + 1}",
            userId = "usr_${index + 1}",
            userAuthProvider = if (isEmail) UserAuthProvider.EMAIL.value else UserAuthProvider.GOOGLE.value,
            identifier = if (isEmail) "user${index + 1}@example.com" else "+1555000000${index + 1}",
            displayName = if (isEmail) "Email Identifier" else "Google Account",
            createdAt = createdAtMs,
            updatedAt = createdAtMs + 10_000L
        )
    }
): List<UserIdentifierPrivatePayload> = createTestList(count, generator)
