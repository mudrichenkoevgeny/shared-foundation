package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.user.security

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEvent
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CommonAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.verifytotp.VerifyTotpPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.totpsetup.TotpSetupPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.totprecoverycodes.TotpRecoveryCodesPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.action.UserAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.resource.UserAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.user.security.BaseSelfManagementUserSecurityRoutes

/**
 * Route paths for the authenticated user's self-service security management.
 *
 * Sensitive operations (disabling TOTP, viewing/regenerating recovery codes) require
 * a recently re-authenticated session.
 */
object SelfManagementUserSecurityRoutes {

    /**
     * **HTTP method:** `POST`
     *
     * Generates a new TOTP secret and returns setup data (secret key, otpauth URL, and challenge token).
     * The factor is not active until verified via [ENABLE_TOTP].
     *
     * Response body: [TotpSetupPayload].
     *
     * **Audit logging:** Persist an [AuditEvent] for setup initiation and for security-relevant denials.
     * * **Action:** [UserAuditActionType.SELF_SETUP_TOTP_INITIATED].
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the current user's [UserId].
     * * **Resource:** [UserAuditResourceType.USER]. Set `resourceId` to the current user's [UserId].
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey])
     */
    const val SETUP_TOTP = BaseSelfManagementUserSecurityRoutes.TOTP_SETUP

    /**
     * **HTTP method:** `POST`
     *
     * Finalizes TOTP activation by verifying the first code from the authenticator app.
     * Upon success, TOTP is marked as enabled for the account.
     *
     * Request body: [VerifyTotpPayload].
     *
     * Response body: [TotpRecoveryCodesPayload] (initial set of backup codes).
     *
     * **Audit logging:** Persist an [AuditEvent] for successful activation and for security-relevant denials.
     * * **Action:** [UserAuditActionType.SELF_ENABLE_TOTP].
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the current user's [UserId].
     * * **Resource:** [UserAuditResourceType.USER]. Set `resourceId` to the current user's [UserId].
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey])
     */
    const val ENABLE_TOTP = BaseSelfManagementUserSecurityRoutes.TOTP_ENABLE

    /**
     * **HTTP method:** `DELETE`
     *
     * Disables TOTP and invalidates all associated recovery codes for the authenticated account.
     * Requires a recently verified session (step-up).
     *
     * **Audit logging:** Persist an [AuditEvent] for successful disabling and for security-relevant denials.
     * * **Action:** [UserAuditActionType.SELF_DISABLE_TOTP].
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the current user's [UserId].
     * * **Resource:** [UserAuditResourceType.USER]. Set `resourceId` to the current user's [UserId].
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey])
     */
    const val DISABLE_TOTP = BaseSelfManagementUserSecurityRoutes.TOTP_DISABLE

    /**
     * **HTTP method:** `GET`
     *
     * Returns the current active recovery codes for the authenticated user.
     * Requires a recently verified session (step-up).
     *
     * Response body: [TotpRecoveryCodesPayload].
     *
     * **Audit logging:** Persist an [AuditEvent] for code retrieval and for security-relevant denials.
     * * **Action:** [UserAuditActionType.SELF_GET_RECOVERY_CODES].
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the current user's [UserId].
     * * **Resource:** [UserAuditResourceType.USER]. Set `resourceId` to the current user's [UserId].
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey])
     */
    const val GET_RECOVERY_CODES = BaseSelfManagementUserSecurityRoutes.GET_RECOVERY_CODES

    /**
     * **HTTP method:** `POST`
     *
     * Invalidates all existing recovery codes and generates a new set for the account.
     * Requires a recently verified session (step-up).
     *
     * Response body: [TotpRecoveryCodesPayload].
     *
     * **Audit logging:** Persist an [AuditEvent] for successful regeneration and for security-relevant denials.
     * * **Action:** [UserAuditActionType.SELF_REGENERATE_RECOVERY_CODES].
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the current user's [UserId].
     * * **Resource:** [UserAuditResourceType.USER]. Set `resourceId` to the current user's [UserId].
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey])
     */
    const val REGENERATE_RECOVERY_CODES = BaseSelfManagementUserSecurityRoutes.REGENERATE_RECOVERY_CODES
}