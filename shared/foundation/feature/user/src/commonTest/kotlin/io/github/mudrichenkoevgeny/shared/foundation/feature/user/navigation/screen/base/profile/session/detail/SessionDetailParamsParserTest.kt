package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.profile.session.detail

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class SessionDetailParamsParserTest {

    @Test
    fun `parse extracts sessionId and userId correctly`() {
        val params = mapOf(
            UserApiPaths.SESSION_ID to "123e4567-e89b-12d3-a456-426614174000",
            UserApiPaths.USER_ID to "123e4567-e89b-12d3-a456-426614174001"
        )
        val parsed = SessionDetailParams.parse(params)

        assertEquals("123e4567-e89b-12d3-a456-426614174000", parsed.sessionId)
        assertEquals("123e4567-e89b-12d3-a456-426614174001", parsed.userId)
        assertEquals("123e4567-e89b-12d3-a456-426614174000", parsed.typedSessionId?.asHexDashString())
        assertEquals("123e4567-e89b-12d3-a456-426614174001", parsed.typedUserId?.asHexDashString())
    }

    @Test
    fun `parse returns empty for missing parameters`() {
        val emptyParsed = SessionDetailParams.parse(emptyMap())

        assertEquals("", emptyParsed.sessionId)
        assertNull(emptyParsed.userId)
        assertNull(emptyParsed.typedSessionId)
        assertNull(emptyParsed.typedUserId)
    }

    @Test
    fun `serialize and toQueryMap produce correct map`() {
        val model = SessionDetailParams(
            sessionId = "123e4567-e89b-12d3-a456-426614174000",
            userId = "123e4567-e89b-12d3-a456-426614174001"
        )
        val serialized = model.toQueryMap()

        assertEquals(listOf("123e4567-e89b-12d3-a456-426614174000"), serialized[UserApiPaths.SESSION_ID])
        assertEquals(listOf("123e4567-e89b-12d3-a456-426614174001"), serialized[UserApiPaths.USER_ID])
        assertEquals(serialized, SessionDetailParamsParser.serialize(model))
    }
}
