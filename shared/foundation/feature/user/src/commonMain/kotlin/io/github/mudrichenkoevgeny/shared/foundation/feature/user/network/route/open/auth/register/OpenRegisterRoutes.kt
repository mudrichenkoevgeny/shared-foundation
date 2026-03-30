package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.register

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.register.RegisterByEmailRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.confirmation.SendConfirmationToEmailRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.data.AuthDataPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.confirmation.SendConfirmationPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.auth.register.BaseRegisterRoutes

/**
 * Route paths for registration in the open API.
 */
object OpenRegisterRoutes {
    /**
     * **HTTP method:** `POST`
     *
     * Request body: [RegisterByEmailRequest].
     *
     * Response body: [AuthDataPayload].
     */
    const val REGISTER_BY_EMAIL = BaseRegisterRoutes.REGISTER_BY_EMAIL

    /**
     * **HTTP method:** `POST`
     *
     * Request body: [SendConfirmationToEmailRequest].
     *
     * Response body: [SendConfirmationPayload].
     */
    const val SEND_REGISTER_CONFIRMATION_TO_EMAIL = BaseRegisterRoutes.SEND_REGISTER_CONFIRMATION_TO_EMAIL
}
