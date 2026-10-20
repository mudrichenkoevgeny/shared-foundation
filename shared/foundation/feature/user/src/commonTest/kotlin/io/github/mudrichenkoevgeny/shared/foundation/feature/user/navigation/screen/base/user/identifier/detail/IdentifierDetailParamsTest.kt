package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.user.identifier.detail

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class IdentifierDetailParamsTest {

    private companion object {
        const val TEST_IDENTIFIER_ID = "00000000-0000-4000-8000-000000000020"
        const val TEST_USER_ID = "00000000-0000-4000-8000-000000000001"
    }

    @Test
    fun `parse extracts identifierId and userId correctly`() {
        val params = mapOf(
            UserApiPaths.USER_IDENTIFIER_ID to TEST_IDENTIFIER_ID,
            UserApiPaths.USER_ID to TEST_USER_ID
        )
        val parsed = IdentifierDetailParams.parse(params)

        assertEquals(TEST_IDENTIFIER_ID, parsed.identifierId)
        assertEquals(TEST_USER_ID, parsed.userId)
    }

    @Test
    fun `parse returns empty for missing parameters`() {
        val emptyParsed = IdentifierDetailParams.parse(emptyMap())

        assertEquals("", emptyParsed.identifierId)
        assertNull(emptyParsed.userId)
    }

    @Test
    fun `serialize and toQueryMap return empty map for path parameters`() {
        val model = IdentifierDetailParams(
            identifierId = TEST_IDENTIFIER_ID,
            userId = TEST_USER_ID
        )
        val serialized = model.toQueryMap()

        assertEquals(emptyMap(), serialized)
        assertEquals(serialized, IdentifierDetailParams.serialize(model))
    }
}
