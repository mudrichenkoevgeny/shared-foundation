package io.github.mudrichenkoevgeny.shared.foundation.generator

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.contract.AuditEventFields
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.audit.resource.CommonAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventId
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventPrivate
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventSummary
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditValueSensitivity
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.listing.AuditFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.listing.AuditSortValues
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.AuditEventMetadata
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CommonAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.status.AuditStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.permissions.AuditPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.contract.AuditApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.model.event.AuditEventMetadataPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.model.event.AuditEventPrivatePayload
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.model.event.AuditEventSummaryPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.navigation.route.management.ManagementAuditNavigation
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.navigation.screen.management.audit.detail.ManagementAuditEventDetailParams
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.navigation.screen.management.audit.list.ManagementAuditEventListParams
import io.github.mudrichenkoevgeny.shared.foundation.core.common.localization.CommonLocales
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.CommonNavigationRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.client.ClientMainNavigation
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.management.ManagementMainNavigation
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.route.base.settings.BaseSettingsRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.security.navigation.route.management.ManagementSecurityNavigation
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.navigation.route.management.ManagementSettingsNavigation
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.email.EmailTemplate
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.contract.UserNavigationPaths
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.route.UserNavigationRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.route.client.ClientAuthNavigation
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.route.client.ClientUserNavigation
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.route.management.ManagementAuthNavigation
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.route.management.ManagementAuthSettingsNavigation
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.route.management.ManagementUsersNavigation
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.route.management.ManagementSelfUserNavigation
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.login.email.LoginByEmailParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.login.phone.LoginByPhoneParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.login.totp.LoginByTotpParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.resetpassword.ResetPasswordParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.client.auth.registration.email.ClientRegistrationByEmailParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.login.totp.LoginByTotpMode
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.totp.TotpStep
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.UnlockMethod
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.identifier.globallist.ManagementGlobalIdentifierListParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.identifier.userlist.ManagementUserIdentifierListParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.session.globallist.ManagementGlobalSessionListParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.session.userlist.ManagementUserSessionListParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.user.detail.UserDetailParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.user.globallist.ManagementUserListParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.user.identifier.detail.IdentifierDetailParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.user.identifier.list.SelfIdentifierListAction
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.user.identifier.list.SelfIdentifierListParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.user.session.detail.SessionDetailParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.user.session.list.SelfSessionListParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.user.totp.main.TotpMainParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.targetinput.UnlockTargetInputParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.otpinput.UnlockOtpInputParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.methodselection.UnlockMethodSelectionParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.success.UnlockSuccessParams
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.route.management.ManagementAuditRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientDeviceId
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientDeviceInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientType
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.theme.ThemeMode
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.CommonSortValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingDefaultValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.permission.PermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.core.common.error.model.ApiErrorResponse
import io.github.mudrichenkoevgeny.shared.foundation.core.common.error.naming.CommonErrorArgs
import io.github.mudrichenkoevgeny.shared.foundation.core.common.error.naming.CommonErrorCodes
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonHttpHeaders
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonWebSocketCloseReasons
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonWebSocketEventTypes
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.WebSocketContract
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.client.ClientDeviceInfoPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.client.ClientInfoPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.websocket.SocketFrame
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.websocket.WebSocketInitializePayload
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.route.base.management.BaseManagementRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.audit.action.SecurityAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.audit.metadata.SecurityAuditMetadataDeniedReasonValues
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.audit.resource.SecurityAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.accountlockout.AccountLockoutPolicy
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.accountlockout.AccountLockoutType
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.crypt.DecryptedString
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.crypt.EncryptedString
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.iprestriction.IpRestrictionPolicy
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.otpconfirmation.OtpConfirmation
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordhash.PasswordHash
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy.ManagementPasswordPolicy
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy.OpenPasswordPolicy
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy.PasswordPolicyFailReason
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.securitysettings.ManagementSecuritySettings
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.securitysettings.OpenSecuritySettings
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.totprecoverycodes.TotpRecoveryCodes
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.totpsetup.TotpSetup
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.verifytotp.VerifyTotp
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.permission.SecurityPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.core.security.error.naming.SecurityErrorArgs
import io.github.mudrichenkoevgeny.shared.foundation.core.security.error.naming.SecurityErrorCodes
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.contract.SecurityApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.contract.SecurityWebSocketEventTypes
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.accountlockout.AccountLockoutPolicyPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.iprestriction.IpRestrictionPolicyPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.otpconfirmation.OtpConfirmationPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.passwordpolicy.ManagementPasswordPolicyPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.passwordpolicy.OpenPasswordPolicyPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.securitysettings.ManagementSecuritySettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.securitysettings.OpenSecuritySettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.totprecoverycodes.TotpRecoveryCodesPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.totpsetup.TotpSetupPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.verifytotp.VerifyTotpPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.route.management.security.settings.ManagementSecuritySettingsRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.route.open.security.settings.OpenSecuritySettingsRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.domain.audit.action.SettingsAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.domain.audit.resource.SettingsAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.domain.model.globalsettings.ManagementGlobalSettings
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.domain.model.globalsettings.OpenGlobalSettings
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.domain.permission.SettingsPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.contract.GlobalSettingsApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.contract.SettingsWebSocketEventTypes
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.model.globalsettings.ManagementGlobalSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.model.globalsettings.OpenGlobalSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.route.management.globalsettings.ManagementGlobalSettingsRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.route.open.globalsettings.OpenGlobalSettingsRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.action.UserAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.metadata.UserAuditMetadataDeniedReasonValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.metadata.UserAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.resource.UserAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.authoritylevel.UserRoleDefaultAuthorityLevel
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.auth.data.AuthData
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.auth.settings.AvailableAuthProviders
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.auth.settings.ManagementAuthSettings
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.auth.settings.OpenAuthSettings
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.ExternalAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.configuration.ManagementUserConfiguration
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.configuration.OpenUserConfiguration
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.emailrestriction.EmailRestrictionPolicy
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSortValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierPrivate
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierSummary
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.DeletedSessions
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionPrivate
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionSummary
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.token.AccessToken
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.token.RefreshToken
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.token.RefreshTokenHash
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.token.SessionToken
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserPrivate
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserSummary
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission.AuthSettingsPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission.IdentifierPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission.SessionPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission.UserPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.error.naming.UserErrorArgs
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.error.naming.UserErrorCodes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserAuthSpec
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserWebSocketCloseReasons
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserWebSocketEventTypes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.data.AuthDataPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.settings.AvailableAuthProvidersPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.settings.ManagementAuthSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.settings.OpenAuthSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.configuration.ManagementUserConfigurationPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.configuration.OpenUserConfigurationPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.emailrestriction.EmailRestrictionPolicyPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.identifier.UserIdentifierPrivatePayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.identifier.UserIdentifierSummaryPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.session.DeletedSessionsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.session.UserSessionPrivatePayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.session.UserSessionSummaryPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.token.RefreshTokenPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.token.SessionTokenPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.user.UserPrivatePayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.user.UserSummaryPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.create.CreateByEmailRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.login.LoginByEmailRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.login.LoginByExternalAuthProviderRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.login.LoginByPhoneRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.password.ResetPasswordRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.password.SendResetPasswordConfirmationRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.registration.RegistrationByEmailRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.unlock.UnlockByEmailConfirmationRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.unlock.UnlockByExternalAuthProviderRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.unlock.UnlockByPhoneConfirmationRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.confirmation.SendConfirmationToEmailRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.confirmation.SendConfirmationToPhoneRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.security.password.EmailPasswordChangeRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.security.useridentifiers.AddUserIdentifierEmailRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.security.useridentifiers.AddUserIdentifierExternalAuthProviderRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.security.useridentifiers.AddUserIdentifierPhoneRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.user.UpdateUserRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.auth.login.SelfManagementLoginRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.auth.refreshtoken.SelfManagementRefreshTokenRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.auth.resetpassword.SelfManagementResetPasswordRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.auth.settings.ManagementAuthSettingsRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.auth.unlock.SelfManagementUnlockRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.configuration.ManagementUserConfigurationRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.users.identifiers.ManagementIdentifiersRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.self.user.identifier.SelfManagementIdentifierRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.users.sessions.ManagementSessionsRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.self.user.session.SelfManagementSessionRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.users.ManagementUsersRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.self.user.SelfManagementUserRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.users.security.ManagementUsersSecurityRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.self.user.security.SelfManagementUserSecurityRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.login.OpenLoginRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.refreshtoken.OpenRefreshTokenRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.registration.OpenRegistrationRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.resetpassword.OpenResetPasswordRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.settings.OpenAuthSettingsRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.unlock.OpenUnlockRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.configuration.OpenUserConfigurationRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.user.identifier.OpenIdentifierRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.user.session.OpenSessionRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.user.OpenUserRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.user.security.OpenUserSecurityRoutes
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.JsonElement
import java.io.File
import java.io.PrintWriter
import java.io.StringWriter
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.companionObjectInstance
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.primaryConstructor

data class EnumInfo(
    val name: String,
    val schemaName: String,
    val entries: List<Pair<String, String>>
)

fun main() {
    try {
        val currentDir = File(System.getProperty("user.dir"))
        val rootDir = if (currentDir.name == "npm-generator") currentDir.parentFile.parentFile else currentDir
        val outputDir = File(rootDir, "build/npm-package")
        outputDir.mkdirs()

        val packageVersion = System.getProperty("PACKAGE_VERSION")
            ?.takeIf { it.isNotBlank() && it != "unspecified" }
            ?: System.getenv("PACKAGE_VERSION")
            ?: parseRootBuildGradleVersion(rootDir)
            ?: "0.0.71"

        val packageJson = """
        {
          "name": "@mudrichenkoevgeny/shared-foundation",
          "version": "$packageVersion",
          "description": "Shared Foundation - TypeScript & Zod contracts and routes",
          "main": "index.js",
          "module": "index.mjs",
          "types": "index.d.ts",
          "exports": {
            ".": {
              "types": "./index.d.ts",
              "import": "./index.mjs",
              "require": "./index.js"
            }
          },
          "dependencies": {
            "zod": "^3.23.0"
          }
        }
        """.trimIndent()

        File(outputDir, "package.json").writeText(packageJson)
        File(outputDir, ".npmignore").writeText("*.tgz\n")

        val npmReadme = """
        # @mudrichenkoevgeny/shared-foundation

        TypeScript & Zod contracts, REST API routes, navigation screen routes, screen parameter schemas, error codes, permission models, runtime enums, branded types, testing fixtures, and domain models generated directly from the [shared-foundation](https://github.com/mudrichenkoevgeny/shared-foundation) repository.

        Provides type-safe REST/WS API contracts, Zod validation schemas, route constants, error codes, permission models, testing fixtures/constants, and domain types for Web, Node.js, and TypeScript applications.

        ## Installation

        ```bash
        # pnpm
        pnpm add @mudrichenkoevgeny/shared-foundation zod

        # npm
        npm install @mudrichenkoevgeny/shared-foundation zod

        # yarn
        yarn add @mudrichenkoevgeny/shared-foundation zod
        ```

        ## Features & Exports

        - **Zod Validation Schemas (`*Schema`)**: Auto-generated `z.ZodType` validation schemas for all Request payloads, Response DTOs, and Screen Parameter models (`managementAuditEventListParamsSchema`, `apiErrorResponseSchema`, `userPrivatePayloadSchema`).
        - **Navigation Screen Routes (`*Navigation`)**: Strongly typed screen routes and parent fallback routes for client and management apps (`ClientMainNavigation`, `ManagementMainNavigation`, `ClientAuthNavigation`, `ClientUserNavigation`, `ManagementUsersNavigation`, `ManagementAuditNavigation`, `ManagementSecurityNavigation`, `ManagementSettingsNavigation`).
        - **REST API Routes**: Strongly typed REST API endpoint paths (`ManagementUsersRoutes`, `ManagementAuditRoutes`, `OpenLoginRoutes`, `SelfManagementUserRoutes`).
        - **Runtime Enums (`.value` representation)**: TS types & runtime JS objects for all domain enums and string unions (`UserRole`, `AuditActorType`, `AuditStatus`, `AccountLockoutType`, `ClientType`, `SortOrder`, `UserSortValues`, `TotpStep`, `UnlockMethod`, etc.).
        - **Error & Permission Codes**: Literal string definitions for all application error codes (`SecurityErrorCodes`, `UserErrorCodes`, `CommonErrorCodes`) and permission strings (`UserPermissionCode`, `SecurityPermissionCode`, `AuditPermissionCode`).
        - **Branded Types & Helpers**: Type-safe domain identifiers (`UserId`, `PermissionCode`, `UserSessionId`, `UserIdentifierId`, `AuditEventId`, `ClientDeviceId`) with converter helpers (`toUserIdOrThrow`, `toPermissionCodeOrNull`).
        - **Testing Fixtures & Collection Generators**: Ready-to-use testing constants (`TEST_*`), single-item mock factories (`createTestUserSummary`, `createTestUserPrivatePayload`), auto-calculating `createTestPagedResult<T>`, generic helper `createTestList<T>`, and deterministic collection generators (`createTest<Entity>List`, `createTest<Entity>PayloadList`) with 0-based index IDs, alternating providers/devices, and time shifting (`TEST_TIME_MILLIS + index * 60_000`). Supports static overrides or callback `(index) => Partial<T>`.
        - **Domain Models & Mappers**: Fully typed TS domain models (`UserSessionSummary`, `UserPrivate`, `AuditEventSummary`) and their runtime mappers from/to API payloads (`toUserPrivate`, `toUserPrivatePayload`, `toAuditEventSummary`).
        - **Localization (`SharedFoundationLocalization`, `*ModuleStrings`, `*Strings`)**: Type-safe localization bundle classes, string formatters (`formatLocalizedString`), and JSON resources (`localizationResources`) for `core/common`, `core/security`, and `feature/user`.

        ## Navigation Screen Routes & Parameter Schemas by Module

        All navigation classes and their Zod parameter validation schemas (`*ParamsSchema`) organized by module/subsystem:

        ### 1. `core/common` (Root Navigation)
        - **`ClientMainNavigation.Main`** (`/`) — Screen Params: None
        - **`ManagementMainNavigation.Main`** (`/`) — Screen Params: None

        ### 2. `core/audit` (Audit Navigation)
        - **`ManagementAuditNavigation`**:
           - `Main` (`/audit`) — Redirect to `/audit/events`
          - `AuditEventList` (`/audit/events`) — Zod Schema: `managementAuditEventListParamsSchema`
          - `AuditEventDetail` (`/audit/events/{event_id}`) — Zod Schema: `managementAuditEventDetailParamsSchema`

        ### 3. `core/security` (Security Settings Navigation)
        - **`ManagementSecurityNavigation`**:
          - `SecuritySettings` (`/settings/security`) — Screen Params: None

        ### 4. `core/settings` (Global Settings Navigation)
        - **`ManagementSettingsNavigation`**:
          - `Main` (`/settings`) — Screen Params: None
          - `GlobalSettings` (`/settings/global`) — Screen Params: None

        ### 5. `feature/user` (IAM / User & Auth Navigation)
        - **`ClientAuthNavigation`** (Client Authentication & Registration):
          - `Auth` (`/auth`) — Redirect to `/auth/login`
          - `Main` (`/auth/login`) — Screen Params: None
          - `LoginByEmail` (`/auth/login/email`) — `loginByEmailParamsSchema`
          - `LoginByPhone` (`/auth/login/phone`) — `loginByPhoneParamsSchema`
          - `LoginByTotp` (`/auth/login/totp`) — `loginByTotpParamsSchema`
          - `Registration` (`/auth/registration`) — `clientRegistrationByEmailParamsSchema`
          - `ResetPassword` (`/auth/reset-password`) — `resetPasswordParamsSchema`
          - `PendingDeletion` (`/auth/login/pending-deletion`) — None
          - `UnlockMethodSelection` (`/auth/unlock`) — `unlockMethodSelectionParamsSchema`
          - `UnlockTargetInput` (`/auth/unlock/input`) — `unlockTargetInputParamsSchema`
          - `UnlockOtpInput` (`/auth/unlock/otp`) — `unlockOtpInputParamsSchema`
          - `UnlockSuccess` (`/auth/unlock/success`) — `unlockSuccessParamsSchema`

        - **`ClientUserNavigation`** (Client Self-Service):
          - `Main` (`/user`) — None
          - `IdentifierList` (`/user/identifiers`) — `selfIdentifierListParamsSchema`
          - `IdentifierDetail` (`/user/identifiers/{user_identifier_id}`) — `identifierDetailParamsSchema`
          - `SessionList` (`/user/sessions`) — `selfSessionListParamsSchema`
          - `SessionDetail` (`/user/sessions/{session_id}`) — `sessionDetailParamsSchema`
          - `TotpMain` (`/user/totp`) — `totpMainParamsSchema`
          - `TotpRecoveryCodes` (`/user/totp/recovery-codes`) — None

        - **`ManagementAuthNavigation`** (Staff Authentication):
          - `Auth` (`/auth`) — Redirect to `/auth/login`
          - `Main` (`/auth/login`) — None
          - `LoginByEmail` (`/auth/login/email`) — `loginByEmailParamsSchema`
          - `LoginByTotp` (`/auth/login/totp`) — `loginByTotpParamsSchema`
          - `ResetPassword` (`/auth/reset-password`) — `resetPasswordParamsSchema`
          - `UnlockMethodSelection` (`/auth/unlock`) — `unlockMethodSelectionParamsSchema`
          - `UnlockTargetInput` (`/auth/unlock/input`) — `unlockTargetInputParamsSchema`
          - `UnlockOtpInput` (`/auth/unlock/otp`) — `unlockOtpInputParamsSchema`
          - `UnlockSuccess` (`/auth/unlock/success`) — `unlockSuccessParamsSchema`

        - **`ManagementAuthSettingsNavigation`** (Auth Policy Settings):
          - `AuthSettings` (`/settings/auth`) — None

        - **`ManagementSelfUserNavigation`** (Staff Self-Service):
          - `Main` (`/self/user`) — None
          - `IdentifierList` (`/self/user/identifiers`) — `selfIdentifierListParamsSchema`
          - `IdentifierDetail` (`/self/user/identifiers/{user_identifier_id}`) — `identifierDetailParamsSchema`
          - `SessionList` (`/self/user/sessions`) — `selfSessionListParamsSchema`
          - `SessionDetail` (`/self/user/sessions/{session_id}`) — `sessionDetailParamsSchema`
          - `TotpMain` (`/self/user/totp`) — `totpMainParamsSchema`
          - `TotpRecoveryCodes` (`/self/user/totp/recovery-codes`) — None

        - **`ManagementUsersNavigation`** (Staff User Administration):
          - `UserList` (`/users`) — `managementUserListParamsSchema`
          - `CreateUser` (`/users/create`) — None
          - `UserDetail` (`/users/{user_id}`) — `userDetailParamsSchema`
          - `UserSessionList` (`/users/{user_id}/sessions`) — `managementUserSessionListParamsSchema`
          - `UserIdentifierList` (`/users/{user_id}/identifiers`) — `managementUserIdentifierListParamsSchema`
          - `GlobalSessionList` (`/sessions`) — `managementGlobalSessionListParamsSchema`
          - `GlobalIdentifierList` (`/identifiers`) — `managementGlobalIdentifierListParamsSchema`
          - `IdentifierDetail` (`/identifiers/{user_identifier_id}`) — `identifierDetailParamsSchema`
          - `SessionDetail` (`/sessions/{session_id}`) — `sessionDetailParamsSchema`

        ## Usage Examples

        ### 1. Validating Screen Parameters (Deep Link & URL Router)

        ```typescript
        import { 
          managementAuditEventListParamsSchema, 
          ManagementAuditEventListParamsParser,
          toQueryMap,
          toQuerySearchString,
          ManagementAuditNavigation 
        } from '@mudrichenkoevgeny/shared-foundation';

        // 1. Parsing raw URL search params into typed params
        function parseSearchParams(rawParams: Record<string, unknown>) {
          const params = ManagementAuditEventListParamsParser.parse(rawParams);
          console.log(params.pageNumber); // Defaults to 1 if omitted
          return params;
        }

        // 2. Serializing typed params back to URL query map or search string
        // Automatically omits default values (pageNumber=1, pageSize=20) to keep URLs clean
        const params = { pageNumber: 1, pageSize: 20, actorIds: ['123'] };
        const queryMap = ManagementAuditEventListParamsParser.toQueryMap(params); // { actor_id: ['123'] }
        const searchString = toQuerySearchString(params); // "actor_id=123"

        // 3. Accessing Screen Route & Parent Route for Fallback Back-Navigation
        const screenRoute = ManagementAuditNavigation.AuditEventList.ROUTE; // "/management/audit/events"
        const parentRoute = ManagementAuditNavigation.AuditEventList.PARENT_ROUTE; // "/"
        ```

        ### 2. Issuing API Requests with Route Constants

        ```typescript
        import { 
          ManagementAuditRoutes, 
          AuditApiPaths 
        } from '@mudrichenkoevgeny/shared-foundation';

        // REST API Path for fetching audit event details
        const eventId = '123e4567-e89b-12d3-a456-426614174000';
        const url = ManagementAuditRoutes.GET_AUDIT_EVENT.replace(`{${AuditApiPaths.EVENT_ID}}`, eventId);
        ```

        ### 3. Validating API Responses with Zod & Error Handling

        ```typescript
        import { 
          apiErrorResponseSchema, 
          SecurityErrorCodes, 
          toUserIdOrThrow 
        } from '@mudrichenkoevgeny/shared-foundation';

        async function fetchUserData(rawResponse: unknown) {
          if (!rawResponse) {
            const error = apiErrorResponseSchema.parse(rawResponse);
            if (error.code === SecurityErrorCodes.PASSWORD_TOO_WEAK) {
              console.error('Password does not meet requirements');
            }
            return;
          }

          const userId = toUserIdOrThrow('123e4567-e89b-12d3-a456-426614174000');
        }
        ```

        ## Source Code & Repository

        The source code for this library and generator is maintained in the main GitHub repository:
        **[GitHub Repository: mudrichenkoevgeny/shared-foundation](https://github.com/mudrichenkoevgeny/shared-foundation)**

        ## License

        Apache License 2.0. See [LICENSE](https://github.com/mudrichenkoevgeny/shared-foundation/blob/main/LICENSE) for details.
        """.trimIndent()

        File(outputDir, "README.md").writeText(npmReadme)

        val brandedCode = generateBrandedTypes()
        val enumCode = generateEnumsJs()
        val compositeParsersJsCode = generateCompositeParsersJs()
        val routeCode = generateRouteObjects()
        val contractCode = generateContractObjects()
        val errorCodeCode = generateErrorCodeObjects()
        val permissionCodeCode = generatePermissionCodeObjects()
        val domainModelsAndMappersCode = generateDomainModelsAndMappersJs()
        val dtoCode = generateDtoSchemasAndTypes()
        val paramParsersJsCode = generateParamParsersAndSerializersJs()
        val testingJsCode = generateTestFixturesJs()
        val testingDtsCode = generateTestFixturesDts()
        val (localizationJsCode, localizationDtsCode) = generateLocalizationCode(rootDir)

        val fullJsContent = """
        import { z } from 'zod';

        $brandedCode

        $enumCode

        $compositeParsersJsCode

        $routeCode

        $contractCode

        $errorCodeCode

        $permissionCodeCode

        $domainModelsAndMappersCode

        $dtoCode

        $paramParsersJsCode

        $testingJsCode

        $localizationJsCode
        """.trimIndent()

        File(outputDir, "index.js").writeText(fullJsContent)
        File(outputDir, "index.mjs").writeText(fullJsContent)

        val dtsContent = generateDeclarationFile(localizationDtsCode, testingDtsCode)
        File(outputDir, "index.d.ts").writeText(dtsContent)

        val localWebSdkDir = File(rootDir.parentFile, "web-platform-sdk/packages/core-common/src/generated")
        if (localWebSdkDir.exists()) {
            outputDir.copyRecursively(localWebSdkDir, overwrite = true)
            println("Synced generated files to local web-platform-sdk: ${localWebSdkDir.absolutePath}")
        }

        println("Successfully generated NPM package in ${outputDir.absolutePath}")
    } catch (e: Throwable) {
        val sw = StringWriter()
        e.printStackTrace(PrintWriter(sw))
        File("build/generator-error.log").apply {
            parentFile.mkdirs()
            writeText(sw.toString())
        }
        throw e
    }
}

private fun generateBrandedTypes(): String {
    val types = listOf(
        "PermissionCode",
        "UserId",
        "UserIdentifierId",
        "UserSessionId",
        "AuditEventId",
        "ClientDeviceId",
        "DecryptedString",
        "EncryptedString",
        "PasswordHash",
        "AccessToken",
        "RefreshToken",
        "RefreshTokenHash"
    )

    return types.joinToString("\n\n") { name ->
        val schemaName = name.decapitalizeFirstLetter() + "Schema"
        """
        export function to${name}OrNull(value) {
          if (!value || typeof value !== 'string' || value.trim() === '') {
            return null;
          }
          return value;
        }

        export function to${name}OrThrow(value) {
          const result = to${name}OrNull(value);
          if (!result) {
            throw new Error('${name} value must not be blank.');
          }
          return result;
        }

        export const $schemaName = z.string().transform((val, ctx) => {
          const result = to${name}OrNull(val);
          if (!result) {
            ctx.addIssue({ code: z.ZodIssueCode.custom, message: 'Invalid ${name}' });
            return z.NEVER;
          }
          return result;
        });
        """.trimIndent()
    }
}

private fun generateCompositeParsersJs(): String {
    return """
    export class CompositeAuditActionTypeParser {
      constructor(auditActionTypes) {
        this.auditActionTypes = auditActionTypes;
      }

      fromValueOrThrow(value) {
        for (const parse of this.auditActionTypes) {
          if (typeof parse === 'function') {
            const result = parse(value);
            if (result !== null && result !== undefined) return result;
          } else if (parse && typeof parse.parseOrNull === 'function') {
            const result = parse.parseOrNull(value);
            if (result !== null && result !== undefined) return result;
          } else if (parse && typeof parse === 'object') {
            // Support passing the enum object directly (e.g. UserAuditActionType)
            const matchedValue = Object.values(parse).find(val => val === value);
            if (matchedValue !== undefined) return matchedValue;
          }
        }
        throw new Error("Unknown value of AuditActionType: '" + value + "'");
      }
    }

    export class CompositeAuditMetadataKeyParser {
      constructor(metadataKeys) {
        this.metadataKeys = metadataKeys;
      }

      fromValueOrThrow(value) {
        for (const parse of this.metadataKeys) {
          if (typeof parse === 'function') {
            const result = parse(value);
            if (result !== null && result !== undefined) return result;
          } else if (parse && typeof parse.parseOrNull === 'function') {
            const result = parse.parseOrNull(value);
            if (result !== null && result !== undefined) return result;
          } else if (parse && typeof parse === 'object') {
            const matchedValue = Object.values(parse).find(val => val === value);
            if (matchedValue !== undefined) return matchedValue;
          }
        }
        throw new Error("Unknown value of AuditMetadataKey: '" + value + "'");
      }
    }

    export class CompositeAuditResourceTypeParser {
      constructor(auditResourceTypes) {
        this.auditResourceTypes = auditResourceTypes;
      }

      fromValueOrThrow(value) {
        for (const parse of this.auditResourceTypes) {
          if (typeof parse === 'function') {
            const result = parse(value);
            if (result !== null && result !== undefined) return result;
          } else if (parse && typeof parse.parseOrNull === 'function') {
            const result = parse.parseOrNull(value);
            if (result !== null && result !== undefined) return result;
          } else if (parse && typeof parse === 'object') {
            const matchedValue = Object.values(parse).find(val => val === value);
            if (matchedValue !== undefined) return matchedValue;
          }
        }
        throw new Error("Unknown value of AuditResourceType: '" + value + "'");
      }
    }
    """.trimIndent()
}

private fun generateCompositeParsersDts(): String {
    return """
    export type AuditActionType = SecurityAuditActionType | SettingsAuditActionType | UserAuditActionType | string;
    export type AuditMetadataKey = CommonAuditMetadataKey | UserAuditMetadataKey | string;
    export type AuditResourceType = CommonAuditResourceType | SecurityAuditResourceType | SettingsAuditResourceType | UserAuditResourceType | string;

    export interface AuditActionTypeParser {
      parseOrNull(value: string): AuditActionType | null;
    }

    export interface AuditMetadataKeyParser {
      parseOrNull(value: string): AuditMetadataKey | null;
    }

    export interface AuditResourceTypeParser {
      parseOrNull(value: string): AuditResourceType | null;
    }

    export declare class CompositeAuditActionTypeParser {
      constructor(auditActionTypes: Array<((value: string) => AuditActionType | null) | AuditActionTypeParser | Record<string, string>>);
      fromValueOrThrow(value: string): AuditActionType;
    }

    export declare class CompositeAuditMetadataKeyParser {
      constructor(metadataKeys: Array<((value: string) => AuditMetadataKey | null) | AuditMetadataKeyParser | Record<string, string>>);
      fromValueOrThrow(value: string): AuditMetadataKey;
    }

    export declare class CompositeAuditResourceTypeParser {
      constructor(auditResourceTypes: Array<((value: string) => AuditResourceType | null) | AuditResourceTypeParser | Record<string, string>>);
      fromValueOrThrow(value: string): AuditResourceType;
    }
    """.trimIndent()
}

private fun extractObjectStringProperties(kClass: KClass<*>): List<Pair<String, String>> {
    val result = mutableListOf<Pair<String, String>>()

    val staticFields = kClass.java.declaredFields
        .filter { java.lang.reflect.Modifier.isStatic(it.modifiers) && it.type == String::class.java }
        .mapNotNull { field ->
            try {
                field.isAccessible = true
                val valName = field.name
                val valValue = field.get(null) as? String
                if (valValue != null) valName to valValue else null
            } catch (_: Exception) { null }
        }
    result.addAll(staticFields)

    val companionInstance = try { kClass.companionObjectInstance } catch (_: Exception) { null }
    if (companionInstance != null) {
        val companionFields = (companionInstance as Any).javaClass.declaredFields
            .filter { it.type == String::class.java }
            .mapNotNull { field ->
                try {
                    field.isAccessible = true
                    val valName = field.name
                    val valValue = field.get(companionInstance) as? String
                    if (valValue != null && result.none { it.first == valName }) valName to valValue else null
                } catch (_: Exception) { null }
            }
        result.addAll(companionFields)
    }

    val instance = kClass.objectInstance
    if (instance != null) {
        val instanceFields = kClass.java.declaredFields
            .filter { it.type == String::class.java }
            .mapNotNull { field ->
                try {
                    field.isAccessible = true
                    val valName = field.name
                    val valValue = field.get(instance) as? String
                    if (valValue != null && result.none { it.first == valName }) valName to valValue else null
                } catch (_: Exception) { null }
            }
        result.addAll(instanceFields)

        val instanceMethods = kClass.java.declaredMethods
            .filter { it.parameterCount == 0 && it.returnType == String::class.java && !java.lang.reflect.Modifier.isStatic(it.modifiers) }
            .mapNotNull { method ->
                try {
                    method.isAccessible = true
                    val name = method.name
                    if (name.startsWith("get") && name.length > 3) {
                        val propName = name.substring(3).replaceFirstChar { it.lowercase() }
                        val valValue = method.invoke(instance) as? String
                        if (valValue != null && result.none { it.first.equals(propName, ignoreCase = true) }) {
                            propName to valValue
                        } else null
                    } else null
                } catch (_: Exception) { null }
            }
        result.addAll(instanceMethods)
    }

    return result
}

data class ClassStructure(
    val directProps: List<Pair<String, String>>,
    val nestedObjects: List<Pair<String, List<Pair<String, String>>>>
)

private fun extractClassStructure(kClass: KClass<*>): ClassStructure {
    val directProps = extractObjectStringProperties(kClass)
    val nestedObjects = mutableListOf<Pair<String, List<Pair<String, String>>>>()

    val nestedClasses = try { kClass.nestedClasses } catch (_: Exception) { emptyList() }
    for (nested in nestedClasses) {
        val nestedName = nested.simpleName ?: continue
        val props = extractObjectStringProperties(nested)
        if (props.isNotEmpty()) {
            nestedObjects.add(nestedName to props)
        }
    }

    return ClassStructure(directProps, nestedObjects)
}

private val routeClasses = listOf(
    ClientMainNavigation::class,
    ManagementMainNavigation::class,
    BaseManagementRoutes::class,
    BaseSettingsRoutes::class,
    CommonNavigationRoutes::class,
    UserNavigationRoutes::class,
    ManagementAuditRoutes::class,
    ManagementAuditNavigation::class,
    ClientAuthNavigation::class,
    ManagementAuthNavigation::class,
    ManagementAuthSettingsNavigation::class,
    ClientUserNavigation::class,
    ManagementSelfUserNavigation::class,
    ManagementUsersNavigation::class,
    ManagementSecurityNavigation::class,
    ManagementSettingsNavigation::class,
    ManagementSecuritySettingsRoutes::class,
    OpenSecuritySettingsRoutes::class,
    ManagementGlobalSettingsRoutes::class,
    OpenGlobalSettingsRoutes::class,
    OpenLoginRoutes::class,
    SelfManagementLoginRoutes::class,
    OpenRefreshTokenRoutes::class,
    SelfManagementRefreshTokenRoutes::class,
    OpenRegistrationRoutes::class,
    OpenResetPasswordRoutes::class,
    SelfManagementResetPasswordRoutes::class,
    OpenUnlockRoutes::class,
    SelfManagementUnlockRoutes::class,
    OpenUserSecurityRoutes::class,
    SelfManagementUserSecurityRoutes::class,
    ManagementUsersSecurityRoutes::class,
    OpenIdentifierRoutes::class,
    SelfManagementIdentifierRoutes::class,
    ManagementIdentifiersRoutes::class,
    OpenSessionRoutes::class,
    SelfManagementSessionRoutes::class,
    ManagementSessionsRoutes::class,
    OpenUserRoutes::class,
    SelfManagementUserRoutes::class,
    ManagementUsersRoutes::class,
    ManagementAuthSettingsRoutes::class,
    OpenAuthSettingsRoutes::class,
    OpenUserConfigurationRoutes::class,
    ManagementUserConfigurationRoutes::class,
    WebSocketContract::class
)

private val contractClasses = listOf(
    CommonLocales::class,
    CommonApiFields::class,
    SecurityApiFields::class,
    GlobalSettingsApiFields::class,
    UserApiFields::class,
    AuditEventFields::class,
    CommonHttpHeaders::class,
    CommonWebSocketCloseReasons::class,
    CommonWebSocketEventTypes::class,
    SecurityWebSocketEventTypes::class,
    SettingsWebSocketEventTypes::class,
    UserWebSocketCloseReasons::class,
    UserWebSocketEventTypes::class,
    UserApiPaths::class,
    UserApiQueryParams::class,
    UserNavigationPaths::class,
    UserAuthSpec::class,
    AuditApiPaths::class,
    UserRoleDefaultAuthorityLevel::class,
    SecurityAuditMetadataDeniedReasonValues::class,
    UserAuditMetadataDeniedReasonValues::class
)

private val errorClasses = listOf(
    CommonErrorCodes::class,
    CommonErrorArgs::class,
    SecurityErrorCodes::class,
    SecurityErrorArgs::class,
    UserErrorCodes::class,
    UserErrorArgs::class
)

private val permClasses = listOf(
    AuditPermissionCode::class,
    SecurityPermissionCode::class,
    SettingsPermissionCode::class,
    AuthSettingsPermissionCode::class,
    IdentifierPermissionCode::class,
    SessionPermissionCode::class,
    UserPermissionCode::class
)

private val enumClasses = listOf(
    ThemeMode::class,
    ClientType::class,
    SortOrder::class,
    AccountLockoutType::class,
    PasswordPolicyFailReason::class,
    AuditActorType::class,
    AuditValueSensitivity::class,
    AuditStatus::class,
    CommonAuditResourceType::class,
    SecurityAuditResourceType::class,
    SettingsAuditResourceType::class,
    UserAuditResourceType::class,
    SecurityAuditActionType::class,
    SettingsAuditActionType::class,
    UserAuditActionType::class,
    CommonAuditMetadataKey::class,
    UserAuditMetadataKey::class,
    UserAccountStatus::class,
    UserRole::class,
    UserAuthProvider::class,
    ExternalAuthProvider::class,
    TotpStep::class,
    LoginByTotpMode::class,
    UnlockMethod::class,
    SelfIdentifierListAction::class,
    UserSortValues.UserSortBy::class,
    UserSortValues.UserIdentifierSortBy::class,
    UserSortValues.UserSessionSortBy::class,
    AuditSortValues.AuditEventSortBy::class,
    CommonSortValues.TimestampSortBy::class
)

private val extraEnumInfos = listOf(
    EnumInfo(
        name = "ConfirmationType",
        schemaName = "confirmationTypeSchema",
        entries = listOf(
            "REGISTRATION_EMAIL" to "REGISTRATION_EMAIL",
            "ADD_EMAIL" to "ADD_EMAIL",
            "PASSWORD_RESET_EMAIL" to "PASSWORD_RESET_EMAIL",
            "LOGIN_PHONE" to "LOGIN_PHONE",
            "ADD_PHONE" to "ADD_PHONE",
            "UNLOCK_EMAIL" to "UNLOCK_EMAIL",
            "UNLOCK_PHONE" to "UNLOCK_PHONE"
        )
    ),
    EnumInfo(
        name = "AppType",
        schemaName = "appTypeSchema",
        entries = listOf(
            "CLIENT" to "CLIENT",
            "MANAGEMENT" to "MANAGEMENT"
        )
    )
)

private fun extractReferencedClasses(type: KType): Set<KClass<*>> {
    val result = mutableSetOf<KClass<*>>()
    val classifier = type.classifier as? KClass<*>
    if (classifier != null) {
        result.add(classifier)
    }
    for (arg in type.arguments) {
        val argType = arg.type
        if (argType != null) {
            result.addAll(extractReferencedClasses(argType))
        }
    }
    return result
}

private fun getDtoDependencies(kClass: KClass<*>, allClasses: Set<KClass<*>>): Set<KClass<*>> {
    val dependencies = mutableSetOf<KClass<*>>()
    val primaryConstructor = kClass.primaryConstructor ?: return dependencies
    for (param in primaryConstructor.parameters) {
        val refClasses = extractReferencedClasses(param.type)
        for (refClass in refClasses) {
            if (refClass != kClass && refClass in allClasses) {
                dependencies.add(refClass)
            }
        }
    }
    return dependencies
}

private fun sortDtosTopologically(classes: List<KClass<*>>): List<KClass<*>> {
    val allClasses = classes.toSet()
    val depMap = classes.associateWith { kClass ->
        getDtoDependencies(kClass, allClasses)
    }

    val sorted = mutableListOf<KClass<*>>()
    val visited = mutableSetOf<KClass<*>>()
    val visiting = mutableSetOf<KClass<*>>()

    fun visit(kClass: KClass<*>) {
        if (kClass in visited) return
        if (kClass in visiting) return
        visiting.add(kClass)
        val deps = depMap[kClass] ?: emptySet()
        for (dep in deps) {
            visit(dep)
        }
        visiting.remove(kClass)
        visited.add(kClass)
        sorted.add(kClass)
    }

    for (kClass in classes) {
        visit(kClass)
    }

    return sorted
}

private fun getDomainPairDependencies(
    pair: Pair<KClass<*>, KClass<*>>,
    allPairs: List<Pair<KClass<*>, KClass<*>>>
): Set<Pair<KClass<*>, KClass<*>>> {
    val dependencies = mutableSetOf<Pair<KClass<*>, KClass<*>>>()
    val (domainClass, payloadClass) = pair
    val domainRefClasses = (domainClass.primaryConstructor?.parameters ?: emptyList())
        .flatMap { extractReferencedClasses(it.type) }
        .toSet()
    val payloadRefClasses = (payloadClass.primaryConstructor?.parameters ?: emptyList())
        .flatMap { extractReferencedClasses(it.type) }
        .toSet()

    for (otherPair in allPairs) {
        if (otherPair == pair) continue
        val (otherDomain, otherPayload) = otherPair
        if (otherDomain in domainRefClasses || otherPayload in payloadRefClasses || otherDomain in payloadRefClasses || otherPayload in domainRefClasses) {
            dependencies.add(otherPair)
        }
    }
    return dependencies
}

private fun sortDomainToDtoTopologically(
    pairs: List<Pair<KClass<*>, KClass<*>>>
): List<Pair<KClass<*>, KClass<*>>> {
    val depMap = pairs.associateWith { pair ->
        getDomainPairDependencies(pair, pairs)
    }

    val sorted = mutableListOf<Pair<KClass<*>, KClass<*>>>()
    val visited = mutableSetOf<Pair<KClass<*>, KClass<*>>>()
    val visiting = mutableSetOf<Pair<KClass<*>, KClass<*>>>()

    fun visit(pair: Pair<KClass<*>, KClass<*>>) {
        if (pair in visited) return
        if (pair in visiting) return
        visiting.add(pair)
        val deps = depMap[pair] ?: emptySet()
        for (dep in deps) {
            visit(dep)
        }
        visiting.remove(pair)
        visited.add(pair)
        sorted.add(pair)
    }

    for (pair in pairs) {
        visit(pair)
    }

    return sorted
}

private val rawDtoClasses = listOf(
    EmailTemplate::class,
    ApiErrorResponse::class,
    ClientDeviceInfoPayload::class,
    AccountLockoutPolicyPayload::class,
    IpRestrictionPolicyPayload::class,
    OtpConfirmationPayload::class,
    ManagementPasswordPolicyPayload::class,
    OpenPasswordPolicyPayload::class,
    ManagementGlobalSettingsPayload::class,
    OpenGlobalSettingsPayload::class,
    AuditEventMetadataPayload::class,
    ManagementAuditEventListParams::class,
    ManagementAuditEventDetailParams::class,
    LoginByEmailParams::class,
    LoginByPhoneParams::class,
    LoginByTotpParams::class,
    ClientRegistrationByEmailParams::class,
    ResetPasswordParams::class,
    UnlockMethodSelectionParams::class,
    UnlockTargetInputParams::class,
    UnlockOtpInputParams::class,
    UnlockSuccessParams::class,
    IdentifierDetailParams::class,
    SelfIdentifierListParams::class,
    SessionDetailParams::class,
    SelfSessionListParams::class,
    TotpMainParams::class,
    ManagementUserListParams::class,
    UserDetailParams::class,
    ManagementUserSessionListParams::class,
    ManagementGlobalSessionListParams::class,
    ManagementUserIdentifierListParams::class,
    ManagementGlobalIdentifierListParams::class,
    AvailableAuthProvidersPayload::class,
    EmailRestrictionPolicyPayload::class,
    SessionTokenPayload::class,
    UserPrivatePayload::class,
    UserSummaryPayload::class,
    UserIdentifierSummaryPayload::class,
    UserIdentifierPrivatePayload::class,
    DeletedSessionsPayload::class,
    RefreshTokenPayload::class,
    TotpRecoveryCodesPayload::class,
    TotpSetupPayload::class,
    VerifyTotpPayload::class,
    SocketFrame::class,
    WebSocketInitializePayload::class,
    ClientInfoPayload::class,
    UserSessionSummaryPayload::class,
    UserSessionPrivatePayload::class,
    AuditEventSummaryPayload::class,
    AuditEventPrivatePayload::class,
    AuthDataPayload::class,
    ManagementAuthSettingsPayload::class,
    OpenAuthSettingsPayload::class,
    ManagementSecuritySettingsPayload::class,
    OpenSecuritySettingsPayload::class,
    ManagementUserConfigurationPayload::class,
    OpenUserConfigurationPayload::class,
    CreateByEmailRequest::class,
    LoginByEmailRequest::class,
    LoginByExternalAuthProviderRequest::class,
    LoginByPhoneRequest::class,
    ResetPasswordRequest::class,
    SendResetPasswordConfirmationRequest::class,
    RegistrationByEmailRequest::class,
    UnlockByEmailConfirmationRequest::class,
    UnlockByExternalAuthProviderRequest::class,
    UnlockByPhoneConfirmationRequest::class,
    SendConfirmationToEmailRequest::class,
    SendConfirmationToPhoneRequest::class,
    EmailPasswordChangeRequest::class,
    AddUserIdentifierEmailRequest::class,
    AddUserIdentifierExternalAuthProviderRequest::class,
    AddUserIdentifierPhoneRequest::class,
    UpdateUserRequest::class
)

private val dtoClasses = sortDtosTopologically(rawDtoClasses)

private fun getEnumInfo(kClass: KClass<*>): EnumInfo {
    val name = kClass.simpleName ?: ""
    val schemaName = name.decapitalizeFirstLetter() + "Schema"
    val entries = mutableListOf<Pair<String, String>>()

    val enumConstants = kClass.java.enumConstants
    if (enumConstants != null && enumConstants.isNotEmpty()) {
        for (enumConst in enumConstants) {
            val key = (enumConst as Enum<*>).name
            val field = try { kClass.java.getField(key) } catch (_: Exception) { null }
            val serialNameAnnot = field?.getAnnotation(SerialName::class.java)?.value
            val serialNameProp = try {
                enumConst.javaClass.getMethod("getSerialName").invoke(enumConst) as? String
            } catch (_: Exception) {
                null
            }
            val wireValue = serialNameAnnot ?: serialNameProp ?: key.lowercase()
            entries.add(key to wireValue)
        }
    } else if (kClass == ExternalAuthProvider::class) {
        for (ext in ExternalAuthProvider.all) {
            entries.add(ext.userAuthProvider.name to ext.userAuthProvider.value)
        }
    }

    if (kClass == UserRole::class) {
        if (entries.none { it.first == "USER" }) {
            entries.add("USER" to "user")
        }
        if (entries.none { it.first == "CLIENT_USER" }) {
            entries.add("CLIENT_USER" to "user")
        }
        if (entries.none { it.first == "MANAGEMENT_USER" }) {
            entries.add("MANAGEMENT_USER" to "admin")
        }
    }

    if (kClass == AccountLockoutType::class) {
        if (entries.none { it.first == "PERMANENT" }) {
            entries.add("PERMANENT" to "indefinite")
        }
    }

    return EnumInfo(name, schemaName, entries)
}

private fun getAllEnumInfos(): List<EnumInfo> {
    return enumClasses.map { getEnumInfo(it) } + extraEnumInfos
}

private fun generateRouteObjects(): String {
    return routeClasses.joinToString("\n\n") { kClass ->
        val name = kClass.simpleName ?: ""
        val structure = extractClassStructure(kClass)

        val directFields = structure.directProps.map { (valName, valValue) -> "  $valName: \"$valValue\"" }
        val nestedFields = structure.nestedObjects.map { (nestedName, nestedProps) ->
            val propsCode = nestedProps.joinToString(",\n    ") { (vName, vVal) -> "$vName: \"$vVal\"" }
            "  $nestedName: {\n    $propsCode\n  }"
        }

        val allFields = directFields + nestedFields

        """
        export const $name = {
        ${allFields.joinToString(",\n")}
        };
        """.trimIndent()
    }
}

private fun generateContractObjects(): String {
    val standardContracts = contractClasses.joinToString("\n\n") { kClass ->
        val name = kClass.simpleName ?: ""
        val props = extractObjectStringProperties(kClass)
        val fields = props.map { (valName, valValue) -> "  $valName: \"$valValue\"" }

        """
        export const $name = {
        ${fields.joinToString(",\n")}
        };
        """.trimIndent()
    }

    val listingParamNamesCode = """
    export const ListingParamNames = {
      PAGE_NUMBER: "${ListingParamNames.Pagination.PAGE_NUMBER}",
      PAGE_SIZE: "${ListingParamNames.Pagination.PAGE_SIZE}",
      SORT_BY: "${ListingParamNames.Sort.SORT_BY}",
      SORT_ORDER: "${ListingParamNames.Sort.SORT_ORDER}",
      Pagination: {
        PAGE_NUMBER: "${ListingParamNames.Pagination.PAGE_NUMBER}",
        PAGE_SIZE: "${ListingParamNames.Pagination.PAGE_SIZE}"
      },
      Sort: {
        SORT_BY: "${ListingParamNames.Sort.SORT_BY}",
        SORT_ORDER: "${ListingParamNames.Sort.SORT_ORDER}"
      }
    };

    export const ListingDefaultValues = {
      PAGE_NUMBER: ${ListingDefaultValues.Pagination.PAGE_NUMBER},
      PAGE_SIZE: ${ListingDefaultValues.Pagination.PAGE_SIZE},
      Pagination: {
        PAGE_NUMBER: ${ListingDefaultValues.Pagination.PAGE_NUMBER},
        PAGE_SIZE: ${ListingDefaultValues.Pagination.PAGE_SIZE}
      }
    };

    export const AuditFilterValues = {
      AuditEventFilterValues: {
        ACTOR_ID: "${AuditFilterValues.AuditEventFilterValues.ACTOR_ID}",
        ACTOR_TYPE: "${AuditFilterValues.AuditEventFilterValues.ACTOR_TYPE}",
        ACTOR_USER_ROLE: "${AuditFilterValues.AuditEventFilterValues.ACTOR_USER_ROLE}",
        ACTION: "${AuditFilterValues.AuditEventFilterValues.ACTION}",
        RESOURCE: "${AuditFilterValues.AuditEventFilterValues.RESOURCE}",
        RESOURCE_ID: "${AuditFilterValues.AuditEventFilterValues.RESOURCE_ID}",
        STATUS: "${AuditFilterValues.AuditEventFilterValues.STATUS}",
        MESSAGE: "${AuditFilterValues.AuditEventFilterValues.MESSAGE}"
      }
    };

    export const UserFilterValues = {
      UserFilterValues: {
        ROLE: "${UserFilterValues.UserFilterValues.ROLE}",
        ACCOUNT_STATUS: "${UserFilterValues.UserFilterValues.ACCOUNT_STATUS}",
        ACCOUNT_STATUS_ON_RESTORE: "${UserFilterValues.UserFilterValues.ACCOUNT_STATUS_ON_RESTORE}",
        ACCOUNT_LOCKOUT_TYPE: "${UserFilterValues.UserFilterValues.ACCOUNT_LOCKOUT_TYPE}",
        AUTHORITY_LEVEL_FROM: "${UserFilterValues.UserFilterValues.AUTHORITY_LEVEL_FROM}",
        AUTHORITY_LEVEL_TO: "${UserFilterValues.UserFilterValues.AUTHORITY_LEVEL_TO}",
        IS_TOTP_ENABLED: "${UserFilterValues.UserFilterValues.IS_TOTP_ENABLED}",
        PERMISSION_CODES: "${UserFilterValues.UserFilterValues.PERMISSION_CODES}"
      },
      UserIdentifierFilterValues: {
        USER_ID: "${UserFilterValues.UserIdentifierFilterValues.USER_ID}",
        USER_AUTH_PROVIDER: "${UserFilterValues.UserIdentifierFilterValues.USER_AUTH_PROVIDER}",
        IDENTIFIER: "${UserFilterValues.UserIdentifierFilterValues.IDENTIFIER}"
      },
      UserSessionFilterValues: {
        USER_ID: "${UserFilterValues.UserSessionFilterValues.USER_ID}",
        USER_ROLE: "${UserFilterValues.UserSessionFilterValues.USER_ROLE}",
        IDENTIFIER_ID: "${UserFilterValues.UserSessionFilterValues.IDENTIFIER_ID}",
        USER_AUTH_PROVIDER: "${UserFilterValues.UserSessionFilterValues.USER_AUTH_PROVIDER}",
        CLIENT_TYPE: "${UserFilterValues.UserSessionFilterValues.CLIENT_TYPE}",
        USER_AGENT: "${UserFilterValues.UserSessionFilterValues.USER_AGENT}",
        IP_ADDRESS: "${UserFilterValues.UserSessionFilterValues.IP_ADDRESS}",
        LANGUAGE: "${UserFilterValues.UserSessionFilterValues.LANGUAGE}",
        DEVICE_ID: "${UserFilterValues.UserSessionFilterValues.DEVICE_ID}",
        DEVICE_NAME: "${UserFilterValues.UserSessionFilterValues.DEVICE_NAME}",
        APP_VERSION: "${UserFilterValues.UserSessionFilterValues.APP_VERSION}",
        OPERATION_SYSTEM_VERSION: "${UserFilterValues.UserSessionFilterValues.OPERATION_SYSTEM_VERSION}",
        IDENTIFIER: "${UserFilterValues.UserSessionFilterValues.IDENTIFIER}"
      }
    };
    """.trimIndent()

    return "$standardContracts\n\n$listingParamNamesCode"
}

private fun generateErrorCodeObjects(): String {
    return errorClasses.joinToString("\n\n") { kClass ->
        val name = kClass.simpleName ?: ""
        val props = extractObjectStringProperties(kClass)
        val fields = props.map { (valName, valValue) -> "  $valName: \"$valValue\"" }

        """
        export const $name = {
        ${fields.joinToString(",\n")}
        };
        """.trimIndent()
    }
}

private fun extractPermissionCodeProperties(kClass: KClass<*>): List<Pair<String, String>> {
    val instance = kClass.objectInstance ?: return emptyList()

    val fields = kClass.java.declaredFields
        .filter { PermissionCode::class.java.isAssignableFrom(it.type) }
        .mapNotNull { field ->
            field.isAccessible = true
            val permObj = field.get(instance) as? PermissionCode ?: field.get(null) as? PermissionCode ?: return@mapNotNull null
            field.name to permObj.value
        }

    if (fields.isNotEmpty()) return fields

    val methods = kClass.java.declaredMethods
        .filter { PermissionCode::class.java.isAssignableFrom(it.returnType) && it.parameterCount == 0 }
        .mapNotNull { method ->
            method.isAccessible = true
            val name = method.name.removePrefix("get")
            val permObj = method.invoke(instance) as? PermissionCode ?: return@mapNotNull null
            name to permObj.value
        }

    return methods
}

private fun generatePermissionCodeObjects(): String {
    return permClasses.joinToString("\n\n") { kClass ->
        val name = kClass.simpleName ?: ""
        val props = extractPermissionCodeProperties(kClass)
        val fields = props.map { (valName, valValue) -> "  $valName: toPermissionCodeOrThrow(\"$valValue\")" }

        """
        export const $name = {
        ${fields.joinToString(",\n")}
        };
        """.trimIndent()
    }
}

private fun generateEnumsJs(): String {
    val enumJsBlocks = getAllEnumInfos().map { info ->
        val schemaValues = info.entries.map { it.second }.distinct().joinToString(", ") { "\"$it\"" }
        val objectFields = info.entries.joinToString(",\n") { (key, wire) -> "  $key: \"$wire\"" }

        """
        export const ${info.schemaName} = z.enum([$schemaValues]);
        export const ${info.name} = {
        $objectFields
        };
        """.trimIndent()
    }

    val extraJs = """
    export const confirmationKeySchema = z.object({
      type: confirmationTypeSchema,
      identifier: z.string()
    });

    export const UserSortValues = {
      UserSortBy,
      UserIdentifierSortBy,
      UserSessionSortBy
    };
    """.trimIndent()

    return (enumJsBlocks + listOf(extraJs)).joinToString("\n\n")
}

private fun generateDtoSchemasAndTypes(): String {
    val pagedResultCode = """
    export function pagedResultSchema(itemSchema) {
      return z.object({
        items: z.array(itemSchema),
        total_count: z.number(),
        page_number: z.number(),
        page_size: z.number(),
        total_pages: z.number()
      }).transform(data => ({
        items: data.items,
        totalCount: data.total_count,
        pageNumber: data.page_number,
        pageSize: data.page_size,
        totalPages: data.total_pages
      }));
    }
    """.trimIndent()

    val generatedDtos = dtoClasses.joinToString("\n\n") { kClass ->
        generateSingleDtoSchema(kClass)
    }

    return "$pagedResultCode\n\n$generatedDtos"
}

private fun generateSingleDtoSchema(kClass: KClass<*>): String {
    val name = kClass.simpleName ?: ""
    val schemaName = name.decapitalizeFirstLetter() + "Schema"

    val primaryConstructor = kClass.primaryConstructor ?: return ""

    val fields = primaryConstructor.parameters.map { param ->
        val paramName = param.name ?: ""

        val serialNameAnnotation = param.annotations.filterIsInstance<SerialName>().firstOrNull()
            ?: kClass.declaredMemberProperties.firstOrNull { it.name == paramName }?.findAnnotation<SerialName>()

        val wireKey = serialNameAnnotation?.value ?: paramName

        val type = param.type
        var zodType = resolveZodType(paramName, type, kClass)

        if (param.isOptional) {
            zodType = when {
                zodType.startsWith("z.array(") -> "$zodType.default([])"
                zodType == "z.number()" && paramName == "pageNumber" -> "$zodType.default(ListingDefaultValues.PAGE_NUMBER)"
                zodType == "z.number()" && paramName == "pageSize" -> "$zodType.default(ListingDefaultValues.PAGE_SIZE)"
                zodType.contains(".nullable()") -> "$zodType.default(null)"
                else -> "$zodType.optional()"
            }
        }

        "    $wireKey: $zodType"
    }

    return """
    export const $schemaName = z.object({
    ${fields.joinToString(",\n")}
    });
    """.trimIndent()
}

private fun resolveZodType(paramName: String, type: KType, parentClass: KClass<*>): String {
    val classifier = type.classifier as? KClass<*> ?: return "z.unknown()"
    val isMarkedNullable = type.isMarkedNullable

    val baseZod = when {
        classifier == UserAuthProvider::class -> "userAuthProviderSchema"
        classifier == ExternalAuthProvider::class -> "externalAuthProviderSchema"
        classifier == UserRole::class -> "userRoleSchema"
        classifier == UserAccountStatus::class -> "userAccountStatusSchema"
        classifier == AccountLockoutType::class -> "accountLockoutTypeSchema"
        classifier == AuditActorType::class -> "auditActorTypeSchema"
        classifier == AuditValueSensitivity::class -> "auditValueSensitivitySchema"
        classifier == AuditStatus::class -> "auditStatusSchema"
        classifier == CommonAuditMetadataKey::class -> "commonAuditMetadataKeySchema"
        classifier == UserAuditMetadataKey::class -> "userAuditMetadataKeySchema"
        classifier == ClientType::class -> "clientTypeSchema"
        classifier == SortOrder::class -> "sortOrderSchema"
        classifier == PasswordPolicyFailReason::class -> "passwordPolicyFailReasonSchema"
        classifier == TotpStep::class -> "totpStepSchema"
        classifier == LoginByTotpMode::class -> "loginByTotpModeSchema"
        classifier == UnlockMethod::class -> "unlockMethodSchema"
        classifier == UserSortValues.UserSortBy::class -> "userSortBySchema"
        classifier == UserSortValues.UserIdentifierSortBy::class -> "userIdentifierSortBySchema"
        classifier == UserSortValues.UserSessionSortBy::class -> "userSessionSortBySchema"
        classifier == AuditSortValues.AuditEventSortBy::class -> "auditEventSortBySchema"
        classifier == CommonSortValues.TimestampSortBy::class -> "timestampSortBySchema"
        classifier == JsonElement::class -> "z.unknown()"

        paramName == "totpStep" -> "totpStepSchema"
        paramName == "loginByTotpMode" || (paramName == "mode" && parentClass == LoginByTotpParams::class) -> "loginByTotpModeSchema"
        paramName == "unlockMethod" -> "unlockMethodSchema"
        paramName == "clientType" -> "clientTypeSchema"
        paramName == "deviceId" || paramName == "clientDeviceId" -> "clientDeviceIdSchema"
        paramName == "role" || paramName == "userRole" || paramName == "actorUserRole" -> "userRoleSchema"
        paramName == "accountStatus" || paramName == "accountStatusOnRestore" -> "userAccountStatusSchema"
        paramName == "lockoutType" -> "accountLockoutTypeSchema"
        paramName == "actorType" -> "auditActorTypeSchema"
        paramName == "status" && (parentClass == AuditEventSummaryPayload::class || parentClass == AuditEventPrivatePayload::class) -> "auditStatusSchema"
        paramName == "identifierAuthProvider" || paramName == "userAuthProvider" -> "userAuthProviderSchema"
        paramName == "sensitivity" || paramName == "valueSensitivity" -> "auditValueSensitivitySchema"
        paramName == "permissionCodes" -> "z.array(permissionCodeSchema)"

        classifier == String::class -> {
            when (paramName) {
                "id" -> when (parentClass) {
                    UserSessionSummaryPayload::class, UserSessionPrivatePayload::class -> "userSessionIdSchema"
                    AuditEventSummaryPayload::class, AuditEventPrivatePayload::class -> "auditEventIdSchema"
                    UserIdentifierSummaryPayload::class, UserIdentifierPrivatePayload::class -> "userIdentifierIdSchema"
                    else -> "userIdSchema"
                }
                "userId" -> "userIdSchema"
                "identifierId" -> "userIdentifierIdSchema"
                "sessionId" -> "userSessionIdSchema"
                "actorId" -> "userIdSchema"
                "permissionCode" -> "permissionCodeSchema"
                else -> "z.string()"
            }
        }
        classifier == Int::class || classifier == Long::class || classifier == Float::class || classifier == Double::class || classifier == Short::class || classifier == Byte::class -> "z.number()"
        classifier == Boolean::class -> "z.boolean()"
        classifier == PermissionCode::class -> "permissionCodeSchema"
        classifier == UserId::class -> "userIdSchema"
        classifier == UserIdentifierId::class -> "userIdentifierIdSchema"
        classifier == UserSessionId::class -> "userSessionIdSchema"
        classifier == AuditEventId::class -> "auditEventIdSchema"
        classifier == ClientDeviceId::class -> "clientDeviceIdSchema"
        classifier == DecryptedString::class -> "decryptedStringSchema"
        classifier == EncryptedString::class -> "encryptedStringSchema"
        classifier == PasswordHash::class -> "passwordHashSchema"
        classifier == AccessToken::class -> "accessTokenSchema"
        classifier == RefreshToken::class -> "refreshTokenSchema"
        classifier == RefreshTokenHash::class -> "refreshTokenHashSchema"
        classifier == List::class || classifier == Set::class || classifier == Collection::class || classifier.simpleName == "Set" || classifier.simpleName == "List" || classifier.simpleName == "Collection" -> {
            val elementType = type.arguments.firstOrNull()?.type
            val elementZod = if (elementType != null) resolveZodType(paramName, elementType, parentClass) else "z.unknown()"
            "z.array($elementZod)"
        }
        classifier == Map::class || classifier.simpleName == "Map" -> {
            val valType = type.arguments.getOrNull(1)?.type
            val valZod = if (valType != null) resolveZodType(paramName, valType, parentClass) else "z.unknown()"
            "z.record(z.string(), $valZod)"
        }
        else -> {
            classifier.simpleName?.decapitalizeFirstLetter() + "Schema"
        }
    }

    return if (isMarkedNullable) "$baseZod.nullable()" else baseZod
}

private fun resolveTsType(paramName: String, type: KType, parentClass: KClass<*>): String {
    val classifier = type.classifier as? KClass<*> ?: return "unknown"
    val isMarkedNullable = type.isMarkedNullable

    val baseTs = when {
        classifier == UserAuthProvider::class -> "UserAuthProvider"
        classifier == ExternalAuthProvider::class -> "ExternalAuthProvider"
        classifier == UserRole::class -> "UserRole"
        classifier == UserAccountStatus::class -> "UserAccountStatus"
        classifier == AccountLockoutType::class -> "AccountLockoutType"
        classifier == AuditActorType::class -> "AuditActorType"
        classifier == AuditValueSensitivity::class -> "AuditValueSensitivity"
        classifier == AuditStatus::class -> "AuditStatus"
        classifier == CommonAuditMetadataKey::class -> "CommonAuditMetadataKey"
        classifier == UserAuditMetadataKey::class -> "UserAuditMetadataKey"
        classifier == ClientType::class -> "ClientType"
        classifier == SortOrder::class -> "SortOrder"
        classifier == PasswordPolicyFailReason::class -> "PasswordPolicyFailReason"
        classifier == TotpStep::class -> "TotpStep"
        classifier == LoginByTotpMode::class -> "LoginByTotpMode"
        classifier == UnlockMethod::class -> "UnlockMethod"
        classifier == UserSortValues.UserSortBy::class -> "UserSortBy"
        classifier == UserSortValues.UserIdentifierSortBy::class -> "UserIdentifierSortBy"
        classifier == UserSortValues.UserSessionSortBy::class -> "UserSessionSortBy"
        classifier == AuditSortValues.AuditEventSortBy::class -> "AuditEventSortBy"
        classifier == CommonSortValues.TimestampSortBy::class -> "TimestampSortBy"
        classifier == JsonElement::class -> "unknown"
        classifier.simpleName == "Instant" -> "Instant"

        paramName == "totpStep" -> "TotpStep"
        paramName == "loginByTotpMode" || (paramName == "mode" && parentClass == LoginByTotpParams::class) -> "LoginByTotpMode"
        paramName == "unlockMethod" -> "UnlockMethod"
        paramName == "clientType" -> "ClientType"
        paramName == "deviceId" || paramName == "clientDeviceId" -> "ClientDeviceId"
        paramName == "role" || paramName == "userRole" || paramName == "actorUserRole" -> "UserRole"
        paramName == "accountStatus" || paramName == "accountStatusOnRestore" -> "UserAccountStatus"
        paramName == "lockoutType" -> "AccountLockoutType"
        paramName == "actorType" -> "AuditActorType"
        paramName == "status" && (parentClass == AuditEventSummaryPayload::class || parentClass == AuditEventPrivatePayload::class) -> "AuditStatus"
        paramName == "identifierAuthProvider" || paramName == "userAuthProvider" -> "UserAuthProvider"
        paramName == "sensitivity" || paramName == "valueSensitivity" -> "AuditValueSensitivity"
        paramName == "permissionCodes" -> "PermissionCode[]"

        classifier == String::class -> {
            when (paramName) {
                "id" -> when (parentClass) {
                    UserSessionSummaryPayload::class, UserSessionPrivatePayload::class -> "UserSessionId"
                    AuditEventSummaryPayload::class, AuditEventPrivatePayload::class -> "AuditEventId"
                    UserIdentifierSummaryPayload::class, UserIdentifierPrivatePayload::class -> "UserIdentifierId"
                    else -> "UserId"
                }
                "userId" -> "UserId"
                "identifierId" -> "UserIdentifierId"
                "sessionId" -> "UserSessionId"
                "actorId" -> "UserId"
                "permissionCode" -> "PermissionCode"
                else -> "string"
            }
        }
        classifier == Int::class || classifier == Long::class || classifier == Float::class || classifier == Double::class || classifier == Short::class || classifier == Byte::class -> "number"
        classifier == Boolean::class -> "boolean"
        classifier == PermissionCode::class -> "PermissionCode"
        classifier == UserId::class -> "UserId"
        classifier == UserIdentifierId::class -> "UserIdentifierId"
        classifier == UserSessionId::class -> "UserSessionId"
        classifier == AuditEventId::class -> "AuditEventId"
        classifier == ClientDeviceId::class -> "ClientDeviceId"
        classifier == DecryptedString::class -> "DecryptedString"
        classifier == EncryptedString::class -> "EncryptedString"
        classifier == PasswordHash::class -> "PasswordHash"
        classifier == AccessToken::class -> "AccessToken"
        classifier == RefreshToken::class -> "RefreshToken"
        classifier == RefreshTokenHash::class -> "RefreshTokenHash"
        classifier == List::class || classifier == Set::class || classifier == Collection::class || classifier.simpleName == "Set" || classifier.simpleName == "List" || classifier.simpleName == "Collection" -> {
            val elementType = type.arguments.firstOrNull()?.type
            val elementTs = if (elementType != null) resolveTsType(paramName, elementType, parentClass) else "unknown"
            "$elementTs[]"
        }
        classifier == Map::class || classifier.simpleName == "Map" -> {
            val valType = type.arguments.getOrNull(1)?.type
            val valTs = if (valType != null) resolveTsType(paramName, valType, parentClass) else "unknown"
            "Record<string, $valTs>"
        }
        else -> {
            classifier.simpleName ?: "unknown"
        }
    }

    return if (isMarkedNullable) "$baseTs | null" else baseTs
}

private val sourceFileMap: Map<String, File> by lazy {
    val currentDir = File(System.getProperty("user.dir"))
    val rootDir = if (currentDir.name == "npm-generator") currentDir.parentFile.parentFile else currentDir
    val sourceDir = File(rootDir, "shared/foundation")
    val map = mutableMapOf<String, File>()
    if (sourceDir.exists()) {
        sourceDir.walkTopDown().filter { it.isFile && it.extension == "kt" }.forEach { file ->
            map[file.nameWithoutExtension] = file
        }
    }
    map
}

private fun extractKDoc(kClass: KClass<*>, memberName: String? = null, nestedClassName: String? = null): String? {
    val className = kClass.simpleName ?: return null
    val file = sourceFileMap[className] ?: return null
    val fullText = file.readText()

    val text = if (nestedClassName != null) {
        val nestedBlockRegex = Regex("""(?:object|class)\s+$nestedClassName\b[\s\S]*?(?=(?:(?:object|class)\s+\w+\b)|$)""")
        nestedBlockRegex.find(fullText)?.value ?: fullText
    } else {
        fullText
    }

    val targetName = memberName ?: nestedClassName ?: className

    val declRegex = Regex("""(?:internal\s+|public\s+|private\s+|const\s+|override\s+|val\s+|var\s+|fun\s+|object\s+|class\s+|interface\s+|enum\s+class\s+)+$targetName\b""")
    val declMatch = declRegex.find(text)
    if (declMatch != null) {
        val declStart = declMatch.range.first
        val prefix = text.substring(0, declStart)
        val lastDocStart = prefix.lastIndexOf("/**")
        if (lastDocStart != -1) {
            val lastDocEnd = prefix.indexOf("*/", lastDocStart)
            if (lastDocEnd != -1 && lastDocEnd > lastDocStart) {
                val between = prefix.substring(lastDocEnd + 2)
                val hasOtherDecl = between.contains("const ") || between.contains("val ") || between.contains("var ") || between.contains("object ") || between.contains("class ")
                if (!hasOtherDecl) {
                    val rawDoc = prefix.substring(lastDocStart + 3, lastDocEnd)
                    val cleaned = rawDoc.lines()
                        .map { it.trimStart().removePrefix("*").trim() }
                        .filter { !it.startsWith("@param") && !it.startsWith("@return") }
                        .joinToString("\n * ") { it.cleanKDocLinks() }
                        .trim()

                    if (cleaned.isNotBlank()) {
                        return "/**\n * $cleaned\n */"
                    }
                }
            }
        }
    }

    if (memberName != null) {
        val classKDocMatch = Regex("""/\*\*([\s\S]*?)\*/\s*(?:@[^\r\n]+\s*)*(?:class|object|interface|enum\s+class)\s+$className\b""").find(fullText)
        if (classKDocMatch != null) {
            val classKDocBody = classKDocMatch.groupValues[1]
            val propRegex = Regex("""@property\s+$memberName\b\s+([^\r\n*]+)""")
            val propMatch = propRegex.find(classKDocBody)
            if (propMatch != null) {
                val desc = propMatch.groupValues[1].trim().cleanKDocLinks()
                return "/**\n * $desc\n */"
            }
        }
    }

    return null
}

private fun String.cleanKDocLinks(): String {
    return this.replace(Regex("""\[([^\]]+)\]""")) { match ->
        val inner = match.groupValues[1]
        inner.substringAfterLast('.')
    }
}

private fun String.formatAsJsDoc(indent: String = ""): String {
    val lines = this.lines()
    if (indent.isEmpty()) return lines.joinToString("\n")
    return lines.joinToString("\n") { line ->
        if (line.isBlank()) line else "$indent${line.trimStart()}"
    }
}

private fun generateDeclarationFile(localizationDtsCode: String, testingDtsCode: String): String {
    val routeDeclarations = routeClasses.joinToString("\n\n") { kClass ->
        val name = kClass.simpleName ?: ""
        val classDoc = extractKDoc(kClass)?.let { "$it\n" } ?: ""
        val structure = extractClassStructure(kClass)

        val directFields = structure.directProps.map { (valName, valValue) ->
            val fieldDoc = extractKDoc(kClass, valName)?.formatAsJsDoc("    ")?.let { "$it\n" } ?: ""
            "${fieldDoc}    readonly $valName: \"$valValue\";"
        }

        val nestedFields = structure.nestedObjects.map { (nestedName, nestedProps) ->
            val nestedDoc = extractKDoc(kClass, nestedClassName = nestedName)?.formatAsJsDoc("    ")?.let { "$it\n" } ?: ""
            val propsCode = nestedProps.joinToString("\n") { (vName, vVal) ->
                val vDoc = extractKDoc(kClass, memberName = vName, nestedClassName = nestedName)?.formatAsJsDoc("      ")?.let { "$it\n" } ?: ""
                "${vDoc}      readonly $vName: \"$vVal\";"
            }
            "${nestedDoc}    readonly $nestedName: {\n$propsCode\n    };"
        }

        val allFields = directFields + nestedFields

        """
        $classDoc export declare const $name: {
        ${allFields.joinToString("\n")}
        };
        """.trimIndent()
    }

    val contractDeclarations = contractClasses.joinToString("\n\n") { kClass ->
        val name = kClass.simpleName ?: ""
        val classDoc = extractKDoc(kClass)?.let { "$it\n" } ?: ""
        val props = extractObjectStringProperties(kClass)
        val fields = props.map { (valName, valValue) ->
            val fieldDoc = extractKDoc(kClass, valName)?.formatAsJsDoc("    ")?.let { "$it\n" } ?: ""
            "${fieldDoc}    readonly $valName: \"$valValue\";"
        }
        """
        $classDoc export declare const $name: {
        ${fields.joinToString("\n")}
        };
        """.trimIndent()
    }

    val listingParamNamesDecl = """
    export declare const ListingParamNames: {
      readonly PAGE_NUMBER: "${ListingParamNames.Pagination.PAGE_NUMBER}";
      readonly PAGE_SIZE: "${ListingParamNames.Pagination.PAGE_SIZE}";
      readonly SORT_BY: "${ListingParamNames.Sort.SORT_BY}";
      readonly SORT_ORDER: "${ListingParamNames.Sort.SORT_ORDER}";
      readonly Pagination: {
        readonly PAGE_NUMBER: "${ListingParamNames.Pagination.PAGE_NUMBER}";
        readonly PAGE_SIZE: "${ListingParamNames.Pagination.PAGE_SIZE}";
      };
      readonly Sort: {
        readonly SORT_BY: "${ListingParamNames.Sort.SORT_BY}";
        readonly SORT_ORDER: "${ListingParamNames.Sort.SORT_ORDER}";
      };
    };

    export declare const ListingDefaultValues: {
      readonly PAGE_NUMBER: ${ListingDefaultValues.Pagination.PAGE_NUMBER};
      readonly PAGE_SIZE: ${ListingDefaultValues.Pagination.PAGE_SIZE};
      readonly Pagination: {
        readonly PAGE_NUMBER: ${ListingDefaultValues.Pagination.PAGE_NUMBER};
        readonly PAGE_SIZE: ${ListingDefaultValues.Pagination.PAGE_SIZE};
      };
    };

    export declare const AuditFilterValues: {
      readonly AuditEventFilterValues: {
        readonly ACTOR_ID: "${AuditFilterValues.AuditEventFilterValues.ACTOR_ID}";
        readonly ACTOR_TYPE: "${AuditFilterValues.AuditEventFilterValues.ACTOR_TYPE}";
        readonly ACTOR_USER_ROLE: "${AuditFilterValues.AuditEventFilterValues.ACTOR_USER_ROLE}";
        readonly ACTION: "${AuditFilterValues.AuditEventFilterValues.ACTION}";
        readonly RESOURCE: "${AuditFilterValues.AuditEventFilterValues.RESOURCE}";
        readonly RESOURCE_ID: "${AuditFilterValues.AuditEventFilterValues.RESOURCE_ID}";
        readonly STATUS: "${AuditFilterValues.AuditEventFilterValues.STATUS}";
        readonly MESSAGE: "${AuditFilterValues.AuditEventFilterValues.MESSAGE}";
      };
    };

    export declare const UserFilterValues: {
      readonly UserFilterValues: {
        readonly ROLE: "${UserFilterValues.UserFilterValues.ROLE}";
        readonly ACCOUNT_STATUS: "${UserFilterValues.UserFilterValues.ACCOUNT_STATUS}";
        readonly ACCOUNT_STATUS_ON_RESTORE: "${UserFilterValues.UserFilterValues.ACCOUNT_STATUS_ON_RESTORE}";
        readonly ACCOUNT_LOCKOUT_TYPE: "${UserFilterValues.UserFilterValues.ACCOUNT_LOCKOUT_TYPE}";
        readonly AUTHORITY_LEVEL_FROM: "${UserFilterValues.UserFilterValues.AUTHORITY_LEVEL_FROM}";
        readonly AUTHORITY_LEVEL_TO: "${UserFilterValues.UserFilterValues.AUTHORITY_LEVEL_TO}";
        readonly IS_TOTP_ENABLED: "${UserFilterValues.UserFilterValues.IS_TOTP_ENABLED}";
        readonly PERMISSION_CODES: "${UserFilterValues.UserFilterValues.PERMISSION_CODES}";
      };
      readonly UserIdentifierFilterValues: {
        readonly USER_ID: "${UserFilterValues.UserIdentifierFilterValues.USER_ID}";
        readonly USER_AUTH_PROVIDER: "${UserFilterValues.UserIdentifierFilterValues.USER_AUTH_PROVIDER}";
        readonly IDENTIFIER: "${UserFilterValues.UserIdentifierFilterValues.IDENTIFIER}";
      };
      readonly UserSessionFilterValues: {
        readonly USER_ID: "${UserFilterValues.UserSessionFilterValues.USER_ID}";
        readonly USER_ROLE: "${UserFilterValues.UserSessionFilterValues.USER_ROLE}";
        readonly IDENTIFIER_ID: "${UserFilterValues.UserSessionFilterValues.IDENTIFIER_ID}";
        readonly USER_AUTH_PROVIDER: "${UserFilterValues.UserSessionFilterValues.USER_AUTH_PROVIDER}";
        readonly CLIENT_TYPE: "${UserFilterValues.UserSessionFilterValues.CLIENT_TYPE}";
        readonly USER_AGENT: "${UserFilterValues.UserSessionFilterValues.USER_AGENT}";
        readonly IP_ADDRESS: "${UserFilterValues.UserSessionFilterValues.IP_ADDRESS}";
        readonly LANGUAGE: "${UserFilterValues.UserSessionFilterValues.LANGUAGE}";
        readonly DEVICE_ID: "${UserFilterValues.UserSessionFilterValues.DEVICE_ID}";
        readonly DEVICE_NAME: "${UserFilterValues.UserSessionFilterValues.DEVICE_NAME}";
        readonly APP_VERSION: "${UserFilterValues.UserSessionFilterValues.APP_VERSION}";
        readonly OPERATION_SYSTEM_VERSION: "${UserFilterValues.UserSessionFilterValues.OPERATION_SYSTEM_VERSION}";
        readonly IDENTIFIER: "${UserFilterValues.UserSessionFilterValues.IDENTIFIER}";
      };
    };
    """.trimIndent()

    val errorDeclarations = errorClasses.joinToString("\n\n") { kClass ->
        val name = kClass.simpleName ?: ""
        val classDoc = extractKDoc(kClass)?.let { "$it\n" } ?: ""
        val props = extractObjectStringProperties(kClass)
        val fields = props.map { (valName, valValue) ->
            val fieldDoc = extractKDoc(kClass, valName)?.formatAsJsDoc("    ")?.let { "$it\n" } ?: ""
            "${fieldDoc}    readonly $valName: \"$valValue\";"
        }
        """
        $classDoc export declare const $name: {
        ${fields.joinToString("\n")}
        };
        """.trimIndent()
    }

    val permDeclarations = permClasses.joinToString("\n\n") { kClass ->
        val name = kClass.simpleName ?: ""
        val classDoc = extractKDoc(kClass)?.let { "$it\n" } ?: ""
        val props = extractPermissionCodeProperties(kClass)
        val fields = props.map { (valName, _) ->
            val fieldDoc = extractKDoc(kClass, valName)?.formatAsJsDoc("    ")?.let { "$it\n" } ?: ""
            "${fieldDoc}    readonly $valName: PermissionCode;"
        }
        """
        $classDoc export const $name: {
        ${fields.joinToString(",\n")}
        };
        """.trimIndent()
    }

    val enumDeclarations = getAllEnumInfos().joinToString("\n\n") { info ->
        val enumClass = enumClasses.firstOrNull { (it.simpleName ?: "") == info.name }
        val classDoc = enumClass?.let { extractKDoc(it) }?.let { "$it\n" } ?: ""
        val unionType = info.entries.map { it.second }.distinct().joinToString(" | ") { "'$it'" }
        val objectFields = info.entries.joinToString("\n") { (key, wire) ->
            val entryDoc = enumClass?.let { extractKDoc(it, key) }?.formatAsJsDoc("  ")?.let { "$it\n" } ?: ""
            "${entryDoc}  readonly $key: '$wire';"
        }

        """
        $classDoc export type ${info.name} = $unionType;
        $classDoc export const ${info.name}: {
        $objectFields
        };
        export const ${info.schemaName}: z.ZodType<${info.name}>;
        """.trimIndent()
    }

    val extraDeclarations = """
    export interface ConfirmationKey {
      readonly type: ConfirmationType;
      readonly identifier: string;
    }
    export const confirmationKeySchema: z.ZodType<ConfirmationKey>;

    ${generateDomainModelsAndMappersDts()}

    ${generateCompositeParsersDts()}

    export type UserSortValues = 'created_at' | 'updated_at' | 'last_accessed_at' | 'last_reauthenticated_at' | 'expires_at';

    export namespace UserSortValues {
      export type UserSortBy = 'last_login_at' | 'last_active_at' | 'scheduled_permanent_deletion_at' | 'account_lockout_type' | 'temporary_lockout_until' | 'created_at' | 'updated_at';
      export const UserSortBy: {
        readonly LAST_LOGIN_AT: 'last_login_at';
        readonly LAST_ACTIVE_AT: 'last_active_at';
        readonly SCHEDULED_PERMANENT_DELETION_AT: 'scheduled_permanent_deletion_at';
        readonly ACCOUNT_LOCKOUT_TYPE: 'account_lockout_type';
        readonly TEMPORARY_LOCKOUT_UNTIL: 'temporary_lockout_until';
        readonly CREATED_AT: 'created_at';
        readonly UPDATED_AT: 'updated_at';
      };

      export type UserIdentifierSortBy = 'created_at' | 'updated_at';
      export const UserIdentifierSortBy: {
        readonly CREATED_AT: 'created_at';
        readonly UPDATED_AT: 'updated_at';
      };

      export type UserSessionSortBy = 'expires_at' | 'last_accessed_at' | 'last_reauthenticated_at' | 'created_at' | 'updated_at';
      export const UserSessionSortBy: {
        readonly EXPIRES_AT: 'expires_at';
        readonly LAST_ACCESSED_AT: 'last_accessed_at';
        readonly LAST_REAUTHENTICATED_AT: 'last_reauthenticated_at';
        readonly CREATED_AT: 'created_at';
        readonly UPDATED_AT: 'updated_at';
      };
    }
    """.trimIndent()

    val dtoDeclarations = dtoClasses.joinToString("\n\n") { kClass ->
        val name = kClass.simpleName ?: ""
        val classDoc = extractKDoc(kClass)?.let { "$it\n" } ?: ""
        val schemaName = name.decapitalizeFirstLetter() + "Schema"
        val primaryConstructor = kClass.primaryConstructor

        val fields = if (primaryConstructor != null) {
            primaryConstructor.parameters.map { param ->
                val paramName = param.name ?: ""
                val serialNameAnnotation = param.annotations.filterIsInstance<SerialName>().firstOrNull()
                    ?: kClass.declaredMemberProperties.firstOrNull { it.name == paramName }?.findAnnotation<SerialName>()

                val wireKey = serialNameAnnotation?.value ?: paramName
                val tsType = resolveTsType(paramName, param.type, kClass)
                val optionalSign = if (param.isOptional) "?" else ""
                val fieldDoc = extractKDoc(kClass, paramName)?.formatAsJsDoc("    ")?.let { "$it\n" } ?: ""
                "${fieldDoc}    $wireKey$optionalSign: $tsType;"
            }
        } else {
            emptyList()
        }

        """
        $classDoc export interface $name {
        ${fields.joinToString("\n")}
        }
        export const $schemaName: z.ZodType<$name>;
        """.trimIndent()
    }

    return """
    import { z } from 'zod';

    export type Instant = number;

    export type PermissionCode = string & { readonly __brand: 'PermissionCode' };
    export function toPermissionCodeOrNull(value: string | null | undefined): PermissionCode | null;
    export function toPermissionCodeOrThrow(value: string): PermissionCode;
    export const permissionCodeSchema: z.ZodType<PermissionCode>;

    export type UserId = string & { readonly __brand: 'UserId' };
    export function toUserIdOrNull(value: string | null | undefined): UserId | null;
    export function toUserIdOrThrow(value: string): UserId;
    export const userIdSchema: z.ZodType<UserId>;

    export type UserIdentifierId = string & { readonly __brand: 'UserIdentifierId' };
    export function toUserIdentifierIdOrNull(value: string | null | undefined): UserIdentifierId | null;
    export function toUserIdentifierIdOrThrow(value: string): UserIdentifierId;
    export const userIdentifierIdSchema: z.ZodType<UserIdentifierId>;

    export type UserSessionId = string & { readonly __brand: 'UserSessionId' };
    export function toUserSessionIdOrNull(value: string | null | undefined): UserSessionId | null;
    export function toUserSessionIdOrThrow(value: string): UserSessionId;
    export const userSessionIdSchema: z.ZodType<UserSessionId>;

    export type AuditEventId = string & { readonly __brand: 'AuditEventId' };
    export function toAuditEventIdOrNull(value: string | null | undefined): AuditEventId | null;
    export function toAuditEventIdOrThrow(value: string): AuditEventId;
    export const auditEventIdSchema: z.ZodType<AuditEventId>;

    export type ClientDeviceId = string & { readonly __brand: 'ClientDeviceId' };
    export function toClientDeviceIdOrNull(value: string | null | undefined): ClientDeviceId | null;
    export function toClientDeviceIdOrThrow(value: string): ClientDeviceId;
    export const clientDeviceIdSchema: z.ZodType<ClientDeviceId>;

    export type DecryptedString = string & { readonly __brand: 'DecryptedString' };
    export function toDecryptedStringOrNull(value: string | null | undefined): DecryptedString | null;
    export function toDecryptedStringOrThrow(value: string): DecryptedString;
    export const decryptedStringSchema: z.ZodType<DecryptedString>;

    export type EncryptedString = string & { readonly __brand: 'EncryptedString' };
    export function toEncryptedStringOrNull(value: string | null | undefined): EncryptedString | null;
    export function toEncryptedStringOrThrow(value: string): EncryptedString;
    export const encryptedStringSchema: z.ZodType<EncryptedString>;

    export type PasswordHash = string & { readonly __brand: 'PasswordHash' };
    export function toPasswordHashOrNull(value: string | null | undefined): PasswordHash | null;
    export function toPasswordHashOrThrow(value: string): PasswordHash;
    export const passwordHashSchema: z.ZodType<PasswordHash>;

    export type AccessToken = string & { readonly __brand: 'AccessToken' };
    export function toAccessTokenOrNull(value: string | null | undefined): AccessToken | null;
    export function toAccessTokenOrThrow(value: string): AccessToken;
    export const accessTokenSchema: z.ZodType<AccessToken>;

    export type RefreshToken = string & { readonly __brand: 'RefreshToken' };
    export function toRefreshTokenOrNull(value: string | null | undefined): RefreshToken | null;
    export function toRefreshTokenOrThrow(value: string): RefreshToken;
    export const refreshTokenSchema: z.ZodType<RefreshToken>;

    export type RefreshTokenHash = string & { readonly __brand: 'RefreshTokenHash' };
    export function toRefreshTokenHashOrNull(value: string | null | undefined): RefreshTokenHash | null;
    export function toRefreshTokenHashOrThrow(value: string): RefreshTokenHash;
    export const refreshTokenHashSchema: z.ZodType<RefreshTokenHash>;

    $routeDeclarations

    $contractDeclarations

    $listingParamNamesDecl

    $errorDeclarations

    $permDeclarations

    $enumDeclarations

    $extraDeclarations

    export function pagedResultSchema<T extends z.ZodTypeAny>(itemSchema: T): z.ZodType<PagedResult<z.infer<T>>>;

    export interface PagedResult<T> {
      items: T[];
      totalCount: number;
      pageNumber: number;
      pageSize: number;
      totalPages: number;
    }

    $dtoDeclarations

    ${generateParamParsersAndSerializersDts()}

    $testingDtsCode

    $localizationDtsCode
    """.trimIndent()
}

private fun generateParamParsersAndSerializersJs(): String {
    val paramClasses = rawDtoClasses.filter { kClass ->
        kClass.simpleName?.endsWith("Params") == true
    }

    val globalQueryMapCode = """
    /**
     * Generic parameter serializer converting a JS/TS parameter object into a URL search query map.
     * Omits default values (pageNumber=1, pageSize=20, default enums, empty arrays, nulls, undefined).
     */
    export function toQueryMap(params) {
      if (!params || typeof params !== 'object') return {};
      const res = {};

      for (const [key, val] of Object.entries(params)) {
        if (val === undefined || val === null) continue;

        let queryKey = key;
        if (key === 'pageNumber') {
          if (val === 1 || val === '1') continue;
          queryKey = 'page_number';
        } else if (key === 'pageSize') {
          if (val === 20 || val === '20') continue;
          queryKey = 'page_size';
        } else if (key === 'sortBy') {
          const sVal = String(val).toLowerCase();
          if (sVal === 'default' || sVal === 'none' || sVal === '') continue;
          queryKey = 'sort_by';
        } else if (key === 'sortOrder') {
          const sVal = String(val).toLowerCase();
          if (sVal === 'default' || sVal === 'none' || sVal === '') continue;
          queryKey = 'sort_order';
        } else if (key === 'userRoles') {
          queryKey = 'role';
        } else if (key === 'actorUserRoles') {
          queryKey = 'actor_user_role';
        } else if (key === 'actorIds') {
          queryKey = 'actor_id';
        } else if (key === 'actorTypes') {
          queryKey = 'actor_type';
        } else if (key === 'resourceIds') {
          queryKey = 'resource_id';
        } else if (key === 'userAuthProviders') {
          queryKey = 'user_auth_provider';
        } else if (key === 'identifiers') {
          queryKey = 'identifier';
        } else if (key === 'identifierIds') {
          queryKey = 'identifier_id';
        } else if (key === 'clientTypes') {
          queryKey = 'client_type';
        } else if (key === 'userAgents') {
          queryKey = 'user_agent';
        } else if (key === 'ipAddresses') {
          queryKey = 'ip_address';
        } else if (key === 'languages') {
          queryKey = 'language';
        } else if (key === 'deviceIds') {
          queryKey = 'device_id';
        } else if (key === 'deviceNames') {
          queryKey = 'device_name';
        } else if (key === 'appVersions') {
          queryKey = 'app_version';
        } else if (key === 'osVersions') {
          queryKey = 'operation_system_version';
        } else if (key === 'userIds') {
          queryKey = 'user_id';
        } else if (key === 'redirectUrl') {
          queryKey = 'redirect_url';
        } else if (key === 'mfaToken') {
          queryKey = 'mfa_token';
        } else if (key === 'loginByTotpMode') {
          queryKey = 'login_by_totp_mode';
        } else if (key === 'totpStep') {
          queryKey = 'totp_step';
        } else if (key === 'unlockMethod') {
          queryKey = 'unlock_method';
        } else if (key === 'targetInput') {
          queryKey = 'target_input';
        } else if (key === 'accountStatus') {
          queryKey = 'account_status';
        } else if (key === 'accountStatuses') {
          queryKey = 'account_status';
        } else if (key === 'accountStatusesOnRestore') {
          queryKey = 'account_status_on_restore';
        } else if (key === 'accountLockoutTypes') {
          queryKey = 'account_lockout_type';
        } else if (key === 'authorityLevelFrom') {
          queryKey = 'authority_level_from';
        } else if (key === 'authorityLevelTo') {
          queryKey = 'authority_level_to';
        } else if (key === 'isTotpEnabled') {
          queryKey = 'is_totp_enabled';
        } else if (key === 'permissionCodes') {
          queryKey = 'permission_codes';
        } else if (key === 'phone') {
          queryKey = 'phone_number';
        } else if (key === 'actions') {
          queryKey = 'action';
        } else if (key === 'resources') {
          queryKey = 'resource';
        } else if (key === 'statuses') {
          queryKey = 'status';
        } else if (key === 'messages') {
          queryKey = 'message';
        } else if (key === 'userId' || key === 'identifierId' || key === 'sessionId' || key === 'eventId') {
          continue;
        } else {
          queryKey = key.replace(/([A-Z])/g, '_$1').toLowerCase();
        }

        if (Array.isArray(val)) {
          if (val.length === 0) continue;
          const strVals = val
            .map(v => (v !== undefined && v !== null ? String(v).trim() : ''))
            .filter(v => v.length > 0);
          if (strVals.length > 0) {
            res[queryKey] = strVals;
          }
        } else {
          const strVal = String(val).trim();
          if (strVal.length > 0) {
            res[queryKey] = [strVal];
          }
        }
      }

      return res;
    }

    /**
     * Converts parameter object into a URL search query string.
     */
    export function toQuerySearchString(params) {
      const map = toQueryMap(params);
      const sp = new URLSearchParams();
      for (const [k, vals] of Object.entries(map)) {
        for (const v of vals) {
          if (v !== undefined && v !== null && v !== '') {
            sp.append(k, v);
          }
        }
      }
      return sp.toString();
    }
    """.trimIndent()

    val individualParsersCode = paramClasses.joinToString("\n\n") { kClass ->
        val name = kClass.simpleName ?: ""
        val schemaName = name.decapitalizeFirstLetter() + "Schema"
        """
        export function serialize$name(params) {
          return toQueryMap(params);
        }

        export function toQueryMap$name(params) {
          return toQueryMap(params);
        }

        export const ${name}Parser = {
          parse(params) {
            return $schemaName.parse(params);
          },
          serialize: serialize$name,
          toQueryMap: serialize$name,
          toSearchString(params) {
            return toQuerySearchString(params);
          }
        };
        """.trimIndent()
    }

    return "$globalQueryMapCode\n\n$individualParsersCode"
}

private fun generateParamParsersAndSerializersDts(): String {
    val paramClasses = rawDtoClasses.filter { kClass ->
        kClass.simpleName?.endsWith("Params") == true
    }

    val globalDts = """
    export declare function toQueryMap(params?: Record<string, unknown> | null): Record<string, string[]>;
    export declare function toQuerySearchString(params?: Record<string, unknown> | null): string;
    """.trimIndent()

    val individualDts = paramClasses.joinToString("\n\n") { kClass ->
        val name = kClass.simpleName ?: ""
        """
        export declare function serialize$name(params?: Partial<$name> | null): Record<string, string[]>;
        export declare function toQueryMap$name(params?: Partial<$name> | null): Record<string, string[]>;

        export declare const ${name}Parser: {
          parse(params: Record<string, unknown>): $name;
          serialize(params?: Partial<$name> | null): Record<string, string[]>;
          toQueryMap(params?: Partial<$name> | null): Record<string, string[]>;
          toSearchString(params?: Partial<$name> | null): string;
        };
        """.trimIndent()
    }

    return "$globalDts\n\n$individualDts"
}

private val rawDomainToDtoClasses = listOf(
    ClientInfo::class to ClientInfoPayload::class,
    ClientDeviceInfo::class to ClientDeviceInfoPayload::class,
    AccountLockoutPolicy::class to AccountLockoutPolicyPayload::class,
    IpRestrictionPolicy::class to IpRestrictionPolicyPayload::class,
    OtpConfirmation::class to OtpConfirmationPayload::class,
    ManagementPasswordPolicy::class to ManagementPasswordPolicyPayload::class,
    OpenPasswordPolicy::class to OpenPasswordPolicyPayload::class,
    ManagementSecuritySettings::class to ManagementSecuritySettingsPayload::class,
    OpenSecuritySettings::class to OpenSecuritySettingsPayload::class,
    TotpRecoveryCodes::class to TotpRecoveryCodesPayload::class,
    TotpSetup::class to TotpSetupPayload::class,
    VerifyTotp::class to VerifyTotpPayload::class,
    ManagementGlobalSettings::class to ManagementGlobalSettingsPayload::class,
    OpenGlobalSettings::class to OpenGlobalSettingsPayload::class,
    AuditEventSummary::class to AuditEventSummaryPayload::class,
    AuditEventPrivate::class to AuditEventPrivatePayload::class,
    AuditEventMetadata::class to AuditEventMetadataPayload::class,
    AuthData::class to AuthDataPayload::class,
    AvailableAuthProviders::class to AvailableAuthProvidersPayload::class,
    ManagementAuthSettings::class to ManagementAuthSettingsPayload::class,
    OpenAuthSettings::class to OpenAuthSettingsPayload::class,
    ManagementUserConfiguration::class to ManagementUserConfigurationPayload::class,
    OpenUserConfiguration::class to OpenUserConfigurationPayload::class,
    EmailRestrictionPolicy::class to EmailRestrictionPolicyPayload::class,
    UserIdentifierSummary::class to UserIdentifierSummaryPayload::class,
    UserIdentifierPrivate::class to UserIdentifierPrivatePayload::class,
    DeletedSessions::class to DeletedSessionsPayload::class,
    UserSessionSummary::class to UserSessionSummaryPayload::class,
    UserSessionPrivate::class to UserSessionPrivatePayload::class,
    SessionToken::class to SessionTokenPayload::class,
    UserPrivate::class to UserPrivatePayload::class,
    UserSummary::class to UserSummaryPayload::class
)

private val domainToDtoClasses = sortDomainToDtoTopologically(rawDomainToDtoClasses)

private fun getWireKey(payloadClass: KClass<*>, paramName: String): String {
    val param = payloadClass.primaryConstructor?.parameters?.firstOrNull { it.name == paramName }
    val serialNameAnnotation = param?.annotations?.filterIsInstance<SerialName>()?.firstOrNull()
        ?: payloadClass.declaredMemberProperties.firstOrNull { it.name == paramName }?.findAnnotation<SerialName>()
    return serialNameAnnotation?.value ?: paramName
}

private fun findMatchingPayloadParam(
    domainParamName: String,
    domainParamType: KType,
    payloadClass: KClass<*>
): Pair<String, KType>? {
    val primaryParams = payloadClass.primaryConstructor?.parameters ?: return null
    val domainClassifier = domainParamType.classifier as? KClass<*>

    val exact = primaryParams.firstOrNull {
        it.name == domainParamName ||
        it.annotations.filterIsInstance<SerialName>().firstOrNull()?.value == domainParamName
    }
    if (exact != null) return (exact.name ?: domainParamName) to exact.type

    val aliasName = when (domainParamName) {
        "deviceInfo" -> "clientDeviceInfo"
        "userPrivate" -> "userPrivatePayload"
        "sessionToken" -> "sessionTokenPayload"
        else -> null
    }
    if (aliasName != null) {
        val aliasMatch = primaryParams.firstOrNull {
            it.name == aliasName ||
            it.annotations.filterIsInstance<SerialName>().firstOrNull()?.value == aliasName
        }
        if (aliasMatch != null) return (aliasMatch.name ?: aliasName) to aliasMatch.type
    }

    val targetPayloadClass = domainToDtoClasses.firstOrNull { it.first == domainClassifier }?.second
    if (targetPayloadClass != null) {
        val typeMatch = primaryParams.firstOrNull { (it.type.classifier as? KClass<*>) == targetPayloadClass }
        if (typeMatch != null) return (typeMatch.name ?: "") to typeMatch.type
    }

    return null
}

private fun findMatchingDomainParam(
    payloadParamName: String,
    payloadParamType: KType,
    domainClass: KClass<*>
): Pair<String, KType>? {
    val primaryParams = domainClass.primaryConstructor?.parameters ?: return null
    val payloadClassifier = payloadParamType.classifier as? KClass<*>

    val exact = primaryParams.firstOrNull { it.name == payloadParamName }
    if (exact != null) return (exact.name ?: payloadParamName) to exact.type

    val aliasName = when (payloadParamName) {
        "clientDeviceInfo" -> "deviceInfo"
        "userPrivatePayload" -> "userPrivate"
        "sessionTokenPayload" -> "sessionToken"
        else -> null
    }
    if (aliasName != null) {
        val aliasMatch = primaryParams.firstOrNull { it.name == aliasName }
        if (aliasMatch != null) return (aliasMatch.name ?: aliasName) to aliasMatch.type
    }

    val targetDomainClass = domainToDtoClasses.firstOrNull { it.second == payloadClassifier }?.first
    if (targetDomainClass != null) {
        val typeMatch = primaryParams.firstOrNull { (it.type.classifier as? KClass<*>) == targetDomainClass }
        if (typeMatch != null) return (typeMatch.name ?: "") to typeMatch.type
    }

    return null
}

private fun buildToDomainFields(domainClass: KClass<*>, payloadClass: KClass<*>): String {
    val fields = domainClass.primaryConstructor?.parameters?.mapNotNull { domainParam ->
        val domainParamName = domainParam.name ?: return@mapNotNull null
        val match = findMatchingPayloadParam(domainParamName, domainParam.type, payloadClass)
        
        if (match == null) {
            return@mapNotNull "  $domainParamName: null"
        }

        val (payloadParamName, _) = match
        val wireKey = getWireKey(payloadClass, payloadParamName)
        val payloadRef = "payload.$wireKey"
        val type = domainParam.type
        val classifier = type.classifier as? KClass<*>
        val isNullable = type.isMarkedNullable
        
        val mappedValue = when {
            classifier?.simpleName?.endsWith("Id") == true -> {
                val brandName = classifier.simpleName
                if (isNullable) {
                    "$payloadRef ? to${brandName}OrThrow($payloadRef) : null"
                } else {
                    "to${brandName}OrThrow($payloadRef)"
                }
            }
            classifier == PermissionCode::class -> {
                if (isNullable) "$payloadRef ? toPermissionCodeOrThrow($payloadRef) : null"
                else "toPermissionCodeOrThrow($payloadRef)"
            }
            classifier == Set::class || classifier == List::class -> {
                val typeArg = type.arguments.firstOrNull()?.type?.classifier as? KClass<*>
                if (typeArg == PermissionCode::class) {
                    "$payloadRef ? $payloadRef.map(val => toPermissionCodeOrThrow(val)) : []"
                } else if (typeArg?.simpleName?.endsWith("Id") == true) {
                    val brandName = typeArg.simpleName
                    "$payloadRef ? $payloadRef.map(val => to${brandName}OrThrow(val)) : []"
                } else if (domainToDtoClasses.any { it.first == typeArg }) {
                    val nestedDomainName = typeArg?.simpleName
                    "$payloadRef ? $payloadRef.map(val => to$nestedDomainName(val)) : []"
                } else {
                    payloadRef
                }
            }
            domainToDtoClasses.any { it.first == classifier } -> {
                val nestedDomainName = classifier?.simpleName
                if (isNullable) {
                    "$payloadRef ? to$nestedDomainName($payloadRef) : null"
                } else {
                    "to$nestedDomainName($payloadRef)"
                }
            }
            else -> payloadRef
        }
        
        "  $domainParamName: $mappedValue"
    } ?: emptyList()
    return fields.joinToString(",\n")
}

private fun buildToPayloadFields(domainClass: KClass<*>, payloadClass: KClass<*>): String {
    val fields = payloadClass.primaryConstructor?.parameters?.mapNotNull { payloadParam ->
        val payloadParamName = payloadParam.name ?: return@mapNotNull null
        val wireKey = getWireKey(payloadClass, payloadParamName)
        val match = findMatchingDomainParam(payloadParamName, payloadParam.type, domainClass)
        
        if (match == null) {
            return@mapNotNull "  $wireKey: null"
        }

        val (domainParamName, _) = match
        val domainRef = "domain.$domainParamName"
        val payloadParamType = payloadParam.type
        val isNullable = payloadParamType.isMarkedNullable
        
        val domainParam = domainClass.primaryConstructor?.parameters?.firstOrNull { it.name == domainParamName }
        val domainClassifier = domainParam?.type?.classifier as? KClass<*>
        
        val mappedValue = when {
            domainClassifier?.simpleName?.endsWith("Id") == true -> domainRef
            domainClassifier == PermissionCode::class -> domainRef
            domainClassifier == Set::class || domainClassifier == List::class -> {
                val typeArg = domainParam?.type?.arguments?.firstOrNull()?.type?.classifier as? KClass<*>
                if (domainToDtoClasses.any { it.first == typeArg }) {
                    val nestedDomainDtoPair = domainToDtoClasses.firstOrNull { it.first == typeArg }
                    val nestedPayloadName = nestedDomainDtoPair?.second?.simpleName
                    "$domainRef ? $domainRef.map(val => to$nestedPayloadName(val)) : []"
                } else {
                    domainRef
                }
            }
            domainToDtoClasses.any { it.first == domainClassifier } -> {
                val nestedDomainDtoPair = domainToDtoClasses.firstOrNull { it.first == domainClassifier }
                val nestedPayloadNameCorrect = nestedDomainDtoPair?.second?.simpleName
                if (isNullable) {
                    "$domainRef ? to$nestedPayloadNameCorrect($domainRef) : null"
                } else {
                    "to$nestedPayloadNameCorrect($domainRef)"
                }
            }
            else -> domainRef
        }
        
        "  $wireKey: $mappedValue"
    } ?: emptyList()
    return fields.joinToString(",\n")
}

private fun generateDomainModelsAndMappersJs(): String {
    return domainToDtoClasses.joinToString("\n\n") { (domainClass, payloadClass) ->
        val domainName = domainClass.simpleName ?: ""
        val payloadName = payloadClass.simpleName ?: ""
        
        if (domainName == "AuditEventSummary") {
            return@joinToString """
            export const toAuditEventSummary = (payload, actionTypeParser, resourceTypeParser) => ({
              id: toAuditEventIdOrThrow(payload.id),
              actorType: payload.actor_type,
              action: actionTypeParser.fromValueOrThrow(payload.action),
              resource: resourceTypeParser.fromValueOrThrow(payload.resource),
              status: payload.status,
              createdAt: payload.created_at
            });
            
            export const toAuditEventSummaryPayload = (domain) => ({
              id: domain.id,
              actor_type: domain.actorType,
              action: domain.action,
              resource: domain.resource,
              status: domain.status,
              created_at: domain.createdAt
            });
            """.trimIndent()
        }

        if (domainName == "AuditEventPrivate") {
            return@joinToString """
            export const toAuditEventPrivate = (payload, actionTypeParser, resourceTypeParser, metadataKeyParser) => ({
              id: toAuditEventIdOrThrow(payload.id),
              actorId: payload.actor_id,
              actorType: payload.actor_type,
              actorUserRole: payload.actor_user_role,
              action: actionTypeParser.fromValueOrThrow(payload.action),
              resource: resourceTypeParser.fromValueOrThrow(payload.resource),
              resourceId: payload.resource_id,
              status: payload.status,
              metadata: payload.metadata ? payload.metadata.map(val => toAuditEventMetadata(val, metadataKeyParser)) : [],
              message: payload.message,
              createdAt: payload.created_at
            });
            
            export const toAuditEventPrivatePayload = (domain) => ({
              id: domain.id,
              actor_id: domain.actorId,
              actor_type: domain.actorType,
              actor_user_role: domain.actorUserRole,
              action: domain.action,
              resource: domain.resource,
              resource_id: domain.resourceId,
              status: domain.status,
              metadata: domain.metadata ? domain.metadata.map(val => toAuditEventMetadataPayload(val)) : [],
              message: domain.message,
              created_at: domain.createdAt
            });
            """.trimIndent()
        }
        
        if (domainName == "AuditEventMetadata") {
            return@joinToString """
            export const toAuditEventMetadata = (payload, metadataKeyParser) => ({
              key: metadataKeyParser.fromValueOrThrow(payload.key),
              value: payload.value,
              valueSensitivity: payload.value_sensitivity
            });
            
            export const toAuditEventMetadataPayload = (domain) => ({
              key: domain.key,
              value: domain.value,
              value_sensitivity: domain.valueSensitivity
            });
            """.trimIndent()
        }
        
        val toDomainFields = buildToDomainFields(domainClass, payloadClass)
        val toPayloadFields = buildToPayloadFields(domainClass, payloadClass)
        
        """
        export const to$domainName = (payload) => ({
        $toDomainFields
        });
        
        export const to$payloadName = (domain) => ({
        $toPayloadFields
        });
        """.trimIndent()
    }
}

private fun generateDomainModelsAndMappersDts(): String {
    return domainToDtoClasses.joinToString("\n\n") { (domainClass, payloadClass) ->
        val domainName = domainClass.simpleName ?: ""
        val payloadName = payloadClass.simpleName ?: ""
        val domainDoc = extractKDoc(domainClass)?.let { "$it\n" } ?: ""
        
        val fields = domainClass.primaryConstructor?.parameters?.mapNotNull { param ->
            val paramName = param.name ?: return@mapNotNull null
            val tsType = resolveTsType(paramName, param.type, domainClass)
            val optionalSign = if (param.isOptional) "?" else ""
            val fieldDoc = extractKDoc(domainClass, paramName)?.formatAsJsDoc("  ")?.let { "$it\n" } ?: ""
            "${fieldDoc}  readonly $paramName$optionalSign: $tsType;"
        }?.joinToString("\n") ?: ""
        
        val toDomainMapperDoc = "/** Converts $payloadName payload DTO to $domainName domain model. */"
        val toPayloadMapperDoc = "/** Converts $domainName domain model to $payloadName payload DTO. */"

        if (domainName == "AuditEventSummary") {
            return@joinToString """
            $domainDoc export interface $domainName {
            $fields
            }
            
            $toDomainMapperDoc
            export declare const toAuditEventSummary: (payload: AuditEventSummaryPayload, actionTypeParser: CompositeAuditActionTypeParser, resourceTypeParser: CompositeAuditResourceTypeParser) => AuditEventSummary;
            $toPayloadMapperDoc
            export declare const toAuditEventSummaryPayload: (domain: AuditEventSummary) => AuditEventSummaryPayload;
            """.trimIndent()
        }

        if (domainName == "AuditEventPrivate") {
            return@joinToString """
            $domainDoc export interface $domainName {
            $fields
            }
            
            $toDomainMapperDoc
            export declare const toAuditEventPrivate: (payload: AuditEventPrivatePayload, actionTypeParser: CompositeAuditActionTypeParser, resourceTypeParser: CompositeAuditResourceTypeParser, metadataKeyParser: CompositeAuditMetadataKeyParser) => AuditEventPrivate;
            $toPayloadMapperDoc
            export declare const toAuditEventPrivatePayload: (domain: AuditEventPrivate) => AuditEventPrivatePayload;
            """.trimIndent()
        }
        
        if (domainName == "AuditEventMetadata") {
            return@joinToString """
            $domainDoc export interface $domainName {
            $fields
            }
            
            $toDomainMapperDoc
            export declare const toAuditEventMetadata: (payload: AuditEventMetadataPayload, metadataKeyParser: CompositeAuditMetadataKeyParser) => AuditEventMetadata;
            $toPayloadMapperDoc
            export declare const toAuditEventMetadataPayload: (domain: AuditEventMetadata) => AuditEventMetadataPayload;
            """.trimIndent()
        }
        
        """
        $domainDoc export interface $domainName {
        $fields
        }
        
        $toDomainMapperDoc
        export declare const to$domainName: (payload: $payloadName) => $domainName;
        $toPayloadMapperDoc
        export declare const to$payloadName: (domain: $domainName) => $payloadName;
        """.trimIndent()
    }
}

private fun parseRootBuildGradleVersion(rootDir: File): String? {
    val buildFile = File(rootDir, "build.gradle.kts")
    if (!buildFile.exists()) return null
    val regex = Regex("""version\s*=\s*["']([^"']+)["']""")
    return buildFile.useLines { lines ->
        lines.mapNotNull { line -> regex.find(line)?.groupValues?.get(1) }.firstOrNull()
    }
}

private fun String.decapitalizeFirstLetter(): String {
    if (isEmpty()) return this
    return replaceFirstChar { it.lowercase() }
}

data class LocalizationKeyEntry(
    val rawKey: String,
    val value: String,
    val cleanKey: String,
    val propertyOrMethodName: String,
    val placeholders: List<String>,
    val isMethod: Boolean
)

data class LocalizationBundleData(
    val modulePath: String,
    val modulePrefix: String,
    val bundleName: String,
    val className: String,
    val entries: List<LocalizationKeyEntry>
)

data class ModuleLocalizationData(
    val modulePath: String,
    val modulePrefix: String,
    val facadeClassName: String,
    val bundles: List<LocalizationBundleData>
)

private fun generateLocalizationCode(rootDir: File): Pair<String, String> {
    val localizationDirs = rootDir.walkTopDown()
        .filter { it.isDirectory && it.name == "localization" && it.parentFile?.name == "resources" }
        .toList()

    val allResources = mutableMapOf<String, MutableMap<String, MutableMap<String, Map<String, String>>>>()

    localizationDirs.forEach { locDir ->
        locDir.walkTopDown()
            .filter { it.isFile && it.extension == "json" }
            .forEach { jsonFile ->
                val relativePath = jsonFile.relativeTo(locDir).path.replace(File.separatorChar, '/')
                val parts = relativePath.split('/')
                if (parts.size >= 3) {
                    val bundleNameWithExt = parts.last()
                    val bundleName = bundleNameWithExt.substringBeforeLast('.')
                    if (bundleName != "email_templates") {
                        val locale = parts[parts.size - 2]
                        val modulePath = parts.subList(0, parts.size - 2).joinToString("/")
                        val entries = parseFlatJson(jsonFile.readText())

                        allResources.getOrPut(locale) { mutableMapOf() }
                            .getOrPut(modulePath) { mutableMapOf() }[bundleName] = entries
                    }
                }
            }
    }

    if (allResources.isEmpty()) {
        return Pair("// No localization resources found", "// No localization resources found")
    }

    val fallbackLocale = if (allResources.containsKey("en")) "en" else allResources.keys.first()
    val fallbackData = allResources[fallbackLocale] ?: emptyMap()

    val placeholderRegex = Regex("""\{([a-zA-Z0-9_]+)\}""")

    val modulesList = fallbackData.map { (modulePath, bundleMap) ->
        val modulePrefix = modulePath.split('/').last().split('_')
            .joinToString("") { part -> part.replaceFirstChar { char -> char.uppercase() } }
        val facadeClassName = "${modulePrefix}ModuleStrings"

        val bundlesList = bundleMap.map { (bundleName, jsonMap) ->
            val bundleClassName = "${modulePrefix}${bundleName.split('_').joinToString("") { part -> part.replaceFirstChar { char -> char.uppercase() } }}Strings"
            val entriesList = jsonMap.map { (rawKey, value) ->
                val placeholders = placeholderRegex.findAll(value).map { it.groupValues[1] }.distinct().toList()
                val cleanKey = rawKey.removeSuffix("_without_args").removeSuffix("_with_args")
                val propertyName = toCamelCase(cleanKey)
                val isMethod = placeholders.isNotEmpty()
                val propOrMethodName = if (!isMethod && rawKey.endsWith("_without_args")) {
                    "${propertyName}WithoutArgs"
                } else {
                    propertyName
                }
                LocalizationKeyEntry(
                    rawKey = rawKey,
                    value = value,
                    cleanKey = cleanKey,
                    propertyOrMethodName = propOrMethodName,
                    placeholders = placeholders,
                    isMethod = isMethod
                )
            }
            LocalizationBundleData(
                modulePath = modulePath,
                modulePrefix = modulePrefix,
                bundleName = bundleName,
                className = bundleClassName,
                entries = entriesList
            )
        }

        ModuleLocalizationData(
            modulePath = modulePath,
            modulePrefix = modulePrefix,
            facadeClassName = facadeClassName,
            bundles = bundlesList
        )
    }

    val jsSb = StringBuilder()

    val jsonResourcesCode = formatAsJsObject(allResources)
    jsSb.append("""
        /**
         * Raw localization resource dictionaries by locale, module path, and bundle name.
         */
        export const localizationResources = $jsonResourcesCode;

        /**
         * Replaces `{placeholder}` tokens in string templates.
         */
        export function formatLocalizedString(template, params) {
          if (!template || !params) return template || '';
          return template.replace(/\{([a-zA-Z0-9_]+)\}/g, (match, key) => {
            return params[key] !== undefined && params[key] !== null ? String(params[key]) : match;
          });
        }

        /**
         * Default localized string resolver fetching strings from `localizationResources`.
         */
        export const defaultLocalizedStringResolver = (modulePath, bundleName, key, locale = 'en') => {
          const localeData = localizationResources[locale] || localizationResources['en'];
          return localeData?.[modulePath]?.[bundleName]?.[key];
        };

    """.trimIndent()).append("\n\n")

    modulesList.forEach { moduleData ->
        moduleData.bundles.forEach { bundleData ->
            jsSb.append("""
                export class ${bundleData.className} {
                  constructor(resolver = defaultLocalizedStringResolver, locale = 'en') {
                    this._resolver = resolver;
                    this._locale = locale;
                  }

                  _get(key) {
                    return this._resolver("${bundleData.modulePath}", "${bundleData.bundleName}", key, this._locale) ?? key;
                  }

            """.trimIndent()).append("\n")

            bundleData.entries.forEach { entry ->
                if (!entry.isMethod) {
                    jsSb.append("""
                          get ${entry.propertyOrMethodName}() {
                            return this._get("${entry.rawKey}");
                          }

                    """.trimIndent()).append("\n")
                } else {
                    val argList = entry.placeholders.indices.map { "arg${it + 1}" }.joinToString(", ")
                    val objProps = entry.placeholders.mapIndexed { idx, p -> "$p: arg${idx + 1}" }.joinToString(", ")
                    jsSb.append("""
                          ${entry.propertyOrMethodName}($argList) {
                            const template = this._get("${entry.rawKey}");
                            const params = (typeof arg1 === 'object' && arg1 !== null) ? arg1 : { $objProps };
                            return formatLocalizedString(template, params);
                          }

                    """.trimIndent()).append("\n")
                }
            }

            jsSb.append("}\n\n")
        }

        jsSb.append("""
            export class ${moduleData.facadeClassName} {
              constructor(resolver = defaultLocalizedStringResolver, locale = 'en') {

        """.trimIndent()).append("\n")
        moduleData.bundles.forEach { bundleData ->
            val fieldName = bundleData.bundleName.split('_').joinToString("") { part -> part.replaceFirstChar { char -> char.uppercase() } }.decapitalizeFirstLetter()
            jsSb.append("    this.$fieldName = new ${bundleData.className}(resolver, locale);\n")
        }
        jsSb.append("  }\n}\n\n")
    }

    jsSb.append("""
        export class SharedFoundationLocalization {
          constructor(resolver = defaultLocalizedStringResolver, locale = 'en') {

    """.trimIndent()).append("\n")
    modulesList.forEach { moduleData ->
        val fieldName = moduleData.modulePrefix.decapitalizeFirstLetter()
        jsSb.append("    this.$fieldName = new ${moduleData.facadeClassName}(resolver, locale);\n")
    }
    jsSb.append("  }\n}\n\n")

    // Global Singletons & SharedLocalization Configuration
    jsSb.append("""
        let _globalLocale = 'en';
        let _globalResolver = defaultLocalizedStringResolver;

        export const SharedLocalization = {
          setLocale(locale) {
            if (locale) _globalLocale = locale;
          },
          getLocale() {
            return _globalLocale;
          },
          setResolver(resolver) {
            if (resolver) _globalResolver = resolver;
          },
          getResolver() {
            return _globalResolver;
          }
        };

        export const sharedLocalization = new Proxy({}, {
          get(_, prop) {
            return new SharedFoundationLocalization(_globalResolver, _globalLocale)[prop];
          }
        });

    """.trimIndent()).append("\n")

    modulesList.forEach { moduleData ->
        val singletonName = "${moduleData.modulePrefix.decapitalizeFirstLetter()}Strings"
        jsSb.append("""
            export const $singletonName = new Proxy({}, {
              get(_, prop) {
                return new ${moduleData.facadeClassName}(_globalResolver, _globalLocale)[prop];
              }
            });

        """.trimIndent()).append("\n")
    }


    val dtsSb = StringBuilder()

    dtsSb.append("""
        export type LocalizedStringResolver = (modulePath: string, bundleName: string, key: string, locale?: string) => string | undefined;

        export declare function formatLocalizedString(template: string, params?: Record<string, unknown>): string;

        export declare const defaultLocalizedStringResolver: LocalizedStringResolver;

        export declare const localizationResources: Record<string, Record<string, Record<string, Record<string, string>>>>;

    """.trimIndent()).append("\n\n")

    modulesList.forEach { moduleData ->
        moduleData.bundles.forEach { bundleData ->
            val rawKeysUnion = bundleData.entries.joinToString(" | ") { "'${it.rawKey}'" }.ifEmpty { "string" }
            val propsUnion = bundleData.entries.joinToString(" | ") { "'${it.propertyOrMethodName}'" }.ifEmpty { "string" }

            dtsSb.append("""
                export type ${bundleData.className}RawKey = $rawKeysUnion;
                export type ${bundleData.className}Property = $propsUnion;

                export class ${bundleData.className} {
                  constructor(resolver?: LocalizedStringResolver, locale?: string);

            """.trimIndent()).append("\n")

            bundleData.entries.forEach { entry ->
                val escapedVal = entry.value.replace("\n", " ").replace("*/", "* /")
                if (!entry.isMethod) {
                    dtsSb.append("""
                          /**
                           * Template: "$escapedVal"
                           */
                          get ${entry.propertyOrMethodName}(): string;

                    """.trimIndent()).append("\n")
                } else {
                    val paramsObjType = entry.placeholders.joinToString("; ") { "$it: string | number" }
                    val paramsPositional = entry.placeholders.joinToString(", ") { "$it: string | number" }
                    dtsSb.append("""
                          /**
                           * Template: "$escapedVal"
                           */
                          ${entry.propertyOrMethodName}(params: { $paramsObjType }): string;
                          ${entry.propertyOrMethodName}($paramsPositional): string;

                    """.trimIndent()).append("\n")
                }
            }

            dtsSb.append("}\n\n")
        }

        dtsSb.append("""
            export class ${moduleData.facadeClassName} {
              constructor(resolver?: LocalizedStringResolver, locale?: string);

        """.trimIndent()).append("\n")
        moduleData.bundles.forEach { bundleData ->
            val fieldName = bundleData.bundleName.split('_').joinToString("") { part -> part.replaceFirstChar { char -> char.uppercase() } }.decapitalizeFirstLetter()
            dtsSb.append("  readonly $fieldName: ${bundleData.className};\n")
        }
        dtsSb.append("}\n\n")
    }

    dtsSb.append("""
        export class SharedFoundationLocalization {
          constructor(resolver?: LocalizedStringResolver, locale?: string);

    """.trimIndent()).append("\n")
    modulesList.forEach { moduleData ->
        val fieldName = moduleData.modulePrefix.decapitalizeFirstLetter()
        dtsSb.append("  readonly $fieldName: ${moduleData.facadeClassName};\n")
    }
    dtsSb.append("}\n\n")

    dtsSb.append("""
        export declare const SharedLocalization: {
          setLocale(locale: string): void;
          getLocale(): string;
          setResolver(resolver: LocalizedStringResolver): void;
          getResolver(): LocalizedStringResolver;
        };

        export declare const sharedLocalization: SharedFoundationLocalization;

    """.trimIndent()).append("\n")

    modulesList.forEach { moduleData ->
        val singletonName = "${moduleData.modulePrefix.decapitalizeFirstLetter()}Strings"
        dtsSb.append("export declare const $singletonName: ${moduleData.facadeClassName};\n")
    }

    return Pair(jsSb.toString(), dtsSb.toString())
}

private fun parseFlatJson(content: String): Map<String, String> {
    val result = mutableMapOf<String, String>()
    val lineRegex = Regex(""""([^"]+)"\s*:\s*"((?:\\.|[^"\\])*)"""")
    lineRegex.findAll(content).forEach { match ->
        val key = match.groupValues[1]
        val value = match.groupValues[2].replace("\\\"", "\"").replace("\\\\", "\\")
        result[key] = value
    }
    return result
}

private fun toCamelCase(snakeCase: String): String {
    val parts = snakeCase.split('_')
    return parts.first() + parts.drop(1).joinToString("") { part ->
        part.replaceFirstChar { char -> char.uppercase() }
    }
}

private fun formatAsJsObject(obj: Any?): String {
    return when (obj) {
        is Map<*, *> -> {
            val entries = obj.entries.joinToString(",\n") { (k, v) ->
                val keyStr = if (k.toString().matches(Regex("^[a-zA-Z_$][a-zA-Z0-9_$]*$"))) k.toString() else "\"$k\""
                "$keyStr: ${formatAsJsObject(v)}"
            }
            "{\n${entries.indent(2)}\n}"
        }
        is String -> "\"${obj.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n")}\""
        else -> obj.toString()
    }
}

private fun String.indent(spaces: Int): String {
    val prefix = " ".repeat(spaces)
    return this.lines().joinToString("\n") { "$prefix$it" }
}

private fun extractClassProperties(kClass: KClass<*>): List<Pair<String, KType>> {
    val constructor = kClass.primaryConstructor ?: return emptyList()
    return constructor.parameters.mapNotNull { param ->
        val name = param.name ?: return@mapNotNull null
        name to param.type
    }
}

private fun getDefaultMockValueJs(paramName: String, type: KType): String {
    val classifier = type.classifier as? KClass<*>
    val typeName = classifier?.simpleName ?: ""

    if (classifier != null && classifier.java.isEnum) {
        val enumInfo = getEnumInfo(classifier)
        val defaultEntry = enumInfo.entries.firstOrNull { it.first == "DEFAULT" || it.first == "NONE" || it.first == "ACTIVE" || it.first == "USER" || it.first == "SUCCESS" }
            ?: enumInfo.entries.firstOrNull()
        return if (defaultEntry != null) {
            "\"${defaultEntry.second}\""
        } else {
            "\"\""
        }
    }

    return when {
        typeName == "String" -> {
            when {
                paramName == "id" || paramName.endsWith("Id") || paramName.endsWith("_id") -> {
                    when (paramName) {
                        "userId", "user_id" -> "TEST_USER_ID"
                        "sessionId", "session_id" -> "TEST_SESSION_ID"
                        "identifierId", "userIdentifierId", "user_identifier_id" -> "TEST_IDENTIFIER_ID"
                        "auditEventId", "event_id", "eventId" -> "TEST_AUDIT_EVENT_ID"
                        "clientDeviceId", "device_id", "deviceId" -> "TEST_CLIENT_DEVICE_ID"
                        else -> "\"00000000-0000-0000-0000-000000000000\""
                    }
                }
                paramName.contains("email", ignoreCase = true) -> "TEST_EMAIL"
                paramName.contains("phone", ignoreCase = true) -> "TEST_PHONE"
                paramName.contains("ipAddress", ignoreCase = true) || paramName.contains("ip_address", ignoreCase = true) -> "TEST_IP_ADDRESS"
                paramName.contains("userAgent", ignoreCase = true) || paramName.contains("user_agent", ignoreCase = true) -> "TEST_USER_AGENT"
                paramName.contains("code", ignoreCase = true) || paramName.contains("otp", ignoreCase = true) || paramName.contains("token", ignoreCase = true) -> "\"123456\""
                paramName.contains("url", ignoreCase = true) -> "\"https://example.com\""
                paramName.contains("host", ignoreCase = true) -> "\"api.example.com\""
                paramName.contains("message", ignoreCase = true) -> "\"Test message\""
                else -> "\"test_${paramName}\""
            }
        }
        typeName == "Int" || typeName == "Long" || typeName == "Number" -> {
            when {
                paramName == "pageNumber" || paramName == "page_number" || paramName == "page" -> "1"
                paramName == "pageSize" || paramName == "page_size" -> "20"
                paramName == "authorityLevel" || paramName == "authority_level" -> "10"
                paramName == "retryAfterSeconds" || paramName == "retry_after_seconds" -> "60"
                paramName.endsWith("At") || paramName.endsWith("_at") || paramName.endsWith("Time") || paramName.endsWith("Timestamp") -> "TEST_TIME_MILLIS"
                else -> "1"
            }
        }
        typeName == "Boolean" -> "false"
        typeName == "List" || typeName == "Set" || typeName == "Collection" -> "[]"
        typeName == "Map" -> "{}"
        typeName == "UserId" -> "toUserIdOrThrow(TEST_USER_ID)"
        typeName == "UserSessionId" -> "toUserSessionIdOrThrow(TEST_SESSION_ID)"
        typeName == "UserIdentifierId" -> "toUserIdentifierIdOrThrow(TEST_IDENTIFIER_ID)"
        typeName == "AuditEventId" -> "toAuditEventIdOrThrow(TEST_AUDIT_EVENT_ID)"
        typeName == "ClientDeviceId" -> "toClientDeviceIdOrThrow(TEST_CLIENT_DEVICE_ID)"
        typeName == "PermissionCode" -> "toPermissionCodeOrThrow(TEST_PERMISSION_CODE)"
        typeName == "AccessToken" -> "toAccessTokenOrThrow(TEST_ACCESS_TOKEN)"
        typeName == "RefreshToken" -> "toRefreshTokenOrThrow(TEST_REFRESH_TOKEN)"
        typeName == "RefreshTokenHash" -> "toRefreshTokenHashOrThrow(TEST_REFRESH_TOKEN_HASH)"
        typeName == "DecryptedString" -> "TEST_DECRYPTED_STRING"
        typeName == "EncryptedString" -> "TEST_ENCRYPTED_STRING"
        typeName == "PasswordHash" -> "TEST_PASSWORD_HASH"
        typeName == "Instant" || typeName == "Duration" -> "TEST_TIME_MILLIS"
        type.isMarkedNullable -> "null"
        else -> "{}"
    }
}

private fun getAllExportableTestClasses(): List<KClass<*>> {
    val currentDir = java.io.File(System.getProperty("user.dir"))
    val rootDir = if (currentDir.name == "npm-generator") currentDir.parentFile.parentFile else currentDir
    val testingSourceDir = java.io.File(rootDir, "shared/foundation/testing/src/commonMain/kotlin")
    
    val classes = mutableSetOf<KClass<*>>()
    
    classes.addAll(rawDtoClasses)
    classes.addAll(rawDomainToDtoClasses.map { it.first })
    
    if (testingSourceDir.exists()) {
        testingSourceDir.walkTopDown().filter { it.isFile && it.extension == "kt" }.forEach { file ->
            val name = file.nameWithoutExtension
            val content = file.readText()
            
            if (name.endsWith("Fixture")) {
                val targetClassName = name.removeSuffix("Fixture")
                val importRegex = Regex("import\\s+([\\w\\.]+\\.$targetClassName)")
                val match = importRegex.find(content)
                if (match != null) {
                    try {
                        val clazz = Class.forName(match.groupValues[1]).kotlin
                        classes.add(clazz)
                    } catch (e: Exception) {}
                }
            } else if (name.startsWith("Test")) {
                val packageMatch = Regex("package\\s+([\\w\\.]+)").find(content)
                if (packageMatch != null) {
                    val fqn = "${packageMatch.groupValues[1]}.$name"
                    try {
                        val clazz = Class.forName(fqn).kotlin
                        classes.add(clazz)
                    } catch (e: Exception) {}
                }
            }
        }
    }
    
    return classes.toList().sortedBy { it.simpleName ?: "" }
}

private fun generateTestFixturesJs(): String {
    val constantsJs = """
    export const TEST_USER_ID = "00000000-0000-0000-0000-000000000001";
    export const TEST_USER_ID_2 = "00000000-0000-0000-0000-000000000002";
    export const TEST_SESSION_ID = "00000000-0000-0000-0000-000000000010";
    export const TEST_IDENTIFIER_ID = "00000000-0000-0000-0000-000000000020";
    export const TEST_AUDIT_EVENT_ID = "00000000-0000-0000-0000-000000000099";
    export const TEST_CLIENT_DEVICE_ID = "00000000-0000-0000-0000-000000000030";
    export const TEST_PERMISSION_CODE = "test.permission.read";
    export const TEST_DECRYPTED_STRING = "test_decrypted_secret";
    export const TEST_ENCRYPTED_STRING = "test_encrypted_secret";
    export const TEST_PASSWORD_HASH = "test_password_hash";
    export const TEST_ACCESS_TOKEN = "test_access_token";
    export const TEST_REFRESH_TOKEN = "test_refresh_token";
    export const TEST_REFRESH_TOKEN_HASH = "test_refresh_token_hash";
    export const TEST_IP_ADDRESS = "127.0.0.1";
    export const TEST_USER_AGENT = "TestAgent/1.0 (Mobile; Android)";
    export const TEST_EMAIL = "test.user@example.com";
    export const TEST_PHONE = "+15550000000";
    export const TEST_TIME_MILLIS = 1700000000000;

    export const SharedTestConstants = {
      TEST_IP_ADDRESS,
      TEST_USER_AGENT,
      TEST_EMAIL,
      TEST_PHONE,
      TEST_TIME_MILLIS
    };

    export const createTestList = (count, generator) => {
      const result = [];
      for (let i = 0; i < count; i++) {
        result.push(generator(i));
      }
      return result;
    };

    export const createTestPagedResult = (overrides = {}) => {
      const items = overrides?.items ?? [];
      const totalCount = overrides?.totalCount ?? items.length;
      const pageSize = overrides?.pageSize ?? 20;
      const totalPages = overrides?.totalPages ?? (totalCount === 0 ? 0 : Math.ceil(totalCount / pageSize));
      const pageNumber = overrides?.pageNumber ?? 1;

      return {
        items,
        pageNumber,
        pageSize,
        totalCount,
        totalPages,
        ...overrides
      };
    };

    export class FakeLocalizedStringResolver {
      constructor(prefix = '[Test]', nullKey = null) {
        this.prefix = prefix;
        this.nullKey = nullKey;
      }

      resolve(modulePath, bundleName, key, locale = 'en') {
        if (key === this.nullKey) return undefined;
        return `${'$'}{this.prefix} ${'$'}{locale}:${'$'}{modulePath}:${'$'}{bundleName}:${'$'}{key}`;
      }
    }

    export class FakeLocalizationBundleProvider {
      constructor(bundles = {}) {
        this.bundles = bundles;
      }

      get(modulePath, bundleName, locale) {
        const key = `${'$'}{modulePath}/${'$'}{bundleName}/${'$'}{locale}`;
        return this.bundles[key];
      }
    }

    export class FakePasswordPolicyValidator {
      constructor(failReasons = []) {
        this.failReasons = failReasons;
      }

      validate(passwordPolicy, password) {
        if (!this.failReasons || this.failReasons.length === 0) {
          return { isSuccess: true, reasons: [] };
        }
        return { isSuccess: false, reasons: this.failReasons };
      }
    }
    """.trimIndent()

    val exportableClasses = getAllExportableTestClasses().filter { it.simpleName != "PagedResult" }

    val factoriesJs = exportableClasses.joinToString("\n\n") { kClass ->
        val className = kClass.simpleName ?: ""
        val isPayload = className.endsWith("Payload") || className.endsWith("Request") || className.endsWith("Response")
        val props = extractClassProperties(kClass)

        val fieldsJs = props.joinToString(",\n") { (paramName, paramType) ->
            val wireKey = if (isPayload) getWireKey(kClass, paramName) else paramName
            val mockVal = getDefaultMockValueJs(paramName, paramType)
            "      $wireKey: $mockVal"
        }

        val listGeneratorCustomLogic = when (className) {
            "UserPrivate", "UserSummary", "UserPrivatePayload", "UserSummaryPayload" -> """
              const createdAtMs = TEST_TIME_MILLIS + (index * 60000);
              return createTest$className({
                id: ${if (isPayload) "`usr_${'$'}{index + 1}`" else "toUserIdOrThrow(`usr_${'$'}{index + 1}`)"},
                email: `user${'$'}{index + 1}@example.com`,
                ${if (isPayload) "created_at" else "createdAt"}: createdAtMs,
                ${if (isPayload) "updated_at" else "updatedAt"}: createdAtMs + 30000,
                ${if (isPayload) "last_login_at" else "lastLoginAt"}: createdAtMs + 15000,
                ${if (isPayload) "last_active_at" else "lastActiveAt"}: createdAtMs + 15000,
                ...itemOverrides
              });
            """.trimIndent()

            "UserIdentifierPrivate", "UserIdentifierSummary", "UserIdentifierPrivatePayload", "UserIdentifierSummaryPayload" -> """
              const createdAtMs = TEST_TIME_MILLIS + (index * 60000);
              return createTest$className({
                id: ${if (isPayload) "`ident_${'$'}{index + 1}`" else "toUserIdentifierIdOrThrow(`ident_${'$'}{index + 1}`)"},
                ${if (isPayload) "user_id" else "userId"}: ${if (isPayload) "`usr_${'$'}{index + 1}`" else "toUserIdOrThrow(`usr_${'$'}{index + 1}`)"},
                ${if (isPayload) "user_auth_provider" else "userAuthProvider"}: index % 2 === 0 ? "email" : "google",
                identifier: index % 2 === 0 ? `user${'$'}{index + 1}@example.com` : `+1555000000${'$'}{index + 1}`,
                ${if (isPayload) "display_name" else "displayName"}: index % 2 === 0 ? "Email Identifier" : "Google Account",
                ${if (isPayload) "created_at" else "createdAt"}: createdAtMs,
                ${if (isPayload) "updated_at" else "updatedAt"}: createdAtMs + 10000,
                ...itemOverrides
              });
            """.trimIndent()

            "UserSessionPrivate", "UserSessionSummary", "UserSessionPrivatePayload", "UserSessionSummaryPayload" -> """
              const createdAtMs = TEST_TIME_MILLIS + (index * 60000);
              return createTest$className({
                id: ${if (isPayload) "`sess_${'$'}{index + 1}`" else "toUserSessionIdOrThrow(`sess_${'$'}{index + 1}`)"},
                ${if (isPayload) "user_id" else "userId"}: ${if (isPayload) "`usr_${'$'}{index + 1}`" else "toUserIdOrThrow(`usr_${'$'}{index + 1}`)"},
                ${if (isPayload) "user_agent" else "userAgent"}: index % 2 === 0 ? "Mozilla/5.0 (Windows NT 10.0; Win64; x64) Chrome/120.0.0.0 Safari/537.36" : "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) Safari/605.1.15",
                ${if (isPayload) "client_device_info" else "clientDeviceInfo"}: index % 2 === 0 ? ${if (isPayload) "createTestClientDeviceInfoPayload({ device_name: \"Windows PC\", operation_system_version: \"Windows 10\" })" else "createTestClientDeviceInfo({ deviceName: \"Windows PC\", operationSystemVersion: \"Windows 10\" })"} : ${if (isPayload) "createTestClientDeviceInfoPayload({ device_name: \"MacBook Pro\", operation_system_version: \"macOS 14.0\" })" else "createTestClientDeviceInfo({ deviceName: \"MacBook Pro\", operationSystemVersion: \"macOS 14.0\" })"},
                ${if (isPayload) "created_at" else "createdAt"}: createdAtMs,
                ${if (isPayload) "last_accessed_at" else "lastAccessedAt"}: createdAtMs + (index * 5000),
                ${if (isPayload) "last_reauthenticated_at" else "lastReauthenticatedAt"}: createdAtMs + (index * 5000),
                ${if (isPayload) "expires_at" else "expiresAt"}: createdAtMs + 86400000,
                ...itemOverrides
              });
            """.trimIndent()

            "AuditEventPrivate", "AuditEventSummary", "AuditEventPrivatePayload", "AuditEventSummaryPayload" -> """
              const createdAtMs = TEST_TIME_MILLIS + (index * 60000);
              return createTest$className({
                id: ${if (isPayload) "`evt_${'$'}{index + 1}`" else "toAuditEventIdOrThrow(`evt_${'$'}{index + 1}`)"},
                status: index % 2 === 0 ? "success" : "failed",
                ${if (isPayload) "created_at" else "createdAt"}: createdAtMs,
                ...itemOverrides
              });
            """.trimIndent()

            else -> """
              return createTest$className(itemOverrides);
            """.trimIndent()
        }

        """
        export const createTest$className = (overrides = {}) => ({
    $fieldsJs,
          ...overrides
        });

        export const createTest${className}List = (count = 2, overrides) =>
          createTestList(count, (index) => {
            const itemOverrides = typeof overrides === 'function' ? overrides(index) : (overrides || {});
            $listGeneratorCustomLogic
          });
        """.trimIndent()
    }

    return "$constantsJs\n\n$factoriesJs"
}

private fun generateTestFixturesDts(): String {
    val constantsDts = """
    /**
     * Type helper allowing either a static partial override or a callback function receiving the 0-based element index.
     */
    export type Overrides<T> = Partial<T> | ((index: number) => Partial<T>);

    /**
     * Creates an array of test instances using the provided generator callback.
     * @param count Number of elements to generate.
     * @param generator Callback function receiving 0-based element index.
     */
    export declare function createTestList<T>(count: number, generator: (index: number) => T): T[];

    /**
     * Creates a test instance of PagedResult with auto-calculated totalPages and totalCount.
     * @param overrides Partial properties or overrides for PagedResult.
     */
    export declare function createTestPagedResult<T>(overrides?: Partial<PagedResult<T>>): PagedResult<T>;

    /** Default test user ID string (UUID format). */
    export const TEST_USER_ID: string;
    /** Secondary test user ID string (UUID format). */
    export const TEST_USER_ID_2: string;
    /** Default test session ID string. */
    export const TEST_SESSION_ID: string;
    /** Default test user identifier ID string. */
    export const TEST_IDENTIFIER_ID: string;
    /** Default test audit event ID string. */
    export const TEST_AUDIT_EVENT_ID: string;
    /** Default test client device ID string. */
    export const TEST_CLIENT_DEVICE_ID: string;
    /** Default test permission code string. */
    export const TEST_PERMISSION_CODE: string;
    /** Default test decrypted secret string. */
    export const TEST_DECRYPTED_STRING: string;
    /** Default test encrypted secret string. */
    export const TEST_ENCRYPTED_STRING: string;
    /** Default test password hash string. */
    export const TEST_PASSWORD_HASH: string;
    /** Default test access token string. */
    export const TEST_ACCESS_TOKEN: string;
    /** Default test refresh token string. */
    export const TEST_REFRESH_TOKEN: string;
    /** Default test refresh token hash string. */
    export const TEST_REFRESH_TOKEN_HASH: string;
    /** Default test IP address string. */
    export const TEST_IP_ADDRESS: string;
    /** Default test User-Agent header string. */
    export const TEST_USER_AGENT: string;
    /** Default test email address string. */
    export const TEST_EMAIL: string;
    /** Default test phone number string. */
    export const TEST_PHONE: string;
    /** Default test epoch-millisecond timestamp (2023-11-14T22:13:20Z). */
    export const TEST_TIME_MILLIS: number;

    /** Shared test constants collection. */
    export const SharedTestConstants: {
      readonly TEST_IP_ADDRESS: string;
      readonly TEST_USER_AGENT: string;
      readonly TEST_EMAIL: string;
      readonly TEST_PHONE: string;
      readonly TEST_TIME_MILLIS: number;
    };

    /** Fake LocalizedStringResolver implementation for testing UI strings. */
    export class FakeLocalizedStringResolver implements LocalizedStringResolver {
      constructor(prefix?: string, nullKey?: string | null);
      resolve(modulePath: string, bundleName: string, key: string, locale?: string): string | undefined;
    }

    /** Fake LocalizationBundleProvider implementation for testing localized bundles. */
    export class FakeLocalizationBundleProvider {
      constructor(bundles?: Record<string, Record<string, string>>);
      get(modulePath: string, bundleName: string, locale: string): Record<string, string> | undefined;
    }

    /** Fake PasswordPolicyValidator implementation for unit testing password validation flows. */
    export class FakePasswordPolicyValidator {
      failReasons: string[];
      constructor(failReasons?: string[]);
      validate(passwordPolicy: unknown, password: string): { isSuccess: boolean; reasons: string[] };
    }
    """.trimIndent()

    val exportableClasses = getAllExportableTestClasses().filter { it.simpleName != "PagedResult" }

    val factoriesDts = exportableClasses.joinToString("\n\n") { kClass ->
        val className = kClass.simpleName ?: ""
        val classDoc = extractKDoc(kClass)?.formatAsJsDoc("    ")?.let { "$it\n    " } ?: ""
        """
        $classDoc/**
         * Creates a test instance of {@link $className} with default mock values.
         * @param overrides Partial overrides for object properties.
         */
        export declare function createTest$className(overrides?: Partial<$className>): $className;

        /**
         * Creates an array of test {@link $className} instances with deterministic index-based IDs and time shifts.
         * @param count Number of elements to generate (defaults to 2).
         * @param overrides Partial overrides object or index-based callback function.
         */
        export declare function createTest${className}List(count?: number, overrides?: Overrides<$className>): $className[];
        """.trimIndent()
    }

    return "$constantsDts\n\n$factoriesDts"
}

