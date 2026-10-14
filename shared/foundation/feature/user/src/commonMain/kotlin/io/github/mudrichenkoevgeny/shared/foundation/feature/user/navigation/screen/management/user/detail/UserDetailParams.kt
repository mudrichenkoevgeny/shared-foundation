package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.user.detail

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params.NavigationScreenParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.toUserIdOrNull
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import kotlinx.serialization.Serializable

/**
 * Strongly typed parsed parameters for the user detail management screen.
 *
 * @property userId Hex-dash string representation of the target user ID; wire key is [UserApiPaths.USER_ID].
 */
@Serializable
data class UserDetailParams(
    val userId: String
) : NavigationScreenParams {

    /**
     * Typed [UserId] representation of [userId], or `null` if invalid/absent.
     */
    val typedUserId: UserId?
        get() = userId.takeIf { it.isNotBlank() }?.toUserIdOrNull()

    override fun toQueryMap(): Map<String, List<String>> =
        UserDetailParamsParser.serialize(this)

    companion object {
        fun parse(params: Map<String, Any?>): UserDetailParams =
            UserDetailParamsParser.parse(params)
    }
}
