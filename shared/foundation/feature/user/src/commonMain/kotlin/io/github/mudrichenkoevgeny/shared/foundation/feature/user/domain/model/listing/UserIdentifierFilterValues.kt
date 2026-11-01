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
object UserIdentifierFilterValues {
    /** Filter by one or more specific identifier IDs. */
    const val IDENTIFIER_ID = UserApiFields.IDENTIFIER_ID

    /** Filter by one or more owning user IDs. */
    const val USER_ID = UserApiFields.USER_ID

    /** Filter by one or more [UserAuthProvider] wire values (OR semantics). */
    const val USER_AUTH_PROVIDER = UserApiFields.USER_AUTH_PROVIDER

    /** Filter to exclude specific [UserAuthProvider] wire values (NOT IN semantics). */
    const val EXCLUDE_USER_AUTH_PROVIDER = "exclude_user_auth_provider"

    /** Filter by exact raw or masked login identifier string. */
    const val IDENTIFIER = UserApiFields.IDENTIFIER

    /** Filter by exact display name. */
    const val DISPLAY_NAME = UserApiFields.DISPLAY_NAME

    /** Filter by exact external provider verified email address. */
    const val EXTERNAL_PROVIDER_EMAIL = UserApiFields.EXTERNAL_PROVIDER_EMAIL

    /** Filter by boolean flag indicating whether an external provider email is associated. */
    const val IS_EXTERNAL_PROVIDER_EMAIL_SET = "is_external_provider_email_set"

    /** Inclusive lower bound filter key for creation timestamp. */
    const val CREATED_AT_FROM = CommonFilterValues.CREATED_AT_FROM

    /** Inclusive upper bound filter key for creation timestamp. */
    const val CREATED_AT_TO = CommonFilterValues.CREATED_AT_TO

    /** Inclusive lower bound filter key for update timestamp. */
    const val UPDATED_AT_FROM = CommonFilterValues.UPDATED_AT_FROM

    /** Inclusive upper bound filter key for update timestamp. */
    const val UPDATED_AT_TO = CommonFilterValues.UPDATED_AT_TO

    /** Filter by boolean flag indicating whether the identifier record has been modified. */
    const val IS_UPDATED_AT_SET = "is_updated_at_set"

    /**
     * Free-text search query string targeting:
     * - Raw login identifier ([UserIdentifierPrivate.identifier]).
     * - Display name ([UserIdentifierPrivate.displayName]).
     * - External provider email ([UserIdentifierPrivate.externalProviderEmail]).
     */
    const val SEARCH_QUERY = ListingParamNames.Search.SEARCH_QUERY
}
