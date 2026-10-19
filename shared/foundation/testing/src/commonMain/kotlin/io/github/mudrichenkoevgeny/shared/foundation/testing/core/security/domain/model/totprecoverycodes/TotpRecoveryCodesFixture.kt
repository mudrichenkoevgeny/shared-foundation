package io.github.mudrichenkoevgeny.shared.foundation.testing.core.security.domain.model.totprecoverycodes

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.totprecoverycodes.TotpRecoveryCodes

fun createTestTotpRecoveryCodes(
    codes: List<String> = listOf("REC-1111-2222", "REC-3333-4444")
) = TotpRecoveryCodes(
    codes = codes
)
