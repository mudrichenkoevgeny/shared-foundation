package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.resource

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.resource.AuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.settings.ManagementAuthSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.auth.settings.ManagementAuthSettingsRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.identifier.ManagementIdentifierRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.session.ManagementSessionRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.user.ManagementUserRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.login.OpenLoginRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.register.OpenRegisterRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.resetpassword.OpenResetPasswordRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.identifier.OpenIdentifierRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.session.OpenSessionRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.user.OpenUserRoutes

/**
 * User-feature audit resource kinds (management API, open/self-service flows).
 */
enum class UserAuditResourceType : AuditResourceType {
    /**
     * Authentication settings ([ManagementAuthSettingsPayload], [ManagementAuthSettingsRoutes]).
     */
    AUTH_SETTINGS,

    /**
     * User account ([ManagementUserRoutes], [OpenUserRoutes]).
     */
    USER,

    /**
     * Email channel for OTP/confirmations ([OpenRegisterRoutes], [OpenResetPasswordRoutes], [OpenIdentifierRoutes] send-email flows).
     */
    USER_EMAIL,

    /**
     * Phone channel for OTP/confirmations ([OpenLoginRoutes], [OpenIdentifierRoutes]).
     */
    USER_PHONE,

    /**
     * User identifier credential record ([ManagementIdentifierRoutes], [OpenIdentifierRoutes]).
     */
    USER_IDENTIFIER,

    /**
     * User session ([ManagementSessionRoutes], [OpenSessionRoutes]).
     */
    USER_SESSION;

    /**
     * String representation of the [UserAuditResourceType].
     */
    override val serialName: String
        get() = when (this) {
            AUTH_SETTINGS -> RESOURCE_AUTH_SETTINGS
            USER -> RESOURCE_USER
            USER_EMAIL -> RESOURCE_USER_EMAIL
            USER_PHONE -> RESOURCE_USER_PHONE
            USER_IDENTIFIER -> RESOURCE_USER_IDENTIFIER
            USER_SESSION -> RESOURCE_USER_SESSION
        }

    override fun parseOrNull(value: String): AuditResourceType? = fromValueOrNull(value)

    override fun parseOrThrow(value: String): AuditResourceType = fromValueOrThrow(value)

    companion object {
        const val RESOURCE_AUTH_SETTINGS = "auth_settings"
        const val RESOURCE_USER = "user"
        const val RESOURCE_USER_EMAIL = "user_email"
        const val RESOURCE_USER_PHONE = "user_phone"
        const val RESOURCE_USER_IDENTIFIER = "user_identifier"
        const val RESOURCE_USER_SESSION = "user_session"

        /**
         * Returns [UserAuditResourceType] based on the provided string value, or null if the value is invalid.
         */
        fun fromValueOrNull(value: String): UserAuditResourceType? =
            runCatching { valueOf(value.uppercase()) }.getOrNull()

        /**
         * Returns [UserAuditResourceType] for the given wire or enum-style string (case-insensitive enum name).
         *
         * @throws IllegalArgumentException if [value] does not match any [UserAuditResourceType].
         */
        fun fromValueOrThrow(value: String): UserAuditResourceType = valueOf(value.uppercase())
    }
}
