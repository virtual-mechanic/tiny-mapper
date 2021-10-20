package Av

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

object Project : Project({
    id("Av")
    name = "AV"
    description = "AntiVirus team project"

    subProject(Av_Testing.Project)
    subProject(Av_Modules.Project)
    subProject(Av_Npm.Project)
    subProject(Av_Builds.Project)
    subProject(Av_Tools.Project)
})
