package Av_Npm.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

object Av_Npm_TinyMapper : BuildType({
    templates(_Self.buildTypes.NpmTemplate)

    name = "tiny-mapper"

    params {
        param("repo", "https://github.com/Nenaddd/tiny-mapper")
        param("dockerImagePath", "%dep.WinDev_NitroUI_DockerImages_Node14.image_path%")
    }

    triggers {
        vcs {
            id = "vcsTrigger"
            triggerRules = "+:*"
        }
    }
})
