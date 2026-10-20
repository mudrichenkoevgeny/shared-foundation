package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.request.auth.login

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.login.LoginByExternalAuthProviderRequest

/**
 * Creates a test [LoginByExternalAuthProviderRequest] request object instance.
 *
 * @param authProvider External provider wire string ([UserAuthProvider]).
 * @param externalProviderToken OAuth token string from provider.
 */
fun createTestLoginByExternalAuthProviderRequest(
    authProvider: String = UserAuthProvider.GOOGLE.value,
    externalProviderToken: String = "google_token_12345"
) = LoginByExternalAuthProviderRequest(
    authProvider = authProvider,
    externalProviderToken = externalProviderToken
)
