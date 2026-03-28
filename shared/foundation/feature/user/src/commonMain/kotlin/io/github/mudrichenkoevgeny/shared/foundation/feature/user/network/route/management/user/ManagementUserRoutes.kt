package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.user

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiQueryParamValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiQueryParamValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.create.CreateByEmailRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.response.user.UserDetailsResponse
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.user.BaseManagementUserRoutes

/**
 * Route paths for user management in the management API.
 */
object ManagementUserRoutes {
    /**
     * **HTTP method:** `GET`
     *
     * **Pagination & sort** (names from [CommonApiQueryParams]):
     * - [CommonApiQueryParams.PaginationQueryParams.PAGE] — zero-based page index.
     * - [CommonApiQueryParams.PaginationQueryParams.PAGE_SIZE] — page size.
     * - [CommonApiQueryParams.SortQueryParams.SORT_BY] — exactly one of
     *   [UserApiQueryParamValues.UserSortBy.LAST_LOGIN_AT],
     *   [UserApiQueryParamValues.UserSortBy.LAST_ACTIVE_AT],
     *   [UserApiQueryParamValues.UserSortBy.CREATED_AT],
     *   [UserApiQueryParamValues.UserSortBy.UPDATED_AT],
     *   [UserApiQueryParamValues.UserSortBy.SCHEDULED_PERMANENT_DELETION_AT].
     * - [CommonApiQueryParams.SortQueryParams.SORT_ORDER] — [CommonApiQueryParamValues.SortOrder.ASC] or
     *   [CommonApiQueryParamValues.SortOrder.DESC].
     *
     * **Filters** ([UserApiQueryParams.UserQueryParams], optional). If omitted, no filtering (all users matching caller
     * access, then pagination/sort). Same key repeated means **OR**; different keys combine as **AND**.
     *
     * - [UserApiQueryParams.UserQueryParams.ROLE] — [UserRole] serial name ([UserDetailsResponse.role]).
     * - [UserApiQueryParams.UserQueryParams.ACCOUNT_STATUS] — [UserAccountStatus] serial name ([UserDetailsResponse.accountStatus]).
     * - [UserApiQueryParams.UserQueryParams.ACCOUNT_STATUS_BEFORE_DELETION] — [UserAccountStatus] serial name ([UserDetailsResponse.accountStatusBeforeDeletion]).
     *
     * Response body: JSON array of [UserDetailsResponse].
     */
    const val GET_USERS = BaseManagementUserRoutes.GET_USERS

    /**
     * **HTTP method:** `GET`
     *
     * Returns the user for the given identifier.
     *
     * Path parameter: [UserApiPaths.USER_ID].
     *
     * Response body: [UserDetailsResponse].
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
     * **HTTP method:** `POST`
     *
     * Request body: [CreateByEmailRequest].
     *
     * Response body: [UserDetailsResponse].
     */
    const val CREATE_USER = BaseManagementUserRoutes.CREATE_USER
}
