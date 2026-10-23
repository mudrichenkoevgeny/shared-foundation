package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.resource

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.resource.AuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.settings.ManagementAuthSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.auth.resetpassword.SelfManagementResetPasswordRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.auth.settings.ManagementAuthSettingsRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.users.identifiers.ManagementIdentifiersRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.self.user.identifier.SelfManagementIdentifierRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.users.sessions.ManagementSessionsRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.self.user.session.SelfManagementSessionRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.users.ManagementUsersRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.login.OpenLoginRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.registration.OpenRegistrationRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.resetpassword.OpenResetPasswordRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.user.identifier.OpenIdentifierRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.user.session.OpenSessionRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.user.OpenUserRoutes
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val RESOURCE_AUTH_SETTINGS = "auth_settings"
private const val RESOURCE_USER = "user"
private const val RESOURCE_EMAIL_ADDRESS = "email_address"
private const val RESOURCE_PHONE_NUMBER = "phone_number"
private const val RESOURCE_IDENTIFIER = "identifier"
private const val RESOURCE_SESSION = "session"

/**
 * User-feature audit resource kinds (management API, open/self-service flows).
 *
 * @property value String representation of the [UserAuditResourceType].
 */
@Serializable
enum class UserAuditResourceType(override val value: String) : AuditResourceType {
    /**
     * [ManagementAuthSettingsRoutes.UPDATE_MANAGEMENT_AUTH_SETTINGS],
     * [ManagementAuthSettingsRoutes.RESET_MANAGEMENT_AUTH_SETTINGS]
     * request/response ([ManagementAuthSettingsPayload]).
     */
    @SerialName(RESOURCE_AUTH_SETTINGS)
    AUTH_SETTINGS(RESOURCE_AUTH_SETTINGS),

    /**
     * User account ([ManagementUsersRoutes], [OpenUserRoutes]).
     */
    @SerialName(RESOURCE_USER)
    USER(RESOURCE_USER),

    /**
     * Email address ([SelfManagementResetPasswordRoutes], [OpenRegistrationRoutes], [OpenResetPasswordRoutes], [OpenIdentifierRoutes]).
     */
    @SerialName(RESOURCE_EMAIL_ADDRESS)
    EMAIL_ADDRESS(RESOURCE_EMAIL_ADDRESS),

    /**
     * Phone number ([OpenLoginRoutes], [OpenIdentifierRoutes]).
     */
    @SerialName(RESOURCE_PHONE_NUMBER)
    PHONE_NUMBER(RESOURCE_PHONE_NUMBER),

    /**
     * User identifier credential record ([ManagementIdentifiersRoutes], [SelfManagementIdentifierRoutes], [OpenIdentifierRoutes]).
     */
    @SerialName(RESOURCE_IDENTIFIER)
    IDENTIFIER(RESOURCE_IDENTIFIER),

    /**
     * User session ([ManagementSessionsRoutes], [SelfManagementSessionRoutes], [OpenSessionRoutes]).
     */
    @SerialName(RESOURCE_SESSION)
    SESSION(RESOURCE_SESSION);

    companion object {
        /**
         * Returns [UserAuditResourceType] based on the provided string value, or null if the value is invalid.
         */
        fun fromValueOrNull(value: String): UserAuditResourceType? =
            entries.firstOrNull {
                it.value.equals(value, ignoreCase = true) ||
                    it.name.equals(value, ignoreCase = true)
            }

        /**
         * Returns [UserAuditResourceType] for the given wire or enum-style string (case-insensitive enum name).
         *
         * @throws IllegalArgumentException if [value] does not match any [UserAuditResourceType].
         */
        fun fromValueOrThrow(value: String): UserAuditResourceType =
            fromValueOrNull(value) ?: throw IllegalArgumentException("Unknown value for UserAuditResourceType: '$value'")
    }
}
