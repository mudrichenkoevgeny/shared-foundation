package io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.category

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val CATEGORY_IMAGE = "image"
private const val CATEGORY_VIDEO = "video"
private const val CATEGORY_AUDIO = "audio"
private const val CATEGORY_DOCUMENT = "document"
private const val CATEGORY_OTHER = "other"

/**
 * Generic classification of uploaded media or document files.
 *
 * Used by backend servers to apply dynamic file size limits, MIME type rules, and processing pipelines.
 *
 * @property value String representation of the [StorageCategory].
 */
@Serializable
enum class StorageCategory(val value: String) {
    /**
     * Image assets (e.g. PNG, JPEG, WEBP, SVG).
     */
    @SerialName(CATEGORY_IMAGE)
    IMAGE(CATEGORY_IMAGE),

    /**
     * Video assets (e.g. MP4, WEBM, MOV).
     */
    @SerialName(CATEGORY_VIDEO)
    VIDEO(CATEGORY_VIDEO),

    /**
     * Audio assets (e.g. MP3, WAV, AAC, OGG).
     */
    @SerialName(CATEGORY_AUDIO)
    AUDIO(CATEGORY_AUDIO),

    /**
     * Document files (e.g. PDF, DOCX, XLSX, TXT).
     */
    @SerialName(CATEGORY_DOCUMENT)
    DOCUMENT(CATEGORY_DOCUMENT),

    /**
     * Generic or unclassified files.
     */
    @SerialName(CATEGORY_OTHER)
    OTHER(CATEGORY_OTHER);

    companion object {
        /**
         * Returns [StorageCategory] based on the provided string value, or null if the value is invalid.
         */
        fun fromValueOrNull(value: String): StorageCategory? =
            entries.firstOrNull {
                it.value.equals(value, ignoreCase = true) ||
                        it.name.equals(value, ignoreCase = true)
            }

        /**
         * Returns [StorageCategory] for the given wire or enum-style string (case-insensitive enum name).
         *
         * @throws IllegalArgumentException if [value] does not match any [StorageCategory].
         */
        fun fromValueOrThrow(value: String): StorageCategory =
            fromValueOrNull(value) ?: throw IllegalArgumentException("Unknown value for StorageCategory: '$value'")
    }
}
