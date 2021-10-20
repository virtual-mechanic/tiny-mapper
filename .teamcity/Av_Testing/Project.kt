package Av_Testing

import Av_Testing.buildTypes.*
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

object Project : Project({
    id("Av_Testing")
    name = "AV Testing"
    description = "Testing PRs"

    template(Av_Testing_VNextNpmTemplate)

    buildType(Av_Testing_NitroUi)
    buildType(Av_Testing_NitroUiLibLocalization)
    buildType(Av_Testing_Spawn)
    buildType(Av_Testing_Kin)
    buildType(Av_Testing_NitroAuth)
    buildType(Av_Testing_NitroMenu)
    buildType(Av_Testing_NitroUiLibLicensing)
})
