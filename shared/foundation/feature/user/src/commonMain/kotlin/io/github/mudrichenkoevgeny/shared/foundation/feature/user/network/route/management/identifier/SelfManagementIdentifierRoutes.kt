package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.identifier

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEvent
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CommonAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.PagedResult
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.action.UserAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.resource.UserAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSortValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.identifier.UserIdentifierPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.security.password.EmailPasswordChangeRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.identifier.BaseSelfManagementIdentifierRoutes

/**
 * Route paths for managing the current authenticated principal's identifiers in the management API (self-service).
 */
object SelfManagementIdentifierRoutes {
    /**
     * **HTTP method:** `GET`
     *
     * Returns identifiers linked to the **current** authenticated management account.
     *
     * **Pagination & sort** (names from [ListingParamNames]):
     * - [ListingParamNames.Pagination.PAGE_NUMBER] — one-based page index.
     * - [ListingParamNames.Pagination.PAGE_SIZE] — page size.
     * - [ListingParamNames.Sort.SORT_BY] — exactly one of
     * [UserSortValues.UserIdentifierSortBy.CREATED_AT],
     * [UserSortValues.UserIdentifierSortBy.UPDATED_AT].
     * - [ListingParamNames.Sort.SORT_ORDER] — [CommonApiFields.SortOrder.ASC] or [CommonApiFields.SortOrder.DESC].
     *
     * **Filters** ([UserFilterValues.UserIdentifierFilterValues], optional). Same key repeated means **OR**;
     * different keys combine as **AND**.
     * - [UserFilterValues.UserIdentifierFilterValues.USER_AUTH_PROVIDER] — list of [UserAuthProvider] serial names.
     * - [UserFilterValues.UserIdentifierFilterValues.IDENTIFIER] — list of substring patterns for identifier values.
     *
     * Response body: [PagedResult] of [UserIdentifierPayload].
     */
    const val GET_IDENTIFIERS = BaseSelfManagementIdentifierRoutes.GET_IDENTIFIERS

    /**
     * **HTTP method:** `POST`
     *
     * Updates the management account password using current credentials.
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
    const val IDENTIFIER_EMAIL_CHANGE_PASSWORD = BaseSelfManagementIdentifierRoutes.IDENTIFIER_EMAIL_CHANGE_PASSWORD
}