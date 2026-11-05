package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.CommonFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierPrivate
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields

/**
 * Stable **keys** for filtering user identifier list endpoints.
 *
 * Keys follow the declaration order of properties in [UserIdentifierPrivate].
 * Use these constants when building HTTP query strings, parsing list requests, or mapping filters in services and
 * persistence so clients, servers, and documentation stay aligned.
 */
public object UserIdentifierFilterValues {
    /** Filter by one or more specific identifier IDs. */
    public const val IDENTIFIER_ID: String = UserApiFields.IDENTIFIER_ID

    /** Filter by one or more owning user IDs. */
    public const val USER_ID: String = UserApiFields.USER_ID

    /** Filter by one or more [UserAuthProvider] wire values (OR semantics). */
    public const val USER_AUTH_PROVIDER: String = UserApiFields.USER_AUTH_PROVIDER

    /** Filter to exclude specific [UserAuthProvider] wire values (NOT IN semantics). */
    public const val EXCLUDE_USER_AUTH_PROVIDER: String = "exclude_user_auth_provider"

    /** Filter by exact raw or masked login identifier string. */
    public const val IDENTIFIER: String = UserApiFields.IDENTIFIER

    /** Filter by exact display name. */
    public const val DISPLAY_NAME: String = UserApiFields.DISPLAY_NAME

    /** Filter by exact external provider verified email address. */
    public const val EXTERNAL_PROVIDER_EMAIL: String = UserApiFields.EXTERNAL_PROVIDER_EMAIL

    /** Filter by boolean flag indicating whether an external provider email is associated. */
    public const val IS_EXTERNAL_PROVIDER_EMAIL_SET: String = "is_external_provider_email_set"

    /** Inclusive lower bound filter key for creation timestamp. */
    public const val CREATED_AT_FROM: String = CommonFilterValues.CREATED_AT_FROM

    /** Inclusive upper bound filter key for creation timestamp. */
    public const val CREATED_AT_TO: String = CommonFilterValues.CREATED_AT_TO

    /** Inclusive lower bound filter key for update timestamp. */
    public const val UPDATED_AT_FROM: String = CommonFilterValues.UPDATED_AT_FROM

    /** Inclusive upper bound filter key for update timestamp. */
    public const val UPDATED_AT_TO: String = CommonFilterValues.UPDATED_AT_TO

    /** Filter by boolean flag indicating whether the identifier record has been modified. */
    public const val IS_UPDATED_AT_SET: String = "is_updated_at_set"

    /**
     * Free-text search query string targeting:
     * - Raw login identifier ([UserIdentifierPrivate.identifier]).
     * - Display name ([UserIdentifierPrivate.displayName]).
     * - External provider email ([UserIdentifierPrivate.externalProviderEmail]).
     */
    public const val SEARCH_QUERY: String = ListingParamNames.Search.SEARCH_QUERY
}
