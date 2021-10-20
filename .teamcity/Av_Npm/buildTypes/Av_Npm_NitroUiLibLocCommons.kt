package Av_Npm.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*

object Av_Npm_NitroUiLibLocCommons : BuildType({
    templates(_Self.buildTypes.NpmTemplate)
    name = "nitro-ui-lib-locCommons"

    params {
        param("repo", "ff/nitro-ui-lib-locCommons")
        param("dockerImagePath", "%dep.WinDev_NitroUI_DockerImages_Node11.image_path%")
    }
})
