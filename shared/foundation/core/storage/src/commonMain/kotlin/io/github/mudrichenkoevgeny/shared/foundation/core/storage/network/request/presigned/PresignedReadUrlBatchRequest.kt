package io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.request.presigned

import io.github.mudrichenkoevgeny.shared.foundation.core.common.validation.RequiredField
import kotlinx.serialization.Serializable

/**
 * Request payload for batch generating temporary presigned S3 GET URLs.
 * Used internally by other services (e.g. Quiz Service) to fetch access links for multiple files simultaneously.
 *
 * @property fileIds List of internal file identifier strings (hex-dash UUIDs) to request access to.
 */
@Serializable
data class PresignedReadUrlBatchRequest(
    @RequiredField
    val fileIds: List<String>
)
