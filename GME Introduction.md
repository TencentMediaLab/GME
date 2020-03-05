欢迎使用 [腾讯云游戏多媒体引擎 SDK](https://cloud.tencent.com/product/tmg?idx=1) 。为方便开发者接入腾讯云游戏多媒体引擎产品，这里向您介绍适用于游戏多媒体引擎 SDK 的接入指引。


## 新建服务
### 新建应用
1. 登录 [游戏多媒体引擎控制台](https://console.cloud.tencent.com/gamegme)，单击左侧菜单【服务管理】。
2. 进入服务管理页面，单击【新建应用】。


### 填写信息  
填写该页面所需信息，按照需要选择所需的服务。 
- 计费详情请参考 [产品价格](https://cloud.tencent.com/document/product/607/17808) 或者咨询相关腾讯云商务工作人员。设置完成可再修改。
- 如果是游戏类应用，需选择相应的平台引擎。【平台/引擎】和【服务区域】中的勾选选项仅作参考，不影响服务效果。
- 语音消息及转文本服务设置完成可再修改。

![](https://main.qcloudimg.com/raw/7e861560d6e0132c1cdafb0c3d63bfdb.png)

## 设置服务
### 创建应用成功后，应用管理列表就有刚刚创建的应用。
列表中的 AppID 在接入 SDK 进行开发过程中会作为参数使用。

![](https://main.qcloudimg.com/raw/973b60c364a7ee43537db2dbdad37494.png)


### 设置应用
单击【设置】，进入应用信息模块，单击【修改】后可对相应信息进行修改。

![](https://main.qcloudimg.com/raw/e1cf88f30e5c710c1b275928f709e634.png)

#### 鉴权信息

- 此模块中的权限密钥会作为参数使用到 SDK 接入过程中。 
- 页面修改密钥后，15分钟 - 1小时内生效，不建议频繁更换。
- 只有创建游戏的账号、主账号、全局协作者可以操作【重置密钥】。
- 鉴权详细使用请参考 [鉴权密钥](https://main.qcloudimg.com/raw/973b60c364a7ee43537db2dbdad37494.png)



### 开启关闭业务
在这里可以对业务及服务进行开启或者关闭。

![](https://main.qcloudimg.com/raw/6880323dec5c8d9604bb78853843aac2.png)


## 下载 SDK 
### 1.下载地址
请在 [SDK 下载指引](https://cloud.tencent.com/document/product/607/18521) 下载相关 Demo 及 SDK。

### 2.接入准备
接入 SDK 需要使用腾讯云提供的 AppID 及相关权限密钥。即应用管理列表中的 AppID 及 应用设置中的鉴权信息模块。

更多平台相关配置请参考各平台工程配置文档。

### 3.官方 Demo 使用需知
Demo 中带有腾讯云测试账号，可进行功能体验，如需更换个人及公司测试账号，需要在 Demo 中在相应界面将腾讯云测试账号 AppID 更换为开发者在控制台获取的 AppID，并需要在 AVChatViewController-GetAuthBuffer 函数中修改实时语音的权限密钥。

## 控制台用量统计
如有疑问请参考 [运营指引](https://cloud.tencent.com/document/product/607/17448)。


## 特殊问题处理
如有疑问请参考 [常见问题](https://cloud.tencent.com/document/product/607/17359) 。
如有疑问请参考 [错误码](https://cloud.tencent.com/document/product/607/15173)。



