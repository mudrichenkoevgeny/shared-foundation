package io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params.NavigationScreenParams

/**
 * Contract for a navigation destination carrying typed screen parameters.
 */
interface ParameterizedNavigationRoute<P : NavigationScreenParams<*>> : NavigationRoute {

    /** Strongly typed screen parameters. */
    val params: P
}
