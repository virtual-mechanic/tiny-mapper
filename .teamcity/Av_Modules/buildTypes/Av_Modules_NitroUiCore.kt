package Av_Modules.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*

object Av_Modules_NitroUiCore : BuildType({
    templates(_Self.buildTypes.ModuleTemplate)
    name = "nitro-ui-core"

    params {
        param("repo", "ff/nitro-ui-core")
        param("dockerImagePath", "%dep.WinDev_NitroUI_DockerImages_Node11.image_path%")
    }

    disableSettings("RQ_1264")
})
