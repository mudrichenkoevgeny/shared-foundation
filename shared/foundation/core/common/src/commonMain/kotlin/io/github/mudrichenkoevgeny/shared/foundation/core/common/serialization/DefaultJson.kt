package io.github.mudrichenkoevgeny.shared.foundation.core.common.serialization

import kotlinx.serialization.json.Json

val DefaultJson = Json {
    ignoreUnknownKeys = true
    isLenient = true
    encodeDefaults = true
    coerceInputValues = true
}