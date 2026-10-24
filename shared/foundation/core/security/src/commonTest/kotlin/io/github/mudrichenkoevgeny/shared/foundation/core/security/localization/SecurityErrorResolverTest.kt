package io.github.mudrichenkoevgeny.shared.foundation.core.security.localization

import io.github.mudrichenkoevgeny.shared.foundation.core.security.error.naming.SecurityErrorArgs
import kotlin.test.Test
import kotlin.test.assertEquals

class SecurityErrorResolverTest {

    private val securityStringsEn = SecurityModuleStrings(locale = "en")
    private val securityStringsRu = SecurityModuleStrings(locale = "ru")

    @Test
    fun `resolvePasswordTooWeakReasons parses error args map correctly`() {
        val args = mapOf<String, Any?>(
            SecurityErrorArgs.PASSWORD_MIN_LENGTH to 12,
            SecurityErrorArgs.PASSWORD_FAIL_TOO_SHORT to true,
            SecurityErrorArgs.PASSWORD_FAIL_NO_UPPERCASE to true,
            SecurityErrorArgs.PASSWORD_FAIL_TOO_COMMON to true
        )

        val reasonsEn = securityStringsEn.resolvePasswordTooWeakReasons(args)
        assertEquals(
            listOf(
                "Password must be at least 12 characters long.",
                "Password must contain at least one uppercase letter.",
                "Password is too common and insecure."
            ),
            reasonsEn
        )

        val reasonsRu = securityStringsRu.resolvePasswordTooWeakReasons(args)
        assertEquals(
            listOf(
                "Пароль должен содержать не менее 12 символов.",
                "Пароль должен содержать хотя бы одну заглавную букву.",
                "Пароль слишком распространенный и ненадежный."
            ),
            reasonsRu
        )
    }

    @Test
    fun `resolvePasswordTooWeakReasons returns fallback when args is null or empty`() {
        val reasonsNullEn = securityStringsEn.resolvePasswordTooWeakReasons(null)
        assertEquals(listOf("Password is too weak."), reasonsNullEn)

        val reasonsNullRu = securityStringsRu.resolvePasswordTooWeakReasons(null)
        assertEquals(listOf("Пароль слишком слабый."), reasonsNullRu)

        val reasonsEmptyEn = securityStringsEn.resolvePasswordTooWeakReasons(emptyMap())
        assertEquals(listOf("Password is too weak."), reasonsEmptyEn)
    }
}
