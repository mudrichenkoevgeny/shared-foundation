package io.github.mudrichenkoevgeny.shared.foundation.testing.core.audit.domain.model.metadata

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditValueSensitivity
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.AuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CompositeAuditMetadataKeyParser

/**
 * Test implementation of [AuditMetadataKey] that accepts any arbitrary string.
 */
data class TestAuditMetadataKey(
    override val value: String,
    override val valueSensitivity: AuditValueSensitivity = AuditValueSensitivity.NON_SENSITIVE
) : AuditMetadataKey {
    override fun parseOrNull(value: String): AuditMetadataKey = TestAuditMetadataKey(value, valueSensitivity)
    override fun parseOrThrow(value: String): AuditMetadataKey = TestAuditMetadataKey(value, valueSensitivity)
}

object AcceptAnyStringAuditMetadataKeyDelegate : AuditMetadataKey {
    override val value: String = "__test_metadata_key_delegate__"
    override val valueSensitivity: AuditValueSensitivity = AuditValueSensitivity.NON_SENSITIVE
    override fun parseOrNull(value: String): AuditMetadataKey = TestAuditMetadataKey(value, valueSensitivity)
    override fun parseOrThrow(value: String): AuditMetadataKey = TestAuditMetadataKey(value, valueSensitivity)
}

/**
 * Creates a permissive [CompositeAuditMetadataKeyParser] for tests, accepting any string key.
 */
fun createTestCompositeAuditMetadataKeyParser(
    delegate: AuditMetadataKey = AcceptAnyStringAuditMetadataKeyDelegate
): CompositeAuditMetadataKeyParser = CompositeAuditMetadataKeyParser(setOf(delegate))