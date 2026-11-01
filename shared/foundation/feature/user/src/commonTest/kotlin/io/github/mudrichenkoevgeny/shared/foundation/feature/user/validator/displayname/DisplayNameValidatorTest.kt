package io.github.mudrichenkoevgeny.shared.foundation.feature.user.validator.displayname

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.error.naming.UserErrorCodes
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DisplayNameValidatorTest {

    private val validator: DisplayNameValidator = DisplayNameValidatorImpl()

    @Test
    fun testValidate_Null_ReturnsSuccess() {
        val result = validator.validate(null, minLength = 1, maxLength = 50)
        assertTrue(result is DisplayNameValidatorResult.Success)
    }

    @Test
    fun testValidate_Valid_ReturnsSuccess() {
        assertTrue(validator.validate("Ivan Ivanov", minLength = 1, maxLength = 50) is DisplayNameValidatorResult.Success)
        assertTrue(validator.validate("A", minLength = 1, maxLength = 50) is DisplayNameValidatorResult.Success)
    }

    @Test
    fun testValidate_TooShortOrLong_ReturnsSpecificError() {
        assertEquals(
            DisplayNameValidatorResult.Fail(UserErrorCodes.DISPLAY_NAME_TOO_SHORT),
            validator.validate("", minLength = 1, maxLength = 50)
        )
        assertEquals(
            DisplayNameValidatorResult.Fail(UserErrorCodes.DISPLAY_NAME_TOO_LONG),
            validator.validate("a".repeat(51), minLength = 1, maxLength = 50)
        )
    }

    @Test
    fun testValidate_ControlCharacters_ReturnsCharactersError() {
        assertEquals(
            DisplayNameValidatorResult.Fail(UserErrorCodes.INVALID_DISPLAY_NAME_CHARACTERS),
            validator.validate("Ivan\nIvanov", minLength = 1, maxLength = 50)
        )
        assertEquals(
            DisplayNameValidatorResult.Fail(UserErrorCodes.INVALID_DISPLAY_NAME_CHARACTERS),
            validator.validate("Ivan\tIvanov", minLength = 1, maxLength = 50)
        )
    }
}
