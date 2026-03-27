package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.configuration

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.response.configuration.UserConfigurationResponse
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.configuration.BaseUserConfigurationRoutes

/**
 * Route paths for user configuration in the public API.
 */
object OpenUserConfigurationRoutes {
    /**
     * **HTTP method:** `GET`
     *
     * Response body: [UserConfigurationResponse].
     */
    const val GET_CONFIGURATION = BaseUserConfigurationRoutes.BASE_USER_CONFIGURATION_ROUTE
}