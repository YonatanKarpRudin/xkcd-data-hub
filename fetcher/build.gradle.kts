plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ktor)
    alias(libs.plugins.jooq.docker)
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
    jooqCodegen(libs.postgres.driver)
    implementation(libs.logback)
    testImplementation(libs.bundles.tests.all)
}

tasks.generateJooqClasses {
    usingJavaConfig {
        name = "org.jooq.codegen.KotlinGenerator"
        generate.isIndexes = false
        generate.isJooqVersionReference = false
        generate.isKotlinNotNullPojoAttributes = true
        generate.isKotlinNotNullRecordAttributes = true
    }
    schemas.set(listOf("xkcd_data_hub"))
    includeFlywayTable.set(false)
    flywayProperties.put("flyway.postgresql.transactional.lock", "false")
    basePackageName.set("com.xkcddatahub.fetcher.adapters.output.database.postgres.jooq")
}

sourceSets[SourceSet.MAIN_SOURCE_SET_NAME].kotlin {
    srcDir("${layout.buildDirectory.get()}/generated-jooq")
}
