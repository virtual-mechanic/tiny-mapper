package Vpn

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

object Project : Project({
    id("Vpn")
    name = "VPN"
    description = "VPN team project"

    subProject(Vpn_Testing.Project)
    subProject(Vpn_Modules.Project)
    subProject(Vpn_Npm.Project)
    subProject(Vpn_Builds.Project)
})
