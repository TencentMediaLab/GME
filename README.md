## GME
[游戏多媒体引擎](https://cloud.tencent.com/product/tmg?idx=1)（Game Multimedia Engine）是一个专门针对游戏场景定制的实时游戏音视频 SDK，覆盖了休闲社交类、MOBA 类、MMORPG 等多种游戏类型，提供了包括多人实时语音、实时视频、语音消息、语音转文本等功能。功能完备，接入门槛低，一个 SDK 即可满足多样化的游戏音视频诉求。

## 产品架构
![image](Image/d.png)




## 接入通用文档

#### [接入指引文档](./GME%20Introduction.md)
#### [控制台用量统计文档](./GME%20Introduction.md#%E6%8E%A7%E5%88%B6%E5%8F%B0%E7%94%A8%E9%87%8F%E7%BB%9F%E8%AE%A1%E4%BD%BF%E7%94%A8)
#### [密钥技术文档](./GME%20Developer%20Manual/GME%20Key%20Manual.md)
#### [范围文档](./GME%20Developer%20Manual/GME%20TeamAudio%20Manual.md)
#### [3D音效文档](./GME%20Developer%20Manual/GME%20Spatializer%20Manual.md)
#### [错误码文档](./GME%20Developer%20Manual/GME%20Error%20Code.md)

## 各平台接入文档

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
- [Windows 工程配置文档](./GME%20Developer%20Manual/Windows%20Developer%20Manual/Windows%20SDK%20Project%20Configuration.md)

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

#### H5 接入
- [H5 工程配置文档](./GME%20Developer%20Manual/H5%20Developer%20Manual/H5%20SDK%20Project%20Configuration.md)

- [H5 开发接入文档](./GME%20Developer%20Manual/H5%20Developer%20Manual/H5%20SDK%20Developer%20Manual.md)


## SDK更新说明
###  GME_SDK2.3 2019-01-11
#### 新增功能
1、支持实时语音过程中使用离线语音；

2、支持实时语音过滤，可识别暴恐、涉黄、涉政等信息；

3、支持 H5 实时语音，实现全平台实时语音互通；

#### 优化能力
1、优化SDK的范围语音功能接口，降低接入门槛；

2、语音降噪效果优化；

3、大幅降低SDK内存消耗；


### GME_SDK2.2 2018-10-30
#### 新增功能：
1、支持多种K歌音效；

2、优化超大房间体验，延时更低更流畅；

3、离线语音消息支持流式语音转文字；

4、windows端支持伴奏；

#### 优化能力
1、语音带宽优化，更省流量；

2、CPU 及内存性能优化；

### GME_SDK2.1 2018-08-21

#### 新增功能：
1、Windows 版支持变声；

2、Windows 端支持离线语音；

3、Windows 端支持 3D 音效；

4、Android SDK 支持 x86 架构；

5、iOS 和 MacSDK 适配 XCode10；

#### 优化能力：
1、离线语音鉴权优化；

2、移动端设备支持单独关闭；

3、优化中音质的网络抗性；

### GME2.0（2018-06-22）
#### 新增功能
1、GME 上架 PC Native、PC Unity 版本；

2、GME 支持 Unreal 引擎；

3、GME 离线语音转文本提供多语言支持，最多支持 120 种语言；

4、GME PC 端支持 3D 实时语音；

#### 优化能力
1、提升通话音量下的高音质体验；

2、降低接入门槛，提供流畅音质、普通音质，高清音质多种选择；

3、稳定性提升；

### GME1.2（2018-03-20）
#### 新增功能
1、GME 支持 Cocos 引擎；

2、提供调节麦克风音量接口；

3、移动端及 PC 端支持小队语音，更好支持 FPS 类游戏；

4、PC 端支持播放伴奏，支持多种格式；

5、Android 端伴奏播放支持更多格式；
#### 优化
 1、优化了狼人杀场景音频前处理效果，多人同时讲话效果更清晰；

 2、优化了 K 歌等场景下的音质表现，支持配置更高音质；

 3、优化了 Moba 场景下语音延迟，小队语音延迟更低；

 4、优化杂音消除算法，语音音质更纯净；

### GME1.1（2017-10-18）
#### 新增功能
1、游戏 SDK 支持多种格式的伴奏和音效；

2、新增游戏场景下离线语音及语音转文本能力；

#### 优化
1、提供进房鉴权的客户端实现模块，降低 SDK 接入门；

2、iOS/Android 端啸叫抑制效果优化；

3、狼人杀场景下音质平稳度、网络抗性等指标优化；
#### 修复
修复了 Android 4.2 及以下系统版本必现 crash 的问题。


## 联系我们
QQ：1558615124

邮箱：GME_Service@tencent.com

## Documentation

[GME Documentation(English)](./README_intl.md)
