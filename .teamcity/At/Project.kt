package At

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

object Project : Project({
    id("At")
    name = "AT"
    description = "AntiTrack team project"

    subProject(At_Testing.Project)
    subProject(At_Modules.Project)
    subProject(At_Builds.Project)
})
