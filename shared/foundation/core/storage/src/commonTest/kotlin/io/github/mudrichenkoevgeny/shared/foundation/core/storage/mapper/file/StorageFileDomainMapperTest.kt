package io.github.mudrichenkoevgeny.shared.foundation.core.storage.mapper.file

import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.category.StorageCategory
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
        thumbnailUrl = "https://cdn.example.com/thumb.png",
        previewUrl = "https://cdn.example.com/prev.png"
    )

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
            fileUrls = testFileUrls,
            createdAt = 1700000000000L
        )

        val domain = payload.toStorageFilePrivate()

        assertEquals(testFileUrls, domain.fileUrls)

        val mappedPayload = domain.toStorageFilePrivatePayload()
        assertEquals(testFileUrls, mappedPayload.fileUrls)
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
            fileUrls = testFileUrls,
            createdAt = 1700000000000L
        )

        val domain = payload.toStorageFileSummary()

        assertEquals(testFileUrls, domain.fileUrls)

        val mappedPayload = domain.toStorageFileSummaryPayload()
        assertEquals(testFileUrls, mappedPayload.fileUrls)
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

        assertEquals(testFileUrls, summaryModel.fileUrls)
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
            s3Bucket = "my-bucket",
            s3Key = "files/test.png",
            fileUrls = testFileUrls,
            createdAt = testCreatedAt,
            updatedAt = null
        )

        val privateModel = internalModel.toStorageFilePrivate()
        assertEquals(testFileUrls, privateModel.fileUrls)

        val summaryModel = internalModel.toStorageFileSummary()
        assertEquals(testFileUrls, summaryModel.fileUrls)
    }
}
