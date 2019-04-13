## 简介
欢迎使用腾讯云游戏多媒体引擎 SDK 。为方便 PlayStation4 开发者调试和接入腾讯云游戏多媒体引擎产品 API，这里向您介绍适用于 PlayStation4 开发的工程配置。

## SDK 动态库导入

### 1. PlayStation4 版本
首先确定使用的 PlayStation4 SDK 版本，GME PlayStation4 SampleCode 使用的版本为5000。

### 2. 导入 SDK
将 GME 头文件及库函数导入到工程所在目录。

### 3. 引入头文件
在工程中引入 GME 头文件 tmg_sdk.h。

```
#include "tmg_sdk.h"
```

### 4. 引入库文件
在工程中引入 GME 相关库文件：GME_stub.a、GME_stub_weak.a 以及 GME.prx。

```
#pragma comment(lib,"GME_stub_weak.a")
```

### 5. 指定堆大小
在工程中显式指定堆的大小，参考代码如下：
```
unsigned int sceLibcHeapExtendedAlloc = 1;  /* Switch to dynamic allocation */
size_t sceLibcHeapSize = 100*1024*1024;     /* Must larger than 100M */
```

### 6. 执行代码
使用 GME SDK 功能，调用相应函数，请参考 GME Sample Code。

