package io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.user

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.user.UserRole
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class UserRoleTest {
    @Test
    fun `fromValue accepts case-insensitive enum names`() {
        assertEquals(UserRole.USER, UserRole.fromValueOrNull("user"))
        assertEquals(UserRole.STAFF, UserRole.fromValueOrNull("StAfF"))
        assertEquals(UserRole.ADMIN, UserRole.fromValueOrNull("admin"))
    }

    @Test
    fun `fromValue returns null for unknown values`() {
        assertNull(UserRole.fromValueOrNull("unknown"))
    }
}
