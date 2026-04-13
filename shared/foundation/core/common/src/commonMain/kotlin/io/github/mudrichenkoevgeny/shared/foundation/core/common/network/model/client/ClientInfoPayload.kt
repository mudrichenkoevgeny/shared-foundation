package io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.client

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ClientInfoPayload(
    @SerialName(CommonApiFields.CLIENT_DEVICE_INFO)
    val clientDeviceInfo: ClientDeviceInfoPayload = ClientDeviceInfoPayload(),

    @SerialName(CommonApiFields.USER_AGENT)
    val userAgent: String? = null,

    @SerialName(CommonApiFields.IP_ADDRESS)
    val ipAddress: String? = null,

    @SerialName(CommonApiFields.API_VERSION)
    val apiVersion: String? = null
)
