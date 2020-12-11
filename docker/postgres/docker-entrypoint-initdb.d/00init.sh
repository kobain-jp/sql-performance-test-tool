sed -i -e "s/#shared_preload_libraries = ''.*/shared_preload_libraries = 'pg_stat_statements,pg_store_plans'	# (change requires restart)\npg_stat_statements.max = 10000\npg_stat_statements.track = all\n/g" /var/lib/postgresql/data/postgresql.conf

