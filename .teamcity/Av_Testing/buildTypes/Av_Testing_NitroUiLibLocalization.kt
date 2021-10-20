package Av_Testing.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

object Av_Testing_NitroUiLibLocalization : BuildType({
    templates(_Self.buildTypes.TestingTemplate)
    name = "nitro-ui-lib-localization"

    params {
        param("repo", "ff/nitro-ui-lib-localization")
    }

    steps {
        step {
            id = "testAndBuildStep"
            name = "Build localizations"
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

                #npm run test # uncomment when errors are fixed

                ./buildArtifacts.js --pr=%teamcity.pullRequest.number% --githubToken=%system.GitHub.CommitStatusToken%
            """.trimIndent())
            param("default-shell-location", "/bin/bash")
            param("docker-image-name", "%dep.WinDev_NitroUI_DockerImages_Node14.image_path%")
            param("vagrantfile-do-override", "no")
        }
    }
})
