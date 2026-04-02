package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.user

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.PagedResult
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSortValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.create.CreateByEmailRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.user.UserDetailsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.user.BaseManagementUserRoutes

/**
 * Route paths for user management in the management API.
 */
object ManagementUserRoutes {
    /**
     * **HTTP method:** `GET`
     *
     * **Pagination & sort** (names from [ListingParamNames]):
     * - [ListingParamNames.Pagination.PAGE_NUMBER] — one-based page index (`1` is the first page).
     * - [ListingParamNames.Pagination.PAGE_SIZE] — page size.
     * - [ListingParamNames.Sort.SORT_BY] — exactly one of
     *   [UserSortValues.UserSortBy.LAST_LOGIN_AT],
     *   [UserSortValues.UserSortBy.LAST_ACTIVE_AT],
     *   [UserSortValues.UserSortBy.CREATED_AT],
     *   [UserSortValues.UserSortBy.UPDATED_AT],
     *   [UserSortValues.UserSortBy.SCHEDULED_PERMANENT_DELETION_AT].
     * - [ListingParamNames.Sort.SORT_ORDER] — [CommonApiFields.SortOrder.ASC] or
     *   [CommonApiFields.SortOrder.DESC].
     *
     * **Filters** ([UserFilterValues.UserFilterValues], optional). If omitted, no filtering (all users matching caller
     * access, then pagination/sort). Same key repeated means **OR**; different keys combine as **AND**.
     *
     * - [UserFilterValues.UserFilterValues.ROLE] — [UserRole] serial name ([UserDetailsPayload.role]).
     * - [UserFilterValues.UserFilterValues.ACCOUNT_STATUS] — [UserAccountStatus] serial name ([UserDetailsPayload.accountStatus]).
     * - [UserFilterValues.UserFilterValues.ACCOUNT_STATUS_BEFORE_DELETION] — [UserAccountStatus] serial name ([UserDetailsPayload.accountStatusBeforeDeletion]).
     *
     * Response body: [PagedResult] of [UserDetailsPayload].
     */
    const val GET_USERS = BaseManagementUserRoutes.GET_USERS

    /**
     * **HTTP method:** `GET`
     *
     * Returns the user for the given identifier.
     *
     * Path parameter: [UserApiPaths.USER_ID].
     *
     * Response body: [UserDetailsPayload].
     */
    const val GET_USER = BaseManagementUserRoutes.GET_USER

    /**
     * **HTTP method:** `PATCH`
     *
     * Updates the account status for the given user.
     *
     * Path parameter: [UserApiPaths.USER_ID].
     */
    const val UPDATE_USER_ACCOUNT_STATUS = BaseManagementUserRoutes.UPDATE_USER_ACCOUNT_STATUS

    /**
     * **HTTP method:** `DELETE`
     *
     * Deletes the user account for the given [UserApiPaths.USER_ID].
     *
     * Path parameter: [UserApiPaths.USER_ID].
     */
    const val DELETE_USER = BaseManagementUserRoutes.DELETE_USER

    /**
     * **HTTP method:** `POST`
     *
     * Request body: [CreateByEmailRequest].
     *
     * Response body: [UserDetailsPayload].
     */
    const val CREATE_USER = BaseManagementUserRoutes.CREATE_USER
}
