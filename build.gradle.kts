import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization") version "1.8.20"
    id("org.jetbrains.compose")
}

group = "com.chosen"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    // Compose for Desktop dependencies
    implementation(compose.desktop.currentOs)


    // Other dependencies
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.2")
    implementation("org.jetbrains.compose.material3:material3:1.0.1")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.compose.desktop:desktop:0.7.0")
    implementation("no.tornado:tornadofx:1.7.20")
    // javafx
    implementation("org.openjfx:javafx-base:18")
    implementation("org.openjfx:javafx-controls:18")
    implementation("org.openjfx:javafx-fxml:18")
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "MessageApp"
            packageVersion = "1.0.0"
        }
    }

    tasks {
        withType<Jar> {
            manifest {
                attributes["Main-Class"] = "MainKt"
            }
            archiveBaseName.set("MessageApp")
            archiveVersion.set("1.0.0")
        }
    }
}

