package Dp_Npm.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*

object Dp_Npm_StateCodes : BuildType({
    templates(_Self.buildTypes.NpmTemplate)
    name = "state-codes"

    params {
        param("repo", "dp/state-codes")
        param("dockerImagePath", "%dep.WinDev_NitroUI_DockerImages_Node11.image_path%")
    }
})
