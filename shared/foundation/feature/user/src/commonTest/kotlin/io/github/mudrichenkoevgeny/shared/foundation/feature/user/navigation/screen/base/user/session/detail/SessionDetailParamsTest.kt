package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.user.session.detail

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class SessionDetailParamsTest {

    @Test
    fun `parse extracts sessionId and userId correctly`() {
        val params = mapOf(
            UserApiPaths.SESSION_ID to "123e4567-e89b-12d3-a456-426614174000",
            UserApiPaths.USER_ID to "123e4567-e89b-12d3-a456-426614174001"
        )
        val parsed = SessionDetailParams.parse(params)

        assertEquals("123e4567-e89b-12d3-a456-426614174000", parsed.sessionId)
        assertEquals("123e4567-e89b-12d3-a456-426614174001", parsed.userId)
    }

    @Test
    fun `parse returns empty for missing parameters`() {
        val emptyParsed = SessionDetailParams.parse(emptyMap())

        assertEquals("", emptyParsed.sessionId)
        assertNull(emptyParsed.userId)
    }

    @Test
    fun `serialize and toQueryMap return empty map for path parameters`() {
        val model = SessionDetailParams(
            sessionId = "123e4567-e89b-12d3-a456-426614174000",
            userId = "123e4567-e89b-12d3-a456-426614174001"
        )
        val serialized = model.toQueryMap()

        assertEquals(emptyMap(), serialized)
        assertEquals(serialized, SessionDetailParams.serialize(model))
    }
}
