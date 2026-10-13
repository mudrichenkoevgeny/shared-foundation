package io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client

/**
 * Contextual metadata about a client request or connection.
 *
 * @property clientDeviceInfo Hardware and environment information ([ClientDeviceInfo]).
 * @property userAgent HTTP `User-Agent` header value.
 * @property ipAddress Client IP address.
 * @property host HTTP `Host` header value.
 * @property origin HTTP `Origin` header value.
 * @property apiVersion Negotiated API version string.
 */
data class ClientInfo(
    val clientDeviceInfo: ClientDeviceInfo,
    val userAgent: String?,
    val ipAddress: String?,
    val host: String?,
    val origin: String?,
    val apiVersion: String?
)
