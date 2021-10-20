package Dp_Modules.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

object Dp_Modules_PasswordChanger : BuildType({
    templates(_Self.buildTypes.ModuleTemplate)
    name = "password-changer"

    params {
        param("version", "1.2")
        param("repo", "dp/password-changer")
        param("branchSpec", "+:refs/heads/release/*")
        param("dockerImagePath", "%dep.WinDev_NitroUI_DockerImages_Node11.image_path%")
    }

    steps {
        step {
            name = "TESTING"
            type = "jonnyzzz.vm"
            enabled = false
            param("vm", "docker")
            param("script", """
                #!/bin/bash
                set -e -E

                apt-get update && apt-get install -y jq

                SYNC_ONLY_DC=prg
                PKG_NAME=Nui${'$'}(jq -r .module package.json)
                PKG_DIR_PER_VERSION=1
                TREE_PACKAGE=tree.zip
                #USE_BNR_TREEVER?=0
                #USE_TREEVER_FROM_PKGJSON?=1
                #cd /webapp;

                set -x

                npm update
                sleep 1200
                npm install
                npm run package -- --buildNumber=%build.number%
            """.trimIndent())
            param("docker-image-name", "docker.int.avast.com/nitro-ui/myavast-node:latest")
        }
    }

    dependencies {
        snapshot(DockerImages.buildTypes.DockerImages_Node11) {
            onDependencyFailure = FailureAction.IGNORE
        }
    }
})
