package io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.client

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Network payload DTO representing contextual client request metadata, aligned with [ClientInfo].
 *
 * @property clientDeviceInfo Hardware and environment information payload ([ClientDeviceInfoPayload]).
 * @property userAgent HTTP `User-Agent` header value.
 * @property ipAddress Client IP address.
 * @property apiVersion Negotiated API version string.
 */
@Serializable
data class ClientInfoPayload(
    @SerialName(CommonApiFields.CLIENT_DEVICE_INFO)
    val clientDeviceInfo: ClientDeviceInfoPayload,

    @SerialName(CommonApiFields.USER_AGENT)
    val userAgent: String?,

    @SerialName(CommonApiFields.IP_ADDRESS)
    val ipAddress: String?,

    @SerialName(CommonApiFields.API_VERSION)
    val apiVersion: String?
)
