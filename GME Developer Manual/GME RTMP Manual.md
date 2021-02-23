为方便 GME 开发者调试和接入腾讯云游戏多媒体引擎产品 API，这里向您介绍适用于推流直播的接入技术文档。

## 白名单开通

使用后台录制功能需要开通白名单，请咨询相关开发人员或提工单咨询。


## 控制台开通服务

开通白名单后，控制台上的 Appid 设置中可以看到有后台录制的选项。

![](https://main.qcloudimg.com/raw/0082b5eeb2eb66491c86ee6eb4c8203f.png)

推流状态：开启后客户端会推流到后台


> 注意：
> 如果开启了推流，将对该 Appid 下所有使用实时语音服务的用户生效。
> 只需要推流直播的话，不需要开启录制。



## 申请云直播服务
>!申请云直播服务之前需要进行实名验证。在【账号信息】中申请实名认证。

1. 登录腾讯云控制台，选择【云直播】服务，进入 [云直播服务申请界面](https://console.cloud.tencent.com/live)。
2. 勾选【同意《腾讯云服务协议》】，并单击【申请开通】进行云直播服务开通。
3. 单击【确认】，云直播服务已申请成功。如下图所示：
![](https://main.qcloudimg.com/raw/53d626f2dea1eaecf459636db1481e4b.png)
4. 服务申请成功后，单击左侧菜单栏【域名管理】，进入域名管理界面。
5. 云直播服务会自动生成一个推流域名，单击【添加域名】，申请播放域名。
请添加自有已备案域名进行直播推流和播放。域名管理使用方法参见 [域名管理](https://cloud.tencent.com/document/product/267/30559) 和 [CNAME配置](https://cloud.tencent.com/document/product/267/30010)。 
![](https://main.qcloudimg.com/raw/d24740621f990a6101ee031de1a78cc4.png)
申请播放地址成功后，界面会有两个域名，一个是推流域名，一个是播放域名。拉流需要使用到播放域名。
![](https://main.qcloudimg.com/raw/8521494652b287a1b5769882e3f52b24.png)
6. 点击播放域名的【管理】，进入【访问控制】，关闭【播放鉴权】。

> 注意
> 需要申请自定义播放域名。


## rtmp 流地址生成
拉取房间内所有说话成员合成的 rtmp 流。

rtmp 流地址生成需要以下几个信息：

|参数|含义|获取方式|
|-----|-----|-----|
|自定义域名|云直播服务控制台自定义域名 |此参数获取方式见下文 [步骤1](#step1)。|
|BizID|云直播服务控制台BizID |此参数获取方式见下文 [步骤1](#step1)。|
|AppID|GME 控制台所申请到的 AppID。 |腾讯云控制台，请参考  [SDK 接入指引](https://cloud.tencent.com/document/product/607/10782)。|
|RoomID|此用户需要收听的房间号 ID。 |此参数由开发者在应用中生成。|
|StreamKey|云直播服务控制台生成的密钥|此参数获取方式见下文 [步骤1](#step1)。|


rtmp 流地址生成有以下几个步骤：
<span id="step1"></span>
### 步骤1 获取参数
1. 进入域名管理界面，在播放域名一栏，单击【管理】，进入播放域名管理界面。
 ![](https://main.qcloudimg.com/raw/8521494652b287a1b5769882e3f52b24.png)
2. 【推流域名】一栏中，在 “.liveplay.myqcloud.com” 后缀前面的播放域名为 BizID 参数。例如下图中的 BizID 即为 38251。 API Key 为 StreamKey 参数。 
 ![](https://main.qcloudimg.com/raw/6a0a8c119acd2d49b575a40aa7ededcc.png)
3. 以上图为例，自定义域名为【播放域名】一栏中的gmepull.qcloudgme.com。


### 步骤2 生成 StreamID

```
mixer_{AppID}_{RoomID}
```


### 步骤3 生成 StreamIDMD5 

> 其中 MD5()；为计算 MD5 的函数（32位小写）。

```
MD5({StreamID});
```

### 步骤4 生成最终地址
```
rtmp://{自定义域名}/live/{Bizid}_{AppID}_{RoomID}_{StreamIDMD5}
```


### 示例
示例所需要的信息为：

```
自定义域名:gmepull.qcloudgme.com
Bizid:38251
AppID:1400089356
RoomID:20190301
```

rtmp 地址如下：

```
StreamID 为 mixer_1400089356_20190301
MD5({StreamID}) 为 fed2b77fb622dcbf978ed6041d9f52d9
rtmp://gmepull.qcloudgme.com/live/38251_1400089356_20190301_fed2b77fb622dcbf978ed6041d9f52d9
```