object Depends {
    object Kotlin {
        val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    }

    object OkHttp3 {
        val loggingIntercepter = "com.squareup.okhttp3:logging-interceptor:3.9.1"
    }

    object Retrofit {
        val core = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        val converterMoshi = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
        val adapterRxJava2 = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    }

    object Kotshi {
        val api = "se.ansman.kotshi:api:${Versions.kotshi}"
        val compiler = "se.ansman.kotshi:compiler:${Versions.kotshi}"
    }

    object AndroidX {
        const val core = "androidx.core:core-ktx:1.5.0-rc02"
        const val fragment = "androidx.fragment:fragment-ktx:1.3.3"
        const val activity = "androidx.activity:activity-ktx:1.2.3"
        const val viewModel =  "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
        const val lifecycle = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
        const val reactivestreams =  "androidx.lifecycle:lifecycle-reactivestreams-ktx:${Versions.lifecycle}"
        const val browser = "androidx.browser:browser:1.3.0"
        const val constraint = "androidx.constraintlayout:constraintlayout:2.0.4"
        const val emoji = "androidx.emoji:emoji-appcompat:1.1.0"

        object Room {
            const val runtime = "androidx.room:room-runtime:${Versions.room}"
            const val rxjava2 = "androidx.room:room-rxjava2:${Versions.room}"
            const val compiler = "androidx.room:room-compiler:${Versions.room}"
        }
    }

    const val material = "com.google.android.material:material:1.3.0"

    object RxJava2 {
        val core = "io.reactivex.rxjava2:rxjava:2.1.9"
        val android = "io.reactivex.rxjava2:rxandroid:2.0.1"
        val kotlin = "io.reactivex.rxjava2:rxkotlin:2.2.0"
    }

    val rxbroadcast = "com.cantrowitz:rxbroadcast:2.0.0"

    object Binding {
        val compiler = "com.android.databinding:compiler:3.0.1"
    }

    object Dagger {
        val core = "com.google.dagger:dagger:${Versions.dagger}"
        val compiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
        val android = "com.google.dagger:dagger-android:${Versions.dagger}"
        val androidSupport = "com.google.dagger:dagger-android-support:${Versions.dagger}"
        val androidProcessor = "com.google.dagger:dagger-android-processor:${Versions.dagger}"
    }

    object PlayService {
        val map = "com.google.android.gms:play-services-maps:${Versions.firebase}"
        val oss = "com.google.android.gms:play-services-oss-licenses:${Versions.firebase}"
    }

    object Firebase {
        val firestore = "com.google.firebase:firebase-firestore:${Versions.firebase}"
        val auth = "com.google.firebase:firebase-auth:${Versions.firebase}"
        val core = "com.google.firebase:firebase-core:${Versions.firebase}"
        val messaging = "com.google.firebase:firebase-messaging:${Versions.firebase}"
    }

    val threetenabp = "com.jakewharton.threetenabp:threetenabp:1.0.5"

    object Kotpref {
        val kotpref = "com.chibatching.kotpref:kotpref:${Versions.kotpref}"
        val initializer = "com.chibatching.kotpref:initializer:${Versions.kotpref}"
        val enumSupport = "com.chibatching.kotpref:enum-support:${Versions.kotpref}"
    }

    object Glide {
        val core = "com.github.bumptech.glide:glide:${Versions.glide}"
        val okhttp3 = "com.github.bumptech.glide:okhttp3-integration:${Versions.glide}"
        val compiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
    }

    object Groupie {
        val core = "com.xwray:groupie:${Versions.groupie}"
        val binding = "com.xwray:groupie-databinding:${Versions.groupie}"
    }

    val downloadableCalligraphy = "com.github.takahirom.downloadable.calligraphy:downloadable-calligraphy:0.1.2"

    object Stetho {
        val core = "com.facebook.stetho:stetho:${Versions.stetho}"
        val okhttp3 = "com.facebook.stetho:stetho-okhttp3:${Versions.stetho}"
    }

    val crashlytics = "com.crashlytics.sdk.android:crashlytics:2.8.0@aar"
    val timber = "com.jakewharton.timber:timber:4.6.0"
    val leakcanary = "com.squareup.leakcanary:leakcanary-android:1.5.4"

    object Debot {
        val core = "com.tomoima.debot:debot:${Versions.debot}"
        val noop = "com.tomoima.debot:debot-no-op:${Versions.debot}"
    }

    val junit = "junit:junit:4.12"
    val mockitoKotlin = "com.nhaarman:mockito-kotlin:1.5.0"

    object Robolectric {
        val core = "org.robolectric:robolectric:${Versions.robolectric}"
        val multidex = "org.robolectric:shadows-multidex:${Versions.robolectric}"
    }

    val assertk = "com.willowtreeapps.assertk:assertk:0.9"
    val threetenbp = "org.threeten:threetenbp:1.3.6"

    object SupportTest {
        val runner = "com.android.support.test:runner:1.0.1"
        val espresso = "com.android.support.test.espresso:espresso-core:3.0.1"
        val contrib = "com.android.support.test.espresso:espresso-contrib:3.0.1"

    }
}
