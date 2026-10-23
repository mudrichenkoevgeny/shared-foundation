package io.github.mudrichenkoevgeny.shared.foundation.core.storage.mapper.file

import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.category.StorageCategory
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.StorageFilePrivate
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.toFileIdOrThrow
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.status.StorageFileStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.visibility.StorageFileVisibility
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.file.StorageFilePrivatePayload
import kotlin.time.Instant

/**
 * Extension functions mapping detailed private storage file models between domain and network representations.
 */

/**
 * Builds domain [StorageFilePrivate] from [StorageFilePrivatePayload].
 */
fun StorageFilePrivatePayload.toStorageFilePrivate(): StorageFilePrivate = StorageFilePrivate(
    id = id.toFileIdOrThrow(),
    ownerUserId = ownerUserId,
    fileName = fileName,
    fileSizeBytes = fileSizeBytes,
    mimeType = mimeType,
    category = StorageCategory.fromValueOrThrow(category),
    visibility = StorageFileVisibility.fromValueOrThrow(visibility),
    status = StorageFileStatus.fromValueOrThrow(status),
    targetScope = targetScope,
    fileUrl = fileUrl,
    createdAt = Instant.fromEpochMilliseconds(createdAt),
    updatedAt = updatedAt?.let { Instant.fromEpochMilliseconds(it) }
)

/**
 * Builds network [StorageFilePrivatePayload] from domain [StorageFilePrivate].
 */
fun StorageFilePrivate.toStorageFilePrivatePayload(): StorageFilePrivatePayload = StorageFilePrivatePayload(
    id = id.asHexDashString(),
    ownerUserId = ownerUserId,
    fileName = fileName,
    fileSizeBytes = fileSizeBytes,
    mimeType = mimeType,
    category = category.value,
    visibility = visibility.value,
    status = status.value,
    targetScope = targetScope,
    fileUrl = fileUrl,
    createdAt = createdAt.toEpochMilliseconds(),
    updatedAt = updatedAt?.toEpochMilliseconds()
)
