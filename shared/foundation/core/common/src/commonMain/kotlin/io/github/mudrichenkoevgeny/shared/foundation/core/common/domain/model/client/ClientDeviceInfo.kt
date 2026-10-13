package io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client

/**
 * Detailed information about the client device and environment.
 *
 * @property deviceId Unique identifier of the device ([ClientDeviceId]).
 * @property deviceName Human-readable device name or model.
 * @property clientType Category of client application ([ClientType]).
 * @property language Preferred language code.
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

    /**
     * Extracts the primary locale from the raw Accept-Language string.
     * For example: "ru-RU,ru;q=0.9,en-US;q=0.8" -> "ru-RU"
     */
    fun getPrimaryLanguage(): String? {
        val rawLanguage = language ?: return null
        return rawLanguage.split(',').firstOrNull()?.substringBefore(';')?.trim()
    }
}