## 简介
欢迎使用腾讯云游戏多媒体引擎 SDK 。为方便 Xbox One 开发者调试和接入腾讯云游戏多媒体引擎产品 API，这里向您介绍适用于 Xbox One 开发的工程配置。

## SDK 静态库导入

### 1. 引入头文件
将 “include” 文件夹复制到项目文件夹。

### 2. 引入 SDK 相关文件
将“gmesdk.lib”复制到项目文件夹。
将“gmesdk.dll”复制到exe输出目录。

### 3. 加入工程中
在项目的属性页面上添加“gmesdk.lib”文件：Linker -> Input -> Additional Dependencies。
在项目的属性页面上添加“gmesdk.lib”目录：Linker  - > Generral  - > Additional Library Directories。

### 4. 增加权限
在 Package.appxmanifest 文件中添加能力项以允许GME访问网络和使用麦克风采集设备。
```
  <Capabilities>
    <Capability Name="internetClientServer" />
    <mx:Capability Name = "kinectAudio" />
    <mx:Capability Name = "kinectGamechat" />
  </Capabilities>
```