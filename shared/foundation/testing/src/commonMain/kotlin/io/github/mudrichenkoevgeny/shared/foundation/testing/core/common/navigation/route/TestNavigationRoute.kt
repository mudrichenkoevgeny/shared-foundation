package io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.navigation.route

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.NavigationRoute

/**
 * Simple test implementation of [NavigationRoute] for unit testing routers and event trackers.
 */
data class TestNavigationRoute(
    override val route: String = "/test/route",
    override val parentRoute: String? = null
) : NavigationRoute