package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.session

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.clienttype.ClientType
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.PagedResult
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSortValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.session.UserSessionMaskedPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.session.UserSessionUnmaskedPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.session.BaseManagementSessionRoutes

/**
 * Route paths for user sessions in the management API (lists, single-session reads, and administrative revocation).
 */
object ManagementSessionRoutes {
    /**
     * **HTTP method:** `GET`
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
     * **Filters** ([UserFilterValues.UserSessionFilterValues]): [UserFilterValues.UserSessionFilterValues.USER_ID] required;
     * others optional. Same key repeated — **OR**; different keys — **AND**.
     *
     * - [UserFilterValues.UserSessionFilterValues.USER_ID]
     * - [UserFilterValues.UserSessionFilterValues.IDENTIFIER_ID] — credential record id ([UserSessionMaskedPayload.identifierId]).
     * - [UserFilterValues.UserSessionFilterValues.USER_AUTH_PROVIDER] — [UserAuthProvider] serial name ([UserSessionMaskedPayload.identifierAuthProvider]).
     * - [UserFilterValues.UserSessionFilterValues.REVOKED] — `true` or `false`.
     * - [UserFilterValues.UserSessionFilterValues.CLIENT_TYPE] — [ClientType] serial name.
     * - [UserFilterValues.UserSessionFilterValues.USER_AGENT] — free-text; server-defined matching; repeat key for multiple values if supported.
     * - [UserFilterValues.UserSessionFilterValues.LANGUAGE] — free-text; server-defined matching; repeat key for multiple values if supported.
     * - [UserFilterValues.UserSessionFilterValues.DEVICE_ID] — opaque device id; free-text / repeat for OR if supported.
     * - [UserFilterValues.UserSessionFilterValues.DEVICE_NAME],
     *   [UserFilterValues.UserSessionFilterValues.APP_VERSION],
     *   [UserFilterValues.UserSessionFilterValues.OPERATION_SYSTEM_VERSION] — free-text; server-defined matching; repeat key for multiple values if supported.
     *
     * Response body: [PagedResult] of [UserSessionMaskedPayload].
     */
    const val GET_USER_SESSIONS_MASKED = BaseManagementSessionRoutes.GET_USER_SESSIONS_MASKED

    /**
     * **HTTP method:** `GET`
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
     * **Filters** ([UserFilterValues.UserSessionFilterValues]): [UserFilterValues.UserSessionFilterValues.USER_ID] required;
     * others optional. Same key repeated — **OR**; different keys — **AND**.
     *
     * - [UserFilterValues.UserSessionFilterValues.USER_ID]
     * - [UserFilterValues.UserSessionFilterValues.IDENTIFIER] — free-text; server-defined matching; repeat key for multiple values if supported.
     * - [UserFilterValues.UserSessionFilterValues.IDENTIFIER_ID] — credential record id ([UserSessionUnmaskedPayload.identifierId]).
     * - [UserFilterValues.UserSessionFilterValues.USER_AUTH_PROVIDER] — [UserAuthProvider] serial name ([UserSessionUnmaskedPayload.identifierAuthProvider]).
     * - [UserFilterValues.UserSessionFilterValues.REVOKED] — `true` or `false`.
     * - [UserFilterValues.UserSessionFilterValues.CLIENT_TYPE] — [ClientType] serial name.
     * - [UserFilterValues.UserSessionFilterValues.USER_AGENT] — free-text; server-defined matching; repeat key for multiple values if supported.
     * - [UserFilterValues.UserSessionFilterValues.IP_ADDRESS] — free-text; server-defined matching; repeat key for multiple values if supported.
     * - [UserFilterValues.UserSessionFilterValues.LANGUAGE] — free-text; server-defined matching; repeat key for multiple values if supported.
     * - [UserFilterValues.UserSessionFilterValues.DEVICE_ID] — opaque device id; free-text / repeat for OR if supported.
     * - [UserFilterValues.UserSessionFilterValues.DEVICE_NAME],
     *   [UserFilterValues.UserSessionFilterValues.APP_VERSION],
     *   [UserFilterValues.UserSessionFilterValues.OPERATION_SYSTEM_VERSION] — free-text; server-defined matching; repeat key for multiple values if supported.
     *
     * Response body: [PagedResult] of [UserSessionUnmaskedPayload].
     */
    const val GET_USER_SESSIONS_UNMASKED = BaseManagementSessionRoutes.GET_USER_SESSIONS_UNMASKED

    /**
     * **HTTP method:** `GET`
     *
     * Path parameters: [UserApiPaths.USER_ID], [UserApiPaths.SESSION_ID].
     *
     * Response body: single JSON object [UserSessionMaskedPayload].
     */
    const val GET_USER_SESSION_MASKED_BY_ID = BaseManagementSessionRoutes.GET_USER_SESSION_MASKED_BY_ID

    /**
     * **HTTP method:** `GET`
     *
     * Path parameters: [UserApiPaths.USER_ID], [UserApiPaths.SESSION_ID].
     *
     * Response body: single JSON object [UserSessionUnmaskedPayload].
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
