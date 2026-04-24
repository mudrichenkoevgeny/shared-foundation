package io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client

import kotlin.jvm.JvmInline
import kotlin.uuid.Uuid

/**
 * Opaque identifier of a physical or logical client device.
 */
@JvmInline
value class ClientDeviceId(val value: Uuid) {

    /**
     * Returns the underlying [Uuid] as a canonical hex string with dashes.
     */
    fun asHexDashString(): String = value.toHexDashString()

    companion object {

        /**
         * Generates a new random [ClientDeviceId].
         */
        fun generate() = ClientDeviceId(Uuid.random())
    }
}

/**
 * Attempts to parse this string as a [ClientDeviceId].
 *
 * Returns `null` if the value is not a valid UUID in hex-with-dashes form.
 */
fun String.toClientDeviceIdOrNull(): ClientDeviceId? =
    Uuid.parseOrNull(this)?.let { ClientDeviceId(it) }

/**
 * Parses this string into a [ClientDeviceId] or throws if the value is not a valid UUID.
 */
fun String.toClientDeviceIdOrThrow(): ClientDeviceId = ClientDeviceId(Uuid.parse(this))