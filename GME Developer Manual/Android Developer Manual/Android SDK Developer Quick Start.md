## 简介

欢迎使用腾讯云游戏多媒体引擎 SDK 。为方便 Android 开发者调试和接入腾讯云游戏多媒体引擎产品 API，这里向您介绍适用于 Android 开发的快速接入文档。


## 使用流程图
![image](Image/i0.png)


### 使用GME 重要事项：

GME 快速入门文档只提供最主要的接入接口，更多详细接口请参考相关接口文档。


|重要接口     | 接口含义|
| ------------- |-------------|
|Init    				|初始化 GME 	|
|Poll    				|触发事件回调	|
|EnterRoom	 		|进房  			|
|EnableMic	 		|开麦克风 		|
|EnableSpeaker		|开扬声器 		|

**GME 的接口调用成功后返回值为 QAVError.OK，数值为0。**

**GME 的接口调用要在同一个线程下。**

**GME 加入房间需要鉴权，请参考文档关于鉴权部分内容。**

## 快速接入步骤

### 1、获取单例
在使用语音功能时，需要首先获取 ITMGContext 对象。
> 函数原型 

```
public static ITMGContext GetInstance(Context context)
```

|参数     | 类型         |意义|
| ------------- |:-------------:|-------------|
| context    |Context |应用程序上下文对象|


> 示例代码  

```
import com.tencent.TMG.ITMGContext; 
TMGContext.getInstance(this);
```

### 2、初始化 SDK
参数获取见文档：[游戏多媒体引擎接入指引](https://github.com/TencentMediaLab/GME/blob/master/GME%20Introduction.md)。
此接口需要来自腾讯云控制台的 SdkAppId 号码作为参数，再加上 openId，这个 openId 是唯一标识一个用户，规则由 App 开发者自行制定，App 内不重复即可（目前只支持 INT64）。
初始化 SDK 之后才可以进房。
> 函数原型

```
ITMGContext public int Init(String sdkAppId, String openID)
```

|参数     | 类型         |意义|
| ------------- |:-------------:|-------------|
| sdkAppId    	|String  |来自腾讯云控制台的 SdkAppId 号码				|
| openID    		|String  |唯一标识一个用户，规则由 App 开发者自行制定，目前只支持大于 10000 的数字类型|

> 示例代码 
```
ITMGContext.GetInstance(this).Init(sdkAppId, identifier);
```

### 3、系统回调触发
通过在 update 里面周期的调用 Poll 可以触发事件回调。
> 函数原型

```
ITMGContext int Poll()
```
> 示例代码
```
ITMGContext.GetInstance(this).Poll();
```

### 4、加入房间
用生成的鉴权信息进房，会收到消息为 ITMG_MAIN_EVENT_TYPE_ENTER_ROOM 的回调。
- 加入房间默认不打开麦克风及扬声器。
- 在 EnterRoom 接口调用之前要先调用 Init 接口。

> 函数原型
```
ITMGContext public abstract void  EnterRoom(int relationId, int roomType, byte[] authBuffer)
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------|
| relationId 	|int		|房间号				|
| roomType 	|int		|房间音频类型		|
| authBuffer	|byte[]	|鉴权码				|

|音频类型     	|含义|参数|
| ------------- |------------ | ---- |
| ITMG_ROOM_TYPE_FLUENCY			|流畅音质	|1|
| ITMG_ROOM_TYPE_STANDARD			|标准音质	|2|
| ITMG_ROOM_TYPE_HIGHQUALITY		|高清音质	|3|

> 示例代码  
```
ITMGContext.GetInstance(this).EnterRoom(Integer.parseInt(relationId),roomType, authBuffer);    
```

### 加入房间事件的回调
加入房间完成后会有回调，消息为 ITMG_MAIN_EVENT_TYPE_ENTER_ROOM。
设置回调相关参考代码。
```
private ITMGContext.ITMGDelegate itmgDelegate = null;
itmgDelegate= new ITMGContext.ITMGDelegate() {
            @Override
 			public void OnEvent(ITMGContext.ITMG_MAIN_EVENT_TYPE type, Intent data) {
                }
        };
```
回调处理相关参考代码。
> 示例代码  
```
public void OnEvent(ITMGContext.ITMG_MAIN_EVENT_TYPE type, Intent data) {
	if (ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVENT_TYPE_ENTER_ROOM == type)
        {
           	 //收到进房成功事件
        }
	}
```

### 5、开启关闭麦克风
此接口用来开启关闭麦克风。加入房间默认不打开麦克风及扬声器。

> 函数原型  
```
ITMGContext public void EnableMic(boolean isEnabled)
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------|
| isEnabled    |boolean     |如果需要关闭麦克风，则传入的参数为 false，如果打开麦克风，则参数为 true|
> 示例代码  
```
ITMGContext.GetInstance(this).GetAudioCtrl().EnableMic(true);
ITMGContext.GetInstance(this).GetAudioCtrl().EnableMic(false);
```


### 6、开启关闭扬声器
此接口用于开启关闭扬声器。

> 函数原型  
```
ITMGContext public void EnableSpeaker(boolean isEnabled)
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------|
| isEnabled    |boolean       |如果需要关闭扬声器，则传入的参数为 false，如果打开扬声器，则参数为 true|
> 示例代码  
```
ITMGContext.GetInstance(this).GetAudioCtrl().EnableSpeaker(true);
ITMGContext.GetInstance(this).GetAudioCtrl().EnableSpeaker(false);
```


## 关于鉴权
### 实时语音鉴权信息
生成 AuthBuffer，用于相关功能的加密和鉴权，相关参数获取及详情见[游戏多媒体引擎密钥文档](https://github.com/TencentMediaLab/GME/blob/master/GME%20Developer%20Manual/GME%20Key%20Manual.md)。    
该接口返回值为 Byte[] 类型。

> 函数原型
```
AuthBuffer public native byte[] genAuthBuffer(int sdkAppId, int roomId, String identifier, String key, int expTime, int authBits)
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------|
| appId    		|int   	|来自腾讯云控制台的 SdkAppId 号码	|
| roomId    		|int   	|要加入的房间名				|
| identifier    	|String |用户标识				|
| key    		|string |来自腾讯云控制台的密钥			|
| expTime    		|int   	|authBuffer 超时时间			|
| authBits    		|int    |权限（ITMG_AUTH_BITS_DEFAULT 代表拥有全部权限）|


> 示例代码  
```
long nExpUTCTime = 1800 + System.currentTimeMillis() / 1000L;
byte[] authBuffer=AuthBuffer.getInstance().genAuthBuffer(Integer.parseInt(sdkAppId), Integer.parseInt(strRoomID),identifier, key, (int)nExpUTCTime, (int) ITMGContext.ITMG_AUTH_BITS_DEFAULT);
```