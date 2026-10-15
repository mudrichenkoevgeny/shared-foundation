package io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params.NavigationScreenParams
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.NavigationScreenParamsParser
import kotlin.test.Test
import kotlin.test.assertEquals

class NavigationRouteUrlMapperTest {

    private object MockNavigationRoute : NavigationRoute {

        override val route: String
            get() = "/test/path"

        override val parentRoute: String?
            get() = null
    }

    private class MockNavigationParams(
        val email: String = "test+1@example.com",
        val search: String = "hello world"
    ) : NavigationScreenParams<MockNavigationParams> {

        override val parser: NavigationScreenParamsParser<MockNavigationParams>
            get() = throw UnsupportedOperationException()

        override fun toQueryMap(): Map<String, List<String>> = mapOf(
            "email" to listOf(email),
            "search" to listOf(search)
        )
    }

    private class MockParameterizedNavigationRoute(
        override val params: MockNavigationParams = MockNavigationParams()
    ) : ParameterizedNavigationRoute<MockNavigationParams> {

        override val route: String
            get() = "/test/search"

        override val parentRoute: String?
            get() = null
    }

    @Test
    fun `toFullUrl formats non-parameterized route correctly`() {
        val url = MockNavigationRoute.toFullUrl("http://localhost:8080")
        assertEquals("http://localhost:8080/test/path", url)
    }

    @Test
    fun `toFullUrl formats parameterized route with URL encoding`() {
        val route = MockParameterizedNavigationRoute()
        val url = route.toFullUrl("https://example.com")
        assertEquals("https://example.com/test/search?email=test%2B1%40example.com&search=hello%20world", url)
    }
}
