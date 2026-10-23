package io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file

import kotlin.jvm.JvmInline
import kotlin.uuid.Uuid

/**
 * Stable, unique identifier for a storage file record.
 *
 * Wraps a [Uuid] to provide type safety across repositories, API DTOs, and domain models.
 *
 * @property value UUID backing the identifier.
 */
@JvmInline
value class FileId(val value: Uuid) {

    /**
     * Returns the identifier as a canonical hex string with dashes.
     */
    fun asHexDashString(): String = value.toHexDashString()

    companion object {

        /**
         * Generates a new random [FileId].
         */
        fun generate() = FileId(Uuid.random())
    }
}

/**
 * Attempts to parse this string as a [FileId].
 *
 * Returns `null` if the value is not a valid UUID in hex-with-dashes form.
 */
fun String.toFileIdOrNull(): FileId? =
    Uuid.parseOrNull(this)?.let { FileId(it) }

/**
 * Parses this string into a [FileId] or throws if the value is not a valid UUID.
 */
fun String.toFileIdOrThrow(): FileId = FileId(Uuid.parse(this))
