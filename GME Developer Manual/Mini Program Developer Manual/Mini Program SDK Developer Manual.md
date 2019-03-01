为方便小程序开发者调试和接入腾讯云游戏多媒体引擎产品 API，这里向您介绍适用于小程序开发的快速接入文档。


## 开启小程序功能

在腾讯云后台中申请 GME 相关服务。



## rtmp流地址生成
rtmp 流支持两种模式，一是拉取房间内某个说话成员的 rtmp 流，二是拉取房间内所有说话成员合成的 rtmp 流。模式一只能听到指定成员的说话声音，模式二可以听到所有成员的声音。

rtmp流地址生成需要以下几个信息：

|参数|含义|获取方式|
|-----|-----|-----|
|AppID|GME 控制台所申请到的 AppID |腾讯云控制台|
|RoomID|此用户需要收听的房间号 ID |开发者在应用中生成|
|UserID|此用户需要收听的另一用户的 ID |开发者在应用中生成|
|BizID|与 AppID 对应个一个标识码 |获取方式见下文步骤1|
|StreamKey|与 AppID 对应个一个密钥 |获取方式见下文步骤2|
|StreamID|rtmp 流的一个唯一标识|获取方式见下文步骤1|
|Timestamp|时间戳，单位为秒 |开发者生成并填入|


rtmp流地址生成有以下几个步骤：
### 1、获取 BizID 和 StreamKey

（空）


### 2、生成 StreamID

模式一：
```
{AppID}_{UserID}_{RoomID}_main
```
模式二：
```
mixer_{AppID}_{RoomID}
```

### 3、生成最终地址
```
rtmp://{BizID}.liveplay.myqcloud.com/live/MD5({StreamID})?txSecret= MD5({StreamKey}{streamidMd5}{timestamp})&txTime={timestamp}
```

> 其中 MD5()；为计算MD5的函数

## 示例代码

示例所需要的信息为：

```
Bizid:38251
streamKey:4531cddc5bd6dc28a812bb87121a5853
AppID:1400089356
RoomID:20180301
Userid:123456
timestamp:1551438840
```

rtmp地址如下：

```
模式一：
rtmp://38251.liveplay.myqcloud.com/live/38251_5a1d7c2f7e8fcb56942691035b49d960?txSecret=09fcc68fc320336c2b85071b11056bd6&txTime=5c7913f8

模式二：
rtmp://38251.liveplay.myqcloud.com/live/38251_fed2b77fb622dcbf978ed6041d9f52d9?txSecret=2d69cfbd346da4531d82624a0bed9368&txTime=5c7913f8
```

