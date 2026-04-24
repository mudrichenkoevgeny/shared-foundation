package io.github.mudrichenkoevgeny.shared.foundation.feature.user.error.naming

import io.github.mudrichenkoevgeny.shared.foundation.core.common.error.naming.CommonErrorArgs

/**
 * User-specific error codes for API responses and backend logging, used to categorize failures.
 */
object UserErrorCodes {
    // --- Token Errors ---

    /** The provided access token is malformed, signature is invalid, or it does not exist. */
    const val INVALID_ACCESS_TOKEN = "INVALID_ACCESS_TOKEN"

    /** The access token has expired. Refresh is required. */
    const val ACCESS_TOKEN_EXPIRED = "ACCESS_TOKEN_EXPIRED"

    /** The refresh token is invalid, has been revoked, or does not match the current session. */
    const val INVALID_REFRESH_TOKEN = "INVALID_REFRESH_TOKEN"

    // --- User Session & Status ---

    /** The session identifier is invalid or the session has been terminated. */
    const val INVALID_SESSION = "INVALID_SESSION"

    /** * The user's role does not have permission to access the requested resource.
     *
     * **Secret arguments:**
     * - [UserErrorArgs.USER_ID]: Identifier of the user who lacks the role.
     */
    const val USER_ROLE_NOT_ALLOWED = "USER_ROLE_NOT_ALLOWED"

    /** * The user lacks specific fine-grained permissions required for the operation.
     *
     * **Secret arguments:**
     * - [UserErrorArgs.USER_ID]: Identifier of the user missing permissions.
     */
    const val USER_MISSING_PERMISSIONS = "USER_MISSING_PERMISSIONS"

    /** * The operation is denied because the user's authority level is lower than required
     * for the target resource or action.
     *
     * **Secret arguments:**
     * - [UserErrorArgs.USER_ID]: Identifier of the user with insufficient authority.
     */
    const val USER_INSUFFICIENT_AUTHORITY_LEVEL = "USER_INSUFFICIENT_AUTHORITY_LEVEL"

    /** * The account is in a state that prevents the requested action.
     *
     * **Secret arguments:**
     * - [UserErrorArgs.USER_ID]: Identifier of the user with illegal status.
     */
    const val USER_ILLEGAL_ACCOUNT_STATUS = "USER_ILLEGAL_ACCOUNT_STATUS"

    /** * The user account has been explicitly blocked by an administrator.
     *
     * **Secret arguments:**
     * - [UserErrorArgs.USER_ID]: Identifier of the blocked user.
     */
    const val USER_BLOCKED = "USER_BLOCKED"

    /** * The account is in read-only mode (e.g. during maintenance or billing issues).
     *
     * **Secret arguments:**
     * - [UserErrorArgs.USER_ID]: Identifier of the affected user.
     */
    const val USER_READ_ONLY = "USER_READ_ONLY"

    /** * Access is temporarily restricted due to suspicious activity or security concerns.
     *
     * **Secret arguments:**
     * - [UserErrorArgs.USER_ID]: Identifier of the restricted user.
     */
    const val USER_SECURITY_HOLD = "USER_SECURITY_HOLD"

    /** * The account is scheduled for deletion and cannot perform this action.
     *
     * **Secret arguments:**
     * - [UserErrorArgs.USER_ID]: Identifier of the user pending deletion.
     */
    const val USER_PENDING_DELETION = "USER_PENDING_DELETION"

    /** * Access to the resource is forbidden for this user, regardless of authentication.
     *
     * **Secret arguments:**
     * - [UserErrorArgs.USER_ID]: Identifier of the forbidden user.
     */
    const val USER_FORBIDDEN = "USER_FORBIDDEN"

    /** * No user account was found matching the provided identifier.
     *
     * **Secret arguments:**
     * - [UserErrorArgs.USER_ID]: The identifier value used for lookup.
     */
    const val USER_NOT_FOUND = "USER_NOT_FOUND"

    // --- Identifiers & Credentials ---

    /** Authentication failed due to incorrect username, email, or password. */
    const val INVALID_CREDENTIALS = "INVALID_CREDENTIALS"

    /** The specified identifier cannot be removed from the account. */
    const val CAN_NOT_DELETE_USER_IDENTIFIER = "CAN_NOT_DELETE_USER_IDENTIFIER"

    /** Failed to link a new identifier to the account. */
    const val CAN_NOT_CREATE_USER_IDENTIFIER = "CAN_NOT_CREATE_USER_IDENTIFIER"

    /** * The maximum number of identifiers for a specific type has been reached.
     *
     * **Public arguments:**
     * - [UserErrorArgs.MAX_NUMBER_OF_IDENTIFIERS]: The limit allowed for the provider.
     * - [UserErrorArgs.USER_AUTH_PROVIDER]: The provider that reached its limit.
     */
    const val USER_IDENTIFIER_LIMIT_REACHED = "USER_IDENTIFIER_LIMIT_REACHED"

    /** * The total number of allowed identifiers for the account has been reached.
     *
     * **Public arguments:**
     * - [UserErrorArgs.MAX_NUMBER_OF_IDENTIFIERS]: The total overall limit.
     */
    const val TOTAL_USER_IDENTIFIERS_LIMIT_REACHED = "TOTAL_USER_IDENTIFIERS_LIMIT_REACHED"

    // --- Internal Identifiers ---

    /** The password provided for confirmation does not match the current password. */
    const val WRONG_PASSWORD = "WRONG_PASSWORD"

    /** The verification code is incorrect or has expired. */
    const val WRONG_CONFIRMATION_CODE = "WRONG_CONFIRMATION_CODE"

    // --- External Identifiers ---

    /** * Linking with an external provider (OAuth/OpenID) failed.
     *
     * **Secret arguments:**
     * - [CommonErrorArgs.MESSAGE]: Cause of the linkage failure.
     */
    const val EXTERNAL_IDENTIFIER_LINKAGE_FAILED = "EXTERNAL_IDENTIFIER_LINKAGE_FAILED"
}