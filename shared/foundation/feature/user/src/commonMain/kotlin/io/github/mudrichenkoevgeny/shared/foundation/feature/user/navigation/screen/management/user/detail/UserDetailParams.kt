package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.user.detail

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params.NavigationScreenParams
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.NavigationScreenParamsParser
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.normalizeQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.toUserIdOrNull
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import kotlinx.serialization.Serializable

/**
 * Strongly typed parsed parameters for the user detail management screen.
 *
 * @property userId Raw string representation of the target user ID extracted from path parameters;
 * wire key is [UserApiPaths.USER_ID]. May be empty or unvalidated until parsed via [typedUserId].
 */
@Serializable
data class UserDetailParams(
    val userId: String = ""
) : NavigationScreenParams<UserDetailParams> {

    /**
     * Parsed strongly typed [UserId] representation of [userId].
     *
     * Returns `null` if [userId] is blank or does not conform to the canonical UUID format.
     */
    val typedUserId: UserId?
        get() = userId.toUserIdOrNull()

    override val parser: NavigationScreenParamsParser<UserDetailParams>
        get() = Companion

    companion object : NavigationScreenParamsParser<UserDetailParams> {

        /**
         * Parses raw Deep Link or Web Router query parameters into typed [UserDetailParams].
         *
         * @param params Map of parameter keys to raw parameter values.
         * @return Validated [UserDetailParams] instance.
         */
        override fun parse(params: Map<String, Any?>): UserDetailParams {
            val normalized = params.normalizeQueryParams()
            val userId = normalized[UserApiPaths.USER_ID]?.firstOrNull()?.trim() ?: ""
            return UserDetailParams(userId = userId)
        }

        /**
         * Serializes [UserDetailParams] into a map of URL query parameter strings.
         *
         * Returns an empty map because the user ID is represented as a path parameter in the route URL
         * rather than a query parameter.
         *
         * @param params Typed [UserDetailParams] to serialize.
         * @return Empty map as no query parameters are associated with this screen.
         */
        override fun serialize(params: UserDetailParams): Map<String, List<String>> = emptyMap()
    }
}