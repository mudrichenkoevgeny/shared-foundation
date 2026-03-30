package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

class UserAuthProviderTest {

    @Test
    fun `fromValueOrNull accepts case-insensitive enum names`() {
        assertEquals(UserAuthProvider.EMAIL, UserAuthProvider.fromValueOrNull("email"))
        assertEquals(UserAuthProvider.PHONE, UserAuthProvider.fromValueOrNull("phone"))
        assertEquals(UserAuthProvider.GOOGLE, UserAuthProvider.fromValueOrNull("GoOgLe"))
        assertEquals(UserAuthProvider.APPLE, UserAuthProvider.fromValueOrNull("APPLE"))
    }

    @Test
    fun `fromValueOrNull returns null for unknown values`() {
        assertNull(UserAuthProvider.fromValueOrNull("unknown"))
        assertNull(UserAuthProvider.fromValueOrNull(""))
    }

    @Test
    fun `fromValueOrThrow returns provider for valid value`() {
        assertEquals(UserAuthProvider.GOOGLE, UserAuthProvider.fromValueOrThrow("google"))
    }

    @Test
    fun `fromValueOrThrow throws for unknown value`() {
        assertFailsWith<IllegalArgumentException> {
            UserAuthProvider.fromValueOrThrow("unknown")
        }
    }
}
