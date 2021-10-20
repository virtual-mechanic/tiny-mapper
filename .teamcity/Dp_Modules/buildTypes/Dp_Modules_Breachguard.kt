package Dp_Modules.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

object Dp_Modules_Breachguard : BuildType({
    templates(_Self.buildTypes.ModuleTemplate)
    name = "nitro-ui-module-breachguard"

    artifactRules = """
        dist/win/avast/*=>avast
        dist/win/avg/*=>avg
    """.trimIndent()

    params {
        param("version", "1.1")
        param("branch", "refs/heads/release")
        param("branchSpec", "refs/heads/master")
        param("repo", "dp/id-protection")
        param("dockerImagePath", "%dep.WinDev_NitroUI_DockerImages_Node11.image_path%")
    }

    steps {
        step {
            name = "Build package - Avast brand"
            id = "RUNNER_3100"
            type = "jonnyzzz.vm"
            param("vm", "docker")
            param("docker-mount-mode", "rw")
            param("script", """
                #!/bin/bash

                set -e -E

                echo npm install
                npm install --unsafe-perm --no-shrinkwrap

                echo npm run build:avast
                npm run build:avast -- --buildNumber=%build.number%
            """.trimIndent())
            param("default-shell-location", "/bin/bash")
            param("docker-image-name", "docker.ida.avast.com/dockerhub/node:12.14")
            param("vagrantfile-do-override", "no")
        }
        step {
            name = "Build package - AVG brand"
            type = "jonnyzzz.vm"
            param("vm", "docker")
            param("docker-mount-mode", "rw")
            param("script", """
                #!/bin/bash

                set -e -E

                echo npm install
                npm install --unsafe-perm --no-shrinkwrap

                echo npm run build:avg
                npm run build:avg -- --buildNumber=%build.number%
            """.trimIndent())
            param("default-shell-location", "/bin/bash")
            param("docker-image-name", "docker.ida.avast.com/dockerhub/node:12.14")
            param("vagrantfile-do-override", "no")
        }
    }

    dependencies {
        snapshot(DockerImages.buildTypes.DockerImages_Node11) {
            onDependencyFailure = FailureAction.IGNORE
            onDependencyCancel = FailureAction.IGNORE
        }
    }

    requirements {
        doesNotEqual("env.OS", "Windows_NT")
    }
})
