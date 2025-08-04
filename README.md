# household-management

[![build](https://github.com/TakuyaFukumura/household-management/actions/workflows/build.yml/badge.svg)](https://github.com/TakuyaFukumura/household-management/actions/workflows/build.yml?query=branch:main)

## プロジェクト概要

Spring Boot を使用した家計管理アプリケーションです。日々の支出記録と予算管理を行い、家計の可視化とコントロールをサポートします。

## 主な機能

### 📊 支出管理
- 日付、カテゴリ、金額、説明による支出記録
- 年月での支出履歴表示
- 支出データの編集・削除
- カテゴリ別支出の可視化（チャート表示）

### 💰 予算管理  
- カテゴリ別の月間予算目標設定
- 予算と実支出の比較
- 予算データの編集・削除
- 予算構成の可視化（チャート表示）

## 技術スタック

- **フレームワーク**: Spring Boot 3.5.3
- **Java バージョン**: Java 17
- **データベース**: H2 Database（組み込み）
- **ORM**: Spring Data JPA / Hibernate
- **テンプレートエンジン**: Thymeleaf
- **ビルドツール**: Maven
- **その他**: Lombok

## 前提条件

- Java 17 以上
- Maven（Maven Wrapper を使用するため、インストール不要）

## 開発環境のセットアップ

### リポジトリのクローン
```bash
git clone https://github.com/TakuyaFukumura/household-management.git
```
```bash
cd household-management
```

### 依存関係の解決とコンパイル
```bash
./mvnw clean compile
```

## 実行方法

### 開発環境での起動
```bash
./mvnw spring-boot:run
```

### 実行可能 JAR の作成と実行
- JAR ファイルの作成
```bash
./mvnw clean package
```
- JAR ファイルの実行
```bash
java -jar ./target/*.jar
```

アプリケーションが起動したら、ブラウザで http://localhost:8080 にアクセスしてください。

## テスト

### テストの実行
```bash
./mvnw test
```

## Docker での開発環境

### 前提条件

- Docker
- Docker Compose

### Docker イメージのビルドと起動

**重要**: Dockerfileはビルド済みのJARファイルを使用するため、事前にアプリケーションをビルドしてください。

```bash
# アプリケーションを事前にビルド
./mvnw clean package -DskipTests
```

#### Docker Compose を使用した起動（推奨）
```bash
# アプリケーションをビルドして起動
docker compose up --build

# バックグラウンドで起動
docker compose up -d --build

# ログを確認
docker compose logs -f

# 停止
docker compose down
```

**注意**: 古い `docker-compose` コマンドも使用可能ですが、新しい `docker compose` コマンドを推奨します。

#### Docker コマンドを直接使用
```bash
# イメージをビルド
docker build -t household-management .

# コンテナを起動
docker run -p 8080:8080 -v $(pwd)/h2db:/app/h2db household-management
```

### アクセス方法

アプリケーションが起動したら、以下の URL からアクセスできます：

- **メインアプリケーション**: http://localhost:8080
- **H2 データベースコンソール**: http://localhost:8080/h2-console/

### データの永続化

Docker Compose 使用時、H2 データベースファイルは `./h2db` ディレクトリにマウントされ、コンテナを削除してもデータが保持されます。

### トラブルシューティング

#### ポートが使用中の場合
```bash
# 使用中のプロセスを確認
lsof -i :8080

# docker-compose.yml でポートを変更
ports:
  - "8081:8080"  # ホストポート8081にマッピング
```

#### コンテナのリビルド
```bash
# キャッシュを使わずにリビルド
docker compose build --no-cache

# または docker-compose を使用
docker-compose build --no-cache

# 不要なイメージを削除
docker system prune
```

## データベース

### H2 Database
本アプリケーションは H2 インメモリデータベースを使用しています。

#### H2 コンソールへのアクセス
H2 コンソールは既定で有効になっています。以下の URL からアクセス可能です：

http://localhost:8080/h2-console/

H2 コンソールを無効にする場合は、`application.properties` で以下を設定してください：
```properties
spring.h2.console.enabled=false
```

## プロジェクト構成

```
src/
├── main/
│   ├── java/
│   │   └── com/example/
│   │       ├── Main.java                 # メインクラス
│   │       ├── controller/               # Web コントローラ
│   │       │   ├── ChartDataController.java
│   │       │   ├── HouseholdBudgetController.java
│   │       │   └── HouseholdExpenseController.java
│   │       ├── dto/                      # データ転送オブジェクト
│   │       ├── entity/                   # エンティティクラス
│   │       │   ├── HouseholdBudget.java
│   │       │   └── HouseholdExpense.java
│   │       ├── repository/               # データアクセス層
│   │       └── service/                  # ビジネスロジック層
│   └── resources/
│       ├── application.properties        # アプリケーション設定
│       ├── data.sql                      # 初期データ
│       └── templates/                    # Thymeleaf テンプレート
│           ├── budget/                   # 予算管理画面
│           ├── expenses/                 # 支出管理画面
│           └── fragments/                # 共通部品
└── test/                                 # テストコード
```

## スクリーンショット

### 支出一覧画面
準備中

### 予算一覧画面  
準備中
