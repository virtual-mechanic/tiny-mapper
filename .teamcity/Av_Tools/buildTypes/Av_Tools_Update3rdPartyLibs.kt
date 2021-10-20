package Av_Tools.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.sshAgent
import Av_Modules.Consts.MODULES_WITH_3RD_PARTY_LIBS
import _Self.Funcs.nameToId

object Av_Tools_Update3rdPartyLibs : BuildType({
    name = "Update 3rd party libs"
    description = "Updates the 3rd party libs (NPM libs) in the About section"

    artifactRules = """%artifactDir%/**"""
    buildNumberPattern = "%version%.%build.counter%"

    params {
        param("version", "1.2")
        param("repo", "ff/nitro-ui")
        param("branch", "refs/heads/master")
        param("branchSpec", "")
        param("dockerImagePath", "%dep.WinDev_NitroUI_DockerImages_Node14.image_path%")
        param("artifactDir", ".artifactDependencies")
    }

    vcs {
        root(_Self.vcsRoots.Repo)
    }

    steps {
        step {
            name = "Build package"
            id = "RUNNER_3100"
            type = "jonnyzzz.vm"
            param("vm", "docker")
            param("docker-commandline", "-v ${'$'}SSH_AUTH_SOCK:/tmp/ssh_auth_sock -e SSH_AUTH_SOCK=/tmp/ssh_auth_sock")
            param("script", """
                #!/bin/bash

                set -e -E

                cd dev
                npm update
                npm install
                npm link

                mkdir -p ~/.ssh
                ssh-keyscan -H git.int.avast.com > ~/.ssh/known_hosts
                git config --global user.name "svc.github.deploy004"
                git config --global user.email "svc.github.deploy004@avast.com"

                nuidev-update-3rd-party-libs -- --githubToken=%system.GitHub.CommitStatusToken%
            """.trimIndent())
            param("default-shell-location", "/bin/bash")
            param("docker-image-name", "%dockerImagePath%")
            param("vagrantfile-do-override", "no")
        }
    }

    dependencies {
        snapshot(DockerImages.buildTypes.DockerImages_Node14) {}

        MODULES_WITH_3RD_PARTY_LIBS.forEach {
          artifacts(AbsoluteId("WinDev_NitroUI_Av_Modules_" + nameToId(it))) {
              buildRule = lastSuccessful()
              artifactRules = "+:.3rdPartyLibs.json=>.artifactDependencies/" + it
          }
        }
    }

    features {
        sshAgent {
            id = "ssh-agent-build-feature"
            teamcitySshKey = "svc-github-deploy004"
        }
    }

    requirements {
        doesNotEqual("env.OS", "Windows_NT")
    }
})
