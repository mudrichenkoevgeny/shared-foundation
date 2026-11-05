package io.github.mudrichenkoevgeny.shared.foundation.core.security.mapper.totprecoverycodes

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.totprecoverycodes.TotpRecoveryCodes
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.totprecoverycodes.TotpRecoveryCodesPayload

/**
 * Maps [TotpRecoveryCodesPayload] DTO to domain model [TotpRecoveryCodes].
 */
public fun TotpRecoveryCodesPayload.toTotpRecoveryCodes(): TotpRecoveryCodes =
    TotpRecoveryCodes(
        codes = this.codes
    )

/**
 * Maps domain model [TotpRecoveryCodes] to [TotpRecoveryCodesPayload] DTO.
 */
public fun TotpRecoveryCodes.toTotpRecoveryCodesPayload(): TotpRecoveryCodesPayload =
    TotpRecoveryCodesPayload(
        codes = this.codes
    )
