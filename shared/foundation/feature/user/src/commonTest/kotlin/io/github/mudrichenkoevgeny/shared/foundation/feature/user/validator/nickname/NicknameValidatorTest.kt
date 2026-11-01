package io.github.mudrichenkoevgeny.shared.foundation.feature.user.validator.nickname

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.error.naming.UserErrorCodes
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class NicknameValidatorTest {

    private val validator: NicknameValidator = NicknameValidatorImpl()

    @Test
    fun testValidate_Null_ReturnsSuccess() {
        val result = validator.validate(null, minLength = 3, maxLength = 30)
        assertTrue(result is NicknameValidatorResult.Success)
    }

    @Test
    fun testValidate_Valid_ReturnsSuccess() {
        assertTrue(validator.validate("a", minLength = 1, maxLength = 30) is NicknameValidatorResult.Success)
        assertTrue(validator.validate("valid_user", minLength = 3, maxLength = 30) is NicknameValidatorResult.Success)
        assertTrue(validator.validate("user.123", minLength = 3, maxLength = 30) is NicknameValidatorResult.Success)
        assertTrue(validator.validate("abc", minLength = 3, maxLength = 30) is NicknameValidatorResult.Success)
    }

    @Test
    fun testValidate_DisabledChange_ReturnsDisabledError() {
        val result = validator.validate(
            nickname = "new_nick",
            minLength = 3,
            maxLength = 30,
            isNicknameChangeAllowed = false,
            isNicknameChanging = true
        )
        assertEquals(NicknameValidatorResult.Fail(UserErrorCodes.NICKNAME_CHANGE_DISABLED), result)
    }

    @Test
    fun testValidate_TooShortOrLong_ReturnsSpecificError() {
        assertEquals(
            NicknameValidatorResult.Fail(UserErrorCodes.NICKNAME_TOO_SHORT),
            validator.validate("ab", minLength = 3, maxLength = 30)
        )
        assertEquals(
            NicknameValidatorResult.Fail(UserErrorCodes.NICKNAME_TOO_LONG),
            validator.validate("a".repeat(31), minLength = 3, maxLength = 30)
        )
    }

    @Test
    fun testValidate_InvalidSymbols_ReturnsCharactersError() {
        assertEquals(
            NicknameValidatorResult.Fail(UserErrorCodes.INVALID_NICKNAME_CHARACTERS),
            validator.validate("user@name", minLength = 3, maxLength = 30)
        )
        assertEquals(
            NicknameValidatorResult.Fail(UserErrorCodes.INVALID_NICKNAME_CHARACTERS),
            validator.validate("user name", minLength = 3, maxLength = 30)
        )
        assertEquals(
            NicknameValidatorResult.Fail(UserErrorCodes.INVALID_NICKNAME_CHARACTERS),
            validator.validate("_username", minLength = 3, maxLength = 30)
        )
        assertEquals(
            NicknameValidatorResult.Fail(UserErrorCodes.INVALID_NICKNAME_CHARACTERS),
            validator.validate("username_", minLength = 3, maxLength = 30)
        )
        assertEquals(
            NicknameValidatorResult.Fail(UserErrorCodes.INVALID_NICKNAME_CHARACTERS),
            validator.validate("john..doe", minLength = 3, maxLength = 30)
        )
        assertEquals(
            NicknameValidatorResult.Fail(UserErrorCodes.INVALID_NICKNAME_CHARACTERS),
            validator.validate("alex__smith", minLength = 3, maxLength = 30)
        )
    }
}
