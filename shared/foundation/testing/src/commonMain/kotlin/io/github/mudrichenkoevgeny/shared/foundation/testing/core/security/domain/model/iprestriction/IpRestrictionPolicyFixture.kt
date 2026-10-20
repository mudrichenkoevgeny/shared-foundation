package io.github.mudrichenkoevgeny.shared.foundation.testing.core.security.domain.model.iprestriction

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.iprestriction.IpRestrictionPolicy

/**
 * Creates a test [IpRestrictionPolicy] domain instance.
 *
 * @param isBlacklistEnabled Flag indicating whether IP blacklist is active.
 * @param blacklist List of blacklisted IP addresses or CIDR blocks.
 * @param isWhitelistEnabled Flag indicating whether IP whitelist is active.
 * @param whitelist List of whitelisted IP addresses or CIDR blocks.
 */
fun createTestIpRestrictionPolicy(
    isBlacklistEnabled: Boolean = false,
    blacklist: List<String> = emptyList(),
    isWhitelistEnabled: Boolean = false,
    whitelist: List<String> = emptyList()
) = IpRestrictionPolicy(
    isBlacklistEnabled = isBlacklistEnabled,
    blacklist = blacklist,
    isWhitelistEnabled = isWhitelistEnabled,
    whitelist = whitelist
)
