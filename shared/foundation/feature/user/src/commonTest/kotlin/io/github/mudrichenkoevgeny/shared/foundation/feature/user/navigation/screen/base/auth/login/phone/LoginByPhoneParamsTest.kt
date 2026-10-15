package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.login.phone

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class LoginByPhoneParamsTest {

    @Test
    fun `parse extracts and normalizes phone number correctly`() {
        val params = mapOf(UserApiFields.PHONE_NUMBER to " 12345678900")
        val parsed = LoginByPhoneParams.parse(params)

        assertEquals("+12345678900", parsed.phone)
    }

    @Test
    fun `parse returns null when phone is missing`() {
        val emptyParsed = LoginByPhoneParams.parse(emptyMap())
        assertNull(emptyParsed.phone)
    }

    @Test
    fun `serialize and toQueryMap produce correct map`() {
        val model = LoginByPhoneParams(phone = "+12345678900")
        val serialized = model.toQueryMap()

        assertEquals(mapOf(UserApiFields.PHONE_NUMBER to listOf("+12345678900")), serialized)
        assertEquals(serialized, LoginByPhoneParams.serialize(model))
    }
}
