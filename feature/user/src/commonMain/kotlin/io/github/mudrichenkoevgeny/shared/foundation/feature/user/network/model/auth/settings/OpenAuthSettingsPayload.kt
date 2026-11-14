package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.settings

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Wire DTO for the **open** auth-settings slice.
 *
 * @property availableAuthProviders Provider lists; wire values follow [UserAuthProvider.value].
 * @property maxTotalIdentifiers Maximum number of identifiers of any type allowed per account.
 * @property maxEmailIdentifiers Maximum number of email-based identifiers allowed per account.
 * @property maxPhoneIdentifiers Maximum number of phone-based identifiers allowed per account.
 * @property maxIdentifiersPerExternalProvider Maximum number of identifiers allowed for each
 * unique external provider per account.
 * @property userNicknameMinLength Minimum character length for user nicknames.
 * @property userNicknameMaxLength Maximum character length for user nicknames.
 * @property userDisplayNameMinLength Minimum character length for user display names.
 * @property userDisplayNameMaxLength Maximum character length for user display names.
 * @property isUserNicknameChangeAllowed Flag indicating whether users are permitted to modify their nickname.
 * @property isRegistrationEnabled Global flag indicating whether new user registrations are permitted.
 */
@Serializable
public data class OpenAuthSettingsPayload(
    @SerialName(UserApiFields.AVAILABLE_AUTH_PROVIDERS)
    public val availableAuthProviders: AvailableAuthProvidersPayload,
    @SerialName(UserApiFields.MAX_TOTAL_IDENTIFIERS)
    public val maxTotalIdentifiers: Int,
    @SerialName(UserApiFields.MAX_EMAIL_IDENTIFIERS)
    public val maxEmailIdentifiers: Int,
    @SerialName(UserApiFields.MAX_PHONE_IDENTIFIERS)
    public val maxPhoneIdentifiers: Int,
    @SerialName(UserApiFields.MAX_IDENTIFIERS_PER_EXTERNAL_PROVIDER)
    public val maxIdentifiersPerExternalProvider: Int,
    @SerialName(UserApiFields.USER_NICKNAME_MIN_LENGTH)
    public val userNicknameMinLength: Int,
    @SerialName(UserApiFields.USER_NICKNAME_MAX_LENGTH)
    public val userNicknameMaxLength: Int,
    @SerialName(UserApiFields.USER_DISPLAY_NAME_MIN_LENGTH)
    public val userDisplayNameMinLength: Int,
    @SerialName(UserApiFields.USER_DISPLAY_NAME_MAX_LENGTH)
    public val userDisplayNameMaxLength: Int,
    @SerialName(UserApiFields.IS_USER_NICKNAME_CHANGE_ALLOWED)
    public val isUserNicknameChangeAllowed: Boolean,
    @SerialName(UserApiFields.IS_REGISTRATION_ENABLED)
    public val isRegistrationEnabled: Boolean
)
