package io.github.mudrichenkoevgeny.shared.foundation.core.common.collection

internal expect fun <K : Any, V : Any> createConcurrentMap(): MutableMap<K, V>
