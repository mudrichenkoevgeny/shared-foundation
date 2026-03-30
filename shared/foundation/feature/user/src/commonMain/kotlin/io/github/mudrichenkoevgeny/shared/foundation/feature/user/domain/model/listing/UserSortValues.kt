package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.CommonSortValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.identifier.UserIdentifierMaskedPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.identifier.UserIdentifierUnmaskedPayload

object UserSortValues {
    /**
     * Supported `sort_by` values for user list endpoints.
     */
    object UserSortBy {
        /** Sort by [UserApiFields.LAST_LOGIN_AT]. */
        const val LAST_LOGIN_AT = UserApiFields.LAST_LOGIN_AT

        /** Sort by [UserApiFields.LAST_ACTIVE_AT]. */
        const val LAST_ACTIVE_AT = UserApiFields.LAST_ACTIVE_AT

        /** Sort by [UserApiFields.SCHEDULED_PERMANENT_DELETION_AT]. */
        const val SCHEDULED_PERMANENT_DELETION_AT = UserApiFields.SCHEDULED_PERMANENT_DELETION_AT

        /** Sort by creation timestamp. */
        const val CREATED_AT = CommonSortValues.TimestampSortBy.CREATED_AT

        /** Sort by update timestamp. */
        const val UPDATED_AT = CommonSortValues.TimestampSortBy.UPDATED_AT
    }

    /**
     * Supported `sort_by` values for user identifier list endpoints, aligned with timestamp fields on
     * [UserIdentifierUnmaskedPayload] (`created_at`, `updated_at` only). [UserIdentifierMaskedPayload] uses the same
     * wire names for those fields.
     */
    object UserIdentifierSortBy {
        /** Sort by creation timestamp. */
        const val CREATED_AT = CommonSortValues.TimestampSortBy.CREATED_AT

        /** Sort by update timestamp. */
        const val UPDATED_AT = CommonSortValues.TimestampSortBy.UPDATED_AT
    }

    /**
     * Supported `sort_by` wire values for **session list** endpoints (open and management), aligned with session DTO
     * timestamp fields (`expires_at`, `last_accessed_at`, `last_reauthenticated_at`, `created_at`, `updated_at`).
     */
    object UserSessionSortBy {
        /** Sort by [UserApiFields.EXPIRES_AT] (`expires_at`). */
        const val EXPIRES_AT = UserApiFields.EXPIRES_AT

        /** Sort by [UserApiFields.LAST_ACCESSED_AT] (`last_accessed_at`). */
        const val LAST_ACCESSED_AT = UserApiFields.LAST_ACCESSED_AT

        /** Sort by [UserApiFields.LAST_REAUTHENTICATED_AT] (`last_reauthenticated_at`). */
        const val LAST_REAUTHENTICATED_AT = UserApiFields.LAST_REAUTHENTICATED_AT

        /** Sort by creation timestamp ([CommonSortValues.TimestampSortBy.CREATED_AT]). */
        const val CREATED_AT = CommonSortValues.TimestampSortBy.CREATED_AT

        /** Sort by update timestamp ([CommonSortValues.TimestampSortBy.UPDATED_AT]). */
        const val UPDATED_AT = CommonSortValues.TimestampSortBy.UPDATED_AT
    }
}