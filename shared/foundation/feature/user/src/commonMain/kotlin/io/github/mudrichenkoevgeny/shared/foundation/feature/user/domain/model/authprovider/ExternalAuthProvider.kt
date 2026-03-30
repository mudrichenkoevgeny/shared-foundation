package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider

/**
 * Represents an external third-party authentication provider (OAuth-based).
 */
sealed interface ExternalAuthProvider {
    /**
     * The corresponding [UserAuthProvider] type for this external provider.
     */
    val userAuthProvider: UserAuthProvider

    data object Google : ExternalAuthProvider {
        override val userAuthProvider = UserAuthProvider.GOOGLE
    }

    data object Apple : ExternalAuthProvider {
        override val userAuthProvider = UserAuthProvider.APPLE
    }

    companion object {
        /**
         * A list of all supported [ExternalAuthProvider] implementations.
         */
        val all: List<ExternalAuthProvider> = listOf(Google, Apple)
    }
}