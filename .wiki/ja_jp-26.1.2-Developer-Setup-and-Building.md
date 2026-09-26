# 🛠️ 開発環境セットアップと Loom ビルド (MC 26.1.2)

> 📌 **リポジトリソースに関する免責事項**: 本 Wiki のドキュメントは**リポジトリ内の現在のソースコード状態**を反映しており、CurseForge や Modrinth での一般公開リリースビルドに先立つ最新の未リリースコミットや開発中の機能が含まれている場合があります。

| ツールチェーンインフォボックス | 技術パラメータ |
| :--- | :--- |
| **サブプロジェクトディレクトリ** | `Magnet v26.1/magnet/` |
| **Java JDK ターゲット** | **Java 25** (`release = 25`) |
| **Gradle Loom プラグイン** | `net.fabricmc.fabric-loom` バージョン `1.15.5` |
| **Minecraft バージョン** | `26.1.2` |
| **Fabric Loader バージョン** | `0.19.1`（最小要件: `>=0.16.10`） |
| **Fabric API バージョン** | `0.145.4+26.1.2` |
| **DasikLibrary バージョン** | `1.8.23` |
| **Cloth Config バージョン** | `26.1.154` |

---

## 💻 開発環境セットアップとツールチェーン

1. **Java Development Kit (JDK 25)**:
   - このワークスペースの Fabric Minecraft プロジェクトは Java 25 でコンパイルされます。
   - `JAVA_HOME` を構成するか、`gradle.properties` 内で `org.gradle.java.home=E:/JDK25` を設定してください。
2. **Git クローン**:
   ```bash
   git clone https://github.com/Rifaditya/Instant-Gratification-Magnet-Let-me-get-that-.git
   cd "Instant-Gratification-Magnet-Let-me-get-that-/Magnet v26.1/magnet"
   ```

---

## ⚙️ プロパティ設定 (`gradle.properties`)

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

## 🔨 Gradle ビルドコマンド

```bash
# 以前のビルド成果物をクリーン
./gradlew clean

# 自動テストを実行
./gradlew test

# 製品版 JAR をコンパイルし、自動アーカイブをトリガー
./gradlew build --no-daemon
```

---

## 📦 自動リリースアーカイブ

MC 26.1.2 の `build.gradle` スクリプトには、自動化された `archiveReleaseJar` ライフサイクルタスクが組み込まれています：

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

## 🔗 関連 Wiki ドキュメント
* [[アーキテクチャ・パッケージ・Mixin 注入|ja_jp-26.1.2-Architecture-and-Mixins]]
* [[API ファサード・インターフェース・アドオンフック|ja_jp-26.1.2-API-and-Addon-Integration]]
* [[MC 26.1.2 ポータルに戻る|ja_jp-26.1.2-Home]]
