const { execSync } = require('child_process');
const path = require('path');
const fs = require('fs');

const rootDir = path.resolve(__dirname, '../../..');
const pkgDir = path.join(rootDir, 'build/npm-package');
const jsPath = path.join(pkgDir, 'index.js');
const mjsPath = path.join(pkgDir, 'index.mjs');
const dtsPath = path.join(pkgDir, 'index.d.ts');

console.log('==================================================');
console.log('  NPM CONSUMER SMOKE TEST & PACKAGE VERIFICATION  ');
console.log('==================================================\n');

let errorsCount = 0;

if (!fs.existsSync(pkgDir)) {
    console.error(`❌ NPM package directory not found at: ${pkgDir}`);
    console.error('   Please run ./gradlew generateNpmPackage first before executing consumer smoke tests.\n');
    process.exit(1);
}

// 1. Verify file existence
console.log('[1/5] Verifying Package Artifacts Presence...');
const requiredFiles = [
    'package.json',
    'index.js',
    'index.mjs',
    'index.d.ts',
    'README.md',
    '.npmignore',
    'scripts/postinstall-sync-rules.mjs',
    'scripts/agent-rules-syncer.mjs'
];
requiredFiles.forEach(file => {
    const filePath = path.join(pkgDir, file);
    if (!fs.existsSync(filePath)) {
        console.error(`  ❌ Missing artifact: ${file}`);
        errorsCount++;
    } else {
        console.log(`  ✅ Verified artifact: ${file}`);
    }
});

// 2. Run npm pack --dry-run
console.log('\n[2/5] Verifying Tarball Structure (npm pack --dry-run)...');
try {
    const packOutput = execSync('npm pack --dry-run 2>&1', { cwd: pkgDir, encoding: 'utf8' });
    if (packOutput.includes('package size') || packOutput.includes('Tarball Contents') || packOutput.includes('filename:') || packOutput.includes('.tgz')) {
        console.log('  ✅ npm pack --dry-run executed successfully');
    } else {
        console.error('  ❌ npm pack --dry-run output missing expected tarball details');
        errorsCount++;
    }
} catch (err) {
    console.error(`  ❌ npm pack --dry-run failed: ${err.message}`);
    errorsCount++;
}

// 3. Syntax Verification on CommonJS & ESM Outputs & Scripts
console.log('\n[3/5] Verifying JS Runtime Syntax (node --check)...');
try {
    execSync(`node --check "${jsPath}"`);
    console.log('  ✅ index.js syntax verified (node --check)');
} catch (err) {
    console.error(`  ❌ index.js syntax check failed: ${err.message}`);
    errorsCount++;
}

try {
    execSync(`node --check "${mjsPath}"`);
    console.log('  ✅ index.mjs syntax verified (node --check)');
} catch (err) {
    console.error(`  ❌ index.mjs syntax check failed: ${err.message}`);
    errorsCount++;
}

const postinstallScriptPath = path.join(pkgDir, 'scripts/postinstall-sync-rules.mjs');
try {
    execSync(`node --check "${postinstallScriptPath}"`);
    console.log('  ✅ scripts/postinstall-sync-rules.mjs syntax verified (node --check)');
} catch (err) {
    console.error(`  ❌ scripts/postinstall-sync-rules.mjs syntax check failed: ${err.message}`);
    errorsCount++;
}

const syncerScriptPath = path.join(pkgDir, 'scripts/agent-rules-syncer.mjs');
try {
    execSync(`node --check "${syncerScriptPath}"`);
    console.log('  ✅ scripts/agent-rules-syncer.mjs syntax verified (node --check)');
} catch (err) {
    console.error(`  ❌ scripts/agent-rules-syncer.mjs syntax check failed: ${err.message}`);
    errorsCount++;
}

// 4. Verify Export Declarations in index.d.ts
console.log('\n[4/5] Verifying TypeScript Declarations (index.d.ts)...');
try {
    const dtsContent = fs.readFileSync(dtsPath, 'utf8');
    const requiredTypes = [
        'export type UserId =',
        'export type PermissionCode =',
        'export type AuditEventId =',
        'export declare const ClientMainNavigation:',
        'export declare const ManagementUsersNavigation:',
        'export const apiErrorResponseSchema:',
        'export function pagedResultSchema',
        'export declare const UiValues:',
        'createTestUserSummary'
    ];
    requiredTypes.forEach(typeName => {
        if (!dtsContent.includes(typeName)) {
            console.error(`  ❌ Missing declaration in index.d.ts: ${typeName}`);
            errorsCount++;
        }
    });
    console.log('  ✅ All core TypeScript declarations verified in index.d.ts');
} catch (err) {
    console.error(`  ❌ TypeScript declaration audit failed: ${err.message}`);
    errorsCount++;
}

// 5. Verify package.json script configuration and subpath exports
console.log('\n[5/5] Verifying Package Scripts Config & Subpath Exports...');
try {
    const pkgJson = JSON.parse(fs.readFileSync(path.join(pkgDir, 'package.json'), 'utf8'));
    if (!pkgJson.main || pkgJson.main !== 'index.js' || !pkgJson.module || pkgJson.module !== 'index.mjs' || !pkgJson.types || pkgJson.types !== 'index.d.ts') {
        console.error('  ❌ package.json missing or invalid main/module/types bundle fields');
        errorsCount++;
    } else {
        console.log('  ✅ package.json main/module/types bundle fields verified');
    }

    if (!pkgJson.scripts || pkgJson.scripts.postinstall !== 'node ./scripts/postinstall-sync-rules.mjs') {
        console.error('  ❌ package.json missing or invalid postinstall script definition');
        errorsCount++;
    } else {
        console.log('  ✅ package.json postinstall script configuration verified');
    }

    if (!pkgJson.exports || !pkgJson.exports['.'] || !pkgJson.exports['./scripts/*']) {
        console.error('  ❌ package.json missing root "." or "./scripts/*" export mapping');
        errorsCount++;
    } else {
        console.log('  ✅ package.json subpath exports verified');
    }
} catch (err) {
    console.error(`  ❌ Package scripts config audit failed: ${err.message}`);
    errorsCount++;
}

console.log('\n==================================================');
if (errorsCount === 0) {
    console.log('  ✅ CONSUMER SMOKE TEST PASSED: Package is 100% Ready!');
    console.log('==================================================\n');
    process.exit(0);
} else {
    console.error(`  ❌ CONSUMER SMOKE TEST FAILED: ${errorsCount} issue(s) detected.`);
    console.log('==================================================\n');
    process.exit(1);
}
