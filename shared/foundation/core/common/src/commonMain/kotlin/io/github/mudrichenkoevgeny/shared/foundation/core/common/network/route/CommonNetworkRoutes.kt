package io.github.mudrichenkoevgeny.shared.foundation.core.common.network.route

/**
 * Cross-cutting base network route path segments for platform endpoints.
 */
public object CommonNetworkRoutes {
    /**
     * Base network route path segment for management operations.
     */
    public const val BASE_MANAGEMENT_NETWORK_ROUTE: String = "/management"

    /**
     * Base network route path segment for self-service staff management operations.
     */
    public const val BASE_SELF_MANAGEMENT_NETWORK_ROUTE: String = "$BASE_MANAGEMENT_NETWORK_ROUTE/self"

    /**
     * Base network route path segment for settings endpoints.
     */
    public const val BASE_SETTINGS_NETWORK_ROUTE: String = "/settings"
}
