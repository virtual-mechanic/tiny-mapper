package Vpn_Testing

import Vpn_Testing.buildTypes.*
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

object Project : Project({
    id("Vpn_Testing")
    name = "VPN Testing"
    description = "Testing PRs"

    buildType(Vpn_Testing_Vpn)
})
