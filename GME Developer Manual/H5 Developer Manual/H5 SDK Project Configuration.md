## 简介
欢迎使用腾讯云游戏多媒体引擎 SDK 。为方便 H5 开发者调试和接入腾讯云游戏多媒体引擎产品 API，这里向您介绍适用于 H5 开发的预备工作。

## GME H5 SDK 支持的平台
| 操作系统平台  | 浏览器/webview  | 版本要求  |  备注|
| ------------------------- | -------- | ---------------------- |------- |
| iOS          | Safari ( 只支持Safari ) | 11.1.2 | 由于苹果 Safari 仍有偶现的 bug，产品化方案建议先规避，待苹果解决后再使用，对于iOS可以考虑使用我们的[小程序解决方案](之后添加) |
| Android      | TBS （微信和手机QQ的默认Webview） | 43600                | 微信和手机QQ默认内置的浏览器内核为TBS。 [TBS 介绍](http://x5.tencent.com/) |
| Android      | Chrome | 60+               | 需要支持 H264  |
| Mac          | Chrome | 47+                |      |
| Mac          | Safari | 11+                |      |
| Windows(PC)  | Chrome | 52+                |      |
| Windows(PC)  | [QQ浏览器](https://browser.qq.com/) | 10.2 | &nbsp;     |


## SDK 准备
可以通过以下方式获取 SDK。

### 1. 请在[下载指引](https://cloud.tencent.com/document/product/607/18521)下载相关 Demo 及 SDK。


### 2. 在界面中找到找到 H5 版本的 SDK 资源。


### 3. 点击【下载】按钮。


## 预备工作

### 1. 端口号开放

如果所在网络有防火墙，请确定有开放以下端口：

| 协议 | 端口号            |
| ---- | ----------------- |
| TCP  | 8687              |
| UDP  | 8000 、 8800 、 443 |

使用 CDN 引入SDK。
### 2. 在页面中引入 WebRTCAPI.min.js

```html
<script src="https://sqimg.qq.com/expert_qq/webrtc/3.0/WebRTCAPI.min.js"></script>
```

### 3. 进行 H5 鉴权设置


## 鉴权设置

### 签名步骤
使用 GME 游戏多媒体引擎，需要对鉴权信息进行签名，以下是鉴权的步骤：

### 下载程序
点击此链接可以下载我们准备的 signdemo 程序，该程序可以完成对指定的 sdkappid 的鉴权信息签名。

### 相应修改
进入到 signdemo 目录，修改 config.js 文件：打开 config.js 文件，先删除默认的配置，在删除代码的地方调用 appidMap 函数，参数为在腾讯云后台申请的 SDKAppid 以及对应的鉴权 key。

```
const AuthBufferConfig = function () {
    this.appidMap = {};
    this.appidMap["1400089356"] = "1cfbfd2a1a03a53e";
};
//将1400089356替换为在腾讯云后台申请的 sdkAppid，1cfbfd2a1a03a53e 替换为对应 sdkAppid 的鉴权 key
```

> 注意：AuthKey 必须与你的sdkAppid相对应

### 安装npm包并运行
进入到 signdemo 目录，执行以下语句以安装相关依赖：
```
npm i
```
然后执行脚本 node index.js，运行签名服务。

> 注意：由于使用到 async 语法，请确保你的node版本在8以上。命令行中执行 node -v 以查看版本。


### 测试
可在命令行用以下命令测试（确保系统中有curl指令）：
```
//生成userSig:
curl "http://127.0.0.1:10005/" --data "sdkappid=1400089356&roomid=1234123&openid=1234567
（验证同样可以访问网站，但同样需要支持的SDK App ID；默认支持1400089356）
```

返回参考：

```
{"userSig":"AqhHE7QHLFYPfV/zfyrdRYHfuUn6eOA8g/J6GMjVy//Shr5ByJPTi8hzR2KyXMvn","errorCode":0}
```
