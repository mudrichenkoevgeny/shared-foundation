package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.navigation.screen.client.auth.login.phone

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.client.auth.login.phone.LoginByPhoneParams
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_PHONE

/**
 * Creates a test client [LoginByPhoneParams] navigation params instance.
 *
 * @param phone Pre-filled phone number string.
 */
fun createTestLoginByPhoneParams(
    phone: String? = TEST_PHONE
): LoginByPhoneParams = LoginByPhoneParams(
    phone = phone
)
