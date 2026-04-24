package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.identifier

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEvent
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CommonAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.PagedResult
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.action.UserAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.metadata.UserAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.resource.UserAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission.IdentifierPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSortValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.identifier.UserIdentifierPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.identifier.BaseManagementIdentifierRoutes

/**
 * Route paths for user identifiers in the management API (list, single read, delete).
 */
object ManagementIdentifierRoutes {
    /**
     * **HTTP method:** `GET`
     *
     * **Pagination & sort** (names from [ListingParamNames]):
     * - [ListingParamNames.Pagination.PAGE_NUMBER] — one-based page index (`1` is the first page).
     * - [ListingParamNames.Pagination.PAGE_SIZE] — page size.
     * - [ListingParamNames.Sort.SORT_BY] — exactly one of
     * [UserSortValues.UserIdentifierSortBy.CREATED_AT],
     * [UserSortValues.UserIdentifierSortBy.UPDATED_AT].
     * - [ListingParamNames.Sort.SORT_ORDER] — order [CommonApiFields.SortOrder.ASC] or
     * [CommonApiFields.SortOrder.DESC].
     *
     * **Filters** ([UserFilterValues.UserIdentifierFilterValues]): [UserFilterValues.UserIdentifierFilterValues.USER_ID]
     * required. Same key repeated means **OR**; different keys combine as **AND**.
     *
     * - [UserFilterValues.UserIdentifierFilterValues.USER_ID] — list of user IDs.
     * - [UserFilterValues.UserIdentifierFilterValues.USER_AUTH_PROVIDER] — list of [UserAuthProvider] serial names ([UserIdentifierPayload.userAuthProvider]).
     * - [UserFilterValues.UserIdentifierFilterValues.IDENTIFIER] — list of free-text identifiers; server-defined matching.
     *
     * **Authorization** ([IdentifierPermissionCode]): for identifiers owned by [UserRole.USER], caller needs
     * [IdentifierPermissionCode.IDENTIFIER_GET_OF_USER_MASKED] and/or
     * [IdentifierPermissionCode.IDENTIFIER_GET_OF_USER_UNMASKED]; for [UserRole.STAFF],
     * [IdentifierPermissionCode.IDENTIFIER_GET_OF_STAFF_MASKED] and/or
     * [IdentifierPermissionCode.IDENTIFIER_GET_OF_STAFF_UNMASKED]. Each element includes
     * [UserIdentifierPayload.isSensitiveValuesMasked] accordingly. No constant in [IdentifierPermissionCode] grants read
     * access to identifiers of users with [UserRole.ADMIN].
     *
     * Response body: [PagedResult] of [UserIdentifierPayload].
     */
    const val GET_IDENTIFIERS = BaseManagementIdentifierRoutes.GET_IDENTIFIERS

    /**
     * **HTTP method:** `GET`
     *
     * Path parameter: [UserApiPaths.USER_IDENTIFIER_ID].
     *
     * **Authorization** ([IdentifierPermissionCode]): for identifiers owned by [UserRole.USER], caller needs
     * [IdentifierPermissionCode.IDENTIFIER_GET_OF_USER_MASKED] and/or
     * [IdentifierPermissionCode.IDENTIFIER_GET_OF_USER_UNMASKED]; for [UserRole.STAFF],
     * [IdentifierPermissionCode.IDENTIFIER_GET_OF_STAFF_MASKED] and/or
     * [IdentifierPermissionCode.IDENTIFIER_GET_OF_STAFF_UNMASKED]. The response [UserIdentifierPayload] includes
     * [UserIdentifierPayload.isSensitiveValuesMasked] accordingly. No constant in [IdentifierPermissionCode] grants read
     * access to identifiers of users with [UserRole.ADMIN].
     *
     * Response body: [UserIdentifierPayload].
     */
    const val GET_IDENTIFIER = BaseManagementIdentifierRoutes.GET_IDENTIFIER

    /**
     * **HTTP method:** `DELETE`
     *
     * Removes the identifier record [UserApiPaths.USER_IDENTIFIER_ID] for the user [UserApiPaths.USER_ID].
     *
     * Path parameter: [UserApiPaths.USER_IDENTIFIER_ID].
     *
     * **Authorization:** [IdentifierPermissionCode.IDENTIFIER_DELETE_FOR_USER] when the identifier owner has
     * [UserRole.USER]; [IdentifierPermissionCode.IDENTIFIER_DELETE_FOR_STAFF] when the owner has [UserRole.STAFF]
     * (server aligns [UserRole.ADMIN] with the staff-scoped delete grant if applicable).
     *
     * **Audit logging:** Persist an [AuditEvent] for successful removal and security-relevant denials.
     * * **Action:** [UserAuditActionType.MANAGEMENT_DELETE_IDENTIFIER].
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the administrator [UserId] performing the deletion.
     * * **Resource:** [UserAuditResourceType.IDENTIFIER]. Set `resourceId` to the [UserApiPaths.USER_IDENTIFIER_ID].
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey])
     * 2. [UserAuditMetadataKey.USER_ID] — the owner of the identifier from [UserApiPaths.USER_ID].
     */
    const val DELETE_IDENTIFIER = BaseManagementIdentifierRoutes.DELETE_IDENTIFIER
}