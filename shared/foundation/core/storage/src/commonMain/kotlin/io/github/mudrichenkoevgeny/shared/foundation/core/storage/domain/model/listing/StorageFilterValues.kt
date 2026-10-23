package io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.listing

import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.category.StorageCategory
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.StorageFileInternal
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.status.StorageFileStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.visibility.StorageFileVisibility
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.contract.StorageApiFields

/**
 * Stable keys for filtering storage file listings.
 *
 * Keys follow the declaration order of properties in [StorageFileInternal].
 * Use these constants when building HTTP query strings or parsing list requests.
 */
object StorageFilterValues {

    /**
     * Optional filters when listing storage files.
     *
     * If a parameter is absent, there is no filtering on that axis before pagination and sort.
     */
    object StorageFileFilterValues {
        /** Filter by specific file IDs (hex-dash UUID strings). */
        const val FILE_ID = StorageApiFields.FILE_ID

        /** Filter by owner user ID (hex-dash UUID string). */
        const val OWNER_USER_ID = StorageApiFields.OWNER_USER_ID

        /** Filter by original file name substring match. */
        const val FILE_NAME = StorageApiFields.FILE_NAME

        /** Filter by MIME type substring or exact match. */
        const val MIME_TYPE = StorageApiFields.MIME_TYPE

        /** Filter by storage category wire values ([StorageCategory.value]). */
        const val CATEGORY = StorageApiFields.CATEGORY

        /** Filter by storage visibility wire values ([StorageFileVisibility.value]). */
        const val VISIBILITY = StorageApiFields.VISIBILITY

        /** Filter by storage file status wire values ([StorageFileStatus.value]). */
        const val STATUS = StorageApiFields.STATUS

        /** Filter by target scope or feature context strings. */
        const val TARGET_SCOPE = StorageApiFields.TARGET_SCOPE
    }
}
