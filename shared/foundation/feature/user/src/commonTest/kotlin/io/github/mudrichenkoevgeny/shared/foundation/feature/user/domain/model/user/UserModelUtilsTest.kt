package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import kotlin.test.Test
import kotlin.test.assertEquals

class UserModelUtilsTest {

    @Test
    fun `resolveDisplayName returns displayName when present`() {
        val user = UserSummary(
            id = UserId.generate(),
            role = UserRole.USER,
            accountStatus = UserAccountStatus.ACTIVE,
            nickname = "ivan",
            displayName = "Ivan Ivanov",
            avatarUrls = null
        )
        assertEquals("Ivan Ivanov", user.resolveDisplayName("User"))
    }

    @Test
    fun `resolveDisplayName returns prefixed nickname when displayName is null or blank`() {
        val user1 = UserSummary(
            id = UserId.generate(),
            role = UserRole.USER,
            accountStatus = UserAccountStatus.ACTIVE,
            nickname = "ivan_123",
            displayName = null,
            avatarUrls = null
        )
        assertEquals("@ivan_123", user1.resolveDisplayName("User"))

        val user2 = UserSummary(
            id = UserId.generate(),
            role = UserRole.USER,
            accountStatus = UserAccountStatus.ACTIVE,
            nickname = "ivan_123",
            displayName = "   ",
            avatarUrls = null
        )
        assertEquals("@ivan_123", user2.resolveDisplayName("User"))
    }

    @Test
    fun `resolveDisplayName returns fallback with short ID when both name and nickname are missing`() {
        val id = "12345678-0000-4000-8000-000000000000".toUserIdOrThrow()
        val user = UserSummary(
            id = id,
            role = UserRole.USER,
            accountStatus = UserAccountStatus.ACTIVE,
            nickname = null,
            displayName = null,
            avatarUrls = null
        )
        assertEquals("User 12345678", user.resolveDisplayName("User"))
    }
}
