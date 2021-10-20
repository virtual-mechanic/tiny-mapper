package Dp_Modules

import Dp_Modules.buildTypes.*
import Dp_Modules.vcsRoots.*
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

object Project : Project({
    id("Dp_Modules")
    name = "DP Modules"

    vcsRoot(Dp_Modules_AosRepo)

    buildType(Dp_Modules_Aos)
    buildType(Dp_Modules_Breachguard)
    buildType(Dp_Modules_BreachguardScenarios)
    buildType(Dp_Modules_PasswordChanger)
})
