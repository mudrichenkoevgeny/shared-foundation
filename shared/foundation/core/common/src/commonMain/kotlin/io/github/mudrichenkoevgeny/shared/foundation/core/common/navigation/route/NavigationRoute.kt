package io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route

/**
 * Common base interface for all strongly typed navigation screen destinations across the platform.
 */
public interface NavigationRoute {
    /** Route path for this screen destination (concrete formatted path if parameterized). */
    public val route: String

    /** Parent navigation route for fallback navigation and breadcrumbs, or null if root destination. */
    public val parentRoute: String?
}
