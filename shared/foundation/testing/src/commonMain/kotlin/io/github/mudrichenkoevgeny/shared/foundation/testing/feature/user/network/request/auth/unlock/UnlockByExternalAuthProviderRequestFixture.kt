package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.request.auth.unlock

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.unlock.UnlockByExternalAuthProviderRequest

/**
 * Creates a test [UnlockByExternalAuthProviderRequest] request object instance.
 *
 * @param authProvider External provider wire string ([UserAuthProvider]).
 * @param externalProviderToken OAuth token string from provider.
 */
fun createTestUnlockByExternalAuthProviderRequest(
    authProvider: String = UserAuthProvider.GOOGLE.value,
    externalProviderToken: String = "google_token_12345"
) = UnlockByExternalAuthProviderRequest(
    authProvider = authProvider,
    externalProviderToken = externalProviderToken
)
