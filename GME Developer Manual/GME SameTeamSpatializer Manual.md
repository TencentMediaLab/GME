# 修改范围语音队伍成员语音3D效果

为方便 GME 开发者调试和接入腾讯云游戏多媒体引擎产品 API，本文为您介绍适用于 GME 修改范围语音队伍成员语音3D效果的使用参考文档。

## 修改范围语音队伍成员语音3D效果
此接口需要在进房后调用，且确保已经接入3D音效且3D效果可用的情况下，调用接口修改目标 openid 的声音3D效果。此接口只对本端生效，且在范围语音队伍中才生效。

### 接口原型

```

//Unity接口
public abstract int AddSameTeamSpatializer(string openId);
public abstract int RemoveSameTeamSpatializer(string openId);
```

### 参数说明

|参数   |类型   |含义   |
|----------|-------|-------|
|openId       |string   |需要修改3D音效效果的目标 openid|

### 回调
接口返回值为 QAV_OK 则表示成功。

更多错误码请参考【https:/cloud.tencent.com/document/product/607/15173】。

### 示例代码

```
transform.Find("inroomPanel/effectPanel/3DVoicePanel/3DToggle").gameObject.GetComponent<Toggle>().onValueChanged.AddListener(delegate (bool value)
       {
           if (value)
           {
               int ret = ITMGContext.GetInstance().GetAudioCtrl().AddSameTeamSpatializer(SpeOpenid_InputFiled.text);
               Debug.Log("SpeOpenid_InputFiled: " + SpeOpenid_InputFiled.text.ToString() + "ret=" + ret);
               ShowWarnning(string.Format("AddSameTeamSpatializer ret:{0}", ret));
           }
           else
           {
               int ret = ITMGContext.GetInstance().GetAudioCtrl().RemoveSameTeamSpatializer(SpeOpenid_InputFiled.text);
               Debug.Log("SpeOpenid_InputFiled: " + SpeOpenid_InputFiled.text + "ret=" + ret);
               ShowWarnning(string.Format("RemoveSameTeamSpatializer ret:{0}", ret));
           }
       });
```
