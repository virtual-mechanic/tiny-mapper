package Dp_Builds

import Dp_Builds.buildTypes.*
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

object Project : Project({
    id("Dp_Builds")
    name = "DP Builds"

    buildType(Dp_Builds_AvastIPR)
    buildType(Dp_Builds_AvgIPR)
})
