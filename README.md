# 순수 함수형 스칼라로 웹 애플리케이션 만들기

[KCD2019](https://kcd2019.festa.io/)

## Prerequisites

- JDK 8+
- [SBT 1.0+](https://www.scala-sbt.org/)
- [Docker](https://www.docker.com)
- [Docker Compose 1.15+](https://docs.docker.com/compose/)

## 데이터베이스 준비

`$ docker-compose up`

## 서버 구동

터미널에서 

`$ sbt run`

또는 sbt 쉘에서 

`sbt:kcd2019> reStart`


## 테스트

데이터베이스 마이그레이션을 위해 서버를 한번 구동시켜야 합니다. (TODO: sbt-flyway 이용해 서버 구동 없이 마이그레이션 지원)

`$ sbt test`

또는

`sbt:kcd2019> test`