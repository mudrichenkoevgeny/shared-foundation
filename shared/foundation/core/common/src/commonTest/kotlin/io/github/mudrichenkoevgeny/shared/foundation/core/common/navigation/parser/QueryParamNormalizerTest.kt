package io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser

import kotlin.test.Test
import kotlin.test.assertEquals

class QueryParamNormalizerTest {

    @Test
    fun `normalize handles single string values`() {
        val raw = mapOf(
            "page" to "1",
            "search" to "query"
        )
        val normalized = raw.normalizeQueryParams()

        assertEquals(listOf("1"), normalized["page"])
        assertEquals(listOf("query"), normalized["search"])
    }

    @Test
    fun `normalize handles list and array values`() {
        val raw = mapOf(
            "roles" to listOf("admin", "staff"),
            "statuses" to arrayOf("active", "pending")
        )
        val normalized = raw.normalizeQueryParams()

        assertEquals(listOf("admin", "staff"), normalized["roles"])
        assertEquals(listOf("active", "pending"), normalized["statuses"])
    }

    @Test
    fun `normalize filters out null and blank values`() {
        val raw = mapOf(
            "empty" to "",
            "blank" to "   ",
            "nullVal" to null,
            "mixed" to listOf("valid", "", null, "  ")
        )
        val normalized = raw.normalizeQueryParams()

        assertEquals(emptyList(), normalized["empty"])
        assertEquals(emptyList(), normalized["blank"])
        assertEquals(emptyList(), normalized["nullVal"])
        assertEquals(listOf("valid"), normalized["mixed"])
    }
}
