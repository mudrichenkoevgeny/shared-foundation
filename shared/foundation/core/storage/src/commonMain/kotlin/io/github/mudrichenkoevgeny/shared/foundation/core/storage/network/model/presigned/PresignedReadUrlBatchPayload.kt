package io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.presigned

import kotlinx.serialization.Serializable

/**
 * Wire payload representing the result of a batch presigned URL request.
 *
 * @property urls Map where the key is the file identifier and the value is the temporary presigned GET URL,
 * or `null` if the file does not exist or access was denied.
 */
@Serializable
data class PresignedReadUrlBatchPayload(
    val urls: Map<String, String?>
)
