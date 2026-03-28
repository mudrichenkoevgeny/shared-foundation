package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.identifier

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiQueryParamValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.listing.PagedResponse
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission.IdentifierPermissionCodes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiQueryParamValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.response.identifier.UserIdentifierMaskedResponse
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.response.identifier.UserIdentifierUnmaskedResponse
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.identifier.BaseManagementIdentifierRoutes

/**
 * Route paths for user identifiers in the management API (lists and deletion).
 *
 * Read: [IdentifierPermissionCodes] for masked vs unmasked lists.
 * Delete: [IdentifierPermissionCodes.IDENTIFIER_DELETE_FOR_USER] or
 * [IdentifierPermissionCodes.IDENTIFIER_DELETE_FOR_STAFF] by target account role.
 */
object ManagementIdentifierRoutes {
    /**
     * **HTTP method:** `GET`
     *
     * **Pagination & sort** (names from [CommonApiQueryParams]):
     * - [CommonApiQueryParams.PaginationQueryParams.PAGE] — zero-based page index.
     * - [CommonApiQueryParams.PaginationQueryParams.PAGE_SIZE] — page size.
     * - [CommonApiQueryParams.SortQueryParams.SORT_BY] — exactly one of
     *   [UserApiQueryParamValues.UserIdentifierSortBy.CREATED_AT],
     *   [UserApiQueryParamValues.UserIdentifierSortBy.UPDATED_AT].
     * - [CommonApiQueryParams.SortQueryParams.SORT_ORDER] — [CommonApiQueryParamValues.SortOrder.ASC] or
     *   [CommonApiQueryParamValues.SortOrder.DESC].
     *
     * **Filters** ([UserApiQueryParams.UserIdentifierQueryParams]): [UserApiQueryParams.UserIdentifierQueryParams.USER_ID]
     * required. Same key repeated means **OR**; different keys combine as **AND**.
     *
     * - [UserApiQueryParams.UserIdentifierQueryParams.USER_ID]
     *
     * Response body: [PagedResponse] of [UserIdentifierMaskedResponse].
     */
    const val GET_IDENTIFIERS_MASKED = BaseManagementIdentifierRoutes.GET_IDENTIFIERS_MASKED

    /**
     * **HTTP method:** `GET`
     *
     * **Pagination & sort** (names from [CommonApiQueryParams]):
     * - [CommonApiQueryParams.PaginationQueryParams.PAGE] — zero-based page index.
     * - [CommonApiQueryParams.PaginationQueryParams.PAGE_SIZE] — page size.
     * - [CommonApiQueryParams.SortQueryParams.SORT_BY] — exactly one of
     *   [UserApiQueryParamValues.UserIdentifierSortBy.CREATED_AT],
     *   [UserApiQueryParamValues.UserIdentifierSortBy.UPDATED_AT].
     * - [CommonApiQueryParams.SortQueryParams.SORT_ORDER] — [CommonApiQueryParamValues.SortOrder.ASC] or
     *   [CommonApiQueryParamValues.SortOrder.DESC].
     *
     * **Filters** ([UserApiQueryParams.UserIdentifierQueryParams]): [UserApiQueryParams.UserIdentifierQueryParams.USER_ID]
     * required; other parameters optional. Same key repeated means **OR**; different keys combine as **AND**.
     *
     * - [UserApiQueryParams.UserIdentifierQueryParams.USER_ID]
     * - [UserApiQueryParams.UserIdentifierQueryParams.USER_AUTH_PROVIDER] — [UserAuthProvider] serial name ([UserIdentifierUnmaskedResponse.userAuthProvider]).
     * - [UserApiQueryParams.UserIdentifierQueryParams.IDENTIFIER] — free-text; server-defined matching; repeat key for multiple values if supported.
     *
     * Response body: [PagedResponse] of [UserIdentifierUnmaskedResponse].
     */
    const val GET_IDENTIFIERS_UNMASKED = BaseManagementIdentifierRoutes.GET_IDENTIFIERS_UNMASKED

    /**
     * **HTTP method:** `DELETE`
     *
     * Removes the identifier record [UserApiPaths.USER_IDENTIFIER_ID] for the user [UserApiPaths.USER_ID].
     *
     * Path parameters: [UserApiPaths.USER_ID], [UserApiPaths.USER_IDENTIFIER_ID].
     */
    const val DELETE_IDENTIFIER = BaseManagementIdentifierRoutes.DELETE_IDENTIFIER
}
