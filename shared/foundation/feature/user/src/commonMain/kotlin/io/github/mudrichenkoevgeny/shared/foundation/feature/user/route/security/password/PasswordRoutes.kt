package io.github.mudrichenkoevgeny.shared.foundation.feature.user.route.security.password

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.route.security.SecurityRoutes

object PasswordRoutes {
    const val BASE_PASSWORD_ROUTE = "${SecurityRoutes.BASE_SECURITY_ROUTE}/password"
    const val PASSWORD_CHANGE = "$BASE_PASSWORD_ROUTE/change"
}