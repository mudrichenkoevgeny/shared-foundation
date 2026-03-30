package io.github.mudrichenkoevgeny.shared.foundation.feature.user.mapper.confirmation

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.confirmation.SendConfirmation
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.confirmation.SendConfirmationPayload

/**
 * Maps between [SendConfirmationPayload] and domain [SendConfirmation].
 */

/**
 * Builds domain [SendConfirmation] from [SendConfirmationPayload].
 */
fun SendConfirmationPayload.toSendConfirmation(): SendConfirmation =
    SendConfirmation(
        retryAfterSeconds = retryAfterSeconds,
        numberOfSymbols = numberOfSymbols
    )

/**
 * Builds network [SendConfirmationPayload] from domain [SendConfirmation].
 */
fun SendConfirmation.toSendConfirmationPayload(): SendConfirmationPayload =
    SendConfirmationPayload(
        retryAfterSeconds = retryAfterSeconds,
        numberOfSymbols = numberOfSymbols
    )
