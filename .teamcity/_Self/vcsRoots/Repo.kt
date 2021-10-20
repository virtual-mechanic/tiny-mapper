package _Self.vcsRoots

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot

object Repo : GitVcsRoot({
    name = "repo"
    url = "git@git.int.avast.com:%repo%.git"
    branch = "%branch%"
    branchSpec = "%branchSpec%"
    authMethod = uploadedKey {
        uploadedKey = "svc-github-deploy004"
    }
})
