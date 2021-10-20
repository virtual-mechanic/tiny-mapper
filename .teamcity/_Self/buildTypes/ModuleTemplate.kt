package _Self.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs
import _Self.Funcs.generate3rdPartyLibsJs

object ModuleTemplate : Template({
    name = "Module template"

    artifactRules = """%artifactDir%/**"""
    buildNumberPattern = "%version%.%build.counter%"

    params {
        param("version", "1.2")
        param("repo", "")
        param("branch", "refs/heads/master")
        param("branchSpec", "")
        param("dockerImagePath", "%dep.WinDev_NitroUI_DockerImages_Node14.image_path%")
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
            param("vm", "docker")
            param("script", """
                #!/bin/bash

                set -e -E

                npm update
                npm install
                npm run package -- --buildNumber=%build.number%
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

    triggers {
        vcs {
            id = "vcsTrigger"
            triggerRules = "+:**"
            branchFilter = ""
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
