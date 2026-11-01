package io.github.mudrichenkoevgeny.shared.foundation.feature.user.error.naming

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.accountlockout.AccountLockoutType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider

/**
 * User-specific keys for error arguments used in API responses and backend logging.
 */
object UserErrorArgs {
    /**
     * Key for the unique identifier of the user related to the error.
     * Mostly used as a **secret argument** for internal logging.
     *
     * Associated with [UserErrorCodes.USER_ROLE_NOT_ALLOWED], [UserErrorCodes.USER_MISSING_PERMISSIONS],
     * [UserErrorCodes.USER_ILLEGAL_ACCOUNT_STATUS], [UserErrorCodes.USER_BANNED], [UserErrorCodes.USER_LOCKED],
     * [UserErrorCodes.USER_READ_ONLY], [UserErrorCodes.USER_SECURITY_HOLD],
     * [UserErrorCodes.USER_PENDING_DELETION], [UserErrorCodes.USER_FORBIDDEN], [UserErrorCodes.USER_NOT_FOUND].
     */
    const val USER_ID = "userId"

    /**
     * Key for the maximum number of identifiers allowed for the specific request context.
     * Used as a **public argument** for client-side localization.
     *
     * Associated with [UserErrorCodes.USER_IDENTIFIER_LIMIT_REACHED],
     * [UserErrorCodes.TOTAL_USER_IDENTIFIERS_LIMIT_REACHED].
     */
    const val MAX_NUMBER_OF_IDENTIFIERS = "maxNumberOfIdentifiers"

    /**
     * Key for the specific UserAuthProvider (see [UserAuthProvider]).
     * Used as a **public argument** for client-side localization.
     *
     * Associated with [UserErrorCodes.USER_IDENTIFIER_LIMIT_REACHED].
     */
    const val USER_AUTH_PROVIDER = "userAuthProvider"

    /**
     * Key for the lockout category ([AccountLockoutType]: `NONE`, `INDEFINITE`, `TEMPORARY`).
     * Used as a **public argument** for client-side localization and behavior handling.
     *
     * Associated with [UserErrorCodes.USER_LOCKED].
     */
    const val ACCOUNT_LOCKOUT_TYPE = "accountLockoutType"

    /**
     * Key for the temporary account lockout expiration timestamp in epoch milliseconds.
     * Used as a **public argument** for client-side localization and countdown timers.
     *
     * Associated with [UserErrorCodes.USER_LOCKED].
     * Present only during temporary lockouts with the expiration timestamp; absent for indefinite lockouts.
     */
    const val TEMPORARY_LOCKOUT_UNTIL = "temporaryLockoutUntil"

    /**
     * Key for user nickname string.
     * Associated with [UserErrorCodes.NICKNAME_ALREADY_TAKEN].
     */
    const val NICKNAME = "nickname"

    /**
     * Key for minimum allowed nickname character length.
     * Associated with [UserErrorCodes.NICKNAME_TOO_SHORT].
     */
    const val NICKNAME_MIN_LENGTH = "nicknameMinLength"

    /**
     * Key for maximum allowed nickname character length.
     * Associated with [UserErrorCodes.NICKNAME_TOO_LONG].
     */
    const val NICKNAME_MAX_LENGTH = "nicknameMaxLength"

    /**
     * Key for user display name string.
     */
    const val DISPLAY_NAME = "displayName"

    /**
     * Key for minimum allowed display name character length.
     * Associated with [UserErrorCodes.DISPLAY_NAME_TOO_SHORT].
     */
    const val DISPLAY_NAME_MIN_LENGTH = "displayNameMinLength"

    /**
     * Key for maximum allowed display name character length.
     * Associated with [UserErrorCodes.DISPLAY_NAME_TOO_LONG].
     */
    const val DISPLAY_NAME_MAX_LENGTH = "displayNameMaxLength"
}
