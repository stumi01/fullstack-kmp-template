pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}


rootProject.name = "fullstack-kmp-template"
include(":androidApp")
include(":desktopApp")
include(":jsApp")
include(":sharedCompose")
include(":shared")
include(":server")
include(":models")
