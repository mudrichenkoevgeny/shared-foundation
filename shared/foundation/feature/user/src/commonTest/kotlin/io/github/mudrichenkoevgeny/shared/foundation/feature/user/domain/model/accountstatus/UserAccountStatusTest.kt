package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class UserAccountStatusTest {

    @Test
    fun `fromValue accepts case-insensitive enum names`() {
        assertEquals(UserAccountStatus.ACTIVE, UserAccountStatus.fromValueOrNull("active"))
        assertEquals(UserAccountStatus.READ_ONLY, UserAccountStatus.fromValueOrNull("READ_ONLY"))
        assertEquals(UserAccountStatus.SECURITY_HOLD, UserAccountStatus.fromValueOrNull("security_hold"))
    }

    @Test
    fun `fromValue returns null for unknown values`() {
        assertNull(UserAccountStatus.fromValueOrNull("unknown"))
        assertNull(UserAccountStatus.fromValueOrNull(""))
    }
}
