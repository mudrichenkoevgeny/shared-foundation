package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model

sealed interface ExternalAuthProvider {
    val userAuthProvider: UserAuthProvider

    data object Google : ExternalAuthProvider {
        override val userAuthProvider = UserAuthProvider.GOOGLE
    }
    data object Apple : ExternalAuthProvider {
        override val userAuthProvider = UserAuthProvider.APPLE
    }
}