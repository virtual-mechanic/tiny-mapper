package Av_Npm.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*

object Av_Npm_Xmli18Next : BuildType({
    templates(_Self.buildTypes.NpmTemplate)
    name = "xmli18Next"

    params {
        param("repo", "ui/xml-i18next")
        param("dockerImagePath", "%dep.WinDev_NitroUI_DockerImages_Node11.image_path%")
        param("3rdPartyLibsStep", "1")
    }
})
