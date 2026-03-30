package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.login

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.login.LoginByEmailRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.login.LoginByExternalAuthProviderRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.login.LoginByPhoneRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.confirmation.SendConfirmationToPhoneRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.data.AuthDataPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.confirmation.SendConfirmationPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.auth.login.BaseLoginRoutes

/**
 * Route paths for login in the open API.
 */
object OpenLoginRoutes {
    /**
     * **HTTP method:** `POST`
     *
     * Request body: [LoginByEmailRequest].
     *
     * Response body: [AuthDataPayload].
     */
    const val LOGIN_BY_EMAIL = BaseLoginRoutes.LOGIN_BY_EMAIL

    /**
     * **HTTP method:** `POST`
     *
     * Request body: [LoginByPhoneRequest].
     *
     * Response body: [AuthDataPayload].
     */
    const val LOGIN_BY_PHONE = BaseLoginRoutes.LOGIN_BY_PHONE

    /**
     * **HTTP method:** `POST`
     *
     * Request body: [LoginByExternalAuthProviderRequest].
     *
     * Response body: [AuthDataPayload].
     */
    const val LOGIN_BY_EXTERNAL_AUTH_PROVIDER = BaseLoginRoutes.LOGIN_BY_EXTERNAL_AUTH_PROVIDER

    /**
     * **HTTP method:** `POST`
     *
     * Request body: [SendConfirmationToPhoneRequest].
     *
     * Response body: [SendConfirmationPayload].
     */
    const val SEND_LOGIN_CONFIRMATION_TO_PHONE = BaseLoginRoutes.SEND_LOGIN_CONFIRMATION_TO_PHONE
}
