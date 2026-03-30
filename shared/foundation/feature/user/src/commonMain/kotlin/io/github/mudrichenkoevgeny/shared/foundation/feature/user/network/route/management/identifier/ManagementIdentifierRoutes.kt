package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.identifier

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.PagedResult
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission.IdentifierPermissionCodes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSortValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.identifier.UserIdentifierMaskedPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.identifier.UserIdentifierUnmaskedPayload
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
     * **Pagination & sort** (names from [ListingParamNames]):
     * - [ListingParamNames.Pagination.PAGE_NUMBER] — one-based page index (`1` is the first page).
     * - [ListingParamNames.Pagination.PAGE_SIZE] — page size.
     * - [ListingParamNames.Sort.SORT_BY] — exactly one of
     *   [UserSortValues.UserIdentifierSortBy.CREATED_AT],
     *   [UserSortValues.UserIdentifierSortBy.UPDATED_AT].
     * - [ListingParamNames.Sort.SORT_ORDER] — [CommonApiFields.SortOrder.ASC] or
     *   [CommonApiFields.SortOrder.DESC].
     *
     * **Filters** ([UserFilterValues.UserIdentifierFilterValues]): [UserFilterValues.UserIdentifierFilterValues.USER_ID]
     * required. Same key repeated means **OR**; different keys combine as **AND**.
     *
     * - [UserFilterValues.UserIdentifierFilterValues.USER_ID]
     *
     * Response body: [PagedResult] of [UserIdentifierMaskedPayload].
     */
    const val GET_IDENTIFIERS_MASKED = BaseManagementIdentifierRoutes.GET_IDENTIFIERS_MASKED

    /**
     * **HTTP method:** `GET`
     *
     * **Pagination & sort** (names from [ListingParamNames]):
     * - [ListingParamNames.Pagination.PAGE_NUMBER] — one-based page index (`1` is the first page).
     * - [ListingParamNames.Pagination.PAGE_SIZE] — page size.
     * - [ListingParamNames.Sort.SORT_BY] — exactly one of
     *   [UserSortValues.UserIdentifierSortBy.CREATED_AT],
     *   [UserSortValues.UserIdentifierSortBy.UPDATED_AT].
     * - [ListingParamNames.Sort.SORT_ORDER] — [CommonApiFields.SortOrder.ASC] or
     *   [CommonApiFields.SortOrder.DESC].
     *
     * **Filters** ([UserFilterValues.UserIdentifierFilterValues]): [UserFilterValues.UserIdentifierFilterValues.USER_ID]
     * required; other parameters optional. Same key repeated means **OR**; different keys combine as **AND**.
     *
     * - [UserFilterValues.UserIdentifierFilterValues.USER_ID]
     * - [UserFilterValues.UserIdentifierFilterValues.USER_AUTH_PROVIDER] — [UserAuthProvider] serial name ([UserIdentifierUnmaskedPayload.userAuthProvider]).
     * - [UserFilterValues.UserIdentifierFilterValues.IDENTIFIER] — free-text; server-defined matching; repeat key for multiple values if supported.
     *
     * Response body: [PagedResult] of [UserIdentifierUnmaskedPayload].
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
