package io.github.mudrichenkoevgeny.shared.foundation.testing.core.audit.domain.model.action

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.action.AuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.action.CompositeAuditActionTypeParser

/**
 * Test implementation of [AuditActionType] that wraps any arbitrary string.
 */
data class TestAuditActionType(
    override val value: String
) : AuditActionType

/**
 * Creates a permissive [CompositeAuditActionTypeParser] for testing, accepting any string action.
 *
 * @param parser Function mapping raw action strings to [AuditActionType].
 */
fun createTestCompositeAuditActionTypeParser(
    parser: (String) -> AuditActionType? = { TestAuditActionType(it) }
): CompositeAuditActionTypeParser = CompositeAuditActionTypeParser(listOf(parser))
