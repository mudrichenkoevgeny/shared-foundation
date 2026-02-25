package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.security.useridentifiers

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.security.SecurityRoutes

/**
 * Object containing route definitions for managing user authentication identifiers.
 *
 * @property BASE_SECURITY_USER_IDENTIFIERS_ROUTE The base path for user identifier management.
 * @property GET_USER_IDENTIFIERS The endpoint to retrieve all identifiers linked to the current account.
 * @property DELETE_USER_IDENTIFIER The endpoint to remove a specific identifier by its unique identifier.
 * @property ADD_USER_IDENTIFIER_EMAIL The endpoint to link email identifier to an existing account.
 * @property ADD_USER_IDENTIFIER_PHONE The endpoint to link phone identifier to an existing account.
 * @property ADD_USER_IDENTIFIER_EXTERNAL_AUTH_PROVIDER The endpoint to link an external auth provider (e.g., Google) to the account.
 * @property BASE_SECURITY_USER_IDENTIFIERS_CONFIRMATION_ROUTE The base path for identifier verification operations.
 * @property SEND_ADD_EMAIL_IDENTIFIER_CONFIRMATION The endpoint to request a verification code for email identifier.
 * @property SEND_ADD_PHONE_IDENTIFIER_CONFIRMATION The endpoint to request a verification code for phone identifier.
 */
object SecurityUserIdentifiersRoutes {
    const val BASE_SECURITY_USER_IDENTIFIERS_ROUTE = "${SecurityRoutes.BASE_SECURITY_ROUTE}/user-identifier"
    const val GET_USER_IDENTIFIERS = BASE_SECURITY_USER_IDENTIFIERS_ROUTE
    const val DELETE_USER_IDENTIFIER = "$BASE_SECURITY_USER_IDENTIFIERS_ROUTE/{${UserApiPaths.USER_IDENTIFIER_ID}}"
    const val ADD_USER_IDENTIFIER_EMAIL = "$BASE_SECURITY_USER_IDENTIFIERS_ROUTE/email"
    const val ADD_USER_IDENTIFIER_PHONE = "$BASE_SECURITY_USER_IDENTIFIERS_ROUTE/phone"
    const val ADD_USER_IDENTIFIER_EXTERNAL_AUTH_PROVIDER = "$BASE_SECURITY_USER_IDENTIFIERS_ROUTE/external-auth-provider"
    const val BASE_SECURITY_USER_IDENTIFIERS_CONFIRMATION_ROUTE = "${SecurityRoutes.BASE_SECURITY_ROUTE}/confirmation"
    const val SEND_ADD_EMAIL_IDENTIFIER_CONFIRMATION = "$BASE_SECURITY_USER_IDENTIFIERS_CONFIRMATION_ROUTE/send-to-email"
    const val SEND_ADD_PHONE_IDENTIFIER_CONFIRMATION = "$BASE_SECURITY_USER_IDENTIFIERS_CONFIRMATION_ROUTE/send-to-phone"
}