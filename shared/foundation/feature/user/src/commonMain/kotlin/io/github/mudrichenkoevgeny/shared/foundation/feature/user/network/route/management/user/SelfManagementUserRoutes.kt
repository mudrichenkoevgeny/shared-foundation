package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.user

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.user.UserDetailsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.user.BaseSelfManagementUserRoutes

/**
 * Route paths for the current authenticated principal's account in the management API (self-service).
 */
object SelfManagementUserRoutes {
    /**
     * **HTTP method:** `GET`
     *
     * Returns the details of the **current** authenticated account used for management tasks.
     *
     * Response body: [UserDetailsPayload].
     */
    const val GET_USER = BaseSelfManagementUserRoutes.GET_USER
}