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

# 查看监控结果

## [THREAD]
### 获取监控结果
 1. 日志
    配置
 2. 回调方法获取
    fas
## [GC]
### 获取监控结果
## [MEMORY]
### 获取监控结果