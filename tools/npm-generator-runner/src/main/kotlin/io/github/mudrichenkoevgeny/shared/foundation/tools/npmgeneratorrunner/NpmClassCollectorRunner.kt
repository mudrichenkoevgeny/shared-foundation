package io.github.mudrichenkoevgeny.shared.foundation.tools.npmgeneratorrunner

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.contract.AuditEventFields
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.audit.resource.CommonAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventInternal
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventPrivate
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventSummary
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditValueSensitivity
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.listing.AuditEventFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.listing.AuditEventSortBy
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
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.apptype.AppType
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientDeviceInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientType
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.CommonFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.CommonSortValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.theme.ThemeMode
import io.github.mudrichenkoevgeny.shared.foundation.core.common.error.client.naming.ClientCommonErrorCodes
import io.github.mudrichenkoevgeny.shared.foundation.core.common.error.model.ApiErrorResponse
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
import io.github.mudrichenkoevgeny.shared.foundation.core.security.error.client.naming.ClientSecurityErrorCodes
import io.github.mudrichenkoevgeny.shared.foundation.core.security.error.naming.SecurityErrorArgs
import io.github.mudrichenkoevgeny.shared.foundation.core.security.error.naming.SecurityErrorCodes
import io.github.mudrichenkoevgeny.shared.foundation.core.security.navigation.route.BaseSecurityNavigationRoutes
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
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.route.BaseSecurityNetworkRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.route.management.security.settings.ManagementSecuritySettingsRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.route.open.security.settings.OpenSecuritySettingsRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.domain.audit.action.SettingsAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.domain.audit.resource.SettingsAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.domain.model.globalsettings.ManagementGlobalSettings
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.domain.model.globalsettings.OpenGlobalSettings
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.domain.permission.SettingsPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.navigation.route.BaseSettingsNavigationRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.navigation.route.management.ManagementSettingsNavigation
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.contract.GlobalSettingsApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.contract.SettingsWebSocketEventTypes
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.model.globalsettings.ManagementGlobalSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.model.globalsettings.OpenGlobalSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.route.BaseSettingsNetworkRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.route.management.globalsettings.ManagementGlobalSettingsRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.route.open.globalsettings.OpenGlobalSettingsRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.audit.action.StorageAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.audit.metadata.StorageAuditMetadataDeniedReasonValues
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.audit.metadata.StorageAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.audit.resource.StorageAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.category.StorageCategory
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.FileUrlPresets
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.FileUrls
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.StorageFileInternal
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.StorageFilePrivate
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.StorageFileSummary
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.listing.StorageFileFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.listing.StorageFileSortBy
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.presigned.PresignedReadUrlBatch
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.presigned.PresignedUrl
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.status.StorageFileStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.storagesettings.ManagementStorageSettings
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.storagesettings.OpenStorageSettings
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.upload.UploadQueueItem
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.upload.UploadQueueItemStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.visibility.StorageFileVisibility
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.permission.StoragePermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.error.client.naming.ClientStorageErrorCodes
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.error.naming.StorageErrorArgs
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.error.naming.StorageErrorCodes
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.contract.StorageNavigationPaths
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.route.BaseStorageNavigationRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.route.management.ManagementSelfStorageNavigation
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.route.management.ManagementStorageNavigation
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.route.management.ManagementStorageSettingsNavigation
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.route.open.OpenStorageNavigation
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.screen.base.detail.StorageFileDetailParams
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.screen.base.upload.StorageFileUploadParams
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.screen.management.list.ManagementStorageFileListParams
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.screen.open.list.SelfStorageFileListParams
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.contract.StorageApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.contract.StorageApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.contract.StorageWebSocketEventTypes
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.file.FileUrlsPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.file.StorageFilePrivatePayload
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.file.StorageFileSummaryPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.presigned.PresignedReadUrlBatchPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.presigned.PresignedUrlPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.storagesettings.ManagementStorageSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.storagesettings.OpenStorageSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.request.file.DeleteFilesBatchRequest
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.request.presigned.PresignedReadUrlBatchRequest
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.request.presigned.PresignedUrlRequest
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.route.BaseStorageNetworkRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.route.management.ManagementStorageRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.route.management.ManagementStorageSettingsRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.route.management.self.SelfManagementStorageRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.route.open.OpenStorageRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.route.open.OpenStorageSettingsRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.ui.component.fileupload.StorageFileUploadUiConfig
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
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.confirmation.ConfirmationKey
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.confirmation.ConfirmationType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.email.EmailTemplate
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.emailrestriction.EmailRestrictionPolicy
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierInternal
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierPrivate
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierSummary
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserIdentifierFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserIdentifierSortBy
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSessionFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSessionSortBy
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSortBy
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.DeletedSessions
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionInternal
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionPrivate
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionSummary
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.storage.UserStorageTargetScopes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.token.SessionToken
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.totp.TotpStep
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserInternal
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserPrivate
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserStorageConfiguration
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserSummary
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission.AuthSettingsPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission.IdentifierPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission.SessionPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission.UserPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.error.client.naming.ClientUserErrorCodes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.error.naming.UserErrorArgs
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.error.naming.UserErrorCodes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.contract.UserNavigationPaths
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.route.BaseUserNavigationRoutes
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
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.user.ManagementUpdateUserRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.user.SelfUpdateUserRequest
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
import kotlin.reflect.KClass

public val routeClasses: List<KClass<*>> = listOf(
    CommonNavigationRoutes::class,
    ClientMainNavigation::class,
    ManagementMainNavigation::class,
    UserNavigationPaths::class,
    BaseUserNavigationRoutes::class,
    ClientAuthNavigation::class,
    ClientUserNavigation::class,
    ManagementAuthNavigation::class,
    ManagementAuthSettingsNavigation::class,
    ManagementSelfUserNavigation::class,
    ManagementUsersNavigation::class,
    OpenLoginRoutes::class,
    OpenRefreshTokenRoutes::class,
    OpenRegistrationRoutes::class,
    OpenResetPasswordRoutes::class,
    OpenAuthSettingsRoutes::class,
    OpenUnlockRoutes::class,
    OpenUserConfigurationRoutes::class,
    OpenUserRoutes::class,
    OpenIdentifierRoutes::class,
    OpenUserSecurityRoutes::class,
    OpenSessionRoutes::class,
    SelfManagementLoginRoutes::class,
    SelfManagementRefreshTokenRoutes::class,
    SelfManagementResetPasswordRoutes::class,
    SelfManagementUnlockRoutes::class,
    SelfManagementUserRoutes::class,
    SelfManagementIdentifierRoutes::class,
    SelfManagementUserSecurityRoutes::class,
    SelfManagementSessionRoutes::class,
    ManagementAuthSettingsRoutes::class,
    ManagementUserConfigurationRoutes::class,
    ManagementUsersRoutes::class,
    ManagementIdentifiersRoutes::class,
    ManagementUsersSecurityRoutes::class,
    ManagementSessionsRoutes::class,
    AuditApiPaths::class,
    ManagementAuditNavigation::class,
    ManagementAuditRoutes::class,
    BaseSecurityNavigationRoutes::class,
    ManagementSecurityNavigation::class,
    BaseSecurityNetworkRoutes::class,
    ManagementSecuritySettingsRoutes::class,
    OpenSecuritySettingsRoutes::class,
    BaseSettingsNavigationRoutes::class,
    ManagementSettingsNavigation::class,
    BaseSettingsNetworkRoutes::class,
    ManagementGlobalSettingsRoutes::class,
    OpenGlobalSettingsRoutes::class,
    StorageApiPaths::class,
    StorageNavigationPaths::class,
    BaseStorageNavigationRoutes::class,
    ManagementSelfStorageNavigation::class,
    ManagementStorageNavigation::class,
    ManagementStorageSettingsNavigation::class,
    OpenStorageNavigation::class,
    BaseStorageNetworkRoutes::class,
    ManagementStorageRoutes::class,
    ManagementStorageSettingsRoutes::class,
    SelfManagementStorageRoutes::class,
    OpenStorageRoutes::class,
    OpenStorageSettingsRoutes::class,
    CommonNetworkRoutes::class
)

public val contractClasses: List<KClass<*>> = listOf(
    CommonApiFields::class,
    CommonHttpHeaders::class,
    CommonWebSocketCloseReasons::class,
    CommonWebSocketEventTypes::class,
    WebSocketContract::class,
    UserApiFields::class,
    UserApiPaths::class,
    UserApiQueryParams::class,
    UserAuthSpec::class,
    UserWebSocketCloseReasons::class,
    UserWebSocketEventTypes::class,
    AuditEventFields::class,
    SecurityApiFields::class,
    SecurityWebSocketEventTypes::class,
    GlobalSettingsApiFields::class,
    SettingsWebSocketEventTypes::class,
    StorageApiFields::class,
    StorageWebSocketEventTypes::class,
    FileUrlPresets::class,
    AuditEventFilterValues::class,
    UserFilterValues::class,
    UserIdentifierFilterValues::class,
    UserSessionFilterValues::class,
    StorageFileFilterValues::class,
    UserStorageTargetScopes::class
)

public val errorClasses: List<KClass<*>> = listOf(
    CommonErrorCodes::class,
    CommonErrorArgs::class,
    ClientCommonErrorCodes::class,
    UserErrorCodes::class,
    UserErrorArgs::class,
    ClientUserErrorCodes::class,
    SecurityErrorCodes::class,
    SecurityErrorArgs::class,
    ClientSecurityErrorCodes::class,
    StorageErrorCodes::class,
    StorageErrorArgs::class,
    ClientStorageErrorCodes::class
)

public val permClasses: List<KClass<*>> = listOf(
    UserPermissionCode::class,
    SessionPermissionCode::class,
    IdentifierPermissionCode::class,
    AuthSettingsPermissionCode::class,
    AuditPermissionCode::class,
    SecurityPermissionCode::class,
    SettingsPermissionCode::class,
    StoragePermissionCode::class
)

public val enumClasses: List<KClass<*>> = listOf(
    CommonLocales::class,
    ThemeMode::class,
    SortOrder::class,
    CommonSortValues::class,
    CommonFilterValues::class,
    ClientType::class,
    AppType::class,
    ConfirmationType::class,
    UserRole::class,
    UserAccountStatus::class,
    UserAuthProvider::class,
    ExternalAuthProvider::class,
    UnlockMethod::class,
    TotpStep::class,
    LoginByTotpMode::class,
    SelfIdentifierListAction::class,
    UserRoleDefaultAuthorityLevel::class,
    UserSortBy::class,
    UserIdentifierSortBy::class,
    UserSessionSortBy::class,
    AuditActorType::class,
    AuditStatus::class,
    AuditEventSortBy::class,
    AuditValueSensitivity::class,
    CommonAuditResourceType::class,
    CommonAuditMetadataKey::class,
    UserAuditActionType::class,
    UserAuditResourceType::class,
    UserAuditMetadataKey::class,
    UserAuditMetadataDeniedReasonValues::class,
    AccountLockoutType::class,
    PasswordPolicyFailReason::class,
    SecurityAuditActionType::class,
    SecurityAuditResourceType::class,
    SecurityAuditMetadataDeniedReasonValues::class,
    SettingsAuditActionType::class,
    SettingsAuditResourceType::class,
    StorageCategory::class,
    StorageFileStatus::class,
    StorageFileVisibility::class,
    UploadQueueItemStatus::class,
    StorageFileSortBy::class,
    StorageAuditActionType::class,
    StorageAuditResourceType::class,
    StorageAuditMetadataKey::class,
    StorageAuditMetadataDeniedReasonValues::class
)

public val rawDtoClasses: List<KClass<*>> = listOf(
    ApiErrorResponse::class,
    ConfirmationKey::class,
    ClientDeviceInfoPayload::class,
    ClientInfoPayload::class,
    SocketFrame::class,
    WebSocketInitializePayload::class,
    LoginByEmailParams::class,
    LoginByPhoneParams::class,
    ClientRegistrationByEmailParams::class,
    LoginByTotpParams::class,
    UnlockMethodSelectionParams::class,
    UnlockTargetInputParams::class,
    UnlockOtpInputParams::class,
    UnlockSuccessParams::class,
    ResetPasswordParams::class,
    ManagementUserListParams::class,
    UserDetailParams::class,
    SelfIdentifierListParams::class,
    IdentifierDetailParams::class,
    ManagementGlobalIdentifierListParams::class,
    ManagementUserIdentifierListParams::class,
    SelfSessionListParams::class,
    SessionDetailParams::class,
    ManagementGlobalSessionListParams::class,
    ManagementUserSessionListParams::class,
    TotpMainParams::class,
    UserStorageConfigurationPayload::class,
    UserSummaryPayload::class,
    UserPrivatePayload::class,
    AuthDataPayload::class,
    SessionTokenPayload::class,
    RefreshTokenPayload::class,
    UserIdentifierSummaryPayload::class,
    UserIdentifierPrivatePayload::class,
    UserSessionSummaryPayload::class,
    UserSessionPrivatePayload::class,
    DeletedSessionsPayload::class,
    OpenAuthSettingsPayload::class,
    ManagementAuthSettingsPayload::class,
    AvailableAuthProvidersPayload::class,
    OpenUserConfigurationPayload::class,
    ManagementUserConfigurationPayload::class,
    EmailRestrictionPolicyPayload::class,
    LoginByEmailRequest::class,
    LoginByPhoneRequest::class,
    LoginByExternalAuthProviderRequest::class,
    RegistrationByEmailRequest::class,
    CreateByEmailRequest::class,
    SendResetPasswordConfirmationRequest::class,
    ResetPasswordRequest::class,
    UnlockByEmailConfirmationRequest::class,
    UnlockByPhoneConfirmationRequest::class,
    UnlockByExternalAuthProviderRequest::class,
    SelfUpdateUserRequest::class,
    ManagementUpdateUserRequest::class,
    SendConfirmationToEmailRequest::class,
    SendConfirmationToPhoneRequest::class,
    EmailPasswordChangeRequest::class,
    AddUserIdentifierEmailRequest::class,
    AddUserIdentifierPhoneRequest::class,
    AddUserIdentifierExternalAuthProviderRequest::class,
    ManagementAuditEventListParams::class,
    ManagementAuditEventDetailParams::class,
    AuditEventMetadataPayload::class,
    AuditEventSummaryPayload::class,
    AuditEventPrivatePayload::class,
    OpenSecuritySettingsPayload::class,
    ManagementSecuritySettingsPayload::class,
    AccountLockoutPolicyPayload::class,
    IpRestrictionPolicyPayload::class,
    OpenPasswordPolicyPayload::class,
    ManagementPasswordPolicyPayload::class,
    OtpConfirmationPayload::class,
    TotpSetupPayload::class,
    TotpRecoveryCodesPayload::class,
    VerifyTotpPayload::class,
    OpenGlobalSettingsPayload::class,
    ManagementGlobalSettingsPayload::class,
    StorageFileDetailParams::class,
    StorageFileUploadParams::class,
    SelfStorageFileListParams::class,
    ManagementStorageFileListParams::class,
    StorageFileUploadUiConfig::class,
    FileUrlsPayload::class,
    StorageFileSummaryPayload::class,
    StorageFilePrivatePayload::class,
    PresignedUrlPayload::class,
    PresignedReadUrlBatchPayload::class,
    OpenStorageSettingsPayload::class,
    ManagementStorageSettingsPayload::class,
    DeleteFilesBatchRequest::class,
    PresignedUrlRequest::class,
    PresignedReadUrlBatchRequest::class
)

public val rawDomainToDtoClasses: List<Pair<KClass<*>, KClass<*>?>> = listOf(
    UserStorageConfiguration::class to UserStorageConfigurationPayload::class,
    UserSummary::class to UserSummaryPayload::class,
    UserPrivate::class to UserPrivatePayload::class,
    UserInternal::class to null,
    AuthData::class to AuthDataPayload::class,
    SessionToken::class to SessionTokenPayload::class,
    UserIdentifierSummary::class to UserIdentifierSummaryPayload::class,
    UserIdentifierPrivate::class to UserIdentifierPrivatePayload::class,
    UserIdentifierInternal::class to null,
    UserSessionSummary::class to UserSessionSummaryPayload::class,
    UserSessionPrivate::class to UserSessionPrivatePayload::class,
    UserSessionInternal::class to null,
    DeletedSessions::class to DeletedSessionsPayload::class,
    OpenAuthSettings::class to OpenAuthSettingsPayload::class,
    ManagementAuthSettings::class to ManagementAuthSettingsPayload::class,
    AvailableAuthProviders::class to AvailableAuthProvidersPayload::class,
    OpenUserConfiguration::class to OpenUserConfigurationPayload::class,
    ManagementUserConfiguration::class to ManagementUserConfigurationPayload::class,
    EmailRestrictionPolicy::class to EmailRestrictionPolicyPayload::class,
    EmailTemplate::class to null,
    ClientDeviceInfo::class to ClientDeviceInfoPayload::class,
    ClientInfo::class to ClientInfoPayload::class,
    AuditEventMetadata::class to AuditEventMetadataPayload::class,
    AuditEventSummary::class to AuditEventSummaryPayload::class,
    AuditEventPrivate::class to AuditEventPrivatePayload::class,
    AuditEventInternal::class to null,
    OpenSecuritySettings::class to OpenSecuritySettingsPayload::class,
    ManagementSecuritySettings::class to ManagementSecuritySettingsPayload::class,
    AccountLockoutPolicy::class to AccountLockoutPolicyPayload::class,
    IpRestrictionPolicy::class to IpRestrictionPolicyPayload::class,
    OpenPasswordPolicy::class to OpenPasswordPolicyPayload::class,
    ManagementPasswordPolicy::class to ManagementPasswordPolicyPayload::class,
    OtpConfirmation::class to OtpConfirmationPayload::class,
    TotpSetup::class to TotpSetupPayload::class,
    TotpRecoveryCodes::class to TotpRecoveryCodesPayload::class,
    VerifyTotp::class to VerifyTotpPayload::class,
    OpenGlobalSettings::class to OpenGlobalSettingsPayload::class,
    ManagementGlobalSettings::class to ManagementGlobalSettingsPayload::class,
    FileUrls::class to FileUrlsPayload::class,
    StorageFileSummary::class to StorageFileSummaryPayload::class,
    StorageFilePrivate::class to StorageFilePrivatePayload::class,
    StorageFileInternal::class to null,
    PresignedUrl::class to PresignedUrlPayload::class,
    PresignedReadUrlBatch::class to PresignedReadUrlBatchPayload::class,
    OpenStorageSettings::class to OpenStorageSettingsPayload::class,
    ManagementStorageSettings::class to ManagementStorageSettingsPayload::class,
    UploadQueueItem::class to null
)

public val dtoClasses: List<KClass<*>> = rawDtoClasses
public val domainToDtoClasses: List<Pair<KClass<*>, KClass<*>?>> = rawDomainToDtoClasses
