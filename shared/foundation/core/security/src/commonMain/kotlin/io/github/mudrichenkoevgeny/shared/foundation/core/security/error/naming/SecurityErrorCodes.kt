package io.github.mudrichenkoevgeny.shared.foundation.core.security.error.naming

import io.github.mudrichenkoevgeny.shared.foundation.core.security.passwordpolicy.model.PasswordPolicy

/**
 * Security-specific error codes for API responses and backend logging, used to categorize failures.
 */
object SecurityErrorCodes {
    /** Additional authentication or confirmation step is required before the action. */
    const val AUTHENTICATION_CONFIRMATION_REQUIRED = "AUTHENTICATION_CONFIRMATION_REQUIRED"

    /** Password does not satisfy the configured [PasswordPolicy]. */
    const val PASSWORD_TOO_WEAK = "PASSWORD_TOO_WEAK"
}
