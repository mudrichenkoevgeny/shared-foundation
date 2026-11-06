package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.self.user.identifier

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventPrivate
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CommonAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.PagedResult
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.security.error.naming.SecurityErrorCodes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.action.UserAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.metadata.UserAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.resource.UserAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierPrivate
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserIdentifierFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserIdentifierSortBy
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.route.management.ManagementSelfUserNavigation
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.identifier.UserIdentifierPrivatePayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.identifier.UserIdentifierSummaryPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.security.password.EmailPasswordChangeRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.self.user.SelfManagementUserRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.self.user.session.SelfManagementSessionRoutes

/**
 * Route paths for managing the current authenticated principal's identifiers in the management API (self-service).
 */
public object SelfManagementIdentifierRoutes {
    /** Base path for self-service identifier operations in management API. */
    private const val BASE_SELF_IDENTIFIERS_ROUTE =
        "${SelfManagementUserRoutes.BASE_SELF_MANAGEMENT_USER_ROUTE}/identifiers"

    /**
     * **HTTP method:** `GET`
     *
     * Returns summary identifiers linked to the **current** authenticated management account.
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
     * - **Allowed Roles:** [UserRole.STAFF], [UserRole.ADMIN] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY] (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ManagementSelfUserNavigation.IdentifierList]
     *
     * @see UserAuthProvider
     * @see UserIdentifierPrivate
     */
    public const val GET_IDENTIFIERS: String = BASE_SELF_IDENTIFIERS_ROUTE

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
     * - **Allowed Roles:** [UserRole.STAFF], [UserRole.ADMIN] (**OR** semantics).
     * - **Allowed Account Statuses:** Any [UserAccountStatus] (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ManagementSelfUserNavigation.IdentifierDetail]
     */
    public const val GET_IDENTIFIER: String = "$BASE_SELF_IDENTIFIERS_ROUTE/{${UserApiPaths.USER_IDENTIFIER_ID}}"

    /**
     * **HTTP method:** `POST`
     *
     * Updates the management account password using current credentials.
     *
     * Path parameter: [UserApiPaths.USER_IDENTIFIER_ID].
     *
     * Request body: [EmailPasswordChangeRequest].
     * Response body: None (204 No Content).
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.STAFF], [UserRole.ADMIN] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY]
     *   (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **Security:** Sensitive operation. MFA Step-up required (if enabled for the account). Returns
     * [SecurityErrorCodes.MFA_CONFIRMATION_REQUIRED] if additional verification is needed.
     * Session must be verified via [SelfManagementSessionRoutes.REAUTHENTICATE_SESSION] if stale.
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
     *   - [ManagementSelfUserNavigation.IdentifierDetail]
     */
    public const val IDENTIFIER_EMAIL_CHANGE_PASSWORD: String =
        "$BASE_SELF_IDENTIFIERS_ROUTE/{${UserApiPaths.USER_IDENTIFIER_ID}}/change-password"
}
