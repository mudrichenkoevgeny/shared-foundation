package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.navigation.screen.client.auth.login.phone

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.client.auth.login.phone.LoginByPhoneParams
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_PHONE

fun createTestClientLoginByPhoneParams(
    phone: String? = TEST_PHONE
): LoginByPhoneParams = LoginByPhoneParams(
    phone = phone
)
