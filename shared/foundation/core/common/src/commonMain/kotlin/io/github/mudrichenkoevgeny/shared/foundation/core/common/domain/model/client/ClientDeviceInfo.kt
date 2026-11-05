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
public data class ClientDeviceInfo(
    public val deviceId: ClientDeviceId?,
    public val deviceName: String?,
    public val clientType: ClientType?,
    public val language: String?,
    public val appVersion: String?,
    public val operationSystemVersion: String?
) {
    public fun isMobileClient(): Boolean = clientType?.isMobileClient() == true

    public companion object {
        /**
         * Extracts the primary locale code from a raw `Accept-Language` header string.
         * For example: `"en-US,en;q=0.9,ru;q=0.8"` -> `"en-US"`.
         */
        public fun parsePrimaryLanguage(rawAcceptLanguageHeader: String?): String? {
            val raw = rawAcceptLanguageHeader?.trim() ?: return null
            if (raw.isBlank()) return null
            return raw
                .split(',')
                .firstOrNull()
                ?.substringBefore(';')
                ?.trim()
                ?.takeIf { it.isNotBlank() }
        }
    }
}
