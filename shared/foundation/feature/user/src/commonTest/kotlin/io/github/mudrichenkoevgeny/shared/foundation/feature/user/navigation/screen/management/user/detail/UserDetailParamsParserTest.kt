package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.user.detail

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class UserDetailParamsParserTest {

    @Test
    fun `parse extracts userId correctly`() {
        val params = mapOf(UserApiPaths.USER_ID to "123e4567-e89b-12d3-a456-426614174000")
        val parsed = UserDetailParams.parse(params)

        assertEquals("123e4567-e89b-12d3-a456-426614174000", parsed.userId)
        assertEquals("123e4567-e89b-12d3-a456-426614174000", parsed.typedUserId?.asHexDashString())
    }

    @Test
    fun `parse returns null when userId is missing`() {
        val emptyParsed = UserDetailParams.parse(emptyMap())

        assertNull(emptyParsed.userId)
        assertNull(emptyParsed.typedUserId)
    }

    @Test
    fun `serialize and toQueryMap produce correct map`() {
        val model = UserDetailParams(userId = "123e4567-e89b-12d3-a456-426614174000")
        val serialized = model.toQueryMap()

        assertEquals(mapOf(UserApiPaths.USER_ID to listOf("123e4567-e89b-12d3-a456-426614174000")), serialized)
        assertEquals(serialized, UserDetailParamsParser.serialize(model))
    }
}
