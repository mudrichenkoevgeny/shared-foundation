package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.emailrestriction

import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.contract.SecurityApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Wire DTO representing blacklist and whitelist restrictions for email domains.
 *
 * @property isBlacklistEnabled Whether email domain blacklist validation is active.
 * @property blacklist List of forbidden email domain names or patterns.
 * @property isWhitelistEnabled Whether email domain whitelist validation is active.
 * @property whitelist List of explicitly allowed email domain names or patterns.
 */
@Serializable
data class EmailRestrictionPolicyPayload(
    @SerialName(SecurityApiFields.IS_BLACKLIST_ENABLED)
    val isBlacklistEnabled: Boolean,

    @SerialName(SecurityApiFields.BLACKLIST)
    val blacklist: List<String>,

    @SerialName(SecurityApiFields.IS_WHITELIST_ENABLED)
    val isWhitelistEnabled: Boolean,

    @SerialName(SecurityApiFields.WHITELIST)
    val whitelist: List<String>
)
