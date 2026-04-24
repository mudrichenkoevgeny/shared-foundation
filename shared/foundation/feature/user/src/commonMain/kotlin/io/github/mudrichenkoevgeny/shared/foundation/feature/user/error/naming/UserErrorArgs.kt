package io.github.mudrichenkoevgeny.shared.foundation.feature.user.error.naming

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider

/**
 * User-specific keys for error arguments used in API responses and backend logging.
 */
object UserErrorArgs {
    /** * Key for the unique identifier of the user related to the error.
     * Mostly used as a **secret argument** for internal logging.
     *
     * Associated with [UserErrorCodes.USER_ROLE_NOT_ALLOWED], [UserErrorCodes.USER_MISSING_PERMISSIONS],
     * [UserErrorCodes.USER_ILLEGAL_ACCOUNT_STATUS], [UserErrorCodes.USER_BLOCKED],
     * [UserErrorCodes.USER_READ_ONLY], [UserErrorCodes.USER_SECURITY_HOLD],
     * [UserErrorCodes.USER_PENDING_DELETION], [UserErrorCodes.USER_FORBIDDEN], [UserErrorCodes.USER_NOT_FOUND].
     */
    const val USER_ID = "userId"

    /** * Key for the maximum number of identifiers allowed for the specific request context.
     * Used as a **public argument** for client-side localization.
     *
     * Associated with [UserErrorCodes.USER_IDENTIFIER_LIMIT_REACHED],
     * [UserErrorCodes.TOTAL_USER_IDENTIFIERS_LIMIT_REACHED].
     */
    const val MAX_NUMBER_OF_IDENTIFIERS = "maxNumberOfIdentifiers"

    /** * Key for the specific UserAuthProvider (see [UserAuthProvider]).
     * Used as a **public argument** for client-side localization.
     *
     * Associated with [UserErrorCodes.USER_IDENTIFIER_LIMIT_REACHED].
     */
    const val USER_AUTH_PROVIDER = "userAuthProvider"
}