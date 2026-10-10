package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.identifier.UserIdentifierSummaryPayload

/**
 * Condensed credential row for list views and login method badges.
 *
 * Wire counterpart is [UserIdentifierSummaryPayload].
 *
 * @property id Identifier row id.
 * @property userAuthProvider Authentication provider category.
 * @property identifier Raw or masked login identifier string.
 * @property displayName Non-null display name for the identifier.
 */
data class UserIdentifierSummary(
    val id: UserIdentifierId = UserIdentifierId.generate(),
    val userAuthProvider: UserAuthProvider,
    val identifier: String,
    val displayName: String
)
