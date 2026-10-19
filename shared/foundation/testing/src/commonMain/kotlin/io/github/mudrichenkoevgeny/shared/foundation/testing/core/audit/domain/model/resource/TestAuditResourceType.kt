package io.github.mudrichenkoevgeny.shared.foundation.testing.core.audit.domain.model.resource

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.resource.AuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.resource.CompositeAuditResourceTypeParser

/**
 * Test implementation of [AuditResourceType] that wraps any arbitrary string.
 */
data class TestAuditResourceType(
    override val value: String
) : AuditResourceType {
    override fun parseOrNull(value: String): AuditResourceType = TestAuditResourceType(value)
    override fun parseOrThrow(value: String): AuditResourceType = TestAuditResourceType(value)
}

/**
 * Permissive delegate that parses any incoming string into a [TestAuditResourceType].
 */
object AcceptAnyStringAuditResourceDelegate : AuditResourceType {
    override val value: String = "__test_resource_delegate__"

    override fun parseOrNull(value: String): AuditResourceType = TestAuditResourceType(value)
    override fun parseOrThrow(value: String): AuditResourceType = TestAuditResourceType(value)
}

/**
 * Creates a permissive [CompositeAuditResourceTypeParser] for testing, accepting any string resource.
 */
fun createTestCompositeAuditResourceTypeParser(
    delegate: AuditResourceType = AcceptAnyStringAuditResourceDelegate
): CompositeAuditResourceTypeParser = CompositeAuditResourceTypeParser(setOf(delegate))