package io.github.mudrichenkoevgeny.shared.foundation.feature.user.route.security.password

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.route.security.SecurityRoutes

/**
 * Object containing route definitions for password management.
 *
 * @property BASE_PASSWORD_ROUTE The base path for password-related security operations.
 * @property PASSWORD_CHANGE The endpoint to update the account password using current credentials.
 */
object PasswordRoutes {
    const val BASE_PASSWORD_ROUTE = "${SecurityRoutes.BASE_SECURITY_ROUTE}/password"
    const val PASSWORD_CHANGE = "$BASE_PASSWORD_ROUTE/change"
}