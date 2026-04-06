package io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditValueSensitivity
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientDeviceId
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientDeviceInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientType
import io.github.mudrichenkoevgeny.shared.foundation.core.common.error.model.ApiErrorResponse

/**
 * Cross-cutting audit metadata keys for [AuditEventMetadata].
 *
 * The first keys follow the field order of [ClientDeviceInfo] then [ClientInfo] (`deviceId` … `apiVersion`), then diagnostic keys,
 * then [DENIED_REASON], [ERROR_ID], and [ERROR_CODE] last.
 */
enum class CommonAuditMetadataKey : AuditMetadataKey {
    /**
     * From [ClientDeviceInfo.deviceId]; [AuditEventMetadata.value] aligns with [ClientDeviceId] wire form.
     */
    DEVICE_ID {
        override val valueSensitivity: AuditValueSensitivity = AuditValueSensitivity.FULL_VALUE_MASK
    },

    /**
     * From [ClientDeviceInfo.deviceName] (e.g. platform-reported device label).
     */
    DEVICE_NAME {
        override val valueSensitivity: AuditValueSensitivity = AuditValueSensitivity.FULL_VALUE_MASK
    },

    /**
     * From [ClientDeviceInfo.clientType]; [AuditEventMetadata.value] is a [ClientType] wire string such as [ClientType.CLIENT_WEB].
     */
    CLIENT_TYPE {
        override val valueSensitivity: AuditValueSensitivity = AuditValueSensitivity.NON_SENSITIVE
    },

    /**
     * From [ClientDeviceInfo.language] (locale or language tag string).
     */
    LANGUAGE {
        override val valueSensitivity: AuditValueSensitivity = AuditValueSensitivity.NON_SENSITIVE
    },

    /**
     * From [ClientDeviceInfo.appVersion].
     */
    APP_VERSION {
        override val valueSensitivity: AuditValueSensitivity = AuditValueSensitivity.NON_SENSITIVE
    },

    /**
     * From [ClientDeviceInfo.operationSystemVersion].
     */
    OS_VERSION {
        override val valueSensitivity: AuditValueSensitivity = AuditValueSensitivity.PARTIAL_VALUE_MASK
    },

    /**
     * From [ClientInfo.userAgent].
     */
    USER_AGENT {
        override val valueSensitivity: AuditValueSensitivity = AuditValueSensitivity.PARTIAL_VALUE_MASK
    },

    /**
     * From [ClientInfo.ipAddress].
     */
    IP_ADDRESS {
        override val valueSensitivity: AuditValueSensitivity = AuditValueSensitivity.IP_ADDRESS
    },

    /**
     * From [ClientInfo.host] (e.g. HTTP `Host`).
     */
    HOST {
        override val valueSensitivity: AuditValueSensitivity = AuditValueSensitivity.NON_SENSITIVE
    },

    /**
     * From [ClientInfo.origin] (e.g. CORS `Origin`).
     */
    ORIGIN {
        override val valueSensitivity: AuditValueSensitivity = AuditValueSensitivity.PARTIAL_VALUE_MASK
    },

    /**
     * From [ClientInfo.apiVersion].
     */
    API_VERSION {
        override val valueSensitivity: AuditValueSensitivity = AuditValueSensitivity.NON_SENSITIVE
    },

    /**
     * End-to-end trace identifier (same role as a `request_id` / correlation id); [AuditEventMetadata.value] is an opaque string
     * from the tracing system.
     */
    TRACE_ID {
        override val valueSensitivity: AuditValueSensitivity = AuditValueSensitivity.NON_SENSITIVE
    },

    /**
     * Why access was denied (rate limit, forbidden, etc.).
     */
    DENIED_REASON {
        override val valueSensitivity: AuditValueSensitivity = AuditValueSensitivity.NON_SENSITIVE
    },

    /**
     * Stable error instance id; [AuditEventMetadata.value] matches [ApiErrorResponse.id] (opaque string, typically UUID hex-with-dashes).
     */
    ERROR_ID {
        override val valueSensitivity: AuditValueSensitivity = AuditValueSensitivity.NON_SENSITIVE
    },

    /**
     * Machine-readable error code; [AuditEventMetadata.value] matches [ApiErrorResponse.code] (e.g. a `*ErrorCodes` constant string).
     */
    ERROR_CODE {
        override val valueSensitivity: AuditValueSensitivity = AuditValueSensitivity.NON_SENSITIVE
    };

    override val serialName: String
        get() = when (this) {
            DEVICE_ID -> KEY_DEVICE_ID
            DEVICE_NAME -> KEY_DEVICE_NAME
            CLIENT_TYPE -> KEY_CLIENT_TYPE
            LANGUAGE -> KEY_LANGUAGE
            APP_VERSION -> KEY_APP_VERSION
            OS_VERSION -> KEY_OS_VERSION
            USER_AGENT -> KEY_USER_AGENT
            IP_ADDRESS -> KEY_IP_ADDRESS
            HOST -> KEY_HOST
            ORIGIN -> KEY_ORIGIN
            API_VERSION -> KEY_API_VERSION
            TRACE_ID -> KEY_TRACE_ID
            DENIED_REASON -> KEY_DENIED_REASON
            ERROR_ID -> KEY_ERROR_ID
            ERROR_CODE -> KEY_ERROR_CODE
        }

    override fun parseOrNull(value: String): AuditMetadataKey? = fromValueOrNull(value)

    override fun parseOrThrow(value: String): AuditMetadataKey = fromValueOrThrow(value)

    companion object {
        const val KEY_DEVICE_ID = "device_id"
        const val KEY_DEVICE_NAME = "device_name"
        const val KEY_CLIENT_TYPE = "client_type"
        const val KEY_LANGUAGE = "language"
        const val KEY_APP_VERSION = "app_version"
        const val KEY_OS_VERSION = "os_version"
        const val KEY_USER_AGENT = "user_agent"
        const val KEY_IP_ADDRESS = "key_ip_address"
        const val KEY_HOST = "host"
        const val KEY_ORIGIN = "origin"
        const val KEY_API_VERSION = "api_version"
        const val KEY_TRACE_ID = "trace_id"
        const val KEY_DENIED_REASON = "denied_reason"
        const val KEY_ERROR_ID = "error_id"
        const val KEY_ERROR_CODE = "error_code"

        /**
         * Returns [CommonAuditMetadataKey] for a wire or enum-style string: first by case-insensitive enum constant name,
         * then by case-insensitive [serialName] match.
         */
        fun fromValueOrNull(value: String): AuditMetadataKey? {
            val byConstantName = runCatching { valueOf(value.uppercase()) }.getOrNull()
            if (byConstantName != null) {
                return byConstantName
            }

            return entries.firstOrNull { entry ->
                entry.serialName.equals(value, ignoreCase = true)
            }
        }

        /**
         * Returns [CommonAuditMetadataKey] for the given wire or enum-style string.
         *
         * @throws IllegalStateException if [value] does not match any [CommonAuditMetadataKey].
         */
        fun fromValueOrThrow(value: String): AuditMetadataKey =
            fromValueOrNull(value) ?: error("Unknown value of CommonAuditMetadataKey: '$value'")
    }
}
