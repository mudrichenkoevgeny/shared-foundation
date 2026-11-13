package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user

/**
 * Storage configuration overrides and limits for a specific user.
 *
 * @property quotaBytes Custom maximum total storage quota in bytes, or `null` if using default settings.
 * @property maxUploadSizeBytes Custom maximum single file upload size in bytes, or `null` if using default settings.
 * @property maxPendingUploads Custom maximum concurrent pending uploads count, or `null` if using default settings.
 */
public data class UserStorageConfiguration(
    public val quotaBytes: Long?,
    public val maxUploadSizeBytes: Long?,
    public val maxPendingUploads: Int?
)
