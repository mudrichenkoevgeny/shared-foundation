package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.navigation.screen.management.user.globallist

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingDefaultValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.accountlockout.AccountLockoutType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSortBy
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.user.globallist.ManagementUserListParams

/**
 * Creates a test [ManagementUserListParams] navigation params instance.
 *
 * @param pageNumber One-based page index.
 * @param pageSize Maximum items per page.
 * @param sortBy Sort field ([UserSortBy]).
 * @param sortOrder Sort direction ([SortOrder]).
 * @param userIds Filter user IDs list.
 * @param userRoles Filter user access roles list ([UserRole]).
 * @param excludeUserRoles Exclude user access roles list ([UserRole]).
 * @param accountStatuses Filter account status list ([UserAccountStatus]).
 * @param excludeAccountStatuses Exclude account status list ([UserAccountStatus]).
 * @param accountStatusesOnRestore Filter account status on restore list ([UserAccountStatus]).
 * @param excludeAccountStatusesOnRestore Exclude account status on restore list ([UserAccountStatus]).
 * @param isAccountStatusOnRestoreSet Filter by target account status on restore presence.
 * @param authorityLevelFrom Lower bound for authority level filter.
 * @param authorityLevelTo Upper bound for authority level filter.
 * @param permissionCodes Included permission codes list (OR).
 * @param excludePermissionCodes Excluded permission codes list (NOT IN).
 * @param isTotpEnabled Filter by TOTP 2FA enabled flag.
 * @param lastLoginAtFrom Lower bound last login timestamp.
 * @param lastLoginAtTo Upper bound last login timestamp.
 * @param isLastLoginAtSet Filter by login activity presence.
 * @param lastActiveAtFrom Lower bound last active timestamp.
 * @param lastActiveAtTo Upper bound last active timestamp.
 * @param isLastActiveAtSet Filter by user activity presence.
 * @param createdAtFrom Lower bound creation timestamp.
 * @param createdAtTo Upper bound creation timestamp.
 * @param updatedAtFrom Lower bound update timestamp.
 * @param updatedAtTo Upper bound update timestamp.
 * @param isUpdatedAtSet Filter by update presence.
 * @param scheduledPermanentDeletionAtFrom Lower bound scheduled deletion timestamp.
 * @param scheduledPermanentDeletionAtTo Upper bound scheduled deletion timestamp.
 * @param isScheduledPermanentDeletionAtSet Filter by scheduled deletion presence.
 * @param accountLockoutTypes Filter account lockout type list ([AccountLockoutType]).
 * @param excludeAccountLockoutTypes Exclude account lockout type list ([AccountLockoutType]).
 * @param temporaryLockoutUntilFrom Lower bound temporary lockout expiration timestamp.
 * @param temporaryLockoutUntilTo Upper bound temporary lockout expiration timestamp.
 * @param isTemporaryLockoutUntilSet Filter by temporary lockout presence.
 * @param storageQuotaBytesFrom Lower bound custom storage quota in bytes.
 * @param storageQuotaBytesTo Upper bound custom storage quota in bytes.
 * @param isStorageQuotaBytesSet Filter by custom storage quota presence.
 * @param storageMaxUploadSizeBytesFrom Lower bound custom max upload size in bytes.
 * @param storageMaxUploadSizeBytesTo Upper bound custom max upload size in bytes.
 * @param isStorageMaxUploadSizeBytesSet Filter by custom max upload size presence.
 * @param storageMaxPendingUploadsFrom Lower bound custom max pending uploads count.
 * @param storageMaxPendingUploadsTo Upper bound custom max pending uploads count.
 * @param isStorageMaxPendingUploadsSet Filter by custom max pending uploads presence.
 * @param isStorageConfigurationSet Filter by custom storage configuration presence.
 * @param nickname Exact nickname handle.
 * @param isNicknameSet Filter by nickname presence.
 * @param displayName Exact display name.
 * @param isDisplayNameSet Filter by display name presence.
 * @param isAvatarSet Filter by avatar image presence.
 * @param birthdateFrom Lower bound user birthdate.
 * @param birthdateTo Upper bound user birthdate.
 * @param isBirthdateSet Filter by birthdate presence.
 * @param searchQuery Free-text search query string.
 */
fun createTestManagementUserListParams(
    pageNumber: Int = ListingDefaultValues.Pagination.PAGE_NUMBER,
    pageSize: Int = ListingDefaultValues.Pagination.PAGE_SIZE,
    sortBy: UserSortBy = UserSortBy.DEFAULT,
    sortOrder: SortOrder = SortOrder.DEFAULT,
    userIds: List<String> = emptyList(),
    userRoles: List<UserRole> = emptyList(),
    excludeUserRoles: List<UserRole> = emptyList(),
    accountStatuses: List<UserAccountStatus> = emptyList(),
    excludeAccountStatuses: List<UserAccountStatus> = emptyList(),
    accountStatusesOnRestore: List<UserAccountStatus> = emptyList(),
    excludeAccountStatusesOnRestore: List<UserAccountStatus> = emptyList(),
    isAccountStatusOnRestoreSet: Boolean? = null,
    authorityLevelFrom: Int? = null,
    authorityLevelTo: Int? = null,
    permissionCodes: List<String> = emptyList(),
    excludePermissionCodes: List<String> = emptyList(),
    isTotpEnabled: Boolean? = null,
    lastLoginAtFrom: String? = null,
    lastLoginAtTo: String? = null,
    isLastLoginAtSet: Boolean? = null,
    lastActiveAtFrom: String? = null,
    lastActiveAtTo: String? = null,
    isLastActiveAtSet: Boolean? = null,
    createdAtFrom: String? = null,
    createdAtTo: String? = null,
    updatedAtFrom: String? = null,
    updatedAtTo: String? = null,
    isUpdatedAtSet: Boolean? = null,
    scheduledPermanentDeletionAtFrom: String? = null,
    scheduledPermanentDeletionAtTo: String? = null,
    isScheduledPermanentDeletionAtSet: Boolean? = null,
    accountLockoutTypes: List<AccountLockoutType> = emptyList(),
    excludeAccountLockoutTypes: List<AccountLockoutType> = emptyList(),
    temporaryLockoutUntilFrom: String? = null,
    temporaryLockoutUntilTo: String? = null,
    isTemporaryLockoutUntilSet: Boolean? = null,
    storageQuotaBytesFrom: Long? = null,
    storageQuotaBytesTo: Long? = null,
    isStorageQuotaBytesSet: Boolean? = null,
    storageMaxUploadSizeBytesFrom: Long? = null,
    storageMaxUploadSizeBytesTo: Long? = null,
    isStorageMaxUploadSizeBytesSet: Boolean? = null,
    storageMaxPendingUploadsFrom: Long? = null,
    storageMaxPendingUploadsTo: Long? = null,
    isStorageMaxPendingUploadsSet: Boolean? = null,
    isStorageConfigurationSet: Boolean? = null,
    nickname: String? = null,
    isNicknameSet: Boolean? = null,
    displayName: String? = null,
    isDisplayNameSet: Boolean? = null,
    isAvatarSet: Boolean? = null,
    birthdateFrom: String? = null,
    birthdateTo: String? = null,
    isBirthdateSet: Boolean? = null,
    searchQuery: String? = null
): ManagementUserListParams = ManagementUserListParams(
    pageNumber = pageNumber,
    pageSize = pageSize,
    sortBy = sortBy,
    sortOrder = sortOrder,
    userIds = userIds,
    userRoles = userRoles,
    excludeUserRoles = excludeUserRoles,
    accountStatuses = accountStatuses,
    excludeAccountStatuses = excludeAccountStatuses,
    accountStatusesOnRestore = accountStatusesOnRestore,
    excludeAccountStatusesOnRestore = excludeAccountStatusesOnRestore,
    isAccountStatusOnRestoreSet = isAccountStatusOnRestoreSet,
    authorityLevelFrom = authorityLevelFrom,
    authorityLevelTo = authorityLevelTo,
    permissionCodes = permissionCodes,
    excludePermissionCodes = excludePermissionCodes,
    isTotpEnabled = isTotpEnabled,
    lastLoginAtFrom = lastLoginAtFrom,
    lastLoginAtTo = lastLoginAtTo,
    isLastLoginAtSet = isLastLoginAtSet,
    lastActiveAtFrom = lastActiveAtFrom,
    lastActiveAtTo = lastActiveAtTo,
    isLastActiveAtSet = isLastActiveAtSet,
    createdAtFrom = createdAtFrom,
    createdAtTo = createdAtTo,
    updatedAtFrom = updatedAtFrom,
    updatedAtTo = updatedAtTo,
    isUpdatedAtSet = isUpdatedAtSet,
    scheduledPermanentDeletionAtFrom = scheduledPermanentDeletionAtFrom,
    scheduledPermanentDeletionAtTo = scheduledPermanentDeletionAtTo,
    isScheduledPermanentDeletionAtSet = isScheduledPermanentDeletionAtSet,
    accountLockoutTypes = accountLockoutTypes,
    excludeAccountLockoutTypes = excludeAccountLockoutTypes,
    temporaryLockoutUntilFrom = temporaryLockoutUntilFrom,
    temporaryLockoutUntilTo = temporaryLockoutUntilTo,
    isTemporaryLockoutUntilSet = isTemporaryLockoutUntilSet,
    storageQuotaBytesFrom = storageQuotaBytesFrom,
    storageQuotaBytesTo = storageQuotaBytesTo,
    isStorageQuotaBytesSet = isStorageQuotaBytesSet,
    storageMaxUploadSizeBytesFrom = storageMaxUploadSizeBytesFrom,
    storageMaxUploadSizeBytesTo = storageMaxUploadSizeBytesTo,
    isStorageMaxUploadSizeBytesSet = isStorageMaxUploadSizeBytesSet,
    storageMaxPendingUploadsFrom = storageMaxPendingUploadsFrom,
    storageMaxPendingUploadsTo = storageMaxPendingUploadsTo,
    isStorageMaxPendingUploadsSet = isStorageMaxPendingUploadsSet,
    isStorageConfigurationSet = isStorageConfigurationSet,
    nickname = nickname,
    isNicknameSet = isNicknameSet,
    displayName = displayName,
    isDisplayNameSet = isDisplayNameSet,
    isAvatarSet = isAvatarSet,
    birthdateFrom = birthdateFrom,
    birthdateTo = birthdateTo,
    isBirthdateSet = isBirthdateSet,
    searchQuery = searchQuery
)
