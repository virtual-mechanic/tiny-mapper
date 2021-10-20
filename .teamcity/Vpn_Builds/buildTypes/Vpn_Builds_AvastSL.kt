package Vpn_Builds.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*

object Vpn_Builds_AvastSL : BuildType({
    templates(_Self.buildTypes.BuildTemplate)
    name = "Build Avast SL"

    params {
        param("skipPublish", "true")
    }
})
