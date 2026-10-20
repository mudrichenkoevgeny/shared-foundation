package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.user

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserSummary
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.createTestList
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.generateTestUuidString

/**
 * Creates a test [UserSummary] instance with default mock properties for unit testing.
 *
 * @param id Unique user ID ([UserId]).
 * @param role Assigned access role ([UserRole]).
 * @param accountStatus Current account state ([UserAccountStatus]).
 */
fun createTestUserSummary(
    id: UserId = createTestUserId(),
    role: UserRole = UserRole.USER,
    accountStatus: UserAccountStatus = UserAccountStatus.ACTIVE
) = UserSummary(
    id = id,
    role = role,
    accountStatus = accountStatus
)

/**
 * Creates a list of test [UserSummary] instances with deterministic index-based UUIDs.
 *
 * @param count Number of elements to generate (defaults to 2).
 * @param generator Custom item builder receiving 0-based element index.
 */
fun createTestUserSummaryList(
    count: Int = 2,
    generator: (index: Int) -> UserSummary = { index ->
        createTestUserSummary(
            id = createTestUserId(generateTestUuidString(index))
        )
    }
): List<UserSummary> = createTestList(count, generator)
