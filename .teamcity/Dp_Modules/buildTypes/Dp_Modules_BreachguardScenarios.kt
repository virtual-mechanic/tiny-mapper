package Dp_Modules.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

object Dp_Modules_BreachguardScenarios : BuildType({
    templates(_Self.buildTypes.ModuleTemplate)
    name = "breachguard-scenarios"

    artifactRules = "dist/*=>"

    params {
        param("version", "1.1")
        param("repo", "dp/breachguard-scenarios")
        param("dockerImagePath", "%dep.WinDev_NitroUI_DockerImages_Node11.image_path%")
    }

    steps {
        step {
            name = "Build advertiser scenarios"
            id = "RUNNER_3100"
            type = "jonnyzzz.vm"
            param("vm", "docker")
            param("docker-mount-mode", "rw")
            param("script", """
                #!/bin/bash

                set -e -E

                echo npm install
                npm install --unsafe-perm --no-shrinkwrap

                echo npm run build
                npm run build
            """.trimIndent())
            param("default-shell-location", "/bin/bash")
            param("docker-image-name", "%dep.WinDev_NitroUI_DockerImages_Node11.image_path%")
            param("vagrantfile-do-override", "no")
        }
    }

    dependencies {
        snapshot(DockerImages.buildTypes.DockerImages_Node11) {
            onDependencyFailure = FailureAction.IGNORE
            onDependencyCancel = FailureAction.IGNORE
        }
    }
})
