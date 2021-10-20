package Dp_Builds.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script

object Dp_Builds_AvastIPR : BuildType({
    templates(_Self.buildTypes.BuildTemplate)
    name = "Build Avast IPR"
})
