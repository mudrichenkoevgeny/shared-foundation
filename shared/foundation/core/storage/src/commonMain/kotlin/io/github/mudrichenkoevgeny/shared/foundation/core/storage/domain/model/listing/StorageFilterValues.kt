package io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.listing

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.CommonFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.category.StorageCategory
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.StorageFilePrivate
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.status.StorageFileStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.visibility.StorageFileVisibility
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.contract.StorageApiFields

/**
 * Stable **keys** for filtering storage file listings.
 *
 * Keys follow the declaration order of properties in [StorageFilePrivate].
 * Use these constants when building HTTP query strings, parsing list requests, or mapping filters in services and
 * persistence so clients, servers, and documentation stay aligned.
 */
object StorageFileFilterValues {
    /** Filter by one or more specific file IDs. */
    const val FILE_ID = StorageApiFields.FILE_ID

    /** Filter by one or more owner user IDs. */
    const val OWNER_USER_ID = StorageApiFields.OWNER_USER_ID

    /** Filter by boolean flag indicating whether the file has an owner (true) or is anonymous/system (false). */
    const val IS_OWNER_USER_ID_SET = "is_owner_user_id_set"

    /** Filter by exact original file name. */
    const val FILE_NAME = StorageApiFields.FILE_NAME

    /** Filter by inclusive lower bound for file size in bytes. */
    const val FILE_SIZE_BYTES_FROM = "file_size_bytes_from"

    /** Filter by inclusive upper bound for file size in bytes. */
    const val FILE_SIZE_BYTES_TO = "file_size_bytes_to"

    /** Filter by exact MIME type (e.g. `image/png`). */
    const val MIME_TYPE = StorageApiFields.MIME_TYPE

    /** Filter by one or more storage category wire values ([StorageCategory.value]) (OR semantics). */
    const val CATEGORY = StorageApiFields.CATEGORY

    /** Filter to exclude specific storage categories ([StorageCategory.value]) (NOT IN semantics). */
    const val EXCLUDE_CATEGORY = "exclude_category"

    /** Filter by one or more storage visibility wire values ([StorageFileVisibility.value]) (OR semantics). */
    const val VISIBILITY = StorageApiFields.VISIBILITY

    /** Filter to exclude specific storage visibilities ([StorageFileVisibility.value]) (NOT IN semantics). */
    const val EXCLUDE_VISIBILITY = "exclude_visibility"

    /** Filter by one or more storage file status wire values ([StorageFileStatus.value]) (OR semantics). */
    const val STATUS = StorageApiFields.STATUS

    /** Filter to exclude specific storage file statuses ([StorageFileStatus.value]) (NOT IN semantics). */
    const val EXCLUDE_STATUS = "exclude_status"

    /** Filter by one or more target scope or feature context strings. */
    const val TARGET_SCOPE = StorageApiFields.TARGET_SCOPE

    /** Filter by boolean flag indicating whether target scope is assigned. */
    const val IS_TARGET_SCOPE_SET = "is_target_scope_set"

    /** Inclusive lower bound filter key for creation timestamp. */
    const val CREATED_AT_FROM = CommonFilterValues.CREATED_AT_FROM

    /** Inclusive upper bound filter key for creation timestamp. */
    const val CREATED_AT_TO = CommonFilterValues.CREATED_AT_TO

    /** Inclusive lower bound filter key for update timestamp. */
    const val UPDATED_AT_FROM = CommonFilterValues.UPDATED_AT_FROM

    /** Inclusive upper bound filter key for update timestamp. */
    const val UPDATED_AT_TO = CommonFilterValues.UPDATED_AT_TO

    /** Filter by boolean flag indicating whether the file record has been modified. */
    const val IS_UPDATED_AT_SET = "is_updated_at_set"

    /**
     * Free-text search query string targeting:
     * - Original file name ([StorageFilePrivate.fileName]).
     */
    const val SEARCH_QUERY = ListingParamNames.Search.SEARCH_QUERY
}
