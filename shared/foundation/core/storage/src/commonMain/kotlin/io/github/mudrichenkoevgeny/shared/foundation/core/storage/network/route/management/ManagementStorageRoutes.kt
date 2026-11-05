package io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.route.management

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventPrivate
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CommonAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.PagedResult
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.route.CommonNetworkRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.audit.action.StorageAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.audit.metadata.StorageAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.audit.resource.StorageAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.listing.StorageFileFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.listing.StorageFileSortBy
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.permission.StoragePermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.route.management.ManagementStorageNavigation
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.contract.StorageApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.file.StorageFilePrivatePayload
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.file.StorageFileSummaryPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.request.file.DeleteFilesBatchRequest
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.route.BaseStorageNetworkRoutes

/**
 * Route paths for storage administrative management in the management API.
 */
public object ManagementStorageRoutes {
    /** Base path for management storage endpoints. */
    private const val BASE_MANAGEMENT_STORAGE_ROUTE =
        "${CommonNetworkRoutes.BASE_MANAGEMENT_NETWORK_ROUTE}${BaseStorageNetworkRoutes.BASE_STORAGE_NETWORK_ROUTE}"

    /** Base path for management storage file endpoints. */
    private const val BASE_MANAGEMENT_STORAGE_FILES_ROUTE = "$BASE_MANAGEMENT_STORAGE_ROUTE/files"

    /**
     * **HTTP method:** `GET`
     *
     * Returns a paged list of all storage files across the platform for administrative inspection.
     *
     * **Pagination & sort** (names from [ListingParamNames]):
     * - [ListingParamNames.Pagination.PAGE_NUMBER] — one-based page index (`1` is the first page).
     * - [ListingParamNames.Pagination.PAGE_SIZE] — page size.
     * - [ListingParamNames.Sort.SORT_BY] — exactly one of:
     *   - [StorageFileSortBy.CREATED_AT]
     *   - [StorageFileSortBy.UPDATED_AT]
     *   - [StorageFileSortBy.FILE_NAME]
     *   - [StorageFileSortBy.FILE_SIZE_BYTES]
     * - [ListingParamNames.Sort.SORT_ORDER] — exactly one of:
     *   - [CommonApiFields.SortOrder.ASC]
     *   - [CommonApiFields.SortOrder.DESC]
     *
     * - **Filters** ([StorageFileFilterValues]): all filters are optional.
     *   Multi-value filters use repeated query keys with **OR** semantics; different keys combine as **AND**.
     *   - **Multi-value Include Filters** (repeated query keys, **OR** semantics):
     *     - [StorageFileFilterValues.FILE_ID] — filter by one or more specific file IDs.
     *     - [StorageFileFilterValues.OWNER_USER_ID] — filter by one or more owner user IDs.
     *     - [StorageFileFilterValues.CATEGORY] — filter by one or more [StorageCategory] serial names.
     *     - [StorageFileFilterValues.VISIBILITY] — filter by one or more [StorageFileVisibility] serial names.
     *     - [StorageFileFilterValues.STATUS] — filter by one or more [StorageFileStatus] serial names.
     *     - [StorageFileFilterValues.TARGET_SCOPE] — filter by one or more target scope or feature context strings.
     *   - **Multi-value Exclude Filters** (repeated query keys, **NOT IN** semantics):
     *     - [StorageFileFilterValues.EXCLUDE_CATEGORY] — exclude specific [StorageCategory] serial names.
     *     - [StorageFileFilterValues.EXCLUDE_VISIBILITY] — exclude specific [StorageFileVisibility] serial names.
     *     - [StorageFileFilterValues.EXCLUDE_STATUS] — exclude specific [StorageFileStatus] serial names.
     *   - **Exact Filters**:
     *     - [StorageFileFilterValues.FILE_NAME] — filter by exact original file name.
     *     - [StorageFileFilterValues.MIME_TYPE] — filter by exact MIME type.
     *   - **Presence Filters**:
     *     - [StorageFileFilterValues.IS_OWNER_USER_ID_SET] — filter by whether file has an owner user ID.
     *     - [StorageFileFilterValues.IS_TARGET_SCOPE_SET] — filter by whether target scope is assigned.
     *     - [StorageFileFilterValues.IS_UPDATED_AT_SET] — filter by whether file record has been updated.
     *   - **Range Bounds** (inclusive bounds):
     *     - [StorageFileFilterValues.FILE_SIZE_BYTES_FROM] — inclusive lower bound for file size in bytes.
     *     - [StorageFileFilterValues.FILE_SIZE_BYTES_TO] — inclusive upper bound for file size in bytes.
     *     - [StorageFileFilterValues.CREATED_AT_FROM] — inclusive lower bound creation timestamp.
     *     - [StorageFileFilterValues.CREATED_AT_TO] — inclusive upper bound creation timestamp.
     *     - [StorageFileFilterValues.UPDATED_AT_FROM] — inclusive lower bound update timestamp.
     *     - [StorageFileFilterValues.UPDATED_AT_TO] — inclusive upper bound update timestamp.
     *   - **Free-text Search**:
     *     - [StorageFileFilterValues.SEARCH_QUERY] — free-text search query string targeting:
     *       - Original file name ([StorageFilePrivate.fileName]).
     *
     * Request body: None.
     * Response body: [PagedResult] of [StorageFileSummaryPayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** `UserRole.STAFF`, `UserRole.ADMIN` (**OR** semantics).
     * - **Allowed Account Statuses:** `UserAccountStatus.ACTIVE`, `UserAccountStatus.READ_ONLY` (**OR** semantics).
     * - **Required Permissions:** [StoragePermissionCode.STORAGE_VIEW_OTHER_USERS_FILES].
     * - **Authority Level:** Actor's level must be strictly greater than the target file owner's authority level.
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ManagementStorageNavigation.FileList]
     */
    public const val GET_FILES: String = BASE_MANAGEMENT_STORAGE_FILES_ROUTE

    /**
     * **HTTP method:** `GET`
     *
     * Retrieves detailed private view of any specific storage file in the platform.
     *
     * Path parameter: [StorageApiPaths.FILE_ID].
     *
     * Request body: None.
     * Response body: [StorageFilePrivatePayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** `UserRole.STAFF`, `UserRole.ADMIN` (**OR** semantics).
     * - **Allowed Account Statuses:** `UserAccountStatus.ACTIVE`, `UserAccountStatus.READ_ONLY` (**OR** semantics).
     * - **Required Permissions:** [StoragePermissionCode.STORAGE_VIEW_OTHER_USERS_FILES].
     * - **Authority Level:** Actor's level must be strictly greater than the target file owner's authority level.
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ManagementStorageNavigation.FileDetail]
     */
    public const val GET_FILE: String = "$BASE_MANAGEMENT_STORAGE_FILES_ROUTE/{${StorageApiPaths.FILE_ID}}"

    /**
     * **HTTP method:** `POST`
     *
     * Deletes multiple specific files in a single batch operation, removing their binaries from S3, ignoring ownership.
     *
     * Request body: [DeleteFilesBatchRequest].
     * Response body: None (204 No Content).
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** `UserRole.STAFF`, `UserRole.ADMIN` (**OR** semantics).
     * - **Allowed Account Statuses:** `UserAccountStatus.ACTIVE` (**OR** semantics).
     * - **Required Permissions:** [StoragePermissionCode.STORAGE_DELETE_OTHER_USERS_FILES].
     * - **Authority Level:** Actor's level must be strictly greater than the target file owner's authority level.
     *
     * **Audit logging:** Persist an [AuditEventPrivate] for **each** file ID in the batch request.
     * - **Action:** [StorageAuditActionType.MANAGEMENT_DELETE_FILE].
     * - **Actor:** [AuditActorType.USER]. Set `actorId` to the administrator performing the deletion.
     * - **Resource:** [StorageAuditResourceType.STORAGE_FILE]. Set `resourceId` to the specific `file_id`.
     * - **Metadata:** Include:
     *   1. [ClientInfo] (see [CommonAuditMetadataKey]).
     *   2. [StorageAuditMetadataKey.FILE_NAME] — the original name of the deleted file.
     *   3. [StorageAuditMetadataKey.FILE_SIZE_BYTES] — the binary size of the deleted file in bytes.
     *   4. [StorageAuditMetadataKey.CATEGORY] — the storage classification category of the deleted file.
     *   5. [StorageAuditMetadataKey.S3_KEY] — the internal S3 object key or storage path of the deleted file.
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ManagementStorageNavigation.FileList]
     */
    public const val DELETE_FILES_BATCH: String = "$BASE_MANAGEMENT_STORAGE_FILES_ROUTE/batch-delete"

    /**
     * **HTTP method:** `DELETE`
     *
     * Deletes a specific file and removes its binary from S3, ignoring ownership.
     *
     * Path parameter: [StorageApiPaths.FILE_ID].
     *
     * Request body: None.
     * Response body: None (204 No Content).
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** `UserRole.STAFF`, `UserRole.ADMIN` (**OR** semantics).
     * - **Allowed Account Statuses:** `UserAccountStatus.ACTIVE` (**OR** semantics).
     * - **Required Permissions:** [StoragePermissionCode.STORAGE_DELETE_OTHER_USERS_FILES].
     * - **Authority Level:** Actor's level must be strictly greater than the target file owner's authority level.
     *
     * **Audit logging:** Persist an [AuditEventPrivate] for successful execution and all failed attempts.
     * - **Action:** [StorageAuditActionType.MANAGEMENT_DELETE_FILE].
     * - **Actor:** [AuditActorType.USER]. Set `actorId` to the administrator performing the deletion.
     * - **Resource:** [StorageAuditResourceType.STORAGE_FILE]. Set `resourceId` to the [StorageApiPaths.FILE_ID].
     * - **Metadata:** Include:
     *   1. [ClientInfo] (see [CommonAuditMetadataKey]).
     *   2. [StorageAuditMetadataKey.FILE_NAME] — the original name of the deleted file.
     *   3. [StorageAuditMetadataKey.FILE_SIZE_BYTES] — the binary size of the deleted file in bytes.
     *   4. [StorageAuditMetadataKey.CATEGORY] — the storage classification category of the deleted file.
     *   5. [StorageAuditMetadataKey.S3_KEY] — the internal S3 object key or storage path of the deleted file.
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ManagementStorageNavigation.FileDetail]
     */
    public const val DELETE_FILE: String = "$BASE_MANAGEMENT_STORAGE_FILES_ROUTE/{${StorageApiPaths.FILE_ID}}"
}
