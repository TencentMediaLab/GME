为方便 GME 开发者调试和接入腾讯云游戏多媒体引擎产品 API，这里向您介绍适用于实时语音ASR的开发的接入技术文档。

## 启动ASR

在成功进房之后，开启麦克风采集及上行的情况下，调用次接口可以启动实时语音ASR功能。如果不传入参数，则默认是普通话实时语音ASR。传入的参数决定了ASR的目标语言。

### 示例代码
```
//启动实时语音ASR
ret = (int)[[ITMGContext GetInstance] StartRealTimeASR];
ret = (int)[[ITMGContext GetInstance] StartRealTimeASR:_ASRID.text];
//停止实时语音ASR
ret = (int)[[ITMGContext GetInstance] StopRealTimeASR];
```

## 目标语言参数

- 16k_en：英语
- 16k_ca：粤语
- 16k_ko：韩语
- 16k_zh-TW：中文普通话繁体
- 16k_ja：16k 日语
- 16k_wuu-SH：16k 上海话方言
- 16k_th：泰语

## 回调
启动实时语音ASR功能之后，会在回调 ITMG_MAIN_EVENT_TYPE_REALTIME_ASR 中返回对应的信息。

### 回调示例代码

```
-(void)OnEvent:(ITMG_MAIN_EVENT_TYPE)eventType data:(NSDictionary *)data{
    NSString* log =[NSString stringWithFormat:@"OnEvent:%d,data:%@", (int)eventType, data];
    [self showLog:log];
    NSLog(@"====%@====",log);
    switch (eventType) {    
        case ITMG_MAIN_EVENT_TYPE_REALTIME_ASR :{
            if (ITMG_REALTIME_ASR_START == ((NSNumber *)data[@"sub_type"]).intValue) {
                UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"实时翻译" message:[NSString stringWithFormat:@"开始 结果:%@ 错误信息:%@",data[@"result"],data[@"error_info"]] delegate:NULL cancelButtonTitle:@"OK" otherButtonTitles:nil];
                [alert show];
                if (((NSNumber *)data[@"result"]).intValue == 0) {
                    isRealTimeASRRunnig = true;
                    asrResult = @"";
                }
            }
         }
     }
 }
```
