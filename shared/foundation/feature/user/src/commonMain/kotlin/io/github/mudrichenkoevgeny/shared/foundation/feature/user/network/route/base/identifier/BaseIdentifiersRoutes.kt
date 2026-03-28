package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.identifier

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths

/**
 * Shared base path segments for user identifier operations .
 */
object BaseIdentifiersRoutes {
    /**
     * Base path for managing authentication identifiers linked to the current account.
     */
    const val BASE_IDENTIFIER_ROUTE = "/identifier"

    /**
     * Path for listing identifiers linked to the current account.
     */
    const val GET_IDENTIFIERS = BASE_IDENTIFIER_ROUTE

    /**
     * Path template for removing an identifier; includes the [UserApiPaths.USER_IDENTIFIER_ID] path parameter.
     */
    const val DELETE_IDENTIFIER = "$BASE_IDENTIFIER_ROUTE/{${UserApiPaths.USER_IDENTIFIER_ID}}"

    /**
     * Path for linking an email identifier to the account.
     */
    const val ADD_IDENTIFIER_EMAIL = "$BASE_IDENTIFIER_ROUTE/email"

    /**
     * Path for linking a phone identifier to the account.
     */
    const val ADD_IDENTIFIER_PHONE = "$BASE_IDENTIFIER_ROUTE/phone"

    /**
     * Path for linking an external auth provider to the account.
     */
    const val ADD_IDENTIFIER_EXTERNAL_AUTH_PROVIDER = "$BASE_IDENTIFIER_ROUTE/external-auth-provider"

    /**
     * Base path for identifier verification (confirmation codes).
     */
    const val BASE_IDENTIFIER_CONFIRMATION_ROUTE = "$BASE_IDENTIFIER_ROUTE/confirmation"

    /**
     * Path for requesting a confirmation code to add an email identifier.
     */
    const val SEND_ADD_EMAIL_IDENTIFIER_CONFIRMATION = "$BASE_IDENTIFIER_CONFIRMATION_ROUTE/send-to-email"

    /**
     * Path for requesting a confirmation code to add a phone identifier.
     */
    const val SEND_ADD_PHONE_IDENTIFIER_CONFIRMATION = "$BASE_IDENTIFIER_CONFIRMATION_ROUTE/send-to-phone"

    /**
     * Path for changing the password for the **email** login identifier on the current account.
     */
    const val IDENTIFIER_EMAIL_CHANGE_PASSWORD = "$BASE_IDENTIFIER_ROUTE/email/password"
}