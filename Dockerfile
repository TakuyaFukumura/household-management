# Multi-stage Dockerfile - Docker内でビルドと実行の両方に対応
# 
# ビルド用ファイル: Dockerfile.build を使用すると Docker内でビルドが可能
# デフォルト: 事前ビルド済みJARファイルを使用（推奨）

# 事前ビルド用（メイン）
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
