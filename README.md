# 👟 2차 팀 프로젝트

---

## 프로젝트 소개

- **개발 기간**: 2024.07.22 ~ 2024.08.09 (3주)
- Spring Boot 기반의 **신발 거래 서비스**입니다.
- 사용자는 구매, 판매, 마이페이지, 상품 등록 등 다양한 기능을 사용할 수 있습니다.
- **JWT**를 활용한 인증, **AWS S3**를 통한 파일 관리 기능이 포함되어 있습니다.
- **RESTful API** 설계를 기반으로 HTTP 메서드(GET, POST, PUT, DELETE)를 통해 클라이언트와 서버 간 상호작용을 처리합니다.
- 비용 문제로 인해 배포서버는 중단되어 있습니다.

---

## 팀원 구성 및 역할

| 이름         | 역할                  | GitHub                                         | 이메일                       |
|------------|---------------------| ---------------------------------------------- | ---------------------------- |
| 😎 조일운(PM, DevOps) | 인증 및 사용자 관리, AWS 배포 | [OneCloudd](https://github.com/OneCloudd)      | whdlfdns12@gmail.com          |
| 😊 이준영(BE) | 물품 조회 및 카테고리 관리     | [junyoung22](https://github.com/junyoung22)    | wnsdud5051@naver.com          |
| 😁 김상윤(BE) | 장바구니 관리             | [94KSY](https://github.com/94KSY)              | -                            |
| 🙂 김선준(BE) | 마이페이지 관리            | [godssun](https://github.com/godssun)          | -                            |
| 🤩 김준규(BE) | 물품 등록 및 재고 관리     | [kjg0223](https://github.com/kjg0223)          | -                            |

---

# 개발 환경

### 주요 기술 스택

- **Spring Boot 3.3.2**
- **Java 17**
- **JPA (Java Persistence API)**
- **Spring Security**
- **JWT (JSON Web Token)**

### IDE
- **IntelliJ* IDEA*

### 데이터베이스

- **MySQL**
- **MariaDB**

### 클라우드

- **AWS EC2**
- **AWS S3**

### 빌드 도구 및 의존성 관리

- **Gradle**

### 버전관리 및 협업 툴
- **Discord**
- **Github**
---
# 프로젝트 구조
```plaintext
project-root/
│
├── src/
│   └── main/
│       ├── java/
│       │   └── com.github.project2/
│       │       ├── config/
│       │       ├── advice/
│       │       ├── controller/
│       │       ├── dto/
│       │       ├── entity/
│       │       ├── filter/
│       │       ├── repository/
│       │       ├── service/
│       │       └── Project2Application.java
│       └── resources/
│           ├── application.yml
│           └── logback-spring.xml
│
├── build.gradle
└── settings.gradle
```
---