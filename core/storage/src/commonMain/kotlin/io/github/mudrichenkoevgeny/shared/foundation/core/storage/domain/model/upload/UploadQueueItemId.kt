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
public value class UploadQueueItemId(
    public val value: Uuid
) {
    /**
     * Returns the identifier as a canonical hex string with dashes.
     */
    public fun asHexDashString(): String = value.toHexDashString()

    public companion object {
        /**
         * Generates a new random [UploadQueueItemId].
         */
        public fun generate(): UploadQueueItemId = UploadQueueItemId(Uuid.random())
    }
}

/**
 * Attempts to parse this string as an [UploadQueueItemId].
 *
 * Returns `null` if the value is not a valid UUID in hex-with-dashes form.
 */
public fun String.toUploadQueueItemIdOrNull(): UploadQueueItemId? =
    Uuid.parseOrNull(this)?.let { UploadQueueItemId(it) }

/**
 * Parses this string into an [UploadQueueItemId] or throws if the value is not a valid UUID.
 */
public fun String.toUploadQueueItemIdOrThrow(): UploadQueueItemId = UploadQueueItemId(Uuid.parse(this))
