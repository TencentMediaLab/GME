## 操作场景
为方便 Unity 开发者调试和接入腾讯云游戏多媒体引擎产品 API，这里向您介绍适用于 Unity 主机开发的工程配置。


## 下载 SDK
1. 请先下载相关 Demo 及 SDK。
2. 下载完的 SDK 资源解压后有以下几个部分。文件说明如下表：

|文件名       | 说明           |作用|
| ------------- |:-------------:|------|
| Plugins   	|SDK 库文件|存放导出各个平台的库文件|
| GMESDK     	|SDK 代码文件|提供 API 接口|

## 工程配置步骤
#### 导入 Plugins 文件
将开发工具包中 Plugins 文件夹中的文件复制在 Unity 工程中 Assets 下的 Plugins 文件夹中，如图所示：  
![](https://main.qcloudimg.com/raw/1221a25f62cedd3831cf2bb27bb1ea45.png)

> 如果不需要导出 win32 架构的可执行文件，请删除 Plugins 文件夹下的 x86 文件夹。


#### 导入代码文件
将开发工具包中 Scripts 文件夹中的文件复制在 Unity 工程中存放代码的文件夹中，如图所示：  
![](https://main.qcloudimg.com/raw/8904a83c6173fa7c5b04ddb0e48138ca.png)
