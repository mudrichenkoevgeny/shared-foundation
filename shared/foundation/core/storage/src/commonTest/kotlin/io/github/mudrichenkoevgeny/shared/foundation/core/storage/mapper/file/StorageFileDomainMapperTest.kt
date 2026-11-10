package io.github.mudrichenkoevgeny.shared.foundation.core.storage.mapper.file

import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.category.StorageCategory
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.FileUrlPresets
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.FileUrls
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.StorageFileInternal
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.StorageFilePrivate
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.toFileIdOrThrow
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.status.StorageFileStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.visibility.StorageFileVisibility
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.file.StorageFilePrivatePayload
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.file.StorageFileSummaryPayload
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Instant

class StorageFileDomainMapperTest {
    private val testFileUrls = FileUrls(
        originalUrl = "https://cdn.example.com/orig.png",
        urlPresets = mapOf(
            FileUrlPresets.THUMBNAIL to "https://cdn.example.com/thumb.png",
            FileUrlPresets.PREVIEW to "https://cdn.example.com/prev.png"
        )
    )

    private val testFileUrlsPayload = testFileUrls.toFileUrlsPayload()

    private val testFileId = "00000000-0000-4000-8000-000000000001".toFileIdOrThrow()
    private val testCreatedAt = Instant.fromEpochMilliseconds(1700000000000L)

    @Test
    fun testStorageFilePrivatePayloadMapping() {
        val payload = StorageFilePrivatePayload(
            id = "00000000-0000-4000-8000-000000000001",
            ownerUserId = "usr_000000000001",
            fileName = "test.png",
            fileSizeBytes = 1024L,
            mimeType = "image/png",
            category = StorageCategory.IMAGE.value,
            visibility = StorageFileVisibility.PUBLIC.value,
            status = StorageFileStatus.READY.value,
            targetScope = "avatar",
            fileUrls = testFileUrlsPayload,
            createdAt = 1700000000000L
        )

        val domain = payload.toStorageFilePrivate()

        assertEquals(testFileUrls, domain.fileUrls)

        val mappedPayload = domain.toStorageFilePrivatePayload()
        assertEquals(testFileUrlsPayload, mappedPayload.fileUrls)
    }

    @Test
    fun testStorageFileSummaryPayloadMapping() {
        val payload = StorageFileSummaryPayload(
            id = "00000000-0000-4000-8000-000000000001",
            fileName = "test.png",
            fileSizeBytes = 1024L,
            mimeType = "image/png",
            category = StorageCategory.IMAGE.value,
            visibility = StorageFileVisibility.PUBLIC.value,
            status = StorageFileStatus.READY.value,
            fileUrls = testFileUrlsPayload,
            createdAt = 1700000000000L
        )

        val domain = payload.toStorageFileSummary()

        assertEquals(testFileUrls, domain.fileUrls)

        val mappedPayload = domain.toStorageFileSummaryPayload()
        assertEquals(testFileUrlsPayload, mappedPayload.fileUrls)
    }

    @Test
    fun testStorageFilePrivateToSummaryMapping() {
        val privateModel = StorageFilePrivate(
            id = testFileId,
            ownerUserId = "usr_000000000001",
            fileName = "test.png",
            fileSizeBytes = 1024L,
            mimeType = "image/png",
            category = StorageCategory.IMAGE,
            visibility = StorageFileVisibility.PUBLIC,
            status = StorageFileStatus.READY,
            targetScope = "avatar",
            fileUrls = testFileUrls,
            createdAt = testCreatedAt,
            updatedAt = null
        )

        val summaryModel = privateModel.toStorageFileSummary()

        assertEquals(testFileId, summaryModel.id)
        assertEquals("test.png", summaryModel.fileName)
        assertEquals(1024L, summaryModel.fileSizeBytes)
        assertEquals("image/png", summaryModel.mimeType)
        assertEquals(StorageCategory.IMAGE, summaryModel.category)
        assertEquals(StorageFileVisibility.PUBLIC, summaryModel.visibility)
        assertEquals(StorageFileStatus.READY, summaryModel.status)
        assertEquals(testFileUrls, summaryModel.fileUrls)
        assertEquals(testCreatedAt, summaryModel.createdAt)
    }

    @Test
    fun testStorageFileInternalMapping() {
        val internalModel = StorageFileInternal(
            id = testFileId,
            ownerUserId = "usr_000000000001",
            fileName = "test.png",
            fileSizeBytes = 1024L,
            mimeType = "image/png",
            category = StorageCategory.IMAGE,
            visibility = StorageFileVisibility.PUBLIC,
            status = StorageFileStatus.READY,
            targetScope = "avatar",
            contentHash = null,
            s3Bucket = "my-bucket",
            s3Key = "files/test.png",
            fileUrls = testFileUrls,
            createdAt = testCreatedAt,
            updatedAt = null
        )

        val privateModel = internalModel.toStorageFilePrivate()

        assertEquals(testFileId, privateModel.id)
        assertEquals("usr_000000000001", privateModel.ownerUserId)
        assertEquals("test.png", privateModel.fileName)
        assertEquals(1024L, privateModel.fileSizeBytes)
        assertEquals("image/png", privateModel.mimeType)
        assertEquals(StorageCategory.IMAGE, privateModel.category)
        assertEquals(StorageFileVisibility.PUBLIC, privateModel.visibility)
        assertEquals(StorageFileStatus.READY, privateModel.status)
        assertEquals("avatar", privateModel.targetScope)
        assertEquals(testFileUrls, privateModel.fileUrls)
        assertEquals(testCreatedAt, privateModel.createdAt)

        val summaryModel = internalModel.toStorageFileSummary()

        assertEquals(testFileId, summaryModel.id)
        assertEquals("test.png", summaryModel.fileName)
        assertEquals(1024L, summaryModel.fileSizeBytes)
        assertEquals("image/png", summaryModel.mimeType)
        assertEquals(StorageCategory.IMAGE, summaryModel.category)
        assertEquals(StorageFileVisibility.PUBLIC, summaryModel.visibility)
        assertEquals(StorageFileStatus.READY, summaryModel.status)
        assertEquals(testFileUrls, summaryModel.fileUrls)
        assertEquals(testCreatedAt, summaryModel.createdAt)
    }
}
