package io.github.mudrichenkoevgeny.shared.foundation.feature.user.localization

import io.github.mudrichenkoevgeny.shared.foundation.core.common.localization.CommonModuleStrings
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.accountlockout.AccountLockoutType

/**
 * Extension functions providing smart, contextual resolution of localized error messages
 * for user domain errors.
 */

/**
 * Resolves localized message for account lockout failures.
 *
 * @param lockoutType Account lockout category ([AccountLockoutType]: `TEMPORARY`, `INDEFINITE`, `NONE`).
 * @param formattedUntil Formatted expiration date-time string for temporary lockouts (e.g., `"2026-03-12 14:30:00 UTC"`).
 * @return Localized user-friendly error message.
 */
fun UserModuleStrings.resolveUserLockedMessage(
    lockoutType: AccountLockoutType?,
    formattedUntil: String? = null
): String = when {
    lockoutType == AccountLockoutType.TEMPORARY && !formattedUntil.isNullOrBlank() ->
        errors.locked(formattedUntil)
    else ->
        errors.lockedWithoutArgs
}

/**
 * Resolves localized message for identifier limit reached failures.
 *
 * Automatically resolves the provider key (`"email"`, `"phone"`, or external providers) into a localized label
 * using [commonStrings].
 *
 * @param commonStrings [CommonModuleStrings] instance used to resolve provider display labels.
 * @param limit Maximum allowed count of identifiers for the provider category.
 * @param providerRaw Raw provider wire string or key (e.g. `"email"`, `"phone"`, `"google"`, `"apple"`, `"external_provider"`).
 * @return Localized user-friendly error message.
 */
fun UserModuleStrings.resolveUserIdentifierLimitReachedMessage(
    commonStrings: CommonModuleStrings,
    limit: Any?,
    providerRaw: String?
): String {
    if (limit == null || providerRaw.isNullOrBlank()) {
        return errors.identifierLimitReachedWithoutArgs
    }

    val localizedProvider = when (providerRaw.lowercase()) {
        "email" -> commonStrings.general.email
        "phone" -> commonStrings.general.phone
        else -> commonStrings.general.externalProvider
    }

    return errors.identifierLimitReached(limit = limit, provider = localizedProvider)
}

/**
 * Resolves localized message for total account-wide identifier limit reached failures.
 *
 * @param limit Total maximum allowed count of identifiers for the account.
 * @return Localized user-friendly error message.
 */
fun UserModuleStrings.resolveTotalUserIdentifiersLimitReachedMessage(
    limit: Any?
): String = if (limit != null) {
    errors.totalIdentifiersLimitReached(limit)
} else {
    errors.totalIdentifiersLimitReachedWithoutArgs
}
