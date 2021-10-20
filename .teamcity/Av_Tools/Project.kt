package Av_Tools

import Av_Tools.buildTypes.*
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

object Project : Project({
    id("Av_Tools")
    name = "AV Tools"
    description = "Miscellaneous tools"

    buildType(Av_Tools_Update3rdPartyLibs)
})
