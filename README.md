# 简介
提供对服务的各种监控，现阶段包括线程，内存，GC。监控方式有两种：
- `1： 主动获取监控结果 - Thread,Memory`
    每隔固定时间（配置文件的delay值），获取当前服务中的Thread，Memory监控信息
    
- `2： 被动获取监控结果 - GC`
    每当GC动作结束时，获取当前GC的监控信息

获取监控结果的方式也有两种
- `1： 查看监控日志`
    需要根据自己的日志组件，配置固定名称的logger，详见各监控说明。
    - 线程监控（需配置logger名为：onyxia-thread-logger），监控日志举例：（/example/onyxia-thread-biz.log)
    - 内存监控（需配置logger名为：onyxia-memory-logger），监控日志举例：（/example/onyxia-memory-biz.log)
    - GC监控（需配置logger名为：onyxia-gc-logger），监控日志举例：（/example/onyxia-gc-biz.log)
    
- `2： 被动获取监控结果 - GC`
    每当GC动作结束时，获取当前GC的监控信息

要求： jdk版本： 1.7以上，spring boot项目

# 接入方式
1：引入jar包 com.junlong:onyxia-starter:版本号
2: 增加onyxia的配置，例如：

```yaml
#增加监控配置
onyxia:
  #监控开关，默认为false，若在正式环境，想添加监控功能，需设置为true
  mainSwitch: true
  #监控功能列表
  monitorMenuList:
    #monitorMenu THREAD：线程监控 GC：GC监控 MEMORY：内存监控
    #delay: 监控时间间隔，单位：毫秒。 默认值：1000。GC监控是通过MBean的listener，所以GC监控配置的delay不会起作用
    - monitorMenu: THREAD
      delay: 1000
    - monitorMenu: MEMORY
      delay: 1000
    - monitorMenu: GC
      delay: 1000
```

[版本号列表](/CHANGELOG.md)

# 监控模块

- [线程监控](#THREAD)  提供对线程的监控.
- [GC监控](#GC)  提供对GC的监控.
- [内存监控](#MEMORY)  提供对内存的监控


##   <h2 id="THREAD">THREAD</h2>
### 获取监控结果
 1. 日志
    配置一个名为"onyxia-thread-logger"的logger，监控结果会保存在相应的日志中。
    以log4j2为例，配置如下：
    
```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="debug">
	<Properties>
		<Property name="log-path">F:\logs
		</Property>
	</Properties>
	<Appenders>
		<Console name="console" target="SYSTEM_OUT">
		</Console>
		<!-- 线程监控日志 -->
		<RollingRandomAccessFile name="thread-appender"
								 fileName="${log-path}/onyxia-thread-biz.log"
								 immediateFlush="true"
								 filePattern="${log-path}/onyxia-thread-biz.log.%d{yyyyMMdd}">
			<Policies>
				<TimeBasedTriggeringPolicy interval="1"
										   modulate="true" />
			</Policies>
		</RollingRandomAccessFile>
	</Appenders>
	<Loggers>
		<Logger name="onyxia-thread-logger" includeLocation="true" additivity="false">
			<AppenderRef ref="thread-appender" />
		</Logger>
		<Root level="INFO" includeLocation="true">
			<AppenderRef ref="console" />
		</Root>
	</Loggers>
</Configuration>
```
日志文件示例：

```log
[2018-02-23 00:09:52,448][onyxia-THREAD-1][INFO][onyxia-thread-logger:34] ThreadInfoEntity{activityCount=18, peakCount=18, totalStartedThreadCount=20, daemonCount=16}
[2018-02-23 00:09:53,483][onyxia-THREAD-1][INFO][onyxia-thread-logger:34] ThreadInfoEntity{activityCount=19, peakCount=19, totalStartedThreadCount=22, daemonCount=17}
```

 2. 回调方法获取  
    实现ThreadResultCallback接口，并在当前类加@OnyxiaCallback注解，monitorMenu属性填写MonitorMenuEnum.THREAD,重写doCallback方法，参数ThreadInfoEntity是监控结果。例如：
```
@OnyxiaCallback(monitorMenu = MonitorMenuEnum.THREAD)
public class ThreadCallback implements ThreadResultCallback {
    @Override
    public void doCallback(ThreadInfoEntity monitorResult) {
        //拿到监控结果后，根据自己的业务逻辑，实现相关逻辑
        System.out.println(monitorResult);
    }
}

```
3. 监控结果ThreadInfoEntity对象属性

| 属性名        | 类型           | 含义  |
| ------------- |:-------------:| -----:|
| activityCount      | int | 当前活动线程数 |
| peakCount      | int      |  峰值线程数 |
| totalStartedThreadCount | long      |   线程总数 |
| daemonCount | int      |    守护线程数 |



## <h2 id="GC">GC</h2>
### 获取监控结果
1. 日志
    配置一个名为"onyxia-gc-logger"的logger，监控结果会保存在相应的日志中。
    以log4j2为例，配置如下：
    
```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="debug">
	<Properties>
		<Property name="log-path">F:\logs
		</Property>
	</Properties>
	<Appenders>
		<Console name="console" target="SYSTEM_OUT">
		</Console>
		<!-- 线程监控日志 -->
		<RollingRandomAccessFile name="gc-appender"
								 fileName="${log-path}/onyxia-gc-biz.log"
								 immediateFlush="true"
								 filePattern="${log-path}/onyxia-gc-biz.log.%d{yyyyMMdd}">
			<Policies>
				<TimeBasedTriggeringPolicy interval="1"
										   modulate="true" />
			</Policies>
		</RollingRandomAccessFile>
	</Appenders>
	<Loggers>
		<Logger name="onyxia-gc-logger" includeLocation="true" additivity="false">
			<AppenderRef ref="gc-appender" />
		</Logger>
		<Root level="INFO" includeLocation="true">
			<AppenderRef ref="console" />
		</Root>
	</Loggers>
</Configuration>
```
日志文件示例：

```log
[2018-02-23 00:10:41,606][Service Thread][INFO][onyxia-gc-logger:88] GcInfoEntity{name='end of minor GC', count=18, gcCause='Allocation Failure', startTime='2018-02-23 00:10:41.466', endTime='2018-02-23 00:10:41.473', beforeMemory=MemoryCommonEntity{committed=50196480, init=0, max=-1, used=48762712}, afterMemory=MemoryCommonEntity{committed=50196480, init=0, max=-1, used=48762712}, GCTime=0}
```

 2. 回调方法获取  
    实现GcResultCallback接口，并在当前类加@OnyxiaCallback注解，monitorMenu属性填写MonitorMenuEnum.GC,重写doCallback方法，参数GcInfoEntity是监控结果。例如：
```
@OnyxiaCallback(monitorMenu = MonitorMenuEnum.GC)
public class GCCallback implements GcResultCallback {
    @Override
    public void doCallback(GcInfoEntity monitorResult) {
 //拿到监控结果后，根据自己的业务逻辑，实现相关逻辑
        System.out.println(monitorResult);
    }
}

```
3. GC监控结果属性  
  
GcInfoEntity对象  
参考：[GarbageCollectionNotificationInfo](https://docs.oracle.com/javase/7/docs/jre/api/management/extension/com/sun/management/GarbageCollectionNotificationInfo.html)
参考：[GcInfo](https://docs.oracle.com/javase/7/docs/jre/api/management/extension/com/sun/management/GcInfo.html)

| 属性名        | 类型           | 含义  |
| ------------- |:-------------:| -----:|
| name      | string | 标识是哪个gc动作，一般为：end of major GC，Young Gen GC等，分别表示老年代和新生代的gc结束 |
| count      | long      |  	标识这个收集器进行了几次gc |
| gcCause | String      |   引起gc的原因,如：System.gc()，Allocation Failure，G1 Humongous Allocation等 |
| startTime | String      |    gc的开始时间 |
| endTime | String      |    gc的结束时间 |
| beforeMemory | MemoryCommonEntity      |    gc前内存情况 |
| afterMemory | MemoryCommonEntity      |    gc后内存情况 |
| GCTime | long      |    GC耗时，单位：毫秒 |
   
   
MemoryCommonEntity对象  
参考：[MemoryUsage](https://docs.oracle.com/javase/7/docs/api/java/lang/management/MemoryUsage.html)


| 属性名        | 类型           | 含义  |
| ------------- |:-------------:| -----:|
| committed      | long | 已申请内存 |
| init      | long      |  	初始化内存 |
| max | long      |   最大内存，收集器有可能不会记录，若不记录，则为-1 |
| used | long      |    已使用内存 |



## <h2 id="MEMORY">MEMORY</h2>
### 获取监控结果
1. 日志
    配置一个名为"onyxia-memory-logger"的logger，监控结果会保存在相应的日志中。
    以log4j2为例，配置如下：
    
```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="debug">
	<Properties>
		<Property name="log-path">F:\logs
		</Property>
	</Properties>
	<Appenders>
		<Console name="console" target="SYSTEM_OUT">
		</Console>
		<!-- 线程监控日志 -->
		<RollingRandomAccessFile name="memory-appender"
								 fileName="${log-path}/onyxia-memory-biz.log"
								 immediateFlush="true"
								 filePattern="${log-path}/onyxia-memory-biz.log.%d{yyyyMMdd}">
			<Policies>
				<TimeBasedTriggeringPolicy interval="1"
										   modulate="true" />
			</Policies>
		</RollingRandomAccessFile>
	</Appenders>
	<Loggers>
		<Logger name="onyxia-memory-logger" includeLocation="true" additivity="false">
			<AppenderRef ref="memory-appender" />
		</Logger>
		<Root level="INFO" includeLocation="true">
			<AppenderRef ref="console" />
		</Root>
	</Loggers>
</Configuration>
```
日志文件示例：

```log
MemoryInfoEntity{headMomory=MemoryCommonEntity{committed=116391936, init=134217728, max=116391936, used=60901928}, noHeadMomory=MemoryCommonEntity{committed=76804096, init=2555904, max=-1, used=73968608}, memoryPoolInfoEntityList=[MemoryPoolInfoEntity{peakMemroy=MemoryCommonEntity{committed=20447232, init=2555904, max=251658240, used=20372352}, currentMemory=MemoryCommonEntity{committed=20447232, init=2555904, max=251658240, used=19354368}, name='Code Cache', managerName='[CodeCacheManager]', objectName='java.lang:type=MemoryPool,name=Code Cache'}, MemoryPoolInfoEntity{peakMemroy=MemoryCommonEntity{committed=49934336, init=0, max=-1, used=48671120}, currentMemory=MemoryCommonEntity{committed=49934336, init=0, max=-1, used=48671120}, name='Metaspace', managerName='[Metaspace Manager]', objectName='java.lang:type=MemoryPool,name=Metaspace'}, MemoryPoolInfoEntity{peakMemroy=MemoryCommonEntity{committed=6422528, init=0, max=1073741824, used=5972976}, currentMemory=MemoryCommonEntity{committed=6422528, init=0, max=1073741824, used=5972976}, name='Compressed Class Space', managerName='[Metaspace Manager]', objectName='java.lang:type=MemoryPool,name=Compressed Class Space'}, MemoryPoolInfoEntity{peakMemroy=MemoryCommonEntity{committed=50331648, init=50331648, max=50331648, used=50331648}, currentMemory=MemoryCommonEntity{committed=34603008, init=50331648, max=34603008, used=26128472}, name='PS Eden Space', managerName='[PS MarkSweep, PS Scavenge]', objectName='java.lang:type=MemoryPool,name=PS Eden Space'}, MemoryPoolInfoEntity{peakMemroy=MemoryCommonEntity{committed=14680064, init=8388608, max=14680064, used=14223680}, currentMemory=MemoryCommonEntity{committed=14680064, init=8388608, max=14680064, used=14223680}, name='PS Survivor Space', managerName='[PS MarkSweep, PS Scavenge]', objectName='java.lang:type=MemoryPool,name=PS Survivor Space'}, MemoryPoolInfoEntity{peakMemroy=MemoryCommonEntity{committed=67108864, init=67108864, max=67108864, used=20896176}, currentMemory=MemoryCommonEntity{committed=67108864, init=67108864, max=67108864, used=20896176}, name='PS Old Gen', managerName='[PS MarkSweep]', objectName='java.lang:type=MemoryPool,name=PS Old Gen'}]}
```

 2. 回调方法获取  
    实现MemoryResultCallback接口，并在当前类加@OnyxiaCallback注解，monitorMenu属性填写MonitorMenuEnum.MEMORY,重写doCallback方法，参数GcInfoEntity是监控结果。例如：

```
@OnyxiaCallback(monitorMenu = MonitorMenuEnum.MEMORY)
public class MemoryCallback implements MemoryResultCallback {
    @Override
    public void doCallback(MemoryInfoEntity monitorResult) {
     //拿到监控结果后，根据自己的业务逻辑，实现相关逻辑
        System.out.println(monitorResult);
    }
}
```
3. 监控结果属性  
  
MemoryInfoEntity对象  

| 属性名        | 类型           | 含义  |
| ------------- |:-------------:| -----:|
| headMomory      | MemoryCommonEntity | 堆内存 |
| noHeadMomory      | MemoryCommonEntity      |  	非堆内存 |
| memoryPoolInfoEntityList | List<MemoryPoolInfoEntity>      |   各个区域内存数据 |

   
   
MemoryCommonEntity对象  

参考：[MemoryUsage](https://docs.oracle.com/javase/7/docs/api/java/lang/management/MemoryUsage.html)


| 属性名        | 类型           | 含义  |
| ------------- |:-------------:| -----:|
| committed      | long | 已申请内存 |
| init      | long      |  	初始化内存 |
| max | long      |   最大内存，收集器有可能不会记录，若不记录，则为-1 |
| used | long      |    已使用内存 |  

MemoryPoolInfoEntity  
参考：[MemoryPoolMXBean](https://docs.oracle.com/javase/7/docs/api/java/lang/management/MemoryPoolMXBean.html)

| 属性名        | 类型           | 含义  |
| ------------- |:-------------:| -----:|
| peakMemroy      | MemoryCommonEntity | 峰值内存 |
| currentMemory      | MemoryCommonEntity      |  	当前内存区域 |
| managerName | String      |   所属管理者名称,例如：[PS MarkSweep, PS Scavenge] |
| objectName | String      |    objectName，例如：java.lang:type=MemoryPool,name=PS Eden Space |
| name | String      |   name，例如：PS Survivor Space |