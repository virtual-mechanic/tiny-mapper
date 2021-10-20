package Av_Npm.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*

object Av_Npm_NitroMenu : BuildType({
    templates(_Self.buildTypes.NpmTemplate)
    name = "nitro-menu"

    params {
        param("repo", "ui/nitro-menu")
        param("dockerImagePath", "%dep.WinDev_NitroUI_DockerImages_Node11.image_path%")
        param("3rdPartyLibsStep", "1")
    }
})
