# tools/detekt-rules

**Custom Detekt static analysis rules plugin** for enforcing shared library architectural standards and coding conventions across `shared-foundation` and consumer applications.

It provides a custom Detekt rule set (`custom-rules`) registered via [CustomRuleSetProvider].

## Included Detekt Rules

1. **`NoFullyQualifiedName`** ([NoFullyQualifiedNameRule]):
   - **Description:** Prohibits Fully Qualified Names (FQN) inside code bodies, inline comments, and KDoc documentation blocks.
   - **Rationale:** Symbols must be explicitly imported using `import` statements to keep code clean and maintain consistent indexing.

2. **`NoInlineComment`** ([NoInlineCommentRule]):
   - **Description:** Prohibits banal or redundant inline implementation comments (`//` or `/* ... */`) inside method bodies.
   - **Rationale:** Code must be self-documenting through clean decomposition into private functions. KDoc documentation (`/** ... */`) and technical markers (`// TODO`, `// FIXME`, `// workaround`) remain permitted.

3. **`PreferValueOverName`** ([PreferValueOverNameRule]):
   - **Description:** Enforces the use of the `.value` property instead of `.name` or `.serialName` when referencing domain enums or wire types.
   - **Rationale:** Wire string representations for domain enums and extensible interfaces in `shared-foundation` consistently use `.value`.

4. **`ProhibitNonSafeAssertion`** ([ProhibitNonSafeAssertionRule]):
   - **Description:** Strictly prohibits the non-null assertion operator (`!!`) in production code.
   - **Rationale:** Nullability must be handled safely using safe calls (`?.`), Elvis operators (`?:`), safe casts (`as?`), or explicit `requireNotNull(...)`/`checkNotNull(...)` invariant checks. (Allowed only in test files).

5. **`ProhibitPrintln`** ([ProhibitPrintlnRule]):
   - **Description:** Prohibits unstructured `println`, `print`, `System.out.print`, or `System.err.print` calls in production code.
   - **Rationale:** Production code must use structured loggers or KMP logging primitives instead of raw console printing.

## Detekt Base Config & Quality Plugin Integration

The base Detekt configuration is maintained in `src/main/resources/config/detekt/detekt-base.yml`.

When using the `shared-foundation-quality` convention plugin:
1. **Base Rule Set:** `detekt-base.yml` is automatically extracted from plugin resources into `build/generated/detekt/detekt-base.yml`.
2. **Local Overrides:** If `$rootDir/config/detekt/detekt.yml` exists in the repository, `QualityPlugin` merges it on top of `detekt-base.yml`, enabling project-specific rule overrides or exclusions (e.g. for code generators).

## Gradle Configuration & Direct Plugin Usage

Add the custom rules artifact to your project's Detekt dependencies:

```kotlin
// build.gradle.kts
dependencies {
    detektPlugins("io.github.mudrichenkoevgeny:shared-foundation-detekt-rules:<version>")
    
    // Or for local repository builds:
    // detektPlugins(project(":tools:detekt-rules"))
}
```

Enable the rule set in `detekt-base.yml` or local `config/detekt/detekt.yml`:

```yaml
custom-rules:
  active: true
  NoFullyQualifiedName:
    active: true
  NoInlineComment:
    active: true
  PreferValueOverName:
    active: true
  ProhibitNonSafeAssertion:
    active: true
  ProhibitPrintln:
    active: true
```

[CustomRuleSetProvider]: src/main/kotlin/io/github/mudrichenkoevgeny/shared/foundation/detekt/CustomRuleSetProvider.kt
[NoFullyQualifiedNameRule]: src/main/kotlin/io/github/mudrichenkoevgeny/shared/foundation/detekt/NoFullyQualifiedNameRule.kt
[NoInlineCommentRule]: src/main/kotlin/io/github/mudrichenkoevgeny/shared/foundation/detekt/NoInlineCommentRule.kt
[PreferValueOverNameRule]: src/main/kotlin/io/github/mudrichenkoevgeny/shared/foundation/detekt/PreferValueOverNameRule.kt
[ProhibitNonSafeAssertionRule]: src/main/kotlin/io/github/mudrichenkoevgeny/shared/foundation/detekt/ProhibitNonSafeAssertionRule.kt
[ProhibitPrintlnRule]: src/main/kotlin/io/github/mudrichenkoevgeny/shared/foundation/detekt/ProhibitPrintlnRule.kt
