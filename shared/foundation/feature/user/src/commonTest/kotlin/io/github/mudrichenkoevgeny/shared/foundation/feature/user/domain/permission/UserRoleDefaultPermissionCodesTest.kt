package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class UserRoleDefaultPermissionCodesTest {

    @Test
    fun `forRole returns USER STAFF and ADMIN baseline sets`() {
        assertEquals(UserRoleDefaultPermissionCodes.USER, UserRoleDefaultPermissionCodes.forRole(UserRole.USER))
        assertEquals(UserRoleDefaultPermissionCodes.STAFF, UserRoleDefaultPermissionCodes.forRole(UserRole.STAFF))
        assertEquals(UserRoleDefaultPermissionCodes.ADMIN, UserRoleDefaultPermissionCodes.forRole(UserRole.ADMIN))
    }

    @Test
    fun `USER has no baseline permissions`() {
        assertTrue(UserRoleDefaultPermissionCodes.USER.isEmpty())
    }

    @Test
    fun `STAFF is non-empty strict subset of ADMIN`() {
        val staff = UserRoleDefaultPermissionCodes.STAFF
        val admin = UserRoleDefaultPermissionCodes.ADMIN
        assertTrue(staff.isNotEmpty())
        assertTrue(admin.containsAll(staff))
        assertTrue(staff.size < admin.size)
    }

    @Test
    fun `STAFF omits staff-only and unmasked capabilities that ADMIN has`() {
        val staff = UserRoleDefaultPermissionCodes.STAFF
        val admin = UserRoleDefaultPermissionCodes.ADMIN
        assertFalse(staff.contains(UserPermissionCodes.USER_CREATE_AS_STAFF))
        assertTrue(admin.contains(UserPermissionCodes.USER_CREATE_AS_STAFF))
        assertFalse(staff.contains(SessionPermissionCodes.SESSION_UNMASKED_GET_LIST_OF_USER))
        assertTrue(admin.contains(SessionPermissionCodes.SESSION_UNMASKED_GET_LIST_OF_USER))
    }
}
