package _Self.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.sshAgent
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script

object BuildTemplate : Template({
    name = "Build template"

    artifactRules = """
        deploy => deploy
        nuget => nuget
    """.trimIndent()
    buildNumberPattern = "%version%.%build.counter%"

    params {
        param("product", "")
        param("skipPublish", "false")
        param("avedition", "Consumer")
        param("json", "")
        param("avbranding", "Avast")
        param("nuget", "true")
        param("version", "1.0")
        param("repo", "ff/nitro-ui-control-file")
        param("branch", "refs/heads/master")
        param("branchSpec", "")
    }

    vcs {
        root(_Self.vcsRoots.Repo)

        cleanCheckout = true
    }

    steps {
        step {
            name = "Build package"
            id = "RUNNER_3100"
            type = "jonnyzzz.vm"
            param("vm", "docker")
            param("script", """
                #!/bin/bash
                set -e -E

                apt-get update && apt-get install -y jq

                node -v
                npm -v
                npm update
                npm install
                npm run package -- --buildNumber=%build.number% --json=%json% --package=%product% --nuget=%nuget%
            """.trimIndent())
            param("docker-image-name", "%dep.WinDev_NitroUI_DockerImages_Node11.image_path%")
        }
        script {
            name = "Publish"
            id = "RUNNER_6278"

            conditions {
                doesNotEqual("skipPublish", "true")
            }
            scriptContent = """
                #!/bin/bash
                package="%product%"
                localdir="${'$'}(pwd)/deploy/*"

                rm -rf CDN-deploy
                mkdir -p ~/.ssh
                ssh-keyscan -H git.int.avast.com > ~/.ssh/known_hosts
                git config --global user.name "svc.github.deploy004"
                git config --global user.email "svc.github.deploy004@avast.com"
                git clone git@git.int.avast.com:ff/nitro-ui-deploy.git CDN-deploy
                echo "RELEASE TO: ${'$'}{package}"
                mkdir -p CDN-deploy/${'$'}{package} && cp -R ${'$'}{localdir} CDN-deploy/${'$'}{package}
                cd CDN-deploy
                git add .
                git commit -m 'Deploy'
                git push -u origin master
                if [ ${'$'}? -ne 0 ]; then
                  git pull
                     git push -u origin master
                fi

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
                                    "pattern": "nuget/(*).nupkg",
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
            param("org.jfrog.artifactory.selectedDeployableServer.targetRepo", "")
        }
    }

    features {
        sshAgent {
            id = "ssh-agent-build-feature"
            teamcitySshKey = "svc-github-deploy004"
        }
    }

    dependencies {
        snapshot(DockerImages.buildTypes.DockerImages_Node11) {}
    }

    requirements {
        doesNotEqual("env.OS", "Windows_NT")
    }

    disableSettings("RQ_1264")
})
