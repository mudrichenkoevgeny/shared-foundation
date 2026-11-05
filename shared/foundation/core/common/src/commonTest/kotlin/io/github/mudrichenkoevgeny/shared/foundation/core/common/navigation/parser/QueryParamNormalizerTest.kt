package io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser

import kotlin.test.Test
import kotlin.test.assertEquals

class QueryParamNormalizerTest {
    @Test
    fun `normalize handles single string values`() {
        val raw =
            mapOf(
                "page" to "1",
                "search" to "query"
            )
        val normalized = raw.normalizeQueryParams()

        assertEquals(listOf("1"), normalized["page"])
        assertEquals(listOf("query"), normalized["search"])
    }

    @Test
    fun `normalize handles list and array values`() {
        val raw =
            mapOf(
                "roles" to listOf("admin", "staff"),
                "statuses" to arrayOf("active", "pending")
            )
        val normalized = raw.normalizeQueryParams()

        assertEquals(listOf("admin", "staff"), normalized["roles"])
        assertEquals(listOf("active", "pending"), normalized["statuses"])
    }

    @Test
    fun `normalize filters out null and blank values`() {
        val raw =
            mapOf(
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

    @Test
    fun `normalize handles unknown object types by converting to string`() {
        val obj =
            object {
                override fun toString() = "custom_obj"
            }
        val blankObj =
            object {
                override fun toString() = "   "
            }

        val raw =
            mapOf(
                "custom" to obj,
                "blankCustom" to blankObj
            )
        val normalized = raw.normalizeQueryParams()

        assertEquals(listOf("custom_obj"), normalized["custom"])
        assertEquals(emptyList(), normalized["blankCustom"])
    }

    @Test
    fun `normalize handles primitive arrays properly`() {
        val raw =
            mapOf(
                "ints" to intArrayOf(1, 2, 3),
                "bools" to booleanArrayOf(true, false),
                "floats" to floatArrayOf(1.1f, 2.2f),
                "doubles" to doubleArrayOf(1.1, 2.2),
                "longs" to longArrayOf(10L, 20L)
            )
        val normalized = raw.normalizeQueryParams()

        assertEquals(listOf("1", "2", "3"), normalized["ints"])
        assertEquals(listOf("true", "false"), normalized["bools"])
        assertEquals(listOf("1.1", "2.2"), normalized["floats"])
        assertEquals(listOf("1.1", "2.2"), normalized["doubles"])
        assertEquals(listOf("10", "20"), normalized["longs"])
    }
}
