package Identity_Npm

import Identity_Npm.buildTypes.*
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

object Project : Project({
    id("Identity_Npm")
    name = "Identity NPM"
    description = "Jobs publishing the modules to NPM"

    buildType(Identity_Npm_TinyMapper)
})
