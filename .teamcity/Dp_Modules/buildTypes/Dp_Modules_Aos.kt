package Dp_Modules.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

object Dp_Modules_Aos : BuildType({
    name = "aos"

    artifactRules = """
        build/*aos-avg*.crx => AVG
        build/*aos-avg*.appx => AVG
        build/*aos-avg*.zip => AVG
        build/*aos-1*.crx
        build/*aos-2*.crx
        build/*aos-1*.zip
        build/*aos-beta-1*.zip
        build/*aos-2*.zip
        build/*aos-beta-2*.zip
        build/*aos-1*.appx
        build/*aos-2*.appx
        build/DEBUG/*.* => DEBUG/
        build/unpacked_DEBUG/safari-aos/**/* => DEBUG/safari
        build/unpacked_DEBUG/safari-aos-avg/**/* => DEBUG/safari-avg
        build/unpacked_RELEASE/safari-aos/**/* => safari
        build/unpacked_RELEASE/safari-aos-avg/**/* => safari-avg
        bin/teamcity-build-carthage-artifacts.py => bin
        package.json
        build/aos-mozilla-src-*.zip => SourceCodes/
        build/aos-opera-src-*.zip => SourceCodes/
        build/aos-mozilla-src.zip => SourceCodes/
        build/aos-opera-src.zip => SourceCodes/
    """.trimIndent()

    vcs {
        root(Dp_Modules.vcsRoots.Dp_Modules_AosRepo)
    }

    steps {
        step {
            type = "jonnyzzz.vm"
            param("vm", "docker")
            param("docker-mount-mode", "rw")
            param("script", """
                #!/bin/bash

                set -e -E

                echo "unsafe-perm = true
                email = re@avast.com
                @avast:registry=https://artifactory.ida.avast.com/artifactory/api/npm/npm-local/
                @hidemyass:registry=https://artifactory.ida.avast.com/artifactory/api/npm/npm-local/
                @babel:registry=https://artifactory.ida.avast.com/artifactory/api/npm/registry.npmjs.org/
                @types:registry=https://artifactory.ida.avast.com/artifactory/api/npm/registry.npmjs.org/
                registry=https://artifactory.ida.avast.com/artifactory/api/npm/registry.npmjs.org-virtual/
                " >  ~/.npmrc

                npm install gulp-cli -g --unsafe-perm
                npm i --unsafe-perm

                # Build plugin
                gulp default --set-version=%build.number%

                # Job to pack source codes for mozilla
                # should be run on develop and master
                gulp zip:mozillaStore --set-version=%build.number%
                gulp zip:operaStore --set-version=%build.number%
            """.trimIndent())
            param("default-shell-location", "/bin/bash")
            param("docker-image-name", "docker.ida.avast.com/dockerhub/node:14.15.4-buster")
            param("vagrantfile-do-override", "no")
        }
    }

    triggers {
        vcs {
            branchFilter = "+:<default>"
        }
    }
})

