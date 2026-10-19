package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.request.auth.login

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.login.LoginByExternalAuthProviderRequest

fun createTestLoginByExternalAuthProviderRequest(
    authProvider: String = UserAuthProvider.GOOGLE.value,
    externalProviderToken: String = "google_token_12345"
) = LoginByExternalAuthProviderRequest(
    authProvider = authProvider,
    externalProviderToken = externalProviderToken
)
