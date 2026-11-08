package io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.listing

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.StorageFilePrivate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val SORT_BY_FILE_NAME = "file_name"
private const val SORT_BY_FILE_SIZE_BYTES = "file_size_bytes"

/**
 * Supported `sort_by` wire values for storage file list endpoints, aligned with fields on [StorageFilePrivate].
 *
 * @property value Wire value for `sort_by`.
 */
@Serializable
public enum class StorageFileSortBy(
    public val value: String
) {
    /** Sort by creation timestamp. */
    @SerialName(CommonApiFields.CREATED_AT)
    CREATED_AT(CommonApiFields.CREATED_AT),

    /** Sort by update timestamp. */
    @SerialName(CommonApiFields.UPDATED_AT)
    UPDATED_AT(CommonApiFields.UPDATED_AT),

    /** Sort by file name. */
    @SerialName(SORT_BY_FILE_NAME)
    FILE_NAME(SORT_BY_FILE_NAME),

    /** Sort by file size in bytes. */
    @SerialName(SORT_BY_FILE_SIZE_BYTES)
    FILE_SIZE_BYTES(SORT_BY_FILE_SIZE_BYTES);

    public companion object {
        /** Default sort field ([CREATED_AT]). */
        public val DEFAULT: StorageFileSortBy = CREATED_AT

        /**
         * Returns [StorageFileSortBy] for the given wire value or case-insensitive enum name.
         */
        public fun fromValueOrNull(value: String): StorageFileSortBy? = entries.firstOrNull {
            it.value.equals(value, ignoreCase = true) ||
                it.name.equals(value, ignoreCase = true)
        }

        /**
         * Returns [StorageFileSortBy] for the given wire or enum-style string.
         *
         * @throws IllegalArgumentException if [value] does not match any [StorageFileSortBy].
         */
        public fun fromValueOrThrow(value: String): StorageFileSortBy =
            fromValueOrNull(value) ?: throw IllegalArgumentException(
                "Unknown value for ${StorageFileSortBy::class.simpleName}: '$value'"
            )
    }
}
