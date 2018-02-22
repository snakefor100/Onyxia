# 简介
提供GC，内存，线程的监控，为服务保驾护航。
要求： jdk版本： 1.7以上，spring boot项目

# 接入方式
1：引入jar包 com.junlong:onyxia-starter:版本号
2: 增加onyxia的配置，例如：

```
#增加监控配置
onyxia:
  #监控开关，默认为false，若在正式环境，想添加监控功能，需设置为true
  mainSwitch: true
  #监控功能列表
  monitorMenuList:
    #monitorMenu THREAD：线程监控 GC：GC监控 MEMORY：内存监控
    #delay: 监控时间间隔，单位：毫秒。 默认值：1000
    - monitorMenu: THREAD
      delay: 1000
    - monitorMenu: MEMORY
      delay: 1000
    - monitorMenu: GC
      delay: 1000
```

[版本号列表](/CHANGELOG.md)

# 监控模块

- [THREAD](#THREAD)提供对线程的监控.
- [GC](#GC)提供对GC的监控.
- [MEMORY](#MEMORY)提供对内存的监控

## [THREAD]
sds

## [GC]
dd
## [MEMORY]
ee