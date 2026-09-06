# 🛠️ Configuração do Desenvolvedor & Compilação com Loom (MC 26.2)

> 📌 **Aviso sobre o Código-Fonte do Repositório**: A documentação nesta Wiki reflete o **estado atual do código-fonte no repositório**, que pode incluir commits recentes ainda não lançados ou recursos em desenvolvimento antes das compilações públicas oficiais no CurseForge e Modrinth.

| Infobox da Toolchain | Parâmetros Técnicos |
| :--- | :--- |
| **Diretório do Subprojeto** | `Magnet v26.2/magnet/` |
| **Alvo do JDK Java** | **Java 25** (`release = 25`) |
| **Plugin Gradle Loom** | `net.fabricmc.fabric-loom` versão `1.15.5` |
| **Versão do Minecraft** | `26.2` |
| **Versão do Fabric Loader** | `0.19.1` |
| **Versão do Fabric API** | `0.150.1+26.2` |
| **Versão da DasikLibrary** | `1.8.23` |
| **Versão do YACL** | `3.9.5+26.2-fabric` |

---

## 💻 Pré-requisitos & Configuração do Ambiente

1. **Java Development Kit (JDK 25)**:
   - Versões modernas do Minecraft 26.x compilam contra Java 25.
   - Configure a variável `JAVA_HOME` ou defina `org.gradle.java.home=E:/JDK25` no `gradle.properties`.
2. **Git & Clonagem do Workspace**:
   ```bash
   git clone https://github.com/Rifaditya/Instant-Gratification-Magnet-Let-me-get-that-.git
   cd "Instant-Gratification-Magnet-Let-me-get-that-/Magnet v26.2/magnet"
   ```

---

## ⚙️ Configuração de Propriedades (`gradle.properties`)

```properties
org.gradle.parallel=false
org.gradle.java.home=E:/JDK25

# Mod Properties
mod_name=Magnet, Let me get that!
mod_version=1.3.9+26.2
maven_group=net.instantgratification
archives_base_name=Magnet-Let-me-get-that

# Dependencies
minecraft_version=26.2
parchment_minecraft_version=26.2
parchment_version=2026.01.22

# Fabric
fabric_version=0.150.1+26.2
fabric_loader_version=0.19.1

# Loom
fabric.loom.suppressJavaCompatibilityChecks=true
loom.suppressJavaCompatibilityChecks=true

dasik_library_version=1.8.23
```

---

## 🔨 Comandos de Compilação do Gradle

```bash
# Limpar cache de compilações anteriores
./gradlew clean

# Executar testes automatizados
./gradlew test

# Compilar JAR de produção e disparar arquivamento automático
./gradlew build --no-daemon
```

---

## 📦 Arquivamento Automático de Lançamentos & Sincronização com Modrinth

O script `build.gradle` do MC 26.2 conta com uma tarefa de ciclo de vida `archiveReleaseJar` registrada:

```groovy
tasks.register('archiveReleaseJar') {
    dependsOn 'build'
    doLast {
        def archiveDir = file("${project.rootDir}/../../Archive Jar of all versions/MC 26.2")
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

Após uma compilação bem-sucedida, o JAR gerado (`Magnet-Let-me-get-that-1.3.9+26.2.jar`) é automaticamente espelhado para `Archive Jar of all versions/MC 26.2/` e implantado nos perfis de teste ativos do Modrinth launcher local.

---

## 🔗 Documentação da Wiki Relacionada
* [[Implementações de Arquitetura & Mixin|pt_br-26.2-Architecture-and-Mixins]]
* [[Integração de API & Addons|pt_br-26.2-API-and-Addon-Integration]]
* [[Retornar ao Portal do MC 26.2|pt_br-26.2-Home]]
