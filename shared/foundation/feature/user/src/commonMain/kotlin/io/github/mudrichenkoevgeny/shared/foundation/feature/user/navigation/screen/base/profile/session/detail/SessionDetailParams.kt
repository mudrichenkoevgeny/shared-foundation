package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.profile.session.detail

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params.NavigationScreenParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.toUserSessionIdOrNull
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.toUserIdOrNull
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import kotlinx.serialization.Serializable

/**
 * Strongly typed parsed parameters for the session detail screen.
 *
 * @property sessionId Hex-dash string representation of the target session ID; wire key is [UserApiPaths.SESSION_ID].
 * @property userId Hex-dash string representation of the target user ID when opened from user management context; wire key is [UserApiPaths.USER_ID].
 */
@Serializable
data class SessionDetailParams(
    val sessionId: String,
    val userId: String? = null
) : NavigationScreenParams {

    /**
     * Typed [UserSessionId] representation of [sessionId], or `null` if invalid/absent.
     */
    val typedSessionId: UserSessionId?
        get() = sessionId.takeIf { it.isNotBlank() }?.toUserSessionIdOrNull()

    /**
     * Typed [UserId] representation of [userId], or `null` if invalid/absent.
     */
    val typedUserId: UserId?
        get() = userId?.toUserIdOrNull()

    override fun toQueryMap(): Map<String, List<String>> =
        SessionDetailParamsParser.serialize(this)

    companion object {
        fun parse(params: Map<String, Any?>): SessionDetailParams =
            SessionDetailParamsParser.parse(params)
    }
}
