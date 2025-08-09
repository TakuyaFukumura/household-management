# Multi-stage Dockerfile - Docker内でビルドから実行まで対応
# ビルドステージ
FROM maven:3.9.9-eclipse-temurin-17 AS builder

WORKDIR /app

# 証明書と依存関係の問題を解決
RUN apt-get update && apt-get install -y ca-certificates && update-ca-certificates

# プロジェクトファイルをすべてコピー
COPY pom.xml ./
COPY src ./src

# アプリケーションをビルド（証明書検証を緩和）
RUN mvn clean package -DskipTests -B -Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true

# 実行ステージ
FROM eclipse-temurin:17-jre

WORKDIR /app

# ビルドステージからJARファイルをコピー
COPY --from=builder /app/target/*.jar app.jar

# H2データベース用のディレクトリを作成
RUN mkdir -p /app/h2db

# アプリケーションポートを公開
EXPOSE 8080

# アプリケーションを起動
ENTRYPOINT ["java", "-jar", "app.jar"]