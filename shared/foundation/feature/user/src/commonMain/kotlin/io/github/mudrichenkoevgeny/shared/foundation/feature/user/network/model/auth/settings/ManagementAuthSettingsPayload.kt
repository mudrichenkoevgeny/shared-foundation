package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.settings

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.emailrestriction.EmailRestrictionPolicyPayload
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Wire body for updating authentication settings via the **management** API.
 *
 * @property availableAuthProviders Provider lists; wire values follow [UserAuthProvider.serialName].
 * @property maxTotalIdentifiers Maximum number of identifiers of any type allowed per account.
 * @property maxEmailIdentifiers Maximum number of email-based identifiers allowed per account.
 * @property maxPhoneIdentifiers Maximum number of phone-based identifiers allowed per account.
 * @property maxIdentifiersPerExternalProvider Maximum number of identifiers allowed for each
 * unique external provider per account.
 * @property maxActiveSessionsForOpenUser Maximum number of active sessions allowed per open user account.
 * @property maxActiveSessionsForManagementUser Maximum number of active sessions allowed per management user account.
 * @property accessTokenExpirationSeconds Validity window for **newly issued** access tokens, in seconds.
 * @property refreshTokenExpirationSeconds Validity window for **newly issued** refresh tokens, in seconds.
 * @property accountDeletionGracePeriodSeconds The grace period in seconds before an account scheduled for
 * deletion is permanently removed.
 * @property accountDeletionCheckIntervalSeconds The background worker check interval in seconds for processing
 * accounts pending permanent deletion.
 * @property isRegistrationEnabled Global flag indicating whether new user registrations are permitted.
 * @property openEmailRestrictionPolicy Email domain blacklist and whitelist restriction policy payload for open API endpoints; see [EmailRestrictionPolicyPayload].
 * @property managementEmailRestrictionPolicy Email domain blacklist and whitelist restriction policy payload for management API endpoints; see [EmailRestrictionPolicyPayload].
 */
@Serializable
data class ManagementAuthSettingsPayload(
    @SerialName(UserApiFields.AVAILABLE_AUTH_PROVIDERS)
    val availableAuthProviders: AvailableAuthProvidersPayload,

    @SerialName(UserApiFields.MAX_TOTAL_IDENTIFIERS)
    val maxTotalIdentifiers: Int,

    @SerialName(UserApiFields.MAX_EMAIL_IDENTIFIERS)
    val maxEmailIdentifiers: Int,

    @SerialName(UserApiFields.MAX_PHONE_IDENTIFIERS)
    val maxPhoneIdentifiers: Int,

    @SerialName(UserApiFields.MAX_IDENTIFIERS_PER_EXTERNAL_PROVIDER)
    val maxIdentifiersPerExternalProvider: Int,

    @SerialName(UserApiFields.MAX_ACTIVE_SESSIONS_FOR_OPEN_USER)
    val maxActiveSessionsForOpenUser: Int,

    @SerialName(UserApiFields.MAX_ACTIVE_SESSIONS_FOR_MANAGEMENT_USER)
    val maxActiveSessionsForManagementUser: Int,

    @SerialName(UserApiFields.ACCESS_TOKEN_VALIDITY_SECONDS)
    val accessTokenExpirationSeconds: Int,

    @SerialName(UserApiFields.REFRESH_TOKEN_VALIDITY_SECONDS)
    val refreshTokenExpirationSeconds: Int,

    @SerialName(UserApiFields.ACCOUNT_DELETION_GRACE_PERIOD_SECONDS)
    val accountDeletionGracePeriodSeconds: Int,

    @SerialName(UserApiFields.ACCOUNT_DELETION_CHECK_INTERVAL_SECONDS)
    val accountDeletionCheckIntervalSeconds: Int,

    @SerialName(UserApiFields.IS_REGISTRATION_ENABLED)
    val isRegistrationEnabled: Boolean,

    @SerialName(UserApiFields.OPEN_EMAIL_RESTRICTION_POLICY)
    val openEmailRestrictionPolicy: EmailRestrictionPolicyPayload,

    @SerialName(UserApiFields.MANAGEMENT_EMAIL_RESTRICTION_POLICY)
    val managementEmailRestrictionPolicy: EmailRestrictionPolicyPayload
)
