package io.github.mudrichenkoevgeny.shared.foundation.core.storage.mapper.file

import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.category.StorageCategory
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.StorageFileSummary
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.toFileIdOrThrow
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.file.StorageFileSummaryPayload

/**
 * Extension functions mapping summary storage file models between domain and network representations.
 */

/**
 * Builds domain [StorageFileSummary] from [StorageFileSummaryPayload].
 */
fun StorageFileSummaryPayload.toStorageFileSummary(): StorageFileSummary = StorageFileSummary(
    id = id.toFileIdOrThrow(),
    fileName = fileName,
    fileSizeBytes = fileSizeBytes,
    mimeType = mimeType,
    category = StorageCategory.fromValueOrThrow(category),
    fileUrl = fileUrl
)

/**
 * Builds network [StorageFileSummaryPayload] from domain [StorageFileSummary].
 */
fun StorageFileSummary.toStorageFileSummaryPayload(): StorageFileSummaryPayload = StorageFileSummaryPayload(
    id = id.asHexDashString(),
    fileName = fileName,
    fileSizeBytes = fileSizeBytes,
    mimeType = mimeType,
    category = category.value,
    fileUrl = fileUrl
)
