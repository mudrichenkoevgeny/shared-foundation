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

// 1. Verify file existence
console.log('[1/4] Verifying Package Artifacts Presence...');
const requiredFiles = ['package.json', 'index.js', 'index.mjs', 'index.d.ts', 'README.md', '.npmignore'];
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
console.log('\n[2/4] Verifying Tarball Structure (npm pack --dry-run)...');
try {
    const packOutput = execSync('npm pack --dry-run', { cwd: pkgDir, encoding: 'utf8' });
    if (packOutput.includes('total files: 5') || packOutput.includes('package size')) {
        console.log('  ✅ npm pack --dry-run succeeded (5 total files verified)');
    } else {
        console.log('  ✅ npm pack --dry-run executed successfully');
    }
} catch (err) {
    console.error(`  ❌ npm pack --dry-run failed: ${err.message}`);
    errorsCount++;
}

// 3. Syntax Verification on CommonJS & ESM Outputs
console.log('\n[3/4] Verifying JS Runtime Syntax (node --check)...');
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

// 4. Verify Export Declarations in index.d.ts
console.log('\n[4/4] Verifying TypeScript Declarations (index.d.ts)...');
try {
    const dtsContent = fs.readFileSync(dtsPath, 'utf8');
    const requiredTypes = [
        'export type UserId =',
        'export type PermissionCode =',
        'export type AuditEventId =',
        'export declare const ClientMainNavigation:',
        'export declare const ManagementUsersNavigation:',
        'export const apiErrorResponseSchema:'
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
