package Dp_Modules.vcsRoots

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot

object Dp_Modules_AosRepo : GitVcsRoot({
    name = "https://git.int.avast.com/be/aos.git#refs/heads/master"
    url = "git@git.int.avast.com:be/aos.git"
    branch = "refs/heads/master"
    authMethod = uploadedKey {
        uploadedKey = "svc-github-deploy004"
    }
})

