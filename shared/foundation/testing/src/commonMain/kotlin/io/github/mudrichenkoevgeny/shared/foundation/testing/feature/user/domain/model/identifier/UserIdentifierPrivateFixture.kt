package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.identifier

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierPrivate
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_EMAIL
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_TIME_MILLIS
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.createTestList
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.generateTestUuidString
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.user.createTestUserId
import kotlin.time.Instant

/**
 * Creates a test [UserIdentifierPrivate] instance with default mock properties for unit testing.
 *
 * @param id Unique identifier ID ([UserIdentifierId]).
 * @param userId Owning user ID ([UserId]).
 * @param userAuthProvider Provider category ([UserAuthProvider]).
 * @param identifier Raw or masked credential identifier.
 * @param displayName Non-null display name.
 * @param externalProviderEmail External email address if available.
 * @param isSensitiveValuesMasked Whether sensitive fields are masked.
 * @param createdAt Creation timestamp.
 * @param updatedAt Modification timestamp.
 */
fun createTestUserIdentifierPrivate(
    id: UserIdentifierId = createTestUserIdentifierId(),
    userId: UserId = createTestUserId(),
    userAuthProvider: UserAuthProvider = UserAuthProvider.EMAIL,
    identifier: String = TEST_EMAIL,
    displayName: String = "Test User",
    externalProviderEmail: String? = null,
    isSensitiveValuesMasked: Boolean = false,
    createdAt: Instant = Instant.fromEpochMilliseconds(TEST_TIME_MILLIS),
    updatedAt: Instant? = Instant.fromEpochMilliseconds(TEST_TIME_MILLIS)
) = UserIdentifierPrivate(
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
 * Creates a list of test [UserIdentifierPrivate] instances with deterministic index-based UUIDs,
 * alternating providers, and time shifts.
 *
 * @param count Number of elements to generate (defaults to 2).
 * @param generator Custom item builder receiving 0-based element index.
 */
fun createTestUserIdentifierPrivateList(
    count: Int = 2,
    generator: (index: Int) -> UserIdentifierPrivate = { index ->
        val createdAtMs = TEST_TIME_MILLIS + (index * 60_000L)
        val isEmail = index % 2 == 0
        createTestUserIdentifierPrivate(
            id = createTestUserIdentifierId(generateTestUuidString(index = index, group = 1)),
            userId = createTestUserId(generateTestUuidString(index = index, group = 2)),
            userAuthProvider = if (isEmail) UserAuthProvider.EMAIL else UserAuthProvider.GOOGLE,
            identifier = if (isEmail) "user${index + 1}@example.com" else "+1555000000${index + 1}",
            displayName = if (isEmail) "Email Identifier" else "Google Account",
            createdAt = Instant.fromEpochMilliseconds(createdAtMs),
            updatedAt = Instant.fromEpochMilliseconds(createdAtMs + 10_000L)
        )
    }
): List<UserIdentifierPrivate> = createTestList(count, generator)
