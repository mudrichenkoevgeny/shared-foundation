package io.github.mudrichenkoevgeny.shared.foundation.core.security.mapper.totpsetup

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.totpsetup.TotpSetup
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.totpsetup.TotpSetupPayload

/**
 * Maps [TotpSetupPayload] DTO to domain model [TotpSetup].
 */
public fun TotpSetupPayload.toTotpSetup(): TotpSetup =
    TotpSetup(
        secretKey = this.secretKey,
        otpAuthUrl = this.otpAuthUrl,
        mfaToken = this.mfaToken
    )

/**
 * Maps domain model [TotpSetup] to [TotpSetupPayload] DTO.
 */
public fun TotpSetup.toTotpSetupPayload(): TotpSetupPayload =
    TotpSetupPayload(
        secretKey = this.secretKey,
        otpAuthUrl = this.otpAuthUrl,
        mfaToken = this.mfaToken
    )
