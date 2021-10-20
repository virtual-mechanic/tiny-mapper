package At_Modules.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*

object At_Modules_NitroUiLibLocalizationAntitrack : BuildType({
    templates(_Self.buildTypes.ModuleTemplate)
    name = "nitro-ui-lib-localization-antitrack"

    params {
        param("repo", "ff/nitro-ui-lib-localization-antitrack")
        param("dockerImagePath", "%dep.WinDev_NitroUI_DockerImages_Node11.image_path%")
    }
})
