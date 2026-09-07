package io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.passwordpolicy

import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.contract.SecurityApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OpenPasswordPolicyPayload(
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
