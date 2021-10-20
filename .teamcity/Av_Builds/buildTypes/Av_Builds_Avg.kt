package Av_Builds.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script

object Av_Builds_Avg : BuildType({
    templates(_Self.buildTypes.BuildTemplate)
    name = "Build AVG"

    params {
        param("avbranding", "AVG")
    }

    steps {
        script {
            name = "Publish NuPkg"
            id = "RUNNER_2862"
            scriptContent = """
                #!/bin/bash
                echo "Publishing nuget to artifactory"
            """.trimIndent()
            param("org.jfrog.artifactory.selectedDeployableServer.publishBuildInfo", "true")
            param("org.jfrog.artifactory.selectedDeployableServer.useSpecs", "true")
            param("org.jfrog.artifactory.selectedDeployableServer.buildDependencies", "Requires Artifactory Pro.")
            param("org.jfrog.artifactory.selectedDeployableServer.urlId", "0")
            param("org.jfrog.artifactory.selectedDeployableServer.envVarsExcludePatterns", "*password*,*secret*")
            param("org.jfrog.artifactory.selectedDeployableServer.uploadSpec", """
                {
                    "files": [
                                {
                                    "pattern": "(*).nupkg",
                                    "target": "%system.NuGet.BasePath%/NitroUI/%avbranding%/%avedition%/{1}.nupkg"
                                }
                             ]
                }
            """.trimIndent())
            param("org.jfrog.artifactory.selectedDeployableServer.downloadSpecSource", "Job configuration")
            param("org.jfrog.artifactory.selectedDeployableServer.overrideDefaultDeployerCredentials", "true")
            param("org.jfrog.artifactory.selectedDeployableServer.uploadSpecSource", "Job configuration")
            param("secure:org.jfrog.artifactory.selectedDeployableServer.deployerPassword", "%artifactory.pass%")
            param("org.jfrog.artifactory.selectedDeployableServer.deployerUsername", "%artifactory.user%")
        }
    }
})
