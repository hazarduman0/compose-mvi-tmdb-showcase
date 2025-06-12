import org.gradle.testing.jacoco.tasks.JacocoReport
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt)
    jacoco
}

android {
    namespace = "com.hazarduman.cinescope"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.hazarduman.cinescope"
        minSdk = 24
        targetSdk = 35
        versionCode = (project.findProperty("versionCode") as? String)?.toInt() ?: 1
        versionName = project.findProperty("versionName") as? String ?: "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            val props = Properties()
            val file = rootProject.file("local.properties")
            if (file.exists()) {
                file.inputStream().use { props.load(it) }
            }
            val storeFilePath = props.getProperty("RELEASE_STORE_FILE")
            val storePassword = props.getProperty("RELEASE_STORE_PASSWORD")
            val keyAlias = props.getProperty("RELEASE_KEY_ALIAS")
            val keyPassword = props.getProperty("RELEASE_KEY_PASSWORD")
            if (!storeFilePath.isNullOrEmpty() && !storePassword.isNullOrEmpty() && !keyAlias.isNullOrEmpty() && !keyPassword.isNullOrEmpty()) {
                storeFile = file(storeFilePath)
                this.storePassword = storePassword
                this.keyAlias = keyAlias
                this.keyPassword = keyPassword
            }
        }
    }

    buildTypes {
        getByName("debug") {
            enableUnitTestCoverage = true
            versionNameSuffix = "-dev"
        }
        create("releaseDebug") {
            initWith(getByName("release"))
            isDebuggable = true
            signingConfig = signingConfigs.getByName("debug")
            versionNameSuffix = "-pre"
            /** Proguard, chucker etc.  **/
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

val exclusions = listOf(
    "**/R.class",
    "**/R\$*.class",
    "**/BuildConfig.*",
    "**/Manifest*.*",
    "**/*Test*.*",
    "android/**/*.*"
)

tasks.register<JacocoReport>("jacocoViewModelUnitTestReport") {
    group = "Reporting"
    description = "Generate JaCoCo unit test coverage report ONLY for ViewModel classes"

    dependsOn("testDebugUnitTest")

    reports {
        xml.required.set(true)
        html.required.set(true)
    }

    val viewModelIncludes = listOf("**/*ViewModel.class")

    val debugTree = fileTree(layout.buildDirectory.dir("intermediates/javac/debug/classes")) {
        include(viewModelIncludes)
        exclude(exclusions)
    }

    val kotlinDebugTree = fileTree(layout.buildDirectory.dir("tmp/kotlin-classes/debug")) {
        include(viewModelIncludes)
        exclude(exclusions)
    }

    classDirectories.setFrom(files(debugTree, kotlinDebugTree))
    sourceDirectories.setFrom(files("src/main/kotlin", "src/main/java"))

    val execFiles = fileTree(layout.buildDirectory) {
        include(
            "jacoco/testDebugUnitTest.exec",
            "outputs/unit_test_code_coverage/debugUnitTest/testDebugUnitTest.exec"
        )
    }
    executionData.setFrom(execFiles)
}

dependencies {
    // --- Production dependencies ---
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.accompanist.navigation.animation)
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)

    // --- Annotation processors (KAPT) ---
    kapt(libs.hilt.android.compiler)

    // --- Unit test dependencies ---
    testImplementation(libs.junit)

    // --- Instrumented Android test dependencies ---
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // --- Debug-only dependencies ---
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

kapt {
    correctErrorTypes = true
}