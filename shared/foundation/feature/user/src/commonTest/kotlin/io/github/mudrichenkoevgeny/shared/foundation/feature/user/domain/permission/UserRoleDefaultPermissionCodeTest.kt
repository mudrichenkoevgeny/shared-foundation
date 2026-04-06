package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class UserRoleDefaultPermissionCodeTest {

    @Test
    fun `forRole returns USER STAFF and ADMIN baseline sets`() {
        assertEquals(UserRoleDefaultPermissionCode.USER, UserRoleDefaultPermissionCode.forRole(UserRole.USER))
        assertEquals(UserRoleDefaultPermissionCode.STAFF, UserRoleDefaultPermissionCode.forRole(UserRole.STAFF))
        assertEquals(UserRoleDefaultPermissionCode.ADMIN, UserRoleDefaultPermissionCode.forRole(UserRole.ADMIN))
    }

    @Test
    fun `USER has no baseline permissions`() {
        assertTrue(UserRoleDefaultPermissionCode.USER.isEmpty())
    }

    @Test
    fun `STAFF is non-empty strict subset of ADMIN`() {
        val staff = UserRoleDefaultPermissionCode.STAFF
        val admin = UserRoleDefaultPermissionCode.ADMIN
        assertTrue(staff.isNotEmpty())
        assertTrue(admin.containsAll(staff))
        assertTrue(staff.size < admin.size)
    }

    @Test
    fun `STAFF omits staff-only and unmasked capabilities that ADMIN has`() {
        val staff = UserRoleDefaultPermissionCode.STAFF
        val admin = UserRoleDefaultPermissionCode.ADMIN
        assertFalse(staff.contains(UserPermissionCode.USER_CREATE_AS_STAFF))
        assertTrue(admin.contains(UserPermissionCode.USER_CREATE_AS_STAFF))
        assertFalse(staff.contains(SessionPermissionCode.SESSION_GET_OF_USER_UNMASKED))
        assertTrue(admin.contains(SessionPermissionCode.SESSION_GET_OF_USER_UNMASKED))
    }
}
