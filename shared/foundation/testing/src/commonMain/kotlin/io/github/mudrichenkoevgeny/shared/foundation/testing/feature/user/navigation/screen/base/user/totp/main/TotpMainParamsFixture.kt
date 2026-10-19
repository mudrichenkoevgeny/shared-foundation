package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.navigation.screen.base.user.totp.main

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.totp.TotpStep
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.user.totp.main.TotpMainParams

fun createTestTotpMainParams(
    totpStep: TotpStep = TotpStep.DEFAULT
): TotpMainParams = TotpMainParams(
    totpStep = totpStep
)
