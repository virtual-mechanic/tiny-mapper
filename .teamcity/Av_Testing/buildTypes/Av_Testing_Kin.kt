package Av_Testing.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

object Av_Testing_Kin : BuildType({
    templates(_Self.buildTypes.TestingTemplate)
    name = "kin"

    params {
        param("repo", "ui/kin")
    }

    steps {
        step {
            id = "testAndBuildStep"
            name = "Build"
            type = "jonnyzzz.vm"
            param("docker-commandline", "-v ${'$'}HOME:${'$'}HOME")
            param("vm", "docker")
            param("docker-mount-mode", "rw")
            param("script", """
                #!/bin/bash
                set -e -E

                if [[ "${'$'}(git log -1 --pretty=%B)" =~ " NO_CI"${'$'} ]]; then
                  echo "CI skipped"
                  exit 1
                fi

                npm update
                npm install
                npm start

                mkdir __resources
                mv dist/app.js __resources/Kin.js
                mv dist/app.raw.js __resources/Kin.raw.js
            """.trimIndent())
            param("default-shell-location", "/bin/bash")
            param("docker-image-name", "%dep.WinDev_NitroUI_DockerImages_Node14.image_path%")
            param("vagrantfile-do-override", "no")
        }
    }
})
