package io.github.mudrichenkoevgeny.shared.foundation.core.audit.navigation.screen.management.audit.detail

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.contract.AuditApiPaths
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ManagementAuditEventDetailParamsParserTest {

    @Test
    fun `parse extracts eventId correctly`() {
        val paramsMap = mapOf(
            AuditApiPaths.EVENT_ID to "123e4567-e89b-12d3-a456-426614174000"
        )

        val parsed = ManagementAuditEventDetailParams.parse(paramsMap)

        assertEquals("123e4567-e89b-12d3-a456-426614174000", parsed.eventId)
        assertEquals("123e4567-e89b-12d3-a456-426614174000", parsed.typedEventId?.asHexDashString())
    }

    @Test
    fun `parse returns empty eventId when map is empty`() {
        val parsed = ManagementAuditEventDetailParams.parse(emptyMap())

        assertEquals("", parsed.eventId)
        assertNull(parsed.typedEventId)
    }

    @Test
    fun `serialize outputs correct eventId map`() {
        val params = ManagementAuditEventDetailParams(
            eventId = "123e4567-e89b-12d3-a456-426614174000"
        )

        val serialized = params.toQueryMap()

        assertEquals(
            mapOf(AuditApiPaths.EVENT_ID to listOf("123e4567-e89b-12d3-a456-426614174000")),
            serialized
        )
    }
}
