## 简介
欢迎使用腾讯云游戏多媒体引擎 SDK 。为方便开发者调试和接入腾讯云游戏多媒体引擎产品 API，这里向您介绍适用于开发的接口升级技术文档(GME 2.1 升级 GME 2.2)。

## 新增功能
1、GenAuthBuffer 参数 roomId 类型由 int32 类型改为字符串类型；
2、EnterRoom 参数 roomId 类型由 int32 类型改为字符串类型；
3、SetMicVolume 由设置麦克风硬件音量改为设置麦克风软件音量；
4、GetMicVolume 由获取麦克风硬件音量改为获取麦克风软件音量；
5、支持多种K歌音效；
6、优化超大房间体验，延时更低更流畅；
7、离线语音消息支持流式语音转文字；
8、windows端支持伴奏；

## 优化能力
1、房间号由 int32 类型升级为字符串类型；
2、音量调节接口由设置及获取硬件音量改为设置及获取软件音量；
3、语音带宽优化，更省流量；
4、CPU 及内存性能优化；

## 主要接口更新
- ### ExitRoom 退房操作由同步改为异步调用，参照RoomExitComplete回调函数处理，回值为AV_OK的时候代表异步投递成功。
```
public abstract int ExitRoom();
```

- ### genAuthBuffer 参数 roomId 类型由 int32 类型改为字符串类型
```
AuthBuffer public native byte[] genAuthBuffer(int sdkAppId, String roomId, String identifier, String key)
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------|
| appId    		|int   		|来自腾讯云控制台的 SdkAppId 号码		|
| roomId    		|String   		|房间号，最大支持127字符（离线语音房间号参数必须填0）|
| openID    	|String 	|用户标识					|
| key    		|String 	|来自腾讯云[控制台](https://console.cloud.tencent.com/gamegme)的密钥				|

- ### EnterRoom 参数 roomId 类型由 int32 类型改为字符串类型
```
ITMGContext public abstract void  EnterRoom(String roomId, int roomType, byte[] authBuffer)
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------|
| roomId 	|String		|房间号，最大支持127字符|
| roomType 	|int		|房间音频类型|
| authBuffer	|byte[]	|鉴权码|


## 新增接口
- ### public int SetKaraokeType(int type) K歌音效
参数 type 表示本端音频变声类型。

|类型参数     |参数代表|意义|
| ------------- |-------------|------------- |
|ITMG_KARAOKE_TYPE_ORIGINAL 		|0	|原声			|
|ITMG_KARAOKE_TYPE_POP 				|1	|流行			|
|ITMG_KARAOKE_TYPE_ROCK 			|2	|摇滚			|
|ITMG_KARAOKE_TYPE_RB 				|3	|嘻哈			|
|ITMG_KARAOKE_TYPE_DANCE 			|4	|舞曲			|
|ITMG_KARAOKE_TYPE_HEAVEN 			|5	|空灵			|
|ITMG_KARAOKE_TYPE_TTS 				|6	|语音合成		|


- ### public void StartRecordingWithStreamingRecognition (String filePath,String language) 流式语音转文字
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------|
| filePath    	|String	|存放的语音路径	|
| language 	|String	|需要转换的语言代码，参考[语音转文字的语言参数参考列表](/GME%20Developer%20Manual/GME%20SpeechToText.md)|

- ### ITMG_MAIN_EVNET_TYPE_PTT_STREAMINGRECOGNITION_COMPLETE  启动流式录音的回调
启动录音完成后的回调调用函数 OnEvent，事件消息为 ITMG_MAIN_EVNET_TYPE_PTT_STREAMINGRECOGNITION_COMPLETE， 在 OnEvent 函数中对事件消息进行判断。传递的参数包含以下四个信息。

|消息名称     | 意义         |
| ------------- |:-------------:|
| result    	|用于判断流式录音是否成功的返回码			|
| text    		|语音转文字识别的文本	|
| file_path 	|录音存放的本地地址		|
| file_id 		|录音在后台的 url 地址	|

|错误码     | 意义         |处理方式|
| ------------- |:-------------:|:-------------:|
|32775	|流式语音转文本失败，但是录音成功	|调用 UploadRecordedFile 接口上传录音，再调用 SpeechToText 接口进行语音转文字操作
|32777	|流式语音转文本失败，但是录音成功，上传成功	|返回的信息中有上传成功的后台 url 地址，调用 SpeechToText 接口进行语音转文字操作

离线语音流程图：
![](https://main.qcloudimg.com/raw/4c875d05cd2b4eaefba676d2e4fc031d.png)