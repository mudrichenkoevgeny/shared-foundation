package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.configuration

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.configuration.UserConfigurationPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.configuration.BaseUserConfigurationRoutes

/**
 * Route paths for user configuration in the public API.
 */
object OpenUserConfigurationRoutes {
    /**
     * **HTTP method:** `GET`
     *
     * Response body: [UserConfigurationPayload].
     */
    const val GET_CONFIGURATION = BaseUserConfigurationRoutes.BASE_USER_CONFIGURATION_ROUTE
}