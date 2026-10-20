package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.user.session.detail

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class SessionDetailParamsTest {

    private companion object {
        const val TEST_SESSION_ID = "00000000-0000-4000-8000-000000000010"
        const val TEST_USER_ID = "00000000-0000-4000-8000-000000000001"
    }

    @Test
    fun `parse extracts sessionId and userId correctly`() {
        val params = mapOf(
            UserApiPaths.SESSION_ID to TEST_SESSION_ID,
            UserApiPaths.USER_ID to TEST_USER_ID
        )
        val parsed = SessionDetailParams.parse(params)

        assertEquals(TEST_SESSION_ID, parsed.sessionId)
        assertEquals(TEST_USER_ID, parsed.userId)
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
            sessionId = TEST_SESSION_ID,
            userId = TEST_USER_ID
        )
        val serialized = model.toQueryMap()

        assertEquals(emptyMap(), serialized)
        assertEquals(serialized, SessionDetailParams.serialize(model))
    }
}
