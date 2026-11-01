package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user

/**
 * Utility extension functions for public and private user domain models ([UserSummary], [UserPrivate]).
 */

/**
 * Resolves the best available human-readable display name for [UserSummary].
 *
 * Fallback resolution order:
 * 1. Non-blank [UserSummary.displayName].
 * 2. Non-blank [UserSummary.nickname] prefixed with `@`.
 * 3. [fallbackPrefix] combined with short 8-character ID string.
 *
 * @param fallbackPrefix Localized fallback string.
 * @return Formatted display name string.
 */
fun UserSummary.resolveDisplayName(fallbackPrefix: String): String =
    resolveUserDisplayName(id, nickname, displayName, fallbackPrefix)

/**
 * Resolves the best available human-readable display name for [UserPrivate].
 *
 * Fallback resolution order:
 * 1. Non-blank [UserPrivate.displayName].
 * 2. Non-blank [UserPrivate.nickname] prefixed with `@`.
 * 3. [fallbackPrefix] combined with short 8-character ID string.
 *
 * @param fallbackPrefix Localized fallback string.
 * @return Formatted display name string.
 */
fun UserPrivate.resolveDisplayName(fallbackPrefix: String): String =
    resolveUserDisplayName(id, nickname, displayName, fallbackPrefix)

private fun resolveUserDisplayName(
    id: UserId,
    nickname: String?,
    displayName: String?,
    fallbackPrefix: String
): String {
    val trimmedDisplay = displayName?.trim()
    if (!trimmedDisplay.isNullOrBlank()) return trimmedDisplay

    val trimmedNick = nickname?.trim()
    if (!trimmedNick.isNullOrBlank()) return "@$trimmedNick"

    val shortId = id.asHexDashString().take(8)
    return "$fallbackPrefix $shortId"
}
