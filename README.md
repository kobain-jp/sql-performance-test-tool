# sql-performance-test-tool

sql-performance-test-tool is a spring boot application to measure performance of sql execution on Oracle/Postgres and create report as followings.

[Report for Postgres 12](https://github.com/kobain-jp/sql-performance-test-tool/blob/master/sptt-2020-12-12-00:21:06.md)
[Report for Oracle 12c](https://github.com/kobain-jp/sql-performance-test-tool/blob/master/sptt-2020-12-14-09:24:14.md)


## What can I do with it?

sql-performance-test-tool features include:

- execute specified SQL by multiple threads according to test parameters
- update statistics before executing sql
- report database information and execution results such as statistics and execution plan


## Tutorial

### 1. Database Requirements

* Configured Database is available on /docker folder in this project

https://github.com/kobain-jp/sql-performance-test-tool/tree/master/docker

Postgres:
- pg_stat_statement and pg_store_plans

Oracle:
- grant followings to test user

```
grant select on SYS.V_$SYSTEM_PARAMETER TO test_user;
grant select on SYS.V_$SQLSTATS TO test_user;
grant select on SYS.V_$SQL TO test_user;
grant select on SYS.V_$SQL_PLAN TO test_user;
```


### 2. configure application.properties

Remove comment out for one of the database configuration and specify test parameters

```
# oracle
spring.profiles.active=oracle
spring.datasource.driver-class-name=oracle.jdbc.driver.OracleDriver
spring.datasource.url=jdbc:oracle:thin:@localhost:1521/orclpdb1.localdomain
spring.datasource.username=developer
spring.datasource.password=developer

# postgres
#spring.profiles.active=postgres  // if you use oracle, please change to oracle
#spring.datasource.driver-class-name=org.postgresql.Driver // dbclass
#spring.datasource.url=jdbc:postgresql://localhost:5432/develop // url for db
#spring.datasource.username=develop // user for db
#pring.datasource.password=develop // user for password

# sptt
sptt.thread-count=20 // thread count for 1 test
sptt.rampup-duration-millsec=10000 // duration time to lunch thread which is configured
sptt.loop-count=5 // test loop count
sptt.script-path=./testscript // directory for sql script
sptt.report-out-dir=./report // directory for test result report
```

### 3. create sql script to the path which is specified at sptt.script-path

*currently only select query is supported
*sql script could have only one select query
*you could check 01.sql as example

### 4. execute tool

select jp.kobain.sqlperformancetesttool.SqlPerformanceTestToolApplication and Run As java application or Spring boot App

### 5. check the report

the report is created for each sql script to the directory which you specified at sptt.report-out-dir

report example

[Report for Postgres 12](https://github.com/kobain-jp/sql-performance-test-tool/blob/master/sptt-2020-12-12-00:21:06.md)
[Report for Oracle 12c](https://github.com/kobain-jp/sql-performance-test-tool/blob/master/sptt-2020-12-14-09:24:14.md)











