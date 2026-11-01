package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientDeviceInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientType
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.CommonFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionPrivate
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields

/**
 * Stable **keys** for filtering user session list endpoints.
 *
 * Keys follow the declaration order of properties in [UserSessionPrivate].
 * Use these constants when building HTTP query strings, parsing list requests, or mapping filters in services and
 * persistence so clients, servers, and documentation stay aligned.
 */
object UserSessionFilterValues {
    /** Filter by one or more specific session IDs. */
    const val SESSION_ID = UserApiFields.SESSION_ID

    /** Filter by one or more owning user IDs. */
    const val USER_ID = UserApiFields.USER_ID

    /** Filter by one or more [UserRole] wire values (OR semantics). */
    const val USER_ROLE = UserApiFields.USER_ROLE

    /** Filter to exclude specific [UserRole] wire values (NOT IN semantics). */
    const val EXCLUDE_USER_ROLE = "exclude_user_role"

    /** Filter by exact raw or masked login identifier string. */
    const val IDENTIFIER = UserApiFields.IDENTIFIER

    /** Filter by one or more specific identifier IDs used for login. */
    const val IDENTIFIER_ID = UserApiFields.IDENTIFIER_ID

    /** Filter by exact identifier display name. */
    const val IDENTIFIER_DISPLAY_NAME = "identifier_display_name"

    /** Filter by one or more [UserAuthProvider] wire values (OR semantics). */
    const val IDENTIFIER_AUTH_PROVIDER = "identifier_auth_provider"

    /** Filter to exclude specific [UserAuthProvider] wire values (NOT IN semantics). */
    const val EXCLUDE_IDENTIFIER_AUTH_PROVIDER = "exclude_identifier_auth_provider"

    // --- Client Device Info (Flattened per Rule 11.6, in declaration order of ClientDeviceInfo) ---

    /** Filter by exact stable device ID. */
    const val DEVICE_ID = CommonApiFields.DEVICE_ID

    /** Filter by boolean flag indicating whether a stable device ID was provided. */
    const val IS_DEVICE_ID_SET = "is_device_id_set"

    /** Filter by exact device name or model. */
    const val DEVICE_NAME = CommonApiFields.DEVICE_NAME

    /** Filter by boolean flag indicating whether device name is present. */
    const val IS_DEVICE_NAME_SET = "is_device_name_set"

    /** Filter by one or more client types ([ClientType]) (OR semantics). */
    const val CLIENT_TYPE = CommonApiFields.CLIENT_TYPE

    /** Filter to exclude specific client types ([ClientType]) (NOT IN semantics). */
    const val EXCLUDE_CLIENT_TYPE = "exclude_client_type"

    /** Filter by boolean flag indicating whether client type is set. */
    const val IS_CLIENT_TYPE_SET = "is_client_type_set"

    /** Filter by primary BCP 47 language code. */
    const val LANGUAGE = CommonApiFields.LANGUAGE

    /** Filter by boolean flag indicating whether client provided language preference. */
    const val IS_LANGUAGE_SET = "is_language_set"

    /** Filter by exact client application version. */
    const val APP_VERSION = CommonApiFields.APP_VERSION

    /** Filter by boolean flag indicating whether client application version is present. */
    const val IS_APP_VERSION_SET = "is_app_version_set"

    /** Filter by exact operating system version. */
    const val OPERATION_SYSTEM_VERSION = CommonApiFields.OPERATION_SYSTEM_VERSION

    /** Filter by boolean flag indicating whether operating system version is present. */
    const val IS_OPERATION_SYSTEM_VERSION_SET = "is_operation_system_version_set"

    // --- Network & Environment ---

    /** Filter by exact HTTP User-Agent string. */
    const val USER_AGENT = CommonApiFields.USER_AGENT

    /** Filter by boolean flag indicating whether User-Agent is present (false = programmatic/system call). */
    const val IS_USER_AGENT_SET = "is_user_agent_set"

    /** Filter by exact raw or masked IP address. */
    const val IP_ADDRESS = CommonApiFields.IP_ADDRESS

    /** Filter by boolean flag indicating whether IP address is present. */
    const val IS_IP_ADDRESS_SET = "is_ip_address_set"

    // --- Timestamps ---

    /** Inclusive lower bound filter key for session expiration timestamp. */
    const val EXPIRES_AT_FROM = "expires_at_from"

    /** Inclusive upper bound filter key for session expiration timestamp. */
    const val EXPIRES_AT_TO = "expires_at_to"

    /** Inclusive lower bound filter key for last accessed timestamp. */
    const val LAST_ACCESSED_AT_FROM = "last_accessed_at_from"

    /** Inclusive upper bound filter key for last accessed timestamp. */
    const val LAST_ACCESSED_AT_TO = "last_accessed_at_to"

    /** Inclusive lower bound filter key for last re-authentication timestamp. */
    const val LAST_REAUTHENTICATED_AT_FROM = "last_reauthenticated_at_from"

    /** Inclusive upper bound filter key for last re-authentication timestamp. */
    const val LAST_REAUTHENTICATED_AT_TO = "last_reauthenticated_at_to"

    /** Inclusive lower bound filter key for creation timestamp. */
    const val CREATED_AT_FROM = CommonFilterValues.CREATED_AT_FROM

    /** Inclusive upper bound filter key for creation timestamp. */
    const val CREATED_AT_TO = CommonFilterValues.CREATED_AT_TO

    /** Inclusive lower bound filter key for update timestamp. */
    const val UPDATED_AT_FROM = CommonFilterValues.UPDATED_AT_FROM

    /** Inclusive upper bound filter key for update timestamp. */
    const val UPDATED_AT_TO = CommonFilterValues.UPDATED_AT_TO

    /** Filter by boolean flag indicating whether the session record has been modified. */
    const val IS_UPDATED_AT_SET = "is_updated_at_set"

    /**
     * Free-text search query string targeting:
     * - Raw or masked login identifier ([UserSessionPrivate.identifier]).
     * - Identifier display name ([UserSessionPrivate.identifierDisplayName]).
     * - Device name ([ClientDeviceInfo.deviceName]).
     */
    const val SEARCH_QUERY = ListingParamNames.Search.SEARCH_QUERY
}
