# AuthBuffer 使用指南
## 文件结构和说明
* Source.go : GME AuthBuffer的使用示例程序
* AuthBuffer.go ： AuthBuffer的逻辑模块

## 示例使用说明
* 1， 将Source.go 和 AuthBuffer.go 分别下载到拥有go运行环境的机器
* 2， 将Source.go 中的 appID 和 appKey 更改为自己工程的对应参数
* 3， 运行 go Source.go AuthBuffer.go 启动AuthBuffer计算服务
* 4， 访问 [服务器IP]:8989 即可完成得到经过base64后的进房AuthBuffer字符串
* 5， 客户端得到 **步骤3** 中的字符串后，base64解码作为参数传入客户端API EnterRoom 中
* 6， 完成进房操作

```
参见客户端API:
public abstract int EnterRoom(String roomID, int roomType, byte[] authBuffer);
```
