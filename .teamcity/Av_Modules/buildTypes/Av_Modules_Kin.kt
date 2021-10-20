package Av_Modules.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

object Av_Modules_Kin : BuildType({
    templates(_Self.buildTypes.ModuleTemplate)
    name = "kin"

    params {
        param("repo", "ui/kin")
        param("3rdPartyLibsStep", "1")
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
            param("docker-mount-mode", "rw")
            param("script", """
                #!/bin/bash

                set -e -E

                npm update
                npm install
                npm start -- --buildNumber=%build.number%
            """.trimIndent())
            param("default-shell-location", "/bin/bash")
            param("docker-image-name", "%dep.WinDev_NitroUI_DockerImages_Node14.image_path%")
            param("vagrantfile-do-override", "no")
        }
    }
})
