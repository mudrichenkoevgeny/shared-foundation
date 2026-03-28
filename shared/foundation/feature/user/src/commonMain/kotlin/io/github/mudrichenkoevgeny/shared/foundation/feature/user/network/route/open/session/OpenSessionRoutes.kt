package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.session

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiQueryParamValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.listing.PagedResponse
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiQueryParamValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.response.session.UserSessionUnmaskedResponse
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.session.BaseSessionRoutes

/**
 * Route paths for session management in the open API.
 */
object OpenSessionRoutes {
    /**
     * **HTTP method:** `POST`
     *
     * Terminates the current active session.
     */
    const val LOGOUT = BaseSessionRoutes.LOGOUT

    /**
     * **HTTP method:** `GET`
     *
     * Returns active sessions for the **current** authenticated user (self-service).
     *
     * **Pagination & sort** (names from [CommonApiQueryParams]):
     * - [CommonApiQueryParams.PaginationQueryParams.PAGE] — zero-based page index.
     * - [CommonApiQueryParams.PaginationQueryParams.PAGE_SIZE] — page size.
     * - [CommonApiQueryParams.SortQueryParams.SORT_BY] — exactly one of
     *   [UserApiQueryParamValues.UserSessionSortBy.LAST_ACCESSED_AT],
     *   [UserApiQueryParamValues.UserSessionSortBy.LAST_REAUTHENTICATED_AT],
     *   [UserApiQueryParamValues.UserSessionSortBy.EXPIRES_AT],
     *   [UserApiQueryParamValues.UserSessionSortBy.CREATED_AT],
     *   [UserApiQueryParamValues.UserSessionSortBy.UPDATED_AT].
     * - [CommonApiQueryParams.SortQueryParams.SORT_ORDER] — [CommonApiQueryParamValues.SortOrder.ASC] or
     *   [CommonApiQueryParamValues.SortOrder.DESC].
     *
     * **Filters** ([UserApiQueryParams.UserSessionQueryParams], optional). If omitted, no filtering (all sessions for
     * the current user, then pagination/sort). Same key repeated means **OR**; different keys combine as **AND**.
     *
     * - [UserApiQueryParams.UserSessionQueryParams.USER_AUTH_PROVIDER] — [UserAuthProvider] serial name ([UserSessionUnmaskedResponse.identifierAuthProvider]).
     * - [UserApiQueryParams.UserSessionQueryParams.REVOKED] — `true` or `false`.
     * - [UserApiQueryParams.UserSessionQueryParams.DEVICE_ID] — opaque device id; free-text / repeat for OR if supported.
     *
     * Response body: [PagedResponse] of [UserSessionUnmaskedResponse].
     */
    const val GET_SESSIONS = BaseSessionRoutes.GET_SESSIONS

    /**
     * **HTTP method:** `GET`
     *
     * Path parameter: [UserApiPaths.SESSION_ID].
     *
     * Response body: single JSON object [UserSessionUnmaskedResponse].
     */
    const val GET_SESSION_BY_ID = BaseSessionRoutes.GET_SESSION_BY_ID

    /**
     * **HTTP method:** `DELETE`
     *
     * Terminates the session for the given identifier.
     *
     * Path parameter: [UserApiPaths.SESSION_ID].
     */
    const val DELETE_SESSION = BaseSessionRoutes.DELETE_SESSION

    /**
     * **HTTP method:** `DELETE`
     *
     * Terminates all sessions for the current user except the one used for this request.
     */
    const val DELETE_ALL_OTHER_SESSIONS = BaseSessionRoutes.DELETE_ALL_OTHER_SESSIONS
}
