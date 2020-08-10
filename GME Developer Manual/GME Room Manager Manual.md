## GME 房间管理功能接入文档
此文档以 iOS 平台 Objective-C 代码进行示例演示。



## 工程配置
导入 GME SDK 之后，参照下图将 ImSDK.framework 设置为 Embed&Sign。

**XCode10：**

![](https://main.qcloudimg.com/raw/70b56125788c041b13ca262df2af117b.png)

**XCode11：**

![](https://main.qcloudimg.com/raw/40e4457650d8f2d6a62f41b18146a5a2.png)

## ITMGRoomManager
GME 房间管理功能在进房后才可调用，且只能修改房间内成员的状态。
所有接口的结果会通过 ITMG_MAIN_EVNET_TYPE_ROOM_MANAGEMENT_OPERATOR 回调，回调详情参考文末回调处理。

```
@interface ITMGRoomManager :NSObject
```

接口中需要传参数 receiverID，即接收方初始化 SDK 时候使用的 OpenId；如果参数传的是 “ALL”，会对房间内所有人生效。

**注意**

采集管理相关接口无法使用参数“ALL”。


## 采集管理相关接口
此部分内容包含**麦克风管理**、**音频上行管理**以及**采集硬件设备管理**，其中麦克风管理相当于音频上行管理加上采集硬件设备管理。区分出音频数据传输上下行与硬件设备的管理是因为打开或者关闭采集设备，会伴随整个设备（采集及播放）重启，如果此时 App 正在播放背景音乐，那么背景音乐的播放也会被中断。利用控制上下行的方式来实现开关麦克风效果，不会中断播放设备。

**采集管理中，涉及到打开某人麦克风、打开某人音频上行、打开某人采集设备的操作，通过OnEvent通知业务层，再由业务层判断后进行打开麦克风、打开上行或者打开采集设备的操作。如果是关闭操作，则直接通过SDK操作关闭，无需业务层处理。**

### 麦克风管理
调用此接口通知房间内某用户打开或关闭麦克风，调用成功后，如果是打开麦克风，该用户将会收到麦克风将被打开的通知，如果是关闭麦克风，则SDK直接关闭该用户麦克风。
EnableMic 相当于同时调用 EnableAudioSend 及 EnableAudioCaptureDevice。
#### 函数原型
```
-(QAVResult)EnableMic:(BOOL)enable Receiver:(NSString *)receiverID;
```

|参数|类型|含义|
|----|---|----|
|enable|BOOL|填入 YES 即打开某用户麦克风；填入 NO 即关闭某用户麦克风|
|receiverID|NSString*|填入目标用户 OpenId|

#### 回调
回调参数为 ITMG_ROOM_MANAGEMENT_MIC_OP。

### 音频上行管理

调用此接口通知房间内某用户打开或关闭音频上行，调用成功后，如果是打开音频上行，该用户将会收到音频上行将被打开的通知，如果是关闭音频上行，则SDK直接关闭该用户音频上行。

#### 函数原型
```
-(QAVResult)EnableAudioSend:(BOOL)enable Receiver:(NSString *)receiverID;
```

|参数|类型|含义|
|----|---|----|
|enable|BOOL|填入 YES 即打开某用户上行；填入 NO 即关闭某用户上行|
|receiverID|NSString*|填入目标用户 OpenId|

#### 回调
回调参数为 ITMG_ROOM_MANAGEMENT_AUDIO_SEND_OP。

### 音频采集硬件设备管理
调用此接口通知房间内某用户打开或关闭采集设备，调用成功后，如果是打开采集设备，该用户将会收到采集设备将被打开的通知，如果是关闭采集设备，则SDK直接关闭该用户采集设备。

#### 函数原型
```
-(QAVResult)EnableAudioCaptureDevice:(BOOL)enabled Receiver:(NSString *)receiverID;
```

|参数|类型|含义|
|----|---|----|
|enable|BOOL|填入 YES 即打开某用户音频采集硬件设备；填入 NO 即关闭某用户音频采集硬件设备|
|receiverID|NSString*|填入目标用户 OpenId|

#### 回调
回调参数为 ITMG_ROOM_MANAGEMENT_CAPTURE_OP。

## 播放管理相关接口
此部分内容包含**扬声器管理**、**音频下行管理**以及**播放硬件设备管理**，其中扬声器管理相当于音频下行管理加上播放硬件设备管理。区分出音频数据传输上下行与硬件设备的管理是因为打开或者关闭采集设备，会伴随整个设备（采集及播放）重启，如果此时 App 正在播放背景音乐，那么背景音乐的播放也会被中断。利用控制上下行的方式来实现开关麦克风效果，不会中断播放设备。
### 扬声器管理

调用此接口打开或关闭房间内某用户的扬声器。调用成功后，该用户的扬声器将被关闭或打开，听到房间内的音频声音。
EnableSpeaker 相当于同时调用 EnableAudioRecv 及 EnableAudioPlayDevice。
#### 函数原型
```
-(QAVResult)EnableSpeaker:(BOOL)enable Receiver:(NSString *)receiverID;
```

|参数|类型|含义|
|----|---|----|
|enable|BOOL|填入 YES 即打开某用户扬声器；填入 NO 即关闭某用户扬声器|
|receiverID|NSString*|填入目标用户 OpenId|

#### 回调
回调参数为 ITMG_ROOM_MANAGEMENT_SPEAKER_OP。

### 音频下行管理
调用此接口打开或关闭房间内某用户的音频下行。调用成功后，该用户的音频下行将被关闭或打开，但不影响播放设备。

#### 函数原型
```
-(QAVResult)EnableAudioRecv:(BOOL)enabled Receiver:(NSString *)receiverID;
```

|参数|类型|含义|
|----|---|----|
|enable|BOOL|填入 YES 即打开某用户音频下行；填入 NO 即关闭某用户音频下行|
|receiverID|NSString*|填入目标用户 OpenId|

#### 回调
回调参数为 ITMG_ROOM_MANAGEMENT_AUDIO_REC_OP。


### 音频播放硬件设备管理
调用此接口打开或关闭房间内某用户的音频播放硬件设备。调用成功后，该用户的音频播放硬件设备将被关闭或打开，但不影响下行。

#### 函数原型
```
-(QAVResult)EnableAudioPlayDevice:(BOOL)enabled Receiver:(NSString *)receiverID;
```

|参数|类型|含义|
|----|---|----|
|enable|BOOL|填入 YES 即打开某用户音频播放硬件设备；填入 NO 即关闭某用户音频播放硬件设备|
|receiverID|NSString*|填入目标用户 OpenId|

#### 回调
回调参数为 ITMG_ROOM_MANAGEMENT_PLAY_OP。

## 获取成员状态接口

### 获取某人麦克风状态
调用此接口，获取房间内某成员的麦克风状态。

#### 函数原型
```
-(QAVResult)GetMicState:(NSString *)receiverID;
```

|参数|类型|含义|
|----|---|----|
|receiverID|NSString*|填入目标用户 OpenId|


#### 回调
回调参数为 ITMG_ROOM_MANAGEMENT_GET_MIC_STATE。

### 获取某人扬声器状态
调用此接口，获取房间内某成员的扬声器状态。
#### 函数原型
```
-(QAVResult)GetSpeakerState:(NSString *)receiverID;
```

#### 回调
回调参数为 ITMG_ROOM_MANAGEMENT_GET_SPEAKER_STATE。


## 其他接口

### 禁止某人操作麦克风及扬声器
调用此接口可以禁止房间内的某位成员操作麦克风及扬声器，该成员退房后失效。成员进房默认是允许操作麦克风及扬声器。
 
```
-(QAVResult)ForbidUserOperation:(BOOL)enable Receiver:(NSString *)receiverID;
```

|参数|类型|含义|
|----|---|----|
|enable|BOOL|填入 YES 即允许某用户操作设备；填入 NO 即禁止某用户操作设备|
|receiverID|NSString*|填入目标用户 OpenId|

#### 回调
回调参数为 ITMG_ROOM_MANAGERMENT_FOBIN_OP。


## 回调处理
与 GME 其他回调相同，房间管理的回调也在 OnEvent 中处理，事件名称为 ITMG_MAIN_EVNET_TYPE_ROOM_MANAGEMENT_OPERATOR，事件会返回一个结构体，具体包含的内容如下。

#### 回调参数
|参数|类型|含义|
|---|---|---|
|SenderID|NSString|事件发送者Id，如果与自己 OpenId 相同，即为本端发送的命令|
|ReceiverID|NSString|事件接收者Id，如果与自己 OpenId 相同，即为本端接收的命令|
|OperateType|NSNumber|事件类型|
|Result|NSNumber|事件结果，0为成功|
|OperateValue|NSNumber|命令详情|

#### OperateType

|数值|事件类型|含义|
|---|---|---|
|0|ITMG_ROOM_MANAGEMENT_CAPTURE_OP|控制采集设备硬件回调|
|1|ITMG_ROOM_MANAGEMENT_PLAY_OP|控制播放设备硬件回调|
|2|ITMG_ROOM_MANAGEMENT_AUDIO_SEND_OP|控制上行回调|
|3|ITMG_ROOM_MANAGEMENT_AUDIO_REC_OP|控制下行回调|
|4|ITMG_ROOM_MANAGEMENT_MIC_OP|控制麦克风回调|
|5|ITMG_ROOM_MANAGEMENT_PLAY_OP|控制扬声器回调|
|6|ITMG_ROOM_MANAGEMENT_GET_MIC_STATE|获取麦克风状态|
|7|ITMG_ROOM_MANAGEMENT_GET_SPEAKER_STATE|获取扬声器状态|
|8|ITMG_ROOM_MANAGERMENT_FOBIN_OP|禁止操作麦克风及扬声器事件|

#### OperateValue

|成员|含义|
|---|---|
|boolValue|0为关闭命令，1为打开命令|


#### 示例代码
```
-(void)OnEvent:(ITMG_MAIN_EVENT_TYPE)eventType data:(NSDictionary *)data{
    NSString* log =[NSString stringWithFormat:@"OnEvent:%d,data:%@", (int)eventType, data];
    [self showLog:log];
    NSLog(@"====%@====",log);
    switch (eventType) {
		case ITMG_MAIN_EVNET_TYPE_ROOM_MANAGEMENT_OPERATOR:
        {
            NSArray *operatorArr = @[@"采集",@"播放",@"上行",@"下行",@"采集上行",@"播放下行",@"mic状态",@"spk状态",@"禁止操作mic/speak"];
			// _openId
            NSString *SenderID = [data objectForKey:@"SenderID"];
            NSString *ReceiverID = [data objectForKey:@"ReceiverID"];
            NSNumber *OperateType = [data objectForKey:@"OperateType"];
            NSNumber *Result = [data objectForKey:@"Result"];
            NSNumber *OperateValue = [data objectForKey:@"OperateValue"];
            
            //自己发出去的命令
            if ([SenderID isEqualToString:_openId]) {
                if (OperateType.intValue == ITMG_ROOM_MANAGEMENT_GET_MIC_STATE || OperateType.intValue == ITMG_ROOM_MANAGEMENT_GET_SPEAKER_STATE) {
                          NSString *alterString = [NSString stringWithFormat:@"发送给id:%@ 的%@操作,结果:%@",ReceiverID,operatorArr[OperateType.intValue],OperateValue.boolValue?@"开":@"关"];
                                             UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"房间管理操作" message:alterString delegate:NULL cancelButtonTitle:@"OK" otherButtonTitles:nil];
                                             [alert show];
                }
                else
                {
                    NSString *alterString = [NSString stringWithFormat:@"发送给id:%@ 的%@%@操作,结果:%@",ReceiverID,OperateValue.boolValue?@"开":@"关",operatorArr[OperateType.intValue],Result];
                               UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"房间管理操作" message:alterString delegate:NULL cancelButtonTitle:@"OK" otherButtonTitles:nil];
                               [alert show];
                }
           
                
            } 
			else if([ReceiverID isEqualToString:_openId] ){ //别人发过来的命令
            	if (Result.intValue == 0) {
                	switch (OperateType.intValue) {
                        case ITMG_ROOM_MANAGEMENT_CAPTURE_OP:{
                            [_micSwitch setOn:OperateValue.boolValue animated:true];
                        }
                            break;
                        case ITMG_ROOM_MANAGEMENT_PLAY_OP:{
                            [_speakerSwitch setOn:OperateValue.boolValue animated:true];
                            }
                            break;
                        case ITMG_ROOM_MANAGEMENT_AUDIO_SEND_OP:{
                            [_sendSwitch setOn:OperateValue.boolValue animated:true];
                        }
                            break;
                        case ITMG_ROOM_MANAGEMENT_AUDIO_REC_OP:{
                            [_recvSwitch setOn:OperateValue.boolValue animated:true];
                        }
                            break;
                        case ITMG_ROOM_MANAGEMENT_MIC_OP:{
                        [_micSwitch setOn:OperateValue.boolValue animated:true];
                        [_sendSwitch setOn:OperateValue.boolValue animated:true];
                        }
                            break;
                        case ITMG_ROOM_MANAGEMENT_SPEAKER_OP:{
                        [_speakerSwitch setOn:OperateValue.boolValue animated:true];
                        [_recvSwitch setOn:OperateValue.boolValue animated:true];
                            
                        }
                            break;
                        default:
                            break;
                    }
                
                if (OperateType.intValue == ITMG_ROOM_MANAGEMENT_GET_MIC_STATE || OperateType.intValue == ITMG_ROOM_MANAGEMENT_GET_SPEAKER_STATE) {
                        NSString *alterString = [NSString stringWithFormat:@"收到id:%@ 发送来的%@操作,结果:%@",SenderID,operatorArr[OperateType.intValue],OperateValue.boolValue?@"开":@"关"];
                                UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"房间管理操作" message:alterString delegate:NULL cancelButtonTitle:@"OK" otherButtonTitles:nil];
                                [alert show];
                    }
                else{
                    	NSString *alterString = [NSString stringWithFormat:@"收到id:%@ 发送来的%@%@操作,结果:%@",SenderID,OperateValue.boolValue?@"开":@"关",operatorArr[OperateType.intValue],Result];
                              UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"房间管理操作" message:alterString delegate:NULL cancelButtonTitle:@"OK" otherButtonTitles:nil];
                              [alert show];
                	}
            	}
			}
    	}
        break;
}
```


## 错误码
#### AV_ERR_IMSDK_FAILD = 6999
出现此错误说明调用 RoomManager 相关接口出现问题，请检查：

1、参数 receiveID 是否在同一房间内。

2、检查参数合法性。

最后请提供日志，日志路径参考[此链接](https://cloud.tencent.com/document/product/607/30410#.E5.A6.82.E4.BD.95.E5.8F.96.E5.BE.97.E6.97.A5.E5.BF.97.EF.BC.9F)。
