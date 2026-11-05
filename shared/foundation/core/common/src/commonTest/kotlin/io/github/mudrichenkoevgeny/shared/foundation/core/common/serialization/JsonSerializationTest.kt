package io.github.mudrichenkoevgeny.shared.foundation.core.common.serialization

import io.github.mudrichenkoevgeny.shared.foundation.core.common.error.client.naming.ClientCommonErrorCodes
import io.github.mudrichenkoevgeny.shared.foundation.core.common.error.model.ApiErrorResponse
import io.github.mudrichenkoevgeny.shared.foundation.core.common.error.naming.CommonErrorCodes
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.client.ClientDeviceInfoPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.client.ClientInfoPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.websocket.SocketFrame
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.websocket.WebSocketInitializePayload
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Unit tests verifying JSON serialization and deserialization contracts for core/common payloads.
 */
public class JsonSerializationTest {
    @Test
    public fun testClientInfoPayloadSerialization() {
        val deviceInfo =
            ClientDeviceInfoPayload(
                clientType = "web",
                language = "en",
                deviceId = "dev_123456789",
                deviceName = "Chrome browser",
                appVersion = "1.0.0",
                operationSystemVersion = "Windows 11"
            )

        val payload =
            ClientInfoPayload(
                clientDeviceInfo = deviceInfo,
                userAgent = "Mozilla/5.0",
                ipAddress = "192.168.1.1",
                apiVersion = "1.0.0"
            )

        val serialized = FoundationJson.encodeToString(payload)

        assertTrue(serialized.contains("\"client_device_info\":"))
        assertTrue(serialized.contains("\"user_agent\":\"Mozilla/5.0\""))
        assertTrue(serialized.contains("\"ip_address\":\"192.168.1.1\""))
        assertTrue(serialized.contains("\"api_version\":\"1.0.0\""))
        assertTrue(serialized.contains("\"client_type\":\"web\""))
        assertTrue(serialized.contains("\"device_id\":\"dev_123456789\""))
        assertTrue(serialized.contains("\"operation_system_version\":\"Windows 11\""))

        val deserialized = FoundationJson.decodeFromString<ClientInfoPayload>(serialized)
        assertEquals(payload, deserialized)
    }

    @Test
    public fun testClientInfoPayloadNullabilityHandling() {
        val deviceInfo =
            ClientDeviceInfoPayload(
                clientType = null,
                language = null,
                deviceId = null,
                deviceName = null,
                appVersion = null,
                operationSystemVersion = null
            )

        val payload =
            ClientInfoPayload(
                clientDeviceInfo = deviceInfo,
                userAgent = null,
                ipAddress = null,
                apiVersion = null
            )

        val serialized = FoundationJson.encodeToString(payload)
        val deserialized = FoundationJson.decodeFromString<ClientInfoPayload>(serialized)

        assertEquals(payload, deserialized)
    }

    @Test
    public fun testApiErrorResponseSerialization() {
        val payload =
            ApiErrorResponse(
                id = "err_001",
                code = CommonErrorCodes.BAD_REQUEST,
                message = "Invalid input",
                args = mapOf("field" to "email")
            )

        val serialized = FoundationJson.encodeToString(payload)

        assertTrue(serialized.contains("\"id\":\"err_001\""))
        assertTrue(serialized.contains("\"code\":\"BAD_REQUEST\""))
        assertTrue(serialized.contains("\"message\":\"Invalid input\""))
        assertTrue(serialized.contains("\"args\":{\"field\":\"email\"}"))

        val deserialized = FoundationJson.decodeFromString<ApiErrorResponse>(serialized)
        assertEquals(payload, deserialized)
    }

    @Test
    public fun testApiErrorResponseErrorCodeMapping() {
        val commonErrorCode = CommonErrorCodes.NOT_FOUND
        val responseWithCommonError =
            ApiErrorResponse(
                id = "err_404",
                code = commonErrorCode,
                message = "User not found",
                args = mapOf("resource" to "User")
            )

        val serializedCommon = FoundationJson.encodeToString(responseWithCommonError)
        val deserializedCommon = FoundationJson.decodeFromString<ApiErrorResponse>(serializedCommon)

        assertEquals(CommonErrorCodes.NOT_FOUND, deserializedCommon.code)
        assertEquals("User", deserializedCommon.args["resource"])

        val clientErrorCode = ClientCommonErrorCodes.NO_INTERNET_CONNECTION
        val responseWithClientError =
            ApiErrorResponse(
                id = "err_client_001",
                code = clientErrorCode,
                message = "No active internet connection",
                args = emptyMap()
            )

        val serializedClient = FoundationJson.encodeToString(responseWithClientError)
        val deserializedClient = FoundationJson.decodeFromString<ApiErrorResponse>(serializedClient)

        assertEquals(ClientCommonErrorCodes.NO_INTERNET_CONNECTION, deserializedClient.code)
    }

    @Test
    public fun testWebSocketInitializePayloadSerialization() {
        val payload =
            WebSocketInitializePayload(
                clientType = "mobile",
                language = "ru",
                deviceId = "device_999",
                deviceName = "Pixel 8",
                appVersion = "2.0.0",
                operationSystemVersion = "Android 14",
                apiVersion = "1.1.0"
            )

        val serialized = FoundationJson.encodeToString(payload)

        assertTrue(serialized.contains("\"client_type\":\"mobile\""))
        assertTrue(serialized.contains("\"language\":\"ru\""))
        assertTrue(serialized.contains("\"device_id\":\"device_999\""))
        assertTrue(serialized.contains("\"device_name\":\"Pixel 8\""))
        assertTrue(serialized.contains("\"app_version\":\"2.0.0\""))
        assertTrue(serialized.contains("\"operation_system_version\":\"Android 14\""))
        assertTrue(serialized.contains("\"api_version\":\"1.1.0\""))

        val deserialized = FoundationJson.decodeFromString<WebSocketInitializePayload>(serialized)
        assertEquals(payload, deserialized)
    }

    @Test
    public fun testSocketFrameSerialization() {
        val payloadJson =
            buildJsonObject {
                put("key", "value")
            }

        val frame =
            SocketFrame(
                id = "frame_12345",
                type = "ping",
                payload = payloadJson,
                metadata = mapOf("trace_id" to "tr_abc123"),
                timestamp = 1700000000000L
            )

        val serialized = FoundationJson.encodeToString(frame)

        assertTrue(serialized.contains("\"id\":\"frame_12345\""))
        assertTrue(serialized.contains("\"type\":\"ping\""))
        assertTrue(serialized.contains("\"payload\":{\"key\":\"value\"}"))
        assertTrue(serialized.contains("\"metadata\":{\"trace_id\":\"tr_abc123\"}"))
        assertTrue(serialized.contains("\"timestamp\":1700000000000"))

        val deserialized = FoundationJson.decodeFromString<SocketFrame>(serialized)
        assertEquals(frame, deserialized)
    }

    @Test
    public fun testSocketFrameNullPayloadSerialization() {
        val frame =
            SocketFrame(
                id = "frame_67890",
                type = "pong",
                payload = null,
                metadata = emptyMap(),
                timestamp = 1700000001000L
            )

        val serialized = FoundationJson.encodeToString(frame)
        val deserialized = FoundationJson.decodeFromString<SocketFrame>(serialized)

        assertEquals(frame, deserialized)
    }
}
