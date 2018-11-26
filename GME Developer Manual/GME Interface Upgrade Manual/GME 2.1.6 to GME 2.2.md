## 简介
欢迎使用腾讯云游戏多媒体引擎 SDK 。为方便开发者调试和接入腾讯云游戏多媒体引擎产品 API，这里向您介绍适用于开发的接口升级技术文档(GME 2.1.6 升级 GME 2.2)。

## 新增功能
1、房间号由 int32 类型升级为字符串类型；

2、支持多种K歌音效；

3、优化超大房间体验，延时更低更流畅；

4、离线语音消息支持流式语音转文字； 

5、windows端支持伴奏；

## 优化能力
1、退房改为异步执行，防止ANR；

2、语音带宽优化，更省流量； 

3、CPU 及内存性能优化；

## 主要接口更新
- ### ExitRoom 退房操作由同步改为异步调用，参照 RoomExitComplete 回调函数处理，回值为AV_OK的时候代表异步投递成功。
- #### 如果应用中有退房后立即进房的场景，在接口调用流程上，需要先等接口 ExitRoom 的回调 RoomExitComplete 回来后，再调用 EnterRoom 接口。
```
public abstract int ExitRoom();
```


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