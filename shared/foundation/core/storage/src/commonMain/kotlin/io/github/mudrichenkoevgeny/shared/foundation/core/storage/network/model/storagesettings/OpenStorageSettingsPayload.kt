package io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.storagesettings

import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.contract.StorageApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Wire payload representing platform-wide storage settings for all clients.
 *
 * @property minUploadSizeBytes The minimum permitted file size in bytes for a single upload.
 * @property maxUploadSizeBytes The maximum permitted file size in bytes for a single upload across the entire platform.
 */
@Serializable
data class OpenStorageSettingsPayload(
    @SerialName(StorageApiFields.MIN_UPLOAD_SIZE_BYTES)
    val minUploadSizeBytes: Long,

    @SerialName(StorageApiFields.MAX_UPLOAD_SIZE_BYTES)
    val maxUploadSizeBytes: Long
)
