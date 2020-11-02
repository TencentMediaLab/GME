# 自定义音频转发路由

为方便 GME 开发者调试和接入腾讯云游戏多媒体引擎产品 API，本文为您介绍适用于 GME 自定义音频转发路由功能的使用参考文档。

## 设置音频转发规则
调用此接口设置音频转发规则，此接口在进房成功回调中调用，调用后此次进房生效，退房后失效。

### 接口原型
```
-(int)SetServerAudioRouteSendOperateType:(ITMG_SERVER_AUDIO_ROUTE_SEND_TYPE) Sendtype  SendList:(NSArray *)OpenIDForSend  RecvOperateType:(ITMG_SERVER_AUDIO_ROUTE_RECV_TYPE) Recvtype RecvList:(NSArray *)OpenIDForRecv;
```

### 类型说明

#### ITMG_SERVER_AUDIO_ROUTE_SEND_TYPE**

设置发送音频规则，不同的规则填入后，会有不同的发送规则。

|接收类型   |效果   |
|----------|-------|
|AUDIO_ROUTE_NOT_SEND_TO_ANYONE |本端音频上行发送到后台，但后台不转发给任何人，相当于将自己静音，此时参数 OpenIDForSend 无效，只需填 null|
|AUDIO_ROUTE_SEND_TO_ALL        |本端音频上行将转发给所有人，此时参数 OpenIDForSend 无效，只需填 null|
|AUDIO_ROUTE_SEND_BLACK_LIST    |本端音频上行将不转发给黑名单的人，黑名单由参数 OpenIDForSend 提供|
|AUDIO_ROUTE_SEND_WHITE_LIST    |本端音频上行将只转发给白名单的人，白名单由参数 OpenIDForSend 提供|

即如果类型传入 AUDIO_ROUTE_NOT_SEND_TO_ANYONE 以及 AUDIO_ROUTE_SEND_TO_ALL ，
此时的参数 OpenIDForSend 不生效，只需要填 null；

如果类型传入 AUDIO_ROUTE_SEND_BLACK_LIST ，此时参数 OpenIDForSend 为黑名单列表；

如果类型传入 AUDIO_ROUTE_SEND_WHITE_LIST ，此时参数 OpenIDForSend 为白名单列表。


#### ITMG_SERVER_AUDIO_ROUTE_RECV_TYPE**

设置接收音频规则，不同的规则填入后，会有不同的接收规则。

|接收类型   |效果   |
|----------|-------|
|AUDIO_ROUTE_NOT_RECV_FROM_ANYONE |本端不接受任何音频，相当于关闭房间内扬声器效果，此时参数 OpenIDForSend 无效，只需填 null|
|AUDIO_ROUTE_RECV_FROM_ALL        |本端接收所有人的音频，此时参数 OpenIDForSend 无效，只需填 null|
|AUDIO_ROUTE_RECV_BLACK_LIST    |本端不接收黑名单的人的音频声音，黑名单由参数 OpenIDForSend 提供|
|AUDIO_ROUTE_RECV_WHITE_LIST    |本端只接收白名单的人的音频声音，白名单由参数 OpenIDForSend 提供|

即如果类型传入 AUDIO_ROUTE_NOT_RECV_FROM_ANYONE 以及 AUDIO_ROUTE_RECV_FROM_ALL OpenIDForSend 不生效；如果类型传入 AUDIO_ROUTE_RECV_BLACK_LIST ，此时参数 OpenIDForSend 为黑名单列表；如果类型传入 AUDIO_ROUTE_RECV_WHITE_LIST ，此时参数 OpenIDForSend 为白名单列表。


### 返回值
接口返回值为 QAV_OK 则表示成功。

回调返回 1004 表示参数错误，建议重新检查参数是否正确。回调返回 1001 表示重复操作；返回 1201 表示房间不存在，建议检查房间号是否正确；如果返回 10001 以及 1005 ，建议重新调用接口一次。更多错误码请参考[错误码文档](https://cloud.tencent.com/document/product/607/15173)。


### 示例代码

**执行语句**

```
@synthesize _sendListArray;
@synthesize _recvListArray;

int ret =  [[[ITMGContext GetInstance] GetRoom] SetServerAudioRouteSendOperateType:SendType  SendList:_sendListArray RecvOperateType:RecvType RecvList:_recvListArray];
if (ret != QAV_OK) {
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"更新audioroute列表失败" message:[NSString stringWithFormat:@"错误码:%d",ret] delegate:NULL cancelButtonTitle:@"OK" otherButtonTitles:nil];
    [alert show];
}
```

**回调**
```
-(void)OnEvent:(ITMG_MAIN_EVENT_TYPE)eventType data:(NSDictionary *)data{
    NSString* log =[NSString stringWithFormat:@"OnEvent:%d,data:%@", (int)eventType, data];
    switch (eventType) {
                case ITMG_MAIN_EVENT_TYPE_SERVER_AUDIO_ROUTE_EVENT:{
            {
                UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"更新audioroute" message:[NSString stringWithFormat:@"结果:%@,sub_type: %@ errorinof: %@", data[@"result"],data[@"sub_type"],data[@"error_info"]] delegate:NULL cancelButtonTitle:@"OK" otherButtonTitles:nil];
                              [alert show];
            }
        }
        default:
            break;
    }
}
```


## 获取音频设置转发规则
调用此接口获取音频转发规则。调用后传入的参数会返回结果。

### 接口原型

```
-(int)GetServerAudioRouteSendOperateType:(ITMG_SERVER_AUDIO_ROUTE_SEND_TYPE *) Sendtype  SendList:(NSMutableArray*) OpenIDForSend  RecvOperateType:(ITMG_SERVER_AUDIO_ROUTE_RECV_TYPE *) Recvtype RecvList:(NSMutableArray *)OpenIDForRecv;
```

