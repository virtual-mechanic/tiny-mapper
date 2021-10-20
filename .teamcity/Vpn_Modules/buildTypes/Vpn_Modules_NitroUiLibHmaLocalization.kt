package Vpn_Modules.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*

object Vpn_Modules_NitroUiLibHmaLocalization : BuildType({
    templates(_Self.buildTypes.ModuleTemplate)
    name = "nitro-ui-lib-hma-localization"

    params {
        param("repo", "ff/nitro-ui-lib-hma-localization")
        param("dockerImagePath", "%dep.WinDev_NitroUI_DockerImages_Node11.image_path%")
    }
})
