package io.github.mudrichenkoevgeny.shared.foundation.core.security.mapper.totpsetup

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.totpsetup.TotpSetup
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.totpsetup.TotpSetupPayload

/**
 * Maps [TotpSetupPayload] DTO to domain model [TotpSetup].
 */
fun TotpSetupPayload.toTotpSetup() = TotpSetup(
    secretKey = this.secretKey,
    otpAuthUrl = this.otpAuthUrl,
    mfaToken = this.mfaToken
)

/**
 * Maps domain model [TotpSetup] to [TotpSetupPayload] DTO.
 */
fun TotpSetup.toTotpSetupPayload() = TotpSetupPayload(
    secretKey = this.secretKey,
    otpAuthUrl = this.otpAuthUrl,
    mfaToken = this.mfaToken
)