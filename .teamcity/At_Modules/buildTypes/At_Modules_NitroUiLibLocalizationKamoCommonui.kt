package At_Modules.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*

object At_Modules_NitroUiLibLocalizationKamoCommonui : BuildType({
    templates(_Self.buildTypes.ModuleTemplate)
    name = "nitro-ui-lib-localization-kamo-commonui"

    params {
        param("repo", "ff/nitro-ui-lib-localization-kamo")
        param("branch", "refs/heads/stage")
        param("dockerImagePath", "%dep.WinDev_NitroUI_DockerImages_Node11.image_path%")
    }
})
