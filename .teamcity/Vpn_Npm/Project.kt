package Vpn_Npm

import Vpn_Npm.buildTypes.*
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

object Project : Project({
    id("Vpn_Npm")
    name = "VPN NPM"
    description = "Jobs publishing the modules to NPM"

    buildType(Vpn_Npm_BurgerClient)
    buildType(Vpn_Npm_NitroUiLibHmaLocalization)
})
