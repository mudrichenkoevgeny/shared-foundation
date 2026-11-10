package io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.navigation.params

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params.NavigationScreenParams
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.NavigationScreenParamsParser

/**
 * Simple test implementation of [NavigationScreenParams] for testing URL builders and serializers.
 */
public data class TestNavigationScreenParams(
    public val query: String = "test-value",
    public val id: Int = 1
) : NavigationScreenParams<TestNavigationScreenParams> {
    public override val parser: NavigationScreenParamsParser<TestNavigationScreenParams>
        get() = Companion

    public companion object : NavigationScreenParamsParser<TestNavigationScreenParams> {
        public override fun parse(params: Map<String, Any?>): TestNavigationScreenParams {
            val query = (params["query"] as? Iterable<*>)?.firstOrNull()?.toString()
                ?: params["query"]?.toString()
                ?: "test-value"

            val id = (params["id"] as? Iterable<*>)?.firstOrNull()?.toString()?.toIntOrNull()
                ?: params["id"]?.toString()?.toIntOrNull()
                ?: 1

            return TestNavigationScreenParams(query = query, id = id)
        }

        public override fun serialize(params: TestNavigationScreenParams): Map<String, List<String>> = mapOf(
            "query" to listOf(params.query),
            "id" to listOf(params.id.toString())
        )
    }
}
