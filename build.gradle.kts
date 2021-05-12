import org.gradle.api.tasks.Delete
import org.gradle.kotlin.dsl.maven
import org.gradle.kotlin.dsl.repositories

allprojects {
    repositories {
        google()
        mavenCentral()
        maven(url="https://plugins.gradle.org/m2/")
        maven(url="https://maven.fabric.io/public")
        maven(url="https://jitpack.io")
    }
}

task("clean", Delete::class) {
    delete = setOf(rootProject.buildDir)
}
