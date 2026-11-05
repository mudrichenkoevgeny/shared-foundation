package io.github.mudrichenkoevgeny.shared.foundation.feature.user.mapper.identifier

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierSummary
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.toUserIdentifierIdOrThrow
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.toUserIdOrThrow
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.identifier.UserIdentifierSummaryPayload

/**
 * Builds domain [UserIdentifierSummary] from [UserIdentifierSummaryPayload].
 */
public fun UserIdentifierSummaryPayload.toUserIdentifierSummary(): UserIdentifierSummary =
    UserIdentifierSummary(
        id = id.toUserIdentifierIdOrThrow(),
        userId = userId.toUserIdOrThrow(),
        userAuthProvider = UserAuthProvider.fromValueOrThrow(userAuthProvider),
        identifier = identifier,
        displayName = displayName
    )

/**
 * Builds network [UserIdentifierSummaryPayload] from domain [UserIdentifierSummary].
 */
public fun UserIdentifierSummary.toUserIdentifierSummaryPayload(): UserIdentifierSummaryPayload =
    UserIdentifierSummaryPayload(
        id = id.asHexDashString(),
        userId = userId.asHexDashString(),
        userAuthProvider = userAuthProvider.value,
        identifier = identifier,
        displayName = displayName
    )
