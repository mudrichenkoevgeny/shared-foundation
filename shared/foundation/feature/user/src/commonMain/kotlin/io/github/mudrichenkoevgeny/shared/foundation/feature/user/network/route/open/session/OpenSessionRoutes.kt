package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.session

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.PagedResult
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSortValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.session.UserSessionUnmaskedPayload
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
     * **Pagination & sort** (names from [ListingParamNames]):
     * - [ListingParamNames.Pagination.PAGE_NUMBER] — one-based page index (`1` is the first page).
     * - [ListingParamNames.Pagination.PAGE_SIZE] — page size.
     * - [ListingParamNames.Sort.SORT_BY] — exactly one of
     *   [UserSortValues.UserSessionSortBy.LAST_ACCESSED_AT],
     *   [UserSortValues.UserSessionSortBy.LAST_REAUTHENTICATED_AT],
     *   [UserSortValues.UserSessionSortBy.EXPIRES_AT],
     *   [UserSortValues.UserSessionSortBy.CREATED_AT],
     *   [UserSortValues.UserSessionSortBy.UPDATED_AT].
     * - [ListingParamNames.Sort.SORT_ORDER] — [CommonApiFields.SortOrder.ASC] or
     *   [CommonApiFields.SortOrder.DESC].
     *
     * **Filters** ([UserFilterValues.UserSessionFilterValues], optional). If omitted, no filtering (all sessions for
     * the current user, then pagination/sort). Same key repeated means **OR**; different keys combine as **AND**.
     *
     * - [UserFilterValues.UserSessionFilterValues.USER_AUTH_PROVIDER] — [UserAuthProvider] serial name ([UserSessionUnmaskedPayload.identifierAuthProvider]).
     * - [UserFilterValues.UserSessionFilterValues.REVOKED] — `true` or `false`.
     * - [UserFilterValues.UserSessionFilterValues.DEVICE_ID] — opaque device id; free-text / repeat for OR if supported.
     *
     * Response body: [PagedResult] of [UserSessionUnmaskedPayload].
     */
    const val GET_SESSIONS = BaseSessionRoutes.GET_SESSIONS

    /**
     * **HTTP method:** `GET`
     *
     * Path parameter: [UserApiPaths.SESSION_ID].
     *
     * Response body: single JSON object [UserSessionUnmaskedPayload].
     */
    const val GET_SESSION = BaseSessionRoutes.GET_SESSION

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
