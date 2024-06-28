plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ktor)
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies {
    implementation(libs.bundles.ktor.all)
    implementation(libs.koin)
    implementation(libs.bundles.persistance.all)
    implementation(libs.logback)
    testImplementation(libs.koin.test)
    testImplementation(libs.bundles.tests.all)
    testImplementation(libs.bundles.persistance.test.all)
    testImplementation("org.awaitility:awaitility-kotlin:4.2.0")
    testImplementation("com.github.tomakehurst:wiremock:3.0.1")
}
