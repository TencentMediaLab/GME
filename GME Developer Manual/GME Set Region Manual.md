# 设定 SDK 服务区域

为方便 GME 开发者调试和接入腾讯云游戏多媒体引擎产品 API，本文为您介绍适用于 GME 设置 SDK 可服务区域的使用参考文档。

## 设定 SDK 服务区域

调用此接口设置 GME SDK 可服务区域。必须在调用 Init 接口前调用，调用即生效。

### 接口原型

```
//c++
virtual void SetRegion(const char* region) = 0;
//iOS
-(void)SetRegion:(NSString*)region;
//Android
public abstract void SetRegion(String region);
```

### 参数

|参数|类型|含义|
|----|----|----|
|region|string|服务区域简称，目前只能填"SG" |