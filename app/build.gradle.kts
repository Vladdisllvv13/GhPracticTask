plugins {
    id ("com.android.application")
    id ("kotlin-android")
    kotlin ("kapt")
}

android {
    compileSdkVersion (32)

    defaultConfig {
        applicationId = "com.example.icerockrepoapp"
        minSdkVersion (21)
        targetSdkVersion (32)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles (getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    val kotlinVersion = rootProject.extra["kotlin_version"]
    implementation ("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")

    implementation ("androidx.core:core-ktx:1.5.0")
    implementation ("androidx.appcompat:appcompat:1.3.1")
    implementation ("com.google.android.material:material:1.4.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.1")
    implementation ("androidx.legacy:legacy-support-v4:1.0.0")

    implementation ("androidx.navigation:navigation-fragment-ktx:2.3.5")
    implementation ("androidx.navigation:navigation-ui-ktx:2.3.5")


    val lifecycleVersion = rootProject.extra["lifecycle_version"]
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")

    val retrofitVersion = rootProject.extra["retrofit_version"]
    implementation ("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation ("com.squareup.retrofit2:converter-gson:$retrofitVersion")


    implementation ("org.commonmark:commonmark:0.17.2")

    testImplementation ("junit:junit:4.+")
    androidTestImplementation ("androidx.test.ext:junit:1.1.3")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.4.0")
}

kapt {
    correctErrorTypes = true
}
