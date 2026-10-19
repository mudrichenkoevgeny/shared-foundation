package io.github.mudrichenkoevgeny.shared.foundation.testing.core.security.network.model.totprecoverycodes

import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.totprecoverycodes.TotpRecoveryCodesPayload

fun createTestTotpRecoveryCodesPayload(
    codes: List<String> = listOf("REC-1111-2222", "REC-3333-4444")
) = TotpRecoveryCodesPayload(
    codes = codes
)
