package io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client

/**
 * Detailed information about the client device and environment.
 *
 * @property deviceId Unique identifier of the device ([ClientDeviceId]).
 * @property deviceName Human-readable device name or model.
 * @property clientType Category of client application ([ClientType]).
 * @property language Primary BCP 47 language or locale code (e.g. `en-US`).
 * @property appVersion Version of the client application.
 * @property operationSystemVersion Operating system version of the device.
 */
data class ClientDeviceInfo(
    val deviceId: ClientDeviceId?,
    val deviceName: String?,
    val clientType: ClientType?,
    val language: String?,
    val appVersion: String?,
    val operationSystemVersion: String?
) {

    fun isMobileClient(): Boolean = clientType?.isMobileClient() == true

    companion object {
        /**
         * Extracts the primary locale code from a raw `Accept-Language` header string.
         * For example: `"en-US,en;q=0.9,ru;q=0.8"` -> `"en-US"`.
         */
        fun parsePrimaryLanguage(rawAcceptLanguageHeader: String?): String? {
            val raw = rawAcceptLanguageHeader?.trim() ?: return null
            if (raw.isBlank()) return null
            return raw.split(',').firstOrNull()?.substringBefore(';')?.trim()?.takeIf { it.isNotBlank() }
        }
    }
}