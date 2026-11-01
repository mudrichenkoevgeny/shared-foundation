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
data class OpenAuthSettingsPayload(
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

    @SerialName(UserApiFields.USER_NICKNAME_MIN_LENGTH)
    val userNicknameMinLength: Int,

    @SerialName(UserApiFields.USER_NICKNAME_MAX_LENGTH)
    val userNicknameMaxLength: Int,

    @SerialName(UserApiFields.USER_DISPLAY_NAME_MIN_LENGTH)
    val userDisplayNameMinLength: Int,

    @SerialName(UserApiFields.USER_DISPLAY_NAME_MAX_LENGTH)
    val userDisplayNameMaxLength: Int,

    @SerialName(UserApiFields.IS_USER_NICKNAME_CHANGE_ALLOWED)
    val isUserNicknameChangeAllowed: Boolean,

    @SerialName(UserApiFields.IS_REGISTRATION_ENABLED)
    val isRegistrationEnabled: Boolean
)
