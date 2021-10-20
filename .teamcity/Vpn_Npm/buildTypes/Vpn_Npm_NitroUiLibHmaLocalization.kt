package Vpn_Npm.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*

object Vpn_Npm_NitroUiLibHmaLocalization : BuildType({
    templates(_Self.buildTypes.NpmTemplate)
    name = "nitro-ui-lib-hma-localization"

    params {
        param("repo", "ff/nitro-ui-lib-hma-localization")
        param("dockerImagePath", "%dep.WinDev_NitroUI_DockerImages_Node11.image_path%")
    }
})
