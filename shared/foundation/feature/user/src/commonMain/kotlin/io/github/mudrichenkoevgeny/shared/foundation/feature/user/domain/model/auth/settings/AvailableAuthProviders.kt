package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.auth.settings

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.ExternalAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider

/**
 * Auth providers enabled for the current environment.
 *
 * The lists are split to keep client UI ordering stable:
 * - [primary] providers should be displayed first;
 * - [secondary] providers can be displayed as additional options.
 *
 * @property primary Primary enabled auth providers.
 * @property secondary Secondary enabled auth providers.
 */
data class AvailableAuthProviders(
    val primary: List<UserAuthProvider>,
    val secondary: List<UserAuthProvider>
) {
    /**
     * External providers (OAuth-like) supported by the enabled auth providers.
     *
     * The mapping is currently limited to the subset supported by the shared contract.
     */
    val supportedExternalProviders: Set<ExternalAuthProvider>
        get() {
            val activeTypes = (primary + secondary).toSet()

            return ExternalAuthProvider.all
                .filter { it.userAuthProvider in activeTypes }
                .toSet()
        }
}