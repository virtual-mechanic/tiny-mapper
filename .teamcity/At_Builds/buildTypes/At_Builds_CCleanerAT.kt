package At_Builds.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*

object At_Builds_CCleanerAT : BuildType({
    templates(_Self.buildTypes.BuildTemplate)
    name = "Build CCleaner AT"

    params {
        param("avbranding", "CCleaner")
    }
})
