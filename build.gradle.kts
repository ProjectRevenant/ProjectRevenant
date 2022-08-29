import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    `java-library`
    `maven-publish`
    id("io.papermc.paperweight.userdev") version "1.3.7"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "com.gestankbratwurst.core"
version = "1.2.0-SNAPSHOT"
description = "Core plugin für moderne Minecraft Server"
var exVersion = version

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://repo.dmulloy2.net/repository/public/")
    maven("https://repo.aikar.co/content/groups/aikar/")
    maven("https://redempt.dev")
}

dependencies {
    paperDevBundle("1.19.1-R0.1-SNAPSHOT")

    compileOnly("org.projectlombok:lombok:1.18.24")
    compileOnly("com.gestankbratwurst.core:MMCore:1.2.0-SNAPSHOT")
    compileOnly("com.comphenix.protocol:ProtocolLib:5.0.0-SNAPSHOT")
    annotationProcessor("org.projectlombok:lombok:1.18.24")
    implementation("com.github.Redempt:Crunch:1.1.2")
    implementation("net.kyori:adventure-text-minimessage:4.11.0")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.gestankbratwurst.revenant"
            artifactId = "ProjectRevenant"
            version = exVersion as String?
                    from(components["java"])
        }
    }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks {
    build {
        dependsOn(reobfJar)
        finalizedBy(publishToMavenLocal)
    }
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }
    processResources {
        filteringCharset = Charsets.UTF_8.name()
    }
}

bukkit {
    load = BukkitPluginDescription.PluginLoadOrder.STARTUP
    main = "com.gestankbratwurst.revenant.projectrevenant.ProjectRevenant"
    apiVersion = "1.19"
    authors = listOf("Gestankbratwurst", "btw25")
    depend = listOf("MMCore")
}
