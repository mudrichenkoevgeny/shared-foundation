package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.model.user

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.user.UserSummaryPayload
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.user.TEST_USER_ID

fun createTestUserSummaryPayload(
    id: String = TEST_USER_ID,
    role: String = UserRole.USER.value,
    accountStatus: String = UserAccountStatus.ACTIVE.value
) = UserSummaryPayload(
    id = id,
    role = role,
    accountStatus = accountStatus
)
