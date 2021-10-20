package Av_Npm.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*

object Av_Npm_I18nextSprintfPostprocessor2 : BuildType({
    templates(_Self.buildTypes.NpmTemplate)
    name = "i18next-sprintf-postprocessor2"

    params {
        param("repo", "ui/i18next-sprintf-postprocessor2")
        param("dockerImagePath", "%dep.WinDev_NitroUI_DockerImages_Node11.image_path%")
        param("3rdPartyLibsStep", "1")
    }
})
