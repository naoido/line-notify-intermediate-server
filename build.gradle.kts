plugins {
    id("java")
}

group = "com.naoido"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("javax.servlet:servlet-api:+")
    implementation("org.eclipse.jetty:jetty-servlet:+")
    implementation("com.fasterxml.jackson.core:jackson-databind:+")
}


val jar by tasks.getting(Jar::class) {
    manifest {
        attributes["Main-Class"] = "com.naoido.linenotify.Main"
    }
}