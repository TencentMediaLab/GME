## Overview

Thank you for using [Tencent Cloud Game Multimedia Engine SDK](https://intl.cloud.tencent.com/product/tmg?idx=2). This document provides a detailed steps  that makes it easy for developers to integrate GME SDK.

Follow the steps below to use GME:
1. [Create a GME service in Tencent Cloud backend](https://intl.cloud.tencent.com/document/product/607/10782#create-a-service);
2. [Download the corresponding version SDK](https://intl.cloud.tencent.com/document/product/607/10782#download-sdk);
3. [Import the SDK to your project by referring to the integration API document](https://intl.cloud.tencent.com/document/product/607/10782#related-sdk-technical-documents);
4. [Check the daily operation backend statistics](https://intl.cloud.tencent.com/document/product/607/10782#usage-statistics-on-the-console);
5. [Troubleshoot special problems during integration and provide feedback](https://intl.cloud.tencent.com/document/product/607/10782#special-problems-handling);


## Create a service
#### 1. After you log in, click **Create Application**.
![](https://main.qcloudimg.com/raw/07aa53fca65f84c4c43ba73417796ce4.png)

#### 2. Enter required information.  
Enter required information on the page, and select the services as needed. 
> The charges vary with different billing modes. The billing mode cannot be modified once set. For more information on the charges, please see [Product Prices](https://intl.cloud.tencent.com/product/tmg?idx=1#price) and consult relevant Tencent Cloud service personnel.
> If you are creating a game application, you need to select the corresponding platform engine, and select an applicable sampling rate according to the solution provided by the technician.
> The setting for voice messaging and voice-to-context conversion services can be modified.

![](https://main.qcloudimg.com/raw/ff4d89cb84137670142535271ddbf2b2.png)


#### 3. After an application is created, the application management list displays the new application.
The AppID in the list will be used as a parameter in the integration of SDK.

![](https://main.qcloudimg.com/raw/664dbdaded600e650ed44b25b18a3ca8.png)


#### 4. Click **Setting** corresponding to the application in the list to enter the application setting page.
![](https://main.qcloudimg.com/raw/ad13c32afec03001858782a3d000ac28.png)
You can click **Modify** to modify the information in the Application Information module.


#### 5. You can obtain the authentication of your application in the Authentication Information module.
![](https://main.qcloudimg.com/raw/bed3c36cdf3fcb421878c64cd5d775ba.png)

 - The permission key in this module will be used as a parameter in the integration of SDK. 
 - The modified key takes effect within 15 minutes to 1 hour. Frequent modification is not recommended.
 - Click **Download Public and Private Keys** to download the public and private keys for the offline voice feature of the application.
 - Only the account creating the game, the main account and the global collaborator can **Reset the Keys**.
 
![](https://main.qcloudimg.com/raw/2eb67cb291d211ed6eaa352fd08c10f6.png)

-  **For more information on authentication, please see the [GME key documentation](https://intl.cloud.tencent.com/document/product/607/12218)**.


#### 6. Enable and disable businesses and services

You can enable or disable businesses and services.
![](https://main.qcloudimg.com/raw/b1335c003b6a01a049ca992ed36feec2.png)
![](https://main.qcloudimg.com/raw/cabf597540281e1df1e028944b5dde01.png)


## Download SDK 
#### 1. Download link
Please download applicable Demo and SDK from [Tencent Cloud GME official website](https://cloud.tencent.com/document/product/607/18521).

#### 2. Integration preparations
To integrate the SDK, you need to use the AppID and the permission key provided by Tencent Cloud, as mentioned above.
- The permission key in the Authentication Information module is used for the integration of voice chat.
- The downloaded public and private keys in the Authentication Information module are used for the integration of offline voice.

For the configuration for other platforms, please see the project configuration document of relevant platform.

#### 3. Notes for using the official sample code
The sample code provides a Tencent Cloud test account for you to experience features. If you want to use personal or corporate test account, you need to change the Tencent Cloud test account AppID to the AppID obtained in the console on relevant page in the Demo, and modify the permission key for voice chat in the AVChatViewController-GetAuthBuffer function.

## Related SDK Technical Documents
**Unity engine** 

[Unity Project Configuration](./GME%20Developer%20Manual/Unity%20Developer%20Manual/Unity%20SDK%20Project%20Configuration_intl.md)     
[Unity integration technical document](./GME%20Developer%20Manual/Unity%20Developer%20Manual/Unity%20SDK%20Developer%20Manual_intl.md)

**Unreal engine**

[Unreal Engine Project Configuration](./GME%20Developer%20Manual/Unreal%20Engine%20Developer%20Manual/Unreal%20Engine%20SDK%20Project%20Configuration_intl.md)     
[Unreal Engine integration technical document](./GME%20Developer%20Manual/Unreal%20Engine%20Developer%20Manual/Unreal%20Engine%20SDK%20Developer%20Manual_intl.md)

**Cocos2D engine**

[Cocos2D-X Project Configuration](./GME%20Developer%20Manual/Cocos2D-X%20Developer%20Manual/Cocos2d%20SDK%20Project%20Configuration_intl.md)     
[Cocos2D-X integration technical document](./GME%20Developer%20Manual/Cocos2D-X%20Developer%20Manual/Cocos2d%20SDK%20Developer%20Manual_intl.md)

**Native application**

[PC (C++) integration technical document](./GME%20Developer%20Manual/Windows%20Developer%20Manual/C%2B%2B%20SDK%20Developer%20Manual_intl.md)
[iOS Project Configuration](./GME%20Developer%20Manual/iOS%20Developer%20Manual/iOS%20SDK%20Project%20Configuration_intl.md)     
[iOS integration technical document](./GME%20Developer%20Manual/iOS%20Developer%20Manual/iOS%20SDK%20Developer%20Manual_intl.md)
[Android Project Configuration](./GME%20Developer%20Manual/Android%20Developer%20Manual/Android%20SDK%20Project%20Configuration_intl.md)     
[Android integration technical document](./GME%20Developer%20Manual/Android%20Developer%20Manual/Android%20SDK%20Developer%20Manual_intl.md)


## Usage Statistics on the Console
### Open usage statistics on the application management page

Click **Usage Statistics** corresponding to the application you want to query.
![](https://main.qcloudimg.com/raw/664dbdaded600e650ed44b25b18a3ca8.png)

Usage statistics include statistics for voice chat, and for voice messaging and voice-to-text conversion.

### Usage statistics for voice chat

This page displays the real-time DAU data of the voice chat application.
- **Total Voice Chat DAU** displays the total DAU of voice chat yesterday
- **China Mainland Voice Chat DAU** displays the DAU of voice chat in Mainland China
- **Overseas Voice Chat DAU** displays the DAU of voice chat overseas
- The total DAU data in the last seven days are displayed by default. You can also view the usage data for other statistical periods and other regions.

![](https://main.qcloudimg.com/raw/0fda092823ea45f4823ba43089c85d79.png)

### Usage statistics for voice messaging and voice-to-text conversion
This page displays the DAU data of the offline voice application.
- **Offline Voice DAU** displays the total DAU of offline voice yesterday
- **Voice-to-text Conversions** displays the number of voice-to-text conversions yesterday
- The total DAU data in the last seven days are displayed by default. You can also view the usage data for other statistical periods.

![](https://main.qcloudimg.com/raw/36b64b6df2a6cdabdb91503d06fd225b.png)

## Special Problems Handling
[FAQs](./GME%20Developer%20Manual/GME%20FAQ%20Manual_intl.md)     
[Error Codes](./GME%20Developer%20Manual/GME%20Error%20Code_intl.md)
