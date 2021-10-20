package At_Builds.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*

object At_Builds_AvgAT : BuildType({
    templates(_Self.buildTypes.BuildTemplate)
    name = "Build Avg AT"

    params {
        param("avbranding", "AVG")
    }
})
