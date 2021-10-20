package Av_Modules.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*

object Av_Modules_NitroUiLibLocalization : BuildType({
    templates(_Self.buildTypes.ModuleTemplate)
    name = "nitro-ui-lib-localization"

    artifactRules = "__resources"

    params {
        param("repo", "ff/nitro-ui-lib-localization")
        param("3rdPartyLibsStep", "1")
        param("artifactDir", "__resources")
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

                ./buildArtifacts.js --buildNumber=%build.number%
            """.trimIndent())
            param("default-shell-location", "/bin/bash")
            param("docker-image-name", "%dep.WinDev_NitroUI_DockerImages_Node14.image_path%")
            param("vagrantfile-do-override", "no")
        }
    }

    disableSettings("RQ_1264")
})
