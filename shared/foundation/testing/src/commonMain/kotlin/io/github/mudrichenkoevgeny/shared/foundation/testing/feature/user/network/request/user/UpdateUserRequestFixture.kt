package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.request.user

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.user.UpdateUserRequest

fun createTestUpdateUserRequest(
    accountStatus: String? = UserAccountStatus.ACTIVE.value,
    authorityLevel: Int? = 10,
    permissionCodes: Set<String>? = emptySet(),
    lockoutType: String? = null,
    temporaryLockoutUntil: Long? = null
) = UpdateUserRequest(
    accountStatus = accountStatus,
    authorityLevel = authorityLevel,
    permissionCodes = permissionCodes,
    lockoutType = lockoutType,
    temporaryLockoutUntil = temporaryLockoutUntil
)
