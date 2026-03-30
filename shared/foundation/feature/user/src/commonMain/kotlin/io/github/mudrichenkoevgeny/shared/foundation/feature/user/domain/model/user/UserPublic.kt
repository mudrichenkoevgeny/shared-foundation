package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.user.UserPublicPayload

/**
 * Public projection of a user: stable id, role, and account status.
 *
 * Consumes the same fields as [UserPublicPayload]; [UserRole] and [UserAccountStatus] replace
 * wire strings ([UserRole.serialName], [UserAccountStatus.serialName]).
 */
data class UserPublic(
    val id: UserId = UserId.generate(),
    val role: UserRole,
    val accountStatus: UserAccountStatus
)
