const fs = require('fs');
const path = require('path');

const rootDir = path.resolve(__dirname, '../../..');

const sourceDirs = [
    path.join(rootDir, 'shared/foundation/core/common/src/commonMain/kotlin'),
    path.join(rootDir, 'shared/foundation/core/audit/src/commonMain/kotlin'),
    path.join(rootDir, 'shared/foundation/core/security/src/commonMain/kotlin'),
    path.join(rootDir, 'shared/foundation/core/settings/src/commonMain/kotlin'),
    path.join(rootDir, 'shared/foundation/core/storage/src/commonMain/kotlin'),
    path.join(rootDir, 'shared/foundation/feature/user/src/commonMain/kotlin'),
];

const testingDir = path.join(rootDir, 'shared/foundation/testing/src/commonMain/kotlin');

function getAllKtFiles(dir) {
    let results = [];
    if (!fs.existsSync(dir)) return results;
    const list = fs.readdirSync(dir);
    list.forEach(file => {
        const fullPath = path.join(dir, file);
        const stat = fs.statSync(fullPath);
        if (stat && stat.isDirectory()) {
            results = results.concat(getAllKtFiles(fullPath));
        } else if (file.endsWith('.kt')) {
            results.push(fullPath);
        }
    });
    return results;
}

const prodKtFiles = sourceDirs.flatMap(getAllKtFiles);
const testKtFiles = getAllKtFiles(testingDir);

const jsPath = path.join(rootDir, 'build/npm-package/index.js');
const dtsPath = path.join(rootDir, 'build/npm-package/index.d.ts');

if (!fs.existsSync(jsPath) || !fs.existsSync(dtsPath)) {
    console.error('NPM package build not found! Run ./gradlew generateNpmPackage first.');
    process.exit(1);
}

const jsContent = fs.readFileSync(jsPath, 'utf8');
const dtsContent = fs.readFileSync(dtsPath, 'utf8');

let errorsCount = 0;

const ignoredObjects = new Set([
    'Companion', 'QueryParamNormalizer', 'PhoneParamNormalizer', 'LocalizationFormatter',
    'LocalizationRegistry', 'PasswordPolicyValidator', 'PasswordPolicyValidatorResult',
    'PasswordPolicyRule', 'Success'
]);

console.log('==================================================');
console.log('       NPM PACKAGE EXPORTS PARITY AUDIT          ');
console.log('==================================================\n');

// 1. Audit Objects (Routes, Contracts, ErrorCodes, FilterValues, SortValues, etc.)
console.log('[1/4] Auditing Top-Level Objects & Contracts...');
const topLevelObjects = [];

prodKtFiles.forEach(file => {
    const rawContent = fs.readFileSync(file, 'utf8');
    // Strip KDoc / comments
    const content = rawContent.replace(/\/\*[\s\S]*?\*\/|\/\/.*/g, '');
    const objectRegex = /^\s*(?:@\w+(?:\([^)]*\))?\s+)*(?:public\s+|internal\s+)?object\s+([A-Za-z0-9_]+)/gm;
    let match;
    while ((match = objectRegex.exec(content)) !== null) {
        const name = match[1];
        if (!ignoredObjects.has(name) && !file.includes('/internal/')) {
            topLevelObjects.push({ name, file });
        }
    }
});

topLevelObjects.forEach(obj => {
    const isBase = obj.name.startsWith('Base');
    const inJs = new RegExp(`(export\\s+const\\s+${obj.name}\\s*=)|(\\b${obj.name}\\s*:)`, 'm').test(jsContent);
    const inDts = new RegExp(`(export\\s+(?:declare\s+)?(?:const|namespace|type)\\s+${obj.name}\\b)|(\\b${obj.name}\\s*:)`, 'm').test(dtsContent);

    if (!inJs && !isBase) {
        console.error(`  ❌ Object missing in JS: ${obj.name} (${path.relative(rootDir, obj.file)})`);
        errorsCount++;
    }
    if (!inDts && !isBase) {
        console.error(`  ❌ Object missing in DTS: ${obj.name} (${path.relative(rootDir, obj.file)})`);
        errorsCount++;
    }
});

// 2. Audit Enums
console.log('\n[2/4] Auditing Domain Enums...');
const enums = [];

prodKtFiles.forEach(file => {
    const rawContent = fs.readFileSync(file, 'utf8');
    const content = rawContent.replace(/\/\*[\s\S]*?\*\/|\/\/.*/g, '');
    const enumRegex = /^\s*(?:@\w+(?:\([^)]*\))?\s+)*(?:public\s+|internal\s+)?enum\s+class\s+([A-Za-z0-9_]+)/gm;
    let match;
    while ((match = enumRegex.exec(content)) !== null) {
        enums.push({ name: match[1], file });
    }
});

enums.forEach(e => {
    const inJs = new RegExp(`export\\s+const\\s+${e.name}\\s*=`, 'm').test(jsContent);
    const inDts = new RegExp(`(export\\s+(?:declare\s+)?(?:const|type)\\s+${e.name}\\b)|(\\b${e.name}\\s*:)`, 'm').test(dtsContent);
    if (!inJs) {
        console.error(`  ❌ Enum missing in JS: ${e.name} (${path.relative(rootDir, e.file)})`);
        errorsCount++;
    }
    if (!inDts) {
        console.error(`  ❌ Enum missing in DTS: ${e.name} (${path.relative(rootDir, e.file)})`);
        errorsCount++;
    }
});

// 3. Audit Testing Fixtures (shared/foundation/testing)
console.log('\n[3/4] Auditing Testing Fixtures (shared/foundation/testing)...');
const fixtures = [];

testKtFiles.forEach(file => {
    const filename = path.basename(file, '.kt');
    if (filename.endsWith('Fixture')) {
        const targetClassName = filename.replace(/Fixture$/, '');
        fixtures.push({ name: targetClassName, fixtureFile: file });
    }
});

fixtures.forEach(fix => {
    const factoryName = `createTest${fix.name}`;
    const inJs = new RegExp(`export\\s+const\\s+${factoryName}\\s*=`, 'm').test(jsContent);
    const inDts = new RegExp(`export\\s+declare\\s+function\\s+${factoryName}\\b`, 'm').test(dtsContent);

    if (!inJs) {
        console.error(`  ❌ Test factory missing in JS: ${factoryName} (${path.relative(rootDir, fix.fixtureFile)})`);
        errorsCount++;
    }
    if (!inDts) {
        console.error(`  ❌ Test factory missing in DTS: ${factoryName} (${path.relative(rootDir, fix.fixtureFile)})`);
        errorsCount++;
    }
});

// 4. Audit Top-Level Duplicate Exports
console.log('\n[4/5] Auditing Duplicate Exports...');
const lines = jsContent.split('\n');
const jsExportNames = {};
lines.forEach(line => {
    const match = /^export\s+(?:const|function|class)\s+([A-Za-z0-9_$]+)/.exec(line);
    if (match) {
        const name = match[1];
        jsExportNames[name] = (jsExportNames[name] || 0) + 1;
    }
});

Object.entries(jsExportNames).forEach(([name, count]) => {
    if (count > 1) {
        console.error(`  ❌ Duplicate export in index.js: ${name} (${count} times)`);
        errorsCount++;
    }
});

// 5. Audit Braces & Syntax Integrity
console.log('\n[5/5] Auditing Braces & Syntax Integrity...');

try {
    const { execSync } = require('child_process');
    execSync(`node --check "${jsPath}"`);
    console.log('  ✅ JS syntax check passed (node --check)');
} catch (err) {
    console.error(`  ❌ Syntax Error in index.js: ${err.message}`);
    errorsCount++;
}

function checkBraceSyntax(filePath, fileContent) {
    let depth = 0;
    const filename = path.basename(filePath);
    // Strip comments, strings, and regex literals
    const cleanContent = fileContent
        .replace(/\/\*[\s\S]*?\*\/|\/\/.*/g, '')
        .replace(/"(?:[^"\\]|\\.)*"|'(?:[^'\\]|\\.)*'|`(?:[^`\\]|\\.)*`/g, '')
        .replace(/\/(?:\\\/|[^\/\n])+\/[gimsuy]*/g, '');
    for (let i = 0; i < cleanContent.length; i++) {
        if (cleanContent[i] === '{') depth++;
        else if (cleanContent[i] === '}') depth--;

        if (depth < 0) {
            console.error(`  ❌ Syntax Error in ${filename}: Unexpected closing brace '}' at char ${i}`);
            errorsCount++;
            break;
        }
    }
    if (depth !== 0) {
        console.error(`  ❌ Syntax Error in ${filename}: Mismatched braces (depth delta: ${depth})`);
        errorsCount++;
    }
}

checkBraceSyntax(dtsPath, dtsContent);

console.log('\n==================================================');
if (errorsCount === 0) {
    console.log('  ✅ AUDIT PASSED: 100% Export Parity Verified!');
    console.log('==================================================\n');
    process.exit(0);
} else {
    console.error(`  ❌ AUDIT FAILED: ${errorsCount} issue(s) detected.`);
    console.log('==================================================\n');
    process.exit(1);
}
