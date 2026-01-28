package io.github.mudrichenkoevgeny.shared.foundation.core.common.error.model

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.constants.CommonApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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