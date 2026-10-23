package io.github.mudrichenkoevgeny.shared.foundation.testing.core.audit.domain.model.resource

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.resource.AuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.resource.CompositeAuditResourceTypeParser

/**
 * Test implementation of [AuditResourceType] that wraps any arbitrary string.
 */
data class TestAuditResourceType(
    override val value: String
) : AuditResourceType

/**
 * Creates a permissive [CompositeAuditResourceTypeParser] for testing, accepting any string resource.
 *
 * @param parser Function mapping raw resource strings to [AuditResourceType].
 */
fun createTestCompositeAuditResourceTypeParser(
    parser: (String) -> AuditResourceType? = { TestAuditResourceType(it) }
): CompositeAuditResourceTypeParser = CompositeAuditResourceTypeParser(listOf(parser))
