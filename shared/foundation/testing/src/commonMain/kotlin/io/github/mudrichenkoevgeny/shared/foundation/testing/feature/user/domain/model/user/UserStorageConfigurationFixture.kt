package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.user

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserStorageConfiguration

/**
 * Creates a test [UserStorageConfiguration] domain instance.
 *
 * @param quotaBytes Custom total storage quota in bytes.
 * @param maxUploadSizeBytes Custom maximum single file upload size in bytes.
 * @param maxPendingUploads Custom maximum concurrent pending uploads count.
 */
fun createTestUserStorageConfiguration(
    quotaBytes: Long? = 1_073_741_824L,
    maxUploadSizeBytes: Long? = 104_857_600L,
    maxPendingUploads: Int? = 5
) = UserStorageConfiguration(
    quotaBytes = quotaBytes,
    maxUploadSizeBytes = maxUploadSizeBytes,
    maxPendingUploads = maxPendingUploads
)
