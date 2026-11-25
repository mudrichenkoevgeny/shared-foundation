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
public data class ClientInfo(
    public val clientDeviceInfo: ClientDeviceInfo,
    public val userAgent: String?,
    public val ipAddress: String?,
    public val host: String?,
    public val origin: String?,
    public val apiVersion: String?
)
