package _Self.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.PullRequests
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.commitStatusPublisher
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.pullRequests
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

object TestingTemplate : Template({
    name = "Testing template"

    artifactRules = "__resources"

    params {
        param("repo", "")
        param("branch", "refs/heads/master")
        param("branchSpec", "+:*")
        param("dockerImagePath", "%dep.WinDev_NitroUI_DockerImages_Node14.image_path%")
        param("dnba", "")
    }

    vcs {
        root(_Self.vcsRoots.Repo)
    }

    steps {
        step {
            id = "testAndBuildStep"
            name = "Run tests & build modules"
            type = "jonnyzzz.vm"
            param("docker-commandline", "-v ${'$'}HOME:${'$'}HOME")
            param("vm", "docker")
            param("docker-mount-mode", "rw")
            param("script", """
                #!/bin/bash

                set -e -E

                # exec your testing script here
            """.trimIndent())
            param("default-shell-location", "/bin/bash")
            param("docker-image-name", "%dockerImagePath%")
            param("vagrantfile-do-override", "no")
        }
    }

    triggers {
        vcs {
            id = "vcsTrigger"
            branchFilter = """
                +:refs/pull/*/head
            """.trimIndent()
        }
    }

    features {
        commitStatusPublisher {
            id = "FEATURE_commitStatusPublisher"
            vcsRootExtId = "WinDev_NitroUI_Repo"
            publisher = github {
                githubUrl = "https://git.int.avast.com/api/v3"
                authType = personalToken {
                    token = "%system.GitHub.CommitStatusToken%"
                }
            }
        }
        pullRequests {
            id = "FEATURE_pullRequests"
            vcsRootExtId = "WinDev_NitroUI_Repo"
            provider = github {
                authType = token {
                    token = "%system.GitHub.CommitStatusToken%"
                }
                filterTargetBranch = "+:refs/heads/master"
                filterAuthorRole = PullRequests.GitHubRoleFilter.MEMBER
            }
        }
    }

    dependencies {
        snapshot(DockerImages.buildTypes.DockerImages_Node14) {}
    }
})

