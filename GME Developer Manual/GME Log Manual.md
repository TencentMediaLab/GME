## 简介

欢迎使用腾讯云游戏多媒体引擎 SDK 。为方便开发者调试和接入腾讯云游戏多媒体引擎产品，这里向您介绍适用于所有平台的日志文件相关技术文档。

## 日志相关要点
### 什么情况需要提供“日志信息”？
如果开发者在接入 GME 的过程中，碰到不能解决的问题，可以提供客户端日志信息给我们，协助追查问题。
获取到日志之后，请在产品控制台上找到【工单】点击提交工单进行处理。

### GME 的日志放在哪里？
默认路径为：

|平台     |路径        |
| ------------- |:-------------:|
|Windows 	|%appdata%\Tencent\GME\ProcessName|
|iOS    		|Application/xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx/Documents|
|Android	|/sdcard/Android/data/xxx.xxx.xxx/files|
|Mac    		|/Users/username/Library/Containers/xxx.xxx.xxx/Data/Documents|

### 除了日志，控制台信息也尽量提供
控制台信息是指——logcat，XCode，dbgView等工具展现的系统日志，这对于我们分析问题很有帮助。

### 提供日志的同时，也请提供相关的信息：
- 发生问题的时间点；
- 日志来自什么平台？（例如：Unity开发的iOS平台）；
- 请提供完整的日志。

### GME 的日志默认会打印，什么情况不会打印？
- 设置日志的路径错了，访问不到；
- 开发者主动关掉了日志打印。




## 日志相关接口
### 设置打印日志等级
用于设置打印日志等级。建议保持默认等级。
#### 函数原型
```
ITMGContext int SetLogLevel(ITMG_LOG_LEVEL levelWrite, ITMG_LOG_LEVEL levelPrint)
```

#### 参数含义

|参数|类型|意义|
|---|---|---|
|levelWrite|ITMG_LOG_LEVEL|设置写入日志的等级，TMG_LOG_LEVEL_NONE 表示不写入|
|levelPrint|ITMG_LOG_LEVEL|设置打印日志的等级，TMG_LOG_LEVEL_NONE 表示不打印|



|ITMG_LOG_LEVEL|意义|
| -------------------------------|----------------------|
|TMG_LOG_LEVEL_NONE=0		|不打印日志			|
|TMG_LOG_LEVEL_ERROR=1		|打印错误日志（默认）	|
|TMG_LOG_LEVEL_INFO=2			|打印提示日志		|
|TMG_LOG_LEVEL_DEBUG=3		|打印开发调试日志	|
|TMG_LOG_LEVEL_VERBOSE=4		|打印高频日志		|

#### 示例代码  
```
ITMGContext.GetInstance(this).SetLogLevel(1,true,true);
```



### 设置打印日志路径
用于设置打印日志路径。默认路径为： /sdcard/Android/data/xxx.xxx.xxx/files。
#### 函数原型
```
ITMGContext int SetLogPath(String logDir)
```

|参数     | 类型         |意义|
| ------------- |:-------------:|-------------|
| logDir    		|String   		|路径|

#### 示例代码  
```
ITMGContext.GetInstance(this).SetLogPath(path);
```
