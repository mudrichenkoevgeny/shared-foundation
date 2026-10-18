package io.github.mudrichenkoevgeny.shared.foundation.feature.user.localization

import io.github.mudrichenkoevgeny.shared.foundation.core.common.localization.CommonLocales
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UserEmailTemplatesTest {

    @Test
    fun testGeneratesAndFormatsEmailTemplatesEn() {
        val userStrings = UserModuleStrings(locale = CommonLocales.ENGLISH)
        val template = userStrings.emailTemplates.verificationCode("123456")

        assertEquals("Verification Code", template.subject)
        assertTrue(template.body.contains("Your code: 123456"))
    }

    @Test
    fun testGeneratesAndFormatsEmailTemplatesRu() {
        val userStrings = UserModuleStrings(locale = CommonLocales.RUSSIAN)
        val template = userStrings.emailTemplates.verificationCode("654321")

        assertEquals("Код подтверждения", template.subject)
        assertTrue(template.body.contains("Ваш код: 654321"))
    }
}
