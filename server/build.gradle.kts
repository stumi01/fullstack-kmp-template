import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform")

    id("kotlinx-serialization")
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("com.squareup.sqldelight")
    application
}

kotlin {
    jvm {
        withJava()
    }
    sourceSets {
        named("jvmMain") {
            kotlin.srcDir("kotlin")
            resources.srcDir("resources")
            dependencies {
                implementation(project(":models"))
                implementation(kotlin("stdlib-jdk8", Versions.kotlin))
                implementation(Deps.SqlDelight.runtime)
                implementation(Deps.SqlDelight.sqliteDriver)

                implementation(Deps.Ktor.serverNetty)
                implementation(Deps.Ktor.serialization)
                implementation(Deps.Ktor.locations)

                implementation(Deps.Log.logback)
                implementation(Deps.Koin.core)

                implementation(Deps.Kotlinx.coroutinesCore)

                implementation(Deps.Ktor.clientCoreJvm)
                implementation(Deps.Ktor.clientJsonJvm)
                implementation(Deps.Ktor.clientLoggingJvm)
                implementation(Deps.Ktor.clientSerializationJvm)
                implementation(Deps.Ktor.clientOkhttp)
            }
        }
    }
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
}


application {
    mainClassName = "io.ktor.server.netty.EngineMain"
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}


val run by tasks.getting(JavaExec::class) {
    // wire env variables from config : environment("ENV_VAR_NAME", getConfigField("config_field_name"))


}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

fun JavaExec.getConfigField(fieldName: String): String {
    return gradleLocalProperties(rootDir).getProperty(fieldName) ?: (project.findProperty(fieldName)
        ?: System.getenv(fieldName)).toString()
}

sqldelight {
    database("TemplateoDB") {
        packageName = "com.bencestumpf.template.db"
    }
}

tasks.create("stage") {
    dependsOn("shadowJar", "clean")
}

distributions {
    main {
        contents {
            from("$buildDir/libs") {
                rename("${rootProject.name}-jvm", rootProject.name)
                into("lib")
            }
        }
    }
}
tasks.withType<Jar> {
    manifest {
        attributes(
            mapOf(
                "Main-Class" to application.mainClassName
            )
        )
    }
}

tasks {
    val shadowJarTask =
        named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
            // explicitly configure the filename of the resulting UberJar
            //archiveFileName.set(uberJarFileName)

            // Appends entries in META-INF/services resources into a single resource. For example, if there are several
            // META-INF/services/org.apache.maven.project.ProjectBuilder resources spread across many JARs the individual
            // entries will all be concatenated into a single META-INF/services/org.apache.maven.project.ProjectBuilder
            // resource packaged into the resultant JAR produced by the shading process -
            // Effectively ensures we bring along all the necessary bits from Jetty
            mergeServiceFiles()

            // As per the App Engine java11 standard environment requirements listed here:
            // https://cloud.google.com/appengine/docs/standard/java11/runtime
            // Your Jar must contain a Main-Class entry in its META-INF/MANIFEST.MF metadata file
            manifest {
                attributes(mapOf("Main-Class" to application.mainClassName))
            }
        }

    // because we're using shadowJar, this task has limited value
    named("jar") {
        enabled = false
    }

    // update the `assemble` task to ensure the creation of a brand new UberJar using the shadowJar task
    named("assemble") {
        dependsOn(shadowJarTask)
    }
}
