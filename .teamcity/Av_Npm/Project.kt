package Av_Npm

import Av_Npm.buildTypes.*
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

object Project : Project({
    id("Av_Npm")
    name = "AV NPM"
    description = "Jobs publishing the modules to NPM"

    buildType(Av_Npm_Dev)
    buildType(Av_Npm_I18nextSprintfPostprocessor2)
    buildType(Av_Npm_NitroAuth)
    buildType(Av_Npm_NitroUiLibLicensing)
    buildType(Av_Npm_NitroUiLibLocCommons)
    buildType(Av_Npm_Xmli18Next)
    buildType(Av_Npm_NitroMenu)
})
