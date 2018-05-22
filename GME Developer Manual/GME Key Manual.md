## 简介

欢迎使用腾讯云游戏多媒体引擎 SDK 。为方便开发者调试和接入腾讯云游戏多媒体引擎产品，这里向您介绍适用于所有平台的密钥相关技术文档。

## 实时语音密钥
腾讯云游戏多媒体引擎提供音视频密钥，用于相关功能的加密和鉴权。鉴权所用到的签名产生过程涉及到明文、密钥和算法。

明文为以下字段的网络序拼接：

|字段描述    		| 类型/长度			| 值定义/备注|
| ---------------- |-------------------|--------------|
| cVer				|unsigned char/1	|版本号，填0|
| wAccountLen		|unsigned short/2	|第三方自己的帐号长度	|
| buffAccount		|wAccountLen		|第三方自己的帐号字符	|
| dwSdkAppid		|unsigned short/2	|sdkappid				|
| dwAuthid			|unsigned int/4		|群组号码				|
| dwExpTime		|unsigned int/4		|过期时间（当前时间+有效期[单位：秒，建议300秒]）|
| dwPriviegeMap	|unsigned int/4		|权限位					|
| dwAccountType	|unsigned int/4		|第三方帐号类型			|

### 1.密钥
腾讯云 GME 控制台获取

![image](Image/j10.png)

### 2.算法
TEA对称加密算法。
总体建议，建议接入初期使用客户端部署方案，后面可优化为部署在游戏 App 后台。

|方案       		| 坏处        				| 详情 																															|
| ------------- |:-------------:| ------------- 
| 后台部署    		|工作量大				|相关详情请跳转下文：实时语音密钥详细说明												|
| 客户端部署      	| 安装包体积大，安全性低	|工程中额外引入libqav_authbuff.so（Android） 和 QAVSDKAuthBuffer.framework（iOS）两个库文件，并额外引入 QAVAuthBuffer.cs。 	|  

使用详情见各平台文档。

## 离线语音密钥
概述：离线语音的上传下载依赖腾讯云的COS平台，需要单独的鉴权。鉴权所用到的签名产生过程涉及到明文、密钥和算法。
### 1.明文
明文为 appid，accountType 和 openid。

### 2.密钥的获取
请在应用设置界面的鉴权信息模块，点击【下载公私钥】可以下载此应用相应的公私钥。

![image](Image/j10.png)

解压下载的 zip 文件后可以看到两个文件，如下：

|文件名       |作用    |
| :-----------: | ------------- |
|public_key |公钥|
|private_key |私钥|

根据需要用记事本打开相应的文件，复制里面的密钥，作为参数填入相应函数中。
>注意：每次下载获取的公私钥对，必须等待1个小时才能正常使用。

### 3.算法的部署
总体建议，建议接入初期使用客户端部署方案，后面可优化为部署在 App 后台。

|方案       | 坏处        | 详情 |
| ------------- |:-------------:| ------------- 
| 后台部署   		|工作量大				|[TLS后台API使用手册](https://cloud.tencent.com/document/product/269/1510#1-.E6.A6.82.E8.BF.B0)					|
| 客户端部署      	| 安装包体积，安全性 		|工程中额外引入 libqav_tlssig.so（Android） 和 QAVSDKTlsSig.framework（iOS）两个库文件，并额外引入 QAVSig.cs。 	|  

使用详情见各平台文档。


## 实时语音密钥详细说明
游戏多媒体引擎提供音视频密钥，用于相关功能的鉴权和加密。目前主要用于实时语音进房鉴权和上下行权限的加密。
- 密钥：APPID 对应音视频密钥的 md5 值，长度 16 字节
- 加密算法：TEA 加密
- 加密库及例子：附件 [tea.zip](http://qzonestyle.gtimg.cn/qzone/vas/opensns/res/doc/tea.zip)

> 控制台修改密钥后，15 分钟 ~ 1 小时内生效，不建议频繁更换。

### 进房权限加密
- 密文内容
<table class="t">
<tbody><tr>
<th>  字段描述
</th><th>  类型/长度
</th><th>  值定义/备注
</th></tr>
<tr>
<td> cVer
</td><td> unsigned char/1
</td><td> 版本号，填0
</td></tr>
<tr>
<td>  wAccountLen
</td><td>  unsigned short /2
</td><td>  第三方自己的帐号长度
</td></tr>
<tr>
<td>  buffAccount
</td><td>  wAccountLen
</td><td>  第三方自己的帐号字符
</td></td>
<tr>
<td>  dwSdkAppid
</td><td>  unsigned int/4
</td><td>  sdkappid
</td></tr>
<tr>
<td>  dwAuthId
</td><td>  unsigned int/4
</td><td>  群组号码，即：roomId
</td></tr>
<tr>
<td>  dwExpTime
</td><td>  unsigned int/4
</td><td>  过期时间  
（当前时间 + 有效期（单位：秒，建议300秒））
</td></tr>
<tr>
<td>  dwPrivilegeMap
</td><td>  unsigned int/4
</td><td>  权限位，建议：<br>
纯音频场景：<br>
需要上麦建议设置为：AUTH_BITS_CREATE_ROOM|AUTH_BITS_JOIN_ROOM|
AUTH_BITS_SEND_AUDIO|AUTH_BITS_RECV_AUDIO<br>
不需要上麦建议设置为：AUTH_BITS_CREATE_ROOM|AUTH_BITS_JOIN_ROOM|
AUTH_BITS_RECV_AUDIO<br>
视频场景：<br>
需要上麦建议设置为：AUTH_BITS_CREATE_ROOM|AUTH_BITS_JOIN_ROOM|
AUTH_BITS_SEND_AUDIO|AUTH_BITS_RECV_AUDIO|
AUTH_BITS_SEND_CAMERA_VIDEO|AUTH_BITS_RECV_CAMERA_VIDEO|
AUTH_BITS_SEND_SCREEN_VIDEO|AUTH_BITS_RECV_SCREEN_VIDEO<br>
不需要上麦建议设置为：AUTH_BITS_CREATE_ROOM|AUTH_BITS_JOIN_ROOM|
AUTH_BITS_RECV_AUDIO|AUTH_BITS_RECV_CAMERA_VIDEO|
AUTH_BITS_RECV_SCREEN_VIDEO

<a href="https://cloud.tencent.com/document/product/268/3227 ">更详细说明请查看这里</a>
</td></tr>
<tr>
<td>  dwAccountType
</td><td>  unsigned int/4
</td><td>  第三方帐号类型，<a href="https://console.cloud.tencent.com/ilvb ">在这里可以找到</a>
</td></tr></tbody></table>

#### 加密方法	
- 密文中的数字转换成网络字节序（大端字节序）；
- 把密文拼接成一段字符串；
- 用 tea 加密对字符串加密，symmetry_encrypt 函数输出的字符串即为权限加密串(注意:不用要把二进制串转成16进制)。

#### 使用方法
后台生成加密串后，下发给客户端，客户端可以用于如下两个场景：
- 调用 API enterRoom 进行进房操作时，将加密串传给进房参数的 authBuffer 字段。
- 调用 API ChangeRole 进行修改角色操作时，将加密串传给 authBuffer 或 buff 参数。