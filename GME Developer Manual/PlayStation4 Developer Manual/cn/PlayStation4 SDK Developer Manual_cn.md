## 简介
欢迎使用腾讯云游戏多媒体引擎 SDK 。为方便 PlayStation4 开发者调试和接入腾讯云游戏多媒体引擎产品 API，这里向您介绍适用于 PlayStation4 开发的接入技术文档。


### 使用 GME 重要事项

|重要接口     | 接口含义|
| ------------- |:-------------:|
|Init    		|初始化 GME 	|
|Poll    		|触发事件回调	|
|EnterRoom	 	|进房  		|
|EnableMic	 	|开麦克风 	|
|EnableSpeaker	|开扬声器 	|


**说明**

**GME 的接口调用成功后返回值为 AV_OK，数值为0。**

**GME 的接口调用要在同一个线程下。**

**GME 加入房间需要鉴权，请参考文档关于鉴权部分内容。**

**GME 需要周期性的调用 Poll 接口触发事件回调。**

**GME 回调信息参考回调消息列表。**

**设备的操作要在进房成功之后。**



## 初始化相关接口
未初始化前，SDK 处于未初始化阶段，需要初始化 SDK 才可以进房。

|接口     | 接口含义   |
| ------------- |:-------------:|
|Init    	|初始化 GME	| 
|Poll    	|触发事件回调	|
|Pause   	|系统暂停	|
|Resume 	|系统恢复	|
|Uninit    	|反初始化 GME 	|


### 获取单例
GME SDK 以单例的形式提供，所有调用都从 ITMGContext 开始，通过 ITMGDelegate 回调回传给应用，必须首先设置。    
#### 函数原型 

```
ITMGContext virtual void TMGDelegate(ITMGDelegate* delegate)
```
#### 示例代码  

```
ITMGContext* m_pTmgContext;
m_pTmgContext = ITMGContextGetInstance();
```


### 消息传递
接口类采用 Delegate 方法用于向应用程序发送回调通知，消息类型参考 ITMG_MAIN_EVENT_TYPE，data 在 Windows 平台下是 json 字符串格式， 具体 key-value 参见说明文档。

#### 示例代码 

```
//函数实现：
class Callback : public SetTMGDelegate 
{
	virtual void OnEvent(ITMG_MAIN_EVENT_TYPE eventType,const char* data)
	{
  		switch(eventType)
  		{
   			//判断消息类型并处理
  		}
 	}
}

Callback*  p = new Callback ();
m_pTmgContext->TMGDelegate(p);

```

### 初始化 SDK

参数获取请参考 [接入指引](/GME%20Introduction.md)。
此接口需要来自腾讯云控制台的 sdkAppId 号码作为参数，再加上 openId，这个 openId 是唯一标识一个用户，规则由 App 开发者自行制定，App 内不重复即可（目前只支持 INT64）。
初始化 SDK 之后才可以进房。
#### 函数原型 

```
ITMGContext virtual void Init(const char* sdkAppId, const char* openId)
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------|
| sdkAppId    	|char*   	|来自腾讯云控制台的 sdkAppId 号码					|
| openID    	|char*   	|OpenID 只支持 Int64 类型（转为 string 传入），必须大于 10000，用于标识用户 	|

#### 示例代码  

```
#define SDKAPPID3RD "1400035750"
cosnt char* openId="10001";
ITMGContext* context = ITMGContextGetInstance();
context->Init(SDKAPPID3RD, openId);
```


### 触发事件回调

通过在 update 里面周期的调用 Poll 可以触发事件回调。
#### 函数原型

```
class ITMGContext {
protected:
    virtual ~ITMGContext() {}
    
public:    	
	virtual void Poll()= 0;
}

```
#### 示例代码
```
//头文件中的声明
//代码实现
void TMGTestScene::update(float delta)
{
    ITMGContextGetInstance()->Poll();
}
```


### 系统暂停

当系统发生 Pause 事件时，需要同时通知引擎进行 Pause。
#### 函数原型

```
ITMGContext int Pause()
```

### 系统恢复
当系统发生 Resume 事件时，需要同时通知引擎进行 Resume。
#### 函数原型

```
ITMGContext  int Resume()
```



### 反初始化 SDK
反初始化 SDK，进入未初始化状态。切换账号需要反初始化。
#### 函数原型 
```
ITMGContext int Uninit()

```
#### 示例代码  
```
ITMGContext* context = ITMGContextGetInstance();
context->Uninit();
```


## 实时语音房间相关接口
初始化之后，SDK 调用进房后进去了房间，才可以进行实时语音通话。

|接口     | 接口含义   |
| ------------- |:-------------:|
|GenAuthBuffer    	|初始化鉴权|
|EnterRoom   		|加入房间|
|IsRoomEntered   	|是否已经进入房间|
|ExitRoom 		|退出房间|
|ChangeRoomType 	|修改用户房间音频类型|
|GetRoomType 		|获取用户房间音频类型|


### 鉴权信息
生成 AuthBuffer，用于相关功能的加密和鉴权，相关后台部署请查看 [鉴权密钥](../GME%20Key%20Manual.md)。  
离线语音获取鉴权时，房间号参数必须填 null。

#### 函数原型
```
QAVSDK_AUTHBUFFER_API int QAVSDK_AUTHBUFFER_CALL QAVSDK_AuthBuffer_GenAuthBuffer(unsigned int dwSdkAppID, const char* strRoomID, const char* strOpenID,const char* strKey, unsigned char* strAuthBuffer, unsigned int bufferLength);
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------|
| dwSdkAppID    			|int   		|来自腾讯云控制台的 sdkAppId 号码		|
| strRoomID    		|char*     |房间号，最大支持127字符（离线语音房间号参数必须填 null）|
| strOpenID  		|char*    		|用户标识								|
| strKey    			|char*	    	|来自腾讯云 [控制台](https://console.cloud.tencent.com/gamegme) 的密钥					|
|strAuthBuffer		|char*	    	|返回的 authbuff							|
| buffLenght   		|int    		|传入的 authbuff 长度，建议为 500					|



#### 示例代码  
```
unsigned int bufferLen = 512;
unsigned char retAuthBuff[512] = {0};
QAVSDK_AuthBuffer_GenAuthBuffer(atoi(SDKAPPID3RD), roomId, "10001", AUTHKEY,retAuthBuff,bufferLen);
```



### 加入房间
用生成的鉴权信息进房，会收到消息为 ITMG_MAIN_EVENT_TYPE_ENTER_ROOM 的回调。加入房间默认不打开麦克风及扬声器。


#### 函数原型

```
ITMGContext virtual int EnterRoom(const char*  roomId, ITMG_ROOM_TYPE roomType, const char* authBuff, int buffLen)//普通进房接口
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------|
| roomId			| char*    		|房间号，最大支持 127 字符	|
| roomType 			|ITMG_ROOM_TYPE	|房间音频类型		|
| authBuffer    		|char*     	|鉴权码			|
| buffLen   			|int   		|鉴权码长度		|

- 房间音频类型请参考[音质选择](https://cloud.tencent.com/document/product/607/18522)。


#### 示例代码  

```
ITMGContext* context = ITMGContextGetInstance();
context->EnterRoom(roomId, ITMG_ROOM_TYPE_STANDARD, (char*)retAuthBuff,bufferLen);
```




### 加入房间事件的回调
加入房间完成后会发送信息 ITMG_MAIN_EVENT_TYPE_ENTER_ROOM，在 OnEvent 函数中进行判断。
#### 代码说明
```

void TMGTestScene::OnEvent(ITMG_MAIN_EVENT_TYPE eventType,const char* data){
	switch (eventType) {
            case ITMG_MAIN_EVENT_TYPE_ENTER_ROOM:
		{
		//进行处理
		break;
		}
	}
}
```

### 判断是否已经进入房间
通过调用此接口可以判断是否已经进入房间，返回值为 bool 类型。
#### 函数原型  
```
ITMGContext virtual bool IsRoomEntered()
```
#### 示例代码  
```
ITMGContext* context = ITMGContextGetInstance();
context->IsRoomEntered();
```

### 退出房间
通过调用此接口可以退出所在房间。这是一个异步接口，返回值为 AV_OK 的时候代表异步投递成功。

#### 如果应用中有退房后立即进房的场景，在接口调用流程上，开发者无需要等待 ExitRoom 的回调 RoomExitComplete 通知，只需直接调用接口。

#### 函数原型  

```
ITMGContext virtual int ExitRoom()
```
#### 示例代码  

```
ITMGContext* context = ITMGContextGetInstance();
context->ExitRoom();
```

### 退出房间回调
退出房间完成后会有回调，消息为 ITMG_MAIN_EVENT_TYPE_EXIT_ROOM。
#### 示例代码  

```
void TMGTestScene::OnEvent(ITMG_MAIN_EVENT_TYPE eventType,const char* data){
	switch (eventType) {
            case ITMG_MAIN_EVENT_TYPE_EXIT_ROOM:
		{
		//进行处理
		break;
		}
	}
}
```

### 修改用户房间音频类型
此接口用于修改用户房间音频类型，结果参见回调事件，事件类型为 ITMG_MAIN_EVENT_TYPE_CHANGE_ROOM_TYPE。
#### 函数原型  
```
IITMGContext TMGRoom public void ChangeRoomType((ITMG_ROOM_TYPE roomType)
```


|参数     | 类型         |意义|
| ------------- |:-------------:|-------------|
| roomType    |ITMG_ROOM_TYPE    |希望房间切换成的类型，房间音频类型参考 EnterRoom 接口|

#### 示例代码  
```
ITMGContext* context = ITMGContextGetInstance();
ITMGContextGetInstance()->GetRoom()->ChangeRoomType(ITMG_ROOM_TYPE_FLUENCY);
```


### 获取用户房间音频类型
此接口用于获取用户房间音频类型，返回值为房间音频类型，返回值为0时代表获取用户房间音频类型发生错误，房间音频类型参考 EnterRoom 接口。

#### 函数原型  
```
IITMGContext TMGRoom public  int GetRoomType()
```

#### 示例代码  
```
ITMGContext* context = ITMGContextGetInstance();
ITMGContextGetInstance()->GetRoom()->GetRoomType();
```


### 房间类型完成回调
房间类型设置完成后，回调的事件消息为 ITMG_MAIN_EVENT_TYPE_CHANGE_ROOM_TYPE，返回的参数为 result、error_info 及 new_room_type，new_room_type 代表的信息如下，在 OnEvent 函数中对事件消息进行判断。

|事件子类型     | 代表参数   |含义|
| ------------- |:-------------:|-------------|
| ITMG_ROOM_CHANGE_EVENT_ENTERROOM		|1 	|表示在进房的过程中，自带的音频类型与房间不符合，被修改为所进入房间的音频类型	|
| ITMG_ROOM_CHANGE_EVENT_START			|2	|表示已经在房间内，音频类型开始切换（例如调用 ChangeRoomType 接口后切换音频类型 ）|
| ITMG_ROOM_CHANGE_EVENT_COMPLETE		|3	|表示已经在房间，音频类型切换完成|
| ITMG_ROOM_CHANGE_EVENT_REQUEST			|4	|表示房间成员调用 ChangeRoomType 接口，请求切换房间音频类型|	


#### 示例代码  
```
void TMGTestScene::OnEvent(ITMG_MAIN_EVENT_TYPE eventType,const char* data) {
	if (ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVENT_TYPE_CHANGE_ROOM_TYPE == type)
        {
		//对房间类型事件进行处理
	 }
}
```

### 成员状态变化
该事件在状态变化才通知，状态不变化的情况下不通知。如需实时获取成员状态，请在上层收到通知时缓存，事件消息为 ITMG_MAIN_EVNET_TYPE_USER_UPDATE，其中 data 包含两个信息，event_id 及 user_list，在 OnEvent 函数中需要对信息 event_id 进行判断。
音频事件的通知有一个阈值，超过这个阈值才会发送通知。超过两秒没有收到音频包才通知“有成员停止发送音频包”消息。

|event_id     | 含义         |应用侧维护内容|
| ------------- |:-------------:|-------------|
|ITMG_EVENT_ID_USER_ENTER    				|有成员进入房间			|应用侧维护成员列表		|
|ITMG_EVENT_ID_USER_EXIT    				|有成员退出房间			|应用侧维护成员列表		|
|ITMG_EVENT_ID_USER_HAS_AUDIO    		|有成员发送音频包		|应用侧维护通话成员列表	|
|ITMG_EVENT_ID_USER_NO_AUDIO    			|有成员停止发送音频包	|应用侧维护通话成员列表	|

#### 示例代码  

```
void TMGTestScene::OnEvent(ITMG_MAIN_EVENT_TYPE eventType,const char* data){
	switch (eventType) {
            case ITMG_MAIN_EVNET_TYPE_USER_UPDATE:
		{
		//进行处理
		//开发者对参数进行解析，得到信息 event_id及 user_list
		    switch (eventID)
 		    {
 		    case ITMG_EVENT_ID_USER_ENTER:
  			    //有成员进入房间
  			    break;
 		    case ITMG_EVENT_ID_USER_EXIT:
  			    //有成员退出房间
			    break;
		    case ITMG_EVENT_ID_USER_HAS_AUDIO:
			    //有成员发送音频包
			    break;
		    case ITMG_EVENT_ID_USER_NO_AUDIO:
			    //有成员停止发送音频包
			    break;
 		    default:
			    break;
		    }
		break;
		}
	}
}
```

### 质量监控事件
质量监控事件，事件消息为 ITMG_MAIN_EVENT_TYPE_CHANGE_ROOM_QUALITY，返回的参数为 weight、floss  及 delay，代表的信息如下，在 OnEvent 函数中对事件消息进行判断。

|参数     | 含义         |
| ------------- |-------------|
|weight    				|范围是 1 到 5，数值为 5 是音质评分极好，数值为 1 是音质评分很差，几乎不能使用，数值为 0 代表初始值，无意义|
|floss    				|丢包率|
|delay    		|音频触达延迟时间（ms）|


### 消息详情

|消息     | 消息代表的意义   
| ------------- |:-------------:|
|ITMG_MAIN_EVENT_TYPE_ENTER_ROOM    				       |进入音视频房间消息|
|ITMG_MAIN_EVENT_TYPE_EXIT_ROOM    				         	|退出音视频房间消息|
|ITMG_MAIN_EVENT_TYPE_ROOM_DISCONNECT    		       |房间因为网络等原因断开消息|
|ITMG_MAIN_EVENT_TYPE_CHANGE_ROOM_TYPE				|房间类型变化事件|

### 消息对应的Data详情
|消息     | Data         |例子|
| ------------- |:-------------:|------------- |
| ITMG_MAIN_EVENT_TYPE_ENTER_ROOM    				|result; error_info					|{"error_info":"","result":0}|
| ITMG_MAIN_EVENT_TYPE_EXIT_ROOM    				|result; error_info  					|{"error_info":"","result":0}|
| ITMG_MAIN_EVENT_TYPE_ROOM_DISCONNECT    		|result; error_info  					|{"error_info":"waiting timeout, please check your network","result":0}|
| ITMG_MAIN_EVENT_TYPE_CHANGE_ROOM_TYPE    		|result; error_info; new_room_type	|{"error_info":"","new_room_type":0,"result":0}|



## 实时语音音频接口
初始化 SDK 之后进房，在房间中，才可以调用实时音频语音相关接口。
调用场景举例：

当用户界面点击打开/关闭麦克风/扬声器按钮时，建议如下方式：
- 对于大部分的游戏类 App，推荐调用 EnableMic 及 EnbaleSpeaker 接口，相当于总是应该同时调用 EnableAudioCaptureDevice/EnableAudioSend 和 EnableAudioPlayDevice/EnableAudioRecv 接口。
  
- 如果想单独释放采集或者播放设备，请参考接口 EnableAudioCaptureDevice 及 EnableAudioPlayDevice。
  
- 调用 pause 暂停音频引擎，调用 resume 恢复音频引擎。

|接口     | 接口含义   |
| ------------- |:-------------:|
|GetMicListCount    				       	|获取麦克风设备数量|
|GetMicList    				      	|枚举麦克风设备|
|GetSpeakerListCount    				      	|获取扬声器设备数量|
|GetSpeakerList    				      	|枚举扬声器设备|
|SelectMic    				      	|选定麦克风设备|
|SelectSpeaker    				|选定扬声器设备|
|EnableMic    						|开关麦克风|
|GetMicState    						|获取麦克风状态|
|EnableAudioCaptureDevice    		|开关采集设备		|
|IsAudioCaptureDeviceEnabled    	|获取采集设备状态	|
|EnableAudioSend    				|打开关闭音频上行	|
|IsAudioSendEnabled    				|获取音频上行状态	|
|GetMicLevel    						|获取实时麦克风音量	|
|SetMicVolume    					|设置麦克风音量		|
|GetMicVolume    					|获取麦克风音量		|
|EnableSpeaker    					|开关扬声器|
|GetSpeakerState    					|获取扬声器状态|
|EnableAudioPlayDevice    			|开关播放设备		|
|IsAudioPlayDeviceEnabled    		|获取播放设备状态	|
|EnableAudioRecv    					|打开关闭音频下行	|
|IsAudioRecvEnabled    				|获取音频下行状态	|
|GetSpeakerLevel    					|获取实时扬声器音量	|
|SetSpeakerVolume    				|设置扬声器音量		|
|GetSpeakerVolume    				|获取扬声器音量		|
|EnableLoopBack    					|开关耳返			|




### 获取麦克风设备数量
此接口用来获取麦克风设备数量。

#### 函数原型  
```
ITMGAudioCtrl virtual int GetMicListCount()
```
#### 示例代码  
```
ITMGContextGetInstance()->GetAudioCtrl()->GetMicListCount();
```

### 枚举麦克风设备
此接口用来枚举麦克风设备。配合 GetMicListCount 接口使用。

#### 函数原型 
```
ITMGAudioCtrl virtual int GetMicList(TMGAudioDeviceInfo* ppDeviceInfoList, int nCount)

class TMGAudioDeviceInfo
{
public:
	const char* pDeviceID;
	const char* pDeviceName;
};
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------|
| ppDeviceInfoList    	|TMGAudioDeviceInfo   	|设备列表		|
| nCount    		|int     		|获取的麦克风设备数量	|

#### 示例代码  

```
ITMGContextGetInstance()->GetAudioCtrl()->GetMicList(ppDeviceInfoList,nCount);
```



### 选中麦克风设备
此接口用来选中麦克风设备。如果不调用或者传入"DEVICEID_DEFAULT"，则选中系统默认设备。设备 ID 来自于 GetMicList 返回列表。

#### 函数原型  
```
ITMGAudioCtrl virtual int SelectMic(const char* pMicID)
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------|
| pMicID    |char*      |麦克风设备 ID|

#### 示例代码  
```
const char* pMicID ="{0.0.1.00000000}.{7b0b712d-3b46-4f7a-bb83-bf9be4047f0d}";
ITMGContextGetInstance()->GetAudioCtrl()->SelectMic(pMicID);
```

### 开启关闭麦克风
此接口用来开启关闭麦克风。加入房间默认不打开麦克风及扬声器。
EnableMic = EnableAudioCaptureDevice + EnableAudioSend.
#### 函数原型  
```
ITMGAudioCtrl virtual void EnableMic(bool bEnabled)
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------|
| bEnabled    |bool     |如果需要打开麦克风，则传入的参数为 true，如果关闭麦克风，则参数为 false		|

#### 示例代码  
```
ITMGContextGetInstance()->GetAudioCtrl()->EnableMic(true);
```


### 麦克风状态获取
此接口用于获取麦克风状态，返回值 0 为关闭麦克风状态，返回值 1 为打开麦克风状态。

#### 函数原型  
```
ITMGAudioCtrl virtual int GetMicState()
```
#### 示例代码  
```
ITMGContextGetInstance()->GetAudioCtrl()->GetMicState();
```


### 开启关闭采集设备
此接口用来开启/关闭采集设备。加入房间默认不打开设备。
- 只能在进房后调用此接口，退房会自动关闭设备。
- 在移动端，打开采集设备通常会伴随权限申请，音量类型调整等操作。

#### 函数原型  
```
ITMGContext virtual int EnableAudioCaptureDevice(bool enable)
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------|
| enable    |bool     |如果需要打开采集设备，则传入的参数为 true，如果关闭采集设备，则参数为 false|

#### 示例代码

```
打开采集设备
ITMGContextGetInstance()->GetAudioCtrl()->EnableAudioCaptureDevice(true);
```

### 采集设备状态获取
此接口用于采集设备状态获取。
#### 函数原型

```
ITMGContext virtual bool IsAudioCaptureDeviceEnabled()
```
#### 示例代码

```
ITMGContextGetInstance()->GetAudioCtrl()->IsAudioCaptureDeviceEnabled();
```

### 打开关闭音频上行
此接口用于打开/关闭音频上行。如果采集设备已经打开，那么会发送采集到的音频数据。如果采集设备没有打开，那么仍旧无声。采集设备的打开关闭参见接口 EnableAudioCaptureDevice。

#### 函数原型

```
ITMGContext  virtual int EnableAudioSend(bool bEnable)
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------|
| bEnable    |bool     |如果需要打开音频上行，则传入的参数为 true，如果关闭音频上行，则参数为 false|

#### 示例代码  

```
ITMGContextGetInstance()->GetAudioCtrl()->EnableAudioSend(true);
```

### 音频上行状态获取
此接口用于音频上行状态获取。
#### 函数原型  
```
ITMGContext virtual bool IsAudioSendEnabled()
```
#### 示例代码  
```
ITMGContextGetInstance()->GetAudioCtrl()->IsAudioSendEnabled();
```

### 获取麦克风实时音量
此接口用于获取麦克风实时音量，返回值为 int 类型。
#### 函数原型  
```
ITMGAudioCtrl virtual int GetMicLevel()
```
#### 示例代码  
```
ITMGContextGetInstance()->GetAudioCtrl()->GetMicLevel();
```

### 设置麦克风的音量
此接口用于设置麦克风的音量。参数 volume 用于设置麦克风的音量，当数值为 0 的时候表示静音，当数值为 100 的时候表示音量不增不减，默认数值为 100。

#### 函数原型  
```
ITMGAudioCtrl virtual int SetMicVolume(int vol)
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------|
| vol    |int      |设置音量，范围 0 到 200|

#### 示例代码  
```
int vol = 100;
ITMGContextGetInstance()->GetAudioCtrl()->SetMicVolume(vol);
```

### 获取麦克风的音量
此接口用于获取麦克风的音量。返回值为一个 int 类型数值，返回值为101代表没调用过接口 SetMicVolume。

#### 函数原型  
```
ITMGAudioCtrl virtual int GetMicVolume()
```
#### 示例代码  
```
ITMGContextGetInstance()->GetAudioCtrl()->GetMicVolume();
```

### 获取扬声器设备数量
此接口用来获取扬声器设备数量。

#### 函数原型  

```
ITMGAudioCtrl virtual int GetSpeakerListCount()
```
#### 示例代码  

```
ITMGContextGetInstance()->GetAudioCtrl()->GetSpeakerListCount();
```

### 枚举扬声器设备
此接口用来枚举扬声器设备。配合 GetSpeakerListCount 接口使用。

#### 函数原型  
```
ITMGAudioCtrl virtual int GetSpeakerList(TMGAudioDeviceInfo* ppDeviceInfoList, int nCount)

class TMGAudioDeviceInfo
{
public:
	const char* pDeviceID;
	const char* pDeviceName;
};
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------|
| ppDeviceInfoList    	|TMGAudioDeviceInfo    	|设备列表		|
| nCount   		|int     		|获取的扬声器设备数量	|

#### 示例代码  
```
ITMGContextGetInstance()->GetAudioCtrl()->GetSpeakerList(ppDeviceInfoList,nCount);
```

### 选中扬声器设备
此接口用来选中播放设备。如果不调用或者传入"DEVICEID_DEFAULT"，则选中系统默认播放设备。设备 ID 来自于 GetSpeakerList 返回列表。

#### 函数原型  
```
ITMGAudioCtrl virtual int SelectSpeaker(const char* pSpeakerID)
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------|
| pSpeakerID    |char*      |扬声器设备 ID|

#### 示例代码  
```
const char* pSpeakerID ="{0.0.1.00000000}.{7b0b712d-3b46-4f7a-bb83-bf9be4047f0d}";
ITMGContextGetInstance()->GetAudioCtrl()->SelectSpeaker(pSpeakerID);
```

### 开启关闭扬声器
此接口用于开启关闭扬声器。
EnableSpeaker = EnableAudioPlayDevice +  EnableAudioRecv.
#### 函数原型  
```
ITMGAudioCtrl virtual void EnableSpeaker(bool enabled)
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------|
| enable   		|bool       	|如果需要关闭扬声器，则传入的参数为 false，如果打开扬声器，则参数为 true	|

#### 示例代码  
```
ITMGContextGetInstance()->GetAudioCtrl()->EnableSpeaker(true);
```

### 扬声器状态获取
此接口用于扬声器状态获取。返回值 0 为关闭扬声器状态，返回值 1 为打开扬声器状态，返回值 2 为扬声器设备正在操作中。

#### 函数原型  
```
ITMGAudioCtrl virtual int GetSpeakerState()
```

#### 示例代码  
```
ITMGContextGetInstance()->GetAudioCtrl()->GetSpeakerState();
```

### 开启关闭播放设备
此接口用于开启关闭播放设备。

#### 函数原型  
```
ITMGContext virtual int EnableAudioPlayDevice(bool enable) 
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------|
| enable    |bool        |如果需要关闭播放设备，则传入的参数为 false，如果打开播放设备，则参数为 true|

#### 示例代码  
```
ITMGContextGetInstance()->GetAudioCtrl()->EnableAudioPlayDevice(true);
```

### 播放设备状态获取
此接口用于播放设备状态获取。
#### 函数原型

```
ITMGContext virtual bool IsAudioPlayDeviceEnabled()
```
#### 示例代码  

```
ITMGContextGetInstance()->GetAudioCtrl()->IsAudioPlayDeviceEnabled();
```

### 打开关闭音频下行
此接口用于打开/关闭音频下行。如果播放设备已经打开，那么会播放房间里其他人的音频数据。如果播放设备没有打开，那么仍旧无声。播放设备的打开关闭参见接口 参见EnableAudioPlayDevice。

#### 函数原型  

```
ITMGContext virtual int EnableAudioRecv(bool enable)
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------|
| enable    |bool     |如果需要打开音频下行，则传入的参数为 true，如果关闭音频下行，则参数为 false|

#### 示例代码  

```
ITMGContextGetInstance()->GetAudioCtrl()->EnableAudioRecv(true);
```



### 音频下行状态获取
此接口用于音频下行状态获取。
#### 函数原型  
```
ITMGContext virtual bool IsAudioRecvEnabled() 
```

#### 示例代码  
```
ITMGContextGetInstance()->GetAudioCtrl()->IsAudioRecvEnabled();
```

### 获取扬声器实时音量
此接口用于获取扬声器实时音量。返回值为 int 类型数值，表示扬声器实时音量。
#### 函数原型  
```
ITMGAudioCtrl virtual int GetSpeakerLevel()
```

#### 示例代码  
```
ITMGContextGetInstance()->GetAudioCtrl()->GetSpeakerLevel();
```

### 设置扬声器的音量
此接口用于设置扬声器的音量。
参数 volume 用于设置扬声器的音量，当数值为 0 的时候表示静音，当数值为 100 的时候表示音量不增不减，默认数值为 100。

#### 函数原型  
```
ITMGAudioCtrl virtual int SetSpeakerVolume(int vol)
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------|
| vol    |int        |设置音量，范围 0 到 200|

#### 示例代码  
```
int vol = 100;
ITMGContextGetInstance()->GetAudioCtrl()->SetSpeakerVolume(vol);
```

### 获取扬声器的音量
此接口用于获取扬声器的音量。返回值为 int 类型数值，代表扬声器的音量，返回值为101代表没调用过接口 SetSpeakerVolume。
Level 是实时音量，Volume 是扬声器的音量，最终声音音量相当于 Level*Volume%。举个例子：实时音量是数值是 100 的话，此时Volume的数值是 60，那么最终发出来的声音数值也是 60。

#### 函数原型  
```
ITMGAudioCtrl virtual int GetSpeakerVolume()
```
#### 示例代码  
```
ITMGContextGetInstance()->GetAudioCtrl()->GetSpeakerVolume();
```


### 启动耳返
此接口用于启动耳返。
#### 函数原型  
``` 
ITMGAudioCtrl virtual int EnableLoopBack(bool enable)
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------|
| enable    |bool         |设置是否启动|

#### 示例代码  
```
ITMGContextGetInstance()->GetAudioCtrl()->EnableLoopBack(true);
```




## 高级 API
### 获取诊断信息
获取音视频通话的实时通话质量的相关信息。该接口主要用来查看实时通话质量、排查问题等，业务侧可以忽略。
#### 函数原型  

```
ITMGRoom virtual const char* GetQualityTips()
```
#### 示例代码  

```
ITMGContextGetInstance()->GetRoom()->GetQualityTips();
```


### 获取版本号
获取 SDK 版本号，用于分析。
#### 函数原型
```
ITMGContext virtual const char* GetSDKVersion()
```
#### 示例代码  
```
ITMGContextGetInstance()->GetSDKVersion();
```

### 设置打印日志等级
用于设置打印日志等级。建议保持默认等级。
#### 函数原型
```
ITMGContext virtual void SetLogLevel(ITMG_LOG_LEVEL levelWrite, ITMG_LOG_LEVEL levelPrint)
```

#### 参数含义

|参数|类型|意义|
|---|---|---|
|levelWrite|ITMG_LOG_LEVEL|设置写入日志的等级，TMG_LOG_LEVEL_NONE 表示不写入|
|levelPrint|ITMG_LOG_LEVEL|设置打印日志的等级，TMG_LOG_LEVEL_NONE 表示不打印|


|ITMG_LOG_LEVEL|意义|
| -------------------------------|:-------------:|
|TMG_LOG_LEVEL_NONE=0		|不打印日志			|
|TMG_LOG_LEVEL_ERROR=1		|打印错误日志（默认）	|
|TMG_LOG_LEVEL_INFO=2			|打印提示日志		|
|TMG_LOG_LEVEL_DEBUG=3		|打印开发调试日志	|
|TMG_LOG_LEVEL_VERBOSE=4		|打印高频日志		|

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




## 回调消息

#### 消息列表：

|消息     | 消息代表的意义   
| ------------- |:-------------:|
|ITMG_MAIN_EVENT_TYPE_ENTER_ROOM    		|进入音频房间消息		|
|ITMG_MAIN_EVENT_TYPE_EXIT_ROOM    		|退出音频房间消息		|
|ITMG_MAIN_EVENT_TYPE_ROOM_DISCONNECT		|房间因为网络等原因断开消息	|
|ITMG_MAIN_EVENT_TYPE_CHANGE_ROOM_TYPE		|房间类型变化事件		|
|ITMG_MAIN_EVENT_TYPE_MIC_NEW_DEVICE    	|新增麦克风设备消息		|
|ITMG_MAIN_EVENT_TYPE_MIC_LOST_DEVICE    	|丢失麦克风设备消息		|
|ITMG_MAIN_EVENT_TYPE_SPEAKER_NEW_DEVICE	|新增扬声器设备消息		|
|ITMG_MAIN_EVENT_TYPE_SPEAKER_LOST_DEVICE	|丢失扬声器设备消息		|
|ITMG_MAIN_EVENT_TYPE_ACCOMPANY_FINISH		|伴奏结束消息			|
|ITMG_MAIN_EVNET_TYPE_USER_UPDATE		|房间成员更新消息		|
|ITMG_MAIN_EVNET_TYPE_PTT_RECORD_COMPLETE	|PTT 录音完成			|
|ITMG_MAIN_EVNET_TYPE_PTT_UPLOAD_COMPLETE	|上传 PTT 完成			|
|ITMG_MAIN_EVNET_TYPE_PTT_DOWNLOAD_COMPLETE	|下载 PTT 完成			|
|ITMG_MAIN_EVNET_TYPE_PTT_PLAY_COMPLETE		|播放 PTT 完成			|
|ITMG_MAIN_EVNET_TYPE_PTT_SPEECH2TEXT_COMPLETE	|语音转文字完成			|

#### Data 列表：

|消息     | Data         |例子|
| ------------- |:-------------:|------------- |
| ITMG_MAIN_EVENT_TYPE_ENTER_ROOM    		|result; error_info			|{"error_info":"","result":0}|
| ITMG_MAIN_EVENT_TYPE_EXIT_ROOM    		|result; error_info  			|{"error_info":"","result":0}|
| ITMG_MAIN_EVENT_TYPE_ROOM_DISCONNECT    	|result; error_info  			|{"error_info":"waiting timeout, please check your network","result":0}|
| ITMG_MAIN_EVENT_TYPE_CHANGE_ROOM_TYPE    	|result; error_info; sub_event_type; new_room_type	|{"error_info":"","new_room_type":0,"result":0}|
| ITMG_MAIN_EVENT_TYPE_SPEAKER_NEW_DEVICE	|result; error_info  			|{"deviceID":"{0.0.0.00000000}.{a4f1e8be-49fa-43e2-b8cf-dd00542b47ae}","deviceName":"扬声器 (Realtek High Definition Audio)","error_info":"","isNewDevice":true,"isUsedDevice":false,"result":0}|
| ITMG_MAIN_EVENT_TYPE_SPEAKER_LOST_DEVICE    	|result; error_info  			|{"deviceID":"{0.0.0.00000000}.{a4f1e8be-49fa-43e2-b8cf-dd00542b47ae}","deviceName":"扬声器 (Realtek High Definition Audio)","error_info":"","isNewDevice":false,"isUsedDevice":false,"result":0}|
| ITMG_MAIN_EVENT_TYPE_MIC_NEW_DEVICE    	|result; error_info  			|{"deviceID":"{0.0.1.00000000}.{5fdf1a5b-f42d-4ab2-890a-7e454093f229}","deviceName":"麦克风 (Realtek High Definition Audio)","error_info":"","isNewDevice":true,"isUsedDevice":true,"result":0}|
| ITMG_MAIN_EVENT_TYPE_MIC_LOST_DEVICE    	|result; error_info 			|{"deviceID":"{0.0.1.00000000}.{5fdf1a5b-f42d-4ab2-890a-7e454093f229}","deviceName":"麦克风 (Realtek High Definition Audio)","error_info":"","isNewDevice":false,"isUsedDevice":true,"result":0}|
| ITMG_MAIN_EVNET_TYPE_USER_UPDATE    		|user_list;  event_id			|{"event_id":1,"user_list":["0"]}|
| ITMG_MAIN_EVNET_TYPE_PTT_RECORD_COMPLETE 	|result; file_path  			|{"file_path":"","result":0}|
| ITMG_MAIN_EVNET_TYPE_PTT_UPLOAD_COMPLETE 	|result; file_path;file_id  		|{"file_id":"","file_path":"","result":0}|
| ITMG_MAIN_EVNET_TYPE_PTT_DOWNLOAD_COMPLETE	|result; file_path;file_id  		|{"file_id":"","file_path":"","result":0}|
| ITMG_MAIN_EVNET_TYPE_PTT_PLAY_COMPLETE 	|result; file_path  			|{"file_path":"","result":0}|
| ITMG_MAIN_EVNET_TYPE_PTT_SPEECH2TEXT_COMPLETE	|result; text;file_id		|{"file_id":"","text":"","result":0}|
| ITMG_MAIN_EVNET_TYPE_PTT_STREAMINGRECOGNITION_COMPLETE	|result; file_path; text;file_id		|{"file_id":"","file_path":","text":"","result":0}|