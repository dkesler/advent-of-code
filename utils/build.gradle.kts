repositories {
  mavenCentral()
}

plugins {
  kotlin("jvm") version "1.4.20"
}

dependencies {
  testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
  testImplementation("org.junit.jupiter:junit-jupiter-params:5.9.2")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")

  implementation(kotlin("stdlib"))
}

tasks {
  test {
    useJUnitPlatform()
    testLogging {
      showStandardStreams = true
    }
  }
  compileKotlin {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
  }
  compileTestKotlin {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
  }
}

