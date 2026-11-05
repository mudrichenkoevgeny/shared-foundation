package io.github.mudrichenkoevgeny.shared.foundation.core.storage.mapper.file

import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.StorageFileInternal
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.StorageFilePrivate
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.StorageFileSummary

/**
 * Builds public [StorageFilePrivate] domain model from internal [StorageFileInternal].
 */
public fun StorageFileInternal.toStorageFilePrivate(): StorageFilePrivate =
    StorageFilePrivate(
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
public fun StorageFileInternal.toStorageFileSummary(): StorageFileSummary =
    StorageFileSummary(
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
public fun StorageFilePrivate.toStorageFileSummary(): StorageFileSummary =
    StorageFileSummary(
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
