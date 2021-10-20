package At_Builds

import At_Builds.buildTypes.*
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

object Project : Project({
    id("At_Builds")
    name = "AT Builds"
    description = "Jobs publishing the modules to CDN and creating nugets"

    buildType(At_Builds_AvastAT)
    buildType(At_Builds_AvgAT)
    buildType(At_Builds_CCleanerAT)
})
