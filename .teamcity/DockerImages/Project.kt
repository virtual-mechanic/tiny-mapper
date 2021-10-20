package DockerImages

import DockerImages.buildTypes.*
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

object Project : Project({
    id("DockerImages")
    name = "Docker Images"

    buildType(DockerImages_Node11)
    buildType(DockerImages_Node14)

})
