package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.user

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.permission.PermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Request body for partially updating a user's account details (management API).
 *
 * @property accountStatus Optional target status as a [UserAccountStatus] serial name.
 * If null, the status remains unchanged.
 * @property permissions Optional set of explicit [PermissionCode] values to replace the current grants.
 * If null, permissions remain unchanged.
 */
@Serializable
data class UpdateUserRequest(
    @SerialName(UserApiFields.ACCOUNT_STATUS)
    val accountStatus: String? = null,

    @SerialName(UserApiFields.PERMISSIONS)
    val permissions: Set<PermissionCode>? = null
)