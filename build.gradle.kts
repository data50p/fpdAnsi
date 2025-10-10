import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm") version "2.2.20"
//    kotlin("plugin.power-assert") version "2.2.20"
    kotlin("plugin.serialization") version "2.2.20"
}

group = "femtioprocent"
version = "0.0.1.0"

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
    maven { url = uri("https://www.jetbrains.com/intellij-repository/releases") }
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
    // https://mvnrepository.com/artifact/commons-codec/commons-codec
    implementation("commons-codec:commons-codec:1.19.0")
    implementation("org.json:json:20231013")
    implementation("org.fusesource.mqtt-client:mqtt-client:1.15")
    implementation("com.github.moquette-io.moquette:moquette-broker:0.18.0")
    implementation("jakarta.xml.ws:jakarta.xml.ws-api:2.3.3")
    implementation("com.sun.xml.ws:jaxws-rt:2.3.3")
    implementation("javax.xml.ws:jaxws-api:2.3.1")
    implementation(kotlin("reflect"))
    implementation("org.swinglabs:swing-layout:1.0.3")
    // https://mvnrepository.com/artifact/com.jetbrains.intellij.java/java-gui-forms-rt
    runtimeOnly("com.jetbrains.intellij.java:java-gui-forms-rt:252.25557.184")
    implementation("com.intellij:forms_rt:7.0.3")
    // ? implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.4.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")

    implementation(platform("io.arrow-kt:arrow-stack:2.1.0"))
    // no versions on libraries
    implementation("io.arrow-kt:arrow-core")
    // implementation("io.arrow-kt:arrow-fx-coroutines")
}

/*powerAssert {
functions = listOf(
"kotlin.test.assertTrue",
"kotlin.test.assertNull",
"kotlin.test.assertFalse",
"kotlin.test.assertNotNull",
"kotlin.test.assertContains",
"kotlin.test.assertEquals"
)
}*/

application {
    mainClass = "femtioprocent.ansi.appl.AnsiDemo"
    applicationDefaultJvmArgs = listOf("--add-opens", "java.base/jdk.internal.misc=ALL-UNNAMED")
    executableDir = "."
}

tasks.jar {
    manifest {
	manifest.attributes["Main-Class"] = application.mainClass
    }
}

tasks.register<Jar>("uberJar") {
    archiveClassifier = "uber"

    from(sourceSets.main.get().output)

    dependsOn(configurations.runtimeClasspath)
    from({
	duplicatesStrategy = DuplicatesStrategy.INCLUDE
	configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}

tasks {
    withType<JavaCompile> {
	options.compilerArgs.add("-Xlint:unchecked")
	options.compilerArgs.add("-Xlint:deprecation")
    }
}

tasks.startScripts {
    doLast {
	listOf(unixScript, windowsScript).forEach { script ->
	    script
		.readText()
		.replace("..", "")
		.let(script::writeText)
	}
    }
}

tasks.compileKotlin {
    doLast {
	val buildNumber = try {
	    File("build-number").readText().toInt()
	} catch (ex: Exception) {
	    1000
	}
	File("build-number").writeText("${buildNumber + 1}")
    }
}

tasks.test {
    useJUnitPlatform()
}

java {
    sourceCompatibility = JavaVersion.VERSION_24
    targetCompatibility = JavaVersion.VERSION_24
}

kotlin {
    compilerOptions {
	jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_24
	freeCompilerArgs.add("-Xcontext-sensitive-resolution")
	freeCompilerArgs.add("-Xdata-flow-based-exhaustiveness")
    }
}

kotlin {
    jvmToolchain(25)
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.compilerOptions {
    languageVersion.set(KotlinVersion.KOTLIN_2_2)
}
