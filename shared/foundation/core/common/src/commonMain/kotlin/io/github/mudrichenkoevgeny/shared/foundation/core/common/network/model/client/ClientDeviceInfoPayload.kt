package io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.client

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ClientDeviceInfoPayload(
    @SerialName(CommonApiFields.CLIENT_TYPE)
    val clientType: String? = null,

    @SerialName(CommonApiFields.LANGUAGE)
    val language: String? = null,

    @SerialName(CommonApiFields.DEVICE_ID)
    val deviceId: String? = null,

    @SerialName(CommonApiFields.DEVICE_NAME)
    val deviceName: String? = null,

    @SerialName(CommonApiFields.APP_VERSION)
    val appVersion: String? = null,

    @SerialName(CommonApiFields.OPERATION_SYSTEM_VERSION)
    val operationSystemVersion: String? = null
)
