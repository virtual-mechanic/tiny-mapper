package Dp_Weirdos.buildTypes

import Dp_Modules.buildTypes.Dp_Modules_PasswordChanger
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.notifications
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.dockerCommand
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.schedule
import jetbrains.buildServer.configs.kotlin.v2019_2.ui.*

object Dp_Weirdos_DbrsAutomationTests : BuildType({
    name = "breachguard-scenarios-tests"

    params {
        param("env.SCRAPPER_ALLOWED_SCENARIO_GEOS", """["ALL"]""")
        param("env.LOGGER_ALLOWED_TYPES", """["CaptchaCommand","Scenario","PostRequest","TemplateBuilder", "ScrapperConsole"]""")
        param("env.SCRAPPER_EXCLUDE_EMAIL_VERIFICATION", "true")
        param("env.SCRAPPER_ALLOWED_SCENARIO_NAMES", "[]")
        param("env.SCRAPPER_EXCLUDED_SCENARIO_NAMES", """["Oracle"]""")
        param("env.API_ENDPOINT", "identityprotection.avast.com")
        param("env.PUPPETEER_DEFAULT_TIMEOUT", "600000")
        param("env.SCRAPPER_SERVICE_CONFIG_ARGUMENTS", """{"address": "320 Cranbury Half Acre Rd", "city": "Cranbury", "email": "randybgomez@armyspy.com", "firstName": "Randy", "lastName": "Gomez", "phone": "8622584708", "state": "New Jersey", "zip": "08512", "birthDateDay": "17", "birthDateMonth": "4", "birthDateYear": "1941", "country": "US"}""")
        param("env.SCRAPPER_SCENARIO_TIMEOUT", "600000")
        param("repo", "dp/breachguard-scenarios-tests")
        param("branch", "master")
        param("branchSpec", "")
    }

    vcs {
        root(_Self.vcsRoots.Repo)
    }

    steps {
        script {
            name = "Update Dockerfile ENV Variables"
            scriptContent = """
                addEnvVariable() {
                  sed -i "/#BUILD ENV VARIABLES/aENV ${'$'}1='${'$'}2'" ./Dockerfile
                }

                addEnvVariable 'API_ENDPOINT' '%env.API_ENDPOINT%'
                addEnvVariable 'PUPPETEER_DEFAULT_TIMEOUT' '%env.PUPPETEER_DEFAULT_TIMEOUT%'
                addEnvVariable 'SCRAPPER_SCENARIO_TIMEOUT' '%env.SCRAPPER_SCENARIO_TIMEOUT%'
                addEnvVariable 'SCRAPPER_ALLOWED_SCENARIO_GEOS' '%env.SCRAPPER_ALLOWED_SCENARIO_GEOS%'
                addEnvVariable 'SCRAPPER_ALLOWED_SCENARIO_NAMES' '%env.SCRAPPER_ALLOWED_SCENARIO_NAMES%'
                addEnvVariable 'SCRAPPER_EXCLUDED_SCENARIO_NAMES' '%env.SCRAPPER_EXCLUDED_SCENARIO_NAMES%'
                addEnvVariable 'SCRAPPER_EXCLUDE_EMAIL_VERIFICATION' '%env.SCRAPPER_EXCLUDE_EMAIL_VERIFICATION%'
                addEnvVariable 'LOGGER_ALLOWED_TYPES' '%env.LOGGER_ALLOWED_TYPES%'
                addEnvVariable 'SCRAPPER_SERVICE_CONFIG_ARGUMENTS' '%env.SCRAPPER_SERVICE_CONFIG_ARGUMENTS%'

                cat ./Dockerfile
            """.trimIndent()
            param("org.jfrog.artifactory.selectedDeployableServer.downloadSpecSource", "Job configuration")
            param("org.jfrog.artifactory.selectedDeployableServer.useSpecs", "false")
            param("org.jfrog.artifactory.selectedDeployableServer.uploadSpecSource", "Job configuration")
        }
        dockerCommand {
            commandType = build {
                source = file {
                    path = "Dockerfile"
                }
                commandArgs = "--pull"
            }
            param("dockerImage.platform", "linux")
        }
    }

    triggers {
        schedule {
            schedulingPolicy = daily {
                hour = 3
            }
            branchFilter = ""
            triggerBuild = always()
            withPendingChangesOnly = false
        }
    }

    failureConditions {
        supportTestRetry = true
    }

    features {
        notifications {
            notifierSettings = emailNotifier {
                email = "jiri.pojezny@avast.com"
            }
            buildFailed = true
        }
    }

    dependencies {
        artifacts(Dp_Modules_PasswordChanger) {
            buildRule = lastSuccessful()
            cleanDestination = true
            artifactRules = "scraper.js=>./res"
        }
    }

    requirements {
        contains("teamcity.agent.name", "cloud-ubuntu-20.04-docker-WIERDO")
    }
})

