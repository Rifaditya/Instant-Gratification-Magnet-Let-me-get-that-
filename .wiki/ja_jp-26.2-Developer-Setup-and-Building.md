# 🛠️ 開発環境セットアップと Loom ビルド (MC 26.2)

> 📌 **リポジトリソースに関する免責事項**: 本 Wiki のドキュメントは**リポジトリ内の現在のソースコード状態**を反映しており、CurseForge や Modrinth での一般公開リリースビルドに先立つ最新の未リリースコミットや開発中の機能が含まれている場合があります。

| ツールチェーンインフォボックス | 技術パラメータ |
| :--- | :--- |
| **サブプロジェクトディレクトリ** | `Magnet v26.2/magnet/` |
| **Java JDK ターゲット** | **Java 25** (`release = 25`) |
| **Gradle Loom プラグイン** | `net.fabricmc.fabric-loom` バージョン `1.15.5` |
| **Minecraft バージョン** | `26.2` |
| **Fabric Loader バージョン** | `0.19.1` |
| **Fabric API バージョン** | `0.150.1+26.2` |
| **DasikLibrary バージョン** | `1.8.23` |
| **YACL バージョン** | `3.9.5+26.2-fabric` |

---

## 💻 前提条件と環境セットアップ

1. **Java Development Kit (JDK 25)**:
   - モダンな Minecraft 26.x プロジェクトは Java 25 でコンパイルされます。
   - `JAVA_HOME` を設定するか、`gradle.properties` 内で `org.gradle.java.home=E:/JDK25` を構成してください。
2. **Git とワークスペースのクローン**:
   ```bash
   git clone https://github.com/Rifaditya/Instant-Gratification-Magnet-Let-me-get-that-.git
   cd "Instant-Gratification-Magnet-Let-me-get-that-/Magnet v26.2/magnet"
   ```

---

## ⚙️ プロパティ設定 (`gradle.properties`)

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

## 🔨 Gradle ビルドコマンド

```bash
# 以前のビルドキャッシュをクリーン
./gradlew clean

# 単体テストを実行
./gradlew test

# 製品版 JAR をコンパイルし、自動アーカイブをトリガー
./gradlew build --no-daemon
```

---

## 📦 自動リリースアーカイブと Modrinth 同期

MC 26.2 の `build.gradle` スクリプトには、`archiveReleaseJar` ライフサイクルタスクが登録されています：

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

コンパイルが成功すると、生成された JAR（`Magnet-Let-me-get-that-1.3.9+26.2.jar`）は自動的に `Archive Jar of all versions/MC 26.2/` へミラーリングされ、ローカルの Modrinth ランチャーテストディレクトリへ配置されます。

---

## 🔗 関連 Wiki ドキュメント
* [[アーキテクチャと Mixin 実装|ja_jp-26.2-Architecture-and-Mixins]]
* [[API とアドオン連携|ja_jp-26.2-API-and-Addon-Integration]]
* [[MC 26.2 ポータルに戻る|ja_jp-26.2-Home]]
