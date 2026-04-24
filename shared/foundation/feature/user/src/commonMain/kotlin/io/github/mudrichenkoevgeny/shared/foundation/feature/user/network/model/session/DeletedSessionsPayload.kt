package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.session

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data transfer object representing the result of a bulk session deletion request.
 *
 * @property deletedSessionIds The list of unique identifiers for the sessions that were successfully deleted.
 */
@Serializable
data class DeletedSessionsPayload(
    @SerialName(UserApiFields.DELETED_SESSION_IDS)
    val deletedSessionIds: List<String>
)