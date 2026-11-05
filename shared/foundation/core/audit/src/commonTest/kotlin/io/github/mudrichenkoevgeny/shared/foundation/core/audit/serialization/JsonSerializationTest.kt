package io.github.mudrichenkoevgeny.shared.foundation.core.audit.serialization

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.model.event.AuditEventMetadataPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.model.event.AuditEventPrivatePayload
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.model.event.AuditEventSummaryPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.common.serialization.FoundationJson
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Unit tests verifying JSON serialization and deserialization contracts for core/audit payloads.
 */
class JsonSerializationTest {
    @Test
    fun testAuditEventSummaryPayloadSerialization() {
        val payload =
            AuditEventSummaryPayload(
                id = "00000000-0000-4000-8000-000000000001",
                actorType = "user",
                action = "user.create",
                resource = "user",
                status = "success",
                createdAt = 1700000000000L
            )

        val serialized = FoundationJson.encodeToString(payload)

        assertTrue(serialized.contains("\"id\":\"00000000-0000-4000-8000-000000000001\""))
        assertTrue(serialized.contains("\"actor_type\":\"user\""))
        assertTrue(serialized.contains("\"action\":\"user.create\""))
        assertTrue(serialized.contains("\"resource\":\"user\""))
        assertTrue(serialized.contains("\"status\":\"success\""))
        assertTrue(serialized.contains("\"created_at\":1700000000000"))

        val deserialized = FoundationJson.decodeFromString<AuditEventSummaryPayload>(serialized)
        assertEquals(payload, deserialized)
    }

    @Test
    fun testAuditEventPrivatePayloadSerialization() {
        val metadata =
            listOf(
                AuditEventMetadataPayload(
                    key = "ip_address",
                    value = "127.0.0.1",
                    valueSensitivity = "none"
                )
            )

        val payload =
            AuditEventPrivatePayload(
                id = "00000000-0000-4000-8000-000000000001",
                actorId = "usr_123",
                actorType = "user",
                actorUserRole = "admin",
                action = "user.update",
                resource = "user",
                resourceId = "usr_456",
                status = "success",
                metadata = metadata,
                message = "Updated user profile",
                createdAt = 1700000000000L
            )

        val serialized = FoundationJson.encodeToString(payload)

        assertTrue(serialized.contains("\"actor_id\":\"usr_123\""))
        assertTrue(serialized.contains("\"actor_user_role\":\"admin\""))
        assertTrue(serialized.contains("\"resource_id\":\"usr_456\""))
        assertTrue(serialized.contains("\"key\":\"ip_address\""))
        assertTrue(serialized.contains("\"value\":\"127.0.0.1\""))
        assertTrue(serialized.contains("\"value_sensitivity\":\"none\""))
        assertTrue(serialized.contains("\"message\":\"Updated user profile\""))

        val deserialized = FoundationJson.decodeFromString<AuditEventPrivatePayload>(serialized)
        assertEquals(payload, deserialized)
    }

    @Test
    fun testAuditEventPrivatePayloadNullabilityHandling() {
        val payload =
            AuditEventPrivatePayload(
                id = "00000000-0000-4000-8000-000000000001",
                actorId = null,
                actorType = "system",
                actorUserRole = null,
                action = "system.cleanup",
                resource = "system",
                resourceId = null,
                status = "success",
                metadata = emptyList(),
                message = null,
                createdAt = 1700000000000L
            )

        val serialized = FoundationJson.encodeToString(payload)
        val deserialized = FoundationJson.decodeFromString<AuditEventPrivatePayload>(serialized)

        assertEquals(payload, deserialized)
    }
}
