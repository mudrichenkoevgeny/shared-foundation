package io.github.mudrichenkoevgeny.shared.foundation.core.security.mapper.totprecoverycodes

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.totprecoverycodes.TotpRecoveryCodes
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.totprecoverycodes.TotpRecoveryCodesPayload

/**
 * Maps [TotpRecoveryCodesPayload] DTO to domain model [TotpRecoveryCodes].
 */
fun TotpRecoveryCodesPayload.toTotpRecoveryCodes() = TotpRecoveryCodes(
    codes = this.codes
)

/**
 * Maps domain model [TotpRecoveryCodes] to [TotpRecoveryCodesPayload] DTO.
 */
fun TotpRecoveryCodes.toTotpRecoveryCodesPayload() = TotpRecoveryCodesPayload(
    codes = this.codes
)