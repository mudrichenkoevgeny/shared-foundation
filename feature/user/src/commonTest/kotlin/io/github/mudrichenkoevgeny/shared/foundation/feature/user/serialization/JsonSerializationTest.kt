package io.github.mudrichenkoevgeny.shared.foundation.feature.user.serialization

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.client.ClientDeviceInfoPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.common.serialization.FoundationJson
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.file.FileUrlsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.data.AuthDataPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.identifier.UserIdentifierPrivatePayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.identifier.UserIdentifierSummaryPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.session.UserSessionPrivatePayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.session.UserSessionSummaryPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.token.SessionTokenPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.user.UserPrivatePayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.user.UserSummaryPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.unlock.UnlockByEmailConfirmationRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.unlock.UnlockByPhoneConfirmationRequest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Unit tests verifying JSON serialization and deserialization contracts for feature/user payloads.
 */
class JsonSerializationTest {
    @Test
    fun testUserSummaryPayloadSerialization() {
        val avatarUrls = FileUrlsPayload(
            originalUrl = "https://cdn.example.com/orig.png",
            urlPresets = mapOf("thumbnail" to "https://cdn.example.com/thumb.png")
        )

        val payload = UserSummaryPayload(
            id = "00000000-0000-4000-8000-000000000001",
            role = UserRole.USER.value,
            accountStatus = UserAccountStatus.ACTIVE.value,
            nickname = "johndoe",
            displayName = "John Doe",
            avatarUrls = avatarUrls
        )

        val serialized = FoundationJson.encodeToString(payload)

        assertTrue(serialized.contains("\"id\":\"00000000-0000-4000-8000-000000000001\""))
        assertTrue(serialized.contains("\"role\":\"user\""))
        assertTrue(serialized.contains("\"account_status\":\"active\""))
        assertTrue(serialized.contains("\"nickname\":\"johndoe\""))
        assertTrue(serialized.contains("\"display_name\":\"John Doe\""))
        assertTrue(serialized.contains("\"avatar_urls\":"))

        val deserialized = FoundationJson.decodeFromString<UserSummaryPayload>(serialized)
        assertEquals(payload, deserialized)
    }

    @Test
    fun testUserPrivatePayloadSerialization() {
        val payload = UserPrivatePayload(
            id = "00000000-0000-4000-8000-000000000001",
            role = UserRole.USER.value,
            accountStatus = UserAccountStatus.ACTIVE.value,
            authorityLevel = 10,
            permissionCodes = setOf("USER_READ", "USER_WRITE"),
            isTotpEnabled = true,
            createdAt = 1700000000000L,
            lockoutType = "none",
            nickname = "johndoe",
            displayName = "John Doe",
            avatarUrls = null
        )

        val serialized = FoundationJson.encodeToString(payload)

        assertTrue(serialized.contains("\"authority_level\":10"))
        assertTrue(serialized.contains("\"permission_codes\":"))
        assertTrue(serialized.contains("\"USER_READ\""))
        assertTrue(serialized.contains("\"USER_WRITE\""))
        assertTrue(serialized.contains("\"is_totp_enabled\":true"))
        assertTrue(serialized.contains("\"account_lockout_type\":\"none\""))

        val deserialized = FoundationJson.decodeFromString<UserPrivatePayload>(serialized)
        assertEquals(payload, deserialized)
    }

    @Test
    fun testAuthDataPayloadSerialization() {
        val userPrivate = UserPrivatePayload(
            id = "00000000-0000-4000-8000-000000000001",
            role = UserRole.USER.value,
            accountStatus = UserAccountStatus.ACTIVE.value,
            authorityLevel = 10,
            permissionCodes = setOf("USER_READ"),
            isTotpEnabled = false,
            createdAt = 1700000000000L,
            lockoutType = "none",
            nickname = "johndoe",
            displayName = "John Doe",
            avatarUrls = null
        )

        val sessionToken = SessionTokenPayload(
            accessToken = "acc_token_123",
            refreshToken = "ref_token_456",
            expiresAt = 1700086400000L,
            tokenType = "Bearer",
            sessionId = "00000000-0000-4000-8000-000000000005",
            identifierId = "00000000-0000-4000-8000-000000000006"
        )

        val payload = AuthDataPayload(
            userPrivatePayload = userPrivate,
            sessionTokenPayload = sessionToken
        )

        val serialized = FoundationJson.encodeToString(payload)

        assertTrue(serialized.contains("\"user\":"))
        assertTrue(serialized.contains("\"session_token\":"))
        assertTrue(serialized.contains("\"access_token\":\"acc_token_123\""))

        val deserialized = FoundationJson.decodeFromString<AuthDataPayload>(serialized)
        assertEquals(payload, deserialized)
    }

    @Test
    fun testUserSessionSummaryPayloadSerialization() {
        val deviceInfo = ClientDeviceInfoPayload(
            clientType = "web",
            language = "en",
            deviceId = "dev_100",
            deviceName = "Chrome",
            appVersion = "1.0.0",
            operationSystemVersion = "Windows"
        )

        val payload = UserSessionSummaryPayload(
            id = "00000000-0000-4000-8000-000000000001",
            userId = "usr_000000000001",
            clientDeviceInfo = deviceInfo,
            identifierDisplayName = "john.doe@example.com",
            identifierAuthProvider = UserAuthProvider.EMAIL.value,
            lastAccessedAt = 1700000000000L,
            expiresAt = 1700086400000L
        )

        val serialized = FoundationJson.encodeToString(payload)

        assertTrue(serialized.contains("\"user_id\":\"usr_000000000001\""))
        assertTrue(serialized.contains("\"client_device_info\":"))
        assertTrue(serialized.contains("\"identifier_display_name\":\"john.doe@example.com\""))
        assertTrue(serialized.contains("\"identifier_auth_provider\":\"email\""))
        assertTrue(serialized.contains("\"last_accessed_at\":1700000000000"))

        val deserialized = FoundationJson.decodeFromString<UserSessionSummaryPayload>(serialized)
        assertEquals(payload, deserialized)
    }

    @Test
    fun testUserSessionPrivatePayloadSerialization() {
        val deviceInfo = ClientDeviceInfoPayload(
            clientType = "mobile",
            language = "en",
            deviceId = "dev_200",
            deviceName = "Pixel 8",
            appVersion = "2.0.0",
            operationSystemVersion = "Android 14"
        )

        val payload = UserSessionPrivatePayload(
            id = "00000000-0000-4000-8000-000000000002",
            userId = "usr_000000000001",
            userRole = UserRole.USER.value,
            identifier = "john.doe@example.com",
            identifierId = "00000000-0000-4000-8000-000000000003",
            identifierDisplayName = "john.doe@example.com",
            identifierAuthProvider = UserAuthProvider.EMAIL.value,
            clientDeviceInfo = deviceInfo,
            userAgent = "MobileApp/2.0",
            ipAddress = "10.0.0.1",
            expiresAt = 1700086400000L,
            lastAccessedAt = 1700000000000L,
            lastReauthenticatedAt = 1700000000000L,
            isSensitiveValuesMasked = false,
            createdAt = 1700000000000L,
            updatedAt = null
        )

        val serialized = FoundationJson.encodeToString(payload)

        assertTrue(serialized.contains("\"user_id\":\"usr_000000000001\""))
        assertTrue(serialized.contains("\"user_role\":\"user\""))
        assertTrue(serialized.contains("\"identifier\":\"john.doe@example.com\""))
        assertTrue(serialized.contains("\"identifier_id\":\"00000000-0000-4000-8000-000000000003\""))
        assertTrue(serialized.contains("\"user_agent\":\"MobileApp/2.0\""))

        val deserialized = FoundationJson.decodeFromString<UserSessionPrivatePayload>(serialized)
        assertEquals(payload, deserialized)
    }

    @Test
    fun testUserIdentifierSummaryPayloadSerialization() {
        val payload = UserIdentifierSummaryPayload(
            id = "00000000-0000-4000-8000-000000000001",
            userId = "usr_000000000001",
            userAuthProvider = UserAuthProvider.EMAIL.value,
            identifier = "john.doe@example.com",
            displayName = "Primary Email"
        )

        val serialized = FoundationJson.encodeToString(payload)

        assertTrue(serialized.contains("\"user_id\":\"usr_000000000001\""))
        assertTrue(serialized.contains("\"user_auth_provider\":\"email\""))
        assertTrue(serialized.contains("\"identifier\":\"john.doe@example.com\""))
        assertTrue(serialized.contains("\"display_name\":\"Primary Email\""))

        val deserialized = FoundationJson.decodeFromString<UserIdentifierSummaryPayload>(serialized)
        assertEquals(payload, deserialized)
    }

    @Test
    fun testUserIdentifierPrivatePayloadSerialization() {
        val payload = UserIdentifierPrivatePayload(
            id = "00000000-0000-4000-8000-000000000001",
            userId = "usr_000000000001",
            userAuthProvider = UserAuthProvider.EMAIL.value,
            identifier = "john.doe@example.com",
            displayName = "Primary Email",
            externalProviderEmail = null,
            isSensitiveValuesMasked = false,
            createdAt = 1700000000000L,
            updatedAt = 1700003600000L
        )

        val serialized = FoundationJson.encodeToString(payload)

        assertTrue(serialized.contains("\"user_id\":\"usr_000000000001\""))
        assertTrue(serialized.contains("\"user_auth_provider\":\"email\""))
        assertTrue(serialized.contains("\"identifier\":\"john.doe@example.com\""))
        assertTrue(serialized.contains("\"display_name\":\"Primary Email\""))
        assertTrue(serialized.contains("\"is_sensitive_values_masked\":false"))

        val deserialized = FoundationJson.decodeFromString<UserIdentifierPrivatePayload>(serialized)
        assertEquals(payload, deserialized)
    }

    @Test
    fun testUnlockByEmailConfirmationRequestSerialization() {
        val request = UnlockByEmailConfirmationRequest(
            email = "user@example.com",
            confirmationCode = "123456"
        )

        val serialized = FoundationJson.encodeToString(request)

        assertTrue(serialized.contains("\"email\":\"user@example.com\""))
        assertTrue(serialized.contains("\"confirmation_code\":\"123456\""))

        val deserialized = FoundationJson.decodeFromString<UnlockByEmailConfirmationRequest>(serialized)
        assertEquals(request, deserialized)
    }

    @Test
    fun testUnlockByPhoneConfirmationRequestSerialization() {
        val request = UnlockByPhoneConfirmationRequest(
            phoneNumber = "+1234567890",
            confirmationCode = "654321"
        )

        val serialized = FoundationJson.encodeToString(request)

        assertTrue(serialized.contains("\"phone_number\":\"+1234567890\""))
        assertTrue(serialized.contains("\"confirmation_code\":\"654321\""))

        val deserialized = FoundationJson.decodeFromString<UnlockByPhoneConfirmationRequest>(serialized)
        assertEquals(request, deserialized)
    }
}
