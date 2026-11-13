package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.emailrestriction

/**
 * Policy defining blacklist and whitelist restrictions for email domain registration and usage.
 *
 * @property isBlacklistEnabled Whether email domain blacklist validation is active.
 * @property blacklist List of forbidden email domain names or patterns.
 * @property isWhitelistEnabled Whether email domain whitelist validation is active.
 * @property whitelist List of explicitly allowed email domain names or patterns.
 */
public data class EmailRestrictionPolicy(
    public val isBlacklistEnabled: Boolean,
    public val blacklist: List<String>,
    public val isWhitelistEnabled: Boolean,
    public val whitelist: List<String>
)
