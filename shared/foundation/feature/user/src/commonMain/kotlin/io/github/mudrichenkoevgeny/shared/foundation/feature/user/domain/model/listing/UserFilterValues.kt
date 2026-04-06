package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientType
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.session.UserSessionPayload

/**
 * Stable **keys** for filtering user-related listings (users, identifiers, sessions).
 *
 * Use these constants when building HTTP query strings, parsing list requests, or mapping filters in services and
 * persistence so clients, servers, and documentation stay aligned.
 */
object UserFilterValues {

    /**
     * Keys used to filter user collection list endpoints.
     */
    object UserFilterValues {
        /** Filter by [UserRole]. */
        const val ROLE = "role"

        /** Filter by [UserAccountStatus]. */
        const val ACCOUNT_STATUS = "account_status"

        /** Filter by stored pre-deletion [UserAccountStatus] ([UserApiFields.ACCOUNT_STATUS_BEFORE_DELETION]). */
        const val ACCOUNT_STATUS_BEFORE_DELETION = UserApiFields.ACCOUNT_STATUS_BEFORE_DELETION
    }

    /**
     * Keys used to filter user identifier list endpoints.
     */
    object UserIdentifierFilterValues {
        /** User id filter. */
        const val USER_ID = UserApiFields.USER_ID

        /** Filter by [UserAuthProvider]. */
        const val USER_AUTH_PROVIDER = UserApiFields.USER_AUTH_PROVIDER

        /** Raw user identifier filter value. */
        const val IDENTIFIER = UserApiFields.IDENTIFIER
    }

    /**
     * Keys used to filter user session list endpoints.
     */
    object UserSessionFilterValues {
        /** User id filter. */
        const val USER_ID = UserApiFields.USER_ID

        /** Filter by identifier id. */
        const val IDENTIFIER_ID = UserApiFields.IDENTIFIER_ID

        /** Filter by [UserAuthProvider]. */
        const val USER_AUTH_PROVIDER = UserApiFields.USER_AUTH_PROVIDER

        /** Filter by session revoked flag; wire values `true` or `false`. */
        const val REVOKED = UserApiFields.REVOKED

        /** Filter by [ClientType]. */
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
         * Raw login / identifier value filter; not a DTO field on [UserSessionPayload]; server-defined matching; repeat key for OR if supported.
         */
        const val IDENTIFIER = UserApiFields.IDENTIFIER
    }
}
