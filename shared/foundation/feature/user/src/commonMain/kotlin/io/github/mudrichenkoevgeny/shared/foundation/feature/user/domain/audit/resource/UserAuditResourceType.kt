package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.resource

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.resource.AuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.settings.ManagementAuthSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.auth.resetpassword.ManagementResetPasswordRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.auth.settings.ManagementAuthSettingsRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.identifier.ManagementIdentifierRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.identifier.SelfManagementIdentifierRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.session.ManagementSessionRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.session.SelfManagementSessionRoutes
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
     * Email address ([ManagementResetPasswordRoutes], [OpenRegisterRoutes], [OpenResetPasswordRoutes], [OpenIdentifierRoutes]).
     */
    EMAIL_ADDRESS,

    /**
     * Phone number ([OpenLoginRoutes], [OpenIdentifierRoutes]).
     */
    PHONE_NUMBER,

    /**
     * User identifier credential record ([ManagementIdentifierRoutes], [SelfManagementIdentifierRoutes], [OpenIdentifierRoutes]).
     */
    IDENTIFIER,

    /**
     * User session ([ManagementSessionRoutes], [SelfManagementSessionRoutes], [OpenSessionRoutes]).
     */
    SESSION;

    /**
     * String representation of the [UserAuditResourceType].
     */
    override val serialName: String
        get() = when (this) {
            AUTH_SETTINGS -> RESOURCE_AUTH_SETTINGS
            USER -> RESOURCE_USER
            EMAIL_ADDRESS -> RESOURCE_EMAIL_ADDRESS
            PHONE_NUMBER -> RESOURCE_PHONE_NUMBER
            IDENTIFIER -> RESOURCE_IDENTIFIER
            SESSION -> RESOURCE_SESSION
        }

    override fun parseOrNull(value: String): AuditResourceType? = fromValueOrNull(value)

    override fun parseOrThrow(value: String): AuditResourceType = fromValueOrThrow(value)

    companion object {
        private const val RESOURCE_AUTH_SETTINGS = "auth_settings"
        private const val RESOURCE_USER = "user"
        private const val RESOURCE_EMAIL_ADDRESS = "email_address"
        private const val RESOURCE_PHONE_NUMBER = "phone_number"
        private const val RESOURCE_IDENTIFIER = "identifier"
        private const val RESOURCE_SESSION = "session"

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
