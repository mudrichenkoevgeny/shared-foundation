package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.model.user

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.user.UserStorageConfigurationPayload

/**
 * Creates a test [UserStorageConfigurationPayload] wire instance.
 *
 * @param quotaBytes Custom total storage quota in bytes.
 * @param maxUploadSizeBytes Custom maximum single file upload size in bytes.
 * @param maxPendingUploads Custom maximum concurrent pending uploads count.
 */
fun createTestUserStorageConfigurationPayload(
    quotaBytes: Long? = 1_073_741_824L,
    maxUploadSizeBytes: Long? = 104_857_600L,
    maxPendingUploads: Int? = 5
) = UserStorageConfigurationPayload(
    quotaBytes = quotaBytes,
    maxUploadSizeBytes = maxUploadSizeBytes,
    maxPendingUploads = maxPendingUploads
)
