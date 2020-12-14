# SQL Performance Tool Report - ${testDateTime}

## SQL

```
${sql}
```

## Test Parameter

```
threadCount:${testParamThreadCount?string("0")}
rampUpDurationMills:${testParamRampUpDurationMills?string("0")}
loopCount:${testParamLoopCount?string("0")}
```

## Database information

Product information

```
<#list productInfo as line>
${line}
</#list>
```

Configuration

```
<#list configInfo as line>
${line}
</#list>
```
### Execute Result on JVM

```
sql:${jvmExecutionResult.sql}
executionCount:${jvmExecutionResult.executionCount?string("0")}
averageExecutionTime:${jvmExecutionResult.averageExecutionTime?string("0")}
```

### Execute Result on DB

Statistics

```
<#list statistics as line>
${line}
</#list>
```

Execution Plan

```
<#list executionPlan as line>
${line}
</#list>
```
