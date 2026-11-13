package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session

/**
 * Domain representation of a bulk session deletion result.
 *
 * @property deletedSessionIds List of IDs of the sessions that were revoked.
 */
public data class DeletedSessions(
    public val deletedSessionIds: List<UserSessionId>
)
