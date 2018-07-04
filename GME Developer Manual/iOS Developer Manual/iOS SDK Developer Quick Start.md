## 简介

欢迎使用腾讯云游戏多媒体引擎 SDK 。为方便 Android 开发者调试和接入腾讯云游戏多媒体引擎产品 API，这里向您介绍适用于 Android 开发的快速接入文档。


## 使用流程图
![image](Image/i0.png)


### 使用GME 重要事项

GME 快速入门文档只提供最主要的接入接口，更多详细接口请参考相关接口文档。


|重要接口     | 接口含义|
| ------------- |-------------|
|InitEngine    		|初始化 GME 	|
|Poll    				|触发事件回调	|
|EnterRoom	 		|进房  			|
|EnableMic	 		|开麦克风 		|
|EnableSpeaker		|开扬声器 		|

**说明**
**GME 的接口调用成功后返回值为 QAVError.OK，数值为0。**

**GME 的接口调用要在同一个线程下。**

**GME 加入房间需要鉴权，请参考文档关于鉴权部分内容。**

## 快速接入步骤

### 1、获取单例
在使用语音功能时，需要首先获取 ITMGContext 对象。
> 函数原型

```
ITMGContext ITMGDelegate <NSObject>
```
> 示例代码  

```
ITMGContext* _context = [ITMGContext GetInstance];
_context.TMGDelegate =self;
```



### 2、初始化 SDK
参数获取见文档：[游戏多媒体引擎接入指引](https://github.com/TencentMediaLab/GME/blob/master/GME%20Introduction.md)。
此接口需要来自腾讯云控制台的 SdkAppId 号码作为参数，再加上 openId，这个 openId 是唯一标识一个用户，规则由 App 开发者自行制定，App 内不重复即可（目前只支持 INT64）。
初始化 SDK 之后才可以进房。
> 函数原型

```
ITMGContext -(void)InitEngine:(NSString*)sdkAppID openID:(NSString*)openID
```

|参数     | 类型         |意义|
| ------------- |:-------------:|-------------|
| sdkAppId    	|NSString  |来自腾讯云控制台的 SdkAppId 号码				|
| openID    		|NSString  |唯一标识一个用户，规则由 App 开发者自行制定，目前只支持大于 10000 的数字类型|

> 示例代码 
```
[[ITMGContext GetInstance] InitEngine:SDKAPPID3RD openID:_openId];
```

### 3、触发事件回调
通过在 update 里面周期的调用 Poll 可以触发事件回调。
> 函数原型

```
ITMGContext -(void)Poll
```
> 示例代码
```
[[ITMGContext GetInstance] Poll];
```

### 4、加入房间
用生成的鉴权信息进房，会收到消息为 ITMG_MAIN_EVENT_TYPE_ENTER_ROOM 的回调。
- 加入房间默认不打开麦克风及扬声器。
- 在 EnterRoom 接口调用之前要先调用 InitEngine 接口。

> 函数原型
```
ITMGContext   -(void)EnterRoom:(int) relationId roomType:(int*)roomType authBuffer:(NSData*)authBuffer
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------|
| relationId 	|int		|房间号				|
| roomType 	|int		|房间音频类型		|
| authBuffer	|NSData	|鉴权码				|

|音频类型     	|含义|参数|
| ------------- |------------ | ---- |
| ITMG_ROOM_TYPE_FLUENCY			|流畅音质	|1|
| ITMG_ROOM_TYPE_STANDARD			|标准音质	|2|
| ITMG_ROOM_TYPE_HIGHQUALITY		|高清音质	|3|

> 示例代码  
```
[[ITMGContext GetInstance] EnterRoom:_roomId roomType:_roomType authBuffer:authBuffer];
```

### 5、加入房间事件的回调
加入房间完成后会有回调，消息为 ITMG_MAIN_EVENT_TYPE_ENTER_ROOM。
设置回调相关参考代码。
```
- (void)OnEvent:(ITMG_MAIN_EVENT_TYPE)eventType data:(NSDictionary*)data
```
回调处理相关参考代码。
> 示例代码  
```
-(void)OnEvent:(ITMG_MAIN_EVENT_TYPE)eventType data:(NSDictionary *)data{
    NSLog(@"OnEvent:%lu,data:%@",(unsigned long)eventType,data);
    switch (eventType) {
        case ITMG_MAIN_EVENT_TYPE_ENTER_ROOM:
        {
            int result = ((NSNumber*)[data objectForKey:@"result"]).intValue;
            NSString* error_info = [data objectForKey:@"error_info"];
            //进入房间
        }
            break;
     }
}
```

### 6、开启关闭麦克风
此接口用来开启关闭麦克风。加入房间默认不打开麦克风及扬声器。

> 函数原型  
```
ITMGContext GetAudioCtrl -(void)EnableMic:(BOOL)enable
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------|
| isEnabled    |boolean     |如果需要打开麦克风，则传入的参数为 YES，如果关闭麦克风，则参数为 NO|
> 示例代码  
```
[[[ITMGContext GetInstance] GetAudioCtrl] EnableMic:YES];
```


### 7、开启关闭扬声器
此接口用于开启关闭扬声器。

> 函数原型  
```
ITMGContext GetAudioCtrl -(void)EnableSpeaker:(BOOL)enable
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------|
| isEnabled    |boolean       |如果需要关闭扬声器，则传入的参数为 NO，如果打开扬声器，则参数为 YES|
> 示例代码  
```
[[[ITMGContext GetInstance] GetAudioCtrl] EnableSpeaker:YES];
```


## 关于鉴权
### 实时语音鉴权信息
生成 AuthBuffer，用于相关功能的加密和鉴权，相关参数获取及详情见[游戏多媒体引擎密钥文档](https://github.com/TencentMediaLab/GME/blob/master/GME%20Developer%20Manual/GME%20Key%20Manual.md)。    
该接口返回值为 NSData 类型。

> 函数原型
```
@interface QAVAuthBuffer : NSObject
+ (NSData*) GenAuthBuffer:(unsigned int)appId roomId:(unsigned int)roomId identifier:(NSString*)identifier  key:(NSString*)key expTime:(unsigned int)expTime authBits:(unsigned int) authBits;
@end
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------|
| appId    		|int   	|来自腾讯云控制台的 SdkAppId 号码	|
| roomId    		|int  	|要加入的房间名				|
| identifier    	|NSString|用户标识				|
| key    			|NSString|来自腾讯云控制台的密钥			|
| expTime    		|int   	|authBuffer 超时时间			|
| authBits    		|uint64   |权限（ITMG_AUTH_BITS_DEFAULT 代表拥有全部权限）|


> 示例代码  
```
NSData* authBuffer =   [QAVAuthBuffer GenAuthBuffer:SDKAPPID3RD.intValue roomId:_roomId identifier:_openId key:AUTHKEY expTime:[[NSDate date] timeIntervalSince1970] + 3600 authBits:ITMG_AUTH_BITS_DEFAULT];
```