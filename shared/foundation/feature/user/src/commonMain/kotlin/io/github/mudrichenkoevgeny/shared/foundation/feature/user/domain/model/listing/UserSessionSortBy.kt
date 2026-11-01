package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionPrivate
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Supported `sort_by` wire values for user session list endpoints, aligned with fields on [UserSessionPrivate].
 *
 * @property value Wire value for `sort_by`.
 */
@Serializable
enum class UserSessionSortBy(val value: String) {
    /** Sort by session expiration timestamp. */
    @SerialName(UserApiFields.EXPIRES_AT)
    EXPIRES_AT(UserApiFields.EXPIRES_AT),

    /** Sort by last accessed timestamp. */
    @SerialName(UserApiFields.LAST_ACCESSED_AT)
    LAST_ACCESSED_AT(UserApiFields.LAST_ACCESSED_AT),

    /** Sort by last re-authentication timestamp. */
    @SerialName(UserApiFields.LAST_REAUTHENTICATED_AT)
    LAST_REAUTHENTICATED_AT(UserApiFields.LAST_REAUTHENTICATED_AT),

    /** Sort by creation timestamp. */
    @SerialName(CommonApiFields.CREATED_AT)
    CREATED_AT(CommonApiFields.CREATED_AT),

    /** Sort by update timestamp. */
    @SerialName(CommonApiFields.UPDATED_AT)
    UPDATED_AT(CommonApiFields.UPDATED_AT);

    companion object {
        /** Default sort field ([CREATED_AT]). */
        val DEFAULT = CREATED_AT

        /**
         * Returns [UserSessionSortBy] for the given wire value or case-insensitive enum name.
         */
        fun fromValueOrNull(value: String): UserSessionSortBy? =
            entries.firstOrNull {
                it.value.equals(value, ignoreCase = true) ||
                        it.name.equals(value, ignoreCase = true)
            }

        /**
         * Returns [UserSessionSortBy] for the given wire or enum-style string.
         *
         * @throws IllegalArgumentException if [value] does not match any [UserSessionSortBy].
         */
        fun fromValueOrThrow(value: String): UserSessionSortBy =
            fromValueOrNull(value) ?: throw IllegalArgumentException(
                "Unknown value for ${UserSessionSortBy::class.simpleName}: '$value'"
            )
    }
}
