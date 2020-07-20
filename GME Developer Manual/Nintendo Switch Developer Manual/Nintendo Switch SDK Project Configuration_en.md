## Overview
Thank you for using Tencent Cloud Game Multimedia Engine SDK. This document provides project configuration that makes it easy for Nintendo Switch developers to debug and access the APIs for Game Multimedia Engine.

## SDK Import GME Static Library

### 1. Confirm Nintendo Switch SDK Version
In order to integration GME, the 7.3.0 or above version of Nintendo Switch SDK should be used.

### 2. Import SDK Files
Import the GME header files and library functions into the directory where the project is located.

### 3. Import SDK Heard Files
Introducing GME header files "tmg_sdk.h" into your project.

```
#include "tmg_sdk.h"
```

### 4. Init the network module
In the project code, add the following lines to init the network module:

```
nn::nifm::Initialize();
nn::ssl::Initialize();
nn::socket::Initialize();
curl_global_init(CURL_GLOBAL_DEFAULT);
```

### 5. Execute Code
Use the GME SDK feature to call the corresponding function, please refer to the GME Sample Code.
