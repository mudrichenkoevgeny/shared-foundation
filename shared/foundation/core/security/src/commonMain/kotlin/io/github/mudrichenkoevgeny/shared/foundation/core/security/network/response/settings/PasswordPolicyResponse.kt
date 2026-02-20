package io.github.mudrichenkoevgeny.shared.foundation.core.security.network.response.settings

import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.contract.SecurityApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO representing the password complexity and validation rules.
 *
 * @property minLength Minimum number of characters required.
 * @property requireLetter Indicates if at least one alphabetic character is required.
 * @property requireUpperCase Indicates if at least one uppercase letter is required.
 * @property requireLowerCase Indicates if at least one lowercase letter is required.
 * @property requireDigit Indicates if at least one numerical digit is required.
 * @property requireSpecialChar Indicates if at least one special character is required.
 */
@Serializable
data class PasswordPolicyResponse(
    @SerialName(SecurityApiFields.MIN_LENGTH)
    val minLength: Int,

    @SerialName(SecurityApiFields.REQUIRE_LETTER)
    val requireLetter: Boolean,

    @SerialName(SecurityApiFields.REQUIRE_UPPER_CASE)
    val requireUpperCase: Boolean,

    @SerialName(SecurityApiFields.REQUIRE_LOWER_CASE)
    val requireLowerCase: Boolean,

    @SerialName(SecurityApiFields.REQUIRE_DIGIT)
    val requireDigit: Boolean,

    @SerialName(SecurityApiFields.REQUIRE_SPECIAL_CHAR)
    val requireSpecialChar: Boolean
)