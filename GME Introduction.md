## 简介

欢迎使用[腾讯云游戏多媒体引擎 SDK](https://cloud.tencent.com/product/tmg?idx=1) 。为方便开发者接入腾讯云游戏多媒体引擎产品，这里向您介绍适用于游戏多媒体引擎 SDK 的接入指引。

使用GME 有以下五个步骤：

[1、在腾讯云后台新建 GME 服务](./GME%20Introduction.md#%E6%96%B0%E5%BB%BA%E6%9C%8D%E5%8A%A1)

[2、下载对应版本的客户端SDK](./GME%20Introduction.md#%E4%B8%8B%E8%BD%BD-sdk)

[3、参照接入API文档， 将SDK移植到工程](./GME%20Introduction.md#%E7%9B%B8%E5%85%B3-sdk-%E6%8A%80%E6%9C%AF%E6%96%87%E6%A1%A3)

[4、查看日常运营后台统计](./ME%20Introduction.md#%E6%8E%A7%E5%88%B6%E5%8F%B0%E7%94%A8%E9%87%8F%E7%BB%9F%E8%AE%A1%E4%BD%BF%E7%94%A8)

[5、接入过程中特殊问题自主排除与反馈](./GME%20Introduction.md#%E7%89%B9%E6%AE%8A%E9%97%AE%E9%A2%98%E5%A4%84%E7%90%86)




## 新建服务

### 1.登录成功后，点击【新建应用】


![image](Image/j1.png)

### 2.填入相应的信息。  
填写该页面所需信息，按照需要选择所需的服务。 
> 计费方式不同收费也不同，设置完成后不可再修改，收费请参考 [产品价格](https://cloud.tencent.com/product/tmg?idx=1#price) 及咨询相关腾讯云商务工作人员。
> 如果是游戏类应用，需选择相应的平台引擎。根据技术人员提供的方案选择相应的采样率。
> 语音消息及转文本服务设置完成可再修改。

![image](Image/j2.png)

### 3.创建应用成功后，应用管理列表就有刚刚创建的应用。
- 列表中的 AppID 在接入 SDK 进行开发过程中会作为参数使用。

![image](Image/j3.png)

### 4.应用管理列表中，在相应的应用那一行，点击【设置】按钮，进入应用设置。

![image](Image/j4.png)

应用信息模块，单击【修改】后可对相应信息进行修改。

### 5.鉴权信息模块，可获取应用相应的鉴权。
![image](Image/j5.png)

- 此模块中的权限密钥会作为参数使用到 SDK 接入过程中。 
- 只有创建游戏的账号、主账号、全局协作者可以操作【重置秘钥】。
- 页面修改密钥后，15 分钟 ~ 1 小时内生效，不建议频繁更换。


详细参见[GME密钥文档](./GME%20Developer%20Manual/GME%20Key%20Manual.md)。



![image](Image/j8.png)


### 6.业务及服务的开启关闭
在这里可以对业务及服务进行开启或者关闭。
![image](Image/j6.png)

![image](Image/j7.png)

## 下载 SDK 
### 1.下载地址
请在[下载指引](https://cloud.tencent.com/document/product/607/18521)下载相关 Demo 及 SDK。

### 2.接入准备
接入 SDK 需要使用腾讯云提供的 appid 及相关权限密钥。即应用管理列表中的 AppID 及 应用设置中的鉴权信息模块。
- 接入时会使用鉴权信息模块中的权限密钥。

详细参见[GME密钥文档](./GME%20Developer%20Manual/GME%20Key%20Manual.md)。


更多平台相关配置请参考各平台工程配置文档。

### 3.官方 Demo 使用需知
Demo 中带有腾讯云测试账号，可进行功能体验，如需更换个人及公司测试账号，需要在 Demo 中在相应界面将腾讯云测试账号 AppID 更换为开发者在控制台获取的 AppID，并需要在 AVChatViewController-GetAuthBuffer 函数中修改实时语音的权限密钥，权限密钥在[控制台](https://console.cloud.tencent.com/gamegme)获取。



## 相关 SDK 技术文档
#### Unity 引擎
- ##### *支持平台:Android, iOS, Windows, MAC*
- [Unity 工程配置文档](./GME%20Developer%20Manual/Unity%20Developer%20Manual/Unity%20SDK%20Project%20Configuration.md)
- [Unity 快速接入文档](./GME%20Developer%20Manual/Unity%20Developer%20Manual/Unity%20SDK%20Developer%20Quick%20Start.md)
- [Unity 开发接入技术文档](./GME%20Developer%20Manual/Unity%20Developer%20Manual/Unity%20SDK%20Developer%20Manual.md)

#### Unreal Engine 引擎
- ##### *支持平台:Android, iOS, Windows, MAC*
- [Unreal Engine 工程配置文档](./GME%20Developer%20Manual/Unreal%20Engine%20Developer%20Manual/Unreal%20Engine%20SDK%20Project%20Configuration.md)
- [Unreal Engine 快速接入文档](./GME%20Developer%20Manual/Unreal%20Engine%20Developer%20Manual/Unreal%20SDK%20Developer%20Quick%20Start.md)
- [Unreal Engine 开发接入技术文档](./GME%20Developer%20Manual/Unreal%20Engine%20Developer%20Manual/Unreal%20Engine%20SDK%20Developer%20Manual.md)

#### Cocos2D 引擎
- ##### *支持平台:Android, iOS*
- [Cocos2D-X 工程配置文档](./GME%20Developer%20Manual/Cocos2D-X%20Developer%20Manual/Cocos2d%20SDK%20Project%20Configuration.md)
- [Cocos2D-X 快速接入文档](./GME%20Developer%20Manual/Cocos2D-X%20Developer%20Manual/Cocos2d%20SDK%20Developer%20Quick%20Start.md)
- [Cocos2D-X 开发接入技术文档](./GME%20Developer%20Manual/Cocos2D-X%20Developer%20Manual/Cocos2d%20SDK%20Developer%20Manual.md)

#### 原生应用
- [Windows 工程配置文档](./GME%20Developer%20Manual/Windows%20Developer%20Manual/Windows%20SDK%20Developer%20Quick%20Start.md)

- [Windows 快速接入文档](./GME%20Developer%20Manual/Windows%20Developer%20Manual/Windows%20SDK%20Developer%20Quick%20Start.md)

- [Windows 开发接入技术文档](./GME%20Developer%20Manual/Windows%20Developer%20Manual/Windows%20SDK%20Developer%20Manual.md)

- [Mac 工程配置文档](./GME%20Developer%20Manual/Mac%20Developer%20Manual/Mac%20SDK%20Project%20Configuration.md)

- [Mac 快速接入文档](./GME%20Developer%20Manual/Mac%20Developer%20Manual/Mac%20SDK%20Developer%20Quick%20Start.md)

- [Mac 开发接入技术文档](./GME%20Developer%20Manual/Mac%20Developer%20Manual/Mac%20SDK%20Developer%20Manual.md)

- [iOS 工程配置文档](./GME%20Developer%20Manual/iOS%20Developer%20Manual/iOS%20SDK%20Project%20Configuration.md)

- [iOS 快速接入文档](./GME%20Developer%20Manual/iOS%20Developer%20Manual/iOS%20SDK%20Developer%20Quick%20Start.md)

- [iOS 开发接入技术文档](./GME%20Developer%20Manual/iOS%20Developer%20Manual/iOS%20SDK%20Developer%20Manual.md)

- [Android 工程配置文档](./GME%20Developer%20Manual/Android%20Developer%20Manual/Android%20SDK%20Project%20Configuration.md)

- [Android 快速接入文档](./GME%20Developer%20Manual/Android%20Developer%20Manual/Android%20SDK%20Developer%20Quick%20Start.md)

- [Android 开发接入技术文档](./GME%20Developer%20Manual/Android%20Developer%20Manual/Android%20SDK%20Developer%20Manual.md)



## 控制台用量统计使用

### 在应用管理界面打开用量统计

在所需查询的应用中单击【用量统计】。

![image](Image/j3.png)


### 实时语音用量统计


用量统计展示实时语音应用 DAU 数据。
- 【语音 DAU 总数】展示该应用昨日的语音 DAU 总数
- 【大陆语音 DAU 数】展示该应用大陆语音 DAU 数
- 【海外语音 DAU 数】展示该应用海外语音 DAU 数
- 默认显示近 7 天的总量 DAU 数据，支持切换时间、切换地域查看用量数据

![image](Image/j11.png)


### 语音消息及转文本
用量统计展示离线语音应用 DAU 数据。
- 【语音 DAU】展示该应用昨日的语音 DAU 总数
- 【转文本次数】展示该应用昨日的转文本次数
- 默认显示近 7 天的总量 DAU 数据，支持切换时间查看用量数据

![image](Image/j12.png)
## 特殊问题处理


[腾讯云游戏多媒体引擎FAQ文档](./GME%20Developer%20Manual/GME%20FAQ%20Manual.md)    

[错误码文档](./GME%20Developer%20Manual/GME%20Error%20Code.md)

