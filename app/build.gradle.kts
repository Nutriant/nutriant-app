import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.dicoding.nutrient"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.dicoding.nutrient"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val properties = gradleLocalProperties(rootDir)
        buildConfigField("String", "BASE_API_URL_LARAVEL", "\"${properties.get("BASE_API_URL_LARAVEL")}\"")
        buildConfigField("String", "BASE_API_NEWS_URL", "\"${properties.get("BASE_API_URL_NEWS_ARTICLES")}\"")
        buildConfigField("String", "TOKEN_NEWS_URL", "\"${properties.get("NEWS_ARTICLES_TOKEN")}\"")
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    val cameraxVersion = "1.3.0"

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.activity:activity:1.8.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    // Camera X
    implementation("androidx.camera:camera-camera2:$cameraxVersion")
    implementation("androidx.camera:camera-lifecycle:$cameraxVersion")
    implementation("androidx.camera:camera-view:$cameraxVersion")

    // Viewpager
    implementation("androidx.viewpager2:viewpager2:1.1.0")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")

    // Circle Image View
    implementation ("de.hdodenhof:circleimageview:3.1.0")

    // datastore
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    // extra viewmodel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.1")

    // Sweet alert
    implementation("com.github.f0ris.sweetalert:library:1.6.2")
}