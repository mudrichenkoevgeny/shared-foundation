package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.identifier

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission.IdentifierPermissionCodes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.user.BaseManagementUserRoutes

/**
 * Shared base path segments for user identifiers in the management API.
 *
 * Lists use query [UserApiQueryParams.UserIdentifierQueryParams.USER_ID]. Delete-by-id uses [UserApiPaths.USER_ID] in the
 * path. Masked vs unmasked lists use different DTOs; authorization aligns with [IdentifierPermissionCodes].
 */
object BaseManagementIdentifierRoutes {
    /**
     * Path prefix for identifier list routes under management.
     */
    const val BASE_MANAGEMENT_IDENTIFIERS_LIST_ROUTE =
        "${BaseManagementUserRoutes.BASE_MANAGEMENT_USERS_ROUTE}/identifiers"

    /**
     * Path for the masked identifier list.
     */
    const val GET_IDENTIFIERS_MASKED = "$BASE_MANAGEMENT_IDENTIFIERS_LIST_ROUTE/masked"

    /**
     * Path for the unmasked identifier list.
     */
    const val GET_IDENTIFIERS_UNMASKED = "$BASE_MANAGEMENT_IDENTIFIERS_LIST_ROUTE/unmasked"

    /**
     * Path template for identifier operations scoped to one user; path parameter [UserApiPaths.USER_ID].
     */
    const val BASE_MANAGEMENT_IDENTIFIERS_ROUTE =
        "${BaseManagementUserRoutes.BASE_MANAGEMENT_USERS_ROUTE}/{${UserApiPaths.USER_ID}}/identifiers"

    /**
     * Path template for deleting one identifier record for a user under management; path parameters [UserApiPaths.USER_ID],
     * [UserApiPaths.USER_IDENTIFIER_ID].
     */
    const val DELETE_IDENTIFIER =
        "$BASE_MANAGEMENT_IDENTIFIERS_ROUTE/{${UserApiPaths.USER_IDENTIFIER_ID}}"
}
