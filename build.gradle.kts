// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    extra.apply {
        set("kotlin_version", "1.7.10")
        set("retrofit_version", "2.9.0")
        set("lifecycle_version", "2.3.1")
    }
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath ("com.android.tools.build:gradle:4.2.2")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:${project.extra["kotlin_version"]}")
    }
}

allprojects {
    repositories {
        google()
        maven{
            url = uri("https://jitpack.io")
        }
        mavenCentral()
    }
}

tasks.register("clean",Delete::class){
    delete(rootProject.buildDir)
}