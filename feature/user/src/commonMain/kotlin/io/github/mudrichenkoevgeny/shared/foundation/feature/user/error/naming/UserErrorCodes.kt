package io.github.mudrichenkoevgeny.shared.foundation.feature.user.error.naming

import io.github.mudrichenkoevgeny.shared.foundation.core.common.error.naming.CommonErrorArgs
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.accountlockout.AccountLockoutType

/**
 * User-specific error codes for API responses and backend logging, used to categorize failures.
 */
public object UserErrorCodes {
    // --- Token Errors ---

    /**
     * The provided access token is malformed, signature is invalid, or it does not exist.
     *
     * **Localization keys:**
     * - `invalid_session`
     */
    public const val INVALID_ACCESS_TOKEN: String = "INVALID_ACCESS_TOKEN"

    /**
     * The access token has expired. Refresh is required.
     *
     * **Localization keys:**
     * - `access_token_expired`
     */
    public const val ACCESS_TOKEN_EXPIRED: String = "ACCESS_TOKEN_EXPIRED"

    /**
     * The refresh token is invalid, has been revoked, or does not match the current session.
     *
     * **Localization keys:**
     * - `invalid_session`
     */
    public const val INVALID_REFRESH_TOKEN: String = "INVALID_REFRESH_TOKEN"

    // --- User Session & Status ---

    /**
     * The session identifier is invalid or the session has been terminated.
     *
     * **Localization keys:**
     * - `invalid_session`
     */
    public const val INVALID_SESSION: String = "INVALID_SESSION"

    /**
     * The user's role does not have permission to access the requested resource.
     *
     * **Localization keys:**
     * - `missing_permissions`
     *
     * **Secret arguments:**
     * - [UserErrorArgs.USER_ID]: Identifier of the user who lacks the role.
     */
    public const val USER_ROLE_NOT_ALLOWED: String = "USER_ROLE_NOT_ALLOWED"

    /**
     * The user lacks specific fine-grained permissions required for the operation.
     *
     * **Localization keys:**
     * - `missing_permissions`
     *
     * **Secret arguments:**
     * - [UserErrorArgs.USER_ID]: Identifier of the user missing permissions.
     */
    public const val USER_MISSING_PERMISSIONS: String = "USER_MISSING_PERMISSIONS"

    /**
     * The operation is denied because the user's authority level is lower than required for the target resource or action.
     *
     * **Localization keys:**
     * - `missing_permissions`
     *
     * **Secret arguments:**
     * - [UserErrorArgs.USER_ID]: Identifier of the user with insufficient authority.
     */
    public const val USER_INSUFFICIENT_AUTHORITY_LEVEL: String = "USER_INSUFFICIENT_AUTHORITY_LEVEL"

    /**
     * The account is in a state that prevents the requested action.
     *
     * **Localization keys:**
     * - `forbidden`
     *
     * **Secret arguments:**
     * - [UserErrorArgs.USER_ID]: Identifier of the user with illegal status.
     */
    public const val USER_ILLEGAL_ACCOUNT_STATUS: String = "USER_ILLEGAL_ACCOUNT_STATUS"

    /**
     * The user account has been explicitly banned by an administrator.
     *
     * **Localization keys:**
     * - `banned`
     *
     * **Secret arguments:**
     * - [UserErrorArgs.USER_ID]: Identifier of the banned user.
     */
    public const val USER_BANNED: String = "USER_BANNED"

    /**
     * The account is temporarily or indefinitely locked out due to exceeding maximum allowed failed authentication attempts or security policy violations.
     *
     * **Localization keys:**
     * - `locked_without_args`: Static fallback message.
     * - `locked_with_args`: Parameterized message template (`{formattedUntil}`).
     *
     * **Formatters & Resolvers:**
     * - Use `UserModuleStrings.resolveUserLockedMessage` to resolve the contextual lockout error message.
     *
     * **Display arguments (Localization placeholders):**
     * - [UserErrorArgs.TEMPORARY_LOCKOUT_UNTIL]: Temporary lockout expiration timestamp in epoch milliseconds or formatted date string (`{formattedUntil}`).
     *
     * **Public arguments (Programmatic API payload):**
     * - [UserErrorArgs.ACCOUNT_LOCKOUT_TYPE]: Category of account lockout ([AccountLockoutType]: `NONE`, `INDEFINITE`, `TEMPORARY`).
     * - [UserErrorArgs.TEMPORARY_LOCKOUT_UNTIL]: Temporary lockout expiration timestamp in epoch milliseconds (absent for indefinite lockouts).
     *
     * **Secret arguments:**
     * - [UserErrorArgs.USER_ID]: Identifier of the locked user.
     */
    public const val USER_LOCKED: String = "USER_LOCKED"

    /**
     * The account is in read-only mode.
     *
     * **Localization keys:**
     * - `read_only`
     *
     * **Secret arguments:**
     * - [UserErrorArgs.USER_ID]: Identifier of the affected user.
     */
    public const val USER_READ_ONLY: String = "USER_READ_ONLY"

    /**
     * Access is temporarily restricted due to suspicious activity or security concerns.
     *
     * **Localization keys:**
     * - `security_hold`
     *
     * **Secret arguments:**
     * - [UserErrorArgs.USER_ID]: Identifier of the restricted user.
     */
    public const val USER_SECURITY_HOLD: String = "USER_SECURITY_HOLD"

    /**
     * The account is scheduled for deletion and cannot perform this action.
     *
     * **Localization keys:**
     * - `pending_deletion`
     */
    public const val USER_PENDING_DELETION: String = "USER_PENDING_DELETION"

    /**
     * Access to the resource is forbidden for this user, regardless of authentication.
     *
     * **Localization keys:**
     * - `forbidden`
     *
     * **Secret arguments:**
     * - [UserErrorArgs.USER_ID]: Identifier of the forbidden user.
     */
    public const val USER_FORBIDDEN: String = "USER_FORBIDDEN"

    /**
     * No user account was found matching the provided identifier.
     *
     * **Localization keys:**
     * - `not_found`
     *
     * **Secret arguments:**
     * - [UserErrorArgs.USER_ID]: The identifier value used for lookup.
     */
    public const val USER_NOT_FOUND: String = "USER_NOT_FOUND"

    // --- Identifiers & Credentials ---

    /**
     * Authentication failed due to incorrect username, email, or password.
     *
     * **Localization keys:**
     * - `invalid_credentials`
     */
    public const val INVALID_CREDENTIALS: String = "INVALID_CREDENTIALS"

    /**
     * The specified identifier cannot be removed from the account.
     *
     * **Localization keys:**
     * - `can_not_delete_identifier`
     */
    public const val CAN_NOT_DELETE_USER_IDENTIFIER: String = "CAN_NOT_DELETE_USER_IDENTIFIER"

    /**
     * Failed to link a new identifier to the account.
     *
     * **Localization keys:**
     * - `can_not_create_identifier`
     */
    public const val CAN_NOT_CREATE_USER_IDENTIFIER: String = "CAN_NOT_CREATE_USER_IDENTIFIER"

    /**
     * The maximum number of identifiers for a specific authentication provider category (`email`, `phone`, or `external_provider`) has been reached.
     *
     * **Localization keys:**
     * - `identifier_limit_reached_without_args`: Static fallback message.
     * - `identifier_limit_reached_with_args`: Parameterized message template (`{limit}`, `{provider}`).
     *
     * **Formatters & Resolvers:**
     * - Use `UserModuleStrings.resolveUserIdentifierLimitReachedMessage` to map provider category and resolve the limit error message.
     *
     * **Display arguments (Localization placeholders):**
     * - `limit`: Maximum allowed count (`{limit}`, derived from [UserErrorArgs.MAX_NUMBER_OF_IDENTIFIERS]).
     * - `provider`: Localized sign-in method category label (`{provider}`, resolved from `general.json` keys: `email`, `phone`, or `external_provider`).
     *
     * **Public arguments (Programmatic API payload):**
     * - [UserErrorArgs.MAX_NUMBER_OF_IDENTIFIERS]: The limit allowed for the sign-in method.
     * - [UserErrorArgs.USER_AUTH_PROVIDER]: Localization key of the sign-in method category (`email`, `phone`, or `external_provider`).
     */
    public const val USER_IDENTIFIER_LIMIT_REACHED: String = "USER_IDENTIFIER_LIMIT_REACHED"

    /**
     * The total number of allowed identifiers for the account has been reached.
     *
     * **Localization keys:**
     * - `total_identifiers_limit_reached_without_args`: Static fallback message.
     * - `total_identifiers_limit_reached_with_args`: Parameterized message template (`{limit}`).
     *
     * **Formatters & Resolvers:**
     * - Use `UserModuleStrings.resolveTotalUserIdentifiersLimitReachedMessage` to resolve the total identifier limit error message.
     *
     * **Display arguments (Localization placeholders):**
     * - [UserErrorArgs.MAX_NUMBER_OF_IDENTIFIERS]: The total overall limit (`{limit}`).
     *
     * **Public arguments (Programmatic API payload):**
     * - [UserErrorArgs.MAX_NUMBER_OF_IDENTIFIERS]: The total overall limit.
     */
    public const val TOTAL_USER_IDENTIFIERS_LIMIT_REACHED: String = "TOTAL_USER_IDENTIFIERS_LIMIT_REACHED"

    // --- Internal Identifiers ---

    /**
     * The password provided for confirmation does not match the current password.
     *
     * **Localization keys:**
     * - `wrong_password`
     */
    public const val WRONG_PASSWORD: String = "WRONG_PASSWORD"

    /**
     * The verification code is incorrect or has expired.
     *
     * **Localization keys:**
     * - `wrong_confirmation_code`
     */
    public const val WRONG_CONFIRMATION_CODE: String = "WRONG_CONFIRMATION_CODE"

    // --- External Identifiers ---

    /**
     * Linking with an external provider (OAuth/OpenID) failed.
     *
     * **Localization keys:**
     * - `external_linkage_failed`
     *
     * **Secret arguments:**
     * - [CommonErrorArgs.MESSAGE]: Cause of the linkage failure.
     */
    public const val EXTERNAL_IDENTIFIER_LINKAGE_FAILED: String = "EXTERNAL_IDENTIFIER_LINKAGE_FAILED"

    /**
     * Registration is currently disabled by system configuration.
     *
     * **Localization keys:**
     * - `registration_disabled`
     */
    public const val REGISTRATION_DISABLED: String = "REGISTRATION_DISABLED"

    /**
     * Self-service unlock is currently disabled by security policy.
     *
     * **Localization keys:**
     * - `self_service_unlock_disabled`
     */
    public const val SELF_SERVICE_UNLOCK_DISABLED: String = "SELF_SERVICE_UNLOCK_DISABLED"

    /**
     * The registration, login, or email linkage attempt was rejected because the email address or domain is not permitted by email restriction policy.
     *
     * **Localization keys:**
     * - `email_not_allowed`
     */
    public const val EMAIL_NOT_ALLOWED: String = "EMAIL_NOT_ALLOWED"

    /**
     * Authentication or operation failed because the account has no password set or initial password setup is required.
     *
     * **Localization keys:**
     * - `password_setup_required`
     */
    public const val PASSWORD_SETUP_REQUIRED: String = "PASSWORD_SETUP_REQUIRED"

    /**
     * Operation failed because the specified identifier type does not support password credentials.
     *
     * **Localization keys:**
     * - `identifier_password_not_supported`
     */
    public const val USER_IDENTIFIER_PASSWORD_NOT_SUPPORTED: String = "USER_IDENTIFIER_PASSWORD_NOT_SUPPORTED"

    /**
     * Operation failed because no password credential is set on the specified identifier record.
     *
     * **Localization keys:**
     * - `identifier_password_not_set`
     */
    public const val USER_IDENTIFIER_PASSWORD_NOT_SET: String = "USER_IDENTIFIER_PASSWORD_NOT_SET"

    // --- User Details & Nickname Errors ---

    /**
     * The requested nickname is already taken by another user.
     *
     * **Localization keys:**
     * - `nickname_already_taken`
     *
     * **Public arguments:**
     * - [UserErrorArgs.NICKNAME]: The requested nickname string.
     */
    public const val NICKNAME_ALREADY_TAKEN: String = "NICKNAME_ALREADY_TAKEN"

    /**
     * Modifying the nickname is disabled by system configuration.
     *
     * **Localization keys:**
     * - `nickname_change_disabled`
     */
    public const val NICKNAME_CHANGE_DISABLED: String = "NICKNAME_CHANGE_DISABLED"

    /**
     * The provided nickname character length is below the required minimum.
     *
     * **Localization keys:**
     * - `nickname_too_short`
     *
     * **Public arguments:**
     * - [UserErrorArgs.NICKNAME_MIN_LENGTH]: Minimum permitted character length (`{minLength}`).
     */
    public const val NICKNAME_TOO_SHORT: String = "NICKNAME_TOO_SHORT"

    /**
     * The provided nickname character length exceeds the allowed maximum.
     *
     * **Localization keys:**
     * - `nickname_too_long`
     *
     * **Public arguments:**
     * - [UserErrorArgs.NICKNAME_MAX_LENGTH]: Maximum permitted character length (`{maxLength}`).
     */
    public const val NICKNAME_TOO_LONG: String = "NICKNAME_TOO_LONG"

    /**
     * The provided nickname contains unsupported or illegal characters.
     *
     * **Localization keys:**
     * - `invalid_nickname_characters`
     */
    public const val INVALID_NICKNAME_CHARACTERS: String = "INVALID_NICKNAME_CHARACTERS"

    /**
     * The provided display name character length is below the required minimum.
     *
     * **Localization keys:**
     * - `display_name_too_short`
     *
     * **Public arguments:**
     * - [UserErrorArgs.DISPLAY_NAME_MIN_LENGTH]: Minimum permitted character length (`{minLength}`).
     */
    public const val DISPLAY_NAME_TOO_SHORT: String = "DISPLAY_NAME_TOO_SHORT"

    /**
     * The provided display name character length exceeds the allowed maximum.
     *
     * **Localization keys:**
     * - `display_name_too_long`
     *
     * **Public arguments:**
     * - [UserErrorArgs.DISPLAY_NAME_MAX_LENGTH]: Maximum permitted character length (`{maxLength}`).
     */
    public const val DISPLAY_NAME_TOO_LONG: String = "DISPLAY_NAME_TOO_LONG"

    /**
     * The provided display name contains illegal control characters or line breaks.
     *
     * **Localization keys:**
     * - `invalid_display_name_characters`
     */
    public const val INVALID_DISPLAY_NAME_CHARACTERS: String = "INVALID_DISPLAY_NAME_CHARACTERS"
}
