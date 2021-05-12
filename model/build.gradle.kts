import org.gradle.api.JavaVersion.VERSION_1_8
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.repositories
import org.gradle.kotlin.dsl.version

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath(kotlin("gradle-plugin", Versions.kotlin))
    }
}

plugins {
    id("java-library")
    kotlin("jvm") version Versions.kotlin
}
apply {
    plugin("kotlin")
}

dependencies {
    compile(kotlin("stdlib-jdk8", Versions.kotlin))
}
