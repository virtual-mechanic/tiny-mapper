package Av_Testing.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

object Av_Testing_NitroUi : BuildType({
    templates(_Self.buildTypes.TestingTemplate)
    name = "nitro-ui"

    params {
        param("repo", "ff/nitro-ui")
    }

    steps {
        step {
            id = "testAndBuildStep"
            name = "Run tests & build modules"
            type = "jonnyzzz.vm"
            conditions {
                doesNotEqual("teamcity.pullRequest.source.branch", "snyk")
            }
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

                cd dev
                npm update
                npm install
                npm link

                nuidev-check-package-locks

                nuidev-generate-unused-lng-ids null true

                cd ..
                npm update
                npm install

                nuidev-run-tests --githubToken=%system.GitHub.CommitStatusToken% --pr=%teamcity.pullRequest.number%

                if [[ "%dnba%" != "false" && "${'$'}(git log -1 --pretty=%B)" =~ " DNBA"${'$'} ]]; then
                  nuidev-build-modules -- --dontBuildAll --copyTo="${'$'}{PWD}/__resources" --format=esm --buildType=stage --pr=%teamcity.pullRequest.number% --githubToken=%system.GitHub.CommitStatusToken%
                else
                  nuidev-build-modules -- --copyTo="${'$'}{PWD}/__resources" --format=esm --buildType=stage --pr=%teamcity.pullRequest.number% --githubToken=%system.GitHub.CommitStatusToken%
                fi
            """.trimIndent())
            param("default-shell-location", "/bin/bash")
            param("docker-image-name", "%dep.WinDev_NitroUI_DockerImages_Node14.image_path%")
            param("vagrantfile-do-override", "no")
        }
        step {
            id = "testAndBuildStep2"
            name = "Run tests & build modules (npm 7)"
            type = "jonnyzzz.vm"
            conditions {
                equals("teamcity.pullRequest.source.branch", "snyk")
            }
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

                export https_proxy=http://build-proxy-01.brq2.re.ida.avast.com:8080

                echo "node $(node -v)"
                echo "npm $(npm -v)"
                echo "snyk $(snyk -v)"

                npm install -g npm@8.0.0

                npm update --legacy-peer-deps -w dev
                npm install --legacy-peer-deps -w dev
                npm link -w dev

                nuidev-check-package-locks
                nuidev-snyk-test --snykToken=%snyk.apiToken%
                nuidev-run-tests --githubToken=%system.GitHub.CommitStatusToken% --pr=%teamcity.pullRequest.number%
                nuidev-generate-unused-lng-ids null true

                npm update -ws --legacy-peer-deps
                npm install -ws --legacy-peer-deps

                if [[ "%dnba%" != "false" && "${'$'}(git log -1 --pretty=%B)" =~ " DNBA"${'$'} ]]; then
                  nuidev-build-modules -- --dontBuildAll --copyTo="${'$'}{PWD}/__resources" --format=esm --buildType=stage --pr=%teamcity.pullRequest.number% --githubToken=%system.GitHub.CommitStatusToken%
                else
                  nuidev-build-modules -- --copyTo="${'$'}{PWD}/__resources" --format=esm --buildType=stage --pr=%teamcity.pullRequest.number% --githubToken=%system.GitHub.CommitStatusToken%
                fi
            """.trimIndent())
            param("default-shell-location", "/bin/bash")
            param("docker-image-name", "%dep.WinDev_NitroUI_DockerImages_Node14.image_path%")
            param("vagrantfile-do-override", "no")
        }
    }
})
