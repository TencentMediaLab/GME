# 快速切换房间接口

为方便 GME 开发者调试和接入腾讯云游戏多媒体引擎产品 API，本文为您介绍适用于 GME 跨房连麦功能的使用参考文档。

## 跨房连麦
调用此接口进行跨房连麦。此接口在进房后调用。

### 接口原型

```
//开始连麦
-(int) StartRoomSharing:(NSString *)targetRoomID targetOpenID:(NSString *)targetOpenID authBuffer:(NSData *)authBuffe

//停止连麦
-(int) StopRoomSharing
```

### 类型说明

|参数   |类型   |含义   |
|----------|-------|-------|
|targetRoomID |NSString *|将要连麦的房间号|
|targetOpenID        |NSString *|将要连麦的目标OpenID|
|authBuffer        |NSdata *|保留标志位，只需填 NULL|

#### 示例代码

```
- (IBAction)shareRoom:(id)sender {
    if(_shareRoomSwitch.isOn){
        [[[ITMGContext GetInstance]GetRoom]StartRoomSharing:_shareRoomID.text targetOpenID:_shareOpenID.text authBuffer:NULL];
    }else{
        [[[ITMGContext GetInstance]GetRoom]StopRoomSharing];
    }
}
```

错误码请参考[错误码文档](https://cloud.tencent.com/document/product/607/15173)。
