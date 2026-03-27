package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiQueryParamValues

object UserApiQueryParamValues {
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
        const val CREATED_AT = CommonApiQueryParamValues.TimestampSortBy.CREATED_AT

        /** Sort by update timestamp. */
        const val UPDATED_AT = CommonApiQueryParamValues.TimestampSortBy.UPDATED_AT
    }

    /**
     * Supported `sort_by` values for user identifier endpoints.
     */
    object UserIdentifierSortBy {
        /** Sort by creation timestamp. */
        const val CREATED_AT = CommonApiQueryParamValues.TimestampSortBy.CREATED_AT

        /** Sort by update timestamp. */
        const val UPDATED_AT = CommonApiQueryParamValues.TimestampSortBy.UPDATED_AT
    }

    /**
     * Supported `sort_by` values for user session endpoints.
     */
    object UserSessionSortBy {
        /** Sort by [UserApiFields.EXPIRES_AT]. */
        const val EXPIRES_AT = UserApiFields.EXPIRES_AT

        /** Sort by [UserApiFields.LAST_ACCESSED_AT]. */
        const val LAST_ACCESSED_AT = UserApiFields.LAST_ACCESSED_AT

        /** Sort by creation timestamp. */
        const val CREATED_AT = CommonApiQueryParamValues.TimestampSortBy.CREATED_AT

        /** Sort by update timestamp. */
        const val UPDATED_AT = CommonApiQueryParamValues.TimestampSortBy.UPDATED_AT
    }
}