package io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.listing

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.contract.StorageApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val SORT_BY_FILE_NAME = "file_name"
private const val SORT_BY_FILE_SIZE_BYTES = "file_size_bytes"

/**
 * Supported `sort_by` wire values for storage file list endpoints.
 */
object StorageSortValues {

    /**
     * Supported `sort_by` values for storage file list endpoints.
     *
     * @property value Wire value for `sort_by`.
     */
    @Serializable
    enum class StorageFileSortBy(val value: String) {
        /** Sort by creation timestamp ([CommonApiFields.CREATED_AT]). */
        @SerialName(CommonApiFields.CREATED_AT)
        CREATED_AT(CommonApiFields.CREATED_AT),

        /** Sort by update timestamp ([CommonApiFields.UPDATED_AT]). */
        @SerialName(CommonApiFields.UPDATED_AT)
        UPDATED_AT(CommonApiFields.UPDATED_AT),

        /** Sort by file name ([StorageApiFields.FILE_NAME]). */
        @SerialName(SORT_BY_FILE_NAME)
        FILE_NAME(SORT_BY_FILE_NAME),

        /** Sort by file size in bytes ([StorageApiFields.FILE_SIZE_BYTES]). */
        @SerialName(SORT_BY_FILE_SIZE_BYTES)
        FILE_SIZE_BYTES(SORT_BY_FILE_SIZE_BYTES);

        companion object {
            /** Default sort field ([CREATED_AT]). */
            val DEFAULT = CREATED_AT

            /**
             * Returns [StorageFileSortBy] for the given wire value or case-insensitive enum name.
             */
            fun fromValueOrNull(value: String): StorageFileSortBy? =
                entries.firstOrNull {
                    it.value.equals(value, ignoreCase = true) ||
                            it.name.equals(value, ignoreCase = true)
                }

            /**
             * Returns [StorageFileSortBy] for the given wire or enum-style string.
             *
             * @throws IllegalArgumentException if [value] does not match any [StorageFileSortBy].
             */
            fun fromValueOrThrow(value: String): StorageFileSortBy =
                fromValueOrNull(value) ?: throw IllegalArgumentException("Unknown value for StorageFileSortBy: '$value'")
        }
    }
}
