package io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client

data class ClientInfo(
    val deviceInfo: ClientDeviceInfo = ClientDeviceInfo(),
    val userAgent: String? = null,
    val ipAddress: String? = null,
    val host: String? = null,
    val origin: String? = null,
    val apiVersion: String? = null
)
