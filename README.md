# 概览

> **PrivacyMethodHooker** 是一款为了保证Android端隐私合规而开发的隐私API调用监控的库，
其目标主要you两个：
>
> 1. 监控App和三方SDK收集用户隐私（调用隐私API）行为，在用户同意隐私协议之前，违规收集用户信息的行为将会在开发阶段就被暴漏出来；
> 2. 解决三方SDK频繁索权（多次调用隐私API）问题，针对隐私API的返回值做缓存，只有第一次调用隐私API需要通过系统API。


# 开发背景

<br/>

# 功能模块

<br/>


# 最佳实践

<br/>


## 频繁索权优化效果

<br/>


# 不止与隐私合规

在框架设计之初，考虑到字节码插桩应该做成通用的库，让业务方可以无侵入性实现方法替换、类替换，实现性能监控

## 替换Method


<br/>

## 替换Class

<br/>

# 实现原理

# 隐私API

## TelePhonyManager

-  getImei
-  getDeviceId
-  getSubscriberId
-  getSimSerialNumber
-  getMeid

这些API只能系统App调用，target 29 以上调用会抛异常，target 28或者以下会返回null

- getLine1Number
- getCellLocation
- getSimOperator
- getSimOperatorName
- getSimCountryIso
- getNetworkOperator
- getNetworkOperatorName
- getNetworkCountryIso

## WifiInfo
- getIpAddress
- getSSID
- getBSSID
- getMacAddress

后面三个需要开启定位才能获取到真实的。

## WifiManager
- getScanResults
需要开启定位才能获取

- getDhcpInfo
- getConnectionInfo

## BluetoothAdapter
- getAddress
- getName

## BluetoothAdapter
- getAddress
- getName


## Settings
Settings$System#getString(android_id)
Settings$Secure#getString(android_id)
Settings$Secure#getString(bluetooth_address)
Settings$Secure#getString(bluetooth_name)

## Runtime


# 友情链接

1. [ASM hook隐私方法调用，防止App被下架](https://juejin.cn/post/7043399520486424612#heading-0) 蓝师傅之前在其它公司的实现方案，以源码形式集成，没有发布到远程仓库，不支持多个项目快速接入，不支持替换Class，扩展性也没那么高。
2. [Booster](https://github.com/didi/booster) 是一款专门为移动应用设计的易用、轻量级且可扩展的质量优化框架，其目标主要是为了解决随着 APP 复杂度的提升而带来的性能、稳定性、包体积等一系列质量问题。Booster 提供了性能检测、多线程优化、资源索引内联、资源去冗余、资源压缩、系统 Bug 修复等一系列功能模块，可以使得稳定性能够提升 15% ~ 25%，包体积可以减小 1MB ~ 10MB。插件的底层基于Booster，适配几乎所有Gradle版本。
3. 我们是xx团队，目前该框架由蓝晓彬（蓝师傅）一个人开发，期待后续有更多同学加入，一起维护这个项目