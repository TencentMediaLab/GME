# GME 链接蓝牙耳机动态切换采集设备

在接入 Wwise 方案的情况下使用此接口，采集设备可以动态切换为蓝牙耳机或者外放。

## 接口原型

```
GMESDK_API int GMESDK_CALL GMESDK_EnableBluetoothCapture(bool enable)
```

**函数意义:** 蓝牙耳机接入的情况下，动态选择设备

**参数含义:** 是否采用蓝牙采集

**调用时机:**
1. 在设置切换时
2. 在EnableCapture时

**参数对应表现细节**

<table>
   <tr>
      <td  rowspan="2">参数为true </td>
      <td>插入蓝牙为电话音量，耳机采集</td>
   </tr>
   <tr>
      <td>不插入蓝牙为媒体音量，手机采集</td>
   </tr>
   <tr>
      <td  rowspan="2">参数为false</td>
      <td>插入蓝牙为媒体音量，手机采集</td>
   </tr>
   <tr>
      <td>不插入蓝牙为媒体音量，手机采集</td>
   </tr>
</table>


## Android特殊设置
在安卓平台使用，需要在 AndroidManifest.xml 文件中添加以下权限：

```
    <uses-permission android:name="android.permission.BLUETOOTH"/>
    <uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
    <uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS" />
```
