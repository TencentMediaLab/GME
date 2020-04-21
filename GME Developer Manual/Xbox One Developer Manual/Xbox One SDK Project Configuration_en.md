## Overview
Thank you for using Tencent Cloud Game Multimedia Engine SDK. This document provides project configuration that makes it easy for Xbox One developers to debug and access the APIs for Game Multimedia Engine.


## Configuring Project

### 1. Improt SDK script files
Copy the "include" folder to the project folder.

### 2. Import SDK related files 
Copy "gmesdk.lib" to the project folder.

Copy "gmesdk.dll" to the exe output directory.

### 3. Add to the project
Add the "gmesdk.lib" file on the attribute page of the project:Linker -> Input -> Additional Dependencies.

Add the directory of "gmesdk.lib" on the attribute page of the project:Linker -> Generral -> Additional Library Directories.

### 4. Add permissions
Add the capabilities to the Package.appxmanifest file to allow network access and microphone usage for GME.

```
  <Capabilities>
    <Capability Name="internetClientServer" />
    <mx:Capability Name = "kinectAudio" />
    <mx:Capability Name = "kinectGamechat" />
  </Capabilities>
```