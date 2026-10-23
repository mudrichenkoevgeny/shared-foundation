package io.github.mudrichenkoevgeny.shared.foundation.generator.testing

import io.github.mudrichenkoevgeny.shared.foundation.generator.collector.getEnumInfo
import io.github.mudrichenkoevgeny.shared.foundation.generator.collector.rawDomainToDtoClasses
import io.github.mudrichenkoevgeny.shared.foundation.generator.collector.rawDtoClasses
import io.github.mudrichenkoevgeny.shared.foundation.generator.doc.extractKDoc
import io.github.mudrichenkoevgeny.shared.foundation.generator.doc.formatAsJsDoc
import io.github.mudrichenkoevgeny.shared.foundation.generator.mapper.getWireKey
import java.io.File
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.primaryConstructor

fun extractClassProperties(kClass: KClass<*>): List<Pair<String, KType>> {
    val constructor = kClass.primaryConstructor ?: return emptyList()
    return constructor.parameters.mapNotNull { param ->
        val name = param.name ?: return@mapNotNull null
        name to param.type
    }
}

fun getDefaultMockValueJs(paramName: String, type: KType, ownerClass: KClass<*>? = null): String {
    val classifier = type.classifier as? KClass<*>
    val typeName = classifier?.simpleName ?: ""
    val ownerClassName = ownerClass?.simpleName ?: ""

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
                paramName == "role" || paramName == "userRole" || paramName == "actorUserRole" || paramName == "user_role" || paramName == "actor_user_role" -> "\"user\""
                paramName == "accountStatus" || paramName == "accountStatusOnRestore" || paramName == "account_status" || paramName == "account_status_on_restore" -> "\"active\""
                paramName == "lockoutType" || paramName == "accountLockoutType" || paramName == "lockout_type" || paramName == "account_lockout_type" -> "\"none\""
                paramName == "identifierAuthProvider" || paramName == "userAuthProvider" || paramName == "identifier_auth_provider" || paramName == "user_auth_provider" -> "\"email\""
                paramName == "actorType" || paramName == "actor_type" -> "\"user\""
                paramName == "action" -> "\"login.email\""
                paramName == "resource" -> "\"audit.event\""
                paramName == "status" -> "\"success\""
                paramName == "clientType" || paramName == "client_type" -> "\"android\""
                paramName == "themeMode" || paramName == "theme_mode" -> "\"system\""
                paramName == "sortOrder" || paramName == "sort_order" -> "\"desc\""
                paramName == "id" || paramName.endsWith("Id") || paramName.endsWith("_id") -> {
                    when (paramName) {
                        "userId", "user_id", "actorId", "actor_id", "resourceId", "resource_id" -> "TEST_USER_ID"
                        "sessionId", "session_id" -> "TEST_SESSION_ID"
                        "identifierId", "userIdentifierId", "user_identifier_id" -> "TEST_IDENTIFIER_ID"
                        "auditEventId", "event_id", "eventId", "audit_event_id" -> "TEST_AUDIT_EVENT_ID"
                        "fileId", "file_id" -> "TEST_FILE_ID"
                        "clientDeviceId", "device_id", "deviceId" -> "TEST_CLIENT_DEVICE_ID"
                        "id" -> {
                            if (ownerClassName.contains("UserIdentifier")) "TEST_IDENTIFIER_ID"
                            else if (ownerClassName.contains("UserSession") || ownerClassName.contains("DeletedSession") || ownerClassName.contains("RefreshToken")) "TEST_SESSION_ID"
                            else if (ownerClassName.contains("AuditEvent")) "TEST_AUDIT_EVENT_ID"
                            else if (ownerClassName.contains("StorageFile")) "TEST_FILE_ID"
                            else if (ownerClassName.contains("User") || ownerClassName.contains("AuthData")) "TEST_USER_ID"
                            else "\"00000000-0000-0000-0000-000000000000\""
                        }
                        else -> "\"00000000-0000-0000-0000-000000000000\""
                    }
                }
                paramName.contains("email", ignoreCase = true) -> "TEST_EMAIL"
                paramName.contains("phone", ignoreCase = true) -> "TEST_PHONE"
                paramName.contains("ipAddress", ignoreCase = true) || paramName.contains("ip_address", ignoreCase = true) -> "TEST_IP_ADDRESS"
                paramName.contains("userAgent", ignoreCase = true) || paramName.contains("user_agent", ignoreCase = true) -> "TEST_USER_AGENT"
                paramName.contains("code", ignoreCase = true) || paramName.contains("otp", ignoreCase = true) || paramName.contains("token", ignoreCase = true) || paramName.contains("mfa", ignoreCase = true) -> "\"123456\""
                paramName.contains("url", ignoreCase = true) -> "\"https://example.com\""
                paramName.contains("host", ignoreCase = true) -> "\"api.example.com\""
                paramName.contains("message", ignoreCase = true) -> "\"Test message\""
                paramName.contains("name", ignoreCase = true) -> "\"test_name\""
                else -> "\"test_${paramName}\""
            }
        }
        typeName == "Int" || typeName == "Long" || typeName == "Number" -> {
            when {
                paramName == "pageNumber" || paramName == "page_number" || paramName == "page" -> "1"
                paramName == "pageSize" || paramName == "page_size" -> "20"
                paramName == "authorityLevel" || paramName == "authority_level" -> "10"
                paramName == "retryAfterSeconds" || paramName == "retry_after_seconds" -> "60"
                paramName.endsWith("At") || paramName.endsWith("_at") || paramName.endsWith("Time") || paramName.endsWith("Timestamp") || paramName.endsWith("Until") || paramName.endsWith("_until") -> "TEST_TIME_MILLIS"
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
        rawDtoClasses.any { it.simpleName == typeName } || typeName.endsWith("Payload") || typeName.endsWith("Params") || typeName.endsWith("Request") || typeName.endsWith("Response") -> "createTest${typeName}()"
        type.isMarkedNullable -> "null"
        else -> "{}"
    }
}

fun getAllExportableTestClasses(): List<KClass<*>> {
    val currentDir = File(System.getProperty("user.dir"))
    val rootDir = if (currentDir.name == "npm-generator") currentDir.parentFile.parentFile else currentDir
    val testingSourceDir = File(rootDir, "shared/foundation/testing/src/commonMain/kotlin")

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
                    } catch (_: Exception) {}
                }
            } else if (name.startsWith("Test")) {
                val packageMatch = Regex("package\\s+([\\w\\.]+)").find(content)
                if (packageMatch != null) {
                    val fqn = "${packageMatch.groupValues[1]}.$name"
                    try {
                        val clazz = Class.forName(fqn).kotlin
                        classes.add(clazz)
                    } catch (_: Exception) {}
                }
            }
        }
    }

    return classes.toList().sortedBy { it.simpleName ?: "" }
}

fun generateTestFixturesJs(): String {
    val constantsJs = """
    export const TEST_USER_ID = "00000000-0000-4000-8000-000000000001";
    export const TEST_USER_ID_2 = "00000000-0000-4000-8000-000000000002";
    export const TEST_SESSION_ID = "00000000-0000-4000-8000-000000000010";
    export const TEST_IDENTIFIER_ID = "00000000-0000-4000-8000-000000000020";
    export const TEST_AUDIT_EVENT_ID = "00000000-0000-4000-8000-000000000099";
    export const TEST_FILE_ID = "00000000-0000-4000-8000-000000000040";
    export const TEST_CLIENT_DEVICE_ID = "00000000-0000-4000-8000-000000000030";
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

    export const generateTestUuidString = (index = 0, group = 0) => {
      if (index < 0) throw new Error(`Index must be non-negative: ${'$'}{index}`);
      if (group < 0) throw new Error(`Group must be non-negative: ${'$'}{group}`);
      const groupHex = group.toString(16).padStart(8, '0');
      const indexHex = (index + 1).toString(16).padStart(12, '0');
      return `${'$'}{groupHex}-0000-4000-8000-${'$'}{indexHex}`;
    };

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

    export const createTestPagedResultPayload = (overrides = {}) => {
      const items = overrides?.items ?? [];
      const total_count = overrides?.total_count ?? items.length;
      const page_size = overrides?.page_size ?? 20;
      const total_pages = overrides?.total_pages ?? (total_count === 0 ? 0 : Math.ceil(total_count / page_size));
      const page_number = overrides?.page_number ?? 1;

      return {
        items,
        page_number,
        page_size,
        total_count,
        total_pages,
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
            val mockVal = getDefaultMockValueJs(paramName, paramType, kClass)
            "      $wireKey: $mockVal"
        }

        val listGeneratorCustomLogic = when (className) {
            "UserPrivate", "UserSummary", "UserPrivatePayload", "UserSummaryPayload" -> """
              const createdAtMs = TEST_TIME_MILLIS + (index * 60000);
              return createTest$className({
                id: ${if (isPayload) "generateTestUuidString(index, 1)" else "toUserIdOrThrow(generateTestUuidString(index, 1))"},
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
                id: ${if (isPayload) "generateTestUuidString(index, 1)" else "toUserIdentifierIdOrThrow(generateTestUuidString(index, 1))"},
                ${if (isPayload) "user_id" else "userId"}: ${if (isPayload) "generateTestUuidString(index, 2)" else "toUserIdOrThrow(generateTestUuidString(index, 2))"},
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
                id: ${if (isPayload) "generateTestUuidString(index, 1)" else "toUserSessionIdOrThrow(generateTestUuidString(index, 1))"},
                ${if (isPayload) "user_id" else "userId"}: ${if (isPayload) "generateTestUuidString(index, 2)" else "toUserIdOrThrow(generateTestUuidString(index, 2))"},
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
                id: ${if (isPayload) "generateTestUuidString(index, 1)" else "toAuditEventIdOrThrow(generateTestUuidString(index, 1))"},
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

fun generateTestFixturesDts(): String {
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
     * Creates a test instance of PagedResult (domain model in camelCase) with auto-calculated totalPages and totalCount.
     * @param overrides Partial properties or overrides for PagedResult.
     */
    export declare function createTestPagedResult<T>(overrides?: Partial<PagedResult<T>>): PagedResult<T>;

    /**
     * Creates a test instance of raw PagedResult payload (network DTO in snake_case) with auto-calculated total_pages and total_count.
     * @param overrides Partial properties or overrides for PagedResult payload.
     */
    export declare function createTestPagedResultPayload<T = Record<string, any>>(overrides?: Partial<{
      items: T[];
      total_count: number;
      page_number: number;
      page_size: number;
      total_pages: number;
    }>): {
      items: T[];
      total_count: number;
      page_number: number;
      page_size: number;
      total_pages: number;
    };

    export declare function generateTestUuidString(index?: number, group?: number): string;

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
