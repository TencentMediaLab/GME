## 简介
欢迎使用腾讯云游戏多媒体引擎 SDK 。为方便开发者调试和接入腾讯云游戏多媒体引擎产品 API，这里向您介绍 GME 小队语音的接入技术文档。

### 1.加入房间
用生成的鉴权数据进房。
>注意:加入房间默认不打开麦克风及扬声器。


#### 实时语音房间
> 函数原型
```
ITMGContext EnterRoom(int relationId, ITMG_ROOM_TYPE roomType, byte[] authBuffer)
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------
| relationId		|int    		|房间号，大于等于六位的整数	|
| roomType 			|ITMG_ROOM_TYPE	|房间音频类型				|
| authBuffer    	|Byte[]  		|鉴权数据					|

|ITMG_ROOM_TYPE     	|含义|参数|
| ------------- |------------ | ---- |
| ITMG_ROOM_TYPE_FLUENCY			|流畅音质	|1
| ITMG_ROOM_TYPE_STANDARD			|标准音质	|2
| ITMG_ROOM_TYPE_HIGHQUALITY		|高清音质	|3

> 示例代码  
```
ITMGContext.GetInstance().EnterRoom(roomId,ITMG_ROOM_TYPE_FLUENCY, authBuffer);
```

#### 小队语音房间
> 小队语音简介：
小队语音：玩家在游戏开始前组队，小队语音仅队友可以听到；
全体语音：玩家在游戏开始前和游戏过程中可设置全体语音模式，设置后玩家附近一定范围的人都能听到该玩家讲话。
游戏过程中可随时切换语音模式。

> 假设 A 玩家状态为全局语音，对应 B 玩家在不同的语音情况下声音的模式：

|是否同一小队	|是否范围内	|语音状态	|A 能不能听到 B 的声音	|B 能不能听到 A 的声音	|
| -----------------	| ------------ | ------------ |--------------------------	|--------------------------	|
|同一小队		|是		 	|全局语音	|A 可以听到 B 的声音		|B 可以听到 A 的声音		|
|同一小队		|是		 	|小队语音	|A 可以听到 B 的声音		|B 可以听到 A 的声音		|
|同一小队		|否		 	|全局语音	|A 可以听到 B 的声音		|B 可以听到 A 的声音		|
|同一小队		|否		 	|小队语音	|A 可以听到 B 的声音		|B 可以听到 A 的声音		|
|不同小队		|是		 	|全局语音	|A 可以听到 B 的声音		|B 可以听到 A 的声音		|
|不同小队		|是			|小队语音	|A 不可以听到 B 的声音	|B 可以听到 A 的声音		|
|不同小队		|否		 	|全局语音	|A 不可以听到 B 的声音	|B 不可以听到 A 的声音	|
|不同小队		|否			|小队语音	|A 不可以听到 B 的声音	|B 不可以听到 A 的声音	|

> 假设 A 玩家状态为小队语音，对应 B 玩家在不同的语音情况下声音的模式：

|是否同一小队	|是否范围内	|语音状态	|A 能不能听到 B 的声音	|B 能不能听到 A 的声音	|
| -----------------	| ------------ | ------------ |--------------------------	|--------------------------	|
|同一小队		|是		 	|全局语音	|A 可以听到 B 的声音		|B 可以听到 A 的声音		|
|同一小队		|是		 	|小队语音	|A 可以听到 B 的声音		|B 可以听到 A 的声音		|
|同一小队		|否		 	|全局语音	|A 可以听到 B 的声音		|B 可以听到 A 的声音		|
|同一小队		|否		 	|小队语音	|A 可以听到 B 的声音		|B 可以听到 A 的声音		|
|不同小队		|是		 	|全局语音	|A 可以听到 B 的声音		|B 不可以听到 A 的声音	|
|不同小队		|是			|小队语音	|A 不可以听到 B 的声音	|B 不可以听到 A 的声音	|
|不同小队		|否		 	|全局语音	|A 不可以听到 B 的声音	|B 不可以听到 A 的声音	|
|不同小队		|否			|小队语音	|A 不可以听到 B 的声音	|B 不可以听到 A 的声音	|

>对语音距离范围的补充
>1、是否在语音距离范围内，不影响同一小队成员互相通话。
>2、设置接收语音距离范围参考 API：UpdateCoordinate

> 函数原型
```
ITMGContext  EnterTeamRoom(int relationId,ITMG_ROOM_TYPE roomType, byte[] authBuffer,int teamId, int audioMode)
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------
| relationId		|int    		|房间号									|
| roomType 			|ITMG_ROOM_TYPE	|房间音频类型							|
| authBuffer    	|Byte[] 		|鉴权数据								|
| teamId    		|int    		|加入的小队语音队伍标识码（不能为 0 ）	|
| audioMode    		|int    		|0 代表全局语音，1 代表小队语音			|

> 示例代码  
```
ITMGContext.GetInstance().EnterTeamRoom(roomId,ITMG_ROOM_TYPE_FLUENCY, authBuffer,1000,1);
```




### 2.修改小队语音通话模式
通过此函数修改小队语音通话模式。
> 函数原型  
```
ITMGRoom int ChangeTeamAudioMode(int audioMode)
```
|参数     | 类型         |意义|
| ------------- |:-------------:|-------------
| audioMode    |int     |0 代表全局语音，1 代表小队语音|

> 示例代码  
```
ITMGContext.GetInstance().GetRoom().ChangeTeamAudioMode(1);
```

### 3.设置接收语音距离范围
此函数用于设置接收的语音范围（距离以游戏引擎为准）。
>注意：1、调用此函数需要每帧调用
>2、进房的前提下调用。
>3、游戏中每个用户都需要调用。

> 函数原型  
```
ITMGRoom int UpdateCoordinate(int pos_x, int pos_y, int pos_z, int range)
```
|参数     | 类型         |意义|
| ------------- |-------------|-------------
| pos_x    |int         |传入用户自身坐标的 x 坐标						|
| pos_y    |int         |传入用户自身坐标的 y 坐标						|
| pos_z    |int         |传入用户自身坐标的 z 坐标						|
| range 	 |int 	  |传入收听的范围，以游戏引擎的距离单位为单位		|

> 示例代码  
```
ITMGContext.GetInstance().GetRoom().UpdateCoordinate(pos_x,pos_y,pos_z,10);
```