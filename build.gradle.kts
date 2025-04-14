plugins {
    id("fabric-loom") version "1.10-SNAPSHOT"
    kotlin("jvm") version "2.1.20"
    idea
}

group = "moe.skjsjhb.mc.fubuki"

base {
    archivesName = "fubuki"
}

idea {
    module {
        isDownloadSources = true
    }
}

loom {
    splitEnvironmentSourceSets()

    accessWidenerPath = file("src/main/resources/fubuki.accesswidener")

    mods {
        create("fubuki") {
            sourceSet(sourceSets["main"])
        }
    }
}

dependencies {
    minecraft("com.mojang:minecraft:${project.property("minecraft_version")}")
    mappings("net.fabricmc:yarn:${project.property("yarn_mappings")}:v2")
    modImplementation("net.fabricmc:fabric-loader:${project.property("loader_version")}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${project.property("fabric_version")}")
    modImplementation("net.fabricmc:fabric-language-kotlin:${project.property("fabric_kotlin_version")}")
    implementation(project("bukkit"))
}

tasks.classes {
    dependsOn(":example-plugin:deployPlugin")
}

tasks.withType<JavaCompile>().configureEach {
    options.release.set(21)
}

kotlin {
    compilerOptions {
        jvmToolchain(21)
    }
}

java {
    withSourcesJar()

    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}


