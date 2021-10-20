package Av_Npm.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

object Av_Npm_Dev : BuildType({
    templates(_Self.buildTypes.NpmTemplate)
    name = "dev"

    params {
        param("repo", "ff/nitro-ui")
        param("workingDir", "dev")
        param("dockerImagePath", "%dep.WinDev_NitroUI_DockerImages_Node14.image_path%")
    }

    artifactRules = """%workingDir%/dist/**/*"""

    triggers {
        vcs {
            id = "vcsTrigger"
            triggerRules = "+:%workingDir%/package.json"
            branchFilter = ""
        }
    }
})
