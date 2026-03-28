package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.resetpassword

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.password.ResetPasswordRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.password.SendResetPasswordConfirmationRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.response.confirmation.SendConfirmationResponse
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.auth.resetpassword.BaseResetPasswordRoutes

/**
 * Route paths for password recovery in the open API.
 */
object OpenResetPasswordRoutes {
    /**
     * **HTTP method:** `POST`
     *
     * Request body: [ResetPasswordRequest].
     */
    const val RESET_EMAIL_PASSWORD = BaseResetPasswordRoutes.RESET_PASSWORD

    /**
     * **HTTP method:** `POST`
     *
     * Request body: [SendResetPasswordConfirmationRequest].
     *
     * Response body: [SendConfirmationResponse].
     */
    const val SEND_RESET_EMAIL_PASSWORD_CONFIRMATION = BaseResetPasswordRoutes.SEND_RESET_PASSWORD_CONFIRMATION
}
