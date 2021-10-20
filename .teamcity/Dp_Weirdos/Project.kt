package Dp_Weirdos

import Dp_Weirdos.buildTypes.*
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

object Project : Project({
    id("Dp_Weirdos")
    name = "DP Weirdos"
    description = "Weirdo Agent Pool Association"

    buildType(Dp_Weirdos_DbrsAutomationTests)
})

