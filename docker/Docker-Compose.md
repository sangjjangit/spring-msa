# Docker Compose

- 여러 컨테이너를 하나의 서비스로 실행하거나 다른 컨테이너를 동시에 생성할 수 있다.
- YAML파일로 생성해서 어플리케이션 서비스를 구성한다.
- docker-compose.yml로 지정해야 한다.
- docker compose config 명령으로 파일 유효성을 확인한다.
- docker compose up 명령으로 서비스를 시작한다.
- 예제
```
version: <docker-compose-version> 
services: 
    database:
        image: <database-docker-image-name> 
        ports: 
            - "<databasePort>:<databasePort>" 
        environment: 
            POSTGRES_USER: <databaseUser>
            POSTGRES_PASSWORD: <databasePassword>
            POSTGRES_DB: <databaseName>

    <service-name>:
        image: <service-docker-image-name> 
        ports: 
            - "<applicationPort>:<applicationPort>" 
        environment: 
            PROFILE: <profile-name> 
            DATABASESERVER_PORT: "<databasePort>"
        container_name: <container_name>
            networks:
            backend:
            aliases:
                - "alias" 
networks:
    backend:
        driver: bridge
```

- version

도커 컴포즈 도구의 버전을 지정한다.

- service

배포할 서비스를 지정한다.

서비스 이름은 도커 인스턴스에 대한 DNS 엔트리이며, 다른 서비스에서 액세스하는 데 사용된다.

- image

특정 이미지를 사용하여 컨테이너를 실행하도록 지정한다.

- port 

시작한 도커 컨테이너가 외부에 노출할 포트 번호를 지정한다. 내부/외부포트를 매핑한다.

- environment

시작하는 도커 이미지에 환경 변수를 전달한다.

- network

복작한 토폴로지를 만들 수 있도록 커스텀 네트워크를 지정한다.

타입(host, overlay, macvlan, none)을 지정하지 않았다면, 디폴트 타입은 bridge다.

브리지(bridge)는 동일한 네트워크 내 컨테이너 연결을 관리할 수 있고, 이 네트워크는 동일한 도커 데몬 호스트에서 실행되는 컨테이너에만 적용된다.

- alias

네트워크 내 서비스에 대한 호스트 별명을 지정한다.

---
- 명령어

```
# 컨테이너 시작
docker compose up -d
# 컨테이너 로그
docker compose logs
docker compose logs <service_id>
# 컨테이너 목록
docker compose ps
# 서비스 중지, 컨테이너 중지
docker compose stop
# 모든 것을 종료, 컨테이너도 모두 제거
docker compose down 
```