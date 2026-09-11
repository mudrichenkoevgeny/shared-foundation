package io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.iprestriction

import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.contract.SecurityApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Wire DTO representing blacklist and whitelist restrictions for IP addresses.
 *
 * @property isBlacklistEnabled Whether IP blacklist filtering is active.
 * @property blacklist List of forbidden IP addresses or CIDR ranges.
 * @property isWhitelistEnabled Whether IP whitelist filtering is active.
 * @property whitelist List of explicitly allowed IP addresses or CIDR ranges.
 */
@Serializable
data class IpRestrictionPolicyPayload(
    @SerialName(SecurityApiFields.IS_BLACKLIST_ENABLED)
    val isBlacklistEnabled: Boolean,

    @SerialName(SecurityApiFields.BLACKLIST)
    val blacklist: List<String>,

    @SerialName(SecurityApiFields.IS_WHITELIST_ENABLED)
    val isWhitelistEnabled: Boolean,

    @SerialName(SecurityApiFields.WHITELIST)
    val whitelist: List<String>
)
