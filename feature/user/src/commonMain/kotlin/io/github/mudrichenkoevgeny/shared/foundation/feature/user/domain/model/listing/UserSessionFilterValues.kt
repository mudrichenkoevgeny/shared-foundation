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
public object UserSessionFilterValues {
    /** Filter by one or more specific session IDs. */
    public const val SESSION_ID: String = UserApiFields.SESSION_ID

    /** Filter by one or more owning user IDs. */
    public const val USER_ID: String = UserApiFields.USER_ID

    /** Filter by one or more [UserRole] wire values (OR semantics). */
    public const val USER_ROLE: String = UserApiFields.USER_ROLE

    /** Filter to exclude specific [UserRole] wire values (NOT IN semantics). */
    public const val EXCLUDE_USER_ROLE: String = "exclude_user_role"

    /** Filter by exact raw or masked login identifier string. */
    public const val IDENTIFIER: String = UserApiFields.IDENTIFIER

    /** Filter by one or more specific identifier IDs used for login. */
    public const val IDENTIFIER_ID: String = UserApiFields.IDENTIFIER_ID

    /** Filter by exact identifier display name. */
    public const val IDENTIFIER_DISPLAY_NAME: String = "identifier_display_name"

    /** Filter by one or more [UserAuthProvider] wire values (OR semantics). */
    public const val IDENTIFIER_AUTH_PROVIDER: String = "identifier_auth_provider"

    /** Filter to exclude specific [UserAuthProvider] wire values (NOT IN semantics). */
    public const val EXCLUDE_IDENTIFIER_AUTH_PROVIDER: String = "exclude_identifier_auth_provider"

    // --- Client Device Info (Flattened per Rule 11.6, in declaration order of ClientDeviceInfo) ---

    /** Filter by exact stable device ID. */
    public const val DEVICE_ID: String = CommonApiFields.DEVICE_ID

    /** Filter by boolean flag indicating whether a stable device ID was provided. */
    public const val IS_DEVICE_ID_SET: String = "is_device_id_set"

    /** Filter by exact device name or model. */
    public const val DEVICE_NAME: String = CommonApiFields.DEVICE_NAME

    /** Filter by boolean flag indicating whether device name is present. */
    public const val IS_DEVICE_NAME_SET: String = "is_device_name_set"

    /** Filter by one or more client types ([ClientType]) (OR semantics). */
    public const val CLIENT_TYPE: String = CommonApiFields.CLIENT_TYPE

    /** Filter to exclude specific client types ([ClientType]) (NOT IN semantics). */
    public const val EXCLUDE_CLIENT_TYPE: String = "exclude_client_type"

    /** Filter by boolean flag indicating whether client type is set. */
    public const val IS_CLIENT_TYPE_SET: String = "is_client_type_set"

    /** Filter by primary BCP 47 language code. */
    public const val LANGUAGE: String = CommonApiFields.LANGUAGE

    /** Filter by boolean flag indicating whether client provided language preference. */
    public const val IS_LANGUAGE_SET: String = "is_language_set"

    /** Filter by exact client application version. */
    public const val APP_VERSION: String = CommonApiFields.APP_VERSION

    /** Filter by boolean flag indicating whether client application version is present. */
    public const val IS_APP_VERSION_SET: String = "is_app_version_set"

    /** Filter by exact operating system version. */
    public const val OPERATION_SYSTEM_VERSION: String = CommonApiFields.OPERATION_SYSTEM_VERSION

    /** Filter by boolean flag indicating whether operating system version is present. */
    public const val IS_OPERATION_SYSTEM_VERSION_SET: String = "is_operation_system_version_set"

    // --- Network & Environment ---

    /** Filter by exact HTTP User-Agent string. */
    public const val USER_AGENT: String = CommonApiFields.USER_AGENT

    /** Filter by boolean flag indicating whether User-Agent is present (false = programmatic/system call). */
    public const val IS_USER_AGENT_SET: String = "is_user_agent_set"

    /** Filter by exact raw or masked IP address. */
    public const val IP_ADDRESS: String = CommonApiFields.IP_ADDRESS

    /** Filter by boolean flag indicating whether IP address is present. */
    public const val IS_IP_ADDRESS_SET: String = "is_ip_address_set"

    // --- Timestamps ---

    /** Inclusive lower bound filter key for session expiration timestamp. */
    public const val EXPIRES_AT_FROM: String = "expires_at_from"

    /** Inclusive upper bound filter key for session expiration timestamp. */
    public const val EXPIRES_AT_TO: String = "expires_at_to"

    /** Inclusive lower bound filter key for last accessed timestamp. */
    public const val LAST_ACCESSED_AT_FROM: String = "last_accessed_at_from"

    /** Inclusive upper bound filter key for last accessed timestamp. */
    public const val LAST_ACCESSED_AT_TO: String = "last_accessed_at_to"

    /** Inclusive lower bound filter key for last re-authentication timestamp. */
    public const val LAST_REAUTHENTICATED_AT_FROM: String = "last_reauthenticated_at_from"

    /** Inclusive upper bound filter key for last re-authentication timestamp. */
    public const val LAST_REAUTHENTICATED_AT_TO: String = "last_reauthenticated_at_to"

    /** Inclusive lower bound filter key for creation timestamp. */
    public const val CREATED_AT_FROM: String = CommonFilterValues.CREATED_AT_FROM

    /** Inclusive upper bound filter key for creation timestamp. */
    public const val CREATED_AT_TO: String = CommonFilterValues.CREATED_AT_TO

    /** Inclusive lower bound filter key for update timestamp. */
    public const val UPDATED_AT_FROM: String = CommonFilterValues.UPDATED_AT_FROM

    /** Inclusive upper bound filter key for update timestamp. */
    public const val UPDATED_AT_TO: String = CommonFilterValues.UPDATED_AT_TO

    /** Filter by boolean flag indicating whether the session record has been modified. */
    public const val IS_UPDATED_AT_SET: String = "is_updated_at_set"

    /**
     * Free-text search query string targeting:
     * - Raw or masked login identifier ([UserSessionPrivate.identifier]).
     * - Identifier display name ([UserSessionPrivate.identifierDisplayName]).
     * - Device name ([ClientDeviceInfo.deviceName]).
     */
    public const val SEARCH_QUERY: String = ListingParamNames.Search.SEARCH_QUERY
}
