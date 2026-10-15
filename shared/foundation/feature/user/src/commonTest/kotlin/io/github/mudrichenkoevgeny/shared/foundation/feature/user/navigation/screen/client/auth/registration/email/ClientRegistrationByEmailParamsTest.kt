package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.client.auth.registration.email

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ClientRegistrationByEmailParamsTest {

    @Test
    fun `parse extracts email correctly`() {
        val params = mapOf(UserApiFields.EMAIL to "client@example.com")
        val parsed = ClientRegistrationByEmailParams.parse(params)

        assertEquals("client@example.com", parsed.email)
    }

    @Test
    fun `parse returns null when email is blank or missing`() {
        val emptyParsed = ClientRegistrationByEmailParams.parse(emptyMap())
        assertNull(emptyParsed.email)
    }

    @Test
    fun `serialize and toQueryMap produce correct map`() {
        val model = ClientRegistrationByEmailParams(email = "client@example.com")
        val serialized = model.toQueryMap()

        assertEquals(mapOf(UserApiFields.EMAIL to listOf("client@example.com")), serialized)
        assertEquals(serialized, ClientRegistrationByEmailParams.serialize(model))
    }
}
