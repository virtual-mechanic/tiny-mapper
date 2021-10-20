package At_Modules.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*

object At_Modules_NitroUiModuleAntitrackCommonUi : BuildType({
    templates(_Self.buildTypes.ModuleTemplate)
    name = "nitro-ui-module-antitrack-common-ui"

    params {
        param("repo", "ff/nitro-ui-module-antitrack-standalone")
        param("branch", "refs/heads/stage")
        param("dockerImagePath", "%dep.WinDev_NitroUI_DockerImages_Node11.image_path%")
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

                npm install
                npm run postinstall
                npm run build -- --buildNumber=%build.number%
            """.trimIndent())
            param("default-shell-location", "/bin/bash")
            param("docker-image-name", "%dockerImagePath%")
            param("vagrantfile-do-override", "no")
        }
    }
})
