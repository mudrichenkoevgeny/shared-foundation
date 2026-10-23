package io.github.mudrichenkoevgeny.shared.foundation.core.storage.mapper.presigned

import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.toFileIdOrThrow
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.presigned.PresignedUrl
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.presigned.PresignedUrlPayload
import kotlin.time.Instant

/**
 * Extension functions mapping presigned upload URL models between domain and network representations.
 */

/**
 * Builds domain [PresignedUrl] from [PresignedUrlPayload].
 */
fun PresignedUrlPayload.toPresignedUrl(): PresignedUrl = PresignedUrl(
    fileId = fileId.toFileIdOrThrow(),
    uploadUrl = uploadUrl,
    fileUrl = fileUrl,
    expiresAt = Instant.fromEpochMilliseconds(expiresAt)
)

/**
 * Builds network [PresignedUrlPayload] from domain [PresignedUrl].
 */
fun PresignedUrl.toPresignedUrlPayload(): PresignedUrlPayload = PresignedUrlPayload(
    fileId = fileId.asHexDashString(),
    uploadUrl = uploadUrl,
    fileUrl = fileUrl,
    expiresAt = expiresAt.toEpochMilliseconds()
)
