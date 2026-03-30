package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission

import kotlin.test.Test
import kotlin.test.assertFailsWith

class UserPermissionCodeTest {

    @Test
    fun `constructor rejects blank value`() {
        assertFailsWith<IllegalArgumentException> {
            UserPermissionCode("")
        }
    }
}
