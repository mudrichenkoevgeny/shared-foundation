package io.github.mudrichenkoevgeny.shared.foundation.plugin.agent

import org.gradle.api.provider.Property

/**
 * Configuration options for agent rules synchronization in consumer projects.
 */
public abstract class AgentSyncConfig {
    /**
     * Whether to include shared contract/foundation rules (`.agent/global/shared/`).
     * Defaults to `true` for shared foundation/contract projects, `false` for application SDKs.
     */
    public abstract val includeShared: Property<Boolean>
}
