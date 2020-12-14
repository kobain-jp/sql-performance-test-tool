FROM postgres:12

RUN apt-get update && apt-get install -y \
  apt-utils \
  wget \
  build-essential \
  postgresql-server-dev-12 && \
  wget https://github.com/ossc-db/pg_store_plans/archive/1.4.tar.gz && \
  tar xvzf 1.4.tar.gz && \
  cd pg_store_plans-1.4 && \
  make USE_PGXS=1 && \
  make USE_PGXS=1 install 
  
  COPY ./docker-entrypoint-initdb.d /docker-entrypoint-initdb.d
