package io.github.mudrichenkoevgeny.shared.foundation.core.common.network.route

/**
 * Cross-cutting base network route path segments for platform endpoints.
 */
object CommonNetworkRoutes {

    /**
     * Base network route path segment for management operations.
     */
    const val BASE_MANAGEMENT_NETWORK_ROUTE = "/management"

    /**
     * Base network route path segment for self-service staff management operations.
     */
    const val BASE_SELF_MANAGEMENT_NETWORK_ROUTE = "$BASE_MANAGEMENT_NETWORK_ROUTE/self"

    /**
     * Base network route path segment for settings endpoints.
     */
    const val BASE_SETTINGS_NETWORK_ROUTE = "/settings"
}
