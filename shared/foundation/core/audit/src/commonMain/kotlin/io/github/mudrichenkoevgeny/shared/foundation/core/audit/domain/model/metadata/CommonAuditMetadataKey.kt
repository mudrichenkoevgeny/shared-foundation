package io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditValueSensitivity
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientDeviceId
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientDeviceInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientType
import io.github.mudrichenkoevgeny.shared.foundation.core.common.error.model.ApiErrorResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val KEY_DEVICE_ID = "device_id"
private const val KEY_DEVICE_NAME = "device_name"
private const val KEY_CLIENT_TYPE = "client_type"
private const val KEY_LANGUAGE = "language"
private const val KEY_APP_VERSION = "app_version"
private const val KEY_OS_VERSION = "os_version"
private const val KEY_USER_AGENT = "user_agent"
private const val KEY_IP_ADDRESS = "key_ip_address"
private const val KEY_HOST = "host"
private const val KEY_ORIGIN = "origin"
private const val KEY_API_VERSION = "api_version"
private const val KEY_TRACE_ID = "trace_id"
private const val KEY_DENIED_REASON = "denied_reason"
private const val KEY_ERROR_ID = "error_id"
private const val KEY_ERROR_CODE = "error_code"

/**
 * Cross-cutting audit metadata keys for [AuditEventMetadata].
 *
 * The first keys follow the field order of [ClientDeviceInfo] then [ClientInfo] (`deviceId` … `apiVersion`), then diagnostic keys,
 * then [DENIED_REASON], [ERROR_ID], and [ERROR_CODE] last.
 *
 * @property value String representation of the [CommonAuditMetadataKey].
 * @property valueSensitivity Sensitivity level for audit logging.
 */
@Serializable
enum class CommonAuditMetadataKey(
    override val value: String,
    override val valueSensitivity: AuditValueSensitivity
) : AuditMetadataKey {
    /**
     * From [ClientDeviceInfo.deviceId]; [AuditEventMetadata.value] aligns with [ClientDeviceId] wire form.
     */
    @SerialName(KEY_DEVICE_ID)
    DEVICE_ID(KEY_DEVICE_ID, AuditValueSensitivity.FULL_VALUE_MASK),

    /**
     * From [ClientDeviceInfo.deviceName] (e.g. platform-reported device label).
     */
    @SerialName(KEY_DEVICE_NAME)
    DEVICE_NAME(KEY_DEVICE_NAME, AuditValueSensitivity.FULL_VALUE_MASK),

    /**
     * From [ClientDeviceInfo.clientType]; [AuditEventMetadata.value] is a [ClientType] wire string such as [ClientType.CLIENT_WEB].
     */
    @SerialName(KEY_CLIENT_TYPE)
    CLIENT_TYPE(KEY_CLIENT_TYPE, AuditValueSensitivity.NON_SENSITIVE),

    /**
     * From [ClientDeviceInfo.language] (locale or language tag string).
     */
    @SerialName(KEY_LANGUAGE)
    LANGUAGE(KEY_LANGUAGE, AuditValueSensitivity.NON_SENSITIVE),

    /**
     * From [ClientDeviceInfo.appVersion].
     */
    @SerialName(KEY_APP_VERSION)
    APP_VERSION(KEY_APP_VERSION, AuditValueSensitivity.NON_SENSITIVE),

    /**
     * From [ClientDeviceInfo.operationSystemVersion].
     */
    @SerialName(KEY_OS_VERSION)
    OS_VERSION(KEY_OS_VERSION, AuditValueSensitivity.PARTIAL_VALUE_MASK),

    /**
     * From [ClientInfo.userAgent].
     */
    @SerialName(KEY_USER_AGENT)
    USER_AGENT(KEY_USER_AGENT, AuditValueSensitivity.PARTIAL_VALUE_MASK),

    /**
     * From [ClientInfo.ipAddress].
     */
    @SerialName(KEY_IP_ADDRESS)
    IP_ADDRESS(KEY_IP_ADDRESS, AuditValueSensitivity.IP_ADDRESS),

    /**
     * From [ClientInfo.host] (e.g. HTTP `Host`).
     */
    @SerialName(KEY_HOST)
    HOST(KEY_HOST, AuditValueSensitivity.NON_SENSITIVE),

    /**
     * From [ClientInfo.origin] (e.g. CORS `Origin`).
     */
    @SerialName(KEY_ORIGIN)
    ORIGIN(KEY_ORIGIN, AuditValueSensitivity.PARTIAL_VALUE_MASK),

    /**
     * From [ClientInfo.apiVersion].
     */
    @SerialName(KEY_API_VERSION)
    API_VERSION(KEY_API_VERSION, AuditValueSensitivity.NON_SENSITIVE),

    /**
     * End-to-end trace identifier (same role as a `request_id` / correlation id); [AuditEventMetadata.value] is an opaque string
     * from the tracing system.
     */
    @SerialName(KEY_TRACE_ID)
    TRACE_ID(KEY_TRACE_ID, AuditValueSensitivity.NON_SENSITIVE),

    /**
     * Why access was denied (rate limit, forbidden, etc.).
     */
    @SerialName(KEY_DENIED_REASON)
    DENIED_REASON(KEY_DENIED_REASON, AuditValueSensitivity.NON_SENSITIVE),

    /**
     * Stable error instance id; [AuditEventMetadata.value] matches [ApiErrorResponse.id] (opaque string, typically UUID hex-with-dashes).
     */
    @SerialName(KEY_ERROR_ID)
    ERROR_ID(KEY_ERROR_ID, AuditValueSensitivity.NON_SENSITIVE),

    /**
     * Machine-readable error code; [AuditEventMetadata.value] matches [ApiErrorResponse.code] (e.g. a `*ErrorCodes` constant string).
     */
    @SerialName(KEY_ERROR_CODE)
    ERROR_CODE(KEY_ERROR_CODE, AuditValueSensitivity.NON_SENSITIVE);

    override fun parseOrNull(value: String): AuditMetadataKey? = fromValueOrNull(value)

    override fun parseOrThrow(value: String): AuditMetadataKey = fromValueOrThrow(value)

    companion object {
        /**
         * Returns [CommonAuditMetadataKey] for a wire or enum-style string: first by case-insensitive enum constant name,
         * then by case-insensitive [value] match.
         */
        fun fromValueOrNull(value: String): AuditMetadataKey? =
            entries.firstOrNull {
                it.value.equals(value, ignoreCase = true) ||
                        it.name.equals(value, ignoreCase = true)
            }

        /**
         * Returns [CommonAuditMetadataKey] for the given wire or enum-style string.
         *
         * @throws IllegalArgumentException if [value] does not match any [CommonAuditMetadataKey].
         */
        fun fromValueOrThrow(value: String): AuditMetadataKey =
            fromValueOrNull(value) ?: throw IllegalArgumentException("Unknown value for CommonAuditMetadataKey: '$value'")
    }
}