package io.github.mudrichenkoevgeny.shared.foundation.core.storage.serialization

import io.github.mudrichenkoevgeny.shared.foundation.core.common.serialization.FoundationJson
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.category.StorageCategory
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.status.StorageFileStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.visibility.StorageFileVisibility
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.file.FileUrlsPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.file.StorageFilePrivatePayload
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.file.StorageFileSummaryPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.presigned.PresignedUrlPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.storagesettings.ManagementStorageSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.storagesettings.OpenStorageSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.request.presigned.PresignedUrlRequest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Unit tests verifying JSON serialization and deserialization contracts for core/storage payloads.
 */
public class JsonSerializationTest {
    @Test
    public fun testStorageFileSummaryPayloadSerialization() {
        val fileUrls = FileUrlsPayload(
            originalUrl = "https://cdn.example.com/orig.png",
            urlPresets = mapOf("thumbnail" to "https://cdn.example.com/thumb.png")
        )

        val payload = StorageFileSummaryPayload(
            id = "00000000-0000-4000-8000-000000000001",
            fileName = "avatar.png",
            fileSizeBytes = 2048L,
            mimeType = "image/png",
            category = StorageCategory.IMAGE.value,
            visibility = StorageFileVisibility.PUBLIC.value,
            status = StorageFileStatus.READY.value,
            fileUrls = fileUrls,
            createdAt = 1700000000000L
        )

        val serialized = FoundationJson.encodeToString(payload)

        assertTrue(serialized.contains("\"file_id\":\"00000000-0000-4000-8000-000000000001\""))
        assertTrue(serialized.contains("\"file_name\":\"avatar.png\""))
        assertTrue(serialized.contains("\"file_size_bytes\":2048"))
        assertTrue(serialized.contains("\"mime_type\":\"image/png\""))
        assertTrue(serialized.contains("\"category\":\"image\""))
        assertTrue(serialized.contains("\"visibility\":\"public\""))
        assertTrue(serialized.contains("\"status\":\"ready\""))
        assertTrue(serialized.contains("\"file_urls\":"))
        assertTrue(serialized.contains("\"created_at\":1700000000000"))

        val deserialized = FoundationJson.decodeFromString<StorageFileSummaryPayload>(serialized)
        assertEquals(payload, deserialized)
    }

    @Test
    public fun testStorageFilePrivatePayloadSerialization() {
        val payload = StorageFilePrivatePayload(
            id = "00000000-0000-4000-8000-000000000001",
            ownerUserId = "usr_100",
            fileName = "doc.pdf",
            fileSizeBytes = 4096L,
            mimeType = "application/pdf",
            category = StorageCategory.DOCUMENT.value,
            visibility = StorageFileVisibility.PRIVATE.value,
            status = StorageFileStatus.READY.value,
            targetScope = "document",
            fileUrls = null,
            createdAt = 1700000000000L
        )

        val serialized = FoundationJson.encodeToString(payload)

        assertTrue(serialized.contains("\"owner_user_id\":\"usr_100\""))
        assertTrue(serialized.contains("\"target_scope\":\"document\""))

        val deserialized = FoundationJson.decodeFromString<StorageFilePrivatePayload>(serialized)
        assertEquals(payload, deserialized)
    }

    @Test
    public fun testPresignedUrlPayloadSerialization() {
        val payload = PresignedUrlPayload(
            fileId = "00000000-0000-4000-8000-000000000001",
            uploadUrl = "https://s3.amazonaws.com/bucket/key?signature=abc",
            fileUrl = "https://cdn.example.com/key",
            expiresAt = 1700003600000L
        )

        val serialized = FoundationJson.encodeToString(payload)

        assertTrue(serialized.contains("\"file_id\":\"00000000-0000-4000-8000-000000000001\""))
        assertTrue(serialized.contains("\"upload_url\":\"https://s3.amazonaws.com/bucket/key?signature=abc\""))
        assertTrue(serialized.contains("\"file_url\":\"https://cdn.example.com/key\""))
        assertTrue(serialized.contains("\"expires_at\":1700003600000"))

        val deserialized = FoundationJson.decodeFromString<PresignedUrlPayload>(serialized)
        assertEquals(payload, deserialized)
    }

    @Test
    public fun testPresignedUrlRequestSerialization() {
        val request = PresignedUrlRequest(
            fileName = "document.pdf",
            fileSizeBytes = 1048576L,
            mimeType = "application/pdf",
            category = StorageCategory.DOCUMENT.value,
            visibility = StorageFileVisibility.PRIVATE.value,
            targetScope = "user_attachment"
        )

        val serialized = FoundationJson.encodeToString(request)

        assertTrue(serialized.contains("\"file_name\":\"document.pdf\""))
        assertTrue(serialized.contains("\"file_size_bytes\":1048576"))
        assertTrue(serialized.contains("\"mime_type\":\"application/pdf\""))
        assertTrue(serialized.contains("\"category\":\"document\""))
        assertTrue(serialized.contains("\"visibility\":\"private\""))
        assertTrue(serialized.contains("\"target_scope\":\"user_attachment\""))

        val deserialized = FoundationJson.decodeFromString<PresignedUrlRequest>(serialized)
        assertEquals(request, deserialized)
    }

    @Test
    public fun testManagementStorageSettingsPayloadSerialization() {
        val payload = ManagementStorageSettingsPayload(
            minUploadSizeBytes = 1024L,
            maxUploadSizeBytes = 104857600L,
            totalStorageQuotaForOpenUserBytes = 1073741824L,
            totalStorageQuotaForManagementUserBytes = null,
            presignedUrlExpirationSeconds = 3600,
            maxPendingUploadsForOpenUser = 5,
            maxPendingUploadsForManagementUser = 20,
            isDeduplicationEnabled = true,
            deletedFilesCleanupIntervalSeconds = 86400
        )

        val serialized = FoundationJson.encodeToString(payload)

        assertTrue(serialized.contains("\"min_upload_size_bytes\":1024"))
        assertTrue(serialized.contains("\"max_upload_size_bytes\":104857600"))
        assertTrue(serialized.contains("\"total_storage_quota_for_open_user_bytes\":1073741824"))
        assertTrue(serialized.contains("\"presigned_url_expiration_seconds\":3600"))
        assertTrue(serialized.contains("\"max_pending_uploads_for_open_user\":5"))
        assertTrue(serialized.contains("\"max_pending_uploads_for_management_user\":20"))
        assertTrue(serialized.contains("\"is_deduplication_enabled\":true"))
        assertTrue(serialized.contains("\"deleted_files_cleanup_interval_seconds\":86400"))

        val deserialized = FoundationJson.decodeFromString<ManagementStorageSettingsPayload>(serialized)
        assertEquals(payload, deserialized)
    }

    @Test
    public fun testOpenStorageSettingsPayloadSerialization() {
        val payload = OpenStorageSettingsPayload(
            minUploadSizeBytes = 1024L,
            maxUploadSizeBytes = 10485760L
        )

        val serialized = FoundationJson.encodeToString(payload)

        assertTrue(serialized.contains("\"min_upload_size_bytes\":1024"))
        assertTrue(serialized.contains("\"max_upload_size_bytes\":10485760"))

        val deserialized = FoundationJson.decodeFromString<OpenStorageSettingsPayload>(serialized)
        assertEquals(payload, deserialized)
    }
}
