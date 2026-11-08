package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierPrivate
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Supported `sort_by` wire values for user identifier list endpoints, aligned with fields on [UserIdentifierPrivate].
 *
 * @property value Wire value for `sort_by`.
 */
@Serializable
public enum class UserIdentifierSortBy(
    public val value: String
) {
    /** Sort by creation timestamp. */
    @SerialName(CommonApiFields.CREATED_AT)
    CREATED_AT(CommonApiFields.CREATED_AT),

    /** Sort by update timestamp. */
    @SerialName(CommonApiFields.UPDATED_AT)
    UPDATED_AT(CommonApiFields.UPDATED_AT),

    /** Sort by identifier string. */
    @SerialName(UserApiFields.IDENTIFIER)
    IDENTIFIER(UserApiFields.IDENTIFIER),

    /** Sort by display name. */
    @SerialName(UserApiFields.DISPLAY_NAME)
    DISPLAY_NAME(UserApiFields.DISPLAY_NAME);

    public companion object {
        /** Default sort field ([CREATED_AT]). */
        public val DEFAULT: UserIdentifierSortBy = CREATED_AT

        /**
         * Returns [UserIdentifierSortBy] for the given wire value or case-insensitive enum name.
         */
        public fun fromValueOrNull(value: String): UserIdentifierSortBy? = entries.firstOrNull {
            it.value.equals(value, ignoreCase = true) ||
                it.name.equals(value, ignoreCase = true)
        }

        /**
         * Returns [UserIdentifierSortBy] for the given wire or enum-style string.
         *
         * @throws IllegalArgumentException if [value] does not match any [UserIdentifierSortBy].
         */
        public fun fromValueOrThrow(value: String): UserIdentifierSortBy =
            fromValueOrNull(value) ?: throw IllegalArgumentException(
                "Unknown value for ${UserIdentifierSortBy::class.simpleName}: '$value'"
            )
    }
}
