# Integration of Lunr in Antora

[![Build Status](https://travis-ci.org/Mogztter/antora-lunr.svg?branch=master)](https://travis-ci.org/Mogztter/antora-lunr)
[![npm](https://img.shields.io/npm/v/antora-lunr.svg)](https://www.npmjs.org/package/antora-lunr)

[Lunr](https://lunrjs.com/) provides a great search experience without the need for external, server-side, search services.
It makes it possible to add an *offline* search engine in your Antora's documentation site.

## Usage

### Generate an index file

To integrate Lunr in Antora, we can either use a custom site generator pipeline that includes Lunr or modify your current site generator pipeline.

**TIP:**
To make things easier, we provide a copy of the default site generator that in addition produces a Lunr index. Learn how to [install and use this generator](https://github.com/Mogztter/antora-site-generator-lunr).

**NOTE:**
The following instructions only apply if you are using the default pipeline.
If you are using a custom pipeline, the logic remains the same but you will have to find yourself where the `generateSite` function should be added.

Antora provides a default pipeline named `@antora/site-generator-default`.
Make sure that it's installed using the command `npm list --depth 0`.
If you don't find the module in the result then it's probably installed globally.
Add the `-g` flag and execute the command again:

```
npm list -g --depth 0
/usr/local/lib
├── @antora/cli@2.0.0
├── @antora/site-generator-default@2.0.0
└── npm@6.5.0
```

As you can see in the example above, the module is installed globally in _/usr/local/lib_.
The `node_modules` folder will be either at the root of your project or in your global libraries folder: _/usr/local/lib/node_modules_.

Once you've located the module, edit the file `node_modules/@antora/site-generator-default/lib/generate-site.js` adding after `use strict`:

```js
const generateIndex = require('antora-lunr')
```

In the `generateSite` function add the following two lines after `const siteFiles = mapSite(playbook, pages).concat(produceRedirects(playbook, contentCatalog))`:

```js
const index = generateIndex(playbook, pages, contentCatalog, env)
siteFiles.push(generateIndex.createIndexFile(index))
```

Install this module:

```console
$ npm i antora-lunr
```

**NOTE**:
If Antora is installed globally, you should also add this module globally using the `-g` flag:

```console
$ npm i -g antora-lunr
```

When generating your documentation site again, an index file will be created at the root of your output directory,
which depends on the value of `output.dir` in your playbook.
For the [default output dir](https://docs.antora.org/antora/2.0/playbook/configure-output/#default-output-dir),
that will be `build/site/search-index.js`.

### Enable the search component in the UI

Now that we have a `search-index.js`, we need to enable the search component in the UI.

Copy the `supplemental_ui` directory from the npm package *node_modules/antora-lunr/supplemental_ui* in your Antora playbook repository and configure a `supplemental_files`:

```yml
ui:
  bundle:
    url: https://gitlab.com/antora/antora-ui-default/-/jobs/artifacts/master/raw/build/ui-bundle.zip?job=bundle-stable
    snapshot: true
  supplemental_files: ./supplemental_ui
```

**NOTE:** For this to function correctly you must provide the `site.url` key in your playbook file.
See the Antora docs on the [playbook schema](https://docs.antora.org/antora/1.1/playbook/playbook-schema/).
If using the site locally (not serving from a web server) then you can set your `site.url` to a `file://` reference, e.g. `file:///home/documents/antora/website/public/`.

**TIP:** If you are using [serve](https://www.npmjs.com/package/serve) HTTP server to view your site locally,
set the `site.url` to `http://localhost:5000`.

### Generate the site

Generate your documentation site with the following environment variables:

* `DOCSEARCH_ENABLED=true`
* `DOCSEARCH_ENGINE=lunr`

For instance, as a command line:

```console
$ DOCSEARCH_ENABLED=true DOCSEARCH_ENGINE=lunr antora site.yml
```

### Configuration

To index only the latest (released) version, set the following environment variable:

* `DOCSEARCH_INDEX_VERSION=latest`

For instance, as a command line:

```console
$ DOCSEARCH_ENABLED=true DOCSEARCH_ENGINE=lunr DOCSEARCH_INDEX_VERSION=latest antora site.yml
```

### Testing this module

In the root of the repository, run `npm t`.
