# Boogakcong-BE

### Backend
- **Java 17** 
- **Spring Boot v3.4.0**
- **Gradle 7.5 이상**
- **Spring Security & JWT** 
- **Spring Data JPA** 
- **PostgreSQL** 
- **AWS S3**

### How to Run

`/resources/application.yml`에 키값 정보를 채우고, PostgreSQL의 host, password를 알맞게 설정합니다.

설정된 프로그램 기준
```
url: jdbc:postgresql://localhost:5432/postgres
username: postgres
password: 123456
```

```
git clone https://github.com/ZoJim/Boogakcong-BE.git
```

```
./gradlew build
./gradlew bootRun
```
