package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.request.user

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.user.UpdateUserRequest

/**
 * Creates a test [UpdateUserRequest] request object instance.
 *
 * @param accountStatus New account status wire string ([UserAccountStatus]).
 * @param authorityLevel New authority level integer.
 * @param permissionCodes New set of permission code wire strings.
 * @param lockoutType New lockout type wire string.
 * @param temporaryLockoutUntil Timestamp until temporary lockout expires.
 */
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
