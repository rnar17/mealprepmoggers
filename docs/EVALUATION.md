Evaluating team tarannon:

Starting with the documentation from the team, it was difficult to figure out how to run the project. After installing creat-react-app and following the group's instructions to run the project, we are faced with the following error:

```
ryl051@LAPTOP-UL5TBM33:~/project-tarannon-traderbird/my-app$ npm start
node:internal/modules/cjs/loader:1239
const err = new Error(message);
^

Error: Cannot find module 'semver'
Require stack:
- /usr/share/nodejs/npm/lib/utils/unsupported.js
- /usr/share/nodejs/npm/lib/cli.js
- /usr/share/nodejs/npm/bin/npm-cli.js
  at Function._resolveFilename (node:internal/modules/cjs/loader:1239:15)
  at Function._load (node:internal/modules/cjs/loader:1064:27)
  at TracingChannel.traceSync (node:diagnostics_channel:322:14)
  at wrapModuleLoad (node:internal/modules/cjs/loader:218:24)
  at Module.require (node:internal/modules/cjs/loader:1325:12)
  at require (node:internal/modules/helpers:136:16)
  at Object.<anonymous> (/usr/share/nodejs/npm/lib/utils/unsupported.js:2:16)
  at Module._compile (node:internal/modules/cjs/loader:1546:14)
  at Object..js (node:internal/modules/cjs/loader:1698:10)
  at Module.load (node:internal/modules/cjs/loader:1303:32) {
  code: 'MODULE_NOT_FOUND',
  requireStack: [
  '/usr/share/nodejs/npm/lib/utils/unsupported.js',
  '/usr/share/nodejs/npm/lib/cli.js',
  '/usr/share/nodejs/npm/bin/npm-cli.js'
  ]
  }

Node.js v23.3.0
```