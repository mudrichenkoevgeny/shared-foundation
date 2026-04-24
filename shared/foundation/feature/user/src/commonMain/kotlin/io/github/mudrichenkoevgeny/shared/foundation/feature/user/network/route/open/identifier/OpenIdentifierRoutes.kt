package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.identifier

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEvent
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CommonAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.PagedResult
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.otpconfirmation.OtpConfirmationPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.action.UserAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.metadata.UserAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.resource.UserAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSortValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.identifier.UserIdentifierPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.confirmation.SendConfirmationToEmailRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.confirmation.SendConfirmationToPhoneRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.security.password.EmailPasswordChangeRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.security.useridentifiers.AddUserIdentifierEmailRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.security.useridentifiers.AddUserIdentifierExternalAuthProviderRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.security.useridentifiers.AddUserIdentifierPhoneRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.identifier.BaseOpenIdentifiersRoutes

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
     * [UserSortValues.UserIdentifierSortBy.CREATED_AT],
     * [UserSortValues.UserIdentifierSortBy.UPDATED_AT].
     * - [ListingParamNames.Sort.SORT_ORDER] — [CommonApiFields.SortOrder.ASC] or
     * [CommonApiFields.SortOrder.DESC].
     *
     * **Filters** ([UserFilterValues.UserIdentifierFilterValues], optional).
     *
     * Response body: [PagedResult] of [UserIdentifierPayload] with [UserIdentifierPayload.isSensitiveValuesMasked] `false`.
     */
    const val GET_IDENTIFIERS = BaseOpenIdentifiersRoutes.GET_IDENTIFIERS

    /**
     * **HTTP method:** `DELETE`
     *
     * Removes the identifier for the given record id.
     *
     * Path parameter: [UserApiPaths.USER_IDENTIFIER_ID].
     *
     * **Audit logging:** Persist an [AuditEvent] for successful removal and security-relevant denials.
     * * **Action:** [UserAuditActionType.SELF_DELETE_IDENTIFIER].
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the current [UserId].
     * * **Resource:** [UserAuditResourceType.IDENTIFIER]. Set `resourceId` to the [UserApiPaths.USER_IDENTIFIER_ID].
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey])
     * 2. [UserAuditMetadataKey.USER_ID] — the account owner of the identifier.
     */
    const val DELETE_IDENTIFIER = BaseOpenIdentifiersRoutes.DELETE_IDENTIFIER

    /**
     * **HTTP method:** `POST`
     *
     * Links an email identifier to the current account after verification.
     *
     * Request body: [AddUserIdentifierEmailRequest].
     *
     * Response body: [UserIdentifierPayload].
     *
     * **Audit logging:** Persist an [AuditEvent] for successful linking and security-relevant denials.
     * * **Action:** [UserAuditActionType.ADD_IDENTIFIER_EMAIL].
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the current [UserId].
     * * **Resource:** [UserAuditResourceType.IDENTIFIER]. Set `resourceId` to the new identifier record id upon success.
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey])
     * 2. [UserAuditMetadataKey.EMAIL_ADDRESS] — the email address being linked.
     */
    const val ADD_IDENTIFIER_EMAIL = BaseOpenIdentifiersRoutes.ADD_IDENTIFIER_EMAIL

    /**
     * **HTTP method:** `POST`
     *
     * Links a phone identifier to the current account after verification.
     *
     * Request body: [AddUserIdentifierPhoneRequest].
     *
     * Response body: [UserIdentifierPayload].
     *
     * **Audit logging:** Persist an [AuditEvent] for successful linking and security-relevant denials.
     * * **Action:** [UserAuditActionType.ADD_IDENTIFIER_PHONE].
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the current [UserId].
     * * **Resource:** [UserAuditResourceType.IDENTIFIER]. Set `resourceId` to the new identifier record id upon success.
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey])
     * 2. [UserAuditMetadataKey.PHONE_NUMBER] — the phone number being linked.
     */
    const val ADD_IDENTIFIER_PHONE = BaseOpenIdentifiersRoutes.ADD_IDENTIFIER_PHONE

    /**
     * **HTTP method:** `POST`
     *
     * Links an external authentication provider to the current account.
     *
     * Request body: [AddUserIdentifierExternalAuthProviderRequest].
     *
     * Response body: [UserIdentifierPayload].
     *
     * **Audit logging:** Persist an [AuditEvent] for successful linking and security-relevant denials.
     * * **Action:** [UserAuditActionType.ADD_IDENTIFIER_EXTERNAL_AUTH_PROVIDER].
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the current [UserId].
     * * **Resource:** [UserAuditResourceType.IDENTIFIER]. Set `resourceId` to the new identifier record id upon success.
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey])
     * 2. [UserAuditMetadataKey.EXTERNAL_ID] — the subject identifier from the external provider.
     */
    const val ADD_IDENTIFIER_EXTERNAL_AUTH_PROVIDER = BaseOpenIdentifiersRoutes.ADD_IDENTIFIER_EXTERNAL_AUTH_PROVIDER

    /**
     * **HTTP method:** `POST`
     *
     * Requests a confirmation code to add an email identifier.
     *
     * Request body: [SendConfirmationToEmailRequest].
     *
     * Response body: [OtpConfirmationPayload].
     */
    const val SEND_ADD_EMAIL_IDENTIFIER_CONFIRMATION = BaseOpenIdentifiersRoutes.SEND_ADD_EMAIL_IDENTIFIER_CONFIRMATION

    /**
     * **HTTP method:** `POST`
     *
     * Requests a confirmation code to add a phone identifier.
     *
     * Request body: [SendConfirmationToPhoneRequest].
     *
     * Response body: [OtpConfirmationPayload].
     */
    const val SEND_ADD_PHONE_IDENTIFIER_CONFIRMATION = BaseOpenIdentifiersRoutes.SEND_ADD_PHONE_IDENTIFIER_CONFIRMATION

    /**
     * **HTTP method:** `POST`
     *
     * Updates the account password using current credentials.
     *
     * Request body: [EmailPasswordChangeRequest].
     *
     * **Audit logging:** Persist an [AuditEvent] for successful password changes and security-relevant denials.
     * * **Action:** [UserAuditActionType.CHANGE_PASSWORD].
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the current [UserId].
     * * **Resource:** [UserAuditResourceType.IDENTIFIER]. Set `resourceId` to the identifier record id associated with the password.
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey])
     */
    const val IDENTIFIER_EMAIL_CHANGE_PASSWORD = BaseOpenIdentifiersRoutes.IDENTIFIER_EMAIL_CHANGE_PASSWORD
}