package Vpn_Modules.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

object Vpn_Modules_ModuleTemplate : Template({
    name = "VPN module template"

    artifactRules = "%system.teamcity.buildConfName%/dist/**/*"
    buildNumberPattern = "%version%.%build.counter%"

    params {
        param("version", "1.2")
        param("repo", "ff/nitro-ui-vpn")
        param("branch", "refs/heads/master")
        param("branchSpec", "")
    }

    vcs {
        root(_Self.vcsRoots.Repo)
    }

    steps {
        step {
            name = "Build package"
            id = "RUNNER_3100"
            type = "jonnyzzz.vm"
            param("teamcity.build.workingDir", "%system.teamcity.buildConfName%")
            param("vm", "docker")
            param("script", """
                #!/bin/bash

                set -e -E

                echo "Building module %system.teamcity.buildConfName%"
                node -v
                npm -v

                #npm update
                npm install
                npm run package -- --buildNumber=%build.number%
            """.trimIndent())
            param("default-shell-location", "/bin/bash")
            param("docker-image-name", "%dep.WinDev_NitroUI_DockerImages_Node14.image_path%")
            param("vagrantfile-do-override", "no")
        }
    }

    triggers {
        vcs {
            id = "vcsTrigger"
            triggerRules = "+:/%system.teamcity.buildConfName%/**"
            branchFilter = ""
        }
    }

    dependencies {
        snapshot(DockerImages.buildTypes.DockerImages_Node14) {}
    }

    requirements {
        doesNotEqual("env.OS", "Windows_NT")
    }
})
