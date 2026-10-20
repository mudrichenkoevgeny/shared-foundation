package io.github.mudrichenkoevgeny.shared.foundation.testing.core.security.network.model.iprestriction

import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.iprestriction.IpRestrictionPolicyPayload

/**
 * Creates a test [IpRestrictionPolicyPayload] wire object instance.
 *
 * @param isBlacklistEnabled Flag indicating whether IP blacklist is active.
 * @param blacklist List of blacklisted IP addresses or CIDR blocks.
 * @param isWhitelistEnabled Flag indicating whether IP whitelist is active.
 * @param whitelist List of whitelisted IP addresses or CIDR blocks.
 */
fun createTestIpRestrictionPolicyPayload(
    isBlacklistEnabled: Boolean = false,
    blacklist: List<String> = emptyList(),
    isWhitelistEnabled: Boolean = false,
    whitelist: List<String> = emptyList()
) = IpRestrictionPolicyPayload(
    isBlacklistEnabled = isBlacklistEnabled,
    blacklist = blacklist,
    isWhitelistEnabled = isWhitelistEnabled,
    whitelist = whitelist
)
