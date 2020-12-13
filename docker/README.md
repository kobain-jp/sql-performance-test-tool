### How to lunch Postgres 

remove the comment out for postgres in docker-compose.yml

```
cd docker
docker-comose up --build
```

### How to lunch Oracle

remove the comment out for oracle in docker-compose.yml

```
cd docker
docker-comose up --build
```
docker exec -it docker_oracle_12c /bin/bash 
sh docker-entrypoint-initdb.d/init.sql