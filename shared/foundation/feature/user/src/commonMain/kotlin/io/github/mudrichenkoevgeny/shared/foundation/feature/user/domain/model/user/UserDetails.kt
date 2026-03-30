package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission.UserPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.user.UserDetailsPayload
import kotlin.time.Instant

/**
 * Extended user profile for authenticated self-service and administrative flows.
 *
 * Mirrors [UserDetailsPayload]: role and account fields use domain enums; timestamps are
 * [Instant] values that correspond to epoch milliseconds on the wire.
 *
 * @property accountStatusBeforeDeletion Status before a deletion was requested; meaningful when
 *   [accountStatus] is [UserAccountStatus.PENDING_DELETION] (semantics are server-defined).
 */
data class UserDetails(
    val id: UserId = UserId.generate(),
    val role: UserRole,
    val accountStatus: UserAccountStatus,
    val accountStatusBeforeDeletion: UserAccountStatus,
    val permissions: Set<UserPermissionCode>,
    val lastLoginAt: Instant? = null,
    val lastActiveAt: Instant? = null,
    val createdAt: Instant,
    val updatedAt: Instant? = null,
    val scheduledPermanentDeletionAt: Instant? = null
)
