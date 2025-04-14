plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.compose.compiler)
    id(libs.plugins.hilt.plugin.get().pluginId)
    alias(libs.plugins.ksp)
}

android {
    kapt.includeCompileClasspath = true
    namespace = "dev.terryrockstar.example"
    compileSdk = 35

    defaultConfig {
        applicationId = "dev.terryrockstar.example"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
            enableUnitTestCoverage = true
            enableAndroidTestCoverage = true
        }
    }
    flavorDimensions += "store"
    productFlavors {
        create("playstore") {
            dimension = "store"
            buildConfigField("Boolean", "FIREBASE_TEST", "false")
        }
        create("firebase") {
            dimension = "store"
            versionNameSuffix = ".debug"
            buildConfigField("Boolean", "FIREBASE_TEST", "true")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    viewBinding {
        enable = true
    }
    buildFeatures {
        compose = true
        dataBinding = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.12"
    }
    hilt {
        enableAggregatingTask = true
    }
    kotlin {
        sourceSets.configureEach {
            kotlin.srcDir(layout.buildDirectory.dir("generated/ksp/$name/kotlin/"))
        }
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }
}

composeCompiler {
    reportsDestination = layout.buildDirectory.dir("compose_compiler")
    stabilityConfigurationFile = rootProject.layout.projectDirectory.file("stability_config.conf")
}

dependencies {

    // androidx
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.lifecycle)
    implementation(libs.androidx.startup)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.swiperefreshlayout)

    // data binding
    implementation(libs.bindables)

    // di
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    androidTestImplementation(libs.hilt.testing)
    kaptAndroidTest(libs.hilt.compiler)

    // coroutines
    implementation(libs.coroutines)

    // whatIf
    implementation(libs.whatif)

    // timber
    implementation(libs.timber)

    // bundler
    implementation(libs.bundler)

    // customViews
    implementation(libs.recyclerview)
    implementation(libs.baseAdapter)
    implementation(libs.progressView)
    implementation(libs.glide)

    // transformation animation
    implementation(libs.transformationLayout)

    // unit test
    testImplementation(libs.junit)
    testImplementation(libs.turbine)
    testImplementation(libs.androidx.test.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.mockito.inline)
    testImplementation(libs.coroutines.test)
    androidTestImplementation(libs.truth)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)
    //androidTestImplementation(libs.android.test.runner)
}