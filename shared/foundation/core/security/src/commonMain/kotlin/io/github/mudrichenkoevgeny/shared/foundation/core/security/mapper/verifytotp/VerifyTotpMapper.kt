package io.github.mudrichenkoevgeny.shared.foundation.core.security.mapper.verifytotp

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.verifytotp.VerifyTotp
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.verifytotp.VerifyTotpPayload

/**
 * Maps [VerifyTotpPayload] DTO to domain model [VerifyTotp].
 */
fun VerifyTotpPayload.toVerifyTotp() = VerifyTotp(
    mfaToken = this.mfaToken,
    code = this.code
)

/**
 * Maps domain model [VerifyTotp] to [VerifyTotpPayload] DTO.
 */
fun VerifyTotp.toVerifyTotpPayload() = VerifyTotpPayload(
    mfaToken = this.mfaToken,
    code = this.code
)