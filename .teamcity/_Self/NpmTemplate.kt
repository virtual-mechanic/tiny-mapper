package _Self

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

object NpmTemplate : Project({
    buildType(_Self.buildTypes.Av_Npm_TinyMapper)
})
