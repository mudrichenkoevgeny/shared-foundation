package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.auth.unlock

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.auth.BaseAuthRoutes

/**
 * Shared base path segments for public self-service account unlock endpoints.
 */
object BaseOpenUnlockRoutes {
    /** Base path for all open unlock operations. */
    const val BASE_OPEN_UNLOCK_ROUTE = "${BaseAuthRoutes.BASE_AUTH_ROUTE}/unlock"

    /** Appended path to unlock an account using email. */
    const val UNLOCK_BY_EMAIL = "$BASE_OPEN_UNLOCK_ROUTE/email"

    /** Appended path to request an email confirmation code for unlocking. */
    const val SEND_UNLOCK_EMAIL_CONFIRMATION = "$UNLOCK_BY_EMAIL/send-confirmation"

    /** Appended path to unlock an account using a phone number. */
    const val UNLOCK_BY_PHONE = "$BASE_OPEN_UNLOCK_ROUTE/phone"

    /** Appended path to request an SMS confirmation code for unlocking. */
    const val SEND_UNLOCK_PHONE_CONFIRMATION = "$UNLOCK_BY_PHONE/send-confirmation"

    /** Appended path to unlock an account via an external identity provider. */
    const val UNLOCK_BY_EXTERNAL_PROVIDER = "$BASE_OPEN_UNLOCK_ROUTE/external-provider"
}
