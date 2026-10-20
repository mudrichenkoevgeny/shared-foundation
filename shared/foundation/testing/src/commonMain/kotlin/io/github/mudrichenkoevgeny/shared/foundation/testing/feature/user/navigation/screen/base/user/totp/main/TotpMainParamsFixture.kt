package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.navigation.screen.base.user.totp.main

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.totp.TotpStep
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.user.totp.main.TotpMainParams

/**
 * Creates a test [TotpMainParams] navigation params instance.
 *
 * @param totpStep Initial TOTP setup flow step ([TotpStep]).
 */
fun createTestTotpMainParams(
    totpStep: TotpStep = TotpStep.DEFAULT
): TotpMainParams = TotpMainParams(
    totpStep = totpStep
)
