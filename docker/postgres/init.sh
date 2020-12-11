sed -i -e "s/#shared_preload_libraries = ''.*/shared_preload_libraries = 'pg_stat_statements,pg_store_plans'	# (change requires restart)\npg_stat_statements.max = 10000\npg_stat_statements.track = all\n/g" /var/lib/postgresql/data/postgresql.conf

cd /etc/
wget https://github.com/ossc-db/pg_store_plans/archive/1.4.tar.gz 
tar xvzf 1.4.tar.gz 

cd pg_store_plans-1.4

make USE_PGXS=1
make USE_PGXS=1 install
