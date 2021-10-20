package Vpn_Modules

import Vpn_Modules.buildTypes.*
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

val VPN_MODULES = listOf(
  "animation",
  "compatibility",
  "core",
  "hma",
  "libs",
  "nitroMenu",
  "secureLine",
  "secureVpn",
  "stores",
  "styles",
  "thirdPartyLibs"
)

object Project : Project({
    id("Vpn_Modules")
    name = "VPN Modules"

    template(Vpn_Modules_ModuleTemplate)

    VPN_MODULES.forEach {
      buildType(
        BuildType({
            id = RelativeId("Vpn_Modules_" + it.capitalize())
            templates(Vpn_Modules.buildTypes.Vpn_Modules_ModuleTemplate)
            name = it
        })
      )
    }

    buildType(Vpn_Modules_NitroUiLibHmaLocalization)
    buildType(Vpn_Modules_NitroUiLibLocalizationAvgSecureline)
    buildType(Vpn_Modules_NitroUiLibLocalizationSecureline)
})
