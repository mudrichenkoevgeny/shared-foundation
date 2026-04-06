package io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client

import kotlin.jvm.JvmInline
import kotlin.uuid.Uuid

/**
 * Opaque identifier of a physical or logical client device.
 *
 * The format is intentionally unconstrained so that callers can plug in
 * platform-specific identifiers, push tokens or installation ids as needed.
 *
 * @param value Raw string backing the id; use [asHexDashString] for serialization.
 */
@JvmInline
value class ClientDeviceId(val value: String) {

    /**
     * Returns the underlying value as a canonical string (e.g. for logging or API).
     */
    fun asHexDashString(): String = value

    companion object {

        /**
         * Generates a new random [ClientDeviceId] (UUID hex-with-dashes form).
         */
        fun generate() = ClientDeviceId(Uuid.random().toHexDashString())
    }
}

/**
 * Attempts to parse this string as a [ClientDeviceId].
 *
 * Returns `null` if the string is blank.
 */
fun String.toClientDeviceIdOrNull(): ClientDeviceId? =
    takeIf { it.isNotBlank() }?.let { ClientDeviceId(it) }

/**
 * Parses this string into a [ClientDeviceId] or throws if the string is blank.
 */
fun String.toClientDeviceIdOrThrow(): ClientDeviceId {
    require(isNotBlank()) { "ClientDeviceId cannot be blank" }
    return ClientDeviceId(this)
}