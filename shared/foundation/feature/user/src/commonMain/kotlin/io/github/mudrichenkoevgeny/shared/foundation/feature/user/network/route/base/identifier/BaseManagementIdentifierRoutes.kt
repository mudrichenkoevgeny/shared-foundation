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
 * [UserApiPaths.USER_ID] and [UserApiPaths.USER_IDENTIFIER_ID]. Response shape is always [UserIdentifierPayload]; wire
 * field [CommonApiFields.IS_SENSITIVE_VALUES_MASKED] reflects masking for the caller ([IdentifierPermissionCode]).
 */
object BaseManagementIdentifierRoutes {
    /**
     * Path prefix for identifier list under management.
     */
    const val BASE_MANAGEMENT_IDENTIFIERS_LIST_ROUTE =
        "${BaseManagementUserRoutes.BASE_MANAGEMENT_USERS_ROUTE}/identifiers"

    /**
     * Paginated identifier list for a user (same path for all callers); masking is expressed in each item.
     */
    const val GET_IDENTIFIERS = BASE_MANAGEMENT_IDENTIFIERS_LIST_ROUTE

    /**
     * Path template for identifier operations scoped to one user; path parameter [UserApiPaths.USER_ID].
     */
    const val BASE_MANAGEMENT_IDENTIFIERS_ROUTE =
        "${BaseManagementUserRoutes.BASE_MANAGEMENT_USERS_ROUTE}/{${UserApiPaths.USER_ID}}/identifiers"

    /**
     * Path template for one identifier row; path parameters [UserApiPaths.USER_ID], [UserApiPaths.USER_IDENTIFIER_ID].
     */
    const val GET_IDENTIFIER =
        "$BASE_MANAGEMENT_IDENTIFIERS_ROUTE/{${UserApiPaths.USER_IDENTIFIER_ID}}"

    /**
     * Path template for deleting one identifier record; path parameters [UserApiPaths.USER_ID],
     * [UserApiPaths.USER_IDENTIFIER_ID].
     */
    const val DELETE_IDENTIFIER =
        "$BASE_MANAGEMENT_IDENTIFIERS_ROUTE/{${UserApiPaths.USER_IDENTIFIER_ID}}"
}
