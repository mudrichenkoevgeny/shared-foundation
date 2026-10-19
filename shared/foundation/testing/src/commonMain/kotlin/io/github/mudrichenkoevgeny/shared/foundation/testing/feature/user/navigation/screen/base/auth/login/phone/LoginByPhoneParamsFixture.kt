package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.navigation.screen.base.auth.login.phone

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.login.phone.LoginByPhoneParams
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_PHONE

fun createTestLoginByPhoneParams(
    phone: String? = TEST_PHONE,
    redirectUrl: String? = null
): LoginByPhoneParams = LoginByPhoneParams(
    phone = phone,
    redirectUrl = redirectUrl
)
