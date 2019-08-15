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

## 如何提供 GME 日志

### 提供本地日志
如果需要提供本地日志，各个平台的日志路径请参考：[下载及使用方法](https://cloud.tencent.com/document/product/607/30410#.E5.A6.82.E4.BD.95.E5.8F.96.E5.BE.97.E6.97.A5.E5.BF.97.EF.BC.9F)



### 提供控制台全量日志
如果需要提供控制台全量日志的话，以 Android 平台为例，在 Android Studio 中打开 Logcat，手机连上电脑后重新复现问题，并把控制台的日志拷贝到文本文档中提交给 GME 开发人员。
 - 1、请不要有任何截取，请提供全量的日志
 - 2、请不要有任何过滤
 - 3、请提供发生问题的时间点

如下图所示：

![](https://main.qcloudimg.com/raw/0ea7dc4b48eb9a977f1b2809d46725f3.png)

[点击下载 Logcat 日志示例文件](https://main.qcloudimg.com/raw/cbae5759397c1b69832760b3e66c409f.txt)


## 日志相关接口
### 设置打印日志等级
用于设置打印日志等级。
#### 函数原型
```
ITMGContext virtual void SetLogLevel(int logLevel, bool enableWrite, bool enablePrint)
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------|
| logLevel    		|int   		|打印日志级别			|
| enableWrite    	|bool   		|是否写文件，默认为是	|
| enablePrint    	|bool   		|是否写控制台，默认为是	|


|ITMG_LOG_LEVEL|意义|
| -------------------------------|:-------------:|
|TMG_LOG_LEVEL_NONE=0		|不打印日志				|
|TMG_LOG_LEVEL_ERROR=1		|打印错误日志（默认）	|
|TMG_LOG_LEVEL_INFO=2		|打印提示日志			|
|TMG_LOG_LEVEL_DEBUG=3		|打印开发调试日志		|
|TMG_LOG_LEVEL_VERBOSE=4	|打印高频日志			|

#### 示例代码  
```
ITMGContext* context = ITMGContextGetInstance();
context->SetLogLevel(0,true,true);
```

### 设置打印日志路径
用于设置打印日志路径。
默认路径为：

|平台     |路径        |
| ------------- |:-------------:|
|Windows 	|%appdata%\Tencent\GME\ProcessName|
|iOS    		|Application/xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx/Documents|
|Android	|/sdcard/Android/data/xxx.xxx.xxx/files|
|Mac    		|/Users/username/Library/Containers/xxx.xxx.xxx/Data/Documents|

#### 函数原型
```
ITMGContext virtual void SetLogPath(const char* logDir) 
```

|参数     | 类型         |意义|
| ------------- |:-------------:|-------------|
| logDir    		|char*    		|路径|
#### 示例代码  
```
cosnt char* logDir = ""//自行设置路径
ITMGContext* context = ITMGContextGetInstance();
context->SetLogPath(logDir);
```

