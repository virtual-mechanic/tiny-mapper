package _Self.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import _Self.Funcs.generate3rdPartyLibsJs

object NpmTemplate : Template({
    name = "NPM template"

    artifactRules = """%artifactDir%/**"""
    buildNumberPattern = "%version%.%build.counter%"

    params {
        param("version", "1.2")
        param("repo", "")
        param("branch", "refs/heads/master")
        param("branchSpec", "")
        param("dockerImagePath", "%dep.WinDev_NitroUI_DockerImages_Node14.image_path%")
        param("workingDir", "")
        param("3rdPartyLibsStep", "0")
        param("artifactDir", "dist")
    }

    vcs {
        root(_Self.vcsRoots.Repo)
    }

    steps {
        step {
            name = "Build package"
            id = "RUNNER_3100"
            type = "jonnyzzz.vm"
            param("teamcity.build.workingDir", "%workingDir%")
            param("vm", "docker")
            param("script", """
                #!/bin/bash

                set -e -E

                echo "unsafe-perm = true" >>  ~/.npmrc

                npm update
                npm install

                echo "_auth = %artifactory.auth%
                always-auth = true
                unsafe-perm = true
                email = re@avast.com" >  ~/.npmrc

                #ignore this script from build NPM package:
                echo "teamcity*.build.cmd" >> .npmignore

                if [[ -f ".npmrc" ]]; then
                  sed -i 's/^registry=.*/registry=https:\/\/artifactory\.ida\.avast\.com\/artifactory\/api\/npm\/npm-local\//g' .npmrc
                else
                  npm config set registry https://artifactory.ida.avast.com/artifactory/api/npm/npm-local/
                fi

                npm publish

                if [[ -f ".npmrc" ]]; then
                  sed -i 's/^registry=.*/registry=https:\/\/artifactory\.ida\.avast\.com\/artifactory\/api\/npm\/registry\.npmjs\.org\//g' .npmrc
                else
                  npm config set registry https://artifactory.ida.avast.com/artifactory/api/npm/registry.npmjs.org/
                fi
            """.trimIndent())
            param("default-shell-location", "/bin/bash")
            param("docker-image-name", "%dockerImagePath%")
            param("vagrantfile-do-override", "no")
        }
        step {
            name = "Generate 3rd party libs JSON"
            id = "3rdPartyLibsStep"
            conditions {
                equals("3rdPartyLibsStep", "1")
            }
            type = "jonnyzzz.vm"
            param("vm", "docker")
            param("script", generate3rdPartyLibsJs("%artifactDir%"))
            param("docker-image-name", "%dockerImagePath%")
        }
    }

    dependencies {
        snapshot(DockerImages.buildTypes.DockerImages_Node11) {}
        snapshot(DockerImages.buildTypes.DockerImages_Node14) {}
    }

    requirements {
        doesNotEqual("env.OS", "Windows_NT")
    }
})
