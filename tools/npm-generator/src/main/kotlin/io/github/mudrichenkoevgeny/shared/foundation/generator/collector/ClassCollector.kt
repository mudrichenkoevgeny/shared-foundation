package io.github.mudrichenkoevgeny.shared.foundation.generator.collector

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.contract.AuditEventFields
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.audit.resource.CommonAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventPrivate
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventSummary
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditValueSensitivity
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.listing.AuditSortValues
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.AuditEventMetadata
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CommonAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.status.AuditStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.permissions.AuditPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.navigation.route.management.ManagementAuditNavigation
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.navigation.screen.management.audit.detail.ManagementAuditEventDetailParams
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.navigation.screen.management.audit.list.ManagementAuditEventListParams
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.contract.AuditApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.model.event.AuditEventMetadataPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.model.event.AuditEventPrivatePayload
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.model.event.AuditEventSummaryPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.route.management.ManagementAuditRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientDeviceInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientType
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.CommonSortValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.theme.ThemeMode
import io.github.mudrichenkoevgeny.shared.foundation.core.common.error.model.ApiErrorResponse
import io.github.mudrichenkoevgeny.shared.foundation.core.common.error.client.naming.ClientCommonErrorCodes
import io.github.mudrichenkoevgeny.shared.foundation.core.common.error.naming.CommonErrorArgs
import io.github.mudrichenkoevgeny.shared.foundation.core.common.error.naming.CommonErrorCodes
import io.github.mudrichenkoevgeny.shared.foundation.core.common.localization.CommonLocales
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.CommonNavigationRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.client.ClientMainNavigation
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.management.ManagementMainNavigation
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonHttpHeaders
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonWebSocketCloseReasons
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonWebSocketEventTypes
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.WebSocketContract
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.client.ClientDeviceInfoPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.client.ClientInfoPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.websocket.SocketFrame
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.websocket.WebSocketInitializePayload
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.route.CommonNetworkRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.audit.action.SecurityAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.audit.metadata.SecurityAuditMetadataDeniedReasonValues
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.audit.resource.SecurityAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.accountlockout.AccountLockoutPolicy
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.accountlockout.AccountLockoutType
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.iprestriction.IpRestrictionPolicy
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.otpconfirmation.OtpConfirmation
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
import io.github.mudrichenkoevgeny.shared.foundation.core.security.navigation.route.management.ManagementSecurityNavigation
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
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.navigation.route.management.ManagementSettingsNavigation
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
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.email.EmailTemplate
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.emailrestriction.EmailRestrictionPolicy
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierPrivate
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierSummary
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSortValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.DeletedSessions
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionPrivate
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionSummary
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.token.SessionToken
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.totp.TotpStep
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserPrivate
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserStorageConfiguration
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserSummary
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission.AuthSettingsPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission.IdentifierPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission.SessionPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission.UserPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.category.StorageCategory
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.StorageFilePrivate
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.StorageFileSummary
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.listing.StorageSortValues
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.presigned.PresignedReadUrlBatch
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.presigned.PresignedUrl
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.status.StorageFileStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.storagesettings.ManagementStorageSettings
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.storagesettings.OpenStorageSettings
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.visibility.StorageFileVisibility
import io.github.mudrichenkoevgeny.shared.foundation.core.security.error.client.naming.ClientSecurityErrorCodes
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.permission.StoragePermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.error.client.naming.ClientStorageErrorCodes
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.error.naming.StorageErrorArgs
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.error.naming.StorageErrorCodes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.error.client.naming.ClientUserErrorCodes
import io.github.mudrichenkoevgeny.shared.foundation.core.security.navigation.route.BaseSecurityNavigationRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.route.BaseSecurityNetworkRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.navigation.route.BaseSettingsNavigationRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.route.BaseSettingsNetworkRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.route.BaseStorageNavigationRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.route.BaseStorageNetworkRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.contract.StorageApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.contract.StorageApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.contract.StorageWebSocketEventTypes
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.route.management.ManagementSelfStorageNavigation
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.route.management.ManagementStorageNavigation
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.route.management.ManagementStorageSettingsNavigation
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.route.open.OpenStorageNavigation
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.audit.action.StorageAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.audit.metadata.StorageAuditMetadataDeniedReasonValues
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.audit.metadata.StorageAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.audit.resource.StorageAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.screen.base.detail.StorageFileDetailParams
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.screen.base.upload.StorageFileUploadParams
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.screen.management.list.ManagementStorageFileListParams
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.screen.open.list.SelfStorageFileListParams
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.file.StorageFilePrivatePayload
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.file.StorageFileSummaryPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.presigned.PresignedReadUrlBatchPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.presigned.PresignedUrlPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.storagesettings.ManagementStorageSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.storagesettings.OpenStorageSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.ui.component.fileupload.StorageFileUploadUiConfig
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.upload.UploadQueueItem
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.upload.UploadQueueItemStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.request.file.DeleteFilesBatchRequest
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.request.presigned.PresignedReadUrlBatchRequest
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.request.presigned.PresignedUrlRequest
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.route.management.ManagementStorageRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.route.management.ManagementStorageSettingsRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.route.management.self.SelfManagementStorageRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.route.open.OpenStorageRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.route.open.OpenStorageSettingsRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.contract.StorageNavigationPaths
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.error.naming.UserErrorArgs
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.error.naming.UserErrorCodes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.contract.UserNavigationPaths
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.route.BaseUserNavigationRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.BaseUserNetworkRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.route.client.ClientAuthNavigation
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.route.client.ClientUserNavigation
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.route.management.ManagementAuthNavigation
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.route.management.ManagementAuthSettingsNavigation
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.route.management.ManagementSelfUserNavigation
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.route.management.ManagementUsersNavigation
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.login.email.LoginByEmailParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.login.totp.LoginByTotpMode
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.login.totp.LoginByTotpParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.resetpassword.ResetPasswordParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.UnlockMethod
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.methodselection.UnlockMethodSelectionParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.otpinput.UnlockOtpInputParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.success.UnlockSuccessParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.targetinput.UnlockTargetInputParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.user.identifier.detail.IdentifierDetailParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.user.identifier.list.SelfIdentifierListAction
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.user.identifier.list.SelfIdentifierListParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.user.session.detail.SessionDetailParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.user.session.list.SelfSessionListParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.user.totp.main.TotpMainParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.client.auth.login.phone.LoginByPhoneParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.client.auth.registration.email.ClientRegistrationByEmailParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.identifier.globallist.ManagementGlobalIdentifierListParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.identifier.userlist.ManagementUserIdentifierListParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.session.globallist.ManagementGlobalSessionListParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.session.userlist.ManagementUserSessionListParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.user.detail.UserDetailParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.user.globallist.ManagementUserListParams
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
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.user.UserStorageConfigurationPayload
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
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.self.user.SelfManagementUserRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.self.user.identifier.SelfManagementIdentifierRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.self.user.security.SelfManagementUserSecurityRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.self.user.session.SelfManagementSessionRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.users.ManagementUsersRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.users.identifiers.ManagementIdentifiersRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.users.security.ManagementUsersSecurityRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.users.sessions.ManagementSessionsRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.login.OpenLoginRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.refreshtoken.OpenRefreshTokenRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.registration.OpenRegistrationRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.resetpassword.OpenResetPasswordRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.settings.OpenAuthSettingsRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.unlock.OpenUnlockRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.configuration.OpenUserConfigurationRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.user.OpenUserRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.user.identifier.OpenIdentifierRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.user.security.OpenUserSecurityRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.user.session.OpenSessionRoutes
import io.github.mudrichenkoevgeny.shared.foundation.generator.decapitalizeFirstLetter
import kotlinx.serialization.SerialName
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.companionObjectInstance
import kotlin.reflect.full.primaryConstructor

data class EnumInfo(
    val name: String,
    val schemaName: String,
    val entries: List<Pair<String, String>>
)

data class ClassStructure(
    val directProps: List<Pair<String, String>>,
    val nestedObjects: List<Pair<String, List<Pair<String, String>>>>
)

fun extractObjectStringProperties(kClass: KClass<*>): List<Pair<String, String>> {
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

fun extractClassStructure(kClass: KClass<*>): ClassStructure {
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

val routeClasses = listOf(
    ClientMainNavigation::class,
    ManagementMainNavigation::class,
    CommonNetworkRoutes::class,
    CommonNavigationRoutes::class,
    BaseSettingsNavigationRoutes::class,
    BaseSettingsNetworkRoutes::class,
    BaseSecurityNavigationRoutes::class,
    BaseSecurityNetworkRoutes::class,
    BaseStorageNavigationRoutes::class,
    BaseStorageNetworkRoutes::class,
    BaseUserNavigationRoutes::class,
    BaseUserNetworkRoutes::class,
    ManagementAuditRoutes::class,
    ManagementAuditNavigation::class,
    ClientAuthNavigation::class,
    ManagementAuthNavigation::class,
    ManagementAuthSettingsNavigation::class,
    ClientUserNavigation::class,
    ManagementSelfUserNavigation::class,
    OpenStorageNavigation::class,
    ManagementSelfStorageNavigation::class,
    ManagementStorageNavigation::class,
    ManagementStorageSettingsNavigation::class,
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
    OpenStorageRoutes::class,
    SelfManagementStorageRoutes::class,
    ManagementStorageRoutes::class,
    OpenStorageSettingsRoutes::class,
    ManagementStorageSettingsRoutes::class,
    WebSocketContract::class
)

val contractClasses = listOf(
    CommonLocales::class,
    CommonApiFields::class,
    SecurityApiFields::class,
    GlobalSettingsApiFields::class,
    UserApiFields::class,
    StorageApiFields::class,
    AuditEventFields::class,
    CommonHttpHeaders::class,
    CommonWebSocketCloseReasons::class,
    CommonWebSocketEventTypes::class,
    SecurityWebSocketEventTypes::class,
    StorageWebSocketEventTypes::class,
    SettingsWebSocketEventTypes::class,
    UserWebSocketCloseReasons::class,
    UserWebSocketEventTypes::class,
    UserApiPaths::class,
    StorageApiPaths::class,
    UserApiQueryParams::class,
    UserNavigationPaths::class,
    StorageNavigationPaths::class,
    UserAuthSpec::class,
    AuditApiPaths::class,
    UserRoleDefaultAuthorityLevel::class,
    SecurityAuditMetadataDeniedReasonValues::class,
    UserAuditMetadataDeniedReasonValues::class,
    StorageAuditMetadataDeniedReasonValues::class
)

val errorClasses = listOf(
    CommonErrorCodes::class,
    CommonErrorArgs::class,
    ClientCommonErrorCodes::class,
    SecurityErrorCodes::class,
    SecurityErrorArgs::class,
    ClientSecurityErrorCodes::class,
    UserErrorCodes::class,
    UserErrorArgs::class,
    ClientUserErrorCodes::class,
    StorageErrorCodes::class,
    StorageErrorArgs::class,
    ClientStorageErrorCodes::class
)

val permClasses = listOf(
    AuditPermissionCode::class,
    SecurityPermissionCode::class,
    SettingsPermissionCode::class,
    AuthSettingsPermissionCode::class,
    IdentifierPermissionCode::class,
    SessionPermissionCode::class,
    UserPermissionCode::class,
    StoragePermissionCode::class
)

val enumClasses = listOf(
    ThemeMode::class,
    ClientType::class,
    SortOrder::class,
    AccountLockoutType::class,
    PasswordPolicyFailReason::class,
    AuditActorType::class,
    AuditValueSensitivity::class,
    AuditStatus::class,
    StorageCategory::class,
    StorageFileStatus::class,
    StorageFileVisibility::class,
    UploadQueueItemStatus::class,
    CommonAuditResourceType::class,
    SecurityAuditResourceType::class,
    SettingsAuditResourceType::class,
    UserAuditResourceType::class,
    StorageAuditResourceType::class,
    SecurityAuditActionType::class,
    SettingsAuditActionType::class,
    UserAuditActionType::class,
    StorageAuditActionType::class,
    CommonAuditMetadataKey::class,
    UserAuditMetadataKey::class,
    StorageAuditMetadataKey::class,
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
    StorageSortValues.StorageFileSortBy::class,
    CommonSortValues.TimestampSortBy::class
)

val extraEnumInfos = listOf(
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

fun extractReferencedClasses(type: KType): Set<KClass<*>> {
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

fun getDtoDependencies(kClass: KClass<*>, allClasses: Set<KClass<*>>): Set<KClass<*>> {
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

fun sortDtosTopologically(classes: List<KClass<*>>): List<KClass<*>> {
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

fun getDomainPairDependencies(
    pair: Pair<KClass<*>, KClass<*>?>,
    allPairs: List<Pair<KClass<*>, KClass<*>?>>
): Set<Pair<KClass<*>, KClass<*>?>> {
    val dependencies = mutableSetOf<Pair<KClass<*>, KClass<*>?>>()
    val (domainClass, payloadClass) = pair
    val domainRefClasses = (domainClass.primaryConstructor?.parameters ?: emptyList())
        .flatMap { extractReferencedClasses(it.type) }
        .toSet()
    val payloadRefClasses = (payloadClass?.primaryConstructor?.parameters ?: emptyList())
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

fun sortDomainToDtoTopologically(
    pairs: List<Pair<KClass<*>, KClass<*>?>>
): List<Pair<KClass<*>, KClass<*>?>> {
    val depMap = pairs.associateWith { pair ->
        getDomainPairDependencies(pair, pairs)
    }

    val sorted = mutableListOf<Pair<KClass<*>, KClass<*>?>>()
    val visited = mutableSetOf<Pair<KClass<*>, KClass<*>?>>()
    val visiting = mutableSetOf<Pair<KClass<*>, KClass<*>?>>()

    fun visit(pair: Pair<KClass<*>, KClass<*>?>) {
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

val rawDtoClasses = listOf(
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
    StorageFileDetailParams::class,
    StorageFileUploadParams::class,
    ManagementStorageFileListParams::class,
    SelfStorageFileListParams::class,
    AvailableAuthProvidersPayload::class,
    EmailRestrictionPolicyPayload::class,
    SessionTokenPayload::class,
    UserPrivatePayload::class,
    UserStorageConfigurationPayload::class,
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
    PresignedUrlPayload::class,
    PresignedReadUrlBatchPayload::class,
    StorageFileSummaryPayload::class,
    StorageFilePrivatePayload::class,
    ManagementStorageSettingsPayload::class,
    OpenStorageSettingsPayload::class,
    DeleteFilesBatchRequest::class,
    PresignedUrlRequest::class,
    PresignedReadUrlBatchRequest::class,
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

val dtoClasses = sortDtosTopologically(rawDtoClasses)

fun getEnumInfo(kClass: KClass<*>): EnumInfo {
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

fun getAllEnumInfos(): List<EnumInfo> {
    return enumClasses.map { getEnumInfo(it) } + extraEnumInfos
}

val rawDomainToDtoClasses: List<Pair<KClass<*>, KClass<*>?>> = listOf(
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
    ManagementStorageSettings::class to ManagementStorageSettingsPayload::class,
    OpenStorageSettings::class to OpenStorageSettingsPayload::class,
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
    PresignedUrl::class to PresignedUrlPayload::class,
    PresignedReadUrlBatch::class to PresignedReadUrlBatchPayload::class,
    StorageFileSummary::class to StorageFileSummaryPayload::class,
    StorageFilePrivate::class to StorageFilePrivatePayload::class,
    SessionToken::class to SessionTokenPayload::class,
    UserPrivate::class to UserPrivatePayload::class,
    UserStorageConfiguration::class to UserStorageConfigurationPayload::class,
    UserSummary::class to UserSummaryPayload::class,
    StorageFileUploadUiConfig::class to null,
    UploadQueueItem::class to null
)

val domainToDtoClasses = sortDomainToDtoTopologically(rawDomainToDtoClasses)
