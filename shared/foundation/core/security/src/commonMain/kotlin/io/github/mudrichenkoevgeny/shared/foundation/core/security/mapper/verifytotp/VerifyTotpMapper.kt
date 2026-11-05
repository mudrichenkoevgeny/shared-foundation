package io.github.mudrichenkoevgeny.shared.foundation.core.security.mapper.verifytotp

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.verifytotp.VerifyTotp
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.verifytotp.VerifyTotpPayload

/**
 * Maps [VerifyTotpPayload] DTO to domain model [VerifyTotp].
 */
public fun VerifyTotpPayload.toVerifyTotp(): VerifyTotp =
    VerifyTotp(
        mfaToken = this.mfaToken,
        code = this.code
    )

/**
 * Maps domain model [VerifyTotp] to [VerifyTotpPayload] DTO.
 */
public fun VerifyTotp.toVerifyTotpPayload(): VerifyTotpPayload =
    VerifyTotpPayload(
        mfaToken = this.mfaToken,
        code = this.code
    )
