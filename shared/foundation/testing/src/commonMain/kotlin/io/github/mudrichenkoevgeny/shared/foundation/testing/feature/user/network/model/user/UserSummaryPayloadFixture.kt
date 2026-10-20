package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.model.user

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.user.UserSummaryPayload
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.createTestList
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.generateTestUuidString
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.user.TEST_USER_ID

/**
 * Creates a test [UserSummaryPayload] wire object for unit testing mapper logic.
 *
 * @param id Raw user ID string.
 * @param role Wire string representation of [UserRole].
 * @param accountStatus Wire string representation of [UserAccountStatus].
 */
fun createTestUserSummaryPayload(
    id: String = TEST_USER_ID,
    role: String = UserRole.USER.value,
    accountStatus: String = UserAccountStatus.ACTIVE.value
) = UserSummaryPayload(
    id = id,
    role = role,
    accountStatus = accountStatus
)

/**
 * Creates a list of test [UserSummaryPayload] wire objects with deterministic raw UUIDs.
 *
 * @param count Number of elements to generate (defaults to 2).
 * @param generator Custom item builder receiving 0-based element index.
 */
fun createTestUserSummaryPayloadList(
    count: Int = 2,
    generator: (index: Int) -> UserSummaryPayload = { index ->
        createTestUserSummaryPayload(
            id = generateTestUuidString(index),
            role = UserRole.USER.value,
            accountStatus = UserAccountStatus.ACTIVE.value
        )
    }
): List<UserSummaryPayload> = createTestList(count, generator)
