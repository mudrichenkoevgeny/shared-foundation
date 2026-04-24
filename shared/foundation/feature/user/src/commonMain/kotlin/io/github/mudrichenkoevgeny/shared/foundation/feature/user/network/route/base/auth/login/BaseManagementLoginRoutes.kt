package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.auth.login

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.route.management.ManagementRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.auth.BaseAuthRoutes

/**
 * Shared base path segments for login endpoints in the management API.
 */
object BaseManagementLoginRoutes {
    /**
     * Base path for management login operations.
     */
    const val BASE_MANAGEMENT_LOGIN_ROUTE = "${ManagementRoutes.BASE_MANAGEMENT_ROUTE}${BaseAuthRoutes.BASE_AUTH_ROUTE}/login"

    /**
     * Login by email and password for management access.
     */
    const val LOGIN_BY_EMAIL = "$BASE_MANAGEMENT_LOGIN_ROUTE/email"

    /**
     * Finalize the management login process by providing a TOTP code.
     */
    const val LOGIN_BY_TOTP = "$BASE_MANAGEMENT_LOGIN_ROUTE/totp"

    /**
     * Finalize the login process by providing a backup recovery code.
     * Used as a fallback second factor when the TOTP device is unavailable.
     */
    const val LOGIN_BY_TOTP_RECOVERY_CODE = "$BASE_MANAGEMENT_LOGIN_ROUTE/totp-recovery-code"

}