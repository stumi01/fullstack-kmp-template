buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven(uri("https://plugins.gradle.org/m2/"))
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
    dependencies {
        classpath(Deps.Gradle.kotlin)
        classpath(Deps.Gradle.android)
        classpath(Deps.Gradle.kotlinSerialization)
        classpath(Deps.Gradle.kotlinter)
        classpath("com.squareup.sqldelight:gradle-plugin:1.5.3")
        classpath(Deps.Gradle.compose)

    }
}

allprojects {
    apply(plugin = "org.jmailen.kotlinter")
    repositories {
        google()
        mavenCentral()
        maven(url = "https://maven.pkg.jetbrains.space/kotlin/p/kotlin/kotlin-js-wrappers")
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}
tasks.named<Wrapper>("wrapper") {
    gradleVersion = "7.2"
    distributionType = Wrapper.DistributionType.ALL
}

//tasks.register("clean", Delete::class) {
//    delete(rootProject.buildDir)
//}
