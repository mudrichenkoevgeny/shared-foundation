package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.session

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.UserClientType
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiQueryParamValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.listing.PagedResponse
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiQueryParamValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.response.session.UserSessionMaskedResponse
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.response.session.UserSessionUnmaskedResponse
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.session.BaseManagementSessionRoutes

/**
 * Route paths for user sessions in the management API (lists, single-session reads, and administrative revocation).
 */
object ManagementSessionRoutes {
    /**
     * **HTTP method:** `GET`
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
     * **Filters** ([UserApiQueryParams.UserSessionQueryParams]): [UserApiQueryParams.UserSessionQueryParams.USER_ID] required;
     * others optional. Same key repeated — **OR**; different keys — **AND**.
     *
     * - [UserApiQueryParams.UserSessionQueryParams.USER_ID]
     * - [UserApiQueryParams.UserSessionQueryParams.IDENTIFIER_ID] — credential record id ([UserSessionMaskedResponse.identifierId]).
     * - [UserApiQueryParams.UserSessionQueryParams.USER_AUTH_PROVIDER] — [UserAuthProvider] serial name ([UserSessionMaskedResponse.identifierAuthProvider]).
     * - [UserApiQueryParams.UserSessionQueryParams.REVOKED] — `true` or `false`.
     * - [UserApiQueryParams.UserSessionQueryParams.CLIENT_TYPE] — [UserClientType] serial name.
     * - [UserApiQueryParams.UserSessionQueryParams.USER_AGENT] — free-text; server-defined matching; repeat key for multiple values if supported.
     * - [UserApiQueryParams.UserSessionQueryParams.LANGUAGE] — free-text; server-defined matching; repeat key for multiple values if supported.
     * - [UserApiQueryParams.UserSessionQueryParams.DEVICE_ID] — opaque device id; free-text / repeat for OR if supported.
     * - [UserApiQueryParams.UserSessionQueryParams.DEVICE_NAME],
     *   [UserApiQueryParams.UserSessionQueryParams.APP_VERSION],
     *   [UserApiQueryParams.UserSessionQueryParams.OPERATION_SYSTEM_VERSION] — free-text; server-defined matching; repeat key for multiple values if supported.
     *
     * Response body: [PagedResponse] of [UserSessionMaskedResponse].
     */
    const val GET_USER_SESSIONS_MASKED = BaseManagementSessionRoutes.GET_USER_SESSIONS_MASKED

    /**
     * **HTTP method:** `GET`
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
     * **Filters** ([UserApiQueryParams.UserSessionQueryParams]): [UserApiQueryParams.UserSessionQueryParams.USER_ID] required;
     * others optional. Same key repeated — **OR**; different keys — **AND**.
     *
     * - [UserApiQueryParams.UserSessionQueryParams.USER_ID]
     * - [UserApiQueryParams.UserSessionQueryParams.IDENTIFIER] — free-text; server-defined matching; repeat key for multiple values if supported.
     * - [UserApiQueryParams.UserSessionQueryParams.IDENTIFIER_ID] — credential record id ([UserSessionUnmaskedResponse.identifierId]).
     * - [UserApiQueryParams.UserSessionQueryParams.USER_AUTH_PROVIDER] — [UserAuthProvider] serial name ([UserSessionUnmaskedResponse.identifierAuthProvider]).
     * - [UserApiQueryParams.UserSessionQueryParams.REVOKED] — `true` or `false`.
     * - [UserApiQueryParams.UserSessionQueryParams.CLIENT_TYPE] — [UserClientType] serial name.
     * - [UserApiQueryParams.UserSessionQueryParams.USER_AGENT] — free-text; server-defined matching; repeat key for multiple values if supported.
     * - [UserApiQueryParams.UserSessionQueryParams.IP_ADDRESS] — free-text; server-defined matching; repeat key for multiple values if supported.
     * - [UserApiQueryParams.UserSessionQueryParams.LANGUAGE] — free-text; server-defined matching; repeat key for multiple values if supported.
     * - [UserApiQueryParams.UserSessionQueryParams.DEVICE_ID] — opaque device id; free-text / repeat for OR if supported.
     * - [UserApiQueryParams.UserSessionQueryParams.DEVICE_NAME],
     *   [UserApiQueryParams.UserSessionQueryParams.APP_VERSION],
     *   [UserApiQueryParams.UserSessionQueryParams.OPERATION_SYSTEM_VERSION] — free-text; server-defined matching; repeat key for multiple values if supported.
     *
     * Response body: [PagedResponse] of [UserSessionUnmaskedResponse].
     */
    const val GET_USER_SESSIONS_UNMASKED = BaseManagementSessionRoutes.GET_USER_SESSIONS_UNMASKED

    /**
     * **HTTP method:** `GET`
     *
     * Path parameters: [UserApiPaths.USER_ID], [UserApiPaths.SESSION_ID].
     *
     * Response body: single JSON object [UserSessionMaskedResponse].
     */
    const val GET_USER_SESSION_MASKED_BY_ID = BaseManagementSessionRoutes.GET_USER_SESSION_MASKED_BY_ID

    /**
     * **HTTP method:** `GET`
     *
     * Path parameters: [UserApiPaths.USER_ID], [UserApiPaths.SESSION_ID].
     *
     * Response body: single JSON object [UserSessionUnmaskedResponse].
     */
    const val GET_USER_SESSION_UNMASKED_BY_ID = BaseManagementSessionRoutes.GET_USER_SESSION_UNMASKED_BY_ID

    /**
     * **HTTP method:** `DELETE`
     *
     * Force-terminates the session identified by [UserApiPaths.SESSION_ID] for the user [UserApiPaths.USER_ID].
     *
     * Path parameters: [UserApiPaths.USER_ID], [UserApiPaths.SESSION_ID].
     */
    const val DELETE_USER_SESSION = BaseManagementSessionRoutes.DELETE_USER_SESSION

    /**
     * **HTTP method:** `DELETE`
     *
     * Force-terminates **all** sessions for the user [UserApiPaths.USER_ID].
     *
     * Path parameter: [UserApiPaths.USER_ID].
     */
    const val DELETE_ALL_USER_SESSIONS = BaseManagementSessionRoutes.DELETE_ALL_USER_SESSIONS
}
