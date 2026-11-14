package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.permission.PermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.accountlockout.AccountLockoutType
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.FileId
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.FileUrls
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
 * @property storageConfiguration Custom user storage configuration overrides and limits ([UserStorageConfiguration]), or `null`.
 * @property nickname Unique handle or username string (e.g. `@username`), or `null`.
 * @property displayName Human-readable display name string (e.g. `"Ivan Ivanov"`), or `null`.
 * @property avatarFileId Avatar image file identifier in storage ([FileId]), or `null`.
 * @property avatarUrls Structured avatar image URL variants ([FileUrls]), or `null`.
 * @property birthdate User birthdate timestamp ([Instant]), or `null`.
 */
public data class UserInternal(
    public val id: UserId = UserId.generate(),
    public val role: UserRole,
    public val accountStatus: UserAccountStatus,
    public val accountStatusOnRestore: UserAccountStatus?,
    public val authorityLevel: Int,
    public val permissionCodes: Set<PermissionCode>,
    public val isTotpEnabled: Boolean,
    public val lastLoginAt: Instant?,
    public val lastActiveAt: Instant?,
    public val createdAt: Instant,
    public val updatedAt: Instant?,
    public val scheduledPermanentDeletionAt: Instant?,
    public val lockoutType: AccountLockoutType,
    public val temporaryLockoutUntil: Instant?,
    public val storageConfiguration: UserStorageConfiguration?,
    public val nickname: String?,
    public val displayName: String?,
    val avatarFileId: FileId?,
    val avatarUrls: FileUrls?,
    public val birthdate: Instant?
)
