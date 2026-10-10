package io.github.mudrichenkoevgeny.shared.foundation.feature.user.mapper.user

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserSummary
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.toUserIdOrThrow
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.user.UserSummaryPayload

/**
 * Extension functions mapping summary user models between domain and network representations.
 */

/**
 * Builds domain [UserSummary] from [UserSummaryPayload].
 *
 * @throws IllegalArgumentException when `id` is not a valid user UUID string, or when a wire value
 * for `role` or `accountStatus` is not recognized by [UserRole.fromValueOrNull] or [UserAccountStatus.fromValueOrNull].
 */
fun UserSummaryPayload.toUserSummary(): UserSummary = UserSummary(
    id = id.toUserIdOrThrow(),
    role = UserRole.fromValueOrThrow(role),
    accountStatus = UserAccountStatus.fromValueOrThrow(accountStatus)
)

/**
 * Builds network [UserSummaryPayload] from domain [UserSummary].
 */
fun UserSummary.toUserSummaryPayload(): UserSummaryPayload = UserSummaryPayload(
    id = id.asHexDashString(),
    role = role.serialName,
    accountStatus = accountStatus.serialName
)
