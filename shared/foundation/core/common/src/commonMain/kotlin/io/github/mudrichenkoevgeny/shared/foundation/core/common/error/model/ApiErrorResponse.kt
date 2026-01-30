package io.github.mudrichenkoevgeny.shared.foundation.core.common.error.model

import io.github.mudrichenkoevgeny.shared.foundation.core.common.error.constants.CommonErrorArgs
import io.github.mudrichenkoevgeny.shared.foundation.core.common.error.constants.CommonErrorCodes
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.constants.CommonApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Standard DTO for API error responses, used to convey failure details to the client.
 *
 * @property id Unique identifier of the error.
 * @property code Machine-readable code of the error (e.g. [CommonErrorCodes]).
 * @property message Human-readable description of the error.
 * @property args Dynamic metadata providing additional context for the failure (e.g. [CommonErrorArgs]).
 */
@Serializable
data class ApiErrorResponse(
    @SerialName(CommonApiFields.ID)
    val id: String,

    @SerialName(CommonApiFields.CODE)
    val code: String,

    @SerialName(CommonApiFields.MESSAGE)
    val message: String,

    @SerialName(CommonApiFields.ARGS)
    val args: Map<String, String> = emptyMap()
)