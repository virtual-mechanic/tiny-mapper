package Vpn_Builds.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*

object Vpn_Builds_Hma : BuildType({
    templates(_Self.buildTypes.BuildTemplate)
    name = "Build PrivaxSL"

    params {
        param("skipPublish", "true")
        param("avbranding", "Privax")
    }
})
