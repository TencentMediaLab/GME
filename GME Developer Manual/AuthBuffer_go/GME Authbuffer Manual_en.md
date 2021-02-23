# AuthBuffer User Guide
## File structure and description
* [Source.go](https://github.com/TencentMediaLab/GME/blob/GME_2.8_Dev/GME%20Developer%20Manual/AuthBuffer_go/Source.go) : Sample Code of GME AuthBuffer
* [AuthBuffer.go](https://github.com/TencentMediaLab/GME/blob/GME_2.8_Dev/GME%20Developer%20Manual/AuthBuffer_go/AuthBuffer.go) ： AuthBuffer logic SDK

## Example
* 1、Download Source.go and AuthBuffer.go to the machine with the go runtime
* 2、Modify the appID and appKey in Source.go to the corresponding parameters of your own project
* 3、Run the command "go Source.go AuthBuffer.go" to start the AuthBuffer computing service
* 4、OpenLink "http://[Server IP]:8989/authbuffer?sdkappid=xxx&roomid=yyy&openid=zzz" to get the AuthBuffer string after base64.
* 5、After the client gets the string in **step 3**,  decoding with base64 and pass it into the client API EnterRoom as a parameter
* 6、Complete entry operation

```
Ref client API:
public abstract int EnterRoom(String roomID, int roomType, byte[] authBuffer);
```
