# 简介
提供对服务的各种监控，现阶段包括线程，内存，GC。监控方式有两种：
- `1： 主动获取监控结果 - Thread,Memory`
    每隔固定时间（配置文件的delay值），获取当前服务中的Thread，Memory监控信息
    
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

- [THREAD](#THREAD)  提供对线程的监控.
- [GC](#GC)  提供对GC的监控.
- [MEMORY](#MEMORY)  提供对内存的监控


##   <span id="THREAD">THREAD</span>
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



## <span id="GC">GC</span>
### 获取监控结果
## <span id="MEMORY">MEMORY</span>
### 获取监控结果