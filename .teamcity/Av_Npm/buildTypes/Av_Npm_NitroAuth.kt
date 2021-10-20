package Av_Npm.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*

object Av_Npm_NitroAuth : BuildType({
    templates(_Self.buildTypes.NpmTemplate)
    name = "nitro-auth"

    params {
        param("repo", "ui/nitro-auth")
        param("dockerImagePath", "%dep.WinDev_NitroUI_DockerImages_Node11.image_path%")
        param("3rdPartyLibsStep", "1")
    }
})
