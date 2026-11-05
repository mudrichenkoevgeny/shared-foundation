package io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.iprestriction

/**
 * Policy defining blacklist and whitelist restrictions for IP address access control.
 *
 * @property isBlacklistEnabled Whether IP blacklist filtering is active.
 * @property blacklist List of forbidden IP addresses or CIDR ranges.
 * @property isWhitelistEnabled Whether IP whitelist filtering is active.
 * @property whitelist List of explicitly allowed IP addresses or CIDR ranges.
 */
public data class IpRestrictionPolicy(
    public val isBlacklistEnabled: Boolean,
    public val blacklist: List<String>,
    public val isWhitelistEnabled: Boolean,
    public val whitelist: List<String>
)
