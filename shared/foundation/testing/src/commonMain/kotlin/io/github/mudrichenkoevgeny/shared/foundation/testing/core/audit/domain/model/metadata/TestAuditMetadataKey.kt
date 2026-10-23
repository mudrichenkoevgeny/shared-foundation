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
) : AuditMetadataKey

/**
 * Creates a permissive [CompositeAuditMetadataKeyParser] for tests, accepting any string key.
 *
 * @param parser Function mapping raw metadata key strings to [AuditMetadataKey].
 */
fun createTestCompositeAuditMetadataKeyParser(
    parser: (String) -> AuditMetadataKey? = { TestAuditMetadataKey(it) }
): CompositeAuditMetadataKeyParser = CompositeAuditMetadataKeyParser(listOf(parser))
