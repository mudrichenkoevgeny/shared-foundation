package io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client

data class ClientDeviceInfo(
    val deviceId: ClientDeviceId? = null,
    val deviceName: String? = null,
    val clientType: ClientType? = null,
    val language: String? = null,
    val appVersion: String? = null,
    val operationSystemVersion: String? = null
) {

    fun isMobileClient(): Boolean = clientType?.isMobileClient() == true
}