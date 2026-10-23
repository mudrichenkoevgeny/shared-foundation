package io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.presigned

import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.FileId

/**
 * Domain model representing a map of requested files to their presigned S3 GET URLs.
 *
 * @property urls Map linking a specific [FileId] to its temporary access URL, or `null` if the file could not be accessed.
 */
data class PresignedReadUrlBatch(
    val urls: Map<FileId, String?>
)
