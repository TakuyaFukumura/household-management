# household-management
## 起動方法
`$ ./mvnw spring-boot:run`
## 実行可能 Jar の作成
`$ ./mvnw clean package`
## Jar の実行
`$ java -jar ./target/*.jar`
## 組み込みH2DB
### H2コンソールの設定
H2コンソールを利用する場合は`/src/main/resources/application.properties`に`spring.h2.console.enabled=true`を書き加えるか、下記コマンドを実行すること
`$ ./mvnw spring-boot:run -Dspring-boot.run.arguments=--spring.h2.console.enabled=true`
`$ java -jar ./target/*.jar --spring.h2.console.enabled=true`
### H2コンソールの開き方
ローカル環境では下記URLから開くことができる
`http://localhost:8080/h2-console/`
