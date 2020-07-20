## 简介
欢迎使用腾讯云游戏多媒体引擎 SDK 。为方便 Nintendo Switch 开发者调试和接入腾讯云游戏多媒体引擎产品 API，这里向您介绍适用于 Nintendo Switch 开发的工程配置。

## SDK 静态库导入

### 1. Nintendo Switch 版本
首先确定使用的 Nintendo Switch SDK 版本，需要配合 7.3.0 以上的 Switch SDK 版本使用。

### 2. 导入 SDK
将 GME 头文件及库函数导入到工程所在目录。

### 3. 引入头文件
在工程中引入 GME 头文件 tmg_sdk.h。

```
#include "tmg_sdk.h"
```

### 4. 添加权限
switch平台需要按顺序调用以下接口初始化网络模块：
```
nn::nifm::Initialize();
nn::ssl::Initialize();
nn::socket::Initialize();
curl_global_init(CURL_GLOBAL_DEFAULT);
```

### 5. 执行代码
使用 GME SDK 功能，调用相应函数，请参考 GME Sample Code。

