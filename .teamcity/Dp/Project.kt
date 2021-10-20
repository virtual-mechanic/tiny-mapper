package Dp

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

object Project : Project({
    id("Dp")
    name = "DP"
    description = "DataProtection team project"

    subProject(Dp_Modules.Project)
    subProject(Dp_Npm.Project)
    subProject(Dp_Builds.Project)
    subProject(Dp_Weirdos.Project)
})
