package io.github.mudrichenkoevgeny.shared.foundation.feature.user.error.naming

import io.github.mudrichenkoevgeny.shared.foundation.core.common.error.naming.CommonErrorArgs
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.accountlockout.AccountLockoutType

/**
 * User-specific error codes for API responses and backend logging, used to categorize failures.
 */
object UserErrorCodes {
    // --- Token Errors ---

    /**
     * The provided access token is malformed, signature is invalid, or it does not exist.
     *
     * **Localization keys:**
     * - `invalid_session`
     */
    const val INVALID_ACCESS_TOKEN = "INVALID_ACCESS_TOKEN"

    /**
     * The access token has expired. Refresh is required.
     *
     * **Localization keys:**
     * - `access_token_expired`
     */
    const val ACCESS_TOKEN_EXPIRED = "ACCESS_TOKEN_EXPIRED"

    /**
     * The refresh token is invalid, has been revoked, or does not match the current session.
     *
     * **Localization keys:**
     * - `invalid_session`
     */
    const val INVALID_REFRESH_TOKEN = "INVALID_REFRESH_TOKEN"

    // --- User Session & Status ---

    /**
     * The session identifier is invalid or the session has been terminated.
     *
     * **Localization keys:**
     * - `invalid_session`
     */
    const val INVALID_SESSION = "INVALID_SESSION"

    /**
     * The user's role does not have permission to access the requested resource.
     *
     * **Localization keys:**
     * - `missing_permissions`
     *
     * **Secret arguments:**
     * - [UserErrorArgs.USER_ID]: Identifier of the user who lacks the role.
     */
    const val USER_ROLE_NOT_ALLOWED = "USER_ROLE_NOT_ALLOWED"

    /**
     * The user lacks specific fine-grained permissions required for the operation.
     *
     * **Localization keys:**
     * - `missing_permissions`
     *
     * **Secret arguments:**
     * - [UserErrorArgs.USER_ID]: Identifier of the user missing permissions.
     */
    const val USER_MISSING_PERMISSIONS = "USER_MISSING_PERMISSIONS"

    /**
     * The operation is denied because the user's authority level is lower than required for the target resource or action.
     *
     * **Localization keys:**
     * - `missing_permissions`
     *
     * **Secret arguments:**
     * - [UserErrorArgs.USER_ID]: Identifier of the user with insufficient authority.
     */
    const val USER_INSUFFICIENT_AUTHORITY_LEVEL = "USER_INSUFFICIENT_AUTHORITY_LEVEL"

    /**
     * The account is in a state that prevents the requested action.
     *
     * **Localization keys:**
     * - `forbidden`
     *
     * **Secret arguments:**
     * - [UserErrorArgs.USER_ID]: Identifier of the user with illegal status.
     */
    const val USER_ILLEGAL_ACCOUNT_STATUS = "USER_ILLEGAL_ACCOUNT_STATUS"

    /**
     * The user account has been explicitly banned by an administrator.
     *
     * **Localization keys:**
     * - `banned`
     *
     * **Secret arguments:**
     * - [UserErrorArgs.USER_ID]: Identifier of the banned user.
     */
    const val USER_BANNED = "USER_BANNED"

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
    const val USER_LOCKED = "USER_LOCKED"

    /**
     * The account is in read-only mode.
     *
     * **Localization keys:**
     * - `read_only`
     *
     * **Secret arguments:**
     * - [UserErrorArgs.USER_ID]: Identifier of the affected user.
     */
    const val USER_READ_ONLY = "USER_READ_ONLY"

    /**
     * Access is temporarily restricted due to suspicious activity or security concerns.
     *
     * **Localization keys:**
     * - `security_hold`
     *
     * **Secret arguments:**
     * - [UserErrorArgs.USER_ID]: Identifier of the restricted user.
     */
    const val USER_SECURITY_HOLD = "USER_SECURITY_HOLD"

    /**
     * The account is scheduled for deletion and cannot perform this action.
     *
     * **Localization keys:**
     * - `pending_deletion`
     */
    const val USER_PENDING_DELETION = "USER_PENDING_DELETION"

    /**
     * Access to the resource is forbidden for this user, regardless of authentication.
     *
     * **Localization keys:**
     * - `forbidden`
     *
     * **Secret arguments:**
     * - [UserErrorArgs.USER_ID]: Identifier of the forbidden user.
     */
    const val USER_FORBIDDEN = "USER_FORBIDDEN"

    /**
     * No user account was found matching the provided identifier.
     *
     * **Localization keys:**
     * - `not_found`
     *
     * **Secret arguments:**
     * - [UserErrorArgs.USER_ID]: The identifier value used for lookup.
     */
    const val USER_NOT_FOUND = "USER_NOT_FOUND"

    // --- Identifiers & Credentials ---

    /**
     * Authentication failed due to incorrect username, email, or password.
     *
     * **Localization keys:**
     * - `invalid_credentials`
     */
    const val INVALID_CREDENTIALS = "INVALID_CREDENTIALS"

    /**
     * The specified identifier cannot be removed from the account.
     *
     * **Localization keys:**
     * - `can_not_delete_identifier`
     */
    const val CAN_NOT_DELETE_USER_IDENTIFIER = "CAN_NOT_DELETE_USER_IDENTIFIER"

    /**
     * Failed to link a new identifier to the account.
     *
     * **Localization keys:**
     * - `can_not_create_identifier`
     */
    const val CAN_NOT_CREATE_USER_IDENTIFIER = "CAN_NOT_CREATE_USER_IDENTIFIER"

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
    const val USER_IDENTIFIER_LIMIT_REACHED = "USER_IDENTIFIER_LIMIT_REACHED"

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
    const val TOTAL_USER_IDENTIFIERS_LIMIT_REACHED = "TOTAL_USER_IDENTIFIERS_LIMIT_REACHED"

    // --- Internal Identifiers ---

    /**
     * The password provided for confirmation does not match the current password.
     *
     * **Localization keys:**
     * - `wrong_password`
     */
    const val WRONG_PASSWORD = "WRONG_PASSWORD"

    /**
     * The verification code is incorrect or has expired.
     *
     * **Localization keys:**
     * - `wrong_confirmation_code`
     */
    const val WRONG_CONFIRMATION_CODE = "WRONG_CONFIRMATION_CODE"

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
    const val EXTERNAL_IDENTIFIER_LINKAGE_FAILED = "EXTERNAL_IDENTIFIER_LINKAGE_FAILED"

    /**
     * Registration is currently disabled by system configuration.
     *
     * **Localization keys:**
     * - `registration_disabled`
     */
    const val REGISTRATION_DISABLED = "REGISTRATION_DISABLED"

    /**
     * Self-service unlock is currently disabled by security policy.
     *
     * **Localization keys:**
     * - `self_service_unlock_disabled`
     */
    const val SELF_SERVICE_UNLOCK_DISABLED = "SELF_SERVICE_UNLOCK_DISABLED"

    /**
     * The registration, login, or email linkage attempt was rejected because the email address or domain is not permitted by email restriction policy.
     *
     * **Localization keys:**
     * - `email_not_allowed`
     */
    const val EMAIL_NOT_ALLOWED = "EMAIL_NOT_ALLOWED"

    /**
     * Authentication or operation failed because the account has no password set or initial password setup is required.
     *
     * **Localization keys:**
     * - `password_setup_required`
     */
    const val PASSWORD_SETUP_REQUIRED = "PASSWORD_SETUP_REQUIRED"

    /**
     * Operation failed because the specified identifier type does not support password credentials.
     *
     * **Localization keys:**
     * - `identifier_password_not_supported`
     */
    const val USER_IDENTIFIER_PASSWORD_NOT_SUPPORTED = "USER_IDENTIFIER_PASSWORD_NOT_SUPPORTED"

    /**
     * Operation failed because no password credential is set on the specified identifier record.
     *
     * **Localization keys:**
     * - `identifier_password_not_set`
     */
    const val USER_IDENTIFIER_PASSWORD_NOT_SET = "USER_IDENTIFIER_PASSWORD_NOT_SET"
}
