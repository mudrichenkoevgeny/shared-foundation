package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.model.emailrestriction

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.emailrestriction.EmailRestrictionPolicyPayload

/**
 * Creates a test [EmailRestrictionPolicyPayload] wire object instance.
 *
 * @param isBlacklistEnabled Flag indicating whether email blacklist is enabled.
 * @param blacklist List of blacklisted domain rule strings.
 * @param isWhitelistEnabled Flag indicating whether email whitelist is enabled.
 * @param whitelist List of whitelisted domain rule strings.
 */
fun createTestEmailRestrictionPolicyPayload(
    isBlacklistEnabled: Boolean = false,
    blacklist: List<String> = emptyList(),
    isWhitelistEnabled: Boolean = false,
    whitelist: List<String> = emptyList()
) = EmailRestrictionPolicyPayload(
    isBlacklistEnabled = isBlacklistEnabled,
    blacklist = blacklist,
    isWhitelistEnabled = isWhitelistEnabled,
    whitelist = whitelist
)
