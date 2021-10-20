package _Self.Funcs

fun nameToId(name: String): String {
  return name.split("-").map { it.capitalize() }.joinToString(separator = "")
}

fun generate3rdPartyLibsJs(artifactDir: String): String {
  return ("""
      #!/bin/bash
      set -e -E

      npm install nlf

      node -e "
        const nlf = require('nlf');
        const fs = require('fs');

        const cwd = process.cwd();

        console.log('UPDATING 3RD PARTY LIBS...');

        nlf.find({directory: cwd}, function(error, data) {
          const dependencies = {};

          if (error) {
            console.error('Updating dependencies failed!', error);
            process.exit(1);
          } else {
            data.forEach(function(depData) {
              if (depData.name.match(/^@avast\//)) return; // not a 3rd party lib

              const licenses = [];
              let licensesStr = null;

              depData.licenseSources.package.sources.map(function(source) {
                if (licenses.indexOf(source.license) === -1) {
                  licenses.push(source.license);
                }
              });

              if (licenses.length) {
                licensesStr = licenses.join(', ');
              }

              if (depData.repository === '(none)') {
                depData.repository = null;
              }

              let licenseText = null;

              if (depData.licenseSources.license.sources.length) {
                licenseText = depData.licenseSources.license.sources[0].text;
              }

              dependencies[depData.name] = {
                licenses: licensesStr,
                version: depData.version,
                licenseText: licenseText,
                repository: depData.repository
              };
            });

            console.log('Total unique dependencies count:', Object.keys(dependencies).length);
            console.log('Saving the list...');

            fs.mkdirSync(cwd + '/""" + artifactDir + """', {recursive: true});
            fs.writeFileSync(cwd + '/""" + artifactDir + """/.3rdPartyLibs.json', JSON.stringify(dependencies));

            console.log('UPDATING 3RD PARTY LIBS DONE!');
          }
        });
      "
  """).trimIndent()
}
