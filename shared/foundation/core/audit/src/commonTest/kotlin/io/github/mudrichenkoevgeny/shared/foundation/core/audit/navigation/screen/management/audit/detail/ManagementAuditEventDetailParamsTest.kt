package io.github.mudrichenkoevgeny.shared.foundation.core.audit.navigation.screen.management.audit.detail

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.contract.AuditApiPaths
import kotlin.test.Test
import kotlin.test.assertEquals

class ManagementAuditEventDetailParamsTest {

    private companion object {
        const val TEST_EVENT_ID = "00000000-0000-4000-8000-000000000099"
    }

    @Test
    fun `parse extracts eventId correctly`() {
        val paramsMap = mapOf(
            AuditApiPaths.EVENT_ID to TEST_EVENT_ID
        )

        val parsed = ManagementAuditEventDetailParams.parse(paramsMap)

        assertEquals(TEST_EVENT_ID, parsed.eventId)
    }

    @Test
    fun `parse returns empty eventId when map is empty`() {
        val parsed = ManagementAuditEventDetailParams.parse(emptyMap())

        assertEquals("", parsed.eventId)
    }

    @Test
    fun `serialize and toQueryMap return empty map for path parameters`() {
        val params = ManagementAuditEventDetailParams(
            eventId = TEST_EVENT_ID
        )

        val serialized = params.toQueryMap()

        assertEquals(emptyMap(), serialized)
        assertEquals(serialized, ManagementAuditEventDetailParams.serialize(params))
    }
}
