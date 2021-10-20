package Av_Testing.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType

object Av_Testing_NitroMenu : BuildType({
    templates(Av_Testing.buildTypes.Av_Testing_VNextNpmTemplate)
    name = "nitro-menu"

    params {
        param("repo", "ui/nitro-menu")
    }

})
