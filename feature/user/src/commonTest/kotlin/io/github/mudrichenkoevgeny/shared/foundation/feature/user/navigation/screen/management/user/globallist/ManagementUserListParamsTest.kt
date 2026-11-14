package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.user.globallist

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.accountlockout.AccountLockoutType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSortBy
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import kotlin.test.Test
import kotlin.test.assertEquals

class ManagementUserListParamsTest {
    @Test
    fun `parse extracts management user list params correctly`() {
        val raw = mapOf(
            ListingParamNames.Pagination.PAGE_NUMBER to "2",
            ListingParamNames.Pagination.PAGE_SIZE to "50",
            ListingParamNames.Sort.SORT_BY to "last_login_at",
            ListingParamNames.Sort.SORT_ORDER to "asc",
            UserFilterValues.USER_ID to listOf("usr_123", "usr_456"),
            UserFilterValues.ROLE to listOf("admin", "staff"),
            UserFilterValues.EXCLUDE_ROLE to listOf("user"),
            UserFilterValues.ACCOUNT_STATUS to listOf("active"),
            UserFilterValues.EXCLUDE_ACCOUNT_STATUS to listOf("banned"),
            UserFilterValues.ACCOUNT_STATUS_ON_RESTORE to listOf("active"),
            UserFilterValues.EXCLUDE_ACCOUNT_STATUS_ON_RESTORE to listOf("read_only"),
            UserFilterValues.IS_ACCOUNT_STATUS_ON_RESTORE_SET to "true",
            UserFilterValues.AUTHORITY_LEVEL_FROM to "10",
            UserFilterValues.AUTHORITY_LEVEL_TO to "90",
            UserFilterValues.PERMISSION_CODES to listOf("user.get"),
            UserFilterValues.EXCLUDE_PERMISSION_CODES to listOf("user.delete"),
            UserFilterValues.IS_TOTP_ENABLED to "true",
            UserFilterValues.LAST_LOGIN_AT_FROM to "2026-01-01T00:00:00Z",
            UserFilterValues.LAST_LOGIN_AT_TO to "2026-03-01T00:00:00Z",
            UserFilterValues.IS_LAST_LOGIN_AT_SET to "true",
            UserFilterValues.LAST_ACTIVE_AT_FROM to "2026-01-02T00:00:00Z",
            UserFilterValues.LAST_ACTIVE_AT_TO to "2026-03-02T00:00:00Z",
            UserFilterValues.IS_LAST_ACTIVE_AT_SET to "true",
            UserFilterValues.CREATED_AT_FROM to "2026-01-03T00:00:00Z",
            UserFilterValues.CREATED_AT_TO to "2026-03-03T00:00:00Z",
            UserFilterValues.UPDATED_AT_FROM to "2026-01-04T00:00:00Z",
            UserFilterValues.UPDATED_AT_TO to "2026-03-04T00:00:00Z",
            UserFilterValues.IS_UPDATED_AT_SET to "true",
            UserFilterValues.SCHEDULED_PERMANENT_DELETION_AT_FROM to "2026-01-05T00:00:00Z",
            UserFilterValues.SCHEDULED_PERMANENT_DELETION_AT_TO to "2026-03-05T00:00:00Z",
            UserFilterValues.IS_SCHEDULED_PERMANENT_DELETION_AT_SET to "true",
            UserFilterValues.ACCOUNT_LOCKOUT_TYPE to listOf("temporary"),
            UserFilterValues.EXCLUDE_ACCOUNT_LOCKOUT_TYPE to listOf("indefinite"),
            UserFilterValues.TEMPORARY_LOCKOUT_UNTIL_FROM to "2026-01-06T00:00:00Z",
            UserFilterValues.TEMPORARY_LOCKOUT_UNTIL_TO to "2026-03-06T00:00:00Z",
            UserFilterValues.IS_TEMPORARY_LOCKOUT_UNTIL_SET to "true",
            UserFilterValues.STORAGE_QUOTA_BYTES_FROM to "1048576",
            UserFilterValues.STORAGE_QUOTA_BYTES_TO to "104857600",
            UserFilterValues.IS_STORAGE_QUOTA_BYTES_SET to "true",
            UserFilterValues.STORAGE_MAX_UPLOAD_SIZE_BYTES_FROM to "1024",
            UserFilterValues.STORAGE_MAX_UPLOAD_SIZE_BYTES_TO to "2048",
            UserFilterValues.IS_STORAGE_MAX_UPLOAD_SIZE_BYTES_SET to "true",
            UserFilterValues.STORAGE_MAX_PENDING_UPLOADS_FROM to "1",
            UserFilterValues.STORAGE_MAX_PENDING_UPLOADS_TO to "10",
            UserFilterValues.IS_STORAGE_MAX_PENDING_UPLOADS_SET to "true",
            UserFilterValues.IS_STORAGE_CONFIGURATION_SET to "true",
            UserFilterValues.NICKNAME to "admin_user",
            UserFilterValues.IS_NICKNAME_SET to "true",
            UserFilterValues.DISPLAY_NAME to "Admin User",
            UserFilterValues.IS_DISPLAY_NAME_SET to "true",
            UserFilterValues.IS_AVATAR_SET to "true",
            UserFilterValues.BIRTHDATE_FROM to "1990-01-01T00:00:00Z",
            UserFilterValues.BIRTHDATE_TO to "2000-01-01T00:00:00Z",
            UserFilterValues.IS_BIRTHDATE_SET to "true",
            UserFilterValues.SEARCH_QUERY to "admin"
        )

        val parsed = ManagementUserListParams.parse(raw)

        assertEquals(2, parsed.pageNumber)
        assertEquals(50, parsed.pageSize)
        assertEquals(UserSortBy.LAST_LOGIN_AT, parsed.sortBy)
        assertEquals(SortOrder.ASC, parsed.sortOrder)
        assertEquals(listOf("usr_123", "usr_456"), parsed.userIds)
        assertEquals(listOf(UserRole.ADMIN, UserRole.STAFF), parsed.userRoles)
        assertEquals(listOf(UserRole.USER), parsed.excludeUserRoles)
        assertEquals(listOf(UserAccountStatus.ACTIVE), parsed.accountStatuses)
        assertEquals(listOf(UserAccountStatus.BANNED), parsed.excludeAccountStatuses)
        assertEquals(listOf(UserAccountStatus.ACTIVE), parsed.accountStatusesOnRestore)
        assertEquals(listOf(UserAccountStatus.READ_ONLY), parsed.excludeAccountStatusesOnRestore)
        assertEquals(true, parsed.isAccountStatusOnRestoreSet)
        assertEquals(10, parsed.authorityLevelFrom)
        assertEquals(90, parsed.authorityLevelTo)
        assertEquals(listOf("user.get"), parsed.permissionCodes)
        assertEquals(listOf("user.delete"), parsed.excludePermissionCodes)
        assertEquals(true, parsed.isTotpEnabled)
        assertEquals("2026-01-01T00:00:00Z", parsed.lastLoginAtFrom)
        assertEquals("2026-03-01T00:00:00Z", parsed.lastLoginAtTo)
        assertEquals(true, parsed.isLastLoginAtSet)
        assertEquals("2026-01-02T00:00:00Z", parsed.lastActiveAtFrom)
        assertEquals("2026-03-02T00:00:00Z", parsed.lastActiveAtTo)
        assertEquals(true, parsed.isLastActiveAtSet)
        assertEquals("2026-01-03T00:00:00Z", parsed.createdAtFrom)
        assertEquals("2026-03-03T00:00:00Z", parsed.createdAtTo)
        assertEquals("2026-01-04T00:00:00Z", parsed.updatedAtFrom)
        assertEquals("2026-03-04T00:00:00Z", parsed.updatedAtTo)
        assertEquals(true, parsed.isUpdatedAtSet)
        assertEquals("2026-01-05T00:00:00Z", parsed.scheduledPermanentDeletionAtFrom)
        assertEquals("2026-03-05T00:00:00Z", parsed.scheduledPermanentDeletionAtTo)
        assertEquals(true, parsed.isScheduledPermanentDeletionAtSet)
        assertEquals(listOf(AccountLockoutType.TEMPORARY), parsed.accountLockoutTypes)
        assertEquals(listOf(AccountLockoutType.INDEFINITE), parsed.excludeAccountLockoutTypes)
        assertEquals("2026-01-06T00:00:00Z", parsed.temporaryLockoutUntilFrom)
        assertEquals("2026-03-06T00:00:00Z", parsed.temporaryLockoutUntilTo)
        assertEquals(true, parsed.isTemporaryLockoutUntilSet)
        assertEquals(1048576L, parsed.storageQuotaBytesFrom)
        assertEquals(104857600L, parsed.storageQuotaBytesTo)
        assertEquals(true, parsed.isStorageQuotaBytesSet)
        assertEquals(1024L, parsed.storageMaxUploadSizeBytesFrom)
        assertEquals(2048L, parsed.storageMaxUploadSizeBytesTo)
        assertEquals(true, parsed.isStorageMaxUploadSizeBytesSet)
        assertEquals(1L, parsed.storageMaxPendingUploadsFrom)
        assertEquals(10L, parsed.storageMaxPendingUploadsTo)
        assertEquals(true, parsed.isStorageMaxPendingUploadsSet)
        assertEquals(true, parsed.isStorageConfigurationSet)
        assertEquals("admin_user", parsed.nickname)
        assertEquals(true, parsed.isNicknameSet)
        assertEquals("Admin User", parsed.displayName)
        assertEquals(true, parsed.isDisplayNameSet)
        assertEquals(true, parsed.isAvatarSet)
        assertEquals("1990-01-01T00:00:00Z", parsed.birthdateFrom)
        assertEquals("2000-01-01T00:00:00Z", parsed.birthdateTo)
        assertEquals(true, parsed.isBirthdateSet)
        assertEquals("admin", parsed.searchQuery)
    }

    @Test
    fun `parse applies default values when map is empty`() {
        val parsed = ManagementUserListParams.parse(emptyMap())
        assertEquals(ManagementUserListParams(), parsed)
    }

    @Test
    fun `serialize and toQueryMap produce correct map`() {
        val model = ManagementUserListParams(
            pageNumber = 2,
            pageSize = 50,
            sortBy = UserSortBy.LAST_LOGIN_AT,
            sortOrder = SortOrder.ASC,
            userIds = listOf("usr_123", "usr_456"),
            userRoles = listOf(UserRole.ADMIN),
            excludeUserRoles = listOf(UserRole.USER),
            accountStatuses = listOf(UserAccountStatus.ACTIVE),
            excludeAccountStatuses = listOf(UserAccountStatus.BANNED),
            accountStatusesOnRestore = listOf(UserAccountStatus.ACTIVE),
            excludeAccountStatusesOnRestore = listOf(UserAccountStatus.READ_ONLY),
            isAccountStatusOnRestoreSet = true,
            authorityLevelFrom = 10,
            authorityLevelTo = 90,
            permissionCodes = listOf("user.get"),
            excludePermissionCodes = listOf("user.delete"),
            isTotpEnabled = true,
            lastLoginAtFrom = "2026-01-01T00:00:00Z",
            lastLoginAtTo = "2026-03-01T00:00:00Z",
            isLastLoginAtSet = true,
            lastActiveAtFrom = "2026-01-02T00:00:00Z",
            lastActiveAtTo = "2026-03-02T00:00:00Z",
            isLastActiveAtSet = true,
            createdAtFrom = "2026-01-03T00:00:00Z",
            createdAtTo = "2026-03-03T00:00:00Z",
            updatedAtFrom = "2026-01-04T00:00:00Z",
            updatedAtTo = "2026-03-04T00:00:00Z",
            isUpdatedAtSet = true,
            scheduledPermanentDeletionAtFrom = "2026-01-05T00:00:00Z",
            scheduledPermanentDeletionAtTo = "2026-03-05T00:00:00Z",
            isScheduledPermanentDeletionAtSet = true,
            accountLockoutTypes = listOf(AccountLockoutType.TEMPORARY),
            excludeAccountLockoutTypes = listOf(AccountLockoutType.INDEFINITE),
            temporaryLockoutUntilFrom = "2026-01-06T00:00:00Z",
            temporaryLockoutUntilTo = "2026-03-06T00:00:00Z",
            isTemporaryLockoutUntilSet = true,
            storageQuotaBytesFrom = 1048576L,
            storageQuotaBytesTo = 104857600L,
            isStorageQuotaBytesSet = true,
            storageMaxUploadSizeBytesFrom = 1024L,
            storageMaxUploadSizeBytesTo = 2048L,
            isStorageMaxUploadSizeBytesSet = true,
            storageMaxPendingUploadsFrom = 1L,
            storageMaxPendingUploadsTo = 10L,
            isStorageMaxPendingUploadsSet = true,
            isStorageConfigurationSet = true,
            nickname = "admin_user",
            isNicknameSet = true,
            displayName = "Admin User",
            isDisplayNameSet = true,
            isAvatarSet = true,
            birthdateFrom = "1990-01-01T00:00:00Z",
            birthdateTo = "2000-01-01T00:00:00Z",
            isBirthdateSet = true,
            searchQuery = "admin"
        )
        val serialized = model.toQueryMap()

        assertEquals(listOf("2"), serialized[ListingParamNames.Pagination.PAGE_NUMBER])
        assertEquals(listOf("50"), serialized[ListingParamNames.Pagination.PAGE_SIZE])
        assertEquals(listOf("last_login_at"), serialized[ListingParamNames.Sort.SORT_BY])
        assertEquals(listOf("asc"), serialized[ListingParamNames.Sort.SORT_ORDER])
        assertEquals(listOf("usr_123", "usr_456"), serialized[UserFilterValues.USER_ID])
        assertEquals(listOf("admin"), serialized[UserFilterValues.ROLE])
        assertEquals(listOf("active"), serialized[UserFilterValues.ACCOUNT_STATUS])
        assertEquals(serialized, ManagementUserListParams.serialize(model))
    }
}
