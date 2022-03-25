pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "Contas_do_Lar"

includeBuild("platforms")

include(":androidApp")
include(":shared")
include(":data")
include(":bills")
