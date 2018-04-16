## 简介
欢迎使用腾讯云游戏多媒体引擎 SDK 。为方便 iOS 开发者调试和接入腾讯云游戏多媒体引擎产品 API，这里向您介绍适用于 iOS 开发的接入技术文档。

## SDK初始化  

### 1.获取单例
GME 以单例的形式提供，所有调用都从 ITMGContext 开始。异步操作的结果或者内部状态的改变都通过 ITMGDelegate 传给应用。    
> 函数原型 
```
ITMGContext @protocol ITMGDelegate <NSObject> 
```
> 示例代码  
```
ITMGContext* _context = [ITMGContext GetInstance];
_context.TMGDelegate =self;
```

### 2.消息传递
GME 的消息通过 ITMGDelegate 传给应用，消息类型参考 ITMG_MAIN_EVENT_TYPE，消息内容为一个字典，不同的事件类型，消息内容也会不一样。
> 函数原型
```
- (void)OnEvent:(ITMG_MAIN_EVENT_TYPE)eventType data:(NSDictionary*)data
```
> 示例代码 
```
-(void)OnEvent:(ITMG_MAIN_EVENT_TYPE)eventType data:(NSDictionary *)data{
    	NSLog(@"OnEvent:%lu,data:%@",(unsigned long)eventType,data);
		switch (eventType) {
			//对eventType进行判断
			}
	}
```
>消息列表：

|消息     | 消息代表的意义   
| ------------- |:-------------:|
| ITMG_MAIN_EVENT_TYPE_ENTER_ROOM    				|进入音视频房间消息
| ITMG_MAIN_EVENT_TYPE_EXIT_ROOM    				|退出音视频房间消息
| ITMG_MAIN_EVENT_TYPE_ROOM_DISCONNECT    		|房间因为网络等原因断开消息
| ITMG_MAIN_EVENT_TYPE_ENABLE_MIC    				|打开麦克风消息
| ITMG_MAIN_EVENT_TYPE_DISABLE_MIC    				|关闭麦克风消息
|ITMG_MAIN_EVENT_TYPE_ENABLE_SPEAKER				|打开扬声器消息
|ITMG_MAIN_EVENT_TYPE_DISABLE_SPEAKER				|关闭扬声器消息
|ITMG_MAIN_EVENT_TYPE_CHANGE_ROLE				|切换角色消息
|ITMG_MAIN_EVENT_TYPE_ACCOMPANY_FINISH			|伴奏结束消息
|ITMG_MAIN_EVNET_TYPE_USER_UPDATE					|房间成员更新消息
|ITMG_MAIN_EVNET_TYPE_PTT_RECORD_COMPLETE		|PTT 录音完成
|ITMG_MAIN_EVNET_TYPE_PTT_UPLOAD_COMPLETE		|上传 PTT 完成
|ITMG_MAIN_EVNET_TYPE_PTT_DOWNLOAD_COMPLETE	|下载 PTT 完成
|ITMG_MAIN_EVNET_TYPE_PTT_PLAY_COMPLETE			|播放 PTT 完成
|ITMG_MAIN_EVNET_TYPE_PTT_SPEECH2TEXT_COMPLETE	|语音转文字完成
|ITMG_MAIN_EVENT_TYPE_OPEN_CAMERA				|打开摄像头消息
|ITMG_MAIN_EVENT_TYPE_CLOSE_CAMERA				|关闭摄像头消息


## 实时语音接入

### 1.设置相关信息
获取相关信息，由腾讯云控制台申请，详情见[游戏多媒体引擎接入指引](https://github.com/TencentMediaLab/GME/blob/master/GME%20Introduction.md)。
此函数需要来自腾讯云控制台的 SdkAppId 号码及 accountType 号码作为参数，再加上 Id，这个 Id 是唯一标识一个用户，规则由 App 开发者自行制定，App 内不重复即可（目前只支持 INT64）。
> 函数原型 
```
ITMGContext -(void)SetAppInfo:(NSString*)sdkAppID accountType:(NSString*)accountType openID:(NSString*)openID
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------
| sdkAppId    	|NSString  |来自腾讯云控制台的 SdkAppId 号码				|
| accountType    	|NSString  |来自腾讯云控制台的 accountType 号码			|
| openID    		|NSString  |唯一标识一个用户，规则由 App 开发者自行制定，目前只支持大于10000的数字类型|
> 示例代码  
```
[[ITMGContext GetInstance] SetAppInfo:SDKAPPID3RD accountType:ACCOUNTTYPE openID:_openId];
```
设置版本信息，用于查 Log 信息及 Bug 时使用（不设置不影响功能）。
> 函数原型
```
ITMGContext  -(void)SetAppVersion:(NSString*)appVersion
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------
| sAppVersion    |NSString  |版本号|
> 示例代码  
```
[[ITMGContext GetInstance]SetAppVersion:appversion];
```
获取 SDK 版本号。
> 函数原型
```
ITMGContext  -(NSString*)GetSDKVersion
```
> 示例代码  
```
[[ITMGContext GetInstance] GetSDKVersion];
```
接下来是生成 AuthBuffer，用于相关功能的加密和鉴权，相关参数获取及详情见[游戏多媒体引擎接入指引](https://github.com/TencentMediaLab/GME/blob/master/GME%20Introduction.md)。  
  
该函数返回值为 NSData 类型。
> 函数原型
```
@interface QAVAuthBuffer : NSObject
+ (NSData*) GenAuthBuffer:(unsigned int)appId roomId:(unsigned int)roomId identifier:(NSString*)identifier accountType:(unsigned int)accountType key:(NSString*)key expTime:(unsigned int)expTime authBits:(unsigned int) authBits;
@end
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------
| appId    		|int   		|来自腾讯云控制台的 SdkAppId 号码		|
| roomId    		|int  		|要加入的房间名							|
| identifier  		|NSString    	|用户标识								|
| accountType    	|int   		|来自腾讯云控制台的 accountType 号码	|
| key    			|NSString    	|来自腾讯云控制台的密钥					|
| expTime    		|int   		|加入房间的超时时间						|
| authBits   	 	|uint64    	|权限									|

>关于权限  
>
ITMG_AUTH_BITS_ALL 代表拥有全部权限，建议实时用户、主播使用，ITMG_AUTH_BITS_RECV 代表下行权限，建议纯听众、观众使用，不能使用startAccompany。

> 示例代码  
```
NSData* authBuffer =   [QAVAuthBuffer GenAuthBuffer:SDKAPPID3RD.intValue roomId:_roomId identifier:_openId accountType:ACCOUNTTYPE.intValue key:AUTHKEY expTime:[[NSDate date] timeIntervalSince1970] + 3600 authBits:ITMG_AUTH_BITS_ALL];
```

### 2.加入房间
用生成的权鉴进房，会收到消息为 ITMG_MAIN_EVENT_TYPE_ENTER_ROOM 的回调。
>注意:加入房间默认不打开麦克风及扬声器。
关于角色的设置，在[游戏多媒体引擎角色说明](https://github.com/TencentMediaLab/GME/blob/master/GME%20Developer%20Manual/GME%20Role%20Manual.md)中有介绍。
> 函数原型
```
ITMGContext   -(void)EnterRoom:(int) relationId controlRole:(NSString*)role authBuffer:(NSData*)authBuffer
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------
| roomID		|int    		|房间号，大于等于六位的整数	|
| controlRole    	|NSString    	|角色名称，按照需求设置		|
| authBuffer    	|NSData    	|鉴权						|
> 示例代码  
```
[[ITMGContext GetInstance] EnterRoom:_roomId controlRole:@"user" authBuffer:authBuffer];
```

### 3.加入房间事件的回调
加入房间完成后会发送信息 ITMG_MAIN_EVENT_TYPE_ENTER_ROOM，在 OnEvent 函数中进行判断。
>函数原型
```
- (void)OnEnterRoomComplete:(int)result WithErrinfo:(NSString *)error_info
```

> 代码说明
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

### 4.判断是否已经进入房间
通过调用此函数可以判断是否已经进入房间，返回值为 BOOL 类型。
> 函数原型  
```
ITMGContext -(BOOL)IsRoomEntered
```
> 示例代码  
```
[[ITMGContext GetInstance] IsRoomEntered];
```

### 5.退出房间
通过调用此函数可以退出所在房间。
> 函数原型  
```
ITMGContext -(void)ExitRoom
```
> 示例代码  
```
[[ITMGContext GetInstance] ExitRoom];
```

### 6.退出房间回调
退出房间完成回调，SDK 通过此回调通知 APP 成功退出了房间，事件为 ITMG_MAIN_EVENT_TYPE_EXIT_ROOM。
> 函数原型  
```
- (void)OnExitRoomComplete
```
> 示例代码  
```
-(void)OnEvent:(ITMG_MAIN_EVENT_TYPE)eventType data:(NSDictionary *)data{
    NSLog(@"OnEvent:%lu,data:%@",(unsigned long)eventType,data);
    switch (eventType) {
        case ITMG_MAIN_EVENT_TYPE_EXIT_ROOM：
        {
	    //退出房间
        }
            break;
    }
}
```

### 7.暂停音频引擎的采集和播放
调用此函数暂停音频引擎的采集和播放，只在进房后有效。
在 EnterRoom 函数调用成功之后之后就会占用麦克风权限，期间其他程序无法进行麦克风采集。
注意：调用 EnableMic(false) 无法释放麦克风占用。
如果确实需要释放麦克风，请调用 PauseAudio 函数。调用 PauseAudio 函数后会整个暂停引擎，调用 ResumeAudio 函数可恢复音频采集。
> 函数原型  
```
GetAudioCtrl -(QAVResult)PauseAudio
```
> 示例代码  
```
[[[ITMGContext GetInstance] GetAudioCtrl] PauseAudio];
```

### 8.恢复音频引擎的采集和播放
调用此函数恢复音频引擎的采集和播放，只在进房后有效。
> 函数原型  
```
GetAudioCtrl -(QAVResult)ResumeAudio
```
> 示例代码  
```
[[[ITMGContext GetInstance] GetAudioCtrl] ResumeAudio];
```

### 9.角色设置
改变流控角色。该方法用于加入频道前设置用户角色，同时允许用户在加入频道后切换角色。
默认自动建6个角色，分别为：”esports””Rhost””Raudience””Werewolf””host””audience”。详细的角色说明请见[游戏多媒体引擎角色说明](https://github.com/TencentMediaLab/GME/blob/master/GME%20Developer%20Manual/GME%20Role%20Manual.md)。
> 函数原型  
```
GetRoom -(void)ChangeRole:(NSString*)role authBuffer:(NSData*)authBuffer
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------
| role    			|NSString     	|设置角色			|
| authBuffer    	|NSData    	|鉴权需要重新设置	|

>注意
流控角色意味着音视频编码参数的调整，所以需要再次调用音视频编码 API 重新设置鉴权（参考生成 AuthBuffer ）。

角色分别代表的通话质量：

|角色名称     | 适用场景         |关键特性|
| ------------- |:-------------:|-------------
| esports    	|适用于MOBA、竞技、射击类游戏     								|普通音质、极低延时	|
| Rhost    	|适用于 MMORPG 类游戏的指挥模式，只有指挥主播可上麦     		|高流畅、低延时		|
| Raudience	|适用于 MMORPG 类游戏的指挥模式，只有指挥主播可上麦     		|高流畅、低延时		|
| Werewolf 	|适用于狼人杀、休闲游戏等										|高音质、网络抗性强	|
| host  		|适用于 MMORPG 类游戏的主播模式，主播可与玩家进行语音视频互动	|高音质、网络抗性强	|
| audience  	|适用于 MMORPG 类游戏的主播模式，主播可与玩家进行语音视频互动	|高音质、网络抗性强	|

> 示例代码  
```
[[[ITMGContext GetInstance]GetRoom ]ChangeRole:@"Playre"authBuffer:authBuffer];
```

### 10.角色设置完成回调
角色设置完成后，回调的事件消息为 ITMG_MAIN_EVENT_TYPE_CHANGE_ROLE，在 OnEvent 函数中对事件消息进行判断。
> 示例代码  
```
-(void)OnEvent:(ITMG_MAIN_EVENT_TYPE)eventType data:(NSDictionary *)data{
    NSLog(@"OnEvent:%lu,data:%@",(unsigned long)eventType,data);
    switch (eventType) {
        case ITMG_MAIN_EVENT_TYPE_CHANGE_ROLE：
        {
	    //角色设置完成
        }
            break;
    }
}
```

### 11.成员状态变化
该事件在状态变化才通知，状态不变化的情况下不通知。如需实时获取成员状态，请在上层收到通知时缓存，事件消息为 ITMG_MAIN_EVNET_TYPE_USER_UPDATE，包含两个信息，event_id 及 endpoints，在 OnEvent 函数中对事件消息进行判断。
> 示例代码  
```
-(void)OnEvent:(ITMG_MAIN_EVENT_TYPE)eventType data:(NSDictionary *)data{
    NSLog(@"OnEvent:%lu,data:%@",(unsigned long)eventType,data);
    switch (eventType) {
        case ITMG_MAIN_EVNET_TYPE_USER_UPDATE：
        {
	    //成员状态变化
        }
            break;
    }
}
```
### 12.开启、关闭音频数据黑名单逻辑
开启黑名单时每次调用黑名单函数，黑名单将被重置为新的成员列表，而不是累加。需要定制所需接收的音频数据才调用，不调用则默认接收房间内所有音频数据。返回值为 0 表示调用失败。
> 函数原型  
```
GetRoom -(QAVResult)SetAudioBlackList:(NSArray*)identifierList
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------
| identifierList    |NSArray      |黑名单列表|
> 示例代码  
```
[[[ITMGContext GetInstance]GetRoom ] GetQualityTips];
```

### 13.麦克风开启关闭事件
此函数用来开启及关闭麦克风。
>注意:加入房间默认不打开麦克风及扬声器。

> 函数原型  
```
GetAudioCtrl -(void)EnableMic:(BOOL)enable
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------
| isEnabled    |boolean     |如果需要打开麦克风，则传入的参数为 YES，如果关闭麦克风，则参数为 NO|
> 示例代码  
```
[[[ITMGContext GetInstance] GetAudioCtrl] EnableMic:YES];
```
### 14.麦克风事件的回调
麦克风事件的回调调用函数 OnEvent，SDK 通过此回调通知 APP 成功调用了麦克风，事件消息为 ITMG_MAIN_EVENT_TYPE_ENABLE_MIC， ITMG_MAIN_EVENT_TYPE_DISABLE_MIC，在 OnEvent 函数中对事件消息进行判断。

> 示例代码  
```
-(void)OnEvent:(ITMG_MAIN_EVENT_TYPE)eventType data:(NSDictionary *)data{
    NSLog(@"OnEvent:%lu,data:%@",(unsigned long)eventType,data);
    switch (eventType) {
        case ITMG_MAIN_EVENT_TYPE_ENABLE_MIC：
        {
	    //打开麦克风
        }
            break;
	case ITMG_MAIN_EVENT_TYPE_DISABLE_MIC：
        {
	    //关闭麦克风
        }
            break;
    }
}
```
### 15.麦克风状态获取
此函数获取麦克风状态，返回值 0 为关闭麦克风状态，返回值 1 为打开麦克风状态，返回值 2 为麦克风设备正在操作中，返回值 4 为设备没初始化好。
> 函数原型  
```
GetAudioCtrl -(int)GetMicState
```
> 示例代码  
```
[[[ITMGContext GetInstance] GetAudioCtrl] GetMicState];
```

### 16.获取麦克风实时音量
此函数用于获取麦克风实时音量，返回值为 int 类型。
> 函数原型  
```
GetAudioCtrl -(int)GetMicLevel
```
> 示例代码  
```
[[[ITMGContext GetInstance] GetAudioCtrl] GetMicLevel];
```

### 17.设置麦克风的软件音量
此函数用于设置麦克风的软件音量。参数 volume 用于设置麦克风的软件音量，当数值为 0 的时候表示静音，当数值为 100 的时候表示音量不增不减，默认数值为 100。
> 函数原型  
```
GetAudioCtrl -(void)SetMicVolume:(int) volume
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------
| volume    |int      |设置音量，范围 0 到 100|
> 示例代码  
```
[[[ITMGContext GetInstance] GetAudioCtrl] SetMicVolume:100];
```

### 18.获取麦克风的软件音量
此函数用于获取麦克风的软件音量。返回值为一个int类型数值。
> 函数原型  
```
GetAudioCtrl -(int) GetMicVolume
```
> 示例代码  
```
[[[ITMGContext GetInstance] GetAudioCtrl] GetMicVolume];
```

### 19.扬声器开启关闭事件
此函数用于设置扬声器开启关闭。
> 函数原型  
```
GetAudioCtrl -(void)EnableSpeaker:(BOOL)enable
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------
| isEnabled    |boolean       |如果需要关闭扬声器，则传入的参数为 NO，如果打开扬声器，则参数为 YES|
> 示例代码  
```
[[[ITMGContext GetInstance] GetAudioCtrl] EnableSpeaker:YES];
```

### 20.扬声器事件的回调
扬声器事件回调，SDK 通过此回调通知 APP 成功调用了扬声器，事件消息为 ITMG_MAIN_EVENT_TYPE_ENABLE_SPEAKER， ITMG_MAIN_EVENT_TYPE_DISABLE_SPEAKER。
> 示例代码  
```
-(void)OnEvent:(ITMG_MAIN_EVENT_TYPE)eventType data:(NSDictionary *)data{
    NSLog(@"OnEvent:%lu,data:%@",(unsigned long)eventType,data);
    switch (eventType) {
        case ITMG_MAIN_EVENT_TYPE_ENABLE_SPEAKER：
        {
	    //打开扬声器
        }
            break;
	case ITMG_MAIN_EVENT_TYPE_DISABLE_SPEAKER：
        {
	    //关闭扬声器
        }
            break;
    }
}
```

### 21.扬声器状态获取
此函数用于扬声器状态获取。返回值为 int 类型数值。返回值 0 为关闭扬声器状态，返回值 1 为打开扬声器状态，返回值 2 为扬声器设备正在操作中，返回值 4 为设备没初始化好。
> 函数原型  
```
GetAudioCtrl -(int)GetSpeakerState
```

> 示例代码  
```
[[[ITMGContext GetInstance] GetAudioCtrl] GetSpeakerState];
```

### 22.获取扬声器实时音量
此函数用于获取扬声器实时音量。返回值为 int 类型数值，表示扬声器实时音量。
> 函数原型  
```
GetAudioCtrl -(int)GetSpeakerLevel
```

> 示例代码  
```
[[[ITMGContext GetInstance] GetAudioCtrl] GetSpeakerLevel];
```

### 23.设置扬声器的软件音量
此函数用于设置扬声器的软件音量。
>注意：参数 volume 用于设置扬声器的软件音量，当数值为 0 的时候表示静音，当数值为 100 的时候表示音量不增不减，默认数值为 100。

> 函数原型  
```
GetAudioCtrl -(void)SetSpeakerVolume:(int)vol 
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------
| vol    |int        |设置音量，范围 0 到 100|
> 示例代码  
```
[[[ITMGContext GetInstance] GetAudioCtrl] SetSpeakerVolume:100];
```

### 24.获取扬声器的软件音量
此函数用于获取扬声器的软件音量。返回值为 int 类型数值，代表扬声器的软件音量。
>注意：Level 是实时音量，Volume 是扬声器的软件音量，最终声音音量相当于 Level*Volume%。举个例子：实时音量是数值是 100 的话，此时Volume的数值是 60，那么最终发出来的声音数值也是 60。

> 函数原型  
```
GetAudioCtrl -(int)GetSpeakerVolume
```
> 示例代码  
```
[[[ITMGContext GetInstance] GetAudioCtrl] GetSpeakerVolume];
```
### 25.启动耳返
此函数用于启动耳返。
> 函数原型  
```
GetAudioCtrl -(QAVResult)EnableLoopBack:(BOOL)enable
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------
| enable    |boolean         |设置是否启动|
> 示例代码  
```
[[[ITMGContext GetInstance] GetAudioCtrl] EnableLoopBack:YES];
```

### 26.开始播放伴奏
调用此函数开始播放伴奏。
注意：1、调用此 API，音量会重置。
2、下行权限不能启用此 API。
> 函数原型  
```
GetAudioEffectCtrl -(QAVAccResult)StartAccompany:(NSString*)filePath loopBack:(BOOL)loopBack loopCount:(int)loopCount
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------
| filePath    	|NSString    		|播放伴奏的路径											|
| loopBack  	|boolean         	|是否混音发送，一般都设置为 YES，即其他人也能听到伴奏	|
| loopCount	|int          		|循环次数，数值为 -1 表示无限循环							|
> 示例代码  
```
[[[ITMGContext GetInstance] GetAudioEffectCtrl] StartAccompany:path loopBack:isLoopBack loopCount:loopCount];
```

### 27.播放伴奏的回调
开始播放伴奏完成后，回调函数调用 OnEvent，事件消息为 ITMG_MAIN_EVENT_TYPE_ACCOMPANY_FINISH，在 OnEvent 函数中对事件消息进行判断。
> 示例代码  
```
-(void)OnEvent:(ITMG_MAIN_EVENT_TYPE)eventType data:(NSDictionary *)data{
    NSLog(@"OnEvent:%lu,data:%@",(unsigned long)eventType,data);
    switch (eventType) {
        case ITMG_MAIN_EVENT_TYPE_ACCOMPANY_FINISH：
        {
	    //完成播放伴奏
        }
            break;
    }
}
```

### 28.停止播放伴奏
调用此函数停止播放伴奏。
> 函数原型  
```
GetAudioEffectCtrl -(QAVAccResult)StopAccompany:(int)duckerTime 
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------
| duckerTimeMs    |int             |淡出时间|

> 示例代码  
```
[[[ITMGContext GetInstance] GetAudioEffectCtrl] StopAccompany:duckerTime];
```

### 29.伴奏是否播放完毕
如果播放完毕，返回值为 YES，如果没播放完，返回值为 NO。
> 函数原型  
```
GetAudioEffectCtrl -(bool)IsAccompanyPlayEnd
```
> 示例代码  
```
[[[ITMGContext GetInstance] GetAudioEffectCtrl] IsAccompanyPlayEnd]; 
```

### 30.暂停播放伴奏
调用此函数暂停播放伴奏。
> 函数原型  
```
GetAudioEffectCtrl -(QAVAccResult)PauseAccompany
```
> 示例代码  
```
[[[ITMGContext GetInstance] GetAudioEffectCtrl] IsAccompanyPlayEnd]; 
```

### 31.重新播放伴奏
此函数用于重新播放伴奏。
> 函数原型  
```
GetAudioEffectCtrl -(QAVAccResult)ResumeAccompany
```
> 示例代码  
```
[[[ITMGContext GetInstance] GetAudioEffectCtrl] ResumeAccompany];
```

### 32.设置自己是否可以听到伴奏
此函数用于设置自己是否可以听到伴奏。
> 函数原型  
```
GetAudioEffectCtrl -(QAVAccResult)EnableAccompanyPlay:(BOOL)enable
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------
| enable    |BOOL             |是否能听到|
> 示例代码  
```
[[[ITMGContext GetInstance] GetAudioEffectCtrl] GetAudioEffectCtrl :YES]; 
```

### 33.设置他人是否也可以听到伴奏
设置播放伴奏的音量，为线性音量，默认值为 100，数值大于 100 伴奏音量增益，数值小于 100 伴奏音量减益。
> 函数原型  
```
GetAudioEffectCtrl -(QAVAccResult)EnableAccompanyLoopBack:(BOOL)enable
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------
| enable    |BOOL             |是否能听到|

> 示例代码  
```
[[[ITMGContext GetInstance] GetAudioEffectCtrl] EnableAccompanyLoopBack:YES]; 
```

### 34.设置伴奏音量
设置播放伴奏的音量，为线性音量，默认值为 100，数值大于 100 伴奏音量增益，数值小于 100 伴奏音量减益。
> 函数原型  
```
GetAudioEffectCtrl -(QAVAccResult)SetAccompanyVolume:(int)vol
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------
| vol    |int             |音量数值|

> 示例代码  
```
[[[ITMGContext GetInstance] GetAudioEffectCtrl] SetAccompanyVolume:volume]; 
```

### 35.获取播放伴奏的音量
此函数用于获取播放伴奏的音量。
> 函数原型  
```
GetAudioEffectCtrl -(int)GetAccompanyVolume
```
> 示例代码  
```
[[[ITMGContext GetInstance] GetAudioEffectCtrl] GetAccompanyVolume];
```

### 36.获得伴奏播放进度
以下两个函数用于获得伴奏播放进度。需要注意：Current / Total = 当前循环次数，Current % Total = 当前循环播放位置。
> 函数原型  
```
GetAudioEffectCtrl -(int)GetAccompanyFileTotalTimeByMs
GetAudioEffectCtrl -(int)GetAccompanyFileCurrentPlayedTimeByMs
```
> 示例代码  
```
[[[ITMGContext GetInstance] GetAudioEffectCtrl] GetAccompanyFileTotalTimeByMs]; 
[[[ITMGContext GetInstance] GetAudioEffectCtrl] GetAccompanyFileCurrentPlayedTimeByMs]; 
```

### 37.设置播放进度
此函数用于设置播放进度。
> 函数原型  
```
GetAudioEffectCtrl -(QAVAccResult)SetAccompanyFileCurrentPlayedTimeByMs:(uint) time
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------
| time    |uint                |播放进度，以毫秒为单位|

> 示例代码  
```
[[[ITMGContext GetInstance] GetAudioEffectCtrl] SetAccompanyFileCurrentPlayedTimeByMs:time];
```

### 38.获取播放音效的音量
获取播放音效的音量，为线性音量，默认值为 100，数值大于 100 为增益效果，数值小于 100 为减益效果。
> 函数原型  
```
GetAudioEffectCtrl -(int)GetEffectsVolume
```
> 示例代码  
```
[[[ITMGContext GetInstance] GetAudioEffectCtrl] GetEffectsVolume]; 
```

### 39.设置播放音效的音量
调用此函数设置播放音效的音量。
> 函数原型  
```
GetAudioEffectCtrl -(QAVResult)SetEffectsVolume:(int)volume
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------
| volume    |int                    |音量数值|

> 示例代码  
```
[[[ITMGContext GetInstance] GetAudioEffectCtrl] SetEffectsVolume:(int)Volume];
```

### 40.播放音效
此函数用于播放音效。参数中音效 id 需要 App 侧进行管理，唯一标识一个独立文件。
> 函数原型  
```
GetAudioEffectCtrl -(QAVResult)PlayEffect:(int)soundId filePath:(NSString*)filePath loop:(BOOL)loop
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------
| soundId  	|int           	|音效 id			|
| filePath    	|NSString    	|音效路径		|
| loop    		|boolean  	|是否重复播放	|
> 示例代码  
```
[[[ITMGContext GetInstance] GetAudioEffectCtrl] PlayEffect:soundId filePath:path loop:isLoop];
```

### 41.暂停播放音效
此函数用于暂停播放音效。
> 函数原型  
```
GetAudioEffectCtrl -(QAVResult)PauseEffect:(int)soundId
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------
| soundId    |int                    |音效 id|

> 示例代码  
```
[[[ITMGContext GetInstance] GetAudioEffectCtrl] PauseEffect:soundId]; 
```

### 42.暂停所有音效
调用此函数暂停所有音效。
> 函数原型  
```
GetAudioEffectCtrl -(QAVResult)PauseAllEffects
```
> 示例代码  
```
[[[ITMGContext GetInstance] GetAudioEffectCtrl] PauseAllEffects]; 
```

### 43.重新播放音效
此函数用于重新播放音效。
> 函数原型  
```
GetAudioEffectCtrl -(QAVResult)ResumeEffect:(int)soundId
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------
| soundId    |int                    |音效 id|
> 示例代码  
```
[[[ITMGContext GetInstance] GetAudioEffectCtrl] ResumeEffect:soundId]; 
```

### 44.重新播放所有音效
调用此函数重新播放所有音效。
> 函数原型  
```
GetAudioEffectCtrl -(QAVResult)ResumeAllEffects
```
> 示例代码  
```
[[[ITMGContext GetInstance] GetAudioEffectCtrl] ResumeAllEffects];
```

### 45.停止播放音效
此函数用于停止播放音效。
> 函数原型  
```
GetAudioEffectCtrl -(QAVResult)StopEffect:(int)soundId
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------
| soundId    |int                    |音效 id|
> 示例代码  
```
[[[ITMGContext GetInstance] GetAudioEffectCtrl] StopEffect:soundId]; 
```

### 46.停止播放所有音效
调用此函数停止播放所有音效。
> 函数原型  
```
GetAudioEffectCtrl -(QAVResult)StopAllEffects
```
> 示例代码  
```
[[[ITMGContext GetInstance] GetAudioEffectCtrl] StopAllEffects]; 
```

### 45.获取诊断信息
获取音视频通话的实时通话质量的相关信息。该函数主要用来查看实时通话质量、排查问题等，业务侧可以不用关心它。
> 函数原型  
```
GetRoom -(NSString*)GetQualityTips
```
> 示例代码  
```
[[[ITMGContext GetInstance]GetRoom ] GetQualityTips]; 
```

## 离线语音接入
### 1.离线语音技术接入初始化
初始化需要传入鉴权 access token 给 TLS 相关函数。鉴权的获取详细流程见[游戏多媒体引擎接入指引](https://github.com/TencentMediaLab/GME/blob/master/GME%20Introduction.md)
。
Error 参数用于传递错误信息，比如参数填错了：appid 填 0、key 为空、identifier 为空之类的情况都会返回错误。
> 函数原型  
```
+(NSString*)genSig:(NSString*)appId identifier:(NSString*)identifier privateKey:(NSString*)privateKey error:(NSError**)error
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------
| sdkAppId  	|int   		|来自腾讯云控制台的 SdkAppId 号码													|
| openID    	|NSString	|唯一标识一个用户，规则由 App 开发者自行制定											|
| key    		|NSString 	|来自腾讯云控制台的鉴权																|
| error    	|Error   		|指定一个传错误值的对象的地址。生成 sig 出错了，返回值为空，error 里面会带上错误信息。	|
```
-(QAVResult)ApplyAccessToken:(NSString*)accessToken
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------
| accessToken    |NSString                       |getTLSSig 函数返回的 accessToken|
> 示例代码  
```
NSString* privateKey = @"自己在官网的 key";
NSString* accessToken = [QAVSDKSigManager genSig:SDKAPPID3RD identifier:_openId privateKey:privateKey error:&error];
[[[ITMGContext GetInstance]GetPTT]ApplyAccessToken:accessToken];
```

### 2.限制最大语音信息时长
限制最大语音消息的长度，最大支持 60 秒。
> 函数原型  
```
GetPTT -(void)SetMaxMessageLength:(int)msTime
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------
| msTime    |int                    |语音时长|
> 示例代码  
```
[[[ITMGContext GetInstance]GetPTT]SetMaxMessageLength:(int)msTime];
```

### 3.启动录音
此函数用于启动录音。
> 函数原型  
```
GetPTT -(void)StartRecording:(NSString*)fileDir
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------
| fileDir    |NSString                     |存放的语音路径|
> 示例代码  
```
[[[ITMGContext GetInstance]GetPTT]StartRecording:path]; 
```

### 4.启动录音的回调
启动录音完成后的回调调用函数 OnEvent，事件消息为 ITMG_MAIN_EVNET_TYPE_PTT_RECORD_COMPLETE， 在 OnEvent 函数中对事件消息进行判断。

> 示例代码  
```
-(void)OnEvent:(ITMG_MAIN_EVENT_TYPE)eventType data:(NSDictionary *)data{
    NSLog(@"OnEvent:%lu,data:%@",(unsigned long)eventType,data);
    switch (eventType) {
        case ITMG_MAIN_EVNET_TYPE_PTT_RECORD_COMPLETE：
        {
	    //录音完成
        }
            break;
    }
}
```

### 5.停止录音
此函数用于停止录音。
> 函数原型  
```
GetPTT -(QAVResult)StopRecording
```
> 示例代码  
```
[[[ITMGContext GetInstance]GetPTT]StopRecording]; 
```

### 6.取消录音
调用此函数取消录音。
> 函数原型  
```
GetPTT -(QAVResult)CancelRecording
```
> 示例代码  
```
[[[ITMGContext GetInstance]GetPTT]CancelRecording]; 
```

### 7.上传语音文件
此函数用于上传语音文件。
> 函数原型  
```
GetPTT -(void)UploadRecordedFile:(NSString*)filePath
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------
| filePath    |NSString                      |上传的语音路径|
> 示例代码  
```
[[[ITMGContext GetInstance]GetPTT]UploadRecordedFile:path]; 
```

### 8.上传语音完成的回调
上传语音完成后，事件消息为 ITMG_MAIN_EVNET_TYPE_PTT_UPLOAD_COMPLETE， 在 OnEvent 函数中对事件消息进行判断。
```
-(void)OnEvent:(ITMG_MAIN_EVENT_TYPE)eventType data:(NSDictionary *)data{
    NSLog(@"OnEvent:%lu,data:%@",(unsigned long)eventType,data);
    switch (eventType) {
        case ITMG_MAIN_EVNET_TYPE_PTT_UPLOAD_COMPLETE：
        {
	    //上传语音完成
        }
            break;
    }
}
```

### 9.下载语音文件
此函数用于下载语音文件。
> 函数原型  
```
GetPTT -(void)DownloadRecordedFile:(NSString*)fileId downloadFilePath:(NSString*)downloadFilePath
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------
| fileID    			|NSString                      |文件的url路径		|
| downloadFilePath 	|NSString                      |文件的下载路径	|
> 示例代码  
```
[[[ITMGContext GetInstance]GetPTT]DownloadRecordedFile:fileIdpath downloadFilePath:path];
```

### 10.下载语音文件完成回调
下载语音完成后，事件消息为 ITMG_MAIN_EVNET_TYPE_PTT_DOWNLOAD_COMPLETE， 在 OnEvent 函数中对事件消息进行判断。
```
-(void)OnEvent:(ITMG_MAIN_EVENT_TYPE)eventType data:(NSDictionary *)data{
    NSLog(@"OnEvent:%lu,data:%@",(unsigned long)eventType,data);
    switch (eventType) {
        case ITMG_MAIN_EVNET_TYPE_PTT_DOWNLOAD_COMPLETE：
        {
	    //完成下载语音文件
        }
            break;
    }	
}
```

### 11.播放语音
此函数用于播放语音。
> 函数原型  
```
GetPTT -(void)PlayRecordedFile:(NSString*)downloadFilePath
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------
| downloadFilePath    |NSString                      |文件的路径|
> 示例代码  
```
[[[ITMGContext GetInstance]GetPTT]downloadFilePath:path]; 
```

### 12.播放语音的回调
播放语音的回调，事件消息为 ITMG_MAIN_EVNET_TYPE_PTT_PLAY_COMPLETE， 在 OnEvent 函数中对事件消息进行判断。
```
-(void)OnEvent:(ITMG_MAIN_EVENT_TYPE)eventType data:(NSDictionary *)data{
    NSLog(@"OnEvent:%lu,data:%@",(unsigned long)eventType,data);
    switch (eventType) {
        case ITMG_MAIN_EVNET_TYPE_PTT_PLAY_COMPLETE：
        {
	    //播放完成
        }
            break;
    }
}
```

### 13.停止播放语音
此函数用于停止播放语音。
> 函数原型  
```
GetPTT -(int)StopPlayFile
```
> 示例代码  
```
[[[ITMGContext GetInstance]GetPTT]StopPlayFile];
```
### 14.获取语音文件的大小
通过此函数，获取语音文件的大小。
> 函数原型  
```
GetPTT -(int)GetFileSize:(NSString*)filePath
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------
| filePath    |NSString                     |语音文件的路径|
> 示例代码  
```
[[[ITMGContext GetInstance]GetPTT]GetFileSize:path]; 
```

### 15.获取语音文件的时长
此函数用于获取语音文件的时长。
> 函数原型  
```
GetPTT -(int)GetVoiceFileDuration:(NSString*)filePath
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------
| filePath    |NSString                     |语音文件的路径|
> 示例代码  
```
[[[ITMGContext GetInstance]GetPTT]GetVoiceFileDuration:path]; 
```

### 16.将指定的语音文件翻译成文字
将指定的语音文件翻译成文字的回调，事件消息为 ITMG_MAIN_EVNET_TYPE_PTT_SPEECH2TEXT_COMPLETE， 在 OnEvent 函数中对事件消息进行判断。
```
-(void)OnEvent:(ITMG_MAIN_EVENT_TYPE)eventType data:(NSDictionary *)data{
    NSLog(@"OnEvent:%lu,data:%@",(unsigned long)eventType,data);
    switch (eventType) {
        case ITMG_MAIN_EVNET_TYPE_PTT_SPEECH2TEXT_COMPLETE：
        {
	    //翻译完成
        }
            break;   
    }
}
```
