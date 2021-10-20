package Dp_Npm.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script

object Dp_Npm_ScenariosRunner : BuildType({
    templates(_Self.buildTypes.NpmTemplate)
    name = "ScenariosRunner"
    description = "Browser extension for securing users privacy settings"

    buildNumberPattern = "%build.counter%"
    artifactRules = "**/scenarios-runner-*tgz"
    publishArtifacts = PublishMode.SUCCESSFUL

    params {
        param("repo", "dp/scenarios-runner")
        param("branch", "master")
        param("dockerImagePath", "%dep.WinDev_NitroUI_DockerImages_Node11.image_path%")
    }

    steps {
        script {
            name = "Pre-build"
            scriptContent = """
                #!/bin/sh

                mkdir -p /tmp/build
            """.trimIndent()
            param("org.jfrog.artifactory.selectedDeployableServer.downloadSpecSource", "Job configuration")
            param("org.jfrog.artifactory.selectedDeployableServer.useSpecs", "false")
            param("org.jfrog.artifactory.selectedDeployableServer.uploadSpecSource", "Job configuration")
        }
        step {
            name = "Build"
            id = "RUNNER_3100"
            type = "jonnyzzz.vm"
            param("vm", "docker")
            param("docker-mount-mode", "rw")
            param("script", """
                # install chromium from Artifactory mirrored repo
                apk update
                apk add curl ca-certificates

                # add avast-ca into system
                curl https://artifactory.ida.avast.com/artifactory/re-generic-local/avast-root-ca.crt -o /usr/local/share/ca-certificates/avast-root-ca.crt
                update-ca-certificates

                #ignore this script from build NPM package:
                echo "teamcity*.build.cmd" >> .npmignore

                echo "unsafe-perm = true
                email = re@avast.com
                @avast:registry=https://artifactory.ida.avast.com/artifactory/api/npm/npm-local/
                @hidemyass:registry=https://artifactory.ida.avast.com/artifactory/api/npm/npm-local/
                @babel:registry=https://artifactory.ida.avast.com/artifactory/api/npm/registry.npmjs.org/
                @types:registry=https://artifactory.ida.avast.com/artifactory/api/npm/registry.npmjs.org/
                registry=https://artifactory.ida.avast.com/artifactory/api/npm/npm-local/
                _auth = %system.windupusherNpmAccessToken%
                always-auth = true
                email = re@avast.com
                " >  ~/.npmrc

                rm package-lock.json

                npm uninstall @rollup/plugin-node-resolve @typescript-eslint/eslint-plugin @typescript-eslint/parser eslint ts-jest ts-node

                npm install && npm run build && npm pack && npm publish
            """.trimIndent())
            param("default-shell-location", "/bin/sh")
            param("docker-image-name", "docker.ida.avast.com/avast/re/node/node12:latest")
            param("vagrantfile-do-override", "no")
        }
    }
})

