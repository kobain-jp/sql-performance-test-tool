# sql-performance-test-tool

This tool is to measure sql performance on oracle/postgres and create the report.

[the report example is here](https://github.com/kobain-jp/sql-performance-test-tool/blob/master/sptt-2020-12-12-00:21:06.md)

This tool could do the followings.

- execute specified SQL by multiple threads
- update statistics before executing sql
- report statistics and execution plan

* it is similar to apache jmeter only for sql executing.

### how to lunch

1.configure application.properties

```
# postgres
spring.profiles.active=postgres  // if you use oracle, please change to oracle
spring.datasource.driver-class-name=org.postgresql.Driver // dbclass
spring.datasource.url=jdbc:postgresql://localhost:5432/develop // url for db
spring.datasource.username=develop // user for db
spring.datasource.password=develop // user for password

# sptt
sptt.thread-count=20 // thread count for 1 test
sptt.rampup-duration-millsec=10000 // duration time to lunch thread which is configured
sptt.loop-count=5 // test loop count
sptt.script-path=./testscript // directory for sql script
sptt.report-out-dir=./report // directory for test result report
```

* the above Postgres DB could be created by following command in this project if you have docker
* if you use your own Postgres, need to install pg_stat_statement and pg_store_plans


```
cd docker
docker-compose up 
```
* check read me /docker/README.md


2.create sql script to the path which is specified at sptt.script-path

*currently only select query is supported
*you could check 01.sql as example

3.execute SqlPerformanceTestToolApplication.java

4.check the report

report is generatated to the directory which you specified at sptt.report-out-dir












