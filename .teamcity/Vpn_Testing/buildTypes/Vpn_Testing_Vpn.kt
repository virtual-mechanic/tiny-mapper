package Vpn_Testing.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType

object Vpn_Testing_Vpn : BuildType({
    templates(_Self.buildTypes.TestingTemplate)
    name = "nitro-ui-vpn"

    params {
        param("repo", "ff/nitro-ui-vpn")
    }

    steps {
        step {
            id = "testAndBuildStep"
            name = "Run tests & build modules"
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

                npm install

                npx nuidev-run-tests

                if [[ "%dnba%" != "false" && "${'$'}(git log -1 --pretty=%B)" =~ " DNBA"${'$'} ]]; then
                  npx nuidev-build-modules -- --dontBuildAll --buildType=stage
                else
                  npx nuidev-build-modules -- --buildType=stage
                fi
            """.trimIndent())
            param("default-shell-location", "/bin/bash")
            param("docker-image-name", "%dep.WinDev_NitroUI_DockerImages_Node14.image_path%")
            param("vagrantfile-do-override", "no")
        }
    }
})

