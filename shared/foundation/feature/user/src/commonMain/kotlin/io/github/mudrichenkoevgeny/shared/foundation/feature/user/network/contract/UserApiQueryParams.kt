package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserIdentifierFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSessionFilterValues

/**
 * User-specific query string parameters that are **not** list filters (see [UserFilterValues], [UserIdentifierFilterValues],
 * and [UserSessionFilterValues] for user, identifier, and session list filter keys).
 */
public object UserApiQueryParams {
    /** Authentication token value (for example WebSocket URL query). */
    public const val ACCESS_TOKEN: String = "token"
}
