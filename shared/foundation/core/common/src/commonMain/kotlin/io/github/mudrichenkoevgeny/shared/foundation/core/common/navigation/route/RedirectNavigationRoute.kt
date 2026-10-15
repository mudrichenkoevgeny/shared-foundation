package io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route

/**
 * A navigation route destination contract representing an alias or virtual endpoint
 * that immediately delegates and redirects navigation to another concrete target path.
 *
 * Implementations do not render dedicated UI screens of their own; instead, navigation
 * processors and routers inspect this contract to forward the client to [targetRoute].
 */
interface RedirectNavigationRoute : NavigationRoute {

    /**
     * The concrete target route path string to which navigation is redirected.
     */
    val targetRoute: String
}