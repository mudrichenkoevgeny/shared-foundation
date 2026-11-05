package io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.navigation.route

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.RedirectNavigationRoute

/**
* Simple test implementation of [RedirectNavigationRoute] for testing redirection logic.
*/
public data class TestRedirectNavigationRoute(
    public override val route: String = "/test/redirect",
    public override val targetRoute: String = "/test/target",
    public override val parentRoute: String? = null
) : RedirectNavigationRoute
