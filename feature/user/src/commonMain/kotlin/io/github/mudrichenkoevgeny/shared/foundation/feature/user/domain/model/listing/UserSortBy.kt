package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserPrivate
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Supported `sort_by` wire values for user list endpoints, aligned with fields on [UserPrivate].
 *
 * @property value Wire value for `sort_by`.
 */
@Serializable
public enum class UserSortBy(
    public val value: String
) {
    /** Sort by authority level. */
    @SerialName(UserApiFields.AUTHORITY_LEVEL)
    AUTHORITY_LEVEL(UserApiFields.AUTHORITY_LEVEL),

    /** Sort by last login timestamp. */
    @SerialName(UserApiFields.LAST_LOGIN_AT)
    LAST_LOGIN_AT(UserApiFields.LAST_LOGIN_AT),

    /** Sort by last active timestamp. */
    @SerialName(UserApiFields.LAST_ACTIVE_AT)
    LAST_ACTIVE_AT(UserApiFields.LAST_ACTIVE_AT),

    /** Sort by scheduled permanent deletion timestamp. */
    @SerialName(UserApiFields.SCHEDULED_PERMANENT_DELETION_AT)
    SCHEDULED_PERMANENT_DELETION_AT(UserApiFields.SCHEDULED_PERMANENT_DELETION_AT),

    /** Sort by temporary lockout expiration timestamp. */
    @SerialName(UserApiFields.TEMPORARY_LOCKOUT_UNTIL)
    TEMPORARY_LOCKOUT_UNTIL(UserApiFields.TEMPORARY_LOCKOUT_UNTIL),

    /** Sort by nickname handle. */
    @SerialName(UserApiFields.NICKNAME)
    NICKNAME(UserApiFields.NICKNAME),

    /** Sort by display name. */
    @SerialName(UserApiFields.DISPLAY_NAME)
    DISPLAY_NAME(UserApiFields.DISPLAY_NAME),

    /** Sort by birthdate. */
    @SerialName(UserApiFields.BIRTHDATE)
    BIRTHDATE(UserApiFields.BIRTHDATE),

    /** Sort by creation timestamp. */
    @SerialName(CommonApiFields.CREATED_AT)
    CREATED_AT(CommonApiFields.CREATED_AT),

    /** Sort by update timestamp. */
    @SerialName(CommonApiFields.UPDATED_AT)
    UPDATED_AT(CommonApiFields.UPDATED_AT);

    public companion object {
        /** Default sort field ([CREATED_AT]). */
        public val DEFAULT: UserSortBy = CREATED_AT

        /**
         * Returns [UserSortBy] for the given wire value or case-insensitive enum name.
         */
        public fun fromValueOrNull(value: String): UserSortBy? = entries.firstOrNull {
            it.value.equals(value, ignoreCase = true) ||
                it.name.equals(value, ignoreCase = true)
        }

        /**
         * Returns [UserSortBy] for the given wire or enum-style string.
         *
         * @throws IllegalArgumentException if [value] does not match any [UserSortBy].
         */
        public fun fromValueOrThrow(value: String): UserSortBy =
            fromValueOrNull(value) ?: throw IllegalArgumentException(
                "Unknown value for ${UserSortBy::class.simpleName}: '$value'"
            )
    }
}
