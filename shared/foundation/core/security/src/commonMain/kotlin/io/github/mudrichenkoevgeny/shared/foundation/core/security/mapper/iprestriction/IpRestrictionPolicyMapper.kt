package io.github.mudrichenkoevgeny.shared.foundation.core.security.mapper.iprestriction

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.iprestriction.IpRestrictionPolicy
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.iprestriction.IpRestrictionPolicyPayload

/**
 * Builds domain [IpRestrictionPolicy] from [IpRestrictionPolicyPayload] DTO.
 *
 * @return domain model aligned with [IpRestrictionPolicy] semantics.
 */
public fun IpRestrictionPolicyPayload.toIpRestrictionPolicy(): IpRestrictionPolicy =
    IpRestrictionPolicy(
        isBlacklistEnabled = isBlacklistEnabled,
        blacklist = blacklist,
        isWhitelistEnabled = isWhitelistEnabled,
        whitelist = whitelist
    )

/**
 * Builds network [IpRestrictionPolicyPayload] DTO from domain [IpRestrictionPolicy].
 *
 * @return payload model aligned with [IpRestrictionPolicyPayload] contract.
 */
public fun IpRestrictionPolicy.toIpRestrictionPolicyPayload(): IpRestrictionPolicyPayload =
    IpRestrictionPolicyPayload(
        isBlacklistEnabled = isBlacklistEnabled,
        blacklist = blacklist,
        isWhitelistEnabled = isWhitelistEnabled,
        whitelist = whitelist
    )
