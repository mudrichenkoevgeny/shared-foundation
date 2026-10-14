package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.profile.identifier.detail

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class IdentifierDetailParamsParserTest {

    @Test
    fun `parse extracts identifierId and userId correctly`() {
        val params = mapOf(
            UserApiPaths.USER_IDENTIFIER_ID to "123e4567-e89b-12d3-a456-426614174000",
            UserApiPaths.USER_ID to "123e4567-e89b-12d3-a456-426614174001"
        )
        val parsed = IdentifierDetailParams.parse(params)

        assertEquals("123e4567-e89b-12d3-a456-426614174000", parsed.identifierId)
        assertEquals("123e4567-e89b-12d3-a456-426614174001", parsed.userId)
        assertEquals("123e4567-e89b-12d3-a456-426614174000", parsed.typedIdentifierId?.asHexDashString())
        assertEquals("123e4567-e89b-12d3-a456-426614174001", parsed.typedUserId?.asHexDashString())
    }

    @Test
    fun `parse returns empty for missing parameters`() {
        val emptyParsed = IdentifierDetailParams.parse(emptyMap())

        assertEquals("", emptyParsed.identifierId)
        assertNull(emptyParsed.userId)
        assertNull(emptyParsed.typedIdentifierId)
        assertNull(emptyParsed.typedUserId)
    }

    @Test
    fun `serialize and toQueryMap produce correct map`() {
        val model = IdentifierDetailParams(
            identifierId = "123e4567-e89b-12d3-a456-426614174000",
            userId = "123e4567-e89b-12d3-a456-426614174001"
        )
        val serialized = model.toQueryMap()

        assertEquals(listOf("123e4567-e89b-12d3-a456-426614174000"), serialized[UserApiPaths.USER_IDENTIFIER_ID])
        assertEquals(listOf("123e4567-e89b-12d3-a456-426614174001"), serialized[UserApiPaths.USER_ID])
        assertEquals(serialized, IdentifierDetailParamsParser.serialize(model))
    }
}
