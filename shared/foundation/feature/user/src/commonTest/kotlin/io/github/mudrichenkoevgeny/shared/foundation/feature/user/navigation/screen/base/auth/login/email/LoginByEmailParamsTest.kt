package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.login.email

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class LoginByEmailParamsTest {

    @Test
    fun `parse extracts email correctly`() {
        val params = mapOf(UserApiFields.EMAIL to "test@example.com")
        val parsed = LoginByEmailParams.parse(params)

        assertEquals("test@example.com", parsed.email)
    }

    @Test
    fun `parse returns null when email is blank or missing`() {
        val emptyParsed = LoginByEmailParams.parse(emptyMap())
        assertNull(emptyParsed.email)

        val blankParsed = LoginByEmailParams.parse(mapOf(UserApiFields.EMAIL to "   "))
        assertNull(blankParsed.email)
    }

    @Test
    fun `serialize and toQueryMap produce correct map`() {
        val model = LoginByEmailParams(email = "test@example.com")
        val serialized = model.toQueryMap()

        assertEquals(mapOf(UserApiFields.EMAIL to listOf("test@example.com")), serialized)
        assertEquals(serialized, LoginByEmailParams.serialize(model))
    }
}
