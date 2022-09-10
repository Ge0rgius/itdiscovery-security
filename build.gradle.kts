plugins {
    java
    eclipse
    id("org.springframework.boot") version "3.0.0-M4" apply false
}

allprojects {
    group = "it.discovery"
}

subprojects {
    apply(plugin = "java")
    //apply(plugin = "org.springframework.boot")

    java.sourceCompatibility = JavaVersion.VERSION_18
    java.targetCompatibility = JavaVersion.VERSION_18

    repositories {
        jcenter()
        maven { url = uri("https://repo.spring.io/milestone") }
    }

    var springBootVersion = "3.0.0-M4"

    dependencies {
        implementation(platform("org.springframework.boot:spring-boot-dependencies:$springBootVersion"))
        implementation("org.springframework.boot:spring-boot-starter-web")

        compileOnly("org.projectlombok:lombok")
        annotationProcessor("org.projectlombok:lombok:1.18.24")
    }
}

