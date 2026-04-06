package io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client

data class ClientDeviceInfo(
    val deviceId: ClientDeviceId,
    val deviceName: String,
    val clientType: ClientType,
    val language: String,
    val appVersion: String,
    val operationSystemVersion: String
) {

    fun isMobileClient(): Boolean = clientType.isMobileClient()

    companion object {
        const val VERSION_UNKNOWN = "unknown"
    }
}