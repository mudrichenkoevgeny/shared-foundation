package io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.navigation.route

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.ParameterizedNavigationRoute
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.navigation.params.TestNavigationScreenParams

/**
* Simple test implementation of [ParameterizedNavigationRoute] utilizing [TestNavigationScreenParams].
*/
public data class TestParameterizedNavigationRoute(
    public override val route: String = "/test/parameterized",
    public override val params: TestNavigationScreenParams = TestNavigationScreenParams(),
    public override val parentRoute: String? = null
) : ParameterizedNavigationRoute<TestNavigationScreenParams>
