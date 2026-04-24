package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.identifier

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.route.management.ManagementRoutes

/**
 * Shared base path segments for the current authenticated principal's identifiers in the management API.
 */
object BaseSelfManagementIdentifierRoutes {
    /**
     * Base path for self-service identifier operations in management API.
     */
    const val BASE_SELF_IDENTIFIER_ROUTE = "${ManagementRoutes.BASE_MANAGEMENT_ROUTE}/self/identifier"

    /**
     * Path for listing identifiers linked to the current management account.
     */
    const val GET_IDENTIFIERS = BASE_SELF_IDENTIFIER_ROUTE

    /**
     * Path for changing the password for the email login identifier on the current management account.
     */
    const val IDENTIFIER_EMAIL_CHANGE_PASSWORD = "$BASE_SELF_IDENTIFIER_ROUTE/email/password"
}