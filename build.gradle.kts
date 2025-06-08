plugins {
    `java-gradle-plugin`
    `maven-publish`
}

group = "com.github.vitamaxDH"
version = "0.1.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.10.1")
}

gradlePlugin {
    plugins {
        create("gradviz") {
            id = "com.github.vitamaxDH.gradviz"
            implementationClass = "io.vitamax.gradviz.GradvizPlugin"
        }
    }
}

// --------------------------------------------------
// 'gradle publish' 시에 JAR+POM을 생성해주는 기본 publication
// JitPack은 이 publication을 읽어서 아티팩트를 배포합니다.
// --------------------------------------------------
publishing {
    publications {
        create<MavenPublication>("pluginMaven") {
            from(components["java"])
        }
    }
}
