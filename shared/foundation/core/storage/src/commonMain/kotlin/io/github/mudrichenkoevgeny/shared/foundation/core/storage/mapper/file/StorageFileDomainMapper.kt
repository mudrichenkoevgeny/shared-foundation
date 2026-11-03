package io.github.mudrichenkoevgeny.shared.foundation.core.storage.mapper.file

import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.StorageFileInternal
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.StorageFilePrivate
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.StorageFileSummary

/**
 * Extension functions mapping internal storage file domain models ([StorageFileInternal])
 * into public domain projections ([StorageFilePrivate] and [StorageFileSummary]).
 */

/**
 * Builds public [StorageFilePrivate] domain model from internal [StorageFileInternal].
 */
fun StorageFileInternal.toStorageFilePrivate(): StorageFilePrivate = StorageFilePrivate(
    id = id,
    ownerUserId = ownerUserId,
    fileName = fileName,
    fileSizeBytes = fileSizeBytes,
    mimeType = mimeType,
    category = category,
    visibility = visibility,
    status = status,
    targetScope = targetScope,
    fileUrls = fileUrls,
    createdAt = createdAt,
    updatedAt = updatedAt
)

/**
 * Builds public [StorageFileSummary] domain model from internal [StorageFileInternal].
 */
fun StorageFileInternal.toStorageFileSummary(): StorageFileSummary = StorageFileSummary(
    id = id,
    fileName = fileName,
    fileSizeBytes = fileSizeBytes,
    mimeType = mimeType,
    category = category,
    visibility = visibility,
    status = status,
    fileUrls = fileUrls,
    createdAt = createdAt
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
    visibility = visibility,
    status = status,
    fileUrls = fileUrls,
    createdAt = createdAt
)
