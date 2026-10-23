package io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.storagesettings

/**
 * Domain model for platform-wide storage settings accessible by all clients.
 *
 * Used by frontends to apply client-side validation before requesting uploads.
 *
 * @property minUploadSizeBytes The minimum permitted file size in bytes for a single upload.
 * @property maxUploadSizeBytes The maximum permitted file size in bytes for a single upload across the entire platform.
 */
data class OpenStorageSettings(
    val minUploadSizeBytes: Long,
    val maxUploadSizeBytes: Long
)
