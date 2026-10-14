package io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route

/**
 * Common base interface for all strongly typed navigation screen destinations across the platform.
 */
interface NavigationRoute {

    /** Route path for this screen destination (concrete formatted path if parameterized). */
    val route: String

    /** Parent navigation route for fallback navigation and breadcrumbs, or null if root destination. */
    val parentRoute: String?
}
