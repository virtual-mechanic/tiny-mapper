package At_Modules

import At_Modules.buildTypes.*
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

object Project : Project({
    id("At_Modules")
    name = "AT Modules"

    buildType(At_Modules_NitroUiLibLocalizationAntitrack)
    buildType(At_Modules_NitroUiLibLocalizationAntitrackCommonui)
    buildType(At_Modules_NitroUiLibLocalizationKamo)
    buildType(At_Modules_NitroUiLibLocalizationKamoCommonui)
    buildType(At_Modules_NitroUiModuleAntitrackCommonUi)
    buildType(At_Modules_NitroUiModuleAntitrackStandalone)
    buildType(At_Modules_NitroUiModuleKamoCommonUi)
    buildType(At_Modules_NitroUiModuleKamoStandalone)
})
