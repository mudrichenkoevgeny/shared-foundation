package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.user

import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.FileUrls
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
 * @param nickname User handle or username.
 * @param displayName Human-readable display name.
 * @param avatarUrls Structured avatar image URL variants ([FileUrls]), or `null`.
 */
public fun createTestUserSummary(
    id: UserId = createTestUserId(),
    role: UserRole = UserRole.USER,
    accountStatus: UserAccountStatus = UserAccountStatus.ACTIVE,
    nickname: String? = "testuser",
    displayName: String? = "Test User",
    avatarUrls: FileUrls? = null
): UserSummary =
    UserSummary(
        id = id,
        role = role,
        accountStatus = accountStatus,
        nickname = nickname,
        displayName = displayName,
        avatarUrls = avatarUrls
    )

/**
 * Creates a list of test [UserSummary] instances with deterministic index-based UUIDs.
 *
 * @param count Number of elements to generate (defaults to 2).
 * @param generator Custom item builder receiving 0-based element index.
 */
public fun createTestUserSummaryList(
    count: Int = 2,
    generator: (index: Int) -> UserSummary = { index ->
        createTestUserSummary(
            id = createTestUserId(generateTestUuidString(index)),
            nickname = "testuser_${index + 1}",
            displayName = "Test User ${index + 1}"
        )
    }
): List<UserSummary> = createTestList(count, generator)
