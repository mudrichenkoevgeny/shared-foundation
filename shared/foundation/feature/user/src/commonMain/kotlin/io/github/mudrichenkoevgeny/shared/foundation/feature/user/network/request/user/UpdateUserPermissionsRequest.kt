package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.user

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.permission.PermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Request body for replacing a user's explicit permission grants (management API).
 *
 * @property permissions The full set of explicit [PermissionCode] values to persist for the user after this request.
 * The server defines how this set combines with role defaults. An empty set clears explicit grants when the product
 * allows it.
 */
@Serializable
data class UpdateUserPermissionsRequest(
    @SerialName(UserApiFields.PERMISSIONS)
    val permissions: Set<PermissionCode>
)
