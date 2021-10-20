package Av_Builds.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*

object Av_Builds_Avast : BuildType({
    templates(_Self.buildTypes.BuildTemplate)
    name = "Build Avast"
})
