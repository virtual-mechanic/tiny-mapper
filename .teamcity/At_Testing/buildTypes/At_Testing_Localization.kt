package At_Testing.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.PullRequests
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.pullRequests
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

object At_Testing_Localization : BuildType({
    templates(_Self.buildTypes.TestingTemplate)
    name = "AntiTrack localization"

    artifactRules = "dist/locale"

    params {
        param("repo", "ff/nitro-ui-lib-localization-antitrack")
    }

    steps {
        step {
            id = "testAndBuildStep"
            name = "build"
            type = "jonnyzzz.vm"
            param("docker-commandline", "-v ${'$'}HOME:${'$'}HOME")
            param("vm", "docker")
            param("docker-mount-mode", "rw")
            param("script", """
                #!/bin/bash

                set -e -E

                if [[ "${'$'}(git log -1 --pretty=%B)" =~ " NO_CI"${'$'} ]]; then
                  echo "CI skipped"
                  exit 1
                fi

                npm install
                npm run package -- --buildNumber=TESTING
            """.trimIndent())
            param("default-shell-location", "/bin/bash")
            param("docker-image-name", "%dep.WinDev_NitroUI_DockerImages_Node11.image_path%")
            param("vagrantfile-do-override", "no")
        }
    }

    features {
        pullRequests {
            id = "FEATURE_pullRequests"
            vcsRootExtId = "WinDev_NitroUI_Repo"
            provider = github {
                authType = token {
                    token = "%system.GitHub.CommitStatusToken%"
                }
                filterTargetBranch = """
                    +:refs/heads/master
                    +:refs/heads/stage
                """.trimIndent()
                filterAuthorRole = PullRequests.GitHubRoleFilter.MEMBER
            }
        }
    }

    dependencies {
        snapshot(DockerImages.buildTypes.DockerImages_Node11) {}
    }
})

