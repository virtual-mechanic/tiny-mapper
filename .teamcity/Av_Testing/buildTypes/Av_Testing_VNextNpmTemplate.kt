package Av_Testing.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.PullRequests
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.commitStatusPublisher
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.pullRequests
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.sshAgent
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

object Av_Testing_VNextNpmTemplate : Template({
    name = "Testing vNext NPM template"

    artifactRules = "nitroUi/__resources"

    params {
        param("repo", "")
        param("branch", "refs/heads/master")
        param("branchSpec", "+:*")
        param("dockerImagePath", "%dep.WinDev_NitroUI_DockerImages_Node14.image_path%")
        param("dnba", "")
        param("customBranchToBuild", "%teamcity.pullRequest.source.branch%")
    }

    vcs {
        root(_Self.vcsRoots.Repo)
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
        sshAgent {
            id = "ssh-agent-build-feature"
            teamcitySshKey = "svc-github-deploy004"
        }
    }

    dependencies {
        snapshot(DockerImages.buildTypes.DockerImages_Node11) {}
        snapshot(DockerImages.buildTypes.DockerImages_Node14) {}
    }

    requirements {
        doesNotEqual("env.OS", "Windows_NT")
    }

    disableSettings("RQ_1264")

    steps {
        step {
            name = "Build NPM packages"
            id = "testAndBuildStep"
            type = "jonnyzzz.vm"
            param("docker-commandline", "-v ${'$'}HOME:${'$'}HOME -v ${'$'}SSH_AUTH_SOCK:/tmp/ssh_auth_sock -e SSH_AUTH_SOCK=/tmp/ssh_auth_sock")
            param("vm", "docker")
            param("docker-mount-mode", "rw")
            param("default-shell-location", "/bin/bash")
            param("docker-image-name", "%dep.WinDev_NitroUI_DockerImages_Node11.image_path%")
            param("vagrantfile-do-override", "no")
            param("script", """
                mkdir -p ~/.ssh
                ssh-keyscan -H git.int.avast.com >> ~/.ssh/known_hosts
                git config --global user.name "svc.github.deploy004"
                git config --global user.email "svc.github.deploy004@avast.com"

                set -e -E
                git clone git@git.int.avast.com:ui/nitro-auth.git nitroAuth
                cd nitroAuth
                # this can failed, so just stay on master branch
                set +e +E
                git checkout %customBranchToBuild%
                set -e -E
                npm install
                npm run prepublishOnly
                cd ..

                git clone git@git.int.avast.com:ff/nitro-ui-lib-licensing.git vNext
                cd vNext
                # this can failed, so just stay on master branch
                set +e +E
                git checkout %customBranchToBuild%
                set -e -E
                npm install
                cp -fR ../nitroAuth/ ./node_modules/\@avast/nitro-auth/
                npm run prepublishOnly
                cd ..

                git clone git@git.int.avast.com:ui/nitro-menu.git nitroMenu
                cd nitroMenu
                # this can failed, so just stay on master branch
                set +e +E
                git checkout %customBranchToBuild%
                set -e -E
                npm install
                cp -fR ../nitroAuth/ ./node_modules/\@avast/nitro-auth/
                cp -fR ../vNext/ ./node_modules/\@avast/nitro-ui-lib-licensing/
                npm run prepublishOnly
                cd ..
            """.trimIndent())
        }
        step {
            name = "Build NitroUI modules"
            id = "BuildNitroUiModules"
            type = "jonnyzzz.vm"
            param("docker-commandline", "-v ${'$'}HOME:${'$'}HOME -v ${'$'}SSH_AUTH_SOCK:/tmp/ssh_auth_sock -e SSH_AUTH_SOCK=/tmp/ssh_auth_sock")
            param("vm", "docker")
            param("docker-mount-mode", "rw")
            param("default-shell-location", "/bin/bash")
            param("docker-image-name", "%dep.WinDev_NitroUI_DockerImages_Node14.image_path%")
            param("vagrantfile-do-override", "no")
            param("script", """
                mkdir -p ~/.ssh
                ssh-keyscan -H git.int.avast.com >> ~/.ssh/known_hosts
                git config --global user.name "svc.github.deploy004"
                git config --global user.email "svc.github.deploy004@avast.com"

                set -e -E
                mkdir __resources

                git clone git@git.int.avast.com:ff/nitro-ui.git nitroUi
                cd nitroUi
                # this can failed, so just stay on master branch
                set +e +E
                git checkout %customBranchToBuild%
                set -e -E

                # dev
                cd dev
                npm update
                npm install
                npm link

                # root
                cd ..
                npm update
                npm install

                # prepare modules to build without installing npm packages
                # napiExtensions
                cd napiExtensions
                npm install
                cp -fR ../../nitroAuth/ ./node_modules/\@avast/nitro-auth/
                cd ..

                # myLicenses
                cd myLicenses
                npm install
                cp -fR ../../vNext/ ./node_modules/\@avast/nitro-ui-lib-licensing/
                cd ..

                # mainLayout
                cd mainLayout
                npm install
                cp -fR ../../nitroAuth/ ./node_modules/\@avast/nitro-auth/
                cp -fR ../../nitroMenu/ ./node_modules/\@avast/nitro-menu/
                cp -fR ../../vNext/ ./node_modules/\@avast/nitro-ui-lib-licensing/
                cd ..

                # smartHome
                cd smartHome
                npm install
                cp -fR ../../nitroAuth/ ./node_modules/\@avast/nitro-auth/
                cd ..

                # darkWebMonitor
                cd darkWebMonitor
                npm install
                cp -fR ../../nitroAuth/ ./node_modules/\@avast/nitro-auth/
                cd ..

                nuidev-build-modules -- --include=napiExtensions,myLicenses,mainLayout,smartHome,darkWebMonitor --copyTo="${'$'}(pwd)/__resources" --format=esm --buildType=stage --githubToken=%system.GitHub.CommitStatusToken%

            """.trimIndent())
        }
    }
})
