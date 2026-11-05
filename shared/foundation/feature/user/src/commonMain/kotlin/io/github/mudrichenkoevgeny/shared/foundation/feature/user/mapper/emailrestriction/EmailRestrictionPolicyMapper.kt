package io.github.mudrichenkoevgeny.shared.foundation.feature.user.mapper.emailrestriction

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.emailrestriction.EmailRestrictionPolicy
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.emailrestriction.EmailRestrictionPolicyPayload

/**
 * Builds domain [EmailRestrictionPolicy] from [EmailRestrictionPolicyPayload] DTO.
 *
 * @return domain model aligned with [EmailRestrictionPolicy] semantics.
 */
public fun EmailRestrictionPolicyPayload.toEmailRestrictionPolicy(): EmailRestrictionPolicy =
    EmailRestrictionPolicy(
        isBlacklistEnabled = isBlacklistEnabled,
        blacklist = blacklist,
        isWhitelistEnabled = isWhitelistEnabled,
        whitelist = whitelist
    )

/**
 * Builds network [EmailRestrictionPolicyPayload] DTO from domain [EmailRestrictionPolicy].
 *
 * @return payload model aligned with [EmailRestrictionPolicyPayload] contract.
 */
public fun EmailRestrictionPolicy.toEmailRestrictionPolicyPayload(): EmailRestrictionPolicyPayload =
    EmailRestrictionPolicyPayload(
        isBlacklistEnabled = isBlacklistEnabled,
        blacklist = blacklist,
        isWhitelistEnabled = isWhitelistEnabled,
        whitelist = whitelist
    )
