package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.user.identifier

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventPrivate
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CommonAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.PagedResult
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.security.error.naming.SecurityErrorCodes
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.otpconfirmation.OtpConfirmationPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.action.UserAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.metadata.UserAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.resource.UserAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserIdentifierFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserIdentifierSortBy
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.route.client.ClientUserNavigation
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.identifier.UserIdentifierPrivatePayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.identifier.UserIdentifierSummaryPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.confirmation.SendConfirmationToEmailRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.confirmation.SendConfirmationToPhoneRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.security.password.EmailPasswordChangeRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.security.useridentifiers.AddUserIdentifierEmailRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.security.useridentifiers.AddUserIdentifierExternalAuthProviderRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.security.useridentifiers.AddUserIdentifierPhoneRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.user.OpenUserRoutes.BASE_USER_ROUTE
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.user.session.OpenSessionRoutes

/**
 * Route paths for managing user authentication identifiers in the open API.
 */
public object OpenIdentifierRoutes {
    /** Base path for managing authentication identifiers linked to the current account. */
    private const val BASE_IDENTIFIERS_ROUTE = "$BASE_USER_ROUTE/identifiers"

    /** Base path for email identifier operations. */
    private const val EMAIL_IDENTIFIER_ROUTE = "$BASE_IDENTIFIERS_ROUTE/email"

    /** Base path for phone identifier operations. */
    private const val PHONE_IDENTIFIER_ROUTE = "$BASE_IDENTIFIERS_ROUTE/phone"

    /** Base path for external auth provider identifier operations. */
    private const val EXTERNAL_AUTH_PROVIDER_IDENTIFIER_ROUTE =
        "$BASE_IDENTIFIERS_ROUTE/external-auth-provider"

    /** Base path for identifier verification (confirmation codes). */
    private const val BASE_IDENTIFIER_CONFIRMATION_ROUTE = "$BASE_IDENTIFIERS_ROUTE/confirmation"

    /**
     * **HTTP method:** `GET`
     *
     * Returns summary identifiers linked to the **current** authenticated account (self-service).
     *
     * **Pagination & sort** (names from [ListingParamNames]):
     * - [ListingParamNames.Pagination.PAGE_NUMBER] — one-based page index (`1` is the first page).
     * - [ListingParamNames.Pagination.PAGE_SIZE] — page size.
     * - [ListingParamNames.Sort.SORT_BY] — exactly one of:
     *   - [UserIdentifierSortBy.CREATED_AT]
     *   - [UserIdentifierSortBy.UPDATED_AT]
     *   - [UserIdentifierSortBy.IDENTIFIER]
     *   - [UserIdentifierSortBy.DISPLAY_NAME]
     * - [ListingParamNames.Sort.SORT_ORDER] — exactly one of:
     *   - [CommonApiFields.SortOrder.ASC]
     *   - [CommonApiFields.SortOrder.DESC]
     *
     * - **Filters** ([UserIdentifierFilterValues]): all filters are optional.
     *   Multi-value filters use repeated query keys with **OR** semantics; different keys combine as **AND**.
     *   - **Multi-value Include Filters** (repeated query keys, **OR** semantics):
     *     - [UserIdentifierFilterValues.IDENTIFIER_ID] — filter by one or more specific identifier IDs.
     *     - [UserIdentifierFilterValues.USER_AUTH_PROVIDER] — filter by one or more [UserAuthProvider] serial names.
     *   - **Multi-value Exclude Filters** (repeated query keys, **NOT IN** semantics):
     *     - [UserIdentifierFilterValues.EXCLUDE_USER_AUTH_PROVIDER] — exclude specific [UserAuthProvider] serial names.
     *   - **Exact Filters**:
     *     - [UserIdentifierFilterValues.IDENTIFIER] — filter by exact raw or masked login identifier string.
     *     - [UserIdentifierFilterValues.DISPLAY_NAME] — filter by exact display name.
     *     - [UserIdentifierFilterValues.EXTERNAL_PROVIDER_EMAIL] — filter by exact external provider email.
     *   - **Presence Filters**:
     *     - [UserIdentifierFilterValues.IS_EXTERNAL_PROVIDER_EMAIL_SET] — filter by external provider email presence.
     *     - [UserIdentifierFilterValues.IS_UPDATED_AT_SET] — filter by whether record has been updated.
     *   - **Range Bounds** (inclusive bounds):
     *     - [UserIdentifierFilterValues.CREATED_AT_FROM] — inclusive lower bound creation timestamp.
     *     - [UserIdentifierFilterValues.CREATED_AT_TO] — inclusive upper bound creation timestamp.
     *     - [UserIdentifierFilterValues.UPDATED_AT_FROM] — inclusive lower bound update timestamp.
     *     - [UserIdentifierFilterValues.UPDATED_AT_TO] — inclusive upper bound update timestamp.
     *   - **Free-text Search**:
     *     - [UserIdentifierFilterValues.SEARCH_QUERY] — free-text search query string targeting:
     *       - Raw or masked login identifier ([UserIdentifierPrivate.identifier]).
     *       - Display name ([UserIdentifierPrivate.displayName]).
     *       - External provider email ([UserIdentifierPrivate.externalProviderEmail]).
     *
     * Request body: None.
     * Response body: [PagedResult] of [UserIdentifierSummaryPayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.USER] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY] (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ClientUserNavigation.IdentifierList]
     */
    public const val GET_IDENTIFIERS: String = BASE_IDENTIFIERS_ROUTE

    /**
     * **HTTP method:** `GET`
     *
     * Retrieves specific private identifier details.
     *
     * Path parameter: [UserApiPaths.USER_IDENTIFIER_ID].
     *
     * Request body: None.
     * Response body: [UserIdentifierPrivatePayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.USER] (**OR** semantics).
     * - **Allowed Account Statuses:** Any [UserAccountStatus] (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ClientUserNavigation.IdentifierDetail]
     */
    public const val GET_IDENTIFIER: String = "$BASE_IDENTIFIERS_ROUTE/{${UserApiPaths.USER_IDENTIFIER_ID}}"

    /**
     * **HTTP method:** `DELETE`
     *
     * Removes the identifier for the given record id.
     *
     * Path parameter: [UserApiPaths.USER_IDENTIFIER_ID].
     *
     * Request body: None.
     * Response body: None (204 No Content).
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.USER] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY] (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **Security:** Sensitive operation. MFA Step-up required (if enabled for the account). Returns
     * [SecurityErrorCodes.MFA_CONFIRMATION_REQUIRED] if additional verification is needed.
     * Session must be verified via [OpenSessionRoutes.REAUTHENTICATE_SESSION] if stale.
     *
     * **Audit logging:** Persist an [AuditEventPrivate] for successful execution and all failed attempts.
     * - **Action:** [UserAuditActionType.SELF_DELETE_IDENTIFIER].
     * - **Actor:** [AuditActorType.USER]. Set `actorId` to the current [UserId].
     * - **Resource:** [UserAuditResourceType.IDENTIFIER]. Set `resourceId` to the [UserApiPaths.USER_IDENTIFIER_ID].
     * - **Metadata:** Include:
     *   1. [ClientInfo] (see [CommonAuditMetadataKey]).
     *   2. [UserAuditMetadataKey.SESSION_ID] — the unique identifier of the authenticated session.
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ClientUserNavigation.IdentifierList]
     *   - [ClientUserNavigation.IdentifierDetail]
     */
    public const val DELETE_IDENTIFIER: String = "$BASE_IDENTIFIERS_ROUTE/{${UserApiPaths.USER_IDENTIFIER_ID}}"

    /**
     * **HTTP method:** `POST`
     *
     * Links an email identifier to the current account after verification.
     *
     * Request body: [AddUserIdentifierEmailRequest].
     * Response body: [UserIdentifierPrivatePayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.USER] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY] (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **Security:** Sensitive operation. MFA Step-up required (if enabled for the account). Returns
     * [SecurityErrorCodes.MFA_CONFIRMATION_REQUIRED] if additional verification is needed.
     * Session must be verified via [OpenSessionRoutes.REAUTHENTICATE_SESSION] if stale.
     *
     * **Audit logging:** Persist an [AuditEventPrivate] for successful execution and all failed attempts.
     * - **Action:** [UserAuditActionType.ADD_IDENTIFIER_EMAIL].
     * - **Actor:** [AuditActorType.USER]. Set `actorId` to the current [UserId].
     * - **Resource:** [UserAuditResourceType.IDENTIFIER]. Set `resourceId` to the new identifier record id upon success.
     * - **Metadata:** Include:
     *   1. [ClientInfo] (see [CommonAuditMetadataKey]).
     *   2. [UserAuditMetadataKey.SESSION_ID] — the unique identifier of the authenticated session.
     *   3. [UserAuditMetadataKey.EMAIL_ADDRESS] — the email address being linked.
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ClientUserNavigation.IdentifierList]
     */
    public const val ADD_IDENTIFIER_EMAIL: String = EMAIL_IDENTIFIER_ROUTE

    /**
     * **HTTP method:** `POST`
     *
     * Links a phone identifier to the current account after verification.
     *
     * Request body: [AddUserIdentifierPhoneRequest].
     * Response body: [UserIdentifierPrivatePayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.USER] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY] (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **Security:** Sensitive operation. MFA Step-up required (if enabled for the account). Returns
     * [SecurityErrorCodes.MFA_CONFIRMATION_REQUIRED] if additional verification is needed.
     * Session must be verified via [OpenSessionRoutes.REAUTHENTICATE_SESSION] if stale.
     *
     * **Audit logging:** Persist an [AuditEventPrivate] for successful execution and all failed attempts.
     * - **Action:** [UserAuditActionType.ADD_IDENTIFIER_PHONE].
     * - **Actor:** [AuditActorType.USER]. Set `actorId` to the current [UserId].
     * - **Resource:** [UserAuditResourceType.IDENTIFIER]. Set `resourceId` to the new identifier record id upon success.
     * - **Metadata:** Include:
     *   1. [ClientInfo] (see [CommonAuditMetadataKey]).
     *   2. [UserAuditMetadataKey.SESSION_ID] — the unique identifier of the authenticated session.
     *   3. [UserAuditMetadataKey.PHONE_NUMBER] — the phone number being linked.
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ClientUserNavigation.IdentifierList]
     */
    public const val ADD_IDENTIFIER_PHONE: String = PHONE_IDENTIFIER_ROUTE

    /**
     * **HTTP method:** `POST`
     *
     * Links an external authentication provider to the current account.
     *
     * Request body: [AddUserIdentifierExternalAuthProviderRequest].
     * Response body: [UserIdentifierPrivatePayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.USER] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY] (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **Security:** Sensitive operation. MFA Step-up required (if enabled for the account). Returns
     * [SecurityErrorCodes.MFA_CONFIRMATION_REQUIRED] if additional verification is needed.
     * Session must be verified via [OpenSessionRoutes.REAUTHENTICATE_SESSION] if stale.
     *
     * **Audit logging:** Persist an [AuditEventPrivate] for successful execution and all failed attempts.
     * - **Action:** [UserAuditActionType.ADD_IDENTIFIER_EXTERNAL_AUTH_PROVIDER].
     * - **Actor:** [AuditActorType.USER]. Set `actorId` to the current [UserId].
     * - **Resource:** [UserAuditResourceType.IDENTIFIER]. Set `resourceId` to the new identifier record id upon success.
     * - **Metadata:** Include:
     *   1. [ClientInfo] (see [CommonAuditMetadataKey]).
     *   2. [UserAuditMetadataKey.SESSION_ID] — the unique identifier of the authenticated session.
     *   3. [UserAuditMetadataKey.EXTERNAL_ID] — the subject identifier from the external provider.
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ClientUserNavigation.IdentifierList]
     */
    public const val ADD_IDENTIFIER_EXTERNAL_AUTH_PROVIDER: String = EXTERNAL_AUTH_PROVIDER_IDENTIFIER_ROUTE

    /**
     * **HTTP method:** `POST`
     *
     * Requests a confirmation code to add an email identifier.
     *
     * Request body: [SendConfirmationToEmailRequest].
     * Response body: [OtpConfirmationPayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.USER] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY] (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ClientUserNavigation.IdentifierList]
     */
    public const val SEND_ADD_EMAIL_IDENTIFIER_CONFIRMATION: String =
        "$BASE_IDENTIFIER_CONFIRMATION_ROUTE/send-to-email"

    /**
     * **HTTP method:** `POST`
     *
     * Requests a confirmation code to add a phone identifier.
     *
     * Request body: [SendConfirmationToPhoneRequest].
     * Response body: [OtpConfirmationPayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.USER] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY] (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ClientUserNavigation.IdentifierList]
     */
    public const val SEND_ADD_PHONE_IDENTIFIER_CONFIRMATION: String =
        "$BASE_IDENTIFIER_CONFIRMATION_ROUTE/send-to-phone"

    /**
     * **HTTP method:** `POST`
     *
     * Updates the account password using current credentials.
     *
     * Path parameter: [UserApiPaths.USER_IDENTIFIER_ID].
     *
     * Request body: [EmailPasswordChangeRequest].
     * Response body: None (204 No Content).
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.USER] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY] (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **Security:** Sensitive operation. MFA Step-up required (if enabled for the account). Returns
     * [SecurityErrorCodes.MFA_CONFIRMATION_REQUIRED] if additional verification is needed.
     * Session must be verified via [OpenSessionRoutes.REAUTHENTICATE_SESSION] if stale.
     *
     * **Audit logging:** Persist an [AuditEventPrivate] for successful execution and all failed attempts.
     * - **Action:** [UserAuditActionType.CHANGE_PASSWORD].
     * - **Actor:** [AuditActorType.USER]. Set `actorId` to the current [UserId].
     * - **Resource:** [UserAuditResourceType.IDENTIFIER]. Set `resourceId` to the identifier record id
     *   associated with the password.
     * - **Metadata:** Include:
     *   1. [ClientInfo] (see [CommonAuditMetadataKey]).
     *   2. [UserAuditMetadataKey.SESSION_ID] — the unique identifier of the authenticated session.
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ClientUserNavigation.IdentifierDetail]
     */
    public const val IDENTIFIER_EMAIL_CHANGE_PASSWORD: String =
        "$BASE_IDENTIFIERS_ROUTE/{${UserApiPaths.USER_IDENTIFIER_ID}}/change-password"
}
