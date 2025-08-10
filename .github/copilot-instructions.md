# 家計管理アプリケーション

家計管理（Household Management）は、Spring Boot を用いた家計の支出・予算管理 Web
アプリケーションです。支出記録、予算管理、グラフによるデータ可視化機能を備え、Spring Boot・Thymeleaf・H2 データベースで構築されています。

まず本ガイドを参照し、ここに記載のない情報や不明点があれば検索やコマンド実行で補完してください。

## 効率的な作業のために

### 初期化・ビルドコマンド

- Maven ラッパーを実行可能にする: `chmod +x mvnw`
- クリーンコンパイル: `./mvnw clean compile`  ※絶対にキャンセルしないこと。初回は2.5分（依存ダウンロード含む）、2回目以降は約3秒。タイムアウトは300秒以上推奨。
- テスト実行: `./mvnw test`  ※絶対にキャンセルしないこと。約53秒。タイムアウトは120秒以上推奨。
- JAR パッケージング: `./mvnw clean package`  ※絶対にキャンセルしないこと。約39秒。タイムアウトは90秒以上推奨。
- テストをスキップしてパッケージ: `./mvnw clean package -DskipTests`  ※絶対にキャンセルしないこと。初回ビルド後は約3秒。タイムアウトは90秒以上推奨。

### アプリケーションの起動

- **開発モード**: `./mvnw spring-boot:run`  約3秒で8080番ポートで起動
- **JAR 実行**: `java -jar ./target/*.jar`
- **メインURL**: http://localhost:8080
- **H2 DBコンソール**: http://localhost:8080/h2-console/
    - JDBC URL: `jdbc:h2:./h2db/h2`
    - ユーザー名: `super`
    - パスワード: （空欄）

### Docker サポート

- Docker ビルド: `docker build -t household-management .`  ※SSL証明書エラーで失敗します（制限環境下）
- Docker Compose: `docker compose up -d --build`  ※SSL証明書エラーで失敗します（制限環境下）
- **重要**: Docker ビルドは証明書検証エラーで失敗します。ローカルの Maven ビルドを使用してください。

## 検証

### 手動テストシナリオ

変更後は必ず以下を手動で検証してください。

1. **アプリ起動確認**:
    - `./mvnw spring-boot:run` を実行
    - エラーなく約3秒で起動すること
    - http://localhost:8080 でホーム画面（日本語UI・Bootstrap）が表示されること
    - http://localhost:8080/h2-console でH2コンソールにアクセスできること

2. **主要機能テスト**:
    - 支出一覧（http://localhost:8080/expenses ）にアクセス
    - 予算一覧（http://localhost:8080/budget ）にアクセス
    - チャートAPI（http://localhost:8080/api/chart/expenses, http://localhost:8080/api/chart/budgets ）を確認
    - グラフが正しく表示されること（Chart.js連携）
    - Web画面から新規支出を追加できること
    - 予算カテゴリの閲覧・編集ができること

3. **DB検証**:
    - H2コンソールに `super`（パスワード空）でログイン
    - `household_expenses` と `household_budgets` テーブルが存在すること
    - `data.sql` からサンプルデータが読み込まれていること

### ビルド検証手順

- 変更前に必ずテストスイートを実行: `./mvnw test`
- **絶対にビルドコマンドをキャンセルしないこと**（初回3分以上かかる場合あり）
- 変更後は必ずビルド: `./mvnw clean compile`
- 重要な変更後はJARパッケージング・実行も確認

### Lint/Format コマンドなし

- 本プロジェクトは Lint/Format プラグイン未導入（checkstyle, spotbugs, spotless等なし）
- コードスタイルは手動で確認
- CIパイプラインは基本的なコンパイルのみ（`.github/workflows/build.yml`参照）

## よくある作業

### 技術スタック

- **フレームワーク**: Spring Boot 3.5.4
- **Javaバージョン**: Java 17
- **ビルドツール**: Maven（ラッパーあり）
- **DB**: H2（ファイル型: `./h2db/h2`）
- **テンプレートエンジン**: Thymeleaf
- **テスト**: Spock Framework（Groovy）
- **UI**: Bootstrap 5.3.2, Chart.js 4.4.0

### プロジェクト構成

```
src/main/java/com/example/
├── Main.java                           # アプリエントリポイント
├── controller/                         # RESTコントローラ
│   ├── ChartDataController.java       # チャートAPI
│   ├── HouseholdBudgetController.java # 予算管理
│   └── HouseholdExpenseController.java # 支出管理  
├── entity/                            # JPAエンティティ
│   ├── HouseholdBudget.java          # 予算エンティティ
│   └── HouseholdExpense.java         # 支出エンティティ
├── repository/                        # データアクセス層
├── service/                          # ビジネスロジック
└── dto/                              # DTO

src/main/resources/
├── application.properties             # 設定
├── data.sql                          # サンプルデータ
├── schema.sql                        # DBスキーマ
└── templates/                        # Thymeleafテンプレート
    ├── budget/                       # 予算管理UI
    ├── expenses/                     # 支出管理UI
    └── fragments/                    # 共通UI部品

src/test/groovy/                      # Spockテスト
```

### DB設定

- **DB種別**: H2ファイルDB
- **ファイル場所**: `./h2db/h2`（自動生成）
- **モード**: MySQL互換
- **スキーマ**: `create-drop`戦略で自動生成
- **サンプルデータ**: `src/main/resources/data.sql`からロード

### 主な依存関係

- `spring-boot-starter-web` - Webフレームワーク
- `spring-boot-starter-data-jpa` - データ永続化
- `spring-boot-starter-thymeleaf` - テンプレートエンジン
- `h2` - 組み込みDB
- `lombok` - コード生成
- `spock-core`, `spock-spring` - テストフレームワーク

### トラブルシューティング

- **8080ポート使用中**: `application.properties`で `server.port=8081` などに変更
- **H2 DB不具合**: `h2db` ディレクトリ削除後、再起動で再作成
- **ビルド失敗**: Java 17インストール・JAVA_HOME設定確認
- **テスト失敗**: 個別Spockテストは `./mvnw test -Dtest=ClassName` で実行
- **Dockerビルド失敗**: 仕様通り。ローカルMavenビルドを使用

### パフォーマンス目安

- **初回ビルド**: 2.5分（依存ダウンロード含む）
- **2回目以降ビルド**: 3-5秒（依存キャッシュ済み）
- **テスト実行**: 53秒
- **アプリ起動**: 3-6秒（JARはやや遅い）
- **JARパッケージング**: 3-39秒（依存状況による）

### CI/CDパイプライン

- **GitHub Actions**: `.github/workflows/build.yml`
- **ビルドコマンド**: `./mvnw clean compile`
- **自動デプロイなし**（手動デプロイ）
- **自動テストなし**（コンパイルのみ）

## 重要な注意事項

- **Mavenコマンドは絶対にキャンセルしないこと**（初回3分以上かかる場合あり）
- 初回ビルドは300秒以上、2回目以降は120秒以上のタイムアウトを設定
- 重要な変更後は必ず手動でアプリをテスト
- Dockerビルドは失敗するため、ローカルMavenビルドのみ使用
- アプリUIは日本語が標準
- H2コンソールはデフォルトで有効（本番では `spring.h2.console.enabled=false` で無効化推奨）
