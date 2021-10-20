package At_Builds.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*

object At_Builds_AvastAT : BuildType({
    templates(_Self.buildTypes.BuildTemplate)
    name = "Build Avast AT"
})

