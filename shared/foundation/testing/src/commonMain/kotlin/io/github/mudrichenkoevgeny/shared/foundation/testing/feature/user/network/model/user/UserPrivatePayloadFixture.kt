package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.model.user

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.accountlockout.AccountLockoutType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.user.UserPrivatePayload
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_TIME_MILLIS
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.user.TEST_USER_ID

fun createTestUserPrivatePayload(
    id: String = TEST_USER_ID,
    role: String = UserRole.USER.value,
    accountStatus: String = UserAccountStatus.ACTIVE.value,
    accountStatusOnRestore: String? = null,
    authorityLevel: Int = 10,
    permissionCodes: Set<String> = emptySet(),
    isTotpEnabled: Boolean = false,
    lastLoginAt: Long? = TEST_TIME_MILLIS,
    lastActiveAt: Long? = TEST_TIME_MILLIS,
    createdAt: Long = TEST_TIME_MILLIS,
    updatedAt: Long? = TEST_TIME_MILLIS,
    scheduledPermanentDeletionAt: Long? = null,
    lockoutType: String = AccountLockoutType.NONE.value,
    temporaryLockoutUntil: Long? = null
) = UserPrivatePayload(
    id = id,
    role = role,
    accountStatus = accountStatus,
    accountStatusOnRestore = accountStatusOnRestore,
    authorityLevel = authorityLevel,
    permissionCodes = permissionCodes,
    isTotpEnabled = isTotpEnabled,
    lastLoginAt = lastLoginAt,
    lastActiveAt = lastActiveAt,
    createdAt = createdAt,
    updatedAt = updatedAt,
    scheduledPermanentDeletionAt = scheduledPermanentDeletionAt,
    lockoutType = lockoutType,
    temporaryLockoutUntil = temporaryLockoutUntil
)
