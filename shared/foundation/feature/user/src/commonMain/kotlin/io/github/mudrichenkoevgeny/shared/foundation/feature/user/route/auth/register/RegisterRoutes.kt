package io.github.mudrichenkoevgeny.shared.foundation.feature.user.route.auth.register

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.route.auth.AuthRoutes

object RegisterRoutes {
    const val BASE_REGISTER_ROUTE = "${AuthRoutes.BASE_AUTH_ROUTE}/register"
    const val REGISTER_BY_EMAIL = "$BASE_REGISTER_ROUTE/email"
    const val BASE_REGISTER_CONFIRMATION_ROUTE = "${REGISTER_BY_EMAIL}/confirmation"
    const val SEND_REGISTER_CONFIRMATION_TO_EMAIL = "$BASE_REGISTER_CONFIRMATION_ROUTE/send-to-email"
}