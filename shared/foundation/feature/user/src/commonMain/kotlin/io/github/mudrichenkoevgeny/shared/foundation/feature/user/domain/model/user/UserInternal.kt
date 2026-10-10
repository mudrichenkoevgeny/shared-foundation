package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.permission.PermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.accountlockout.AccountLockoutType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import kotlin.time.Instant

/**
 * Internal user row for server-side storage and business logic.
 *
 * Adds database row modification timestamp [updatedAt] to [UserPrivate]-equivalent fields.
 * Internal fields must not be exposed directly via public API payloads.
 *
 * @property id Unique user id ([UserId]); defaults to [UserId.generate] when creating new instances.
 * @property role Access level assigned to the user ([UserRole]).
 * @property accountStatus Current account state ([UserAccountStatus]).
 * @property accountStatusOnRestore Target account status assigned upon restoring account ([UserAccountStatus]).
 * @property authorityLevel Hierarchical weight of the user (0-100) used for access control.
 * @property permissionCodes Explicit permission codes assigned to the user ([PermissionCode]).
 * @property isTotpEnabled Whether Time-based One-Time Password (TOTP) two-factor authentication is active.
 * @property lastLoginAt Timestamp of the most recent successful authentication ([Instant]), or `null`.
 * @property lastActiveAt Timestamp of last recorded user activity ([Instant]), or `null`.
 * @property createdAt Timestamp of account creation ([Instant]).
 * @property updatedAt Timestamp of last database row update ([Instant]), or `null`.
 * @property scheduledPermanentDeletionAt Scheduled permanent removal timestamp ([Instant]), or `null`.
 * @property lockoutType Category of account lockout ([AccountLockoutType]).
 * @property temporaryLockoutUntil Timestamp until which the account is temporarily locked out ([Instant]), or `null`.
 */
data class UserInternal(
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
