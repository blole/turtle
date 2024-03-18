import com.adarshr.gradle.testlogger.TestLoggerExtension
import com.adarshr.gradle.testlogger.TestLoggerPlugin
import com.adarshr.gradle.testlogger.theme.ThemeType

plugins {
    java
    id("com.adarshr.test-logger") version "4.0.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains:annotations:24.1.0")
    testImplementation("org.mockito:mockito-core:5.11.0")
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter("5.10.0")
        }
    }
}

tasks.withType<Test> {
    /*
     * running tests otherwise prints:
     * WARNING: A Java agent has been loaded dynamically (/root/.gradle/caches/modules-2/files-2.1/net.bytebuddy/byte-buddy-agent/1.14.12/be4984cb6fd1ef1d11f218a648889dfda44b8a15/byte-buddy-agent-1.14.12.jar)
     * WARNING: If a serviceability tool is in use, please run with -XX:+EnableDynamicAgentLoading to hide this warning
     * WARNING: If a serviceability tool is not in use, please run with -Djdk.instrument.traceUsage for more information
     * WARNING: Dynamic loading of agents will be disallowed by default in a future release
     */
    jvmArgs("-XX:+EnableDynamicAgentLoading")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

plugins.withType<TestLoggerPlugin> {
    configure<TestLoggerExtension> {
        theme = ThemeType.MOCHA_PARALLEL
        showSimpleNames = true
        slowThreshold = 20
        showStandardStreams = true
    }
}

tasks.shadowJar {
    archiveBaseName.set("turtle")
    manifest {
        attributes("Main-Class" to "dev.blole.turtle.Main")
    }
}
