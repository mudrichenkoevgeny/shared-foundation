# tools/design-tokens

**Pure Kotlin Design Tokens Parser and Generators** for multiplatform design token code generation across Jetpack Compose, Web (CSS/TypeScript), and Penpot.

This module contains zero Compose UI or Node.js runtime dependencies. It parses design token JSON specifications, resolves color/primitive aliases recursively, and generates type-safe multi-target outputs.

## Embedded Assets

- **Design Token JSON Specs:** `src/main/resources/design-tokens/tokens/` (`colors.json`, `elevation.json`, `radius.json`, `sizing.json`, `spacing.json`, `typography.json`).
- **TTF Font Resources:** `src/main/resources/design-tokens/fonts/ttf/` (`pt_sans_bold.ttf`, `pt_sans_bold_italic.ttf`, `pt_sans_italic.ttf`, `pt_sans_regular.ttf`).
- **WOFF2 Font Resources:** `src/main/resources/design-tokens/fonts/woff2/` (`pt-sans-bold-italic.woff2`, `pt-sans-bold.woff2`, `pt-sans-italic.woff2`, `pt-sans-regular.woff2`).

## Core Components

1. **`DesignTokensParser`** ([DesignTokensParser]):
   - Loads and deep-merges token JSON specifications from classpath resources or filesystem directories.
   - Recursively resolves `{color.primitive.*}` token alias references.

2. **`ComposeTokensGenerator`** ([ComposeTokensGenerator]):
   - Generates `PlatformDesignTokens.kt` with configurable `packageName` (default: `io.github.mudrichenkoevgeny.kmp.core.common.ui.theme.tokens`).

3. **`WebTokensGenerator`** ([WebTokensGenerator]):
   - Generates `tokens.css`, `tokens.js`, `tokens.d.ts`, and `tokens.ts` (`PlatformDesignTokens`) with CSS custom properties, `@font-face` blocks, JavaScript ES module exports, and strongly typed TypeScript design token object definitions.

4. **`PenpotTokensGenerator`** ([PenpotTokensGenerator]):
   - Generates Penpot-compatible `tokens.json` and `tokens.ts` definitions.

5. **`DesignTokensFontExtractor`** ([DesignTokensFontExtractor]):
   - Utility for extracting embedded TTF and WOFF2 font files to specified target directories.

## Local Gradle Task

Run the local Penpot generation task:

```bash
./gradlew :tools:design-tokens:generatePenpotTokens
```

Outputs `tokens.json` and `tokens.ts` into `build/penpot/`.

[DesignTokensParser]: src/main/kotlin/io/github/mudrichenkoevgeny/shared/foundation/tools/designtokens/DesignTokensParser.kt
[ComposeTokensGenerator]: src/main/kotlin/io/github/mudrichenkoevgeny/shared/foundation/tools/designtokens/ComposeTokensGenerator.kt
[WebTokensGenerator]: src/main/kotlin/io/github/mudrichenkoevgeny/shared/foundation/tools/designtokens/WebTokensGenerator.kt
[PenpotTokensGenerator]: src/main/kotlin/io/github/mudrichenkoevgeny/shared/foundation/tools/designtokens/PenpotTokensGenerator.kt
[DesignTokensFontExtractor]: src/main/kotlin/io/github/mudrichenkoevgeny/shared/foundation/tools/designtokens/DesignTokensFontExtractor.kt
