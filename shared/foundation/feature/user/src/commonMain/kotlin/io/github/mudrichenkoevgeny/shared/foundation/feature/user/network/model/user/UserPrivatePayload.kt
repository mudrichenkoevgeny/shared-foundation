package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.user

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.accountlockout.AccountLockoutType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserPrivate
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

/**
 * Private user profile wire model for authenticated self-service and administrative management flows.
 *
 * Wire keys use `snake_case` via [CommonApiFields] and [UserApiFields]. Aligns with domain [UserPrivate].
 *
 * @property id [UserPrivate.id] on the wire: [UserId] as hex-dash string.
 * @property role [UserPrivate.role]; wire values match [UserRole.serialName].
 * @property accountStatus [UserPrivate.accountStatus]; wire values match [UserAccountStatus.serialName].
 * @property accountStatusOnRestore [UserPrivate.accountStatusOnRestore]; wire values match [UserAccountStatus.serialName], or `null`.
 * @property authorityLevel [UserPrivate.authorityLevel].
 * @property permissionCodes [UserPrivate.permissionCodes] as strings.
 * @property isTotpEnabled [UserPrivate.isTotpEnabled].
 * @property lastLoginAt [UserPrivate.lastLoginAt] as Unix epoch milliseconds ([Instant]), or `null`.
 * @property lastActiveAt [UserPrivate.lastActiveAt] as Unix epoch milliseconds ([Instant]), or `null`.
 * @property createdAt [UserPrivate.createdAt] as Unix epoch milliseconds ([Instant]).
 * @property updatedAt [UserPrivate.updatedAt] as Unix epoch milliseconds ([Instant]), or `null`.
 * @property scheduledPermanentDeletionAt [UserPrivate.scheduledPermanentDeletionAt] as Unix epoch milliseconds ([Instant]), or `null`.
 * @property lockoutType [UserPrivate.lockoutType]; wire values match [AccountLockoutType.serialName].
 * @property temporaryLockoutUntil [UserPrivate.temporaryLockoutUntil] as Unix epoch milliseconds ([Instant]), or `null`.
 */
@Serializable
data class UserPrivatePayload(
    @SerialName(CommonApiFields.ID)
    val id: String,

    @SerialName(UserApiFields.ROLE)
    val role: String,

    @SerialName(UserApiFields.ACCOUNT_STATUS)
    val accountStatus: String,

    @SerialName(UserApiFields.ACCOUNT_STATUS_ON_RESTORE)
    val accountStatusOnRestore: String? = null,

    @SerialName(UserApiFields.AUTHORITY_LEVEL)
    val authorityLevel: Int,

    @SerialName(UserApiFields.PERMISSION_CODES)
    val permissionCodes: Set<String>,

    @SerialName(UserApiFields.IS_TOTP_ENABLED)
    val isTotpEnabled: Boolean,

    @SerialName(UserApiFields.LAST_LOGIN_AT)
    val lastLoginAt: Long? = null,

    @SerialName(UserApiFields.LAST_ACTIVE_AT)
    val lastActiveAt: Long? = null,

    @SerialName(CommonApiFields.CREATED_AT)
    val createdAt: Long,

    @SerialName(CommonApiFields.UPDATED_AT)
    val updatedAt: Long? = null,

    @SerialName(UserApiFields.SCHEDULED_PERMANENT_DELETION_AT)
    val scheduledPermanentDeletionAt: Long? = null,

    @SerialName(UserApiFields.ACCOUNT_LOCKOUT_TYPE)
    val lockoutType: String,

    @SerialName(UserApiFields.TEMPORARY_LOCKOUT_UNTIL)
    val temporaryLockoutUntil: Long? = null
)
