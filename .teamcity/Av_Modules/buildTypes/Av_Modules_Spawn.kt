package Av_Modules.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.sshAgent
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script

object Av_Modules_Spawn : BuildType({
    templates(_Self.buildTypes.ModuleTemplate)
    name = "spawn"

    params {
        select("CDN", "test", label = "CDN",
                options = listOf("test", "both"))
        param("repo", "ff/spawn")
        param("dockerImagePath", "%dep.WinDev_NitroUI_DockerImages_Node14.image_path%")
        param("3rdPartyLibsStep", "1")
        param("artifactDir", "package")
    }

    vcs {
        root(_Self.vcsRoots.Repo)
    }

    steps {
        script {
            name = "CDN"
            id = "RUNNER_5371"
            scriptContent = """
                #!/bin/bash

                # Guys working on account.avast.com (which uses spawn too)
                # sometimes need to build and publish spawn to the production CDN
                # so they run the job with CDN=both
                if [ %CDN% != "both" ]; then
                   exit 0
                fi

                package="NuiSpawn"
                localdir="${'$'}(pwd)/deploy/*"

                rm -rf CDN-deploy
                mkdir -p ~/.ssh
                ssh-keyscan -H git.int.avast.com >> ~/.ssh/known_hosts
                git config --global user.name "svc.github.deploy004"
                git config --global user.email "svc.github.deploy004@avast.com"
                git clone git@git.int.avast.com:ff/nitro-ui-deploy.git CDN-deploy
                echo "RELEASE TO: ${'$'}{package}"
                mkdir -p CDN-deploy/${'$'}{package} && cp -R ${'$'}{localdir} CDN-deploy/${'$'}{package}
                cd CDN-deploy
                git add .
                git commit -m 'Deploy'
                git push -u origin master

                ls
                ls -la ${'$'}{package}
                cd ..
                rm -rf CDN-deploy
                echo "RELEASE command finished"
            """.trimIndent()
            param("org.jfrog.artifactory.selectedDeployableServer.downloadSpecSource", "Job configuration")
            param("org.jfrog.artifactory.selectedDeployableServer.useSpecs", "false")
            param("org.jfrog.artifactory.selectedDeployableServer.uploadSpecSource", "Job configuration")
        }
        stepsOrder = arrayListOf("RUNNER_3100", "RUNNER_5371")
    }

    features {
        sshAgent {
            id = "ssh-agent-build-feature"
            teamcitySshKey = "svc-github-deploy004"
        }
    }

    disableSettings("RQ_1264")
})
