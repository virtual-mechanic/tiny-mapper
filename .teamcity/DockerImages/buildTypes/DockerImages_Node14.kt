package DockerImages.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.dockerSupport

object DockerImages_Node14 : BuildType({
    name = "Docker image Node 14"

    params {
        param("image_path", "")
        param("repo", "ui/nitro-ui-docker-file")
        param("branch", "refs/heads/master")
        param("branchSpec", "")
    }

    vcs {
        root(_Self.vcsRoots.Repo)
    }

    steps {
        step {
            name = "Docker Build"
            type = "BuildDockerImage"
            param("image_name", "nitroui/node14")
            param("no_cache", "true")
            param("docker_url", "docker.ida.avast.com/avast")
        }
    }

    features {
        dockerSupport {
            cleanupPushedImages = true
            loginToRegistry = on {
                dockerRegistryId = "PROJECT_EXT_37"
            }
        }
    }
})
