object Depends {
    object Kotlin {
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    }

    object OkHttp3 {
        const val client = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
        const val loggingIntercepter = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
    }

    object Retrofit {
        const val core = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val converterMoshi = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
        const val adapterRxJava2 = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    }

    object Kotshi {
        const val api = "se.ansman.kotshi:api:${Versions.kotshi}"
        const val compiler = "se.ansman.kotshi:compiler:${Versions.kotshi}"
    }

    object AndroidX {
        const val core = "androidx.core:core-ktx:1.7.0"
        const val fragment = "androidx.fragment:fragment-ktx:1.4.0"
        const val activity = "androidx.activity:activity-ktx:1.4.0"
        const val viewModel =  "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
        const val lifecycle = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
        const val reactivestreams =  "androidx.lifecycle:lifecycle-reactivestreams-ktx:${Versions.lifecycle}"
        const val browser = "androidx.browser:browser:1.4.0"
        const val constraint = "androidx.constraintlayout:constraintlayout:2.1.2"
        const val emoji = "androidx.emoji:emoji-appcompat:1.1.0"

        object Room {
            const val runtime = "androidx.room:room-runtime:${Versions.room}"
            const val rxjava2 = "androidx.room:room-rxjava2:${Versions.room}"
            const val compiler = "androidx.room:room-compiler:${Versions.room}"
        }

        const val runner = "androidx.test:runner:1.4.0"
        const val espresso = "androidx.test.espresso:espresso-core:3.4.0"
        const val contrib = "androidx.test.espresso:espresso-contrib:3.4.0"
    }

    const val material = "com.google.android.material:material:1.4.0"

    object RxJava2 {
        const val core = "io.reactivex.rxjava2:rxjava:2.2.21"
        const val android = "io.reactivex.rxjava2:rxandroid:2.1.1"
        const val kotlin = "io.reactivex.rxjava2:rxkotlin:2.4.0"
    }

    const val rxbroadcast = "com.cantrowitz:rxbroadcast:2.0.0"

    object Dagger {
        const val core = "com.google.dagger:dagger:${Versions.dagger}"
        const val compiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
        const val android = "com.google.dagger:dagger-android:${Versions.dagger}"
        const val androidSupport = "com.google.dagger:dagger-android-support:${Versions.dagger}"
        const val androidProcessor = "com.google.dagger:dagger-android-processor:${Versions.dagger}"
    }

    object PlayService {
        const val map = "com.google.android.gms:play-services-maps:18.0.2"
        const val oss = "com.google.android.gms:play-services-oss-licenses:17.0.0"
    }

    object Firebase {
        const val firestore = "com.google.firebase:firebase-firestore:24.0.0"
        const val auth = "com.google.firebase:firebase-auth:21.0.1"
        const val analytics = "com.google.firebase:firebase-analytics-ktx:20.0.2"
        const val messaging = "com.google.firebase:firebase-messaging:23.0.0"
    }

    const val threetenabp = "com.jakewharton.threetenabp:threetenabp:1.3.1"

    object Kotpref {
        const val kotpref = "com.chibatching.kotpref:kotpref:${Versions.kotpref}"
        const val initializer = "com.chibatching.kotpref:initializer:${Versions.kotpref}"
        const val enumSupport = "com.chibatching.kotpref:enum-support:${Versions.kotpref}"
    }

    object Glide {
        val core = "com.github.bumptech.glide:glide:${Versions.glide}"
        val okhttp3 = "com.github.bumptech.glide:okhttp3-integration:${Versions.glide}"
        val compiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
    }

    object Groupie {
        const val core = "com.github.lisawray.groupie:groupie:${Versions.groupie}"
        const val binding = "com.github.lisawray.groupie:groupie-databinding:${Versions.groupie}"
    }

    const val downloadableCalligraphy =
            "com.github.takahirom.downloadable.calligraphy:downloadable-calligraphy:0.1.5"

    object Stetho {
        const val core = "com.facebook.stetho:stetho:${Versions.stetho}"
        const val okhttp3 = "com.facebook.stetho:stetho-okhttp3:${Versions.stetho}"
    }

    const val timber = "com.jakewharton.timber:timber:5.0.1"
    const val leakcanary = "com.squareup.leakcanary:leakcanary-android:2.8.1"

    object Debot {
        const val core = "com.github.tomoima525.debot:debot:${Versions.debot}"
        const val noop = "com.github.tomoima525.debot:debot-no-op:${Versions.debot}"
    }

    const val junit = "junit:junit:4.12"
    const val mockitoKotlin = "org.mockito.kotlin:mockito-kotlin:3.2.0"

    object Robolectric {
        const val core = "org.robolectric:robolectric:${Versions.robolectric}"
        const val multidex = "org.robolectric:shadows-multidex:${Versions.robolectric}"
    }

    const val assertk = "com.willowtreeapps.assertk:assertk:0.25"
    const val threetenbp = "org.threeten:threetenbp:1.5.2"

    const val guavaListenableFutureOnly =
            "com.google.guava:listenablefuture:9999.0-empty-to-avoid-conflict-with-guava"
}
