# 动态设置房间内某个成员声音大小

为方便 GME 开发者调试和接入腾讯云游戏多媒体引擎产品 API，本文为您介绍适用于 GME 用户动态设置自身听到的某个成员声音大小（百分比）。

## 设置某个成员的声音大小

调用此接口需要在进房回调事件ITMG_MAIN_EVENT_TYPE_ENTER_ROOM且errorCode为AV_OK之后设置。

### 接口原型

```
//Android 接口
public abstract int SetSpeakerVolumeByOpenID(String openId, int volume);
//iOS接口
-(int) SetSpeakerVolumeByOpenID:(NSString *)openId volume:(int)volume;
//C++接口
virtual int SetSpeakerVolumeByOpenID(const char* openId, int volume) = 0;
```

### 参数说明

|参数   |类型   |含义   |
|----------|-------|-------|
|openId       |String *   |需要调节音量大小的OpenID|
|volume  |int        |百分比，建议[0-200]，其中100为默认值|

### 返回值

接口返回值为 QAV_OK 则表示成功。

更多错误码请参考【https:/cloud.tencent.com/document/product/607/15173】。

### 示例代码

**执行语句**

```
// 将123333的声音压低到现在声音的80%
String strOpenID = "1233333";
int nOpenVolume = Integer.valueOf(80);
int nRet = ITMGContext.GetInstance(getActivity()).GetAudioCtrl().SetSpeakerVolumeByOpenID(strOpenID, nOpenVolume);
if (nRet != 0)
{
  // Toast error occured
}
else
{
  // Toast set successfully
}
```

## 获取设置的声音百分比

调用此接口获取SetSpeakerVolumeByOpenID设置的能量值

### 接口原型

```
//Android 接口
public abstract int GetSpeakerVolumeByOpenID(String openId);
//iOS接口
-(int) GetSpeakerVolumeByOpenID:(NSString *)openId;
//C++接口
virtual int GetSpeakerVolumeByOpenID(const char* openId) = 0;
```

### 返回值

如果接口返回 OpenID 设置的能量百分比， 默认返回100