import kotlin.reflect.full.memberProperties
import groovy.json.JsonSlurper

plugins {
    id("java")
    id("org.openapi.generator").version("7.0.1")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
//    implementation("org.openapitools:openapi-generator-gradle-plugin:7.0.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}


tasks.register<Copy>("one") {
    println("one ")
    from(layout.projectDirectory.file("config/test.yaml"))
    into(layout.buildDirectory.dir("config"))
    println("one_test:" + layout.buildDirectory.file("config/test.yaml").get().toString())
}

tasks.register<org.openapitools.generator.gradle.plugin.tasks.GenerateTask>("two") {
    /*
    ext {
        var inConfig = layout.projectDirectory.file("config/test2.yaml")
//        set("generatorName", "generatorName")
        inputs.files(inConfig);
        println(inputs.files)
        println("two_0:")
        println(getProperties())
    }
    val properties = Properties().apply {
        load(rootProject.file("config/test3.properties").reader())
    }
//    generatorName="spring"
    inputs.property("generatorName", "generatorName")
    println("two_1:" + properties)
    println("two_2:" + properties.get("generatorName"))
    println(this)
    println("two")
    println(inputs.properties)
    println(inputs.files)
    println(inputs.files.files)
//    dependsOn("one")
//    println("two_test:" + File(layout.buildDirectory.file("config/test.yaml").get().toString()).readText())
//    generatorName.set("spring")
    inputSpec.set(layout.projectDirectory.file("openapi/pet.yaml").toString())
    outputDir.set(layout.buildDirectory.dir("test").get().toString())
     */
    // Загружаем конфигурацию из YAML файла
    // Загружаем конфигурацию из YAML файла
//    doLast {
        val yaml = org.yaml.snakeyaml.Yaml()
        val config = yaml.load<Map<String, Any>>(file("config/test2.yaml").inputStream())

    println("RI_1")
//    this::class.members.forEach { println("- $it") }
    //this.generatorName.set(config["generatorName"] as String)

    println("RI_2")
        // Проходим по ключам конфигурации и настраиваем задачу
        config.forEach { (key, value) ->
            when (key) {
                "configOptions" -> {
                    // Если ключ - это configOptions, то настраиваем как Map
//                this.configOptions = value as Map<String, Any>
                }
                else -> {
                    // Все остальные ключи (например, generatorName, inputSpec, outputDir)
                    try {
                        val property = this::class.memberProperties.find { it.name == key }
                        // Используем метод "setter" для свойства
//                        property?.let { it.call(this, value) }
                        if (property is kotlin.reflect.KMutableProperty1) {
                            property.let {
                                (it as kotlin.reflect.KMutableProperty1<org.openapitools.generator.gradle.plugin.tasks.GenerateTask, Any>)
                                    .set(this, "Alex")
                            }
                        } else {
                            println("shlyapa")
                        }
                        println("setting property $key: $value")
                    } catch (e: Exception) {
                        println("Error setting property $key: ${e.message}")
                    }
                }
            }
        }

        // Дополнительно выводим информацию о конфигурации
        println("Configuration for OpenAPI generation loaded from ${configFile}:")
        config.forEach { (key, value) ->
            println("  $key: $value")
        }
//    }
}

tasks.register<Copy>("three") {
    println("three " + tasks["one"].outputs.files.asPath)
    from(tasks["one"])
    into(layout.buildDirectory.dir("config2"))
}


//tasks.register( "myTask", org.openapitools.generator.gradle.plugin.tasks.GenerateTask::class ) {
//    var javaExtension = extensions.getByType(JavaPluginExtension::class)
//    var mainSourceSet = javaExtension.sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME)
//    compileClasspath.from(mainSourceSet.compileClasspath)
//}



val four by tasks.registering(org.openapitools.generator.gradle.plugin.tasks.GenerateTask::class) {
    /*
    ext {
        var inConfig = layout.projectDirectory.file("config/test2.yaml")
//        set("generatorName", "generatorName")
        inputs.files(inConfig);
        println(inputs.files)
        println("two_0:")
        println(getProperties())
    }
    val properties = Properties().apply {
        load(rootProject.file("config/test3.properties").reader())
    }
//    generatorName="spring"
    inputs.property("generatorName", "generatorName")
    println("two_1:" + properties)
    println("two_2:" + properties.get("generatorName"))
    println(this)
    println("two")
    println(inputs.properties)
    println(inputs.files)
    println(inputs.files.files)
//    dependsOn("one")
//    println("two_test:" + File(layout.buildDirectory.file("config/test.yaml").get().toString()).readText())
//    generatorName.set("spring")
    inputSpec.set(layout.projectDirectory.file("openapi/pet.yaml").toString())
    outputDir.set(layout.buildDirectory.dir("test").get().toString())
     */
    // Загружаем конфигурацию из YAML файла
    // Загружаем конфигурацию из YAML файла
//    doLast {
    val yaml = org.yaml.snakeyaml.Yaml()
    val config = yaml.load<Map<String, Any>>(file("config/test2.yaml").inputStream())

    println("RI_1")
//    this::class.members.forEach { println("- $it") }
    //this.generatorName.set(config["generatorName"] as String)

    println("RI_2")
    // Проходим по ключам конфигурации и настраиваем задачу

    // Дополнительно выводим информацию о конфигурации
    println("Configuration for OpenAPI generation loaded from ${config}:")
    config.forEach { (key, value) ->
        //println("  $key: $value")
        this.setProperty(key, value)
    }
//    }
}

tasks.register("five") {
    val yaml = org.yaml.snakeyaml.Yaml()
    val config = yaml.load<Map<String, Any>>(file("config/test2.yaml").inputStream())
    println("Configuration for OpenAPI generation loaded from ${config}:")
    doLast {
        tasks.register("six") {
            println("six")
        }
    }
}



// Чтение конфигурации из файла
val taskConfig = readConfigFile("tasks-config.json")

//val ti = object : GenericTypeIndicator<HashMap<String?, String?>?>() {}
fun readConfigFile(fileName: String): Map<String, Any> {
//    return JsonSlurper().parse(file(fileName)) as Map<String, Any>
    val t = JsonSlurper().parse(file(fileName))
    return when (t) {
        is Map<*, *> -> t.filterKeys { it is String }.mapKeys { it.key as String }.mapValues { it.value as Any }
        else -> throw IllegalArgumentException("Parsed JSON is not a Map<String, Any>")
    }
}

// Регистрация задач
task("registerTasks") {
    taskConfig.forEach { (taskName, taskConfig) ->
        println("Registered task: $taskName")
        tasks.register(taskName, org.openapitools.generator.gradle.plugin.tasks.GenerateTask::class) {
//            doLast {
            val description = (taskConfig as Map<String, Any>)["description"]
            taskConfig.forEach { (key, value) ->
                //println("  $key: $value")
                this.setProperty(key, value)
            }
            println("Complete task: $taskName")
            println("Description task: $description")
//            }
        }
    }
}

val printFileContent by tasks.registering {
    // doFirst { inputs.file("config/contract2.json")
    val replaceTokens =
        org.apache.tools.ant.filters.ReplaceTokens(layout.projectDirectory.file("tasks-config.json").asFile.bufferedReader());
    (subprojects.associateBy({ it.name }, { it.projectDir.absolutePath }) + (projectDir.name to projectDir.absolutePath))
        .forEach {
            println("it.key:" + it.key)
            println("it.value:" + it.value)
            val token = org.apache.tools.ant.filters.ReplaceTokens.Token();
            token.key = it.key
            token.value = it.value
            replaceTokens.addConfiguredToken(token);
        }
    println("T3_:" + replaceTokens.readText())
}