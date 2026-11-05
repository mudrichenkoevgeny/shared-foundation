package io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client

import kotlin.jvm.JvmInline
import kotlin.uuid.Uuid

/**
 * Opaque identifier of a physical or logical client device.
 */
@JvmInline
public value class ClientDeviceId(
    public val value: Uuid
) {
    /**
     * Returns the underlying [Uuid] as a canonical hex string with dashes.
     */
    public fun asHexDashString(): String = value.toHexDashString()

    public companion object {
        /**
         * Generates a new random [ClientDeviceId].
         */
        public fun generate(): ClientDeviceId = ClientDeviceId(Uuid.random())
    }
}

/**
 * Attempts to parse this string as a [ClientDeviceId].
 *
 * Returns `null` if the value is not a valid UUID in hex-with-dashes form.
 */
public fun String.toClientDeviceIdOrNull(): ClientDeviceId? = Uuid.parseOrNull(this)?.let { ClientDeviceId(it) }

/**
 * Parses this string into a [ClientDeviceId] or throws if the value is not a valid UUID.
 */
public fun String.toClientDeviceIdOrThrow(): ClientDeviceId = ClientDeviceId(Uuid.parse(this))
