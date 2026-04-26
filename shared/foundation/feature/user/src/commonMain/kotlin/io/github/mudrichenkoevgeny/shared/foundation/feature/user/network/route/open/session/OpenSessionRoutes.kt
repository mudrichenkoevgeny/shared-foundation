package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.session

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEvent
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CommonAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientType
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.PagedResult
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.verifytotp.VerifyTotpPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.action.UserAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.metadata.UserAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.resource.UserAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSortValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.session.DeletedSessionsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.session.UserSessionPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.session.BaseOpenSessionRoutes

/**
 * Route paths for session management in the open API.
 */
object OpenSessionRoutes {
    /**
     * **HTTP method:** `POST`
     *
     * Deletes the current active session.
     *
     * **Audit logging:** Persist an [AuditEvent] for every logout attempt.
     * * **Action:** [UserAuditActionType.LOGOUT].
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the current [UserId].
     * * **Resource:** [UserAuditResourceType.SESSION]. Set `resourceId` to the current session identifier.
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey])
     */
    const val LOGOUT = BaseOpenSessionRoutes.LOGOUT

    /**
     * **HTTP method:** `GET`
     *
     * Returns active sessions for the **current** authenticated user (self-service).
     *
     * **Pagination & sort** (names from [ListingParamNames]):
     * - [ListingParamNames.Pagination.PAGE_NUMBER] — one-based page index (`1` is the first page).
     * - [ListingParamNames.Pagination.PAGE_SIZE] — page size.
     * - [ListingParamNames.Sort.SORT_BY] — exactly one of
     * [UserSortValues.UserSessionSortBy.LAST_ACCESSED_AT],
     * [UserSortValues.UserSessionSortBy.LAST_REAUTHENTICATED_AT],
     * [UserSortValues.UserSessionSortBy.EXPIRES_AT],
     * [UserSortValues.UserSessionSortBy.CREATED_AT],
     * [UserSortValues.UserSessionSortBy.UPDATED_AT].
     * - [ListingParamNames.Sort.SORT_ORDER] — [CommonApiFields.SortOrder.ASC] or
     * [CommonApiFields.SortOrder.DESC].
     *
     * **Filters** ([UserFilterValues.UserSessionFilterValues]): [UserFilterValues.UserSessionFilterValues.USER_ID]
     * required; others optional. Same key repeated — **OR**; different keys — **AND**.
     *
     * - [UserFilterValues.UserSessionFilterValues.IDENTIFIER] — list of free-text identifiers; server-defined.
     * - [UserFilterValues.UserSessionFilterValues.IDENTIFIER_ID] — list of credential record IDs ([UserSessionPayload.identifierId]).
     * - [UserFilterValues.UserSessionFilterValues.USER_AUTH_PROVIDER] — list of [UserAuthProvider] serial names ([UserSessionPayload.identifierAuthProvider]).
     * - [UserFilterValues.UserSessionFilterValues.CLIENT_TYPE] — list of [ClientType] serial names.
     * - [UserFilterValues.UserSessionFilterValues.USER_AGENT] — list of free-text agents; server-defined.
     * - [UserFilterValues.UserSessionFilterValues.IP_ADDRESS] — list of free-text IP addresses; server-defined.
     * - [UserFilterValues.UserSessionFilterValues.LANGUAGE] — list of free-text languages; server-defined.
     * - [UserFilterValues.UserSessionFilterValues.DEVICE_ID] — list of opaque device IDs.
     * - [UserFilterValues.UserSessionFilterValues.DEVICE_NAME] — list of free-text device names; server-defined.
     * - [UserFilterValues.UserSessionFilterValues.APP_VERSION] — list of application versions.
     * - [UserFilterValues.UserSessionFilterValues.OPERATION_SYSTEM_VERSION] — list of free-text values; server-defined.
     *
     * Response body: [PagedResult] of [UserSessionPayload] .
     */
    const val GET_SESSIONS = BaseOpenSessionRoutes.GET_SESSIONS

    /**
     * **HTTP method:** `GET`
     *
     * Path parameter: [UserApiPaths.SESSION_ID].
     *
     * Response body: [UserSessionPayload].
     */
    const val GET_SESSION = BaseOpenSessionRoutes.GET_SESSION

    /**
     * **HTTP method:** `DELETE`
     *
     * Deletes the session for the given identifier.
     *
     * Path parameter: [UserApiPaths.SESSION_ID].
     *
     * **Audit logging:** Persist an [AuditEvent] for successful revocation and security-relevant denials.
     * * **Action:** [UserAuditActionType.SELF_DELETE_SESSION].
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the current [UserId].
     * * **Resource:** [UserAuditResourceType.SESSION]. Set `resourceId` to the [UserSessionId] from [UserApiPaths.SESSION_ID].
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey])
     * 2. [UserAuditMetadataKey.USER_ID] — the owner of the session.
     */
    const val DELETE_SESSION = BaseOpenSessionRoutes.DELETE_SESSION

    /**
     * **HTTP method:** `DELETE`
     *
     * Deletes all sessions for the current user except the one used for this request.
     *
     * Response body: [DeletedSessionsPayload].
     *
     * **Audit logging:** Persist an [AuditEvent] for successful bulk revocation and security-relevant denials.
     * * **Action:** [UserAuditActionType.SELF_DELETE_OTHER_SESSIONS].
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the current [UserId].
     * * **Resource:** [UserAuditResourceType.SESSION]. Leave `resourceId` unset (bulk operation).
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey])
     * 2. [UserAuditMetadataKey.USER_ID] — the account whose sessions are being deleted.
     */
    const val DELETE_ALL_OTHER_SESSIONS = BaseOpenSessionRoutes.DELETE_ALL_OTHER_SESSIONS

    /**
     * **HTTP method:** `POST`
     *
     * Performs re-authentication via TOTP to update the session's trust level.
     *
     * Request body: [VerifyTotpPayload].
     *
     * **Audit logging:** Persist an [AuditEvent] for every re-authentication attempt.
     * * **Action:** [UserAuditActionType.REAUTHENTICATE_SESSION].
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the current [UserId].
     * * **Resource:** [UserAuditResourceType.SESSION]. Set `resourceId` to the current [UserSessionId].
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey])
     * 2. [UserAuditMetadataKey.MFA_TOKEN] — the challenge token.
     */
    const val REAUTHENTICATE_SESSION = BaseOpenSessionRoutes.REAUTHENTICATE_SESSION
}