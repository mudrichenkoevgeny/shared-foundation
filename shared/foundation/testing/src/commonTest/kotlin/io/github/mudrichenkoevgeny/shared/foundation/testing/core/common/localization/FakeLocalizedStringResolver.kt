package io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.localization

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class FakeLocalizedStringResolverTest {

    @Test
    fun resolvesFormattedStringWithPrefix() {
        val resolver = FakeLocalizedStringResolver(prefix = "[Mock]")

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
        val resolver = FakeLocalizedStringResolver(nullKey = nullKey)

        val result = resolver.resolve(
            module = "core/common",
            file = "errors",
            key = nullKey,
            locale = "en-US"
        )

        assertNull(result)
    }
}