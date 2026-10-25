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
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSortValues
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
object SelfManagementIdentifierRoutes {
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
     * - [ListingParamNames.Sort.SORT_BY] — exactly one of
     *   [UserSortValues.UserIdentifierSortBy.CREATED_AT],
     *   [UserSortValues.UserIdentifierSortBy.UPDATED_AT].
     * - [ListingParamNames.Sort.SORT_ORDER] — [CommonApiFields.SortOrder.ASC] or [CommonApiFields.SortOrder.DESC].
     *
     * - **Filters** ([UserFilterValues.UserIdentifierFilterValues]): all filters are optional.
     *   Multi-value filters use repeated query keys with **OR** semantics
     *   (e.g., `?identifier=test&identifier=admin`); different keys combine as **AND**.
     *   - [UserFilterValues.UserIdentifierFilterValues.USER_AUTH_PROVIDER] — filter by [UserAuthProvider] serial name.
     *   - [UserFilterValues.UserIdentifierFilterValues.IDENTIFIER] — filter by substring pattern for identifier value.
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
     */
    const val GET_IDENTIFIERS = BASE_SELF_IDENTIFIERS_ROUTE

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
    const val GET_IDENTIFIER = "$BASE_SELF_IDENTIFIERS_ROUTE/{${UserApiPaths.USER_IDENTIFIER_ID}}"

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
    const val IDENTIFIER_EMAIL_CHANGE_PASSWORD =
        "$BASE_SELF_IDENTIFIERS_ROUTE/{${UserApiPaths.USER_IDENTIFIER_ID}}/change-password"
}