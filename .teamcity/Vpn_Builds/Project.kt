package Vpn_Builds

import Vpn_Builds.buildTypes.*
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

object Project : Project({
    id("Vpn_Builds")
    name = "VPN Builds"

    buildType(Vpn_Builds_AvastSL)
    buildType(Vpn_Builds_AvgSL)
    buildType(Vpn_Builds_Hma)

})
