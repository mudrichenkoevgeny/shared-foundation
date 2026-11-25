package io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.route.management.self

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.PagedResult
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.route.CommonNetworkRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.category.StorageCategory
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.StorageFilePrivate
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.listing.StorageFileFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.listing.StorageFileSortBy
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.status.StorageFileStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.visibility.StorageFileVisibility
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.route.management.ManagementSelfStorageNavigation
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.contract.StorageApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.file.StorageFilePrivatePayload
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.file.StorageFileSummaryPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.presigned.PresignedUrlPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.request.file.DeleteFilesBatchRequest
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.request.presigned.PresignedUrlRequest
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.route.BaseStorageNetworkRoutes

/**
 * Route paths for managing the current authenticated staff member's storage files in the management API (self-service).
 */
public object SelfManagementStorageRoutes {
    /** Base path for self-service staff storage operations. */
    private const val BASE_SELF_MANAGEMENT_STORAGE_ROUTE =
        "${CommonNetworkRoutes.BASE_SELF_MANAGEMENT_NETWORK_ROUTE}${BaseStorageNetworkRoutes.BASE_STORAGE_NETWORK_ROUTE}"

    /** Base path for self-service staff storage file endpoints. */
    private const val BASE_SELF_MANAGEMENT_STORAGE_FILES_ROUTE = "$BASE_SELF_MANAGEMENT_STORAGE_ROUTE/files"

    /**
     * **HTTP method:** `GET`
     *
     * Returns a paged list of summary storage files uploaded by the **current** authenticated staff member (self-service).
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
     * - **Required Permissions:** None.
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ManagementSelfStorageNavigation.FileList]
     *
     * @see StorageCategory
     * @see StorageFilePrivate
     * @see StorageFileStatus
     * @see StorageFileVisibility
     */
    public const val GET_FILES: String = BASE_SELF_MANAGEMENT_STORAGE_FILES_ROUTE

    /**
     * **HTTP method:** `GET`
     *
     * Retrieves detailed private view of a specific file uploaded by the **current** authenticated staff member (self-service).
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
     * - **Required Permissions:** None.
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ManagementSelfStorageNavigation.FileDetail]
     */
    public const val GET_FILE: String = "$BASE_SELF_MANAGEMENT_STORAGE_FILES_ROUTE/{${StorageApiPaths.FILE_ID}}"

    /**
     * **HTTP method:** `POST`
     *
     * Requests a presigned S3 upload URL for a new file uploaded by the **current** authenticated staff member.
     *
     * Request body: [PresignedUrlRequest].
     * Response body: [PresignedUrlPayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** `UserRole.STAFF`, `UserRole.ADMIN` (**OR** semantics).
     * - **Allowed Account Statuses:** `UserAccountStatus.ACTIVE` (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ManagementSelfStorageNavigation.FileUpload]
     */
    public const val POST_PRESIGNED_UPLOAD_URL: String = "$BASE_SELF_MANAGEMENT_STORAGE_ROUTE/presigned-url"

    /**
     * **HTTP method:** `POST`
     *
     * Confirms that a file has been successfully uploaded to S3 by the **current** authenticated staff member.
     * Triggers backend verification and transitions the file status from `PENDING_UPLOAD` to `READY`.
     *
     * Path parameter: [StorageApiPaths.FILE_ID].
     *
     * Request body: None.
     * Response body: [StorageFilePrivatePayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** `UserRole.STAFF`, `UserRole.ADMIN` (**OR** semantics).
     * - **Allowed Account Statuses:** `UserAccountStatus.ACTIVE` (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ManagementSelfStorageNavigation.FileUpload]
     */
    public const val CONFIRM_UPLOAD: String =
        "$BASE_SELF_MANAGEMENT_STORAGE_FILES_ROUTE/{${StorageApiPaths.FILE_ID}}/confirm"

    /**
     * **HTTP method:** `DELETE`
     *
     * Deletes a specific file uploaded by the **current** authenticated staff member (self-service deletion).
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
     * - **Required Permissions:** None.
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ManagementSelfStorageNavigation.FileDetail]
     */
    public const val DELETE_FILE: String = "$BASE_SELF_MANAGEMENT_STORAGE_FILES_ROUTE/{${StorageApiPaths.FILE_ID}}"

    /**
     * **HTTP method:** `POST`
     *
     * Deletes multiple specific files uploaded by the **current** authenticated staff member
     * in a single batch operation (self-service batch deletion).
     *
     * Request body: [DeleteFilesBatchRequest].
     * Response body: None (204 No Content).
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** `UserRole.STAFF`, `UserRole.ADMIN` (**OR** semantics).
     * - **Allowed Account Statuses:** `UserAccountStatus.ACTIVE` (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ManagementSelfStorageNavigation.FileList]
     */
    public const val DELETE_FILES_BATCH: String = "$BASE_SELF_MANAGEMENT_STORAGE_FILES_ROUTE/batch-delete"
}
