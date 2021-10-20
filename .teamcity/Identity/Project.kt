package Identity

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

object Project : Project({
    id("Identity")
    name = "Identity"
    description = "Identity team project"

    subProject(Identity_Npm.Project)
})
