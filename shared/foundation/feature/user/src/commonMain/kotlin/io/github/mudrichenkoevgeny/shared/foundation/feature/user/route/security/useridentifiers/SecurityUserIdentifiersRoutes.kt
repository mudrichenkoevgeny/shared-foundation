package io.github.mudrichenkoevgeny.shared.foundation.feature.user.route.security.useridentifiers

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.route.security.SecurityRoutes

object SecurityUserIdentifiersRoutes {
    const val BASE_SECURITY_USER_IDENTIFIERS_ROUTE = "${SecurityRoutes.BASE_SECURITY_ROUTE}/user-identifier"
    const val GET_USER_IDENTIFIERS_ROUTE = BASE_SECURITY_USER_IDENTIFIERS_ROUTE
    const val DELETE_USER_IDENTIFIER = "$BASE_SECURITY_USER_IDENTIFIERS_ROUTE/{id}"
    const val ADD_USER_IDENTIFIER_EMAIL = "$BASE_SECURITY_USER_IDENTIFIERS_ROUTE/email/{id}"
    const val ADD_USER_IDENTIFIER_PHONE = "$BASE_SECURITY_USER_IDENTIFIERS_ROUTE/phone/{id}"
    const val ADD_USER_IDENTIFIER_EXTERNAL_AUTH_PROVIDER = "$BASE_SECURITY_USER_IDENTIFIERS_ROUTE/external-auth-provider/{id}"
    const val BASE_SECURITY_USER_IDENTIFIERS_CONFIRMATION_ROUTE = "${SecurityRoutes.BASE_SECURITY_ROUTE}/confirmation"
    const val SEND_ADD_EMAIL_IDENTIFIER_CONFIRMATION = "$BASE_SECURITY_USER_IDENTIFIERS_CONFIRMATION_ROUTE/send-to-email"
    const val SEND_ADD_PHONE_IDENTIFIER_CONFIRMATION = "$BASE_SECURITY_USER_IDENTIFIERS_CONFIRMATION_ROUTE/send-to-phone"
}