package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.identifier

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierSummary
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_EMAIL
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.createTestList
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.generateTestUuidString
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.user.createTestUserId

/**
 * Creates a test [UserIdentifierSummary] instance with default mock properties for unit testing.
 *
 * @param id Unique identifier ID ([UserIdentifierId]).
 * @param userId Owning user ID ([UserId]).
 * @param userAuthProvider Provider category ([UserAuthProvider]).
 * @param identifier Raw or masked credential identifier.
 * @param displayName Non-null display name.
 */
fun createTestUserIdentifierSummary(
    id: UserIdentifierId = createTestUserIdentifierId(),
    userId: UserId = createTestUserId(),
    userAuthProvider: UserAuthProvider = UserAuthProvider.EMAIL,
    identifier: String = TEST_EMAIL,
    displayName: String = "Test User"
) = UserIdentifierSummary(
    id = id,
    userId = userId,
    userAuthProvider = userAuthProvider,
    identifier = identifier,
    displayName = displayName
)

/**
 * Creates a list of test [UserIdentifierSummary] instances with deterministic index-based UUIDs,
 * and alternating auth providers.
 *
 * @param count Number of elements to generate (defaults to 2).
 * @param generator Custom item builder receiving 0-based element index.
 */
fun createTestUserIdentifierSummaryList(
    count: Int = 2,
    generator: (index: Int) -> UserIdentifierSummary = { index ->
        val isEmail = index % 2 == 0
        createTestUserIdentifierSummary(
            id = createTestUserIdentifierId(generateTestUuidString(index = index, group = 1)),
            userId = createTestUserId(generateTestUuidString(index = index, group = 2)),
            userAuthProvider = if (isEmail) UserAuthProvider.EMAIL else UserAuthProvider.GOOGLE,
            identifier = if (isEmail) "user${index + 1}@example.com" else "+1555000000${index + 1}",
            displayName = if (isEmail) "Email Identifier" else "Google Account"
        )
    }
): List<UserIdentifierSummary> = createTestList(count, generator)
