package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.navigation.screen.management.user.detail

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.user.detail.UserDetailParams
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.user.TEST_USER_ID

/**
 * Creates a test [UserDetailParams] navigation params instance.
 *
 * @param userId Target user ID string.
 */
fun createTestUserDetailParams(
    userId: String = TEST_USER_ID
): UserDetailParams = UserDetailParams(
    userId = userId
)
