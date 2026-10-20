package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.navigation.screen.client.auth.registration.email.ClientRegistrationByEmailParamsFixture

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.client.auth.registration.email.ClientRegistrationByEmailParams
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_EMAIL

/**
 * Creates a test [ClientRegistrationByEmailParams] navigation params instance.
 *
 * @param email Pre-filled registration email string.
 * @param redirectUrl Post-registration redirect URL.
 */
fun createTestClientRegistrationByEmailParams(
    email: String? = TEST_EMAIL,
    redirectUrl: String? = null
): ClientRegistrationByEmailParams = ClientRegistrationByEmailParams(
    email = email,
    redirectUrl = redirectUrl
)
