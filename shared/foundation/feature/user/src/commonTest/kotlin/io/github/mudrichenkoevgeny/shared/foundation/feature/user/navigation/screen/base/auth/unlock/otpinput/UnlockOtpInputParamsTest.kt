package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.otpinput

import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.contract.SecurityApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.UnlockMethod
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.contract.UserNavigationPaths
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class UnlockOtpInputParamsTest {

    @Test
    fun `parse extracts method identifier retryAfterSeconds and code correctly`() {
        val params = mapOf(
            UserNavigationPaths.UNLOCK_METHOD to "email",
            UserApiFields.IDENTIFIER to "user@example.com",
            SecurityApiFields.RETRY_AFTER_SECONDS to "60",
            UserApiFields.CONFIRMATION_CODE to "123456"
        )
        val parsed = UnlockOtpInputParams.parse(params)

        assertEquals(UnlockMethod.EMAIL, parsed.unlockMethod)
        assertEquals("user@example.com", parsed.identifier)
        assertEquals(60, parsed.retryAfterSeconds)
        assertEquals("123456", parsed.code)
    }

    @Test
    fun `parse normalizes phone identifier when method is phone`() {
        val params = mapOf(
            UserNavigationPaths.UNLOCK_METHOD to "phone",
            UserApiFields.IDENTIFIER to " 12345678900"
        )
        val parsed = UnlockOtpInputParams.parse(params)

        assertEquals(UnlockMethod.PHONE, parsed.unlockMethod)
        assertEquals("+12345678900", parsed.identifier)
    }

    @Test
    fun `parse returns null for missing fields`() {
        val emptyParsed = UnlockOtpInputParams.parse(emptyMap())
        assertNull(emptyParsed.unlockMethod)
        assertNull(emptyParsed.identifier)
        assertNull(emptyParsed.retryAfterSeconds)
        assertNull(emptyParsed.code)
    }

    @Test
    fun `serialize and toQueryMap produce correct map`() {
        val model = UnlockOtpInputParams(
            unlockMethod = UnlockMethod.EMAIL,
            identifier = "user@example.com",
            retryAfterSeconds = 60,
            code = "123456"
        )
        val serialized = model.toQueryMap()

        assertEquals(listOf("email"), serialized[UserNavigationPaths.UNLOCK_METHOD])
        assertEquals(listOf("user@example.com"), serialized[UserApiFields.IDENTIFIER])
        assertEquals(listOf("60"), serialized[SecurityApiFields.RETRY_AFTER_SECONDS])
        assertEquals(listOf("123456"), serialized[UserApiFields.CONFIRMATION_CODE])
        assertEquals(serialized, UnlockOtpInputParams.serialize(model))
    }
}
