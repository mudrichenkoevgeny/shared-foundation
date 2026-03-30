package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserFilterValues

/**
 * User-specific query string parameters that are **not** list filters (see [UserFilterValues] for user, identifier, and
 * session list filter keys).
 */
object UserApiQueryParams {

    /** Authentication token value (for example WebSocket URL query). */
    const val ACCESS_TOKEN = "token"
}
