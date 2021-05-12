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
    implementation(kotlin("stdlib-jdk8", Versions.kotlin))
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
