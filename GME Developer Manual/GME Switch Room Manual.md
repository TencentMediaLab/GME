# 快速切换房间接口

为方便 GME 开发者调试和接入腾讯云游戏多媒体引擎产品 API，本文为您介绍适用于 GME 快速切换房间功能的使用参考文档。

## 快速切换房间
调用此接口快速切换实时语音房间。此接口在进房后调用。切换房间后，不重置设备，即如果在此房间已经是打开麦克风状态，在切换房间后也会是打开麦克风状态。

### 接口原型
```
-(int) SwitchRoom:(NSString *)roomID  authBuffer:(NSData*)authBuffer;
```

### 类型说明

|参数   |类型   |含义   |
|----------|-------|-------|
|roomID |NSString *|将要进入的房间号|
|authBuffer        |NSData*|用将要进入的房间号生成的新鉴权|

### 回调

```
ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVENT_TYPE_SWITCH_ROOM
```



更多错误码请参考[错误码文档](https://cloud.tencent.com/document/product/607/15173)。
