plugins {
    id("com.leandrodev.android-application")
    id("com.google.gms.google-services")
}

android {
    defaultConfig {
        applicationId = "com.leandrodev.contasdolar.android"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(project(":aspects"))
    implementation(project(":data"))
    implementation(project(":bills"))
    implementation(project(":ui"))
}