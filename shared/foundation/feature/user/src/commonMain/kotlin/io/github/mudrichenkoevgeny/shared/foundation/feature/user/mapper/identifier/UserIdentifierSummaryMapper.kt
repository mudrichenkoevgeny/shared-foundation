package io.github.mudrichenkoevgeny.shared.foundation.feature.user.mapper.identifier

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierSummary
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.toUserIdentifierIdOrThrow
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.identifier.UserIdentifierSummaryPayload

/**
 * Extension functions mapping summary identifier models between domain and network representations.
 */

/**
 * Builds domain [UserIdentifierSummary] from [UserIdentifierSummaryPayload].
 */
fun UserIdentifierSummaryPayload.toUserIdentifierSummary(): UserIdentifierSummary = UserIdentifierSummary(
    id = id.toUserIdentifierIdOrThrow(),
    userAuthProvider = UserAuthProvider.fromValueOrThrow(userAuthProvider),
    identifier = identifier,
    displayName = displayName
)

/**
 * Builds network [UserIdentifierSummaryPayload] from domain [UserIdentifierSummary].
 */
fun UserIdentifierSummary.toUserIdentifierSummaryPayload(): UserIdentifierSummaryPayload = UserIdentifierSummaryPayload(
    id = id.asHexDashString(),
    userAuthProvider = userAuthProvider.serialName,
    identifier = identifier,
    displayName = displayName
)
