package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.identifier

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission.IdentifierPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.identifier.UserIdentifierPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.user.BaseManagementUserRoutes

/**
 * Shared base path segments for user identifiers in the management API.
 *
 * List uses query [UserFilterValues.UserIdentifierFilterValues.USER_ID]. Single-identifier read and delete use
 * only [UserApiPaths.USER_IDENTIFIER_ID] in path. Response shape is always [UserIdentifierPayload]; wire
 * field [CommonApiFields.IS_SENSITIVE_VALUES_MASKED] reflects masking for the caller ([IdentifierPermissionCode]).
 */
object BaseManagementIdentifierRoutes {
    /**
     * Base path for managing user identifiers
     */
    const val BASE_MANAGEMENT_IDENTIFIERS_ROUTE =
        "${BaseManagementUserRoutes.BASE_MANAGEMENT_USERS_ROUTE}/identifiers"

    /**
     * Paginated identifier list.
     */
    const val GET_IDENTIFIERS = BASE_MANAGEMENT_IDENTIFIERS_ROUTE

    /**
     * Path template for one identifier row; path parameter [UserApiPaths.USER_IDENTIFIER_ID].
     */
    const val GET_IDENTIFIER =
        "$BASE_MANAGEMENT_IDENTIFIERS_ROUTE/{${UserApiPaths.USER_IDENTIFIER_ID}}"

    /**
     * Path template for deleting one identifier record; path parameter [UserApiPaths.USER_IDENTIFIER_ID].
     */
    const val DELETE_IDENTIFIER =
        "$BASE_MANAGEMENT_IDENTIFIERS_ROUTE/{${UserApiPaths.USER_IDENTIFIER_ID}}"
}
