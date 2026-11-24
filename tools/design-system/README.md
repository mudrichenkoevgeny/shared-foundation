# tools/design-system

**Pure Kotlin Design Tokens Parser, Icon Extractors, and Generators** for multiplatform design token and icon code generation across Jetpack Compose, Web (CSS/TypeScript), and Penpot.

This module contains zero Compose UI or Node.js runtime dependencies. It parses design token JSON specifications, resolves color/primitive aliases recursively, extracts multi-target icons, and generates type-safe outputs.

## Embedded Assets

- **Design Token JSON Specs:** `src/main/resources/design-system/tokens/` (`colors.json`, `elevation.json`, `radius.json`, `sizing.json`, `spacing.json`, `typography.json`).
- **TTF Font Resources:** `src/main/resources/design-system/fonts/ttf/` (`pt_sans_bold.ttf`, `pt_sans_bold_italic.ttf`, `pt_sans_italic.ttf`, `pt_sans_regular.ttf`).
- **WOFF2 Font Resources:** `src/main/resources/design-system/fonts/woff2/` (`pt-sans-bold-italic.woff2`, `pt-sans-bold.woff2`, `pt-sans-italic.woff2`, `pt-sans-regular.woff2`).
- **Dual-Format Icon Assets:** `src/main/resources/design-system/icons/<module>/{drawable,svg}/` (`core-common`, `core-storage`, `feature-user`).

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

6. **`DesignSystemIconExtractor`** ([DesignSystemIconExtractor]):
   - Utility for extracting dual-format icon assets (Vector Drawable XML for KMP and SVG for Web).

## Local Gradle Task

Run the local Penpot generation task:

```bash
./gradlew :tools:design-system:generatePenpotTokens
```

Outputs `tokens.json` and `tokens.ts` into `build/penpot/`.

[DesignTokensParser]: src/main/kotlin/io/github/mudrichenkoevgeny/shared/foundation/tools/designsystem/DesignTokensParser.kt
[ComposeTokensGenerator]: src/main/kotlin/io/github/mudrichenkoevgeny/shared/foundation/tools/designsystem/ComposeTokensGenerator.kt
[WebTokensGenerator]: src/main/kotlin/io/github/mudrichenkoevgeny/shared/foundation/tools/designsystem/WebTokensGenerator.kt
[PenpotTokensGenerator]: src/main/kotlin/io/github/mudrichenkoevgeny/shared/foundation/tools/designsystem/PenpotTokensGenerator.kt
[DesignTokensFontExtractor]: src/main/kotlin/io/github/mudrichenkoevgeny/shared/foundation/tools/designsystem/DesignTokensFontExtractor.kt
[DesignSystemIconExtractor]: src/main/kotlin/io/github/mudrichenkoevgeny/shared/foundation/tools/designsystem/DesignSystemIconExtractor.kt
