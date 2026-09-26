# 🛠️ Configuração do Desenvolvedor & Compilação com Loom (MC 26.1.2)

> 📌 **Aviso sobre o Código-Fonte do Repositório**: A documentação nesta Wiki reflete o **estado atual do código-fonte no repositório**, que pode incluir commits recentes ainda não lançados ou recursos em desenvolvimento antes das compilações públicas oficiais no CurseForge e Modrinth.

| Infobox da Toolchain | Parâmetros Técnicos |
| :--- | :--- |
| **Diretório do Subprojeto** | `Magnet v26.1/magnet/` |
| **Alvo do JDK Java** | **Java 25** (`release = 25`) |
| **Plugin Gradle Loom** | `net.fabricmc.fabric-loom` versão `1.15.5` |
| **Versão do Minecraft** | `26.1.2` |
| **Versão do Fabric Loader** | `0.19.1` (Limite mín.: `>=0.16.10`) |
| **Fabric API Version** | `0.145.4+26.1.2` |
| **Versão da DasikLibrary** | `1.8.23` |
| **Versão do Cloth Config** | `26.1.154` |

---

## 💻 Configuração do Ambiente & Cadeias de Ferramentas

1. **Java Development Kit (JDK 25)**:
   - Os projetos Fabric modernos de Minecraft neste workspace compilam contra Java 25.
   - Configure a variável `JAVA_HOME` ou defina `org.gradle.java.home=E:/JDK25` em `gradle.properties`.
2. **Clone do Repositório Git**:
   ```bash
   git clone https://github.com/Rifaditya/Instant-Gratification-Magnet-Let-me-get-that-.git
   cd "Instant-Gratification-Magnet-Let-me-get-that-/Magnet v26.1/magnet"
   ```

---

## ⚙️ Configuração de Propriedades (`gradle.properties`)

```properties
org.gradle.parallel=false
org.gradle.java.home=E:/JDK25

# Mod Properties
mod_name=Magnet, Let me get that!
mod_version=1.1.2+26.1.2
maven_group=net.instantgratification
archives_base_name=Magnet-Let-me-get-that

# Dependencies
minecraft_version=26.1.2
parchment_minecraft_version=26.1.2
parchment_version=2026.01.22

# Fabric
fabric_version=0.145.4+26.1.2
fabric_loader_version=0.19.1

# Loom
fabric.loom.suppressJavaCompatibilityChecks=true
loom.suppressJavaCompatibilityChecks=true

dasik_library_version=1.8.23
```

---

## 🔨 Comandos de Compilação do Gradle

```bash
# Limpar artefatos de compilações anteriores
./gradlew clean

# Executar testes automatizados
./gradlew test

# Compilar JAR de produção e disparar arquivamento automático
./gradlew build --no-daemon
```

---

## 📦 Arquivamento Automático de Lançamentos

O script `build.gradle` do MC 26.1.2 inclui uma tarefa de ciclo de vida automatizada `archiveReleaseJar`:

```groovy
tasks.register('archiveReleaseJar') {
    dependsOn 'build'
    doLast {
        def archiveDir = file("${project.rootDir}/../../Archive Jar of all versions/MC 26.1.2")
        archiveDir.mkdirs()
        def jarFile = tasks.named('jar', Jar).get().archiveFile.get().asFile
        if (jarFile.exists()) {
            copy {
                from jarFile
                into archiveDir
            }
            println "[AUTO-ARCHIVE] Successfully copied ${jarFile.name} to central Archive directory: ${archiveDir.absolutePath}"
        }
    }
}

tasks.named('build') {
    finalizedBy 'archiveReleaseJar'
}
```

---

## 🔗 Documentação da Wiki Relacionada
* [[Implementações de Arquitetura & Mixin|pt_br-26.1.2-Architecture-and-Mixins]]
* [[Integração de API & Addons|pt_br-26.1.2-API-and-Addon-Integration]]
* [[Retornar ao Portal do MC 26.1.2|pt_br-26.1.2-Home]]
