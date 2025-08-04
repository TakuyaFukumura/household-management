# シンプルなDockerfile - 事前にビルドしたJARを使用
FROM eclipse-temurin:17-jre

WORKDIR /app

# ビルド済みのJARファイルをコピー
COPY target/*.jar app.jar

# H2データベース用のディレクトリを作成
RUN mkdir -p /app/h2db

# アプリケーションポートを公開
EXPOSE 8080

# アプリケーションを起動
ENTRYPOINT ["java", "-jar", "app.jar"]
