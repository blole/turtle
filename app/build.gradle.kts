plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter("5.10.0")
        }
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    mainClass = "dev.blole.Main"
}
