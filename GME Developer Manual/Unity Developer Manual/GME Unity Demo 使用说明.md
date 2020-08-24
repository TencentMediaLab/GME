为方便 Unity 开发者调试和接入腾讯云游戏多媒体引擎产品 API，这里向您介绍适用于 Unity 开发的DEMO使用文档。


## 1.登录

![image](Image/u6.png)
> 图1 登录界面

点击“Login”按钮，就会使用设置的userid进行登录。登录后，界面会多出两个按钮，“Voice Chat”和“Voice Message”。

![image](Image/u7.png)
> 图2模式选择界面

点击“Voice Chat”按钮会进入语音聊天功能，点击“Voice Message”按钮会进入语音消息功能。

## 2.语音聊天
点击图2中的“Voice Chat”按钮，进入如下界面：

![image](Image/u8.png)
> 图3进房界面

Room Id：房间号相同的成员会进入同一个房间。

RoomType：控制语音质量：

- Fluency：流畅音质。流畅优先、超低延迟实时语音，应用在游戏内开黑场景，适用于FPS、MOBA等类型的游戏；

- Standard：标准音质。音质较好，延时适中，适用于狼人杀、棋牌等休闲游戏的实时通话场景；

- Hign Quality：高清音质。超高音质，延时相对大一些，适用于音乐舞蹈类游戏以及语音社交类APP；适用于播放音乐、线上K歌等有高音质要求的场景；

点击“JoinRoom”按钮 进入房间。

![image](https://main.qcloudimg.com/raw/a2ac816eacd95dfa89c8a5cee4f93f40.png)
> 图4房间内界面


Speaker: 打开扬声器。

Talking Members：房间内正在说话的人

3D Voice Effect: 打开3D音效。

Voice Change：修改实时语音音效。

3D Voice Effect setting:

- x:  自身x轴

- y:  自身y轴

- z:  自身z轴

- xr: 绕x坐标轴旋转的方向

- yr: 绕y坐标轴旋转的方向

- zr: 绕z坐标轴旋转的方向

## 3.语音消息

点击图2中的“Voice Message”按钮进入语音消息。

![image](Image/u10.png)
> 图5语音消息界面

Language：使用的语言

Audio：录制的语音消息和语音时长, 点击 ![image](Image/u11.png) 播放录音，播放过程中再次点击，结束播放。

Audio-to-Text：语音转换成的文字，按住“Push To Talk”按钮，开始录制；松开此按钮，结束录制。
