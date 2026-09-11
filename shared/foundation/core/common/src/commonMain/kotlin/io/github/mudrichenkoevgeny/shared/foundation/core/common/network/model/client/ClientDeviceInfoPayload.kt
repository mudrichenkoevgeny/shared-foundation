package io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.client

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientDeviceInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientType
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Network payload DTO representing client device information, aligned with [ClientDeviceInfo].
 *
 * @property clientType Wire value matching [ClientType.serialName].
 * @property language Preferred language code.
 * @property deviceId Unique identifier of the device as a hex-dash string.
 * @property deviceName Human-readable device name or model.
 * @property appVersion Version of the client application.
 * @property operationSystemVersion Operating system version of the device.
 */
@Serializable
data class ClientDeviceInfoPayload(
    @SerialName(CommonApiFields.CLIENT_TYPE)
    val clientType: String?,

    @SerialName(CommonApiFields.LANGUAGE)
    val language: String?,

    @SerialName(CommonApiFields.DEVICE_ID)
    val deviceId: String?,

    @SerialName(CommonApiFields.DEVICE_NAME)
    val deviceName: String?,

    @SerialName(CommonApiFields.APP_VERSION)
    val appVersion: String?,

    @SerialName(CommonApiFields.OPERATION_SYSTEM_VERSION)
    val operationSystemVersion: String?
)
