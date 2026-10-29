package io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.localization

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class LocalizedStringResolverMockTest {

    @Test
    fun resolvesFormattedStringWithPrefix() {
        val resolver = LocalizedStringResolverMock(prefix = "[Mock]")

        val result = resolver.resolve(
            module = "core/common",
            file = "errors",
            key = "unknown_error",
            locale = "en-US"
        )

        assertEquals("[Mock] en-US:core/common:errors:unknown_error", result)
    }

    @Test
    fun returnsNullWhenKeyMatchesConfiguredNullKey() {
        val nullKey = "missing_key"
        val resolver = LocalizedStringResolverMock(nullKey = nullKey)

        val result = resolver.resolve(
            module = "core/common",
            file = "errors",
            key = nullKey,
            locale = "en-US"
        )

        assertNull(result)
    }
}
