# 概览

**PrivacyMethodHooker**是一个隐私API 调用监控的库。

**功能介绍：**

> 1、监控App和三方SDK收集用户隐私（调用隐私API）行为，在用户同意隐私协议之前，违规收集用户信息的行为将会被暴露出来；
2、解决三方SDK频繁索权（高频率调用隐私API）问题，针对隐私API的返回值做缓存，第二次调用可以直接从缓存中读取并返回。（默认只会缓存那些返回值固定的隐私API）；
3、可扩展，通过注解和插件，可以实现对某个方法的hook，以及某个类的hook。

# 功能模块

- **注解库**：method_replace_annotation
  > 通过注解描述方法替换信息，结合method_replace_plugin使用。
- **方法替换插件**：method_replace_plugin
  > 扫描注解，收集要替换的方法信息，然后通过ASM修改原方法字节码，替换成目标方法。
- **隐私API Hook库**：privacy_method_replace_library
  > 基于注解库和方法替换插件，枚举隐私API并进行hook，暴露一些业务方可能需要用到的接口。

# 实现效果

## 1.监控同意隐私协议前的隐私API调用行为

第一次打开app，没有同意隐私协议之前，我调用了`TelePhonyManagerUtil.getCellLocation()`，可以看到toast提示

![](https://files.catbox.moe/7p62xa.png)

## 2.解决频繁索权问题

在TT语音项目接入，打开App，过滤日志 **onPrivacyMethodCall**
![](https://files.catbox.moe/s5igo9.png)

可以看到 **TelephonyManager#getSimOperator** 这个方法被调用了9次，除了第一次，后面的调用都是读缓存，这样隐私API检测平台只会检测到第一次读取。

# 最佳实践

## 快速接入（依赖和初始化）

**version = 1.0.3**

[点击查看最新版本]

[ChangeLog](CHANGELOG.md)

### 1.添加依赖

1. 在项目根目录的build.gradle中声明插件的classpath
   ```
   classpath 'com.lanshifu:method_replace_plugin:{version}'
   ```
2. 在App主工程的build.gradle中使用插件
   ```
   apply plugin: 'com.lanshifu.method_replace_plugin'
   ```
3. 在App主工程的build.gradle中依赖隐私方法hook库
   ```
   implementation 'com.lanshifu:privacy_method_replace_library:{version}'
   ```


### 2.初始化

**在Application的onCreate方法最前面**调用`PrivacyMethodManager.init`进行初始化。

```kotlin
class Myapp : Application() {

    override fun onCreate() {
        //尽量早初始化
        initPrivacyManager(this)
        super.onCreate()
    }

    private fun initPrivacyManager(context: Context) {

        PrivacyMethodManager.init(context, object : DefaultPrivacyMethodManagerDelegate() {

            override fun isDebugMode(): Boolean {
                // 开发阶段返回true，生产包返回false
                return BuildConfig.FLAVOR == "internal" || BuildConfig.DEBUG
            }

            override fun isAgreePrivacy(): Boolean {
                // 是否同意隐私协议（每个App可能获取方式不一样）
                return PrivacyHelper.isAgree
            }

        })
    }

}
```

第二个参数是传一个代理，**DefaultPrivacyMethodManagerDelegate** 是默认的代理实现类，**isDebugMode**和**isAgreePrivacy**需要自定义实现，其它的为可选。

<br/>

到此，就完成此库的接入了，

此外，提供了可选的高级设置如下：

## 高级设置

高级设置都是通过重写 `DefaultPrivacyMethodManagerDelegate` 的方法来实现，`IPrivacyMethodManagerDelegate` 接口定义的所有方法都可以按需重写。

```kotlin
interface IPrivacyMethodManagerDelegate {

    /**
     * 是否同意隐私协议
     */
    fun isAgreePrivacy(): Boolean

    /**
     * debug 模式会弹toast
     */
    fun isDebugMode(): Boolean

    /**
     * 是否使用缓存
     * @param methodName: 对应某个隐私API方法名
     * @param callerClassName: 调用者类名
     */
    fun isUseCache(methodName: String, callerClassName: String): Boolean

    /**
     * 是否显示隐私方法调用堆栈，默认情况下只有违规调用才会打印堆栈
     *
     */
    fun isShowPrivacyMethodStack(): Boolean

    /**
     * 每一次隐私调用都会回调
     * @param methodName: 对应某个隐私API方法名
     * @param callerClassName: 调用者类名
     */
    fun onPrivacyMethodCall(callerClassName: String, methodName: String, methodStack: String)

    /**
     * 隐私调用违规的时候回调
     * @param methodName: 对应某个隐私API方法名
     * @param callerClassName: 调用者类名
     */
    fun onPrivacyMethodCallIllegal(callerClassName: String, methodName: String, methodStack: String)

    /**
     * 缓存过期回调
     * @param methodName: 对应某个隐私API方法名
     */
    fun onCacheExpire(methodName: String)

    /**
     * 自定义缓存过期时间，key是方法名，value是多少秒
     */
    fun customCacheExpireTime(): HashMap<String, Int>

    /**
     * 自定义缓存框架，默认是HashMap
     */
    fun customCacheImpl(): IPrivacyMethodCache?

    /**
     * 自定义日志输出
     */
    fun customLogImpl(): IPrivacyMethodLog?
```

### 1.隐私API缓存配置

可以设置是否需要缓存

#### 重写isUseCache

重写**DefaultPrivacyMethodManagerDelegate**的**isUseCache**方法，可以设置对某个隐私API做缓存（返回true）或者不缓存（返回fanse）。

```kotlin
override fun isUseCache(methodName: String, callerClassName: String): Boolean {

    //自定义是否要使用缓存，methodName可以在日志找到，过滤一下 onPrivacyMethodCall 关键字
    when (methodName) {
        "TelephonyManager#getSimCountryIso",
        "TelephonyManager#getSimOperator",
        "TelephonyManager#getSimOperatorName",
        "TelephonyManager#getNetworkOperator",
        "TelephonyManager#getNetworkOperatorName",
        "TelephonyManager#getNetworkCountryIso",
        "TelephonyManager#getLine1Number",
        "TelephonyManager#getCellLocation",

        "LocationManager#getLastKnownLocation",

        "PackageManager#getInstalledPackages",
        "PackageManager#getInstalledApplications",

        "WifiManager#getConnectionInfo",
        "WifiManager#getScanResults",
        "WifiManager#getDhcpInfo",

        "ActivityManager#getRunningServices",
        "ActivityManager#getRecentTasks",
        "ActivityManager#getRunningTasks",
        "ActivityManager#getRunningAppProcesses" -> return true

        "ClipboardManager#getPrimaryClip" -> {
            if (!isAppInnerCall(callerClassName)) {
                return true
            }
        }
    }

    return super.isUseCache(methodName, callerClassName)

}
```

**也可以只针对三方SDK的调用做缓存，通过callerClassName来区分三方SDK调用。**

#### 自定义缓存时长 customCacheExpireTime

如果你希望设置缓存时长，可以重写**customCacheExpireTime**方法，返回一个HashMap，**key是方法名，value是秒数**，默认如下所示：

```kotlin
    override fun customCacheExpireTime(): HashMap<String, Int> {

        //设置缓存过期时间，解决频繁索权问题，同时不能影响业务
        // 对业务可能有影响的，时间设置短一点， 否则时间长一点
        val defaultCacheExpireTimeSort = 5
        val defaultCacheExpireTimeLong = 60 * 2

        return HashMap<String, Int>().apply {
            //缓存过期时间(秒)
            this["TelephonyManager#getSimCountryIso"] = defaultCacheExpireTimeLong
            this["TelephonyManager#getSimOperator"] = defaultCacheExpireTimeLong
            this["TelephonyManager#getSimOperatorName"] = defaultCacheExpireTimeLong
            this["TelephonyManager#getNetworkOperator"] = defaultCacheExpireTimeLong
            this["TelephonyManager#getNetworkOperatorName"] = defaultCacheExpireTimeLong
            this["TelephonyManager#getNetworkCountryIso"] = defaultCacheExpireTimeLong
            this["TelephonyManager#getLine1Number"] = defaultCacheExpireTimeSort
            this["TelephonyManager#getCellLocation"] = defaultCacheExpireTimeSort

            this["LocationManager#getLastKnownLocation"] =
                defaultCacheExpireTimeSort //获取定位的频率

            this["PackageManager#getInstalledPackages"] = defaultCacheExpireTimeLong
            this["PackageManager#getInstalledApplications"] = defaultCacheExpireTimeLong

            this["WifiManager#getConnectionInfo"] = defaultCacheExpireTimeSort
            this["WifiManager#getScanResults"] = defaultCacheExpireTimeSort
            this["WifiManager#getDhcpInfo"] = defaultCacheExpireTimeSort

            this["WifiInfo#getIpAddress"] = defaultCacheExpireTimeSort
            this["WifiInfo#getSSID"] = defaultCacheExpireTimeSort
            this["WifiInfo#getBSSID"] = defaultCacheExpireTimeSort
            this["WifiInfo#getMacAddress"] = defaultCacheExpireTimeSort

            this["ActivityManager#getRunningAppProcesses"] = defaultCacheExpireTimeLong
            this["ActivityManager#getRunningServices"] = defaultCacheExpireTimeLong
            this["ActivityManager#getRecentTasks"] = defaultCacheExpireTimeLong
            this["ActivityManager#getRunningTasks"] = defaultCacheExpireTimeLong

            this["ClipboardManager#getPrimaryClip"] =
                defaultCacheExpireTimeLong //限制三方sdk剪贴板读取频率
        }
    }
```

#### 自定义缓存实现 customCacheImpl

默认缓存是`HashMap+SharedPreference`，字符串类型会存一份到 `SharedPreference`，杀进程重启能够复用缓存。

可以通过重写 customCacheImpl方法，来自定义缓存实现。

#### 支持的隐私API

以下隐私方法名可以按需设置缓存、缓存时长

```
"Settings\$System#getString(android_id)",
"Settings\$Secure#getString(android_id)",
"Settings\$Secure#getString(bluetooth_address)",
"Settings\$Secure#getString(bluetooth_name)",
"NetworkInterface#getHardwareAddress",
"SensorManager#getSensorList",
"TelephonyManager#getImei",
"TelephonyManager#getImei(0)",
"TelephonyManager#getImei(1)",
"TelephonyManager#getDeviceId",
"TelephonyManager#getDeviceId(0)",
"TelephonyManager#getDeviceId(1)",
"TelephonyManager#getSubscriberId",
"TelephonyManager#getSimSerialNumber",
"TelephonyManager#getMeid",
"TelephonyManager#getSimCountryIso",
"TelephonyManager#getSimOperator",
"TelephonyManager#getSimOperatorName",
"TelephonyManager#getNetworkOperator",
"TelephonyManager#getNetworkOperatorName",
"TelephonyManager#getNetworkCountryIso",
"TelephonyManager#getLine1Number",
"TelephonyManager#getCellLocation",

"LocationManager#getLastKnownLocation",

"PackageManager#getInstalledPackages",
"PackageManager#getInstalledApplications",

"WifiManager#getConnectionInfo",
"WifiManager#getScanResults",
"WifiManager#getDhcpInfo",

"WifiInfo#getIpAddress",
"WifiInfo#getSSID",
"WifiInfo#getBSSID",
"WifiInfo#getMacAddress",

"ActivityManager#getRunningServices",
"ActivityManager#getRecentTasks",
"ActivityManager#getRunningTasks",
"ActivityManager#getRunningAppProcesses"
```

以下这几个API返回值一般是不变的，默认做了持久化缓存。

```
  "Settings\$System#getString(android_id)",
  "Settings\$Secure#getString(android_id)",
  "Settings\$Secure#getString(bluetooth_address)",
  "Settings\$Secure#getString(bluetooth_name)",
  "NetworkInterface#getHardwareAddress",
  "SensorManager#getSensorList",
  // 下面这几个Android 10之后都不会调用
  "TelephonyManager#getImei",
  "TelephonyManager#getImei(0)",
  "TelephonyManager#getImei(1)",
  "TelephonyManager#getDeviceId",
  "TelephonyManager#getDeviceId(0)",
  "TelephonyManager#getDeviceId(1)",
  "TelephonyManager#getSubscriberId",
  "TelephonyManager#getSimSerialNumber",
  "TelephonyManager#getMeid",
```

### 2.隐私违规回调

onPrivacyMethodCallIllegal 回调，默认是弹toast，如果不想要toast，可以重写，替换成其它的，例如弹窗

```kotlin
    override fun onPrivacyMethodCallIllegal(
        callerClassName: String,
        methodName: String,
        methodStack: String
    ) {
        LogUtil.e(
            TAG,
            "onPrivacyMethodCallIllegal,callerClassName=$callerClassName，methodName=$methodName,methodStack=$methodStack"
        )

        if (PrivacyMethodManager.getDelegate().isDebugMode()) {
            PrivacyMethodManager.mContext?.let {
                Toast.makeText(
                    it, "存在审核风险【调用了隐私API】\nmethodName: $methodName\nclassName: $callerClassName",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
```

### 3.重定向日志

复用App的日志框架，重写**customLogImpl**：

```kotlin
override fun customLogImpl(): IPrivacyMethodLog? {
    return CustomLogImpl()
}

/**
 * 自定义日志输出
 */
class CustomLogImpl : IPrivacyMethodLog {
    override fun d(tag: String, message: String) {
        Log.d(tag, message)
    }

    override fun e(tag: String, message: String) {
        Log.e(tag, message)
    }

    override fun i(tag: String, message: String) {
        Log.i(tag, message)
    }

    override fun w(tag: String, message: String) {
        Log.w(tag, message)
    }

}
```

其它的参考demo的MyApp。

# 扩展

在框架设计之初，考虑到字节码插桩可以做成通用的库，业务方可以通过注解和插件来自定义方法替换、类替换功能，实现SDK bug修复、性能监控等功能。

引入注解库：

> implementation 'com.lanshifu:method_replace_annotation:{version}'

## 替换Method

通过`AsmMethodReplace` 注解支持将**原方法**的调用**替换成另一个方法**调用，实现hook功能。

<br/>

**对于成员方法，hook的规则参考如下：**

假设原方法：`Class1#method1(param1: String,param2: String) : String`

hook 方式如下：

```kotlin
object HookTest {

    @AsmMethodReplace(
          oriClass = Class1::class,
          oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
      )
    fun method1(class1: Class1, param1: String, param2: String,callerClassName: String): String{

    }

}
```

编译后原方法的调用会重定向到上面这个带有`AsmMethodReplace`注解的方法：原class对象放在第一个参数，调用者的类名放到最后一个参数，其它的参数跟原方法保持一致。

> Class对象放到第一个参数主要是考虑到要调用原方法，可以直接class1.method1这样调用；
>
> 最后一个参数callerClassName，是为了区分调用者的身份是App本身还是三方SDK，可以只针对三方SDK做缓存处理。（在编译期才能拿到真实的类名，如果在运行时去获取是混淆过的）

**如果要Hook的方法是一个静态方法，则第一个参数不要**，因为静态方法是直接通过类名调用的。

下面是例子：

### 无侵入性统计方法耗时

`MainActivity`有 `updateData() {}`的方法，无侵入性统计耗时，如下：

```kotlin
object MainActivityHook {

    /**
     * 统计某个public方法耗时，例如 MainActivity#updateData()
     */
    @JvmStatic
    @AsmMethodReplace(
        oriClass = MainActivity::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun updateData(
        activity:MainActivity,
        callerClassName: String
    ) {
        val startTime = System.currentTimeMillis()

        //调用原方法
        activity.updateData()

        val costTime = System.currentTimeMillis() - startTime
        LogUtil.i("MainActivity updateData cost $costTime ms,callerClassName=$callerClassName")
    }
}
```

日志如下：

```
I/PrivacyMethod: MainActivity updateData cost 452 ms,
callerClassName=MainActivity$onCreate$1
```

### SDK某个方法会crash，通过Hook来临时修复

模拟SDK有个方法会发生crash

```kotlin
class CrashSdk {

    fun crash(){
        // 被除数是0
        var i = 1/0
    }
}

//调用的地方会crash
CrashSdk().crash()
```

快速修复，加个try catch

```kotlin
object CrashSdkHook {

    @JvmStatic
    @AsmMethodReplace(
        oriClass = CrashSdk::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun crash(
        crashSdk: CrashSdk,
        callerClassName: String
    ) {
        try {
            crashSdk.crash()
        } catch (e: Exception) {
            Log.e("CrashSdkHook", "hook,e=${e.message}")
            e.printStackTrace()
        }
    }
}
```

运行日志如下，异常被捕获了。

![](https://files.catbox.moe/dma8e9.png)

## 替换Class

**AsmClassReplace** 注解支持将目标类替换成它的子类（或者其它实现类），实现对目标类的行为监控。

### 将类的创建替换成子类

有个Dog类如下

```kotlin
open class Dog {

    init {
        Log.i("Dog","init")
    }

    open fun eat(){
        Log.i("Dog","eat")
    }
}

```

想要替换成如下 HookDog

```kotlin
class HookDog : Dog() {

    init {
        Log.i("Dog", "HookDog init")
    }

    override fun eat() {
        super.eat()
        Log.i("Dog", "HookDog eat")
    }
}
```

通过注解即可完成替换

```kotlin
object DogHook {

    @AsmClassReplace(oriClass = Dog::class, targetClass = HookDog::class)
    fun hookDog() {
    }

}
```

编译完之后，
`new Dog().eat()` 会被替换成

`new HookDog().eat()`

通过反编译可以看到结果

![](https://files.catbox.moe/bj18uf.png)

日志也能验证

```
I/Dog: init
I/Dog: HookDog init
I/Dog: eat
I/Dog: HookDog eat
```

# 实现原理

该项目是基于 [https://github.com/lanshifu/PrivacyMethodHooker](https://github.com/lanshifu/PrivacyMethodHooker) 这个开源项目进行**重构和优化**，

原理是通过 Gradle Plugin + Transform + ASM + 注解，编译期替换所有隐私API调用，

![](https://files.catbox.moe/pbxfhw.png)

相当于增加一个中间层，实现缓存处理和未同意隐私协议的处理，最终实现隐私API调用监控。

<br/>

更多细节可以参考这篇文章[ASM hook隐私方法调用，防止App被下架](https://juejin.cn/post/7043399520486424612#heading-0)，这里就不再阐述。

tip：

> 该方案属于Java层的hook，native层通过JNI调用隐私API的情况无法hook，需要采用native hook方案来实现。

# 隐私API汇总

## 常规调用

已知的隐私API：

**TelePhonyManager**

- getImei
- getDeviceId
- getSubscriberId
- getSimSerialNumber
- getMeid
- getAllCellInfo

  **以上这几个API在target 29或以上，只能有特殊权限的App能调用，普通App调用会抛异常，target 28或者以下会返回null，所以统一做了处理，Android 10之后的调用都会直接返回null，不会调用到系统API。**
- getLine1Number
- getCellLocation
- getSimOperator
- getSimOperatorName
- getSimCountryIso
- getNetworkOperator
- getNetworkOperatorName
- getNetworkCountryIso

**WifiInfo**

- getIpAddress
- getSSID
- getBSSID
- getMacAddress

后面三个需要开启定位才能获取到真实的值。

**WifiManager**

- getScanResults
需要开启定位才能获取
- getDhcpInfo
- getConnectionInfo

**BluetoothAdapter**

- getAddress
- getName

**BlueToothDevice**

- getAddress
- getName

**SensorManager**

- getSensorList

**Sensor**

- getName
- getType
- getVersion

**NetworkInterface**

- getHardwareAddress
  > 测试用例没覆盖，获取的NetworkInterface是null，调用不到这个API

**PackageManager**

- getInstalledPackages
- getInstalledApplications
- ActivityManager#getRunningAppProcesses
- ActivityManager#getRunningServices
- ActivityManager#getRecentTasks
- ActivityManager#getRunningTasks

**Settings**

- Settings\$System#getString("android_id")
- Settings\$Secure#getString("android_id")
- Settings\$Secure#getString("bluetooth_address")
- Settings\$Secure#getString("bluetooth_name")

**Runtime**

- exec(params:String)
参数如下会被hook：
getprop ro.serialno
cat /sys/class/net/*/address
pm list package

**File**

构造方法，正则过滤：sys/class/net/*/address

<br/>

## 反射调用

有些三方SDK会通过反射去调用隐私API，最终堆栈信息看到是**Method#invoke()**调用，

在1.0.1 版本对反射做了处理，具体参考
[MethodHook.java](privacy_method_hook_library/hook/hookmethod/MethodHook.java)

通过hook Method#invoke方法，判断是隐私API就重定向到各个hook类，否则放行。

![](https://files.catbox.moe/5aj0rx.png)

<br/>

# 如何定位问题

插件每一个hook操作都会记录日志

## 编译期日志

### 方法替换日志

日志存放在：app/build/report/PrivacyMethodReplaceTransform/debug(or release)/report.txt

![](https://files.catbox.moe/wvn8jd.png)

箭头前面是替换前的方法信息，监听后面是替换后的方法信息。

## 运行时日志

### 隐私API调用日志

TAG：**onPrivacyMethodCall**

![](https://files.catbox.moe/avhzv0.png)

日志中可以看到有数美、bugly、skyengine、cmic、个推、adplus等SDK调用了隐私API，而对于同一个API，短时间内频繁调用的，会返回缓存的数据，缓存时长App可以自定义。

### 所有日志

TAG：**[PrivacyMethod]**

这个TAG可以过滤出框架所有日志。

# 其它

**维护：**

此项目在公司内部开源，如果你有新的需求，例如新增隐私API、统计隐私API调用信息、或者发现了bug，欢迎反馈，或者提交MR参与维护。

**相关链接：**

[Android 敏感函数API](https://q9jvw0u5f5.feishu.cn/docx/doxcnouToRBHL4MWwlmRPHOD6BO)

[ASM hook隐私方法调用，防止App被下架](https://juejin.cn/post/7043399520486424612)
