package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.model.identifier

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.identifier.UserIdentifierSummaryPayload
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_EMAIL
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.createTestList
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.generateTestUuidString
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.identifier.TEST_IDENTIFIER_ID
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.user.TEST_USER_ID

/**
 * Creates a test [UserIdentifierSummaryPayload] wire object for unit testing mapper logic.
 *
 * @param id Raw identifier ID string.
 * @param userId Raw user ID string.
 * @param userAuthProvider Wire string representation of [UserAuthProvider].
 * @param identifier Raw credential string.
 * @param displayName Non-null display name string.
 */
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

/**
 * Creates a list of test [UserIdentifierSummaryPayload] wire objects with deterministic raw UUIDs,
 * and alternating auth providers.
 *
 * @param count Number of elements to generate (defaults to 2).
 * @param generator Custom item builder receiving 0-based element index.
 */
fun createTestUserIdentifierSummaryPayloadList(
    count: Int = 2,
    generator: (index: Int) -> UserIdentifierSummaryPayload = { index ->
        val isEmail = index % 2 == 0
        createTestUserIdentifierSummaryPayload(
            id = generateTestUuidString(index = index, group = 1),
            userId = generateTestUuidString(index = index, group = 2),
            userAuthProvider = if (isEmail) UserAuthProvider.EMAIL.value else UserAuthProvider.GOOGLE.value,
            identifier = if (isEmail) "user${index + 1}@example.com" else "+1555000000${index + 1}",
            displayName = if (isEmail) "Email Identifier" else "Google Account"
        )
    }
): List<UserIdentifierSummaryPayload> = createTestList(count, generator)
