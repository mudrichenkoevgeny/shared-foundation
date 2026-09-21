package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.permission.PermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.accountlockout.AccountLockoutType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.user.UserDetailsPayload
import kotlin.time.Instant

/**
 * Extended user profile for authenticated self-service and administrative flows.
 *
 * Mirrors [UserDetailsPayload]: role and account fields use domain enums; timestamps are
 * [Instant] values that correspond to epoch milliseconds on the wire.
 *
 * @property id Unique user id ([UserId]); defaults to [UserId.generate] when creating new instances.
 * @property role Access level assigned to the user ([UserRole]).
 * @property accountStatus Current account state ([UserAccountStatus]).
 * @property accountStatusOnRestore Target account status assigned to the user upon restoring the account / canceling
 *   deletion procedure; meaningful when [accountStatus] is [UserAccountStatus.PENDING_DELETION].
 * @property authorityLevel Hierarchical weight of the user (0-100) used for access control.
 * @property permissionCodes Explicit permission codes assigned to the user ([PermissionCode]).
 * @property isTotpEnabled Whether Time-based One-Time Password (TOTP) two-factor authentication is active.
 * @property lastLoginAt Timestamp of the most recent successful authentication ([Instant]), or `null` if never logged in.
 * @property lastActiveAt Timestamp of last recorded user activity ([Instant]), or `null` if inactive.
 * @property createdAt Timestamp of account creation ([Instant]).
 * @property updatedAt Timestamp of last profile modification ([Instant]), or `null` if never modified.
 * @property scheduledPermanentDeletionAt Scheduled permanent removal timestamp ([Instant]) when pending deletion, or `null`.
 * @property lockoutType Category of account lockout ([AccountLockoutType]).
 * @property temporaryLockoutUntil Timestamp until which the account is temporarily locked out ([Instant]), or `null` if not locked out.
 */
data class UserDetails(
    val id: UserId = UserId.generate(),
    val role: UserRole,
    val accountStatus: UserAccountStatus,
    val accountStatusOnRestore: UserAccountStatus?,
    val authorityLevel: Int,
    val permissionCodes: Set<PermissionCode>,
    val isTotpEnabled: Boolean,
    val lastLoginAt: Instant?,
    val lastActiveAt: Instant?,
    val createdAt: Instant,
    val updatedAt: Instant?,
    val scheduledPermanentDeletionAt: Instant?,
    val lockoutType: AccountLockoutType,
    val temporaryLockoutUntil: Instant?
)
