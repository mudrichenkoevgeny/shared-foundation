package io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.visibility

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val VISIBILITY_PUBLIC = "public"
private const val VISIBILITY_PRIVATE = "private"

/**
 * Access visibility classification of a storage file.
 *
 * @property value String representation of the [StorageFileVisibility].
 */
@Serializable
enum class StorageFileVisibility(val value: String) {
    /**
     * File is publicly accessible via permanent URL or CDN.
     */
    @SerialName(VISIBILITY_PUBLIC)
    PUBLIC(VISIBILITY_PUBLIC),

    /**
     * File access is restricted; requires owner authentication or temporary presigned read URLs.
     */
    @SerialName(VISIBILITY_PRIVATE)
    PRIVATE(VISIBILITY_PRIVATE);

    companion object {
        /**
         * Returns [StorageFileVisibility] based on the provided string value, or null if the value is invalid.
         */
        fun fromValueOrNull(value: String): StorageFileVisibility? =
            entries.firstOrNull {
                it.value.equals(value, ignoreCase = true) ||
                        it.name.equals(value, ignoreCase = true)
            }

        /**
         * Returns [StorageFileVisibility] for the given wire or enum-style string.
         *
         * @throws IllegalArgumentException if [value] does not match any [StorageFileVisibility].
         */
        fun fromValueOrThrow(value: String): StorageFileVisibility =
            fromValueOrNull(value) ?: throw IllegalArgumentException("Unknown value for StorageFileVisibility: '$value'")
    }
}
