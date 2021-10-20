package Vpn_Npm.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*

object Vpn_Npm_BurgerClient : BuildType({
    templates(_Self.buildTypes.NpmTemplate)
    name = "burger-client"

    params {
        param("repo", "dp/burger-client")
        param("dockerImagePath", "%dep.WinDev_NitroUI_DockerImages_Node11.image_path%")
    }
})
