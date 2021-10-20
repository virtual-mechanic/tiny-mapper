package _Self

import _Self.buildTypes.*
import _Self.vcsRoots.*
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project
import jetbrains.buildServer.configs.kotlin.v2019_2.projectFeatures.dockerRegistry

object Project : Project({

    vcsRoot(_Self.vcsRoots.Repo)

    template(_Self.buildTypes.BuildTemplate)
    template(_Self.buildTypes.ModuleTemplate)
    template(_Self.buildTypes.NpmTemplate)
    template(_Self.buildTypes.TestingTemplate)

    params {
        param("artifactory.user", "nitrouipusher")
        password("artifactory.pass", "credentialsJSON:9b3ba63c-2393-4443-8625-833f45d18840", display = ParameterDisplay.HIDDEN)
        password("artifactory.auth", "credentialsJSON:34745dc8-bed0-4e17-b1d3-c9e6eb3cc794", display = ParameterDisplay.HIDDEN)
        param("dockerRegistry.user", "nitrouipusher")
        password("dockerRegistry.pass", "credentialsJSON:bb2346d8-41de-4610-aa0d-7e3956831f38", display = ParameterDisplay.HIDDEN)
        password("snyk.apiToken", "credentialsJSON:38ba435b-74af-4e26-ae5c-a243943f502e", display = ParameterDisplay.HIDDEN)
        //param("teamcity.ui.settings.readOnly", "true")
    }

    features {
        dockerRegistry {
            id = "PROJECT_EXT_37"
            name = "Docker Registry"
            url = "docker.ida.avast.com"
            userName = "%dockerRegistry.user%"
            password = "%dockerRegistry.pass%"
        }
    }

    cleanup {
        baseRule {
            all(builds = 20, days = 60)
        }
    }

    subProject(Av.Project)
    subProject(At.Project)
    subProject(Dp.Project)
    subProject(Vpn.Project)
    subProject(DockerImages.Project)
    subProject(Identity.Project)
})
