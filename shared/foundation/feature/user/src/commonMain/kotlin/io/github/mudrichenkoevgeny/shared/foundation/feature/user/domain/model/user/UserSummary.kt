package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.user.UserSummaryPayload

/**
 * Public summary projection of a user for list views, mentions, and cards.
 *
 * Wire counterpart is [UserSummaryPayload].
 *
 * @property id Unique user id ([UserId]); defaults to [UserId.generate] when creating new instances.
 * @property role Access level assigned to the user ([UserRole]).
 * @property accountStatus Current account state ([UserAccountStatus]).
 */
data class UserSummary(
    val id: UserId = UserId.generate(),
    val role: UserRole,
    val accountStatus: UserAccountStatus
)
