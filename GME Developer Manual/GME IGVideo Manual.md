# IGVideo 使用文档

为方便开发者调试和接入腾讯云游戏多媒体引擎产品 API，这里向您介绍适用于 IGVideo SDK 的开发的接入技术文档。


## 功能介绍

使用  IGVideo SDK 可以录制视频，播放视频及分享视频。本接口文档以 Android 端为例。

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
static public IGVideoSDK getInstance() {
	if (mInstance == null) {
		mInstance = new GVideoSDKImpl();
	}
	return mInstance;
}
```


### 初始化

初始化 GVideoSDK，初始化后才可以使用 SDK 功能。

openID 用于唯一标识一个用户，数值需大于 10000（目前只支持 INT64），规则由 App 开发者自行制定，App 内不重复即可。

#### 函数原型

```
public abstract int initialize(String sdkAppID, String openID, String authBuffer);
```

|参数		|类型	|作用																			|
|-----------|-------|-------------------------------------------------------------------------------|
|sdkAppID	|String	|来自腾讯云控制台，GME 服务提供的 AppId。											|
|openID		|String	|openID 只支持 Int64 类型（转为 string 传入），数值必须大于10000，否则初始化不成功。	|
|authBuffer	|String	|鉴权二进制串，通过AppID， OpenID计算而来。											|


#### 示例代码

```
String sdkAppID = "1400089356";
String openID = "10001";//此参数必须大于10000

int ret = IGVideoSDK.GetInstance(this).initialize(sdkAppId, openId, authBuffer);
if(ret != 0){
	Log.e(TAG,"初始化IGVideoSDK失败");
}
```

#### 返回值

参见QAVError。如果当前已在房间内，则必定失败。

### 鉴权


### 音视频播放操作

创建一个新的 VideoPlay 的 Controller，用于控制音视频播放操作。

#### 函数原型

```
public abstract IGVideoPlayControl createVideoPlayControl();
```

### 上传分享操作

创建一个新的 GVideoShareControl 实例，用于上传分享操作。

#### 函数原型

```
public abstract IGVideoShareControl getVideoShareControlInstance();
```

### 屏幕录制操作

获取 IGVideoScreenRecordControl 实例，用于屏幕录制操作。

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


#### 示例代码

```
int ret = IGVideoSDK.GetInstance(this).createVideoPlayControl().setVideoControlCallback(IGVPlayControlcallback);
```

### 容器绑定

将上层的容器与 VideoPlayer 绑定。

#### 函数原型

```
public abstract int bindView(Activity activity, ViewGroup viewContainer);
```

|参数			|类型		|作用						|
|---------------|-----------|---------------------------|
|activity		|Activity	|当前页面的 Activity			|
|viewContainer	|ViewGroup	|需要与 VideoPlayer 绑定的组件|

### 容器解绑

将上层的容器与 VideoPlayer 解除绑定。

#### 函数原型

```
public abstract int unBindView(Activity activity, ViewGroup viewContainer);
```

### 路径设置相关接口

**需要先设置路径，才可以播放视频。**

### 设置本地的视频路径

设置本地的视频路径。

#### 函数原型

```
public abstract int setVideoPath(String videoPath);
```

|参数			|类型		|作用						|
|---------------|-----------|---------------------------|
|videoPath		|String		|待播放的视频本地路径			|


### 设置网络的的视频URL

设置网络的的视频 URL。

#### 函数原型

```
public abstract int setVideoURL(String videoURL);
```

|参数			|类型		|作用						|
|---------------|-----------|---------------------------|
|videoURL		|String		|待播放的视频网络路径			|


### 获取设置的路径

获取设置的路径。

#### 函数原型

```
public abstract String getVideoLocalPath();
```

#### 返回值

返回值为设置的播放路径。


### 播放相关接口

**调用播放相关接口可以对视频的播放进行播放暂停操作，以及获取文件时长及播放进度制作进度条。**

### 获取文件时长

获取文件时长以毫秒为单位。

#### 函数原型

```
public abstract int getFileDuration();
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
public static void setActivityResult(Context context, int requestCode, int resultCode, Intent data) 
{
    ScreenRecorder.getInstance().onActivityResult(context, requestCode, resultCode, data);
}
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

#### 视频宽高

视频宽高一般采用屏幕的宽与高。

#### 视频码率帧率

视频码率 = (int)(屏幕宽[像素] × 屏幕高[像素] × 3.6)。

视频帧率影响画面流畅度，且成正比，但视频帧率最好不低于24fps。

#### 音频

音频采样率，通常有16000、32000、44100、48000，越高代表音质越好，所需空间也越大。

音频通道数，1或者2。2即为双声道。

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

#### 函数原型

```
public abstract int generateShareLink(String UUID);
```

|参数			|类型		|作用						|
|---------------|-----------|---------------------------|
|UUID		|String		|待上传的本地文件路径			|


### 取消正在分享的链接

取消正在分享的链接。

#### 函数原型

```
public abstract int cancelGenerateShareLink(String filePath);
```

|参数			|类型		|作用						|
|---------------|-----------|---------------------------|
|filePath		|String		|正在分享的链接			|
