package io.github.mudrichenkoevgeny.shared.foundation.core.storage.mapper.file

import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.StorageFileInternal
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.StorageFilePrivate
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.StorageFileSummary

/**
 * Extension functions mapping internal storage file domain models ([StorageFileInternal])
 * into public domain projections ([StorageFilePrivate] and [StorageFileSummary]).
 */

/**
 * Builds public [StorageFilePrivate] domain model from internal [StorageFileInternal] using the provided [fileUrl].
 *
 * @param fileUrl Dynamically generated CDN URL or presigned read URL.
 */
fun StorageFileInternal.toStorageFilePrivate(fileUrl: String): StorageFilePrivate = StorageFilePrivate(
    id = id,
    ownerUserId = ownerUserId,
    fileName = fileName,
    fileSizeBytes = fileSizeBytes,
    mimeType = mimeType,
    category = category,
    visibility = visibility,
    status = status,
    targetScope = targetScope,
    fileUrl = fileUrl,
    createdAt = createdAt,
    updatedAt = updatedAt
)

/**
 * Builds public [StorageFileSummary] domain model from internal [StorageFileInternal] using the provided [fileUrl].
 *
 * @param fileUrl Dynamically generated CDN URL or presigned read URL.
 */
fun StorageFileInternal.toStorageFileSummary(fileUrl: String): StorageFileSummary = StorageFileSummary(
    id = id,
    fileName = fileName,
    fileSizeBytes = fileSizeBytes,
    mimeType = mimeType,
    category = category,
    fileUrl = fileUrl
)

/**
 * Builds public [StorageFileSummary] domain model from detailed [StorageFilePrivate].
 */
fun StorageFilePrivate.toStorageFileSummary(): StorageFileSummary = StorageFileSummary(
    id = id,
    fileName = fileName,
    fileSizeBytes = fileSizeBytes,
    mimeType = mimeType,
    category = category,
    fileUrl = fileUrl
)
