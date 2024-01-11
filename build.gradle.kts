plugins {
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.google.play.services) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.ktlint.gradle) apply false
    alias(libs.plugins.moko.resources) apply false
    alias(libs.plugins.native.coroutines) apply false
    alias(libs.plugins.sqldelight) apply false
}

group = "io.github.elkhoudiry"
version = "dev"

subprojects {
    apply(plugin = rootProject.libs.plugins.ktlint.gradle.get().pluginId)

    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        filter {
            exclude("*.gradle.kts")
            exclude {
                 it.file.path.contains("${buildDir}/generated/")
            }
        }
    }

    tasks.withType<Test>().configureEach {
        if (!project.hasProperty("createReports")) {
            reports.html.required.set(false)
            reports.junitXml.required.set(false)
        }

        testLogging {
            showStandardStreams = true
            showCauses = true
            showExceptions = true
            showStackTraces = true
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}