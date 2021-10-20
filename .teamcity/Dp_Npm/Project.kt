package Dp_Npm

import Dp_Npm.buildTypes.*
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

object Project : Project({
    id("Dp_Npm")
    name = "DP NPM"
    description = "Jobs publishing the modules to NPM"

    buildType(Dp_Npm_ScenariosRunner)
    buildType(Dp_Npm_StateCodes)
})
