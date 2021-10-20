package Av_Builds

import Av_Builds.buildTypes.*
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

object Project : Project({
    id("Av_Builds")
    name = "AV Builds"
    description = "Jobs publishing the modules to CDN and creating nugets"

    buildType(Av_Builds_Avast)
    buildType(Av_Builds_AvastOmni)
    buildType(Av_Builds_AvastOne)
    buildType(Av_Builds_AvastOneV2)
    buildType(Av_Builds_Avg)
})
