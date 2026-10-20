package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.request.security.useridentifiers

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.security.useridentifiers.AddUserIdentifierExternalAuthProviderRequest

/**
 * Creates a test [AddUserIdentifierExternalAuthProviderRequest] request object instance.
 *
 * @param authProvider External provider wire string ([UserAuthProvider]).
 * @param externalProviderToken OAuth token string from provider.
 */
fun createTestAddUserIdentifierExternalAuthProviderRequest(
    authProvider: String = UserAuthProvider.GOOGLE.value,
    externalProviderToken: String = "google_token_12345"
) = AddUserIdentifierExternalAuthProviderRequest(
    authProvider = authProvider,
    externalProviderToken = externalProviderToken
)
