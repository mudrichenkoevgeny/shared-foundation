package io.github.mudrichenkoevgeny.shared.foundation.feature.user.mapper.user

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.accountlockout.AccountLockoutType
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.FileUrlPresets
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.FileUrls
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.mapper.file.toFileUrlsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserInternal
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.user.UserPrivatePayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.user.UserSummaryPayload
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Instant

class UserDomainMapperTest {
    private val testFileUrls = FileUrls(
        originalUrl = "https://cdn.example.com/avatar_orig.png",
        urlPresets = mapOf(
            FileUrlPresets.THUMBNAIL to "https://cdn.example.com/avatar_thumb.png",
            FileUrlPresets.PREVIEW to "https://cdn.example.com/avatar_prev.png"
        )
    )

    private val testFileUrlsPayload = testFileUrls.toFileUrlsPayload()

    private val testCreatedAt = Instant.fromEpochMilliseconds(1700000000000L)

    @Test
    fun testUserPrivatePayloadMapping() {
        val payload = UserPrivatePayload(
            id = "00000000-0000-4000-8000-000000000001",
            role = UserRole.USER.value,
            accountStatus = UserAccountStatus.ACTIVE.value,
            authorityLevel = 10,
            permissionCodes = emptySet(),
            isTotpEnabled = false,
            createdAt = 1700000000000L,
            lockoutType = "none",
            nickname = "testuser",
            displayName = "Test User",
            avatarUrls = testFileUrlsPayload
        )

        val domain = payload.toUserPrivate()

        assertEquals(testFileUrls, domain.avatarUrls)

        val mappedPayload = domain.toUserPrivatePayload()
        assertEquals(testFileUrlsPayload, mappedPayload.avatarUrls)
    }

    @Test
    fun testUserSummaryPayloadMapping() {
        val payload = UserSummaryPayload(
            id = "00000000-0000-4000-8000-000000000001",
            role = UserRole.USER.value,
            accountStatus = UserAccountStatus.ACTIVE.value,
            nickname = "testuser",
            displayName = "Test User",
            avatarUrls = testFileUrlsPayload
        )

        val domain = payload.toUserSummary()

        assertEquals(testFileUrls, domain.avatarUrls)

        val mappedPayload = domain.toUserSummaryPayload()
        assertEquals(testFileUrlsPayload, mappedPayload.avatarUrls)
    }

    @Test
    fun testUserInternalToPrivateAndSummaryMapping() {
        val internalUser = UserInternal(
            role = UserRole.USER,
            accountStatus = UserAccountStatus.ACTIVE,
            accountStatusOnRestore = null,
            authorityLevel = 10,
            permissionCodes = emptySet(),
            isTotpEnabled = true,
            lastLoginAt = null,
            lastActiveAt = null,
            createdAt = testCreatedAt,
            updatedAt = null,
            scheduledPermanentDeletionAt = null,
            lockoutType = AccountLockoutType.NONE,
            temporaryLockoutUntil = null,
            storageConfiguration = null,
            nickname = "testuser",
            displayName = "Test User",
            avatarFileId = null,
            avatarUrls = testFileUrls,
            birthdate = null
        )

        val privateModel = internalUser.toUserPrivate()

        assertEquals(UserRole.USER, privateModel.role)
        assertEquals(UserAccountStatus.ACTIVE, privateModel.accountStatus)
        assertEquals(10, privateModel.authorityLevel)
        assertEquals(emptySet(), privateModel.permissionCodes)
        assertEquals(true, privateModel.isTotpEnabled)
        assertEquals(AccountLockoutType.NONE, privateModel.lockoutType)
        assertEquals("testuser", privateModel.nickname)
        assertEquals("Test User", privateModel.displayName)
        assertEquals(testFileUrls, privateModel.avatarUrls)
        assertEquals(testCreatedAt, privateModel.createdAt)

        val summaryModelFromInternal = internalUser.toUserSummary()

        assertEquals(UserRole.USER, summaryModelFromInternal.role)
        assertEquals(UserAccountStatus.ACTIVE, summaryModelFromInternal.accountStatus)
        assertEquals("testuser", summaryModelFromInternal.nickname)
        assertEquals("Test User", summaryModelFromInternal.displayName)
        assertEquals(testFileUrls, summaryModelFromInternal.avatarUrls)

        val summaryModelFromPrivate = privateModel.toUserSummary()

        assertEquals(UserRole.USER, summaryModelFromPrivate.role)
        assertEquals(UserAccountStatus.ACTIVE, summaryModelFromPrivate.accountStatus)
        assertEquals("testuser", summaryModelFromPrivate.nickname)
        assertEquals("Test User", summaryModelFromPrivate.displayName)
        assertEquals(testFileUrls, summaryModelFromPrivate.avatarUrls)
    }
}
