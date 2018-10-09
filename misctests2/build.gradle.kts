import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    groovy
    java
    kotlin("jvm") version "1.2.60"
}

group = "io.chan"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    compile("org.codehaus.groovy:groovy-all:2.3.11")
    compile(kotlin("stdlib-jdk8"))
    compile("com.github.kittinunf.fuel:fuel-gson:1.15.0")
    compile("com.google.code.gson:gson:2.8.5")
    compile("org.jetbrains.exposed:exposed:0.10.5")
    compile("org.postgresql:postgresql:42.2.5")
    testCompile("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}