package io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.route.open

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.PagedResult
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.listing.StorageFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.listing.StorageSortValues
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.route.open.OpenStorageNavigation
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.contract.StorageApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.file.StorageFilePrivatePayload
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.file.StorageFileSummaryPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.presigned.PresignedUrlPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.request.file.DeleteFilesBatchRequest
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.request.presigned.PresignedUrlRequest
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.route.BaseStorageNetworkRoutes

/**
 * Route paths for open storage operations in the open API.
 */
object OpenStorageRoutes {

    /** Base path for open storage file endpoints. */
    private const val BASE_STORAGE_FILES_ROUTE = "${BaseStorageNetworkRoutes.BASE_STORAGE_NETWORK_ROUTE}/files"

    /**
     * **HTTP method:** `GET`
     *
     * Returns a paged list of summary storage files uploaded by the **current** authenticated user (self-service).
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
     * Request body: None.
     * Response body: [PagedResult] of [StorageFileSummaryPayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** `UserRole.USER` (**OR** semantics).
     * - **Allowed Account Statuses:** `UserAccountStatus.ACTIVE`, `UserAccountStatus.READ_ONLY` (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **UI Presentation:**
     * - Screens:
     *   - [OpenStorageNavigation.FileList]
     */
    const val GET_FILES = BASE_STORAGE_FILES_ROUTE

    /**
     * **HTTP method:** `GET`
     *
     * Retrieves detailed private view of a specific file uploaded by the **current** authenticated user (self-service).
     *
     * Path parameter: [StorageApiPaths.FILE_ID].
     *
     * Request body: None.
     * Response body: [StorageFilePrivatePayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** `UserRole.USER` (**OR** semantics).
     * - **Allowed Account Statuses:** `UserAccountStatus.ACTIVE`, `UserAccountStatus.READ_ONLY` (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **UI Presentation:**
     * - Screens:
     *   - [OpenStorageNavigation.FileDetail]
     */
    const val GET_FILE = "$BASE_STORAGE_FILES_ROUTE/{${StorageApiPaths.FILE_ID}}"

    /**
     * **HTTP method:** `POST`
     *
     * Requests a presigned S3 upload URL for a new file.
     *
     * Request body: [PresignedUrlRequest].
     * Response body: [PresignedUrlPayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** `UserRole.USER` (**OR** semantics).
     * - **Allowed Account Statuses:** `UserAccountStatus.ACTIVE` (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **UI Presentation:**
     * - Screens:
     *   - [OpenStorageNavigation.FileUpload]
     */
    const val POST_PRESIGNED_UPLOAD_URL = "${BaseStorageNetworkRoutes.BASE_STORAGE_NETWORK_ROUTE}/presigned-url"

    /**
     * **HTTP method:** `POST`
     *
     * Confirms that a file has been successfully uploaded to S3.
     * Triggers backend verification and transitions the file status from `PENDING_UPLOAD` to `READY`.
     *
     * Path parameter: [StorageApiPaths.FILE_ID].
     *
     * Request body: None.
     * Response body: [StorageFilePrivatePayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** `UserRole.USER` (**OR** semantics).
     * - **Allowed Account Statuses:** `UserAccountStatus.ACTIVE` (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **UI Presentation:**
     * - Screens:
     *   - [OpenStorageNavigation.FileUpload]
     */
    const val CONFIRM_UPLOAD = "$BASE_STORAGE_FILES_ROUTE/{${StorageApiPaths.FILE_ID}}/confirm"

    /**
     * **HTTP method:** `DELETE`
     *
     * Deletes a specific file uploaded by the **current** authenticated user (self-service deletion).
     *
     * Path parameter: [StorageApiPaths.FILE_ID].
     *
     * Request body: None.
     * Response body: None (204 No Content).
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** `UserRole.USER` (**OR** semantics).
     * - **Allowed Account Statuses:** `UserAccountStatus.ACTIVE` (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **UI Presentation:**
     * - Screens:
     *   - [OpenStorageNavigation.FileDetail]
     */
    const val DELETE_FILE = "$BASE_STORAGE_FILES_ROUTE/{${StorageApiPaths.FILE_ID}}"

    /**
     * **HTTP method:** `POST`
     *
     * Deletes multiple specific files uploaded by the **current** authenticated user in a single batch operation (self-service batch deletion).
     *
     * Request body: [DeleteFilesBatchRequest].
     * Response body: None (204 No Content).
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** `UserRole.USER` (**OR** semantics).
     * - **Allowed Account Statuses:** `UserAccountStatus.ACTIVE` (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **UI Presentation:**
     * - Screens:
     *   - [OpenStorageNavigation.FileList]
     */
    const val DELETE_FILES_BATCH = "$BASE_STORAGE_FILES_ROUTE/batch-delete"
}
