package io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.upload

import kotlin.jvm.JvmInline
import kotlin.uuid.Uuid

/**
 * Stable, unique identifier for a client upload queue item.
 *
 * Wraps a [Uuid] to provide type safety across upload queues, UI state, and worker tasks.
 *
 * @property value UUID backing the identifier.
 */
@JvmInline
value class UploadQueueItemId(val value: Uuid) {

    /**
     * Returns the identifier as a canonical hex string with dashes.
     */
    fun asHexDashString(): String = value.toHexDashString()

    companion object {

        /**
         * Generates a new random [UploadQueueItemId].
         */
        fun generate() = UploadQueueItemId(Uuid.random())
    }
}

/**
 * Attempts to parse this string as an [UploadQueueItemId].
 *
 * Returns `null` if the value is not a valid UUID in hex-with-dashes form.
 */
fun String.toUploadQueueItemIdOrNull(): UploadQueueItemId? =
    Uuid.parseOrNull(this)?.let { UploadQueueItemId(it) }

/**
 * Parses this string into an [UploadQueueItemId] or throws if the value is not a valid UUID.
 */
fun String.toUploadQueueItemIdOrThrow(): UploadQueueItemId = UploadQueueItemId(Uuid.parse(this))
