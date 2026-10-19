package io.github.mudrichenkoevgeny.shared.foundation.testing.core.audit.parser

import io.github.mudrichenkoevgeny.shared.foundation.testing.core.audit.domain.model.action.createTestCompositeAuditActionTypeParser
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.audit.domain.model.metadata.createTestCompositeAuditMetadataKeyParser
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.audit.domain.model.resource.createTestCompositeAuditResourceTypeParser
import kotlin.test.Test
import kotlin.test.assertEquals

class TestCompositeAuditParsersTest {

    @Test
    fun actionParserAcceptsArbitraryActionString() {
        val parser = createTestCompositeAuditActionTypeParser()
        val parsed = parser.fromValueOrThrow("custom_dynamic_action")

        assertEquals("custom_dynamic_action", parsed.value)
    }

    @Test
    fun resourceParserAcceptsArbitraryResourceString() {
        val parser = createTestCompositeAuditResourceTypeParser()
        val parsed = parser.fromValueOrThrow("custom_dynamic_resource")

        assertEquals("custom_dynamic_resource", parsed.value)
    }

    @Test
    fun metadataKeyParserAcceptsArbitraryMetadataKeyString() {
        val parser = createTestCompositeAuditMetadataKeyParser()
        val parsed = parser.fromValueOrThrow("custom_metadata_key")

        assertEquals("custom_metadata_key", parsed.value)
    }
}