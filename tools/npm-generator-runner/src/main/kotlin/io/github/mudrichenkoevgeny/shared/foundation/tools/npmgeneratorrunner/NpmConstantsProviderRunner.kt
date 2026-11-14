package io.github.mudrichenkoevgeny.shared.foundation.tools.npmgeneratorrunner

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingDefaultValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.ui.UiValues

internal fun buildExtraConstantsCode(): String = buildListingConstantsCode()

private fun buildListingConstantsCode(): String = """
export const CommonLocales = {
  ENGLISH: "en",
  RUSSIAN: "ru",
  DEFAULT: "en",
  SUPPORTED: ["en", "ru"]
};

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
},
Search: {
SEARCH_QUERY: "${ListingParamNames.Search.SEARCH_QUERY}"
}
};

export const ListingDefaultValues = {
INITIAL_PAGE_NUMBER: ${ListingDefaultValues.Pagination.INITIAL_PAGE_NUMBER},
DEFAULT_PAGE_SIZE: ${ListingDefaultValues.Pagination.DEFAULT_PAGE_SIZE},
LARGE_PAGE_SIZE: ${ListingDefaultValues.Pagination.LARGE_PAGE_SIZE},
Pagination: {
INITIAL_PAGE_NUMBER: ${ListingDefaultValues.Pagination.INITIAL_PAGE_NUMBER},
DEFAULT_PAGE_SIZE: ${ListingDefaultValues.Pagination.DEFAULT_PAGE_SIZE},
LARGE_PAGE_SIZE: ${ListingDefaultValues.Pagination.LARGE_PAGE_SIZE}
}
};

export const UiValues = {
Placeholder: {
EMAIL: "${UiValues.Placeholder.EMAIL}",
TOTP_CODE: "${UiValues.Placeholder.TOTP_CODE}"
}
};
""".trimIndent()

internal fun buildExtraConstantsDts(): String = """
export declare const CommonLocales: {
  readonly ENGLISH: string;
  readonly RUSSIAN: string;
  readonly DEFAULT: string;
  readonly SUPPORTED: readonly string[];
};

export declare const ListingParamNames: {
  readonly PAGE_NUMBER: string;
  readonly PAGE_SIZE: string;
  readonly SORT_BY: string;
  readonly SORT_ORDER: string;
  readonly Pagination: {
    readonly PAGE_NUMBER: string;
    readonly PAGE_SIZE: string;
  };
  readonly Sort: {
    readonly SORT_BY: string;
    readonly SORT_ORDER: string;
  };
  readonly Search: {
    readonly SEARCH_QUERY: string;
  };
};

export declare const ListingDefaultValues: {
  readonly INITIAL_PAGE_NUMBER: number;
  readonly DEFAULT_PAGE_SIZE: number;
  readonly LARGE_PAGE_SIZE: number;
  readonly Pagination: {
    readonly INITIAL_PAGE_NUMBER: number;
    readonly DEFAULT_PAGE_SIZE: number;
    readonly LARGE_PAGE_SIZE: number;
  };
};

export declare const UiValues: {
  readonly Placeholder: {
    readonly EMAIL: string;
    readonly TOTP_CODE: string;
  };
};
""".trimIndent()

internal fun buildExtraDtsCode(): String = """
export type UserStorageTargetScopes = string;

export interface SecurityModuleStrings {
  resolvePasswordTooWeakReasons(args?: Record<string, unknown> | null): string[];
}
export declare function resolvePasswordTooWeakReasons(strings: SecurityModuleStrings, args?: Record<string, unknown> | null): string[];

export interface UserModuleStrings {
  resolveUserLockedMessage(lockoutType?: AccountLockoutType | string | null, formattedUntil?: string | null): string;
  resolveUserIdentifierLimitReachedMessage(limit: number | string | null | undefined, providerRaw: string | null | undefined): string;
  resolveUserIdentifierLimitReachedMessage(commonStrings: CommonModuleStrings, limit: number | string | null | undefined, providerRaw: string | null | undefined): string;
  resolveTotalUserIdentifiersLimitReachedMessage(limit?: number | string | null): string;
  resolveNicknameTooShortMessage(minLength?: number | string | null): string;
  resolveNicknameTooLongMessage(maxLength?: number | string | null): string;
  resolveDisplayNameTooShortMessage(minLength?: number | string | null): string;
  resolveDisplayNameTooLongMessage(maxLength?: number | string | null): string;
}

export declare function formatByteSize(bytesRaw: number | string | bigint | unknown, locale?: string, decimals?: number, resolver?: LocalizedStringResolver): string;
export declare function formatByteSize(bytesRaw: number | string | bigint | unknown, locale: string, resolver: LocalizedStringResolver): string;
export declare function createTestPagedResult<T>(overrides?: Partial<PagedResult<T>>): PagedResult<T>;
export declare function createTestPagedResultPayload<T>(overrides?: Record<string, unknown>): Record<string, unknown>;
export declare function generateTestUuidString(index?: number, group?: number): string;

export declare class PasswordPolicyValidator {
  validate(policy: unknown, password: unknown): { success: boolean; reasons: PasswordPolicyFailReason[] };
}

export declare const passwordPolicyValidator: PasswordPolicyValidator;

export declare const storageUploadValidator: {
  isFileTypeAccepted(acceptedFileTypes: string[] | null | undefined, fileName: string | null | undefined, mimeType: string | null | undefined): boolean;
  isFileSizeValid(maxFileSizeBytes: number | null | undefined, fileSizeBytes: number | null | undefined): boolean;
  isBatchSizeValid(maxFiles: number | null | undefined, currentCount: number | null | undefined): boolean;
};

export declare const nicknameValidator: {
  validate(nickname: string | null | undefined, minLength: number, maxLength: number, isNicknameChangeAllowed?: boolean, isNicknameChanging?: boolean): string | null;
};

export declare const displayNameValidator: {
  validate(displayName: string | null | undefined, minLength: number, maxLength: number): string | null;
};

export declare function resolveUserDisplayName(user: Record<string, unknown> | null | undefined, fallbackPrefix: string): string;

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

export declare const TEST_USER_ID: string;
export declare const TEST_USER_ID_2: string;
export declare const TEST_IDENTIFIER_ID: string;
export declare const TEST_SESSION_ID: string;
export declare const TEST_AUDIT_EVENT_ID: string;
export declare const TEST_FILE_ID: string;
export declare const TEST_CLIENT_DEVICE_ID: string;
export declare const TEST_EMAIL: string;
export declare const TEST_PHONE: string;
export declare const TEST_IP_ADDRESS: string;
export declare const TEST_USER_AGENT: string;

export declare const TEST_PERMISSION_CODE: PermissionCode;
export declare const TEST_DECRYPTED_STRING: DecryptedString;
export declare const TEST_ENCRYPTED_STRING: EncryptedString;
export declare const TEST_PASSWORD_HASH: PasswordHash;
export declare const TEST_ACCESS_TOKEN: AccessToken;
export declare const TEST_REFRESH_TOKEN: RefreshToken;
export declare const TEST_REFRESH_TOKEN_HASH: RefreshTokenHash;
export declare const TEST_TIME_MILLIS: number;

export declare const SharedTestConstants: Record<string, string | number>;

export declare class LocalizedStringResolverMock {
  constructor(prefix?: string, nullKey?: string | null);
  resolve(modulePath: string, bundleName: string, key: string, locale?: string): string | undefined;
}

export declare class LocalizationBundleProviderMock {
  constructor(bundles?: Record<string, Record<string, string>>);
  get(modulePath: string, bundleName: string, locale?: string): Record<string, string>;
  getResolver(): LocalizedStringResolver;
}

export declare class PasswordPolicyValidatorMock {
  constructor(failReasons?: PasswordPolicyFailReason[]);
  validate(policy: unknown, password: unknown): { isSuccess: boolean; reasons: PasswordPolicyFailReason[] };
}
""".trimIndent()

internal fun buildExtraJsCode(): String = listOf(
    buildValidatorsJsCode(),
    buildCompositeParsersJsCode(),
    buildTestingJsCode(),
    buildFormattingJsCode()
).joinToString("\n\n")

private fun buildValidatorsJsCode(): String = """
export class PasswordPolicyValidator {
  validate(policy, password) {
    if (!policy || typeof password !== 'string') {
      return { success: false, reasons: [PasswordPolicyFailReason.TOO_SHORT] };
    }
    const reasons = [];
    const minLength = policy.minLength ?? 0;
    if (password.length < minLength) {
      reasons.push(PasswordPolicyFailReason.TOO_SHORT);
    }
    if (policy.requireLetter && !/[a-zA-Z]/.test(password)) {
      reasons.push(PasswordPolicyFailReason.NO_LETTER);
    }
    if (policy.requireUpperCase && !/[A-Z]/.test(password)) {
      reasons.push(PasswordPolicyFailReason.NO_UPPERCASE);
    }
    if (policy.requireLowerCase && !/[a-z]/.test(password)) {
      reasons.push(PasswordPolicyFailReason.NO_LOWERCASE);
    }
    if (policy.requireDigit && !/[0-9]/.test(password)) {
      reasons.push(PasswordPolicyFailReason.NO_DIGIT);
    }
    if (policy.requireSpecialChar && !/[^a-zA-Z0-9]/.test(password)) {
      reasons.push(PasswordPolicyFailReason.NO_SPECIAL_CHAR);
    }
    if (Array.isArray(policy.commonPasswords) && policy.commonPasswords.includes(password)) {
      reasons.push(PasswordPolicyFailReason.TOO_COMMON);
    }

    if (reasons.length > 0) {
      return { success: false, reasons };
    }
    return { success: true, reasons: [] };
  }
}

export const passwordPolicyValidator = new PasswordPolicyValidator();

export const storageUploadValidator = {
  isFileTypeAccepted: (acceptedFileTypes, fileName, mimeType) => {
    if (!Array.isArray(acceptedFileTypes) || acceptedFileTypes.length === 0) return true;
    if (!fileName) return false;

    const lowerFileName = fileName.toLowerCase();
    const lastDot = lowerFileName.lastIndexOf('.');
    const extension = lastDot !== -1 ? lowerFileName.substring(lastDot) : '';
    const normalizedMimeType = mimeType ? mimeType.toLowerCase().trim() : null;

    return acceptedFileTypes.some((rawFilter) => {
      if (!rawFilter) return false;
      const filter = rawFilter.trim().toLowerCase();
      if (!filter) return false;

      if (filter.startsWith('.')) {
        return extension === filter || lowerFileName.endsWith(filter);
      }
      if (filter.endsWith('/*')) {
        const mimePrefix = filter.slice(0, -2);
        return Boolean(normalizedMimeType && normalizedMimeType.startsWith(mimePrefix + '/'));
      }
      if (filter.includes('/')) {
        return normalizedMimeType === filter;
      }
      return extension === '.' + filter;
    });
  },

  isFileSizeValid: (maxFileSizeBytes, fileSizeBytes) => {
    if (maxFileSizeBytes === null || maxFileSizeBytes === undefined || maxFileSizeBytes <= 0) return true;
    if (typeof fileSizeBytes !== 'number' || fileSizeBytes < 0) return false;
    return fileSizeBytes <= maxFileSizeBytes;
  },

  isBatchSizeValid: (maxFiles, currentCount) => {
    if (maxFiles === null || maxFiles === undefined || maxFiles <= 0) return typeof currentCount === 'number' && currentCount >= 0;
    if (typeof currentCount !== 'number' || currentCount < 0) return false;
    return currentCount <= maxFiles;
  }
};

export const nicknameValidator = {
  validate: (nickname, minLength, maxLength, isNicknameChangeAllowed = true, isNicknameChanging = false) => {
    if (nickname === null || nickname === undefined) return null;
    if (isNicknameChanging && !isNicknameChangeAllowed) return UserErrorCodes.NICKNAME_CHANGE_DISABLED;
    const valStr = String(nickname);
    if (valStr.length < minLength) return UserErrorCodes.NICKNAME_TOO_SHORT;
    if (valStr.length > maxLength) return UserErrorCodes.NICKNAME_TOO_LONG;
    if (!/^[a-zA-Z0-9](?:[._]?[a-zA-Z0-9]+)*$/.test(valStr)) return UserErrorCodes.INVALID_NICKNAME_CHARACTERS;
    return null;
  }
};

export const displayNameValidator = {
  validate: (displayName, minLength, maxLength) => {
    if (displayName === null || displayName === undefined) return null;
    const valStr = String(displayName);
    if (valStr.length < minLength) return UserErrorCodes.DISPLAY_NAME_TOO_SHORT;
    if (valStr.length > maxLength) return UserErrorCodes.DISPLAY_NAME_TOO_LONG;
    if (/[\x00-\x1F\x7F-\x9F]/.test(valStr)) return UserErrorCodes.INVALID_DISPLAY_NAME_CHARACTERS;
    return null;
  }
};

export function resolveUserDisplayName(user, fallbackPrefix) {
  if (!user) return fallbackPrefix;
  const displayName = user.displayName || user.display_name;
  if (displayName && String(displayName).trim().length > 0) {
    return String(displayName).trim();
  }
  if (user.nickname && String(user.nickname).trim().length > 0) {
    return '@' + String(user.nickname).trim();
  }
  const rawId = String(user.id || '');
  const shortId = rawId.substring(0, 8);
  return (fallbackPrefix + ' ' + shortId).trim();
}
""".trimIndent()

private fun buildCompositeParsersJsCode(): String = """
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

private fun buildTestingJsCode(): String {
    val passHashStr = "\"\$2a\$12\$e86d/6gX2S3K4u5.v6w7x8y9z0a1b2c3d4e5f6g7h8i9j0\""
    return """
export const TEST_USER_ID = "00000000-0000-0000-0000-000000000001";
export const TEST_USER_ID_2 = "00000000-0000-0000-0000-000000000002";
export const TEST_IDENTIFIER_ID = "00000000-0000-0000-0000-000000000003";
export const TEST_SESSION_ID = "00000000-0000-0000-0000-000000000004";
export const TEST_AUDIT_EVENT_ID = "00000000-0000-0000-0000-000000000005";
export const TEST_FILE_ID = "00000000-0000-0000-0000-000000000006";
export const TEST_CLIENT_DEVICE_ID = "00000000-0000-0000-0000-000000000007";
export const TEST_EMAIL = "test@example.com";
export const TEST_PHONE = "+15550000000";
export const TEST_IP_ADDRESS = "127.0.0.1";
export const TEST_USER_AGENT = "Mozilla/5.0 (TestApp/1.0)";

export const TEST_PERMISSION_CODE = toPermissionCodeOrThrow("USER_READ");
export const TEST_DECRYPTED_STRING = toDecryptedStringOrThrow("decrypted_secret_value");
export const TEST_ENCRYPTED_STRING = toEncryptedStringOrThrow("encrypted_payload_string");
export const TEST_PASSWORD_HASH = toPasswordHashOrThrow($passHashStr);
export const TEST_ACCESS_TOKEN = toAccessTokenOrThrow("test_access_token_jwt");
export const TEST_REFRESH_TOKEN = toRefreshTokenOrThrow("test_refresh_token_string");
export const TEST_REFRESH_TOKEN_HASH = toRefreshTokenHashOrThrow("test_refresh_token_hash");
export const TEST_TIME_MILLIS = 1700000000000;

export const SharedTestConstants = {
  TEST_IP_ADDRESS,
  TEST_USER_AGENT,
  TEST_EMAIL,
  TEST_PHONE,
  TEST_TIME_MILLIS
};

export const generateTestUuidString = (index = 0, group = 0) => {
  const safeGroup = Math.max(0, Number(group) || 0);
  const safeIndex = Math.max(0, Number(index) || 0);
  const groupHex = safeGroup.toString(16).padStart(8, '0');
  const indexHex = (safeIndex + 1).toString(16).padStart(12, '0');
  return `${'$'}{groupHex}-0000-0000-0000-${'$'}{indexHex}`;
};

export const createTestPagedResult = (overrides = {}) => {
  const items = overrides?.items ?? [];
  const totalCount = overrides?.totalCount ?? items.length;
  const pageSize = overrides?.pageSize ?? 20;
  const totalPages = overrides?.totalPages ?? (totalCount === 0 ? 0 : Math.ceil(totalCount / pageSize));
  const pageNumber = overrides?.pageNumber ?? 1;
  return { items, pageNumber, pageSize, totalCount, totalPages, ...overrides };
};

export const createTestPagedResultPayload = (overrides = {}) => {
  const items = overrides?.items ?? [];
  const total_count = overrides?.total_count ?? items.length;
  const page_size = overrides?.page_size ?? 20;
  const total_pages = overrides?.total_pages ?? (total_count === 0 ? 0 : Math.ceil(total_count / page_size));
  const page_number = overrides?.page_number ?? 1;
  return { items, page_number, page_size, total_count, total_pages, ...overrides };
};

export class LocalizedStringResolverMock {
  constructor(prefix = '[Test]', nullKey = null) {
    this.prefix = prefix;
    this.nullKey = nullKey;
  }
  resolve(modulePath, bundleName, key, locale = 'en') {
    if (this.nullKey && key === this.nullKey) return null;
    return `${'$'}{this.prefix} ${'$'}{locale}:${'$'}{modulePath}:${'$'}{bundleName}:${'$'}{key}`;
  }
}

export class LocalizationBundleProviderMock {
  constructor(bundles = {}) {
    this.bundles = bundles;
  }
  get(modulePath, bundleName, locale = 'en') {
    const key = `${'$'}{modulePath}/${'$'}{bundleName}/${'$'}{locale}`;
    return this.bundles[key];
  }
  getResolver() {
    return (modulePath, bundleName, key, locale) => this.get(modulePath, bundleName, locale)?.[key];
  }
}

export class PasswordPolicyValidatorMock {
  constructor(failReasons = []) {
    this.failReasons = failReasons;
  }
  validate(policy, password) {
    return {
      isSuccess: this.failReasons.length === 0,
      reasons: this.failReasons
    };
  }
}
""".trimIndent()
}

private fun buildFormattingJsCode(): String = """
export function formatByteSize(bytesRaw, locale, decimalsOrResolver, resolver) {
  const ONE_KB = 1024;
  const ONE_MB = 1024 * 1024;
  const ONE_GB = 1024 * 1024 * 1024;
  const ONE_TB = 1024 * 1024 * 1024 * 1024;

  const bytes = Number(bytesRaw);
  if (isNaN(bytes) || bytes < 0) return '0 B';

  const absBytes = Math.abs(bytes);
  let activeResolver = defaultLocalizedStringResolver;
  let activeLocale = 'en';

  if (typeof locale === 'string') activeLocale = locale;
  if (typeof locale === 'function') activeResolver = locale;
  if (typeof decimalsOrResolver === 'function') activeResolver = decimalsOrResolver;
  if (typeof resolver === 'function') activeResolver = resolver;

  const getUnit = (key) => activeResolver('core/common', 'general', key, activeLocale) ?? key;
  const decimals = typeof decimalsOrResolver === 'number' ? decimalsOrResolver : 0;

  const formatValue = (divider) => {
    const val = bytes / divider;
    return decimals > 0 ? val.toFixed(decimals) : String(Math.trunc(val));
  };

  if (absBytes >= ONE_TB) return formatValue(ONE_TB) + ' ' + getUnit('byte_unit_tb');
  if (absBytes >= ONE_GB) return formatValue(ONE_GB) + ' ' + getUnit('byte_unit_gb');
  if (absBytes >= ONE_MB) return formatValue(ONE_MB) + ' ' + getUnit('byte_unit_mb');
  if (absBytes >= ONE_KB) return formatValue(ONE_KB) + ' ' + getUnit('byte_unit_kb');
  return bytes + ' ' + getUnit('byte_unit_b');
}
""".trimIndent()

internal fun buildCustomDtsSmokeTestCode(): String = """
const _assert_user: Pkg.UserSummaryPayload = Pkg.userSummaryPayloadSchema.parse({ id: "00000000-0000-0000-0000-000000000001" });
const _assert_perm: Pkg.PermissionCode = Pkg.toPermissionCodeOrThrow("USER_READ");
const _assert_routes: string = Pkg.ManagementUsersRoutes.GET_USERS;
const _assert_list: Pkg.UserSummaryPayload[] = Pkg.createTestUserSummaryPayloadList(2);
const _assert_paged: Pkg.PagedResult<Pkg.UserSummaryPayload> = Pkg.createTestPagedResult();
const _filter_map: Record<string, string> = {};
const _assert_filter_index: string | undefined = _filter_map[Pkg.UserFilterValues.USER_ID];
const _assert_pwd_reasons: Pkg.PasswordPolicyFailReason[] = Pkg.passwordPolicyValidator.validate({}, "123").reasons;

function _testExhaustiveAppType(appType: Pkg.AppType): string {
  switch (appType) {
    case Pkg.AppType.CLIENT:
      return "c";
    case Pkg.AppType.MANAGEMENT:
      return "m";
  }
}

function _testExhaustiveStorageFileStatus(status: Pkg.StorageFileStatus): string {
  switch (status) {
    case Pkg.StorageFileStatus.PENDING_UPLOAD:
      return "p";
    case Pkg.StorageFileStatus.READY:
      return "r";
    case Pkg.StorageFileStatus.PROCESSING:
      return "proc";
    case Pkg.StorageFileStatus.DELETED:
      return "d";
  }
}
""".trimIndent()

internal fun buildCustomRuntimeSmokeTestCode(): String = """
if (typeof Pkg.toPermissionCodeOrThrow === 'function') {
  try {
    const perm = Pkg.toPermissionCodeOrThrow("USER_READ");
    if (perm !== "USER_READ") errors++;
  } catch (e) {
    errors++;
  }
}
if (typeof Pkg.toUserIdOrThrow === 'function') {
  try {
    const uid = Pkg.toUserIdOrThrow("00000000-0000-0000-0000-000000000001");
    if (uid !== "00000000-0000-0000-0000-000000000001") errors++;
  } catch (e) {
    errors++;
  }
}
if (typeof Pkg.createTestUserSummaryPayloadList === 'function') {
  try {
    const list = Pkg.createTestUserSummaryPayloadList(3);
    if (!Array.isArray(list) || list.length !== 3) errors++;
  } catch (e) {
    errors++;
  }
}
if (typeof Pkg.createTestPagedResult === 'function') {
  try {
    const paged = Pkg.createTestPagedResult({ totalCount: 10 });
    if (paged.totalCount !== 10) errors++;
  } catch (e) {
    errors++;
  }
}
if (typeof Pkg.toQueryMap === 'function') {
  try {
    const map = Pkg.toQueryMap({ pageNumber: 1, pageSize: 20, userId: "123", roles: ["admin"] });
    if (map.pageNumber || !map.page_number || !map.user_id || !map.roles) errors++;
  } catch (e) {
    errors++;
  }
}
if (typeof Pkg.securityStrings === 'object' && typeof Pkg.securityStrings.resolvePasswordTooWeakReasons === 'function') {
  try {
    const reasons = Pkg.securityStrings.resolvePasswordTooWeakReasons({ passwordTooShort: true, passwordMinLength: 8 });
    if (!Array.isArray(reasons) || reasons.length === 0) errors++;
  } catch (e) {
    errors++;
  }
}
if (typeof Pkg.userStrings === 'object' && typeof Pkg.userStrings.resolveUserLockedMessage === 'function') {
  try {
    const lockedMsg = Pkg.userStrings.resolveUserLockedMessage("temporary", "2026-03-12");
    if (typeof lockedMsg !== 'string' || lockedMsg.length === 0) errors++;
    const limitMsg = Pkg.userStrings.resolveUserIdentifierLimitReachedMessage(3, "email");
    if (typeof limitMsg !== 'string' || limitMsg.length === 0) errors++;
  } catch (e) {
    errors++;
  }
}
""".trimIndent()

internal fun buildPostLocalizationJsCode(): String = """
export function resolvePasswordTooWeakReasons(strings, args) {
  if (!strings || !strings.errors) return [];
  if (!args || typeof args !== 'object') return [strings.errors.passwordTooWeak];

  const minLength = args.passwordMinLength ?? args.minLength ?? args.min_length ?? null;
  const reasonsArr = Array.isArray(args.reasons) ? args.reasons.map(r => String(r).toUpperCase()) : [];

  const isTooShort = Boolean(args.passwordTooShort || args.isTooShort || args.too_short || reasonsArr.includes('TOO_SHORT') || reasonsArr.includes('PASSWORD_TOO_SHORT'));
  const isNoLetter = Boolean(args.passwordNoLetter || args.isNoLetter || args.no_letter || reasonsArr.includes('NO_LETTER') || reasonsArr.includes('PASSWORD_NO_LETTER'));
  const isNoUpperCase = Boolean(args.passwordNoUpperCase || args.isNoUpperCase || args.no_uppercase || reasonsArr.includes('NO_UPPERCASE') || reasonsArr.includes('PASSWORD_NO_UPPERCASE'));
  const isNoLowerCase = Boolean(args.passwordNoLowerCase || args.isNoLowerCase || args.no_lowercase || reasonsArr.includes('NO_LOWERCASE') || reasonsArr.includes('PASSWORD_NO_LOWERCASE'));
  const isNoDigit = Boolean(args.passwordNoDigit || args.isNoDigit || args.no_digit || reasonsArr.includes('NO_DIGIT') || reasonsArr.includes('PASSWORD_NO_DIGIT'));
  const isNoSpecialChar = Boolean(args.passwordNoSpecialChar || args.isNoSpecialChar || args.no_special_char || reasonsArr.includes('NO_SPECIAL_CHAR') || reasonsArr.includes('PASSWORD_NO_SPECIAL_CHAR'));
  const isTooCommon = Boolean(args.passwordTooCommon || args.isTooCommon || args.too_common || reasonsArr.includes('TOO_COMMON') || reasonsArr.includes('PASSWORD_TOO_COMMON'));

  const result = [];
  if (isTooShort) {
    if (minLength !== null && minLength !== undefined) {
      result.push(typeof strings.errors.passwordTooShort === 'function' ? strings.errors.passwordTooShort(minLength) : strings.errors.passwordTooShort);
    } else if (strings.errors.passwordTooShortWithoutArgs) {
      result.push(strings.errors.passwordTooShortWithoutArgs);
    } else {
      result.push(typeof strings.errors.passwordTooShort === 'function' ? strings.errors.passwordTooShort(0) : strings.errors.passwordTooShort);
    }
  }

  if (isNoLetter && strings.errors.passwordNoLetter) result.push(strings.errors.passwordNoLetter);
  if (isNoUpperCase && strings.errors.passwordNoUppercase) result.push(strings.errors.passwordNoUppercase);
  if (isNoLowerCase && strings.errors.passwordNoLowercase) result.push(strings.errors.passwordNoLowercase);
  if (isNoDigit && strings.errors.passwordNoDigit) result.push(strings.errors.passwordNoDigit);
  if (isNoSpecialChar && strings.errors.passwordNoSpecialChar) result.push(strings.errors.passwordNoSpecialChar);
  if (isTooCommon && strings.errors.passwordTooCommon) result.push(strings.errors.passwordTooCommon);

  return result.length > 0 ? result : [strings.errors.passwordTooWeak];
}

if (typeof SecurityModuleStrings !== 'undefined') {
  SecurityModuleStrings.prototype.resolvePasswordTooWeakReasons = function(args) {
    return resolvePasswordTooWeakReasons(this, args);
  };
}

if (typeof UserModuleStrings !== 'undefined') {
  UserModuleStrings.prototype.resolveUserLockedMessage = function(lockoutType, formattedUntil) {
    if (lockoutType === AccountLockoutType.TEMPORARY && formattedUntil && String(formattedUntil).trim().length > 0) {
      return this.errors.locked(formattedUntil);
    }
    return this.errors.lockedWithoutArgs;
  };

  UserModuleStrings.prototype.resolveUserIdentifierLimitReachedMessage = function(arg1, arg2, arg3) {
    const hasCustomCommon = arg1 && typeof arg1 === 'object' && 'general' in arg1;
    const targetCommon = hasCustomCommon ? arg1 : commonStrings;
    const limit = hasCustomCommon ? arg2 : arg1;
    const providerRaw = hasCustomCommon ? arg3 : arg2;

    if (limit === null || limit === undefined || !providerRaw || String(providerRaw).trim().length === 0) {
      return this.errors.identifierLimitReachedWithoutArgs;
    }

    const normalized = String(providerRaw).toLowerCase();
    let localizedProvider;
    if (normalized === 'email') {
      localizedProvider = targetCommon.general.email;
    } else if (normalized === 'phone') {
      localizedProvider = targetCommon.general.phone;
    } else {
      localizedProvider = targetCommon.general.externalProvider;
    }

    return this.errors.identifierLimitReached({ limit, provider: localizedProvider });
  };

  UserModuleStrings.prototype.resolveTotalUserIdentifiersLimitReachedMessage = function(limit) {
    return limit !== null && limit !== undefined
      ? this.errors.totalIdentifiersLimitReached(limit)
      : this.errors.totalIdentifiersLimitReachedWithoutArgs;
  };

  UserModuleStrings.prototype.resolveNicknameTooShortMessage = function(minLength) {
    return minLength !== null && minLength !== undefined
      ? this.errors.nicknameTooShort(minLength)
      : this.errors.nicknameTooShortWithoutArgs;
  };

  UserModuleStrings.prototype.resolveNicknameTooLongMessage = function(maxLength) {
    return maxLength !== null && maxLength !== undefined
      ? this.errors.nicknameTooLong(maxLength)
      : this.errors.nicknameTooLongWithoutArgs;
  };

  UserModuleStrings.prototype.resolveDisplayNameTooShortMessage = function(minLength) {
    return minLength !== null && minLength !== undefined
      ? this.errors.displayNameTooShort(minLength)
      : this.errors.displayNameTooShortWithoutArgs;
  };

  UserModuleStrings.prototype.resolveDisplayNameTooLongMessage = function(maxLength) {
    return maxLength !== null && maxLength !== undefined
      ? this.errors.displayNameTooLong(maxLength)
      : this.errors.displayNameTooLongWithoutArgs;
  };
}
""".trimIndent()
