plugins {
    kotlin("jvm")
}

group = "moe.skjsjhb.mc.fubuki"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(project(":bukkit"))
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.register<Copy>("deployPlugin") {
    dependsOn("jar")
    from(tasks.jar.get().archiveFile)
    into(rootProject.file("run/plugins"))
}

kotlin {
    jvmToolchain(21)
}