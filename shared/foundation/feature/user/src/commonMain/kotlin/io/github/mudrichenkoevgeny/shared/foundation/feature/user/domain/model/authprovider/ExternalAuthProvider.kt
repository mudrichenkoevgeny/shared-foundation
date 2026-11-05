package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider

/**
 * Represents an external third-party authentication provider (OAuth-based).
 */
public sealed interface ExternalAuthProvider {
    /**
     * The corresponding [UserAuthProvider] type for this external provider.
     */
    public val userAuthProvider: UserAuthProvider

    public data object Google : ExternalAuthProvider {
        override val userAuthProvider: UserAuthProvider = UserAuthProvider.GOOGLE
    }

    public data object Apple : ExternalAuthProvider {
        override val userAuthProvider: UserAuthProvider = UserAuthProvider.APPLE
    }

    public companion object {
        /**
         * A list of all supported [ExternalAuthProvider] implementations.
         */
        public val all: List<ExternalAuthProvider> = listOf(Google, Apple)
    }
}
