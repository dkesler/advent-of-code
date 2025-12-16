repositories {
  mavenCentral()
}

plugins {
  kotlin("jvm") version "1.4.20"
}

dependencies {
  implementation(project("::utils"))
  implementation("com.google.code.gson:gson:2.8.2")
  implementation("org.apache.commons:commons-math3:3.6.1")
  //implementation("ai.timefold.solver:timefold-solver-core:1.29.0")
  implementation("org.choco-solver:choco-solver:4.10.18")
  implementation("org.apache.commons:commons-rng-sampling:1.5")
  implementation(kotlin("stdlib"))
}
