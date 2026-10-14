package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.profile.identifier.detail

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params.NavigationScreenParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.toUserIdentifierIdOrNull
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.toUserIdOrNull
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import kotlinx.serialization.Serializable

/**
 * Strongly typed parsed parameters for the credential/identifier detail screen.
 *
 * @property identifierId Hex-dash string representation of the target identifier ID; wire key is [UserApiPaths.USER_IDENTIFIER_ID].
 * @property userId Hex-dash string representation of the target user ID when opened from user management context; wire key is [UserApiPaths.USER_ID].
 */
@Serializable
data class IdentifierDetailParams(
    val identifierId: String,
    val userId: String? = null
) : NavigationScreenParams {

    /**
     * Typed [UserIdentifierId] representation of [identifierId], or `null` if invalid/absent.
     */
    val typedIdentifierId: UserIdentifierId?
        get() = identifierId.takeIf { it.isNotBlank() }?.toUserIdentifierIdOrNull()

    /**
     * Typed [UserId] representation of [userId], or `null` if invalid/absent.
     */
    val typedUserId: UserId?
        get() = userId?.toUserIdOrNull()

    override fun toQueryMap(): Map<String, List<String>> =
        IdentifierDetailParamsParser.serialize(this)

    companion object {
        fun parse(params: Map<String, Any?>): IdentifierDetailParams =
            IdentifierDetailParamsParser.parse(params)
    }
}
