# K歌合唱功能

为方便 GME 开发者调试和接入腾讯云游戏多媒体引擎产品 API，本文为您介绍适用于 GME K歌合唱功能的使用参考文档。

## 启动合唱接口

使用 K歌合唱功能，需要启动合唱接口 StartChorusWithOpenID 配合启动伴唱接口 StartChorusVocalAccompaniment 一起调用。这些功能都需要在进房成功之后。

即开启合唱的第一个人调用此接口，伴唱的人等待此接口的回调后启动伴唱接口。当合唱开始后，启动合唱端无法听到房间里面的声音，声音将直接发送到伴唱端，伴唱端将接收到的启动端声音作为伴奏，与自己的声音混合后发送给同房间的所有人。

|声音发起端|声音可达状态|声音接收状态|
|----|----|----|
|合唱发起端|声音只到达伴奏端，但通过伴奏端到达房间其他人|可听到房间内所有声音，建议开启合唱后将伴奏端加入黑名单，以免听到自己的声音|
|伴奏端|将合唱端的声音混合后的声音，可达房间所有人|可听到房间内其他人声音，包括合唱发起端|
|房间内其他开麦成员|声音可达房间所有人|可听见除合唱发起端的其他全部声音，合唱端的声音通过伴奏段可听见|

### 接口原型
```
//发起合唱
-(int)StartChorusWithOpenID:(NSString*)openID;
//停止合唱
-(int)StopChorus;
```

|参数|类型|含义|
|---|----|---|
|openID|NSString*| 伴唱端的 openID|


### 返回值
接口返回值为 QAV_OK 则表示成功。

回调返回 1004 表示参数错误，建议重新检查参数是否正确。回调返回 1001 表示重复操作；返回 1201 表示房间不存在，建议检查房间号是否正确；如果返回 10001 以及 1005 ，建议重新调用接口一次。更多错误码请参考[错误码文档](https://cloud.tencent.com/document/product/607/15173)。

### 回调 ITMG_MAIN_EVENT_TYPE_CHORUS_EVENT

成功启动合唱功能之后，本端会收到 ITMG_MAIN_EVENT_TYPE_CHORUS_EVENT 的回调，伴奏端也会收到 ITMG_MAIN_EVENT_TYPE_CHORUS_EVENT 回调。
回调的 data 包含以下三个信息：**事件类型 sub_type、事件结果 result 以及合唱的openID Accompanier_Openid**。

**接收合唱端（伴唱端）**

当调用发起合唱接口后，首先伴奏端会收到回调，并进行处理：

|事件类型|含义|处理方式|
|------|-------|---|
|ITMG_MAIN_EVENT_TYPE_CHORUS_SUB_EVENT_ACCOMPANIER_OPTION|收到来自某 openID 的合唱请求|判断请求|

此时进行判断：
如果接受的话调用 StartChorusVocalAccompaniment ，发起端会收到*ITMG_MAIN_EVENT_TYPE_CHORUS_SUB_EVENT_START*；
否则调用 StopChorusVocalAccompaniment ，发起端会收到*ITMG_MAIN_EVENT_TYPE_CHORUS_SUB_EVENT_STATUS_REFUSE*。

**发起合唱端**

当伴奏端进行处理后，会有相应的状态回调返回到发起合唱端：

|事件类型|含义|处理方式|
|------|-------|---|
|ITMG_MAIN_EVENT_TYPE_CHORUS_SUB_EVENT_START|某 openID **接受**了合唱请求|判断 result，如果是 0，则发起合唱成功；否则判断错误码进行处理|
|ITMG_MAIN_EVENT_TYPE_CHORUS_SUB_EVENT_STATUS_REFUSE|某 openID **拒绝**了合唱请求|业务层可提示本端对方拒绝请求|

发起合唱成功后，可调用 AddAudioBlackList 接口将伴奏端 openID 加入黑名单，以免听到自己延迟的声音。结束后可以调用 RemoveAudioBlackList 去除伴奏端的黑名单效果。
如果合唱过程中需要预览伴奏端声音，也可通过将伴奏端暂时移除黑名单来实现（此时可以听到自己与伴奏端合唱的声音）。

**合唱过程中的回调事件处理**

在合唱过程中，如果有停止合唱的接口调用，本端会收到 *ITMG_MAIN_EVENT_TYPE_CHORUS_SUB_EVENT_STOP*，对端会收到 *ITMG_MAIN_EVENT_TYPE_CHORUS_SUB_EVENT_STOP_BY_PEER*。

合唱发起端无法听到对端声音，可以通过回调事件 *ITMG_MAIN_EVENT_TYPE_CHORUS_SUB_EVENT_HAS_NO_CMD_PACK* 及 *ITMG_MAIN_EVENT_TYPE_CHORUS_SUB_EVENT_HAS_CMD_PACK* 来得此时的合唱状态是否异常或者是否恢复正常状态（一般是网络断线等导致）。

伴奏端可直接通过 人耳是否能听到合唱发起端的声音 来判断此时的合唱状态。

|事件类型|含义|处理方式|
|------|-------|---|
|ITMG_MAIN_EVENT_TYPE_CHORUS_SUB_EVENT_STOP|本端停止合唱的回调|在合唱过程中，如果本端调用 StopChorus/StopChorusVocalAccompaniment（具体看本端是发起端或伴奏端），会有此回调。|
|ITMG_MAIN_EVENT_TYPE_CHORUS_SUB_EVENT_STOP_BY_PEER|对端停止合唱|在合唱过程中，如果对端调用 StopChorus/StopChorusVocalAccompaniment ，会有此回调|
|ITMG_MAIN_EVENT_TYPE_CHORUS_SUB_EVENT_HAS_NO_CMD_PACK|超过四秒没收到对端信令数据|检查合唱状态是否正确或对方是否在线（一般为合唱发起端调用）|
|ITMG_MAIN_EVENT_TYPE_CHORUS_SUB_EVENT_HAS_CMD_PACK|收到来自对端的信令数据（持续收到只提示一次）|说明合唱状态恢复正常|


### 示例代码

**执行语句**

```
ret = (int)[[[ITMGContext GetInstance] GetRoom] StartChorusWithOpenID:_shareOpenID.text];
```

**回调**
```
-(void)OnEvent:(ITMG_MAIN_EVENT_TYPE)eventType data:(NSDictionary *)data{
    NSString* log =[NSString stringWithFormat:@"OnEvent:%d,data:%@", (int)eventType, data];
    switch (eventType) {
                case ITMG_MAIN_EVENT_TYPE_CHORUS_EVENT:
                {
                    int sub_event =  ((NSNumber*)[data objectForKey:@"sub_type"]).intValue;
                    int result =  ((NSNumber*)[data objectForKey:@"result"]).intValue;
                    NSString *Accompanier_Openid = [data objectForKey:@"Accompanier_Openid"];
                    AccompanimentOpenID = Accompanier_Openid;
                    UIAlertView *alert  = nil;
                    if (sub_event == ITMG_MAIN_EVENT_TYPE_CHORUS_SUB_EVENT_ACCOMPANIER_OPTION ) {
                        alert = [[UIAlertView alloc] initWithTitle:[NSString stringWithFormat:@"收到来自:%@ 合唱请求 result:%d",Accompanier_Openid,result]  message:@"" delegate:self cancelButtonTitle:@"开" otherButtonTitles:@"关",nil];
                    } else if (ITMG_MAIN_EVENT_TYPE_CHORUS_SUB_EVENT_START == sub_event) {
                        if (result == QAV_OK)
                            alert = [[UIAlertView alloc] initWithTitle:[NSString stringWithFormat:@"发起和%@的合唱成功,可以开启伴奏",Accompanier_Openid]  message:@"" delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
                        else
                            alert = [[UIAlertView alloc] initWithTitle:[NSString stringWithFormat:@"发起和%@的合唱失败   错误码:%d",Accompanier_Openid,result]  message:@"" delegate:nil cancelButtonTitle:@"ok" otherButtonTitles:nil];
                    }else if (ITMG_MAIN_EVENT_TYPE_CHORUS_SUB_EVENT_STOP == sub_event) {
                        alert = [[UIAlertView alloc] initWithTitle:[NSString stringWithFormat:@"终止和%@的伴唱 操作结果错误码:%d",Accompanier_Openid,result]  message:@"" delegate:nil cancelButtonTitle:@"ok" otherButtonTitles:nil];
                    }
                    else if (ITMG_MAIN_EVENT_TYPE_CHORUS_SUB_EVENT_STOP_BY_PEER == sub_event) {
                        alert = [[UIAlertView alloc] initWithTitle:[NSString stringWithFormat:@"和%@的伴唱被终止 操作结果错误码:%d",Accompanier_Openid,result]  message:@"" delegate:nil cancelButtonTitle:@"ok" otherButtonTitles:nil];
                    }else if (ITMG_MAIN_EVENT_TYPE_CHORUS_SUB_EVENT_HAS_NO_CMD_PACK == sub_event){
                        alert = [[UIAlertView alloc] initWithTitle:[NSString stringWithFormat:@"超过四秒没到来自 %@ 的信令数据，请检查合唱状态是否正确或对方是否在线",Accompanier_Openid]  message:@"" delegate:nil cancelButtonTitle:@"ok" otherButtonTitles:nil];
                    } else if(ITMG_MAIN_EVENT_TYPE_CHORUS_SUB_EVENT_STATUS_REFUSE == sub_event) {
                        alert = [[UIAlertView alloc] initWithTitle:[NSString stringWithFormat:@"请求被拒绝 %@",Accompanier_Openid]  message:@"" delegate:nil cancelButtonTitle:@"ok" otherButtonTitles:nil];
                    } else if (ITMG_MAIN_EVENT_TYPE_CHORUS_SUB_EVENT_HAS_CMD_PACK == sub_event){
                        alert = [[UIAlertView alloc] initWithTitle:[NSString stringWithFormat:@"收到来自 %@ 的信令数据",Accompanier_Openid]  message:@"" delegate:nil cancelButtonTitle:@"ok" otherButtonTitles:nil];
                    }
            
                    if (alert) {
                        alert.tag = sub_event+100;
                        [alert show];
                    }
                }
            }
        default:
            break;
    }
}
```


## 伴唱接口

在获取到启动合唱接口 StartChorusWithOpenID 的回调后调用。

### 接口原型

```
//发起伴唱
-(int)StartChorusVocalAccompaniment:(NSString*)openID;
//结束伴唱
-(int)StopChorusVocalAccompaniment;
```

|参数|类型|含义|
|---|----|---|
|openID|NSString*| 合唱发起端的 openID|

### 示例代码

```
ret = [[[ITMGContext GetInstance] GetRoom] StartChorusVocalAccompaniment:AccompanimentOpenID];
```