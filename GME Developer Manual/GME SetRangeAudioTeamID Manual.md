# 修改范围语音队伍ID

为方便 GME 开发者调试和接入腾讯云游戏多媒体引擎产品 API，本文为您介绍适用于 GME 修改范围语音队伍ID的使用参考文档。

## 修改范围语音队伍ID
此接口与进房前设置的 SetRangeAudioTeamID 相同，在进房前调用，则进入房间已经在队伍中，在进房后调用，则是改变队伍号。如果不在任何队伍里，则直接进去队伍语音中。

### 接口原型

```

//Unity接口
public abstract int SetRangeAudioTeamID(int teamID);
```

### 参数说明

|参数   |类型   |含义   |
|----------|-------|-------|
|teamID       |int   |需要进入的队伍号，不能为 0 |

### 回调
接口返回值为 QAV_OK 则表示成功。

更多错误码请参考【https:/cloud.tencent.com/document/product/607/15173】。

### 示例代码

**Json字段**

error_info 为错误信息，result 为回调结果，teamid 为进去的队伍号。

```
public class ChangeTeamIdClass
{
    public string error_info;
    public int result;
    public int teamid;
}

```

**回调**
```
void OnEvent(int eventType,int subEventType,string data)
	{
		Debug.Log (data);
		switch (eventType) {
			case (int)ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVENT_TYPE_CHANGETEAMID:
                teamidjs = JsonUtility.FromJson<ChangeTeamIdClass>(data);
                Debug.Log("result" + teamidjs.result);
                Debug.Log("error_info" + teamidjs.error_info);
                Debug.Log("teamid" + teamidjs.teamid);
                ShowWarnning(string.Format("change teamid to:{0},result:{1},error:{2}", teamidjs.teamid.ToString(), teamidjs.result, teamidjs.error_info));
                break;
				}
	}
```

