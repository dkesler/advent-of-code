repositories {
  mavenCentral()
}

plugins {
  kotlin("jvm") version "1.4.20"
}

dependencies {
  implementation(project("::utils"))
  implementation("com.google.code.gson:gson:2.8.2")
}
