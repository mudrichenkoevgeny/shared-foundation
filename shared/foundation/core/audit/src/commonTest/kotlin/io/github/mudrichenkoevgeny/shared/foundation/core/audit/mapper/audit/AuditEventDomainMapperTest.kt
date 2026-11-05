package io.github.mudrichenkoevgeny.shared.foundation.core.audit.mapper.audit

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.audit.resource.CommonAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.action.AuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventId
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventInternal
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventPrivate
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventSummary
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditValueSensitivity
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.AuditEventMetadata
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CommonAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.resource.AuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.status.AuditStatus
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Instant

/**
 * Unit tests verifying domain projection mapping for audit events (Internal -> Private -> Summary).
 */
class AuditEventDomainMapperTest {
    private val testAction: AuditActionType =
        object : AuditActionType {
            override val value: String = "test.action"
        }

    private val testResource: AuditResourceType = CommonAuditResourceType.AUDIT_EVENT

    private val testId: AuditEventId = AuditEventId.generate()
    private val testCreatedAt: Instant = Instant.fromEpochMilliseconds(1700000000000L)

    private val testMetadata: Set<AuditEventMetadata> =
        setOf(
            AuditEventMetadata(
                key = CommonAuditMetadataKey.IP_ADDRESS,
                value = "127.0.0.1"
            )
        )

    private val testInternalEvent: AuditEventInternal =
        AuditEventInternal(
            id = testId,
            actorId = "usr_100",
            actorType = AuditActorType.USER,
            actorUserRole = "admin",
            action = testAction,
            resource = testResource,
            resourceId = "res_200",
            resourceValueSensitivity = AuditValueSensitivity.FULL_VALUE_MASK,
            status = AuditStatus.SUCCESS,
            metadata = testMetadata,
            message = "Test event message",
            createdAt = testCreatedAt
        )

    @Test
    fun testInternalToPrivateMapping() {
        val privateModel: AuditEventPrivate = testInternalEvent.toAuditEventPrivate()

        assertEquals(testId, privateModel.id)
        assertEquals("usr_100", privateModel.actorId)
        assertEquals(AuditActorType.USER, privateModel.actorType)
        assertEquals("admin", privateModel.actorUserRole)
        assertEquals(testAction, privateModel.action)
        assertEquals(testResource, privateModel.resource)
        assertEquals("res_200", privateModel.resourceId)
        assertEquals(AuditStatus.SUCCESS, privateModel.status)
        assertEquals(testMetadata, privateModel.metadata)
        assertEquals("Test event message", privateModel.message)
        assertEquals(testCreatedAt, privateModel.createdAt)
    }

    @Test
    fun testPrivateToSummaryMapping() {
        val privateModel: AuditEventPrivate = testInternalEvent.toAuditEventPrivate()
        val summaryModel: AuditEventSummary = privateModel.toAuditEventSummary()

        assertEquals(testId, summaryModel.id)
        assertEquals(AuditActorType.USER, summaryModel.actorType)
        assertEquals(testAction, summaryModel.action)
        assertEquals(testResource, summaryModel.resource)
        assertEquals(AuditStatus.SUCCESS, summaryModel.status)
        assertEquals(testCreatedAt, summaryModel.createdAt)
    }

    @Test
    fun testInternalToSummaryMappingDirect() {
        val summaryModel: AuditEventSummary = testInternalEvent.toAuditEventSummary()

        assertEquals(testId, summaryModel.id)
        assertEquals(AuditActorType.USER, summaryModel.actorType)
        assertEquals(testAction, summaryModel.action)
        assertEquals(testResource, summaryModel.resource)
        assertEquals(AuditStatus.SUCCESS, summaryModel.status)
        assertEquals(testCreatedAt, summaryModel.createdAt)
    }
}
