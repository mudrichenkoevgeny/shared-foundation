package io.github.mudrichenkoevgeny.shared.foundation.feature.user.route.auth.login

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.route.auth.AuthRoutes

object LoginRoutes {
    const val BASE_LOGIN_ROUTE = "${AuthRoutes.BASE_AUTH_ROUTE}/login"
    const val LOGIN_BY_EMAIL = "$BASE_LOGIN_ROUTE/email"
    const val LOGIN_BY_PHONE = "$BASE_LOGIN_ROUTE/phone"
    const val LOGIN_BY_EXTERNAL_AUTH_PROVIDER = "$BASE_LOGIN_ROUTE/external-auth-provider"
    const val BASE_LOGIN_CONFIRMATION_ROUTE = "${LOGIN_BY_PHONE}/confirmation"
    const val SEND_LOGIN_CONFIRMATION_TO_PHONE = "$BASE_LOGIN_CONFIRMATION_ROUTE/send-to-phone"
}