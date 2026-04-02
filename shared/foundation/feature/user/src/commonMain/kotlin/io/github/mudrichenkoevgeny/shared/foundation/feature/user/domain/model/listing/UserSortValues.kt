package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.CommonSortValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.identifier.UserIdentifierMaskedPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.identifier.UserIdentifierUnmaskedPayload
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

object UserSortValues {
    /**
     * Supported `sort_by` values for user list endpoints.
     */
    @Serializable
    enum class UserSortBy {
        /** Sort by [UserApiFields.LAST_LOGIN_AT]. */
        @SerialName(UserApiFields.LAST_LOGIN_AT)
        LAST_LOGIN_AT,

        /** Sort by [UserApiFields.LAST_ACTIVE_AT]. */
        @SerialName(UserApiFields.LAST_ACTIVE_AT)
        LAST_ACTIVE_AT,

        /** Sort by [UserApiFields.SCHEDULED_PERMANENT_DELETION_AT]. */
        @SerialName(UserApiFields.SCHEDULED_PERMANENT_DELETION_AT)
        SCHEDULED_PERMANENT_DELETION_AT,

        /** Sort by creation timestamp. */
        @SerialName(CommonApiFields.CREATED_AT)
        CREATED_AT,

        /** Sort by update timestamp. */
        @SerialName(CommonApiFields.UPDATED_AT)
        UPDATED_AT;

        /** Wire value for `sort_by`. */
        val serialName: String
            get() = when (this) {
                LAST_LOGIN_AT -> UserApiFields.LAST_LOGIN_AT
                LAST_ACTIVE_AT -> UserApiFields.LAST_ACTIVE_AT
                SCHEDULED_PERMANENT_DELETION_AT -> UserApiFields.SCHEDULED_PERMANENT_DELETION_AT
                CREATED_AT -> CommonApiFields.CREATED_AT
                UPDATED_AT -> CommonApiFields.UPDATED_AT
            }

        companion object {
            /**
             * Returns [UserSortBy] for the given wire value or case-insensitive enum name.
             */
            fun fromValueOrNull(value: String): UserSortBy? =
                runCatching { valueOf(value.uppercase()) }.getOrNull()

            /**
             * Returns [UserSortBy] for the given wire or enum-style string.
             *
             * @throws IllegalArgumentException if [value] does not match any [UserSortBy].
             */
            fun fromValueOrThrow(value: String): UserSortBy = valueOf(value.uppercase())
        }
    }

    /**
     * Supported `sort_by` values for user identifier list endpoints, aligned with timestamp fields on
     * [UserIdentifierUnmaskedPayload] (`created_at`, `updated_at` only). [UserIdentifierMaskedPayload] uses the same
     * wire names for those fields.
     */
    @Serializable
    enum class UserIdentifierSortBy {
        /** Sort by creation timestamp. */
        @SerialName(CommonApiFields.CREATED_AT)
        CREATED_AT,

        /** Sort by update timestamp. */
        @SerialName(CommonApiFields.UPDATED_AT)
        UPDATED_AT;

        /** Wire value for `sort_by`. */
        val serialName: String
            get() = when (this) {
                CREATED_AT -> CommonApiFields.CREATED_AT
                UPDATED_AT -> CommonApiFields.UPDATED_AT
            }

        companion object {
            /**
             * Returns [UserIdentifierSortBy] for the given wire value or case-insensitive enum name.
             */
            fun fromValueOrNull(value: String): UserIdentifierSortBy? =
                runCatching { valueOf(value.uppercase()) }.getOrNull()

            /**
             * Returns [UserIdentifierSortBy] for the given wire or enum-style string.
             *
             * @throws IllegalArgumentException if [value] does not match any [UserIdentifierSortBy].
             */
            fun fromValueOrThrow(value: String): UserIdentifierSortBy =
                valueOf(value.uppercase())
        }
    }

    /**
     * Supported `sort_by` wire values for **session list** endpoints (open and management), aligned with session DTO
     * timestamp fields (`expires_at`, `last_accessed_at`, `last_reauthenticated_at`, `created_at`, `updated_at`).
     */
    @Serializable
    enum class UserSessionSortBy {
        /** Sort by [UserApiFields.EXPIRES_AT] (`expires_at`). */
        @SerialName(UserApiFields.EXPIRES_AT)
        EXPIRES_AT,

        /** Sort by [UserApiFields.LAST_ACCESSED_AT] (`last_accessed_at`). */
        @SerialName(UserApiFields.LAST_ACCESSED_AT)
        LAST_ACCESSED_AT,

        /** Sort by [UserApiFields.LAST_REAUTHENTICATED_AT] (`last_reauthenticated_at`). */
        @SerialName(UserApiFields.LAST_REAUTHENTICATED_AT)
        LAST_REAUTHENTICATED_AT,

        /** Sort by creation timestamp ([CommonSortValues.TimestampSortBy.CREATED_AT]). */
        @SerialName(CommonApiFields.CREATED_AT)
        CREATED_AT,

        /** Sort by update timestamp ([CommonSortValues.TimestampSortBy.UPDATED_AT]). */
        @SerialName(CommonApiFields.UPDATED_AT)
        UPDATED_AT;

        /** Wire value for `sort_by`. */
        val serialName: String
            get() = when (this) {
                EXPIRES_AT -> UserApiFields.EXPIRES_AT
                LAST_ACCESSED_AT -> UserApiFields.LAST_ACCESSED_AT
                LAST_REAUTHENTICATED_AT -> UserApiFields.LAST_REAUTHENTICATED_AT
                CREATED_AT -> CommonApiFields.CREATED_AT
                UPDATED_AT -> CommonApiFields.UPDATED_AT
            }

        companion object {
            /**
             * Returns [UserSessionSortBy] for the given wire value or case-insensitive enum name.
             */
            fun fromValueOrNull(value: String): UserSessionSortBy? =
                runCatching { valueOf(value.uppercase()) }.getOrNull()

            /**
             * Returns [UserSessionSortBy] for the given wire or enum-style string.
             *
             * @throws IllegalArgumentException if [value] does not match any [UserSessionSortBy].
             */
            fun fromValueOrThrow(value: String): UserSessionSortBy =
                valueOf(value.uppercase())
        }
    }
}
