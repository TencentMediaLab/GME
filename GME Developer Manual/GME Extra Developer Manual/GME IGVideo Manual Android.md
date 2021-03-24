# GVideo Android 端使用文档

为方便开发者调试和接入腾讯云游戏多媒体引擎产品 API，这里向您介绍适用于 GVideo SDK 的开发的接入技术文档。


## 功能介绍

使用  GVideo SDK 可以录制屏幕，播放视频及分享视频。本接口文档以 Android 端为例。

## 使用流程

![](https://main.qcloudimg.com/raw/f77c7305b294f385a14fc72768756f3d.png)

## 类介绍

|类							|作用	|
|---------------------------|-------|
|IGVideoSDK					|主要接口|
|IGVideoScreenRecordControl	|录制接口|
|IGVideoPlayControl			|播放接口|
|IGVideoShareControl		|分享接口|

## 重要接口

以下的接口，接入 SDK 功能必须调用。

### 引入SDK

```
import com.tencent.GVideoImpl.GVideoSDKImpl;
```

### 获取实例

调用接口需要获取GVideoSDK的初始化实例。

```
IGVideoSDK mIGVideoSDK = IGVideoSDK.getInstance();
```


### 初始化

初始化 GVideoSDK，初始化后才可以使用 SDK 功能。

openID 用于唯一标识一个用户，规则由 App 开发者自行制定，App 内不重复即可。

#### 函数原型

```
public abstract int initialize(String sdkAppID, String openID, String authBuffer);
```

|参数		|类型	|作用																			|
|-----------|-------|-------------------------------------------------------------------------------|
|sdkAppID	|String	|来自腾讯云控制台，GME 服务提供的 AppId。		   |
|openID		|String	|openID 只支持 Int64 类型（转为 string 传入）	|
|authBuffer	|String	|鉴权二进制串，从腾讯云上获得的16字节长密钥	 |


#### 示例代码

```
String sdkAppID = "1400089356";
String openID = "10001";

int ret = IGVideoSDK.GetInstance(this).initialize(sdkAppId, openId, authBuffer);
if(ret != 0){
	Log.e(TAG,"初始化GVideoSDK失败");
}
```

#### 返回值

本方法目前不校验参数内容，如果传参错误，则会在实际使用时（比如上传下载）返回失败。

### 音视频播放操作

创建一个新的 VideoPlay 的 Controller，用于控制音视频播放操作。
VideoPlayController是多实例对象，需要上层持有并管理生命期。

#### 函数原型

```
public abstract IGVideoPlayControl createVideoPlayControl(Context context, int type);
```

|参数		|类型	|作用																			|
|-----------|-------|-------------------------------------------------------------------------------|
|playerType	| int	| 	建议传入GVideoPlayerType_IjkPlayer   |

### 上传分享操作

获取 GVideoShareControl 单例，用于上传分享操作。

#### 函数原型

```
public abstract IGVideoShareControl getVideoShareControlInstance();
```

### 屏幕录制操作

获取 IGVideoScreenRecordControl 单例，用于屏幕录制操作。

#### 函数原型

```
public abstract IGVideoScreenRecordControl getScreenRecordControlInstance();
```


### 反初始化

反初始化 SDK，进入未初始化状态。**切换账号需要反初始化，再用新的 openID 进行初始化**。

#### 函数原型

```
public abstract void unInitialize();
```

## 音视频播放

播放音视频操作的接口在 IGVideoPlayControl 类中。调用的前提是已经调用过 createVideoPlayControl。

此部分接口分为三个部分，分别是**播放器设置**，**路径设置**以及**播放设置**。

### 播放器设置相关接口

### 设置回调

设置 VideoPlayControl 的回调事件。

#### 函数原型

```
public abstract int setVideoControlCallback(IGVideoPlayControlCallback callback);
```

|参数		|类型	|作用											|
|-----------|-------|-----------------------------------------------|
|callback	|IGVideoPlayControlCallback	|GVideoPlayControl的回调事件	|


### 容器绑定

播放器上屏需要绑定一个UIView，本质上是在这个UIView上创建等大的子View来实现画面上屏

#### 函数原型

```
public abstract int bindView(Activity activity, ViewGroup viewContainer);
```

|参数			|类型		|作用						|
|---------------|-----------|---------------------------|
|activity		|Activity	|当前页面的 Activity			|
|viewContainer	|ViewGroup	|ViewGroup组件|

### 容器解绑

将上层的容器与 VideoPlayer 解除绑定。

#### 函数原型

```
public abstract int unBindView(Activity activity, ViewGroup viewContainer);
```
如果IGVideoPlayType为TYPE_PLAY_PIXELS，则不需要bindView和unBindView。

### 路径设置相关接口

**需要先设置路径，才可以播放视频。**


### 设置视频路径

设置需要播放的视频的路径。

#### 函数原型

```
public abstract int setVideoURL(String videoURL);
```

|参数			|类型		|作用						|
|---------------|-----------|---------------------------|
|videoURL		|String		|待播放的视频路径，填入本地路径播放本地视频，填入url 播放网络视频。			|


### 获取设置的路径

获取设置的路径。

#### 函数原型

```
public abstract String getVideoUrl();
```

#### 返回值

返回值为设置的播放本地路径或网络url。


### 播放相关接口

**调用播放相关接口可以对视频的播放进行播放暂停操作，以及获取文件时长及播放进度制作进度条。**

### 获取文件时长

获取文件时长以毫秒为单位。

#### 函数原型

```
public abstract long getFileDuration();
```

#### 返回值

视频文件的时长。


### 设置当前播放进度

设置当前播放进度，以毫秒为单位。

#### 函数原型

```
public abstract int seek(int position);
```

|参数			|类型		|作用						|
|---------------|-----------|---------------------------|
|position		|int		|当前播放进度，以毫秒为单位		|


### 从头开始播放视频

从头开始播放视频。

#### 函数原型

```
public abstract int play();
```


### 暂停播放

暂停播放视频。

#### 函数原型

```
public abstract int pause();
```

### 恢复播放

恢复播放视频。

#### 函数原型

```
public abstract int resume();
```


### 停止播放

停止播放，无法恢复播放。暂停播放可以恢复播放进度。

#### 函数原型

```
public abstract int stop();
```

### 播放进度回调的间隔

设置播放进度回调的间隔， 默认50ms。

#### 函数原型

```
public abstract int setUpdatePlayingProgressFrequency(int nFrequency);
```

|参数			|类型		|作用						|
|---------------|-----------|---------------------------|
|nFrequency		|int		|播放进度回调的间隔			|


### 播放回调相关接口

|返回状态|含义|
|-------|----|
|onPlay | 播放开始|
|onPlayStop| 播放结束|
|onPlayingProgress| 播放进度|
|onError |无法获取到资源（或网络超时），错误码统一为GVideoError_Error，errMsg为常量|

#### 函数原型
```
- (void)onPlay;
- (void)onPlayStop;
- (void)onPlayingProgress:(NSTimeInterval)progressPlaying :(NSTimeInterval)progressBuffering;
- (void)onError:(int)code;
```

### 缓冲相关

|返回状态|含义|
|-------|----|
|onPlayBufferingStart |开始缓冲，上层可显示加载中动画|
|onPlayBufferingEnd | 缓冲完成，准备播放，上层可隐藏加载中动画|

#### 函数原型
```
- (void)onPlayBufferingStart;
- (void)onPlayBufferingEnd;
```

## 录屏操作


### 设置回调

设置 IGVideoScreenRecordControl 的回调事件。

#### 函数原型

```
public abstract void setScreenRecordCallBack(IGVideoScreenRecordControlCallback callBack);
```

|参数			|类型		|作用						|
|---------------|-----------|---------------------------|
|callBack		|IGVideoScreenRecordControlCallback		|IGVideoScreenRecordControl的回调事件			|


### 设置onActivityResult回调返回

把 Activity的 onActivityResult 回调返回的参数设置到SDK，必须在 Activity 的 onActivityResult 回调下调用该接口。

#### 函数原型

```
public static void setActivityResult(Context context, int requestCode, int resultCode, Intent data) {}
```

|参数			|类型		|作用						|
|---------------|-----------|---------------------------|
|context		|Context		|当前Activity的上下文|
|requestCode		|int		|Activity的onActivityResult回调返回的请求码|
|resultCode		|int		|Activity的onActivityResult回调返回的结果码|
|data		|Intent		|Activity的onActivityResult回调返回的Intent|


### 开始录制

调用此接口开始录制屏幕。

#### 函数原型

```
public abstract int startRecord(String filePath, Activity activity, int recordWidth, int recordHeight, int videoBitRate, int videoFrameRate, int audioSampleRate, int audioChanel);
```

|参数			|类型		|作用						|
|---------------|-----------|---------------------------|
|filePath		|String		|生成视频的路径|
|activity		|Activity		|当前Activity|
|recordWidth		|int		|视频宽|
|recordHeight		|int		|视频高|
|videoBitRate		|int		|视频码率|
|videoFrameRate		|int		|视频帧率|
|audioSampleRate	|int		|音频采样率|
|audioChanel		|int		|音频通道数|

#### 视频

视频宽高一般采用屏幕的宽与高。

视频帧率影响画面流畅度，且成正比，但视频帧率最好不低于24fps。

#### 音频

音频采样率，通常有16000、32000、44100、48000，越高代表音质越好，所需空间也越大。

音频通道数，1或者2。2即为双声道。

#### 示例参数

以下是几个主流的录制画质参数：

|画质|分辨率|码率|帧率|采样率|音频通道数|
|----|------|----|----|------|----------|
|普通画质|960x540|800kbps |30fps |48000|2|
|高清画质|1280x720|1550kbps |30fps |48000|2 |
|超清画质|2340x1080|3000kbps |30fps | 48000|2|

#### 示例代码

```
Display display = getWindowManager().getDefaultDisplay();
DisplayMetrics metrics = new DisplayMetrics();
display.getMetrics(metrics);
mRecordFileName =  "screen_record_" + System.currentTimeMillis() + ".mp4";
mRecordFilePath = Environment.getExternalStorageDirectory() + "/" + mRecordFileName;
startRecord(
	mRecordFilePath,
	GVideoMainActivity.this,
	metrics.widthPixels,
	metrics.heightPixels,
	(int)(metrics.widthPixels * metrics.heightPixels * 3.6),
	30,
	48000,
	2);
```


### 结束录制

结束录制屏幕。

#### 函数原型

```
public abstract int stopRecord();
```

### 录屏回调相关接口

### 状态相关

|返回状态|含义|
|------|----|
|onStart| 录制开始|
|onComplete| 录制结束，错误码为0表示正常结束，否则为AssetWriter/ReplayKit抛出的NSError的值。|

#### 函数原型
```
- (void)onStart;
- (void)onComplete:(int)errorCode error:(NSString*)errMsg;
```

## 分享操作


### 设置回调

设置 VideoShare 的回调事件。

#### 函数原型

```
public abstract void setVideoShareDelegate(IGVideoShareControlDelegate delegate) {
}
```

|参数			|类型		|作用						|
|---------------|-----------|---------------------------|
|callBack		|IGVideoShareControlDelegate		|GVideoShareControlDelegate的实例			|


### 获取分享链接

上传路径指定的Video， 异步获取分享链接。
必须正确设置appid及auth才能使用。

#### 函数原型

```
public abstract int generateShareLink(String filePath);
```

|参数			|类型		|作用						|
|---------------|-----------|---------------------------|
|filePath		|String		|待上传的本地文件路径			|


### 取消正在分享的链接

取消正在分享的链接。

#### 函数原型

```
public abstract int cancelGenerateShareLink(String UUID);
```

|参数			|类型		|作用						|
|---------------|-----------|---------------------------|
|UUID		|String		| UUID可以在IGVideoShareControlDelegate的onStart中获取，唯一标识一次上传任务；也可以传入filePath，则取消的是第一个同名文件的上传任务。 |

### 分享回调相关接口


|返回状态|含义|
|-------|----|
|onStart|开始上传任务，返回UUID，唯一标识一个上传任务，用于后续的进度同步和取消|
|onComplete|上传结束。如果errorCode为0表示上传成功，fileID为可播放地址。否则上传失败，errorCode可参见GVideoError_NET_XXX|
|onProgress|上传进度回调。|

#### 函数原型
```
- (void)onStart:(NSString *)filePath UUID:(NSString *)UUID;
- (void)onComplete:(NSString*)filePath fileID:(NSString*)fileID errorCode:(int)code errInfo:(NSString *)errorInfo UUID:(NSString *)UUID;
- (void)onProgress:(NSString*)filePath uploadBytes:(long long)uploadBytes totalBytes:(long long)totalBytes UUID:(NSString *)UUID;;
```