package io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.route.management.self

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.PagedResult
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.route.CommonNetworkRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.listing.StorageFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.listing.StorageSortValues
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.route.management.ManagementStorageNavigation
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
object SelfManagementStorageRoutes {

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
     * - [ListingParamNames.Sort.SORT_BY] — exactly one of
     *   [StorageSortValues.StorageFileSortBy.CREATED_AT],
     *   [StorageSortValues.StorageFileSortBy.UPDATED_AT],
     *   [StorageSortValues.StorageFileSortBy.FILE_NAME],
     *   [StorageSortValues.StorageFileSortBy.FILE_SIZE_BYTES].
     * - [ListingParamNames.Sort.SORT_ORDER] — [CommonApiFields.SortOrder.ASC] or [CommonApiFields.SortOrder.DESC].
     *
     * - **Filters** ([StorageFilterValues.StorageFileFilterValues]): all filters are optional.
     *   - [StorageFilterValues.StorageFileFilterValues.CATEGORY] — filter by category.
     *   - [StorageFilterValues.StorageFileFilterValues.VISIBILITY] — filter by visibility.
     *   - [StorageFilterValues.StorageFileFilterValues.STATUS] — filter by status.
     *   - [StorageFilterValues.StorageFileFilterValues.MIME_TYPE] — filter by MIME type substring.
     *   - [StorageFilterValues.StorageFileFilterValues.FILE_NAME] — filter by file name substring.
     *
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
     *   - [ManagementStorageNavigation.FileList]
     */
    const val GET_FILES = BASE_SELF_MANAGEMENT_STORAGE_FILES_ROUTE

    /**
     * **HTTP method:** `GET`
     *
     * Retrieves detailed private view of a specific file uploaded by the **current** authenticated staff member (self-service).
     *
     * Path parameter: [StorageApiPaths.FILE_ID].
     *
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
     *   - [ManagementStorageNavigation.FileDetail]
     */
    const val GET_FILE = "$BASE_SELF_MANAGEMENT_STORAGE_FILES_ROUTE/{${StorageApiPaths.FILE_ID}}"

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
     *   - [ManagementStorageNavigation.FileUpload]
     */
    const val POST_PRESIGNED_UPLOAD_URL = "$BASE_SELF_MANAGEMENT_STORAGE_ROUTE/presigned-url"

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
     *   - [ManagementStorageNavigation.FileUpload]
     */
    const val CONFIRM_UPLOAD = "$BASE_SELF_MANAGEMENT_STORAGE_FILES_ROUTE/{${StorageApiPaths.FILE_ID}}/confirm"

    /**
     * **HTTP method:** `DELETE`
     *
     * Deletes a specific file uploaded by the **current** authenticated staff member (self-service deletion).
     *
     * Path parameter: [StorageApiPaths.FILE_ID].
     *
     * Request body: None.
     * Response body: None (`204 No Content`).
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** `UserRole.STAFF`, `UserRole.ADMIN` (**OR** semantics).
     * - **Allowed Account Statuses:** `UserAccountStatus.ACTIVE` (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ManagementStorageNavigation.FileDetail]
     */
    const val DELETE_FILE = "$BASE_SELF_MANAGEMENT_STORAGE_FILES_ROUTE/{${StorageApiPaths.FILE_ID}}"

    /**
     * **HTTP method:** `POST`
     *
     * Deletes multiple specific files uploaded by the **current** authenticated staff member in a single batch operation (self-service batch deletion).
     *
     * Request body: [DeleteFilesBatchRequest].
     * Response body: None.
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** `UserRole.STAFF`, `UserRole.ADMIN` (**OR** semantics).
     * - **Allowed Account Statuses:** `UserAccountStatus.ACTIVE` (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ManagementStorageNavigation.FileList]
     */
    const val DELETE_FILES_BATCH = "$BASE_SELF_MANAGEMENT_STORAGE_FILES_ROUTE/batch-delete"
}
