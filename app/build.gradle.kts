import com.android.build.gradle.api.ApplicationVariant
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    id("com.android.application") version Versions.gradleBuildTool
    kotlin("android") version Versions.kotlin
    kotlin("kapt") version Versions.kotlin
    id("org.jlleitschuh.gradle.ktlint") version Versions.ktlintGradle
    id("com.google.android.gms.oss-licenses-plugin") version Versions.ossLicenses
    id("com.github.ben-manes.versions") version Versions.gradleVersions
    id("deploygate") version Versions.deploygate
    id("com.google.gms.google-services") version Versions.googleServices apply false
}

// Manifest version
val versionMajor = 1
val versionMinor = 0
val versionPatch = 0

android {
    compileSdk = Versions.compileSdk

    buildFeatures {
        dataBinding = true
    }

    defaultConfig {
        applicationId = "io.github.droidkaigi.confsched2018"
        minSdk = Versions.minSdk
        targetSdk = Versions.targetSdk
        versionCode = versionMajor * 10000 + versionMinor * 100 + versionPatch
        versionName = "$versionMajor.$versionMinor.$versionPatch"
        testInstrumentationRunner = "io.github.droidkaigi.confsched2018.test.TestAppRunner"
        vectorDrawables.useSupportLibrary = true
        multiDexEnabled = true
        resourceConfigurations.addAll(listOf("en", "ja"))

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf("room.schemaLocation" to "$projectDir/schemas")
            }
        }
    }
    applicationVariants.all(object : Action<ApplicationVariant> {
        override fun execute(variant: ApplicationVariant) {
            variant.resValue("string", "versionInfo", variant.versionName)
        }

    })
    signingConfigs {
        getByName("debug") {
            storeFile = rootProject.file("debug.keystore")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }
        create("release") {
            storeFile = rootProject.file("release.keystore")
            storePassword = System.getenv("ANDROID_KEYSTORE_PASSWORD")
            keyAlias = System.getenv("ANDROID_KEYSTORE_ALIAS")
            keyPassword = System.getenv("ANDROID_KEYSTORE_PRIVATE_KEY_PASSWORD")
        }
    }
    buildTypes {
        getByName("debug") {
            manifestPlaceholders += mapOf("scheme" to "conference", "host" to "droidkaigi.co.jp" +
                    ".debug")
            resValue("string", "app_name", "DroidKaigi 2018 Dev")
            signingConfig = signingConfigs.getByName("debug")
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
        }
        getByName("release") {
            manifestPlaceholders += mapOf("scheme" to "conference", "host" to "droidkaigi.co.jp")
            resValue("string", "app_name", "DroidKaigi 2018")
            signingConfig = signingConfigs.getByName("release")
            isDebuggable = false
            isMinifyEnabled = true
            proguardFile(getDefaultProguardFile("proguard-android.txt"))
            // global proguard settings
            proguardFile(file("proguard-rules.pro"))
            // library proguard settings
            val files = rootProject.file("proguard")
                    .listFiles()
                    .filter { it.name.startsWith("proguard") }
                    .toTypedArray()
            proguardFiles(*files)
        }
    }
    testOptions {
        unitTests.isReturnDefaultValues = true
        unitTests.isIncludeAndroidResources = true
    }
    lint {
        lintConfig = file("lint.xml")
        textReport = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

kapt {
    useBuildCache = true
}

dependencies {

    implementation(project(":model"))

    // Kotlin
    implementation(Depends.Kotlin.stdlib)
    implementation(Depends.Kotlin.coroutines) // added
    implementation(Depends.Kotlin.coroutinesRx2) // added
    implementation(Depends.AndroidX.core)

//==================== Network ====================
    implementation(Depends.OkHttp3.client)
    implementation(Depends.OkHttp3.loggingIntercepter)

    implementation(Depends.Retrofit.core)
    implementation(Depends.Retrofit.converterMoshi)
    implementation(Depends.Retrofit.adapterRxJava2)

//==================== AndroidX ====================

    implementation(Depends.AndroidX.fragment)
    implementation(Depends.AndroidX.activity)
    implementation(Depends.AndroidX.livedata)
    implementation(Depends.AndroidX.viewModel)
    implementation(Depends.AndroidX.reactivestreams)
    implementation(Depends.AndroidX.browser)
    implementation(Depends.AndroidX.constraint)
    implementation(Depends.AndroidX.emoji)
    implementation(Depends.AndroidX.Room.runtime)
    implementation(Depends.AndroidX.Room.rxjava2)
    kapt(Depends.AndroidX.Room.compiler)

//==================== Structure ====================
    implementation(Depends.Kotshi.api)
    kapt(Depends.Kotshi.compiler)

    implementation(Depends.material)

    implementation(Depends.RxJava2.core)
    implementation(Depends.RxJava2.android)
    implementation(Depends.RxJava2.kotlin)
    implementation(Depends.rxbroadcast)

    implementation(Depends.Dagger.core)
    implementation(Depends.Dagger.android)
    implementation(Depends.Dagger.androidSupport)
    kapt(Depends.Dagger.compiler)
    kapt(Depends.Dagger.androidProcessor)

    implementation(Depends.PlayService.map)

    implementation(Depends.Firebase.firestore)
    implementation(Depends.Firebase.auth)
    implementation(Depends.Firebase.analytics)
    implementation(Depends.Firebase.messaging)

    implementation(Depends.threetenabp)

    implementation(Depends.Kotpref.kotpref)
    implementation(Depends.Kotpref.initializer)
    implementation(Depends.Kotpref.enumSupport)

//==================== UI ====================
    implementation(Depends.Glide.core)
    implementation(Depends.Glide.okhttp3)
    kapt(Depends.Glide.compiler)

    implementation(Depends.Groupie.core)
    implementation(Depends.Groupie.binding)

    implementation(Depends.downloadableCalligraphy)
    implementation(Depends.PlayService.oss)

//==================== Debug ====================
    debugImplementation(Depends.Stetho.core)
    debugImplementation(Depends.Stetho.okhttp3)

    implementation(Depends.timber)

    // see: https://github.com/google/guava/blob/master/futures/listenablefuture9999/pom.xml#L14
    implementation(Depends.guavaListenableFutureOnly)

    debugImplementation(Depends.leakcanary)

    debugImplementation(Depends.Debot.core)
    releaseImplementation(Depends.Debot.noop)

//==================== Test ====================
    testImplementation(Depends.junit)
    testImplementation(Depends.mockitoKotlin)

    testImplementation(Depends.Robolectric.core)
    testImplementation(Depends.Robolectric.multidex)
    testImplementation(Depends.assertk)

    testImplementation(Depends.threetenbp)

    androidTestImplementation(Depends.AndroidX.runner)
    androidTestImplementation(Depends.AndroidX.espresso)
    androidTestImplementation(Depends.AndroidX.contrib)
    androidTestImplementation(Depends.assertk)
}

repositories {
    mavenCentral()
}

ktlint {
    version.set(Versions.ktlint)
    android.set(true)
    reporters {
        reporter(ReporterType.CHECKSTYLE)
    }
    ignoreFailures.set(true)
}

apply(mapOf("plugin" to "com.google.gms.google-services"))
