package Vpn_Builds.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*

object Vpn_Builds_AvgSL : BuildType({
    templates(_Self.buildTypes.BuildTemplate)
    name = "Build Avg SL"

    params {
        param("skipPublish", "true")
        param("avbranding", "AVG")
    }
})
