package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.emailrestriction

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.emailrestriction.EmailRestrictionPolicy

/**
 * Creates a test [EmailRestrictionPolicy] domain instance.
 *
 * @param isBlacklistEnabled Flag indicating whether email blacklist is enabled.
 * @param blacklist List of blacklisted domain rules.
 * @param isWhitelistEnabled Flag indicating whether email whitelist is enabled.
 * @param whitelist List of whitelisted domain rules.
 */
fun createTestEmailRestrictionPolicy(
    isBlacklistEnabled: Boolean = false,
    blacklist: List<String> = emptyList(),
    isWhitelistEnabled: Boolean = false,
    whitelist: List<String> = emptyList()
) = EmailRestrictionPolicy(
    isBlacklistEnabled = isBlacklistEnabled,
    blacklist = blacklist,
    isWhitelistEnabled = isWhitelistEnabled,
    whitelist = whitelist
)
