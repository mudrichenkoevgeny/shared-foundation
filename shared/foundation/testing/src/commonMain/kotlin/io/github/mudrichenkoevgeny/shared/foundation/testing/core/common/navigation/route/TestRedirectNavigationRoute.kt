package io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.navigation.route

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.RedirectNavigationRoute

/**
 * Simple test implementation of [RedirectNavigationRoute] for testing redirection logic.
 */
data class TestRedirectNavigationRoute(
    override val route: String = "/test/redirect",
    override val targetRoute: String = "/test/target",
    override val parentRoute: String? = null
) : RedirectNavigationRoute