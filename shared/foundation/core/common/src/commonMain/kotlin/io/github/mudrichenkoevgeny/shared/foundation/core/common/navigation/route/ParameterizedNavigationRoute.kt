package io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params.NavigationScreenParams

/**
 * Contract for a navigation destination carrying typed screen parameters.
 */
public interface ParameterizedNavigationRoute<P : NavigationScreenParams<*>> : NavigationRoute {
    /** Strongly typed screen parameters. */
    public val params: P
}
