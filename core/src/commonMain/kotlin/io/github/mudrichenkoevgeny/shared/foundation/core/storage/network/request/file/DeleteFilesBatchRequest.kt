package io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.request.file

import io.github.mudrichenkoevgeny.shared.foundation.core.common.validation.NotEmptyCollectionField
import kotlinx.serialization.Serializable

/**
 * Request payload for batch deleting multiple files simultaneously.
 *
 * @property fileIds List of file identifier strings (hex-dash UUIDs) to be deleted.
 */
@Serializable
public data class DeleteFilesBatchRequest(
    @NotEmptyCollectionField
    public val fileIds: List<String>
)
