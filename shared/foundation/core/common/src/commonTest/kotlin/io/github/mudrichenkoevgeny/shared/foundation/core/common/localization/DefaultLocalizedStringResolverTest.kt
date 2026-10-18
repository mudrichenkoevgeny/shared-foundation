package io.github.mudrichenkoevgeny.shared.foundation.core.common.localization

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

class DefaultLocalizedStringResolverTest {

    @Test
    fun testResolvesStringWithFallback() {
        val resolver = DefaultLocalizedStringResolver(
            resourceLoader = { path ->
                when (path) {
                    "localization/core/common/en/errors.json" -> """{"unknown":"Unknown error","not_found":"Not found"}"""
                    "localization/core/common/ru/errors.json" -> """{"unknown":"Неизвестная ошибка"}"""
                    else -> null
                }
            },
            defaultLocale = CommonLocales.ENGLISH
        )

        assertEquals("Неизвестная ошибка", resolver.resolve("core/common", "errors", "unknown", "ru"))
        assertEquals("Not found", resolver.resolve("core/common", "errors", "not_found", "ru"))
        assertEquals("Unknown error", resolver.resolve("core/common", "errors", "unknown", "fr"))
        assertNull(resolver.resolve("core/common", "errors", "non_existent_key", "ru"))
    }

    @Test
    fun testThrowsExceptionOnMalformedJson() {
        val resolver = DefaultLocalizedStringResolver(
            resourceLoader = { path ->
                if (path == "localization/core/common/ru/errors.json") {
                    """{ "unknown": "Malformed JSON missing closing brace"""
                } else {
                    null
                }
            }
        )

        assertFailsWith<IllegalStateException> {
            resolver.resolve("core/common", "errors", "unknown", "ru")
        }
    }

    @Test
    fun testEmbeddedLocalizationResourcesRegistration() {
        val commonStrings = CommonModuleStrings(
            resolver = DefaultLocalizedStringResolver(),
            locale = CommonLocales.RUSSIAN
        )

        assertEquals("Повторить", commonStrings.ui.retry)
        assertEquals("Произошла неизвестная ошибка.", commonStrings.errors.unknown)
    }
}
