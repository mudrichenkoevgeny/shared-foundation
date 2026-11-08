package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.model.user

import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.file.FileUrlsPayload
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
 * @param nickname User handle or username.
 * @param displayName Human-readable display name.
 * @param avatarUrls Structured avatar image URL variants ([FileUrlsPayload]), or `null`.
 */
public fun createTestUserSummaryPayload(
    id: String = TEST_USER_ID,
    role: String = UserRole.USER.value,
    accountStatus: String = UserAccountStatus.ACTIVE.value,
    nickname: String? = "testuser",
    displayName: String? = "Test User",
    avatarUrls: FileUrlsPayload? = null
): UserSummaryPayload = UserSummaryPayload(
    id = id,
    role = role,
    accountStatus = accountStatus,
    nickname = nickname,
    displayName = displayName,
    avatarUrls = avatarUrls
)

/**
 * Creates a list of test [UserSummaryPayload] wire objects with deterministic raw UUIDs.
 *
 * @param count Number of elements to generate (defaults to 2).
 * @param generator Custom item builder receiving 0-based element index.
 */
public fun createTestUserSummaryPayloadList(
    count: Int = 2,
    generator: (index: Int) -> UserSummaryPayload = { index ->
        createTestUserSummaryPayload(
            id = generateTestUuidString(index),
            role = UserRole.USER.value,
            accountStatus = UserAccountStatus.ACTIVE.value,
            nickname = "testuser_${index + 1}",
            displayName = "Test User ${index + 1}"
        )
    }
): List<UserSummaryPayload> = createTestList(count, generator)
