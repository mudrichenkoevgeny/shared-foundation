package io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params.NavigationScreenParams
import kotlin.test.Test
import kotlin.test.assertEquals

class NavigationRouteUrlMapperTest {

    private object DummyRoute : NavigationRoute {
        override val route: String = "/test/path"
        override val parentRoute: String? = null
    }

    private class DummyParams(
        val email: String = "test+1@example.com",
        val search: String = "hello world"
    ) : NavigationScreenParams {
        override fun toQueryMap(): Map<String, List<String>> = mapOf(
            "email" to listOf(email),
            "search" to listOf(search)
        )
    }

    private class DummyParameterizedRoute(
        override val params: DummyParams = DummyParams()
    ) : ParameterizedNavigationRoute<DummyParams> {
        override val route: String = "/test/search"
        override val parentRoute: String? = null
    }

    @Test
    fun `toFullUrl formats non-parameterized route correctly`() {
        val url = DummyRoute.toFullUrl("http://localhost:8080")
        assertEquals("http://localhost:8080/test/path", url)
    }

    @Test
    fun `toFullUrl formats parameterized route with URL encoding`() {
        val route = DummyParameterizedRoute()
        val url = route.toFullUrl("https://example.com")
        assertEquals("https://example.com/test/search?email=test%2B1%40example.com&search=hello%20world", url)
    }
}
