package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.user.session.detail

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params.NavigationScreenParams
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.NavigationScreenParamsParser
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.normalizeQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.toUserSessionIdOrNull
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.toUserIdOrNull
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import kotlinx.serialization.Serializable

/**
 * Strongly typed parsed parameters for the session detail screen.
 *
 * @property sessionId Raw string representation of the target session ID extracted from path parameters;
 * wire key is [UserApiPaths.SESSION_ID]. May be empty or unvalidated until parsed via [typedSessionId].
 * @property userId Raw string representation of the target user ID when opened from user management context;
 * wire key is [UserApiPaths.USER_ID]. May be null or unvalidated until parsed via [typedUserId].
 */
@Serializable
data class SessionDetailParams(
    val sessionId: String = "",
    val userId: String? = null
) : NavigationScreenParams<SessionDetailParams> {

    /**
     * Parsed strongly typed [UserSessionId] representation of [sessionId].
     *
     * Returns `null` if [sessionId] is blank or does not conform to the canonical UUID format.
     */
    val typedSessionId: UserSessionId?
        get() = sessionId.toUserSessionIdOrNull()

    /**
     * Parsed strongly typed [UserId] representation of [userId].
     *
     * Returns `null` if [userId] is null, blank, or does not conform to the canonical UUID format.
     */
    val typedUserId: UserId?
        get() = userId?.toUserIdOrNull()

    override val parser: NavigationScreenParamsParser<SessionDetailParams>
        get() = Companion

    companion object : NavigationScreenParamsParser<SessionDetailParams> {

        /**
         * Parses raw Deep Link or Web Router query/path parameters into typed [SessionDetailParams].
         *
         * @param params Map of parameter keys to raw parameter values.
         * @return Validated [SessionDetailParams] instance.
         */
        override fun parse(params: Map<String, Any?>): SessionDetailParams {
            val normalized = params.normalizeQueryParams()
            val sessionId = normalized[UserApiPaths.SESSION_ID]?.firstOrNull()?.trim().orEmpty()
            val userId = normalized[UserApiPaths.USER_ID]?.firstOrNull()?.trim()?.takeIf { it.isNotBlank() }
            return SessionDetailParams(sessionId = sessionId, userId = userId)
        }

        /**
         * Serializes [SessionDetailParams] into a map of URL query parameter strings.
         *
         * Returns an empty map because sessionId and userId are represented as path parameters in route URLs
         * rather than query parameters.
         *
         * @param params Typed [SessionDetailParams] to serialize.
         * @return Empty map as no query parameters are associated with this screen.
         */
        override fun serialize(params: SessionDetailParams): Map<String, List<String>> = emptyMap()
    }
}