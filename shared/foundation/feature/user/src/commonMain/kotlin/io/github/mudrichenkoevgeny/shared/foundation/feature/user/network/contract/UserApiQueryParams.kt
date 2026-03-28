package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.UserClientType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.response.session.UserSessionUnmaskedResponse

/**
 * User-specific naming conventions for API query string parameters.
 */
object UserApiQueryParams {
    /**
     * Query parameter names used for token-related endpoints.
     */
    object TokenQueryParams {
        /** Authentication token value. */
        const val TOKEN = "token"
    }

    /**
     * Query parameter names used to filter user collections.
     */
    object UserQueryParams {
        /** Filter by [UserRole]. */
        const val ROLE = "role"

        /** Filter by [UserAccountStatus]. */
        const val ACCOUNT_STATUS = "account_status"

        /** Filter by stored pre-deletion [UserAccountStatus] ([UserApiFields.ACCOUNT_STATUS_BEFORE_DELETION]). */
        const val ACCOUNT_STATUS_BEFORE_DELETION = UserApiFields.ACCOUNT_STATUS_BEFORE_DELETION
    }

    /**
     * Query parameter names used for user identifier search and filtering.
     */
    object UserIdentifierQueryParams {
        /** User id filter. */
        const val USER_ID = UserApiFields.USER_ID

        /** Filter by [UserAuthProvider]. */
        const val USER_AUTH_PROVIDER = UserApiFields.USER_AUTH_PROVIDER

        /** Raw user identifier filter value. */
        const val IDENTIFIER = UserApiFields.IDENTIFIER
    }

    /**
     * Query parameter names used for user session search and filtering.
     */
    object UserSessionQueryParams {
        /** User id filter. */
        const val USER_ID = UserApiFields.USER_ID

        /** Filter by identifier id. */
        const val IDENTIFIER_ID = UserApiFields.IDENTIFIER_ID

        /** Filter by [UserAuthProvider]. */
        const val USER_AUTH_PROVIDER = UserApiFields.USER_AUTH_PROVIDER

        /** Filter by session revoked flag; wire values `true` or `false`. */
        const val REVOKED = UserApiFields.REVOKED

        /** Filter by [UserClientType]. */
        const val CLIENT_TYPE = CommonApiFields.CLIENT_TYPE

        /** Filter by user agent string. */
        const val USER_AGENT = CommonApiFields.USER_AGENT

        /** Filter by IP address. */
        const val IP_ADDRESS = CommonApiFields.IP_ADDRESS

        /** Filter by language tag. */
        const val LANGUAGE = CommonApiFields.LANGUAGE

        /** Filter by stable device id ([CommonApiFields.DEVICE_ID]); repeat key for OR if supported. */
        const val DEVICE_ID = CommonApiFields.DEVICE_ID

        /** Filter by device name. */
        const val DEVICE_NAME = CommonApiFields.DEVICE_NAME

        /** Filter by application version. */
        const val APP_VERSION = CommonApiFields.APP_VERSION

        /** Filter by operating system version. */
        const val OPERATION_SYSTEM_VERSION = CommonApiFields.OPERATION_SYSTEM_VERSION

        /**
         * Raw login / identifier value filter; not a field on [UserSessionUnmaskedResponse]; server-defined matching; repeat key for OR if supported.
         */
        const val IDENTIFIER = UserApiFields.IDENTIFIER
    }
}