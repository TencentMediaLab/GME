## Overview
Thank you for using Tencent Cloud Game Multimedia Engine SDK. This document provides project configuration that makes it easy for PlayStation4 developers to debug and access the APIs for Game Multimedia Engine.

## SDK Import GME Dynamic Library

### 1. Confirm PlayStation4 Version
First determine the version of the PlayStation4 SDK you are using, and the version of the GME PlayStation4 SampleCode is 5000.

### 2. Import SDK Files
Import the GME header files and library functions into the directory where the project is located.

### 3. Import SDK Heard Files
Introducing GME header files "tmg_sdk.h" into your project.

```
#include "tmg_sdk.h"
```

### 4. Import SDK Library Files
Introducing GME related library files in the project：GME_stub.a、GME_stub_weak.a and GME.prx。

```
#pragma comment(lib,"GME_stub_weak.a")
```

### 5. Specify The Size of The Stack
Explicitly specify the size of the stack in the project, the reference code is as follows:
```
unsigned int sceLibcHeapExtendedAlloc = 1;  /* Switch to dynamic allocation */
size_t sceLibcHeapSize = 100*1024*1024;     /* Must larger than 100M */
```

### 6. Execute Code
Use the GME SDK feature to call the corresponding function, please refer to the GME Sample Code.
