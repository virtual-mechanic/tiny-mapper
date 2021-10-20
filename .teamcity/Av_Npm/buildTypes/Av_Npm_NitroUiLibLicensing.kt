package Av_Npm.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*

object Av_Npm_NitroUiLibLicensing : BuildType({
    templates(_Self.buildTypes.NpmTemplate)
    name = "nitro-ui-lib-licensing"

    params {
        param("repo", "ff/nitro-ui-lib-licensing")
        param("dockerImagePath", "%dep.WinDev_NitroUI_DockerImages_Node11.image_path%")
        param("3rdPartyLibsStep", "1")
    }
})
