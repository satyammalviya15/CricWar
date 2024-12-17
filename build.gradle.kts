buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.1")
        // Import the BoM for the Firebase platform
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.7.2" apply false
}