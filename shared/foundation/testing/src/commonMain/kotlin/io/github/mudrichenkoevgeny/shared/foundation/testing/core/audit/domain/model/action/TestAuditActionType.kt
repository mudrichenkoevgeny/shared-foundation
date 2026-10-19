package io.github.mudrichenkoevgeny.shared.foundation.testing.core.audit.domain.model.action

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.action.AuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.action.CompositeAuditActionTypeParser

/**
 * Test implementation of [AuditActionType] that wraps any arbitrary string.
 */
data class TestAuditActionType(
    override val value: String
) : AuditActionType {
    override fun parseOrNull(value: String): AuditActionType = TestAuditActionType(value)
    override fun parseOrThrow(value: String): AuditActionType = TestAuditActionType(value)
}

/**
 * Permissive delegate that parses any incoming string into a [TestAuditActionType].
 */
object AcceptAnyStringAuditActionDelegate : AuditActionType {
    override val value: String = "__test_action_delegate__"

    override fun parseOrNull(value: String): AuditActionType = TestAuditActionType(value)
    override fun parseOrThrow(value: String): AuditActionType = TestAuditActionType(value)
}

/**
 * Creates a permissive [CompositeAuditActionTypeParser] for testing, accepting any string action.
 */
fun createTestCompositeAuditActionTypeParser(
    delegate: AuditActionType = AcceptAnyStringAuditActionDelegate
): CompositeAuditActionTypeParser = CompositeAuditActionTypeParser(setOf(delegate))