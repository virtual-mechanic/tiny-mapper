package Av_Testing.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType

object Av_Testing_NitroUiLibLicensing : BuildType({
    templates(Av_Testing.buildTypes.Av_Testing_VNextNpmTemplate)
    name = "nitro-ui-lib-licensing"

    params {
        param("repo", "ff/nitro-ui-lib-licensing")
    }

})
