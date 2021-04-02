为方便 GME 开发者调试和接入腾讯云游戏多媒体引擎产品 API，这里向您介绍适用于后台录制开发的接入技术文档。

## 原理介绍


![](https://main.qcloudimg.com/raw/5765c30439c126c0c0e3a14f4353785b.png)

## 白名单开通

使用后台录制功能需要开通白名单，请咨询相关开发人员或提工单咨询。


## 控制台开通服务
使用录制功能，需要在控制台开通以下服务：

### 1. 开通GME推流服务服务
开通白名单后，[GME控制台](https://console.cloud.tencent.com/gamegme)上的 Appid 设置中可以看到有后台录制的选项。

![](https://main.qcloudimg.com/raw/0082b5eeb2eb66491c86ee6eb4c8203f.png)

- 推流状态：开启后客户端会推流到后台
- 录制状态：开启之后，后台会将房间内声音录制成音频。
- 录制文件：点击可以查看录制文件。


> ?如果开启了推流和录制，将对该 Appid 下所有使用实时语音服务的用户生效。

### 2. 开通云直播服务
需要在[云直播控制台](https://console.cloud.tencent.com/live/config/record)中开启服务（需要申请白名单才能访问此界面）。


> ?点击【域名管理】可以看到自己的域名，如果域名如红框处，开头的数字未五位，可以忽略开通云直播服务这个步骤。
![](https://main.qcloudimg.com/raw/74ac7bf9561bca158a10f1f307fe94aa.png)



点击【创建录制模版】，创建一个录制模版，推荐配置如下图：

![](https://main.qcloudimg.com/raw/faf16efe9166ab37d3cdd19059d1b383.png)

创建完成后，需要绑定域名，域名是自动生成的，无需额外配置，点击绑定即可。


### 3. 对接云点播服务进行查询
对接[云点播](https://console.cloud.tencent.com/vod/media)，可以使用腾讯云API 进行对接，进行如下所以的查询，支持文件名称 && 文件前缀 && 时间段查询。

例子：如果想查找aabb房间内的openid 12222 用户的录音，直接搜索 12222 并通过时间段再进行筛选。

![](https://main.qcloudimg.com/raw/07ab9a058968991de5899aeead7ab4d5.png)



## 云直播控制台操作步骤

**客户对接云点播API 进行录制文件查询操作:**
列如搜索文件名前缀为 129509_1400485926 其中1400485926 为appid 、创建时间在 2021-03-25T07:25:52Z 到 2021-03-26T07:25:52Z一天之内的文件，并按创建时间进行降序排序。

![](https://main.qcloudimg.com/raw/f351e40c49fd3f4e0111e7265367b6d3.png)

这个里面涉及鉴权签名，目前使用腾讯云API 鉴权，参考：[签名方法](https://cloud.tencent.com/document/product/267/20460)。


## GME Sign算法
其中sign 签名过过程如下：

![](https://main.qcloudimg.com/raw/7f1027c1576ff525c61dccc8a3e054f0.png)

其中 key在目前业务是：
**key = ${upstreamKey} + ${bzid} + ${appid} + ${roomid} + ${openid}**
其中的upstreamKey 是和云直播创建推流url时 带回的上传秘钥


## 云直播存储时长
用户侧可以定时任务去遍历录制文件列表，通过云点播修改文件属性API，更改文件过期时间。

修改媒体文件属性API：[修改媒体文件属性](https://cloud.tencent.com/document/product/266/31762)。

更改参数：ExpireTime
