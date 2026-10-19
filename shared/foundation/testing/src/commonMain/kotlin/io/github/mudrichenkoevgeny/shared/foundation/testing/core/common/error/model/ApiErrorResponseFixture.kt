package io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.error.model

import io.github.mudrichenkoevgeny.shared.foundation.core.common.error.model.ApiErrorResponse
import io.github.mudrichenkoevgeny.shared.foundation.core.common.error.naming.CommonErrorCodes

fun createTestApiErrorResponse(
    id: String = "err_12345",
    code: String = CommonErrorCodes.INTERNAL,
    message: String = "Internal server error",
    args: Map<String, String> = emptyMap()
) = ApiErrorResponse(
    id = id,
    code = code,
    message = message,
    args = args
)
