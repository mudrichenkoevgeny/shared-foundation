package io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client

data class ClientInfo(
    val deviceInfo: ClientDeviceInfo,
    val userAgent: String?,
    val ipAddress: String?,
    val host: String?,
    val origin: String?,
    val apiVersion: String?
)
