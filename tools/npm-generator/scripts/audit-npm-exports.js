const fs = require('fs');
const path = require('path');

const pkgDir = process.env.NPM_PACKAGE_PATH ? path.dirname(process.env.NPM_PACKAGE_PATH) : path.resolve(process.cwd(), 'build/npm-package');
const jsPath = path.join(pkgDir, 'index.js');
const dtsPath = path.join(pkgDir, 'index.d.ts');

if (!fs.existsSync(jsPath) || !fs.existsSync(dtsPath)) {
  console.error('NPM package build not found!');
  process.exit(1);
}

const jsContent = fs.readFileSync(jsPath, 'utf8');
const dtsContent = fs.readFileSync(dtsPath, 'utf8');

let errorsCount = 0;

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
    console.error(`Duplicate export in index.js: ${name} (${count} times)`);
    errorsCount++;
  }
});

try {
  const { execSync } = require('child_process');
  execSync(`node --check "${jsPath}"`);
} catch (err) {
  console.error(`Syntax Error in index.js: ${err.message}`);
  errorsCount++;
}

function checkBraceSyntax(filePath, fileContent) {
  let depth = 0;
  const filename = path.basename(filePath);
  const cleanContent = fileContent
    .replace(/\/\*[\s\S]*?\*\/|\/\/.*/g, '')
    .replace(/"(?:[^"\\]|\\.)*"|'(?:[^'\\]|\\.)*'|`(?:[^`\\]|\\.)*`/g, '')
    .replace(/\/(?:\\\/|[^\/\n])+\/[gimsuy]*/g, '');
  for (let i = 0; i < cleanContent.length; i++) {
    if (cleanContent[i] === '{') depth++;
    else if (cleanContent[i] === '}') depth--;

    if (depth < 0) {
      console.error(`Syntax Error in ${filename}: Unexpected closing brace '}' at char ${i}`);
      errorsCount++;
      break;
    }
  }
  if (depth !== 0) {
    console.error(`Syntax Error in ${filename}: Mismatched braces (depth delta: ${depth})`);
    errorsCount++;
  }
}

checkBraceSyntax(dtsPath, dtsContent);

if (errorsCount > 0) {
  process.exit(1);
}
