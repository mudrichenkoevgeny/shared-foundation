package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.resetpassword

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ResetPasswordParamsTest {

    @Test
    fun `parse extracts email and code correctly`() {
        val params = mapOf(
            UserApiFields.EMAIL to "user@example.com",
            UserApiFields.CONFIRMATION_CODE to "123456"
        )
        val parsed = ResetPasswordParams.parse(params)

        assertEquals("user@example.com", parsed.email)
        assertEquals("123456", parsed.code)
    }

    @Test
    fun `parse returns null for missing fields`() {
        val emptyParsed = ResetPasswordParams.parse(emptyMap())
        assertNull(emptyParsed.email)
        assertNull(emptyParsed.code)
    }

    @Test
    fun `serialize and toQueryMap produce correct map`() {
        val model = ResetPasswordParams(email = "user@example.com", code = "123456")
        val serialized = model.toQueryMap()

        assertEquals(listOf("user@example.com"), serialized[UserApiFields.EMAIL])
        assertEquals(listOf("123456"), serialized[UserApiFields.CONFIRMATION_CODE])
        assertEquals(serialized, ResetPasswordParams.serialize(model))
    }
}
