package Av_Modules

import Av_Modules.buildTypes.*
import Av_Modules.Consts.NITRO_UI_MODULES
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

object Project : Project({
    id("Av_Modules")
    name = "AV Modules"

    template(Av_Modules_NitroUiModuleTemplate)

    NITRO_UI_MODULES.forEach {
      buildType(
        BuildType({
            id = RelativeId("Av_Modules_" + it.capitalize())
            templates(Av_Modules.buildTypes.Av_Modules_NitroUiModuleTemplate)
            name = it
        })
      )
    }

    buildType(Av_Modules_Kin)
    buildType(Av_Modules_NitroUiCore)
    buildType(Av_Modules_NitroUiLibLocalization)
    buildType(Av_Modules_Spawn)
})
