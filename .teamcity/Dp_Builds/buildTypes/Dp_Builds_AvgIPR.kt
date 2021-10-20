package Dp_Builds.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script

object Dp_Builds_AvgIPR : BuildType({
    templates(_Self.buildTypes.BuildTemplate)
    name = "Build Avg IPR"

    params {
        param("avbranding", "AVG")
    }
})
