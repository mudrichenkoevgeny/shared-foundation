package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.user

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserStorageConfiguration
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Storage configuration wire payload for user-specific limits and overrides.
 *
 * Wire keys use `snake_case` via [UserApiFields]. Aligns with domain [UserStorageConfiguration].
 *
 * @property quotaBytes [UserStorageConfiguration.quotaBytes].
 * @property maxUploadSizeBytes [UserStorageConfiguration.maxUploadSizeBytes].
 * @property maxPendingUploads [UserStorageConfiguration.maxPendingUploads].
 */
@Serializable
data class UserStorageConfigurationPayload(
    @SerialName(UserApiFields.QUOTA_BYTES)
    val quotaBytes: Long?,

    @SerialName(UserApiFields.MAX_UPLOAD_SIZE_BYTES)
    val maxUploadSizeBytes: Long?,

    @SerialName(UserApiFields.MAX_PENDING_UPLOADS)
    val maxPendingUploads: Int?
)
