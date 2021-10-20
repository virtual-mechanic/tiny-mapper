package Vpn_Modules.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*

object Vpn_Modules_NitroUiLibLocalizationAvgSecureline : BuildType({
    templates(_Self.buildTypes.ModuleTemplate)
    name = "nitro-ui-lib-localization-avg-secureline"

    params {
        param("repo", "ff/nitro-ui-lib-localization-avg-secureline")
        param("dockerImagePath", "%dep.WinDev_NitroUI_DockerImages_Node11.image_path%")
    }
})
