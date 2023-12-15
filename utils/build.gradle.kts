repositories {
  mavenCentral()
}

plugins {
  kotlin("jvm") version "1.4.20"
}

dependencies {
  testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
}

tasks {
  test {
    useJUnitPlatform()
    testLogging {
      showStandardStreams = true
    }
  }
}