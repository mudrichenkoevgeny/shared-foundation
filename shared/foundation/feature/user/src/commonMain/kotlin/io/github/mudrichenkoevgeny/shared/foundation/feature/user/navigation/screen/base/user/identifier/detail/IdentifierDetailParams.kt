package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.user.identifier.detail

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params.NavigationScreenParams
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.NavigationScreenParamsParser
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.normalizeQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.toUserIdentifierIdOrNull
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.toUserIdOrNull
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import kotlinx.serialization.Serializable

/**
 * Strongly typed parsed parameters for the credential/identifier detail screen.
 *
 * @property identifierId Raw string representation of the target identifier ID extracted from path parameters;
 * wire key is [UserApiPaths.USER_IDENTIFIER_ID]. May be empty or unvalidated until parsed via [typedIdentifierId].
 * @property userId Raw string representation of the target user ID when opened from user management context;
 * wire key is [UserApiPaths.USER_ID]. May be null or unvalidated until parsed via [typedUserId].
 */
@Serializable
data class IdentifierDetailParams(
    val identifierId: String = "",
    val userId: String? = null
) : NavigationScreenParams<IdentifierDetailParams> {

    /**
     * Parsed strongly typed [UserIdentifierId] representation of [identifierId].
     *
     * Returns `null` if [identifierId] is blank or does not conform to the canonical UUID format.
     */
    val typedIdentifierId: UserIdentifierId?
        get() = identifierId.toUserIdentifierIdOrNull()

    /**
     * Parsed strongly typed [UserId] representation of [userId].
     *
     * Returns `null` if [userId] is null, blank, or does not conform to the canonical UUID format.
     */
    val typedUserId: UserId?
        get() = userId?.toUserIdOrNull()

    override val parser: NavigationScreenParamsParser<IdentifierDetailParams>
        get() = Companion

    companion object : NavigationScreenParamsParser<IdentifierDetailParams> {

        /**
         * Parses raw Deep Link or Web Router query/path parameters into typed [IdentifierDetailParams].
         *
         * @param params Map of parameter keys to raw parameter values.
         * @return Validated [IdentifierDetailParams] instance.
         */
        override fun parse(params: Map<String, Any?>): IdentifierDetailParams {
            val normalized = params.normalizeQueryParams()
            val identifierId = normalized[UserApiPaths.USER_IDENTIFIER_ID]?.firstOrNull()?.trim().orEmpty()
            val userId = normalized[UserApiPaths.USER_ID]?.firstOrNull()?.trim()?.takeIf { it.isNotBlank() }
            return IdentifierDetailParams(identifierId = identifierId, userId = userId)
        }

        /**
         * Serializes [IdentifierDetailParams] into a map of URL query parameter strings.
         *
         * Returns an empty map because identifierId and userId are represented as path parameters in route URLs
         * rather than query parameters.
         *
         * @param params Typed [IdentifierDetailParams] to serialize.
         * @return Empty map as no query parameters are associated with this screen.
         */
        override fun serialize(params: IdentifierDetailParams): Map<String, List<String>> = emptyMap()
    }
}