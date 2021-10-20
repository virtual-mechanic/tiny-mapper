package At_Testing

import At_Testing.buildTypes.*
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

object Project : Project({
    id("At_Testing")
    name = "At Testing"
    description = "Testing PRs"

    buildType(At_Testing_Antitrack)
    buildType(At_Testing_Kamo)
    buildType(At_Testing_Localization)
    buildType(At_Testing_KamoLocalization)
})
