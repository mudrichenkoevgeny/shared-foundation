package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.identifier

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEvent
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.PagedResult
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.action.UserAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.metadata.UserAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.resource.UserAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSortValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.confirmation.SendConfirmationToEmailRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.confirmation.SendConfirmationToPhoneRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.security.password.PasswordChangeRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.security.useridentifiers.AddUserIdentifierEmailRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.security.useridentifiers.AddUserIdentifierExternalAuthProviderRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.security.useridentifiers.AddUserIdentifierPhoneRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.confirmation.SendConfirmationPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.identifier.UserIdentifierPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.identifier.BaseIdentifiersRoutes

/**
 * Route paths for managing user authentication identifiers in the open API.
 */
object OpenIdentifierRoutes {
    /**
     * **HTTP method:** `GET`
     *
     * Returns identifiers linked to the **current** authenticated account (self-service).
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
     * **Filters** ([UserFilterValues.UserIdentifierFilterValues], optional). If omitted, no filtering (all identifiers
     * for the current user, then pagination/sort). Same key repeated means **OR**; different keys combine as **AND**.
     *
     * - [UserFilterValues.UserIdentifierFilterValues.USER_AUTH_PROVIDER] — [UserAuthProvider] serial name
     *   ([UserIdentifierPayload.userAuthProvider]).
     *
     * Response body: [PagedResult] of [UserIdentifierPayload] with [UserIdentifierPayload.isSensitiveValuesMasked]
     * `false` (self-service raw values).
     */
    const val GET_IDENTIFIERS = BaseIdentifiersRoutes.GET_IDENTIFIERS

    /**
     * **HTTP method:** `DELETE`
     *
     * Removes the identifier for the given record id.
     *
     * Path parameter: [UserApiPaths.USER_IDENTIFIER_ID].
     *
     * **Audit logging:** Persist an [AuditEvent] for successful removal and for security-relevant denials. Use action
     * [UserAuditActionType.SELF_DELETE_IDENTIFIER] and resource [UserAuditResourceType.USER_IDENTIFIER]. Set `resourceId`
     * to the path [UserApiPaths.USER_IDENTIFIER_ID]. Add metadata [UserAuditMetadataKey.USER_ID] when the subject account
     * must be explicit.
     */
    const val DELETE_IDENTIFIER = BaseIdentifiersRoutes.DELETE_IDENTIFIER

    /**
     * **HTTP method:** `POST`
     *
     * Links an email identifier to the current account after verification.
     *
     * Request body: [AddUserIdentifierEmailRequest].
     *
     * **Audit logging:** Persist an [AuditEvent] for successful linking and for security-relevant denials. Use action
     * [UserAuditActionType.ADD_IDENTIFIER_EMAIL] and resource [UserAuditResourceType.USER_IDENTIFIER]. After success, set
     * `resourceId` to the new identifier record id when the API returns it.
     */
    const val ADD_IDENTIFIER_EMAIL = BaseIdentifiersRoutes.ADD_IDENTIFIER_EMAIL

    /**
     * **HTTP method:** `POST`
     *
     * Links a phone identifier to the current account after verification.
     *
     * Request body: [AddUserIdentifierPhoneRequest].
     *
     * **Audit logging:** Persist an [AuditEvent] for successful linking and for security-relevant denials. Use action
     * [UserAuditActionType.ADD_IDENTIFIER_PHONE] and resource [UserAuditResourceType.USER_IDENTIFIER]. After success, set
     * `resourceId` to the new identifier record id when the API returns it.
     */
    const val ADD_IDENTIFIER_PHONE = BaseIdentifiersRoutes.ADD_IDENTIFIER_PHONE

    /**
     * **HTTP method:** `POST`
     *
     * Links an external authentication provider to the current account.
     *
     * Request body: [AddUserIdentifierExternalAuthProviderRequest].
     *
     * **Audit logging:** Persist an [AuditEvent] for successful linking and for security-relevant denials. Use action
     * [UserAuditActionType.ADD_IDENTIFIER_EXTERNAL_AUTH_PROVIDER] and resource [UserAuditResourceType.USER_IDENTIFIER].
     * After success, set `resourceId` to the new identifier record id when the API returns it.
     */
    const val ADD_IDENTIFIER_EXTERNAL_AUTH_PROVIDER = BaseIdentifiersRoutes.ADD_IDENTIFIER_EXTERNAL_AUTH_PROVIDER

    /**
     * **HTTP method:** `POST`
     *
     * Requests a confirmation code to add an email identifier.
     *
     * Request body: [SendConfirmationToEmailRequest].
     *
     * Response body: [SendConfirmationPayload].
     */
    const val SEND_ADD_EMAIL_IDENTIFIER_CONFIRMATION = BaseIdentifiersRoutes.SEND_ADD_EMAIL_IDENTIFIER_CONFIRMATION

    /**
     * **HTTP method:** `POST`
     *
     * Requests a confirmation code to add a phone identifier.
     *
     * Request body: [SendConfirmationToPhoneRequest].
     *
     * Response body: [SendConfirmationPayload].
     */
    const val SEND_ADD_PHONE_IDENTIFIER_CONFIRMATION = BaseIdentifiersRoutes.SEND_ADD_PHONE_IDENTIFIER_CONFIRMATION

    /**
     * **HTTP method:** `POST`
     *
     * Updates the account password using current credentials.
     *
     * Request body: [PasswordChangeRequest].
     *
     * **Audit logging:** Persist an [AuditEvent] for successful password changes and for security-relevant denials. Use
     * action [UserAuditActionType.CHANGE_PASSWORD] and resource [UserAuditResourceType.USER_IDENTIFIER]. Do not log
     * passwords or secrets in metadata.
     */
    const val IDENTIFIER_EMAIL_CHANGE_PASSWORD = BaseIdentifiersRoutes.IDENTIFIER_EMAIL_CHANGE_PASSWORD
}